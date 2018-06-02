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

		// le client cherche la même address ip que le server
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
			clSocket = new Socket("192.168.43.45", 45000);
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
			System.out.println("IP envoyé "+ipString);

			// lecture du message statut connexion connecté/pas connecté
			String connectionStatus;
			connectionStatus = buffin.readLine();
			System.out.println(connectionStatus);
			// lecture du choix de l'utilisateur ou déconnecté
			String messageChoice = buffin.readLine();
			System.out.println(messageChoice);
			
			//Ouverture du thread d'attente de transfert client client
			Thread clientConnexionManager = new Thread(new threadClientConnexion());
			clientConnexionManager.start();
			System.out.println("Thread Client lancé");
			

			if (messageChoice
					.equals("1 pour uploader un fichier | 2 pour downloader un fichier | autre touche pour quitter")) {
				String choice = sc.nextLine();
				pout.println(choice);
				pout.flush();

				if (choice.equals("1")) {
					String messagePath = buffin.readLine();
					System.out.println(messagePath);
					String path = sc.nextLine();
					pout.println(path);
					pout.flush();
					String messageFileName = buffin.readLine();
					System.out.println(messageFileName);
					String fileName = sc.nextLine();
					pout.println(fileName);
					pout.flush();
					
				} else {
					if (choice.equals("2")) {

//						int x = buffin.read();
//						System.out.println(x);
						// reception des fichiers téléchargeable

						System.out.println("Voici la liste des fichiers disponibles :");
						System.out.println();
						
						String messageFileDL="Continue";
						String[] FileDL;
						
						boolean out = false;
						do{
							messageFileDL = buffin.readLine();
							if(messageFileDL.equals("Terminate")){
								out = true;
							}else{
								System.out.println(messageFileDL);
							}
							
						}while(out == false );


						System.out.println();
						System.out.println("Entrer le numéro du fichier que vous voulez télécharger :");
						String downloadFileNumber = sc.nextLine();
						pout.println(downloadFileNumber);
						pout.flush();

						// reception de l'ip et du chemin pour le téléchargement
						String downloadIp = buffin.readLine();
						String downloadPath = buffin.readLine();

						// ouverture de la connexion
						System.out.println(downloadIp);
						Socket connexionToClient = new Socket(downloadIp, 45002);

					}
				}
			}

			clSocket.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
