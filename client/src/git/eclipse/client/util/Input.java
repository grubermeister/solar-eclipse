package git.eclipse.client.util;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    private static Input ms_Instance = null;
    public static Input Instance() {
        if(ms_Instance == null) ms_Instance = new Input();
        return ms_Instance;
    }

    private final Map<Integer, Boolean> m_Keys, m_JustPressed;
    private final Map<Integer, Boolean> m_MButtons, m_MBJustPressed;

    private Key m_LastKey;

    private Input() {
        m_Keys = new HashMap<>();
        m_JustPressed = new HashMap<>();

        m_MButtons = new HashMap<>();
        m_MBJustPressed = new HashMap<>();

        init();
    }

    private void init() {
        for(var k = GLFW_KEY_SPACE; k < GLFW_KEY_LAST; ++k) {
            m_Keys.putIfAbsent(k, false);
            m_JustPressed.putIfAbsent(k, false);
        }

        for(var mb = GLFW_MOUSE_BUTTON_1; mb < GLFW_MOUSE_BUTTON_LAST; ++mb) {
            m_MButtons.putIfAbsent(mb, false);
            m_MBJustPressed.putIfAbsent(mb, false);
        }
    }

    public void process() {
        for(var i = GLFW_KEY_SPACE; i < GLFW_KEY_LAST; ++i) {
            if(m_Keys.get(i) && !m_JustPressed.get(i)) {
                m_JustPressed.put(i, true);
                m_Keys.put(i, false);
            } else if(!m_Keys.get(i) && m_JustPressed.get(i)) {
                m_JustPressed.put(i, false);
            }
        }

        for(var i = GLFW_MOUSE_BUTTON_1; i < GLFW_MOUSE_BUTTON_LAST; ++i) {
            if(m_MButtons.get(i) && !m_MBJustPressed.get(i)) {
                m_MBJustPressed.put(i, true);
                m_MButtons.put(i, false);
            } else if(!m_MButtons.get(i) && m_MBJustPressed.get(i)) {
                m_MBJustPressed.put(i, false);
            }
        }
    }

    public boolean isKeyDown(int key) {
        return m_Keys.get(key);
    }

    public boolean keyJustPressed(int key) {
        return m_JustPressed.get(key);
    }

    public boolean isMouseDown(int mouseButton) {
        return m_MButtons.get(mouseButton);
    }

    public boolean mouseJustPressed(int mouseButton) {
        return m_MBJustPressed.get(mouseButton);
    }

    public static void key_callback(long window, int key, int scancode, int action, int mods) {
        Instance().m_Keys.put(key, action != GLFW_RELEASE);
    }

    private static class Key {
        boolean down = false, justPressed = false;
    }

    private static class MButton {
        boolean down = false, justPressed = false;
    }

}
