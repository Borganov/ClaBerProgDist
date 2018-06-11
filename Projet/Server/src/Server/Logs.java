package Server;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.Date;

public class Logs {

	static Logger log ;
	
	public Logs(String designation, int level){	
		String path = creatDirectoryByMonth();
		
		try {
			log = Logger.getLogger(designation);
			FileHandler fileH = new FileHandler(path, true);
			
			log.addHandler(fileH);
			
			switch(level){
				case 1: log.setLevel(Level.INFO);
				break;
				
				case 2: log.setLevel(Level.WARNING);
				break;
				
				case 3: log.setLevel(Level.SEVERE);
				break;
			}
			
			fileH.setFormatter(new FormaLog());

			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			//ConsoleWritter.WriteLine(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//ConsoleWritter.WriteLine(e.getMessage());
		}
		
	}
	
	public static void LogsWriter(Level level, Exception message){
		message.printStackTrace();
		log.log(level, message.getMessage());
		
	}

	private String creatDirectoryByMonth(){
		String path;
		path = System.getProperty("user.home") + System.getProperty("file.separator")+"ClaberSoftwar";
		
		tryCreatingFolder(path);
		
		path = path + System.getProperty("file.separator");
		
		Date date = new Date();	
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		int year = localDate.getYear();
		
		path = path + year + "_" + month + "_" + "Logs.log"; // exemple : 2018_6_Logs
		// On essaye de créer le dossier si pas déjà existant
		tryCreatingFolder(path);
		
		//On retourne le path
		return path;
	}
	
	private class FormaLog extends Formatter{

		@Override
		public String format(LogRecord r) {
			// TODO Auto-generated method stub
			StringBuffer sb = new StringBuffer();
			Date date = new Date(r.getMillis());
			sb.append(date.toString());
			sb.append(";");
			
			sb.append(r.getSourceClassName());
			sb.append(";");
			
			sb.append(r.getLevel().getName());
			sb.append(";");
			
			sb.append(formatMessage(r) + "\n");
			
		
			return sb.toString();
		}
		
	}
	
	private void tryCreatingFolder(String path){
		File d = new File(path);
		
		//Control si le dossier est déjà créer si non on le crée
		if(!d.exists()){
			d.mkdir();
		}
	}
	
	
	
}
