package Server;

import java.sql.*;
import java.util.ArrayList;
public class dbManager {
	
	private  Connection con;
	private  Statement stmt;

	public dbManager() throws java.lang.Exception{
		Class.forName("org.hsqldb.jdbcDriver");
		String url ="jdbc:hsqldb:/c:/temp/projetdb_file";
		
		con = DriverManager.getConnection(url, "sa", "");
		
		stmt=con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		
		/*Cr�ation de la db avec les deux tables user et file*/				
		if(existe(con, "USER")){
			//Nothing to do
		}else{
			stmt.execute(
			"CREATE TABLE USER (U_ID INTEGER IDENTITY PRIMARY KEY, "
			+"U_NAME VARCHAR(50) NOT NULL, "
			+"U_PASSWORD VARCHAR(50) NOT NULL, )"
			);
			
			stmt.execute(
			"INSERT INTO USER(U_NAME, U_PASSWORD)"
			+"VALUES('Alex', '123')");
	
			stmt.execute(
			"INSERT INTO USER(U_NAME, U_PASSWORD)"
			+"VALUES('Yann', '123')");
	
			stmt.execute(
			"INSERT INTO USER(U_NAME, U_PASSWORD)"
			+"VALUES('Valentin', '123')");
		}
		
		if(existe(con, "FILE")){
			//Nothing to do
		}else{
			stmt.execute(
			"CREATE TABLE FILE (F_ID INTEGER IDENTITY PRIMARY KEY , "
			+"F_NAME VARCHAR(50) NOT NULL, "
			+"F_PATH VARCHAR(150) NOT NULL, "
			+ "U_ID INTEGER NOT NULL, "
			+ "FOREIGN KEY (U_ID) REFERENCES USER(U_ID))");
		}
			
		stmt.close();

	}
	
	public boolean existe(Connection connection, String nomTable) throws SQLException{
		boolean existe;
		DatabaseMetaData dmd = connection.getMetaData();
		ResultSet tables = dmd.getTables(connection.getCatalog(), null, nomTable, null);
		existe = tables.next();
		tables.close();
		return existe;
		
	}
	
	public void addUser(String userName, String password) throws java.lang.Exception{
		stmt=con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		
		PreparedStatement stmt = con.prepareStatement("INSERT INTO USER(U_NAME, U_PASSWORD) "
				+"VALUES(?, ?)");
		stmt.setString(1, userName);
		stmt.setString(2, password);
		
		
		int rs = stmt.executeUpdate();
		
		stmt.close();
	}
	
	public void addFile(String fileName, String filePath, int userId) throws java.lang.Exception{
		stmt=con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		
		PreparedStatement stmt = con.prepareStatement("INSERT INTO FILE(F_NAME, F_PATH, U_ID) "
				+"VALUES(?, ?, ?)");
		stmt.setString(1, fileName);
		stmt.setString(2, filePath);
		stmt.setInt(3, userId);
		
		
		int rs = stmt.executeUpdate();
		
		stmt.close();
	}
	
	public boolean login(String userName, String password)throws java.lang.Exception{
		
		boolean result;
		stmt=con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM USER WHERE"
				+ " U_NAME = ? "
				+ "AND U_PASSWORD = ?");
		
		stmt.setString(1, userName);
		stmt.setString(2, password);
		
		
		ResultSet rs = stmt.executeQuery();
		
		result = rs.next();
		
		stmt.close();
		
		return result;
	}

	public int getIdByLogin(String login)throws java.lang.Exception {
		// TODO Auto-generated method stub
		stmt=con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		
		PreparedStatement stmt = con.prepareStatement("SELECT U_ID FROM USER WHERE"
				+ " U_NAME = ? ");
		
		stmt.setString(1, login);
		
		
		ResultSet rs = stmt.executeQuery();
		//rs.next();
		
		if(rs.next()==false){
			return -1;
		}
		else{
			return rs.getInt("U_ID");
		}

	}
	
	public String getUserNameByUserId(int userId)throws java.lang.Exception {
		// TODO Auto-generated method stub
		stmt=con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		
		PreparedStatement stmt = con.prepareStatement("SELECT U_NAME FROM USER WHERE"
				+ " U_ID = ? ");
		
		stmt.setInt(1, userId);
		
		
		ResultSet rs = stmt.executeQuery();
		//rs.next();
		
		if(rs.next()==false){
			return "Yann";
		}
		else{
			return rs.getString("U_NAME");
		}

	}
	
	public void printFileTable()throws java.lang.Exception {
		// TODO Auto-generated method stub
		stmt=con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM FILE");
		
		
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()){
			System.out.println(rs.getInt("F_ID")+" - "+rs.getString("F_NAME")+" - "+rs.getString("F_PATH")+" - "+rs.getString("U_ID"));
		}
		
		System.out.println(rs.toString());
	}
	
	public ArrayList<file> getFileByUserId(int userId)throws java.lang.Exception {
		// TODO Auto-generated method stub
		stmt=con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		
		PreparedStatement stmt = con.prepareStatement("SELECT F_ID, F_NAME, F_PATH FROM FILE WHERE"
				+ " U_ID = ? ");
		
		stmt.setInt(1, userId);
		
		
		ResultSet rs = stmt.executeQuery();
		ArrayList<file> result = new ArrayList<file>();
		while(rs.next()){
			int id = rs.getInt("F_ID");
			String name = rs.getString("F_NAME");
			String path = rs.getString("F_PATH");
			
			
			
			file f = new file(id, name, path);
			result.add(f);
		}
		
		return result;
	}
	
}
