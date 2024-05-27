package dev.atomixsoft.solar_eclipse.client.audio.files;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Midi {
    private final File m_File;

    private byte[] m_Data;
    private int m_Channels;
    private int m_FrameSize, m_SampleSize;
    private float m_SampleRate, m_FrameRate;


    public Midi(String filePath) {
        m_File = new File(filePath);
        load();
    }

    private void load() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(m_File);
            AudioFormat format = ais.getFormat();


            int bufferSize = 1024 * 8;
            byte[] buffer = new byte[bufferSize];
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            try {
                int numBytesRead;
                while ((numBytesRead = ais.read(buffer)) != -1)
                    out.write(buffer, 0, numBytesRead);

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
        } catch (IOException | UnsupportedAudioFileException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

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
