package daoBDD;


import java.io.FileInputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionPizza {
	
	public static void main(String[] args)
	{
		Connection conn = null;

		try
		{
			conn = ConnectionPizza.getConnection();
			
			System.out.println("Connexion effective !");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				conn.close();
			}
			catch (SQLException e)
			{
				// do nothing
			}
		}
	}

	public static Connection getConnection() throws Exception
	{
		
//		String url = "jdbc:mysql://localhost:3333/pizzeria";
//		String user = "root";
//		String passwd = "root";
		
		Properties prop = new Properties();
		/* Ici le fichier contenant les données de configuration est nommé 'db.myproperties' */
		FileInputStream in = new FileInputStream("src/main/resources/config.properties");
		prop.load(in);
		// Extraction des propriétés
		String url = prop.getProperty("dburl");
		String user = prop.getProperty("dbuser");
		String password = prop.getProperty("dbpassword");

		DriverManager.setLogWriter(new PrintWriter(System.out));
		Class.forName("com.mysql.jdbc.Driver");
		
		return DriverManager.getConnection(url, user, password);
	}
}
