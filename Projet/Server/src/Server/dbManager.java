package Server;

import java.sql.*;
public class dbManager {
	
	private Connection con;
	private Statement stmt;

	public dbManager() throws java.lang.Exception{
		Class.forName("org.hsqldb.jdbcDriver");
		
		String url ="jdbc:hsqldb:/c:/temp/projetdb_file";
		
		con = DriverManager.getConnection(url, "sa", "");
		
		
		stmt=con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		
		/*Cr�ation de la db avec les deux tables user et file*/
		stmt.execute(
				"CREATE TABLE USER (U_ID INTEGER IDENTITY PRIMARY KEY, "
				+"U_NAME VARCHAR(50) NOT NULL, "
				+"U_PASSWORD VARCHAR(50) NOT NULL, )"
				);
		
		stmt.execute(
				"CREATE TABLE FILE (F_ID INTEGER IDENTITY PRIMARY KEY , "
				+"F_NAME VARCHAR(50) NOT NULL, "
				+"F_PATH VARCHAR(150) NOT NULL, "
				+ "U_ID INTEGER NOT NULL, "
				+ "FOREIGN KEY (U_ID) REFERENCES USER(U_ID))");
		
		stmt.execute(
				"INSERT INTO USER(U_NAME, U_PASSWORD)"
				+"VALUES('Alex', '123')");
		
		stmt.execute(
				"INSERT INTO USER(U_NAME, U_PASSWORD)"
				+"VALUES('Yann', '123')");
		
		stmt.execute(
				"INSERT INTO USER(U_NAME, U_PASSWORD)"
				+"VALUES('Valentin', '123')");
		
		
		stmt.execute("SELECT * FROM USER");
		
		
		ResultSet rs = stmt.getResultSet();
		
		while(rs.next()) {
			int id = rs.getInt("U_ID");
			String name = rs.getString("U_NAME");
			String password = rs.getString("U_PASSWORD");
			System.out.println(id + " " +name+ " " +password);

		}
		
		stmt.close();

	}
}
