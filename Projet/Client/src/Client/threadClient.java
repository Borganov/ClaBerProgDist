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
			// lecture du message statut connexion connecté/pas connecté
			String connectionStatus;
			connectionStatus = buffin.readLine();
			System.out.println(connectionStatus);
			// lecture du choix de l'utilisateur ou déconnecté
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

						// reception des fichiers téléchargeable
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
						System.out.println("Entrer le numéro du fichier que vous voulez télécharger :");
						String downloadFileNumber = sc.nextLine();
						pout.println(downloadFileNumber);
						pout.flush();

						// reception de l'ip et du chemin pour le téléchargement
						String downloadIpSlashed = buffin.readLine();
						String downloadPath = buffin.readLine();
						String downloadFileName = buffin.readLine();

						String downloadIp = downloadIpSlashed.substring(1);

						// ouverture de la connexion
						Socket connexionToClient = new Socket(downloadIp, 45002);
						PrintWriter cpout = new PrintWriter(connexionToClient.getOutputStream());
						cpout.println(downloadPath);
						cpout.flush();
						
						//récupération de l'extention du fichier
						int indexExtentionFichierDebut = downloadPath.lastIndexOf(".");
						int indexExtentionFichierFin = downloadPath.length();
						String extention = downloadPath.substring(indexExtentionFichierDebut, indexExtentionFichierFin);
						
						System.out.println(extention);

						int bytes;
						
						String filePath = System.getProperty("user.home") + System.getProperty("file.separator") +"Downloads"+System.getProperty("file.separator") + downloadFileName + extention ;

						File f = new File(filePath);

						InputStream input = connexionToClient.getInputStream();

						OutputStream output = new FileOutputStream(f);

						BufferedReader cbuffin = new BufferedReader(
								new InputStreamReader(connexionToClient.getInputStream()));
						String fileLengthString = cbuffin.readLine();
						int fileLength = Integer.parseInt(fileLengthString);

						int percentageBytes = 0;
						int ProgressBarStep = fileLength / 20;
						int nextStep = 0;
						int ProgressBarCurrentState = 0;
						
						System.out.println("Début du téléchargement ! Veuillez patienter");

						byte[] buffer = new byte[1024];
						while ((bytes = input.read(buffer)) != -1) {
							output.write(buffer, 0, bytes);

							ProgressBarCurrentState += 1024;
							if (ProgressBarCurrentState > nextStep) {
								System.out.print("|");
								nextStep = nextStep + ProgressBarStep;
							}

							percentageBytes = percentageBytes + 1024;
						}
						System.out.print(" 100%");
						output.close();
						System.out.println();
						System.out.println("Fichier disponaible dans " + filePath);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
