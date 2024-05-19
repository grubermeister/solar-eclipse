package git.eclipse.server;

import git.eclipse.core.network.ServerData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EclipseServer implements Runnable {

    private final ServerData m_Data;
    private final Thread m_Thread;

    private volatile boolean m_Running;

    public EclipseServer(ServerData data) {
        m_Data = data;
        m_Running = false;

        m_Thread = new Thread(this, "Server_Thread");
    }

    public synchronized void start() {
        if(m_Running) return;

        m_Running = true;
        m_Thread.start();
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while(m_Running) {
            try {
                String line = reader.readLine();
                if(line == null) continue; // Don't want to crash, but also want nothing to happen

                boolean isCmd = line.charAt(0) == '/';
                if(isCmd) { // If we pass a command, process it
                    handleCommand(line.substring(1));
                } else { // Otherwise treat as /say ...
                    // For now, we simply echo it.
                    System.out.println(line);
                }

            } catch (IOException e) {
                System.err.println(e.getMessage());
                m_Running = false;
            }
        }

        try {
            reader.close();
            m_Thread.join(1);
        } catch (InterruptedException | IOException e) {
            System.out.println("Failed to join thread: \n" + e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * Handles the command and arguments passed through the terminal.
     *
     * @param input command and possible arguments to process.
     */
    private void handleCommand(String input) {
        String cmd = input;
        List<String> arguments = new ArrayList<>();
        boolean hasArgs = input.indexOf(' ') != -1;

        // If it has arguments we should store them
        if(hasArgs) {
            String args = input.substring(input.indexOf(' ') + 1);
            cmd = input.substring(0, input.indexOf(' '));

            while(!args.isEmpty()) {
                if(args.indexOf(' ') != -1) { // If we find a space, there are more arguments
                    String curr = args.substring(0, args.indexOf(' '));
                    arguments.add(curr);
                    args = args.substring(args.indexOf(' ') + 1);
                } else { // Otherwise we're on our last one
                    arguments.add(args);
                    args = "";
                }
            }
        }

        // Command Switch Statement
        switch (cmd) {
            case "Exit", "exit": {
                m_Running = false;
                break;
            }

            case "Test", "test": {
                System.out.println("Testing!!!");
                break;
            }
        }

        // Empty our list just to make sure memory is free
        if(!arguments.isEmpty())
            arguments.clear();
    }
}
