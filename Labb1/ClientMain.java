package lecture1;

import java.io.*;
import java.net.Socket;

public class ClientMain{
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost",1234);
        PrintStream out = new PrintStream(socket.getOutputStream());
        BufferedReader indata = new BufferedReader(new InputStreamReader(System.in));
        String text;
        Runnable serverListenerSession = new ClientServerListener(socket);
        Thread newSession = new Thread(serverListenerSession);
        newSession.start();
        
        System.out.print(">  ");
        while( (text = indata.readLine()) != null){
            out.println(text);
            System.out.print("> ");
        }
        socket.close();
    }
}