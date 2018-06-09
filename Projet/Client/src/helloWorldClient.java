
public class helloWorldClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Bonjour je suis le client ");
		
		System.out.println("Client branche develop");
		
		String userHome = System.getProperty("user.home");
		
		String fileSeparator = System.getProperty("file.separator");
		
		System.out.println(userHome);
		System.out.println(fileSeparator);
	}

}
