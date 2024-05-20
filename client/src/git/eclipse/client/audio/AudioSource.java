package git.eclipse.client.audio;

import org.joml.Vector3f;
import org.lwjgl.openal.AL11;

public class AudioSource {

    private int m_SourceID;

    public AudioSource() {
        m_SourceID = AL11.alGenSources();

        setGain(1.0f);
        setPitch(1.0f);
        setPosition(new Vector3f(0));
    }

    public void play(int buffer) {
        AL11.alSourcei(m_SourceID, AL11.AL_BUFFER, buffer);
        AL11.alSourcePlay(m_SourceID);
    }

    public void pause(int buffer) {
        AL11.alSourcei(m_SourceID, AL11.AL_BUFFER, buffer);
        AL11.alSourcePause(m_SourceID);
    }

    public void dispose() {
        AL11.alDeleteSources(m_SourceID);
    }

    public void setGain(float gain) {
        AL11.alSourcef(m_SourceID, AL11.AL_GAIN, gain);
    }

    public void setPitch(float pitch) {
        AL11.alSourcef(m_SourceID, AL11.AL_PITCH, pitch);
    }

    public void setPosition(Vector3f position) {
        AL11.alSource3f(m_SourceID, AL11.AL_POSITION, position.x, position.y, position.y);
    }

}
