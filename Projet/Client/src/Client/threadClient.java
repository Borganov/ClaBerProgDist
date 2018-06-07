package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class threadClient implements Runnable{
	private Socket clientSocketOnClient;

	public threadClient(Socket clientSocketOnClient){

		this.clientSocketOnClient = clientSocketOnClient;

	}
	
	@Override
	public void run() {
	// TODO Auto-generated method stub
		try {
			BufferedReader buffin = new BufferedReader(new InputStreamReader(clientSocketOnClient.getInputStream()));
			PrintWriter pout = new PrintWriter(clientSocketOnClient.getOutputStream());
			Scanner sc = new Scanner(System.in);
			// lecture du message statut connexion connect�/pas connect�
			String connectionStatus;
			connectionStatus = buffin.readLine();
			System.out.println(connectionStatus);
			// lecture du choix de l'utilisateur ou d�connect�
			String messageChoice = buffin.readLine();
			System.out.println(messageChoice);

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

						// reception des fichiers t�l�chargeable
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
						System.out.println("Entrer le num�ro du fichier que vous voulez t�l�charger :");
						String downloadFileNumber = sc.nextLine();
						pout.println(downloadFileNumber);
						pout.flush();

						// reception de l'ip et du chemin pour le t�l�chargement
						String downloadIpSlashed = buffin.readLine();
						String downloadPath = buffin.readLine();
						
						String downloadIp = downloadIpSlashed.substring(1);

						// ouverture de la connexion
						System.out.println(downloadIp);
						Socket connexionToClient = new Socket("192.168.1.106", 45002);

					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		


	}

}
