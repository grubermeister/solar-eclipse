package git.eclipse.client.audio;

import org.joml.Math;
import org.joml.Vector3f;
import org.lwjgl.openal.AL11;

public class AudioSource {

    private final int m_SourceID;

    public AudioSource() {
        m_SourceID = AL11.alGenSources();

        setGain(1.0f);
        setPitch(1.0f);
        setPosition(new Vector3f(0));
    }

    public void start(int buffer) {
        stop();
        AL11.alSourcei(m_SourceID, AL11.AL_BUFFER, buffer);
        play();
    }

    public void play() {
        AL11.alSourcePlay(m_SourceID);
    }

    public void pause() {
        AL11.alSourcePause(m_SourceID);
    }

    public void stop() {
        AL11.alSourceStop(m_SourceID);
    }

    public void dispose() {
        stop();
        AL11.alDeleteSources(m_SourceID);
    }

    public boolean isPlaying() {
        return AL11.alGetSourcei(m_SourceID, AL11.AL_SOURCE_STATE) == AL11.AL_PLAYING;
    }

    public void setVolume(int volume) {
        if(volume <= 0) {
            setGain(volume);
            return;
        }

        float clampedVol = Math.clamp(1, 100, volume);
        setGain(clampedVol / 100.0f);
    }

    public void setGain(float gain) {
        AL11.alSourcef(m_SourceID, AL11.AL_GAIN, gain);
    }

    public void setPitch(float pitch) {
        AL11.alSourcef(m_SourceID, AL11.AL_PITCH, pitch);
    }

    public void setLooping(boolean looping) {
        AL11.alSourcei(m_SourceID, AL11.AL_LOOPING, looping ? AL11.AL_TRUE : AL11.AL_FALSE);
    }

    public void setVelocity(Vector3f velocity) {
        AL11.alSource3f(m_SourceID, AL11.AL_VELOCITY, velocity.x, velocity.y,velocity.z);
    }

    public void setPosition(Vector3f position) {
        AL11.alSource3f(m_SourceID, AL11.AL_POSITION, position.x, position.y, position.y);
    }

}
