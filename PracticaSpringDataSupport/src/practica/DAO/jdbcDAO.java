package practica.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import practica.model.Circle;

public class jdbcDAO {
	
	@Autowired
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Circle getCircle(Integer idCircle) {
		Connection conn = null;
		
//		String driver = "org.apache.derby.jdbc.ClientDriver";
		
		try {
//			Class.forName(driver).newInstance();
//			conn = DriverManager.getConnection("jdbc:derby://localhost:1527/db");
			
			conn = dataSource.getConnection();
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM circle WHERE id=?");
			ps.setInt(1, idCircle);
			
			ResultSet rs = ps.executeQuery();
			
			Circle circulito = null;
			
			while(rs.next()) {
				if(rs.getInt("id") == idCircle) {
					circulito = new Circle(rs.getInt("id"), rs.getString("name"));
				}
			}
			
			ps.close();
			rs.close();
			
			return circulito;
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
		finally{
			try {
				conn.close();
			}
			catch(Exception e) {}
		}
	}
}
