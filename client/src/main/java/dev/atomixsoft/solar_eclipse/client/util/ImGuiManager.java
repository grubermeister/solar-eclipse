package dev.atomixsoft.solar_eclipse.client.util;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

import dev.atomixsoft.solar_eclipse.client.util.logging.Logger;

public class ImGuiManager {

    private final ImGuiImplGl3 m_imguiImplGL3;
    private final ImGuiImplGlfw m_imguiImplGLFW;
    private final Logger logger;

    public ImGuiManager(Logger logger) {
        this.logger = logger;
        m_imguiImplGL3 = new ImGuiImplGl3();
        m_imguiImplGLFW = new ImGuiImplGlfw();
    }

    public void init(long window, String glslVersion) {
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();

        io.setIniFilename(null);

        m_imguiImplGL3.init(glslVersion);
        m_imguiImplGLFW.init(window, true);
    }

    public void setup() {
        m_imguiImplGLFW.newFrame();
        ImGui.newFrame();
    }

    public void render() {
        ImGui.render();
        m_imguiImplGL3.renderDrawData(ImGui.getDrawData());

        if(ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long backupPtr = glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            glfwMakeContextCurrent(backupPtr);
        }
    }

    public void dispose() {
        logger.trace("Disposing ImGuiManager resources...");
        m_imguiImplGL3.dispose();
        m_imguiImplGLFW.dispose();

        logger.trace("Destroying ImGui context...");
        ImGui.destroyContext();
    }

}
