package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Scanner;

public class clientMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Enumeration<NetworkInterface> allNetworkInterfaces;
		Enumeration<InetAddress> allInetAddresses;
		InetAddress ip = null;

		Socket clSocket;
		ServerSocket myClserver;
		
		String ipServer;

		// le client cherche la m�me address ip que le server
		try {
			allNetworkInterfaces = NetworkInterface.getNetworkInterfaces();

			while (allNetworkInterfaces.hasMoreElements()) {
				NetworkInterface ni = allNetworkInterfaces.nextElement();

				allInetAddresses = ni.getInetAddresses();

				while (allInetAddresses.hasMoreElements()) {
					InetAddress ia = allInetAddresses.nextElement();

					if (!ia.isLoopbackAddress()) {
						if (!ia.isLinkLocalAddress()) {
							ip = ia;
						}
					}
				}
			}
			System.out.println("Veuillez entrer l'ip du serveur");
			Scanner ipReader = new Scanner(System.in);
			ipServer = ipReader.nextLine();

			
			clSocket = new Socket(ipServer, 45000);
			// reception du message de connexion
			BufferedReader buffin = new BufferedReader(new InputStreamReader(clSocket.getInputStream()));
			String messageConnexion;
			messageConnexion = buffin.readLine();
			System.out.println(messageConnexion);
			System.out.println("Votre choix :");
			// envoi du choix de connexion
			PrintWriter pout = new PrintWriter(clSocket.getOutputStream());
			Scanner sc = new Scanner(System.in);
			String choiceConnexion = sc.nextLine();
			pout.println(choiceConnexion);
			pout.flush();
			// identification ou enregistrement de l'id
			boolean controleID = true;
			do {
				String messageId = buffin.readLine();
				System.out.println(messageId);
				String choiceId = sc.nextLine();
				pout.println(choiceId);
				pout.flush();
				String bool = buffin.readLine();
				if (bool.equals("true")) {
					controleID = true;
				} else {
					controleID = false;
				}
			} while (controleID == false);
			// identification ou enregistrement du mot de passe
			String messagePass = buffin.readLine();
			System.out.println(messagePass);
			String pass = sc.nextLine();
			pout.println(pass);
			pout.flush();
			// envoie de l'ip au serveur en String
			String ipString = ip.toString();
			pout.println(ipString);
			pout.flush();

			Thread threadClient = new Thread(new threadClient(clSocket));
			threadClient.start();
			// Ouverture du thread d'attente de transfert client client
			myClserver = new ServerSocket(45002, 5, ip);

			while (true) {
				Socket clientclientSocket = myClserver.accept();
				Thread clientclient = new Thread(new threadClientConnexion(clientclientSocket));
				clientclient.start();
			}

			

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
