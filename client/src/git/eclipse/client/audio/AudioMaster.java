package git.eclipse.client.audio;

import org.lwjgl.openal.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

public class AudioMaster {

    private static AudioMaster ms_Instance = null;
    public static AudioMaster Instance() {
        if(ms_Instance == null) ms_Instance = new AudioMaster();
        return ms_Instance;
    }

    private final Map<String, Integer> m_BufferMap;

    private ALCCapabilities m_alcCapabilities;
    private ALCapabilities m_alCapabilities;

    private long m_Device, m_Context;

    private AudioMaster() {
        m_BufferMap = new HashMap<>();
    }

    private Map<String, Integer> getBufferMap() {
        return m_BufferMap;
    }

    public static void Init() {
        AudioMaster instance = Instance();

        String defaultDevice = ALC11.alcGetString(0, ALC11.ALC_DEFAULT_DEVICE_SPECIFIER);
        if(defaultDevice == null) throw new RuntimeException("Failed to find a default audio device!");

        instance.m_Device = ALC11.alcOpenDevice(defaultDevice);
        instance.m_alcCapabilities = ALC.createCapabilities(instance.m_Device);

        instance.m_Context = ALC11.alcCreateContext(instance.m_Device, (IntBuffer) null);
        ALC11.alcMakeContextCurrent(instance.m_Context);

        instance.m_alCapabilities = AL.createCapabilities(instance.m_alcCapabilities);
    }

    public static void CleanUp() {
        AudioMaster instance = Instance();

        instance.getBufferMap().values().forEach(AL11::alDeleteBuffers);
        instance.getBufferMap().clear();

        ALC11.alcMakeContextCurrent(MemoryUtil.NULL);
        ALC11.alcDestroyContext(instance.m_Context);
        ALC11.alcCloseDevice(instance.m_Device);
    }

    public static int LoadSound(String name, String fileName) {
        int buffer = AL11.alGenBuffers();

        ByteBuffer byteBuffer = null;
        try {
            Wav file = new Wav(fileName);
            System.out.println(file);

            byteBuffer = MemoryUtil.memAlloc(file.getData().length);
            byteBuffer.put(0, file.getData());

            int format = file.getSampleSize() == 8 ? AL11.AL_FORMAT_MONO8 : AL11.AL_FORMAT_MONO16;
            AL11.alBufferData(buffer, format, byteBuffer, (int) file.getSampleRate());
        } finally {
            if(byteBuffer != null)
                MemoryUtil.memFree(byteBuffer);
        }

        Instance().getBufferMap().put(name, buffer);
        return buffer;
    }

}
