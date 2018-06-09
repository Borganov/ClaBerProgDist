package Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class threadClient implements Runnable {
	private Socket clientSocketOnClient;

	public threadClient(Socket clientSocketOnClient) {
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

						String messageFileDL = "Continue";
						String[] FileDL;

						boolean out = false;
						do {
							messageFileDL = buffin.readLine();
							if (messageFileDL.equals("Terminate")) {
								out = true;
							} else {
								System.out.println(messageFileDL);
							}

						} while (out == false);

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
						Socket connexionToClient = new Socket(downloadIp, 45002);
						PrintWriter cpout = new PrintWriter(connexionToClient.getOutputStream());
						pout.println(downloadPath);
						pout.flush();
						
						int bytes;
						String filePath = "C:/Users/Yann/Desktop/PacmanSion2026.pptx";
						File f = new File(filePath);


							InputStream input = connexionToClient.getInputStream();

							OutputStream output = new FileOutputStream(f);
							
							BufferedReader cbuffin = new BufferedReader(new InputStreamReader(connexionToClient.getInputStream()));
							int fileLength = cbuffin.read();
							int percentage = 0;
							int percentageBytes = 0;

							byte[] buffer = new byte[1024];
							while ((bytes = input.read(buffer)) != -1) {
								output.write(buffer, 0, bytes);
								percentage = percentageBytes/fileLength;
								System.out.print("|");
								percentageBytes=percentageBytes+1024;
							}
							System.out.print(" 100%");
							output.close();
							System.out.println();
							System.out.println("T�l�chargement termin� !");
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
