package lecture1;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

public class Server{
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(1234);
        Socket socket = null;
        String text = "";
        while( (socket = serverSocket.accept()) != null){
            BufferedReader indata = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while( (text = indata.readLine()) != null){
                System.out.println("Received:" + text);
            }
            socket.shutdownInput();
        }
        serverSocket.close();
    }
}
