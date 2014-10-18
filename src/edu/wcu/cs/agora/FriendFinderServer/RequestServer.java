package edu.wcu.cs.agora.FriendFinderServer;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import com.sun.net.http

/**
 * TODO: Finish me
 */
public class RequestServer
{
    public static final int SOCKET_ERROR = 1;

    public static final int TIMEOUT = 5000;
    public static final int DEFAULT_PORT = 8888;

    private ServerSocket serverSocket;
    private int port;

    public RequestServer(int port) throws IOException
    {
        this.port = port;
        serverSocket = SSLServerSocketFactory.getDefault().createServerSocket(port);
    }

    public void listen() throws IOException
    {
        Socket client = serverSocket.accept();
        client.setSoTimeout(TIMEOUT);
        Scanner in = new Scanner(client.getInputStream());
        while (in.hasNextLine())
        {
            System.out.println(in.nextLine());
        }
        //Request request = Request.requestBuilder(in);
       // request.getResponse();
    }

    public static void main(String[] args)
    {
        RequestServer requestServer = null;
        try
        {
            requestServer = new RequestServer(DEFAULT_PORT);
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
            System.exit(SOCKET_ERROR);
        }

        while (true)
        {
            try
            {
                requestServer.listen();
            }
            catch (IOException e)
            {
                System.err.println("Issue while listening.");
            }
        }
    }
}
