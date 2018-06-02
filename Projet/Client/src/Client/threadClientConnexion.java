package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;

public class threadClientConnexion implements Runnable{
	
	Enumeration<NetworkInterface> allNetworkInterfaces;
	Enumeration<InetAddress> allInetAddresses;
	InetAddress serverAddress = null;
	
	Socket socketClientClient;
	ServerSocket socketServerClientClient;
	private String path;
	
	public threadClientConnexion(){

		
		try {
			
			allNetworkInterfaces = NetworkInterface.getNetworkInterfaces();

			while (allNetworkInterfaces.hasMoreElements()) {
				NetworkInterface ni = allNetworkInterfaces.nextElement();

				allInetAddresses = ni.getInetAddresses();

				while (allInetAddresses.hasMoreElements()) {
					InetAddress ia = allInetAddresses.nextElement();

					if (!ia.isLoopbackAddress()) {
						if (!ia.isLinkLocalAddress()) {
							serverAddress = ia;
						}
					}
				}
			}
			

	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		try {
			socketServerClientClient = new ServerSocket(45002, 5, serverAddress);

			while(true){
				socketClientClient = socketServerClientClient.accept();
				System.out.println("un client se connecte au thread ClientConnexion");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		
		
	}

}
