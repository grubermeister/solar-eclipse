package dev.atomixsoft.solar_eclipse.client.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import dev.atomixsoft.solar_eclipse.client.graphics.Shader;
import dev.atomixsoft.solar_eclipse.client.graphics.Texture;


public class AssetLoader {
    // =========== INTERNAL DIRECTORIES ===========
    private static final String TEXTURES_DIR = "client/assets/graphics/";
    private static final String SHADERS_DIR = "client/assets/shaders/";

    // private static final String SFX_DIR = "audio/sfx/";
    // private static final String BGM_DIR = "audio/bgm/";
    // =========== INTERNAL DIRECTORIES ===========

    // =========== EXTERNAL DIRECTORIES ===========
    // private static final String LOG_DIR = "logs/";
    // private static final String CACHED_MAPS_DIR = "maps/";
    // =========== EXTERNAL DIRECTORIES ===========

    private final Map<String, Texture> m_TextureMap;
    private final Map<String, Shader> m_ShaderMap;

    private static AssetLoader m_Instance = null;


    public static AssetLoader Instance() {
        if(m_Instance == null) {
            m_Instance = new AssetLoader();
            return m_Instance;
        }

        return m_Instance;
    }

    public static void Dispose() {
        Instance().dispose();
    }

    public static void AddTexture(String name, String path) throws Exception {
        try {
            Instance().addTexture(name, path);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void AddShader(String name, String shaderFile) throws Exception {
        try {
            Instance().addShader(name, shaderFile);
        } catch (Exception e) {
            throw e;
        }
    }

    public static Texture GetTexture(String name) throws Exception {
        try {
            return Instance().getTexture(name, false);
        } catch (Exception e) {
            throw e;
        }
    }

    public static Texture GetTextureFromPath(String filePath) throws Exception {
        try {
            return Instance().getTexture(filePath, true);
        } catch (Exception e) {
            throw e;
        }
    }

    public static Shader GetShader(String name) throws Exception {
        try {
            return Instance().getShader(name);
        } catch (Exception e) {
            throw e;
        }
    }

    private AssetLoader() {
        m_TextureMap = new HashMap<>();
        m_ShaderMap = new HashMap<>();
    }

    private void dispose() {
        for(Texture texture : m_TextureMap.values())
            texture.dispose();

        for(Shader shader : m_ShaderMap.values()) {
            shader.unbind();
            shader.dispose();
        }

        m_TextureMap.clear();
        m_ShaderMap.clear();

        m_Instance = null;
    }

    private void addTexture(String name, String path) throws Exception {
        String fullPath = TEXTURES_DIR + path;
        for(Texture texture : m_TextureMap.values()) {
            String texturePath = texture.getFilepath();
            if(texturePath.equalsIgnoreCase(fullPath)) {
                throw new Exception(fullPath + " already exists in the TextureMap!");
            }
        }

        Texture texture = new Texture(fullPath);
        m_TextureMap.put(name, texture);
    }

    private void addShader(String name, String shaderFile) throws Exception {
        if(m_ShaderMap.containsKey(name)) {
            throw new Exception("Already contains shader: " + name);
        }

        String fullPath = SHADERS_DIR + shaderFile;
        List<Shader.ShaderModuleData> dataList = new ArrayList<>();

        dataList.add(new Shader.ShaderModuleData(fullPath + ".vert", GL_VERTEX_SHADER));
        dataList.add(new Shader.ShaderModuleData(fullPath + ".frag", GL_FRAGMENT_SHADER));

        Shader shader = new Shader(dataList);
        m_ShaderMap.put(name, shader);
    }

    private Texture getTexture(String name, boolean filePath) throws Exception {
        // Checks if the string give is a filepath or a Map Key
        if(!filePath) {
            if(!m_TextureMap.containsKey(name)) {
                throw new Exception("Couldn't find Texture: " + name);
            }

            return m_TextureMap.get(name);
        
        } else {
            for(Texture texture : m_TextureMap.values()) {
                if(texture.getFilepath().equalsIgnoreCase(name))
                    return texture;
            }

            throw new Exception("Failed to find Texture from path: " + name);
        }
    }

    private Shader getShader(String name) throws Exception {
        if(!m_ShaderMap.containsKey(name)) {
            throw new Exception("Couldn't find Shader: " + name);
        }

        return m_ShaderMap.get(name);
    }
}
