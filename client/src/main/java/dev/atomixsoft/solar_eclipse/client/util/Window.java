package dev.atomixsoft.solar_eclipse.client.util;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import dev.atomixsoft.solar_eclipse.client.util.input.InputHandler;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryStack.stackPush;


/**
 * <p>Handles everything related to our Window. This is required for any OpenGL related functions to matter.
 * If you make your own Window class or want things set up different, I'd be cautious.</p>
 */
public class Window {
    private long m_Handle;

    private String m_Title;
    private int m_Width, m_Height;
    private boolean m_Resizable, m_Focused, m_Resized, m_VSync;


    public Window(String title, int width, int height) {
        this(title, width, height, false, true);
    }

    public Window(String title, int width, int height, boolean resizable, boolean vSync) {
        m_Title = title;
        m_Width = width;
        m_Height = height;

        m_Focused = false;
        m_Resizable = resizable;
        m_VSync = vSync;

        initialize();
    }

    /**
     * <p>Initializes GLFW and sets our Window to show up in the Center of our screen.</p>
     */
    private void initialize() {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, m_Resizable ? GLFW_TRUE : GLFW_FALSE);

        m_Handle = glfwCreateWindow(m_Width, m_Height, m_Title, MemoryUtil.NULL, MemoryUtil.NULL);
        if(m_Handle == MemoryUtil.NULL)
            throw new RuntimeException("Failed to create a Window!");

        try(MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            GLFWVidMode video_mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            if(video_mode == null)
                throw new RuntimeException("Failed to find a video mode!");

            glfwGetWindowSize(m_Handle, pWidth, pHeight);
            glfwSetWindowPos(m_Handle, (video_mode.width() - pWidth.get(0)) / 2, (video_mode.height() - pHeight.get(0)) / 2);
        }

        glfwSetWindowFocusCallback(m_Handle, (window, focused) -> m_Focused = focused);

        int swapInterval = m_VSync ? 1 : 0;
        glfwMakeContextCurrent(m_Handle);
        glfwSwapInterval(swapInterval);

        glfwSetWindowSizeCallback(m_Handle, this::resize);
        glfwSetKeyCallback(m_Handle, InputHandler::key_callback);
        glfwSetMouseButtonCallback(m_Handle, InputHandler::mouse_button_callback);

        GL.createCapabilities();
    }

    /**
     * <p>Displays our Window</p>
     */
    public void show() {
        if(m_Handle != MemoryUtil.NULL)
            glfwShowWindow(m_Handle);
    }

    /**
     * <p>Hides our Window</p>
     */
    public void hide() {
        if(m_Handle != MemoryUtil.NULL)
            glfwHideWindow(m_Handle);
    }

    /**
     * <p>Swaps the back and current buffer, so we can continue displaying visuals while updating them.</p>
     */
    public void swapBuffers() {
        if(m_Handle != MemoryUtil.NULL)
            glfwSwapBuffers(m_Handle);
    }

    /**
     * <p>Disposes our Window and its resources.</p>
     */
    public void dispose() {
        if(m_Handle != MemoryUtil.NULL) {
            glfwFreeCallbacks(m_Handle);
            glfwDestroyWindow(m_Handle);
        }
    }

    private void resize(long window, int width, int height) {
        glViewport(0, 0, width, height);

        m_Width = width;
        m_Height = height;
        m_Resized = true;
    }

    /**
     * <p>Closes our Window</p>
     */
    public void close() {
        if(m_Handle != MemoryUtil.NULL)
            glfwSetWindowShouldClose(m_Handle, true);
    }

    public void setResized(boolean resized) {
        m_Resized = resized;
    }

    public void setVSync(boolean vSync) {
        m_VSync = vSync;
    }

    /**
     * <p>Polls if our Window is closing or should close.</p>
     *
     * @return whether our Window should close.
     */
    public boolean shouldClose() {
        return glfwWindowShouldClose(m_Handle);
    }

    public long getHandle() {
        return m_Handle;
    }

    public boolean hasResized() {
        return m_Resized;
    }

    public boolean isResizable() {
        return m_Resizable;
    }

    public boolean isFocused() {
        return m_Focused;
    }

    public boolean vSyncEnabled() { return m_VSync; }

    public String getTitle() {
        return m_Title;
    }

    public int getWidth() {
        return m_Width;
    }

    public int getHeight() {
        return m_Height;
    }
}
