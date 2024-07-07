package dev.atomixsoft.solar_eclipse.client;

import dev.atomixsoft.solar_eclipse.client.util.AssetLoader;
import dev.atomixsoft.solar_eclipse.client.util.ImGuiManager;

import static org.lwjgl.glfw.GLFW.*;

import dev.atomixsoft.solar_eclipse.client.util.Window;

import dev.atomixsoft.solar_eclipse.client.util.input.Input;
import dev.atomixsoft.solar_eclipse.client.util.logging.Logger;
import dev.atomixsoft.solar_eclipse.client.util.logging.Logger.Log4jGLFWErrorCallback;
import dev.atomixsoft.solar_eclipse.client.audio.AudioMaster;

import dev.atomixsoft.solar_eclipse.client.graphics.RenderCmd;

import dev.atomixsoft.solar_eclipse.client.scene.SceneHandler;
import dev.atomixsoft.solar_eclipse.client.scene.MainScene;
import dev.atomixsoft.solar_eclipse.client.scene.TestScene;


public class ClientThread implements Runnable {
    private final Thread m_Thread;
    private final Logger logger;

    private volatile boolean m_Running;

    private String m_Title;
    private Window m_Window;
    private SceneHandler m_Scenes;
    private ImGuiManager m_GUIManager;


    public ClientThread(String title, Logger logger) {
        m_Title = title;
        m_Running = false;

        this.m_Thread = new Thread(this, "Client_Thread");
        this.logger = logger;
        this.m_GUIManager = new ImGuiManager(this.logger);
    }

    public synchronized void start() {
        if(m_Running)
            return;

        glfwSetErrorCallback(logger.new Log4jGLFWErrorCallback());
        if(!glfwInit()) 
            throw new RuntimeException("Failed to initialize GLFW!");

        m_Running = true;
        m_Thread.start();
    }

    public synchronized void stop() {
        if(!m_Running)
            return;

        dispose();
        m_Running = false;

        if(m_Thread.isAlive()) {
            logger.error("Thread did not stop in time, forcing interrupt...");
            m_Thread.interrupt();
        }
    }

    public synchronized boolean isRunning() {
        return m_Running;
    }

    private void initialize() {
        m_GUIManager.init(m_Window.getHandle(), "#version 130");
        logger.debug("GUI loaded.");

        AudioMaster.Init(this.logger);
        logger.debug("Audio loaded.");

        try {
            m_Scenes.addScene("Test", new TestScene());
            m_Scenes.addScene("Main", new MainScene());
            logger.debug("Scenes loaded.");
            
            m_Scenes.setActiveScene("Test");
            m_Scenes.getActiveScene().resize(m_Window.getWidth(), m_Window.getHeight());
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
        }
    }

    private void dispose() {
        logger.debug("Cleaning up...");

        try {
            if(m_Scenes != null)
                m_Scenes.dispose();
            logger.debug("Scenes unloaded.");

            AudioMaster.CleanUp(this.logger);
            logger.debug("Audio unloaded.");

            AssetLoader.Dispose();
            logger.debug("Assets unloaded.");

            m_GUIManager.dispose();
            if(!m_Window.shouldClose())
                m_Window.close();
            logger.debug("GUI unloaded.");

            glfwPostEmptyEvent();
            glfwTerminate();
            logger.debug("GLFW terminated.");
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            e.printStackTrace();
        } finally {
            try {
                m_Thread.join();
                logger.debug("Thread cleaned.");
            } catch (InterruptedException e) {
                logger.error("Error joining thread: " + e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        m_Window = new Window(m_Title, 800, 600);
        m_Window.show();

        RenderCmd.Init();
        RenderCmd.ClearColor(0.05f, 0.05f, 0.05f);

        m_Scenes = new SceneHandler(m_Window);
        initialize();
        Input input = Input.Instance();

        double accumulator = 0.0;
        double optimal = 1.0 / 60.0;
        double currentTime = System.nanoTime() / 1e9;
        double newTime = System.nanoTime() / 1e9;
        double frameTime = 0.0;

        logger.debug("Running...");
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

            try {
                m_Scenes.render(m_GUIManager);
                m_Window.swapBuffers();
            } catch (Exception e) {
                logger.error(e.getStackTrace().toString());
            }

            glfwPollEvents();

            if(!m_Window.vSyncEnabled())
                sleep(currentTime);
        }
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
