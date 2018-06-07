package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;

public class threadClientConnexion implements Runnable{
	private Socket clientSocketOnClient;
	public threadClientConnexion(Socket clientSocketOnClient){

		this.clientSocketOnClient = clientSocketOnClient;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("un client est connecté");
		
	}

}
