package bestbooks.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil
{
	private static Connection connection;
	
	private DBUtil() {}
	
	public static synchronized Connection getConnection() throws DBException
	{
		if (connection != null)
		{
			return connection;
		}
		else
		{
			try
			{
				String url = "jdbc:mysql://localhost:3306/Books";
				String username = "root";
				String password = "";
				
				connection = DriverManager.getConnection(url, username, password);
				
				return connection;
			} 
			catch (SQLException e)
			{
				throw new DBException(e);
			}
		}
	}
	
	public static synchronized void closeConnection() throws DBException
	{
		if (connection != null)
		{
			try
			{
				connection.close();
			}
			catch (SQLException e)
			{
				throw new DBException(e);
			}
			finally
			{
				connection = null;
			}
		}
	}

}
