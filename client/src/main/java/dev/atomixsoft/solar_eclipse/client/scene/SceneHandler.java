package dev.atomixsoft.solar_eclipse.client.scene;

import java.util.HashMap;
import java.util.Map;

import dev.atomixsoft.solar_eclipse.client.util.ImGuiManager;
import dev.atomixsoft.solar_eclipse.client.util.Window;


public class SceneHandler {
    private final Map<String, Scene> m_SceneMap;
    private final Window m_Window;

    private Scene m_ActiveScene;


    public abstract static class Scene {

        public abstract void show() throws Exception;
        public abstract void hide();
        public abstract void dispose();

        public abstract void update(double dt);
        public abstract void render() throws Exception;
        public abstract void imgui();

        public abstract void resize(int width, int height);

    }


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

    public void render(ImGuiManager guiManager) throws Exception {
        if(m_ActiveScene != null) {
            try {
                m_ActiveScene.render();
            } catch (Exception e) {
                throw e;
            }

            guiManager.setup();
            m_ActiveScene.imgui();
            guiManager.render();
        }
    }

    public void resize(int width, int height) {
        if(m_ActiveScene != null)
            m_ActiveScene.resize(width, height);
    }

    public void dispose() throws Exception {
        if(m_ActiveScene != null) {
            try {
                setActiveScene("none");
            } catch (Exception e) {
                throw e;
            }
        }

        for(Scene scene : m_SceneMap.values())
            scene.dispose();
    }

    public void addScene(String name, Scene scene) throws Exception {
        if(scene == null)
            throw new Exception("Can't store a null scene!");

        if(m_SceneMap.containsKey(name))
            throw new Exception("Scene " + name + " already exists!");

        m_SceneMap.put(name, scene);
    }

    public void removeScene(String name) throws Exception {
        if(!m_SceneMap.containsKey(name))
            throw new Exception("Scene " + name + "wasn't found!");

        m_SceneMap.remove(name);
    }

    public Scene getActiveScene() {
        return m_ActiveScene;
    }

    public void setActiveScene(String name) throws Exception{
        Scene scene = null;
        if(!name.isEmpty() && !name.equalsIgnoreCase("none")) {
            if(!m_SceneMap.containsKey(name))
                throw new Exception("Scene " + name + " wasn't found!");

            scene = m_SceneMap.get(name);
        }

        if(m_ActiveScene != null)
            m_ActiveScene.hide();

        if(scene != null) {
            try {
                scene.show();
            } catch (Exception e) {
                throw e;
            }
        }

        m_ActiveScene = scene;
    }
}
