package git.eclipse.client.scene;

import git.eclipse.client.util.ImGuiManager;
import git.eclipse.client.util.Window;

import java.util.HashMap;
import java.util.Map;

public class SceneHandler {

    private final Map<String, Scene> m_SceneMap;
    private final Window m_Window;

    private Scene m_ActiveScene;

    public SceneHandler(Window window) {
        m_SceneMap = new HashMap<>();
        m_Window = window;
        m_ActiveScene = null;
    }

    public void update(double dt) {
        if(m_ActiveScene != null) {
            if(m_Window.hasResized()) {
                m_ActiveScene.resize(m_Window.getWidth(), m_Window.getHeight());
                m_Window.setResized(false);
            }

            m_ActiveScene.update(dt);
        }
    }

    public void render(ImGuiManager uiManager) {
        if(m_ActiveScene != null) {
            m_ActiveScene.render();

            uiManager.begin();
            m_ActiveScene.imgui();
            uiManager.end();
        }
    }

    public void resize(int width, int height) {
        if(m_ActiveScene != null)
            m_ActiveScene.resize(width, height);
    }

    public void dispose() {
        if(m_ActiveScene != null) setActiveScene("none");

        for(Scene scene : m_SceneMap.values())
            scene.dispose();
    }

    public void addScene(String name, Scene scene) {
        if(scene == null) {
            System.err.println("Can't store a null scene!");
            return;
        }

        if(m_SceneMap.containsKey(name)) {
            System.err.printf("Scene [%s] already exists!%n", name);
            return;
        }

        m_SceneMap.put(name, scene);
    }

    public void removeScene(String name) {
        if(!m_SceneMap.containsKey(name)) {
            System.err.printf("Scene [%s] wasn't found!%n", name);
            return;
        }

        m_SceneMap.remove(name);
    }

    public Scene getActiveScene() {
        return m_ActiveScene;
    }

    public void setActiveScene(String name) {
        Scene scene = null;
        if(!name.isEmpty() && !name.equalsIgnoreCase("none")) {
            if(!m_SceneMap.containsKey(name)) {
                System.err.printf("Scene [%s] wasn't found!%n", name);
                return;
            }

            scene = m_SceneMap.get(name);
        }

        if(m_ActiveScene != null)
            m_ActiveScene.hide();

        if(scene != null)
            scene.show();

        m_ActiveScene = scene;
    }

    public abstract static class Scene {

        public abstract void show();
        public abstract void hide();
        public abstract void dispose();

        public abstract void update(double dt);
        public abstract void render();
        public abstract void imgui();

        public abstract void resize(int width, int height);

    }


}
