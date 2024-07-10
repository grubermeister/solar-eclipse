package dev.atomixsoft.solar_eclipse.client;

import dev.atomixsoft.solar_eclipse.client.logging.Logger;

import dev.atomixsoft.solar_eclipse.client.util.ImGuiManager;
import dev.atomixsoft.solar_eclipse.client.util.input.Controller;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;

import dev.atomixsoft.solar_eclipse.client.util.Window;

import dev.atomixsoft.solar_eclipse.client.util.input.Input;

import dev.atomixsoft.solar_eclipse.client.audio.AudioMaster;

import dev.atomixsoft.solar_eclipse.client.graphics.RenderCmd;

import dev.atomixsoft.solar_eclipse.client.scene.SceneHandler;
import dev.atomixsoft.solar_eclipse.client.scene.MainScene;
import dev.atomixsoft.solar_eclipse.client.scene.TestScene;


public class ClientThread implements Runnable {
    private final Controller m_Controller;
    private final Thread m_Thread;
    private final Logger m_Logger;

    private volatile boolean m_Running;

    private String m_Title;
    private GLFWErrorCallback m_ErrorCallback;
    private Window m_Window;
    private SceneHandler m_Scenes;
    private ImGuiManager m_GUIManager;


    public ClientThread(String title, Logger logger) {
        m_Title = title;
        m_Running = false;

        this.m_Controller = new Controller();
        this.m_Thread = new Thread(this, "Main_Thread");
        this.m_Logger = logger;
        this.m_GUIManager = new ImGuiManager();
    }

    public synchronized void start() {
        if(m_Running)
            return;

        m_ErrorCallback = GLFWErrorCallback.createPrint(System.err);
        if(!glfwInit()) {
            this.m_Logger.error("Failed to initialize GLFW!");
            throw new RuntimeException("Failed to initialize the program!");
        }

        m_Running = true;
        m_Thread.start();
    }

    public synchronized void stop() {
        if(!m_Running)
            return;

        m_Running = false;
    }

    private void initialize() {
        m_GUIManager.init(m_Window.getHandle(), "#version 130");
        AudioMaster.Init();

        //TODO: Load Input from config
        m_Controller.addBinding("camUp", GLFW_KEY_UP);
        m_Controller.addBinding("camDown", GLFW_KEY_DOWN);
        m_Controller.addBinding("camLeft", GLFW_KEY_LEFT);
        m_Controller.addBinding("camRight", GLFW_KEY_RIGHT);

        m_Scenes.addScene("Test", new TestScene());
        m_Scenes.addScene("Main", new MainScene());

        m_Scenes.setActiveScene("Test");
        m_Scenes.getActiveScene().resize(m_Window.getWidth(), m_Window.getHeight());
    }

    private void dispose() {
        this.m_Logger.debug("Client thread cleaning up...");

        AudioMaster.CleanUp();

        try {
            if(m_Scenes != null)
                m_Scenes.dispose();

            if(m_Window != null)
                m_Window.close();

            m_GUIManager.dispose();
            AssetLoader.Dispose();

            if(m_ErrorCallback != null) {
                m_ErrorCallback.free();
                glfwSetErrorCallback(null);
            }

            glfwTerminate();
            m_Thread.join(1);

            this.m_Logger.debug("Client thread terminated.");
            System.exit(0);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public void run() {
        this.m_Logger.debug("Client thread running...");

        m_Window = new Window(m_Title, 800, 600);
        m_Window.show();

        RenderCmd.Init();
        RenderCmd.ClearColor(0.05f, 0.05f, 0.05f);

        m_Scenes = new SceneHandler(m_Controller, m_Window);
        initialize();

        double accumulator = 0.0;
        double optimal = 1.0 / 60.0;
        double currentTime = System.nanoTime() / 1e9;
        double newTime = System.nanoTime() / 1e9;
        double frameTime = 0.0;

        Input input = Input.Instance();
        while(m_Running) {
            if(m_Window.shouldClose()) {
                stop();
                continue;
            }

            if(m_Window.hasResized() && m_Window.isResizable()) {
                m_Scenes.resize(m_Window.getWidth(), m_Window.getHeight());
                m_Window.setResized(false);
            }

            newTime = System.nanoTime() / 1e9;
            frameTime = newTime - currentTime;
            currentTime = newTime;
            accumulator += frameTime;

            while(accumulator >= optimal) {
                input.process();
                m_Scenes.update(optimal);
                accumulator -= optimal;
            }

            RenderCmd.Clear();
            m_Scenes.render(m_GUIManager);
            m_Window.swapBuffers();
            glfwPollEvents();

            if(!m_Window.vSyncEnabled())
                sleep(currentTime);
        }

        dispose();
    }

    private void sleep(double currentTime) {
        double desiredTime = 1.0 / 60.0;
        long sleepTime = (long) ((currentTime - System.nanoTime() + desiredTime) / 1e9);

        try {
            if (sleepTime > 0)
                Thread.sleep(sleepTime);

        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
