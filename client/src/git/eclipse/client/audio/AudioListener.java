package git.eclipse.client.audio;

import org.joml.Vector3f;
import org.lwjgl.openal.AL11;

public class AudioListener {

    public void setPosition(float x, float y, float z) {
        setPosition(new Vector3f(x, y, z));
    }

    public void setPosition(Vector3f position) {
        AL11.alListener3f(AL11.AL_POSITION, position.x, position.y, position.z);
        AL11.alListener3f(AL11.AL_VELOCITY, 0, 0, 0);
    }

}
