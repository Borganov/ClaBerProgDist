package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ListIterator;

public class threadMultiClients implements Runnable {
	private Socket clientSocketOnServer;
	private user u;
	private usersList usersList;
	private downloadableFilesTab dft = new downloadableFilesTab();
	private dbManager dbm;

	public threadMultiClients(Socket clientSocketOnServer, usersList usersList) {
		this.clientSocketOnServer = clientSocketOnServer;
		this.usersList = usersList;
		
		try {
			dbm = new dbManager();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			// envoi des choix lors de la connexion du client
			PrintWriter pout = new PrintWriter(clientSocketOnServer.getOutputStream());
			pout.println("1 pour se connecter | 2 pour s'enregistrer | autre touche pour quitter");
			pout.flush();

			// réception du choix du client
			BufferedReader buffin = new BufferedReader(new InputStreamReader(clientSocketOnServer.getInputStream()));
			String choiceConnexion = buffin.readLine();


			// identification du client
			if (choiceConnexion.equals("1")) {
				// demande identifiant
				pout = new PrintWriter(clientSocketOnServer.getOutputStream());
				pout.println("Entrer votre identifiant");
				pout.flush();
				// reception identifiant
				buffin = new BufferedReader(new InputStreamReader(clientSocketOnServer.getInputStream()));
				String id = buffin.readLine();
				/* pour ne pas qu'il rentre dans la boucle destinée au contrôle de
				l'enregistrement destiné à l'enregistrement*/
				pout.println("true");
				pout.flush();
				// demande mot de passe
				pout = new PrintWriter(clientSocketOnServer.getOutputStream());
				pout.println("Entrer votre mot de passe");
				pout.flush();
				// reception mot de passe
				buffin = new BufferedReader(new InputStreamReader(clientSocketOnServer.getInputStream()));
				String pass = buffin.readLine();

				// contrôle identifiant/mot de passe
				boolean allowconnexion = dbm.login(id, pass);


				// connexion si id et pass ok
				if (allowconnexion == true) {
					String ipUser = buffin.readLine();

					u = new user(id, ipUser);
					usersList.add(u);

					pout.println("Bienvenu sur ClaBer server" + " " + id);
					pout.flush();
					pout.println(
							"1 pour uploader un fichier | 2 pour downloader un fichier | autre touche pour quitter");
					pout.flush();
					// reception du choix
					String choiceOption = buffin.readLine();

					if (choiceOption.equals("1")) {
						pout.println("Entrer le chemin du fichier");
						pout.flush();
						String path = buffin.readLine();
						pout.println("Entrer nom du fichier");
						pout.flush();
						String fileName = buffin.readLine();
						int uid = dbm.getIdByLogin(id);
						dbm.addFile(fileName, path, uid );	

						// effaçage de l'utilisateur et deconnexion de ce dernier
						usersList.remove(id);
					} else {
						if (choiceOption.equals("2")) {

							//Liste des utilisateur connecté
							int uid;
							ArrayList<file> fileList = new ArrayList<file>();
							ListIterator<user> iu = usersList.usersList.listIterator();
							while (iu.hasNext()) {
								uid=dbm.getIdByLogin(iu.next().getId());
								ArrayList<file> currentUserFileList = dbm.getFileByUserId(uid);
								ListIterator<file> cu = currentUserFileList.listIterator();
								while(cu.hasNext()){
									fileList.add(cu.next());
								}
								//fileList.addAll(currentUserFileList);	 								
							}

							ListIterator<file> fi = fileList.listIterator();
							
							while(fi.hasNext()){
								fi.next();
								String sendLine =  fileList.get(fi.nextIndex()-1).getUid() + " - " + fileList.get(fi.nextIndex()-1).getName();
								pout.println(sendLine);
								pout.flush();
							}
							
							String loopEnd = "Terminate";
							pout.println(loopEnd);
							pout.flush();
							//System.out.println(loopEnd);
							

							// reception du choix de téléchargement
							String downloadChoice = buffin.readLine();
							System.out.println("---" + downloadChoice);
							
							// recherche de l'ip et du chemin du fichier à télécharger
							String downloadPath = null;
							String downloadUser = null;
							for (int i = 0; i < dft.downloadableFiles.length; i++) {
								if (dft.downloadableFiles[i][0].equals(downloadChoice)) {
									downloadUser = dft.downloadableFiles[i][1];
									downloadPath = dft.downloadableFiles[i][2];
								}
							}

							String downloadIp = null;

							while (iu.hasNext()) {
								if (iu.next().getId().equals(downloadUser)) {
									downloadIp = iu.next().getIp();
								}
							}

							pout.println(downloadIp);
							pout.flush();
							pout.println(downloadPath);
							pout.flush();

							// effaçage de l'utilisateur quand il se déconnecte
							usersList.remove(id);

						} else {
							System.out.println("quit");
						}
					}

					// déconnexion si pass ou id faux
				} else {
					pout.println("Erreur d'identifiant et/ou de mot de passe.");
					pout.flush();
					pout.println("Vous avez été déconnecté !");
					pout.flush();
				}

			} else {
				// enregistrement d'un nouveau client
//				if (choiceConnexion.equals("2")) {
//					boolean control = true;
//					boolean message = true;
//					String newID;
//
//					in = new FileReader("./usersFile.txt");
//					chain = checkline.split(";");
//					// boucle de contrôle redondance des ids
//					do {
//						control = true;
//						// demande de l'identifiant
//						pout = new PrintWriter(clientSocketOnServer.getOutputStream());
//						if (message == true) {
//							pout.println("Entrer votre identifiant :");
//							pout.flush();
//						} else{
//							pout.println("Identifiant déjà utilisé, entrer un autre identifiant :");
//							pout.flush();
//						}
//						// reception de l'identifiant
//						buffin = new BufferedReader(new InputStreamReader(clientSocketOnServer.getInputStream()));
//						newID = buffin.readLine();
//						// contrôle de la redondance de l'identifiant
//						for (int i = 0; i < chain.length; i++) {
//							if (chain[i].equals(newID)) {
//								control = false;
//								message = false;
//							}
//							i = i + 1;
//						}
//						// envoi des informations à la boucle client
//						if (control == true) {
//							pout.println("true");
//							pout.flush();
//						} else {
//							pout.println("false");
//							pout.flush();
//						}
//					} while (control == false);
//
//					// demande du mot de passe
//					pout.println("Enregistrer votre mot de passe");
//					pout.flush();
//					// reception du mot de passe
//					String newPass = buffin.readLine();
//					System.out.println(newPass);
//					// reception de l'ip
//					String ipUser = buffin.readLine();
//					System.out.println(ipUser);
//					// écriture de l'utilisateur
//					dbm.addUser(newID, newPass);
//					BufferedWriter bout = new BufferedWriter(new FileWriter("./usersFile.txt", true));
//					bout.write(newID + ";" + newPass + ";");
//					
//					// bout.newLine();
//					bout.close();
//					// connexion
//					pout.println("Utilisateur créé");
//					pout.flush();
//					pout.println("Veuillez vous connecter pour utiliser nos services");
//					pout.flush();
//
//					clientSocketOnServer.close();
//				} else {
//					// fermeture du serveur
//
//					clientSocketOnServer.close();
//				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
