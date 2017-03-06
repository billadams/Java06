package bestbooks.ui;

public class Login
{	
	private final static String ADMIN_USERNAME = "admin";
	private final static String ADMIN_PASSWORD = "admin";
	private final static String STANDARD_USERNAME = "default";
	private final static String STANDARD_PASSWORD = "default";
	
	// Only used to authenticate a user.
	public static boolean authenticateUser(String username, String password, boolean isAdminAttempt)
	{
		if (isAdminAttempt)
		{
			if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD))
			{
				return true;
			} 
		}
		else
		{
			if (username.equals(STANDARD_USERNAME) && password.equals(STANDARD_PASSWORD))
			{
				return true;
			}
		}

		return false;
	}

}
