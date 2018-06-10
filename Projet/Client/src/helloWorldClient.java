
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class helloWorldClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		String userHome = System.getProperty("user.home");
		
		String fileSeparator = System.getProperty("file.separator");
		
		
		
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		int year = localDate.getYear();
		
		System.out.println(year);
	}

}
