package Client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class threadClientConnexion implements Runnable {
	private Socket clientSocketOnClient;

	public threadClientConnexion(Socket clientSocketOnClient) {

		this.clientSocketOnClient = clientSocketOnClient;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		sentFile("C:/Users/Yann/Documents/usersFile.txt");

	}

	public void sentFile(String sourcePath) {
		try {
			File f = new File(sourcePath);
			byte[] bytes = new byte[(int) f.length()];

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
