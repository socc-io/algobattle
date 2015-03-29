import java.net.*;
import java.io.*;

/* 
쓰레드를 구현하는 방법은 Thread클래스를 상속받는 방법과 Runnable인터페이스를 구현하는 방법 2가지가 있다.
Thread클래스를 상속받으면 다른 클래스를 상속받을 수 없기 때문에, Runable인터페이스를 구현하는 방법이 일반적이다.
 */
public class ChatServer implements Runnable
{
	private ServerSocket	 server = null;
	private Thread			 thread = null;
	private ChatServerThread client = null;

	public ChatServer(int port)
	{
		try
		{
			System.out.println("Binding to port "+port+", pleasse wait ...");
			server  = new ServerSocket(port);
			System.out.println("Server started: "+ server);
			
			//쓰레드는 재사용이 가능하지 않다. 하나의 쓰레드에 대해 start()가 한번만 호출될 수 있다는 뜻이다.
			start();
		}
		catch(IOException ioe)
		{ System.out.println(ioe); }
	}

	public void start()
   { 
   		if (thread == null)
    	{
    		thread = new Thread(this); 
        	thread.start();
    	}
   }

	public void run()
	{
		while( thread != null )
		{
			try
			{
				System.out.println("Waiting for a client ...");
				addThread(server.accept());
			}
			catch(IOException ie)
			{ System.out.println("Acceptance Error: " + ie); }
		}
	}

	public void addThread(Socket socket)
	{
		System.out.println("client accepted: " + socket);
		client = new ChatServerThread(this, socket);

		try
		{
			client.open();
			client.start();
		}
		catch(IOException ie)
		{ System.out.println("Acceptance Error: " + ie); }
	}

   	public void stop()
   {  
   		if (thread != null)
      	{  
      		thread.stop(); 
        	thread = null;
      	}
   }

	public static void main(String args[])
   {  
   		ChatServer server = null;
    	if (args.length != 1)
        	System.out.println("Usage: java ChatServer port");
    	else
        	server = new ChatServer(Integer.parseInt(args[0]));
   }
}

