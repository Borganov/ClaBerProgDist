package Server;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class serverMain {

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		try {
			dbManager dbm = new dbManager();
			// dbm.printFileTable();
			// return;

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		Enumeration<NetworkInterface> allNetworkInterfaces;
		Enumeration<InetAddress> allInetAddresses;
		InetAddress serverAddress = null;

		ServerSocket mySkserver;

		// initialisation du port1
		int port1 = 45000;

		// d�finition de l'address du server
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

			System.out.println("D�marrage du serveur " + serverAddress + " sur le port " + port1);

			mySkserver = new ServerSocket(port1, 5, serverAddress);
			usersList usersList = new usersList();

			while (true) {
				Socket clientSocket = mySkserver.accept();
				Thread multiClients = new Thread(new threadMultiClients(clientSocket, usersList));

				multiClients.start();

			}

			// mySkserver.close();
			// clientSocket.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}