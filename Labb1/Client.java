package lecture1;

import java.io.*;
import java.net.Socket;

public class Client{
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost",1234);
        PrintStream out = new PrintStream(socket.getOutputStream());
        BufferedReader indata = new BufferedReader(new InputStreamReader(System.in));
        String text;
        System.out.print("Enter text to send: ");
        while( (text = indata.readLine()) != null){
            out.println(text);
            System.out.print("Enter text to send: ");
        }
        socket.shutdownOutput();
        socket.close();
    }
}