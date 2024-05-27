package git.eclipse.client.util;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

/**
 * Convenient class for handling the initialization and special updates of imgui.
 */
public class ImGuiManager {

    private final ImGuiImplGl3 m_imguiGL3;
    private final ImGuiImplGlfw m_imguiGLFW;

    public ImGuiManager() {
        m_imguiGL3 = new ImGuiImplGl3();
        m_imguiGLFW = new ImGuiImplGlfw();
    }

    public void init(long window, String glslVersion) {
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();

        io.setIniFilename(null);
        //io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable); // Enables viewports, duh

        m_imguiGL3.init(glslVersion);
        m_imguiGLFW.init(window, true);
    }

    public void begin() {
        m_imguiGLFW.newFrame();
        ImGui.newFrame();
    }

    public void end() {
        ImGui.render();
        m_imguiGL3.renderDrawData(ImGui.getDrawData());

        // Necessary for Viewports to function properly
        if(ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long backupPtr = glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            glfwMakeContextCurrent(backupPtr);
        }
    }

    public void dispose() {
        m_imguiGL3.dispose();
        m_imguiGLFW.dispose();
        ImGui.destroyContext();
    }

}
