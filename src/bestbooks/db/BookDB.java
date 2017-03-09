package bestbooks.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bestbooks.business.Book;

public class BookDB
{
	public static List<Book> getAll() throws DBException
	{
		String sql = "SELECT * FROM products ORDER BY ProductCode";
		List<Book> books = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		
		try (PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery())
		{
			while (rs.next()) 
			{
				String productCode = rs.getString("ProductCode");
				String description = rs.getString("Description");
				double price	   = rs.getDouble("Price");
				
				Book b = new Book();
				b.setCode(productCode);
				b.setDescription(description);
				b.setPrice(price);
				books.add(b);
			}
			
			return books;
		}
		catch (SQLException e)
		{
			throw new DBException(e);
		}
	}
	
	public static Book get(String productCode) throws DBException
	{
		String sql = "SELECT * FROM products WHERE productCode = ?";
		Connection connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql))
		{
			ps.setString(1,  productCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
//				String productCode = rs.getString("ProductCode");
				String description = rs.getString("Description");
				double price	   = rs.getDouble("Price");
				rs.close();
				
				Book b = new Book();
				b.setCode(productCode);
				b.setDescription(description);
				b.setPrice(price);
				
				return b;
			}
			else
			{
				rs.close();
				return null;
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e);
		}
	}
	
    public static void add(Book book) throws DBException {
        String sql
                = "INSERT INTO products (ProductCode, Description, Price) "
                + "VALUES (?, ?, ?)";
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, book.getCode());
            ps.setString(2, book.getDescription());
            ps.setDouble(3, book.getPrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public static void update(Book book) throws DBException {
        String sql = "UPDATE products SET "
                + "ProductCode = ?, "
                + "Description = ?, "
                + "Price = ? "
                + "WHERE ProductCode = ?";
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, book.getCode());
            ps.setString(2, book.getDescription());
            ps.setDouble(3, book.getPrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }        
    }
    
    public static void delete(Book book) 
            throws DBException {
        String sql = "DELETE FROM products "
                   + "WHERE ProductCode = ?";
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, book.getCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
