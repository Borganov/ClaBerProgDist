package Client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class threadClientConnexion implements Runnable {
	private Socket clientSocketOnClient;

	public threadClientConnexion(Socket clientSocketOnClient) {

		this.clientSocketOnClient = clientSocketOnClient;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		sentFile("C:/Users/Yann/Documents/HES/PacmanSion2026.pptx");

	}

	public void sentFile(String sourcePath) {
		try {
			int fileLength;
			
			File f = new File(sourcePath);
			
			fileLength=(int) f.length();
			
			byte[] bytes = new byte[fileLength];
			PrintWriter pout = new PrintWriter(clientSocketOnClient.getOutputStream());
			pout.println(fileLength);
			pout.flush();

			FileInputStream fis = new FileInputStream(f);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(bytes, 0, bytes.length);

			OutputStream os = clientSocketOnClient.getOutputStream();

			os.write(bytes, 0, bytes.length);

			os.flush();
			bis.close();
			fis.close();
			os.close();
		} catch (Exception e) {

		}
	}
}
