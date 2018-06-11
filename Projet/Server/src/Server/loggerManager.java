package Server;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class loggerManager {
	
	Logger logger ;
	FileHandler fh;
	String loggerName;
	
	public loggerManager(String loggerName){
		this.loggerName = loggerName;
		logger = Logger.getLogger(loggerName);
		
	}
	
	public void setLoggs(int level, String message){
		//Création du dossier si besoins
		String path;
		path = "./ClaberSoftwar";
		
		tryCreatingFolder(path);
		
		Date date = new Date();	
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		int year = localDate.getYear();
		
		path = path + "/" +  year + "_" + month + "_" + "Logs.log"; // exemple : 2018_6_Logs

		
		
		try {
			fh = new FileHandler(path,true);        
			logger.addHandler(fh);
			
			//utilisation d'un format de log propre à notre application
			MyFormatter myformatter = new MyFormatter();
			fh.setFormatter(myformatter);
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Définir le level du log
		switch(level){
		case 1: logger.setLevel(Level.INFO);
				logger.info(message + "\r\n");
				//logger.log(Level.INFO,message);  
		break;
		
		case 2: logger.setLevel(Level.WARNING);
				logger.warning(message+ "\r\n");
				//logger.log(Level.WARNING,message);  
		break;
		
		case 3: logger.setLevel(Level.SEVERE);
				logger.severe(message+ "\r\n");
				//logger.log(Level.SEVERE,message);  
		break;

		}
		
		fh.close();
		
		
	}
	
	private void tryCreatingFolder(String path){
		File d = new File(path);
		
		//Control si le dossier est déjà créer si non on le crée
		if(!d.exists()){
			d.mkdir();
		}
	}

}

class MyFormatter extends Formatter {
    // Create a DateFormat to format the logger timestamp.
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder(1000);
        builder.append(df.format(new Date(record.getMillis()))).append(" - ");
        builder.append("[").append(record.getSourceClassName()).append(".");
        builder.append(record.getSourceMethodName()).append("] - ");
        builder.append("[").append(record.getLevel()).append("] - ");
        builder.append(formatMessage(record));
        builder.append("\n");
        return builder.toString();
    }

    public String getHead(Handler h) {
        return super.getHead(h);
    }

    public String getTail(Handler h) {
        return super.getTail(h);
    }
}
