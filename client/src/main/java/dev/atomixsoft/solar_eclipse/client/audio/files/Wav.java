package dev.atomixsoft.solar_eclipse.client.audio.files;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;


/**
 * Reads a WAV file and stores the information, so we can use it with OpenAL.
 */
public class Wav {
    private final File m_File;

    private byte[] m_Data;
    private int m_Channels;
    private int m_FrameSize, m_SampleSize;
    private float m_SampleRate, m_FrameRate;


    public Wav(String fileName) {
        m_File = new File(fileName);

        if(m_File.exists()) load();
        else throw new RuntimeException("Failed to load file: " + fileName);
    }

    private void load() {
        int totalFramesRead = 0;
        try {
            // Loads up an AudioInputStream and AudioFormat for file processing
            AudioInputStream ais = AudioSystem.getAudioInputStream(m_File);
            AudioFormat format = ais.getFormat();

            int bytesPerFrame = format.getFrameSize();
            if(bytesPerFrame == AudioSystem.NOT_SPECIFIED) bytesPerFrame = 1;

            // Set the buffer size (fixed) ty grubermeister!!!
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            try {
                int numBytesRead;
                while ((numBytesRead = ais.read(buffer)) != -1) {
                    out.write(buffer, 0, numBytesRead);
                    //numFramesRead = numBytesRead / bytesPerFrame;
                    totalFramesRead += numBytesRead / bytesPerFrame;
                }

                m_Data = out.toByteArray();
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                System.exit(-1);
            }

            m_Channels   = format.getChannels();
            m_FrameSize  = format.getFrameSize();
            m_SampleSize = format.getSampleSizeInBits();

            m_SampleRate = format.getSampleRate();
            m_FrameRate  = format.getFrameRate();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    // Getters

    public byte[] getData() {
        return m_Data;
    }

    public int getChannels() {
        return m_Channels;
    }

    public int getFrameSize() {
        return m_FrameSize;
    }

    public int getSampleSize() {
        return m_SampleSize;
    }

    public float getSampleRate() {
        return m_SampleRate;
    }

    public float getFrameRate() {
        return m_FrameRate;
    }

    @Override
    public String toString() {
        return String.format("""
				FilePath: %s
				Channels:   %d
				FrameSize:  %d
				SampleSize: %d
				
				SampleRate: %f
				FrameRate: %f
				""", m_File.getPath(), getChannels(), getFrameSize(), getSampleSize(), getSampleRate(), getFrameRate());
    }
}
