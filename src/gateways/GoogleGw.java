package gateways;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class GoogleGw implements ILoginGw{
	
	String serverIP;
	int serverPort;
	private static GoogleGw instance = new GoogleGw();
	
	public static GoogleGw getInstance() {
		return instance;
	}
	
	public GoogleGw() {
		serverIP = "127.0.0.1";
		serverPort = 8002;
	}
	
	public String autenticar(String email, String password) {
		String s = "false";
		try (Socket tcpSocket = new Socket(serverIP, serverPort);
		DataInputStream in = new DataInputStream(tcpSocket.getInputStream());
		DataOutputStream out = new DataOutputStream(tcpSocket.getOutputStream())){
			String message = "val;"+email+";"+password;
			out.writeUTF(message);
			System.out.println(" - TCPSocketClient: Sent data to '" + tcpSocket.getInetAddress().getHostAddress() + ":" + tcpSocket.getPort() + "' -> '" + message + "'");
			
			String data = in.readUTF();			
			System.out.println(" - TCPSocketClient: Received data from '" + tcpSocket.getInetAddress().getHostAddress() + ":" + tcpSocket.getPort() + "' -> '" + data + "'");
			s=data;
		} catch (UnknownHostException e) {
			System.err.println("# TCPSocketClient: Socket error: " + e.getMessage());
		} catch (EOFException e) {
			System.err.println("# TCPSocketClient: EOF error: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("# TCPSocketClient: IO error: " + e.getMessage());
		}
		return s;
	}
	
}