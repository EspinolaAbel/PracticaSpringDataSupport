package practica.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import practica.model.Circle;

public class jdbcDAO {
	
	@Autowired
	private DataSource dataSource;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}


	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
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
	
	public Integer getNumberOfRows() {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM  circle", Integer.class);
	}
	
	public String getCircleName(Integer circleId) {
		String sql = "SELECT name FROM circle WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {circleId}, String.class);
	}

	public Circle getCircleForId(Integer circleId) {
		String sql = "SELECT * FROM circle WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {circleId}, new CircleMapper());
	}
	
	public List<Circle> getListOfCircles() {
		String sql = "SELECT * FROM circle";
		return jdbcTemplate.query(sql, new CircleMapper());
	}
	
//	public void insert(Integer id, String name) {
//		String sql = "INSERT INTO circle VALUES(?, ?)";
////		El método update me sirve tanto para insertar como para borrar filas.
//		jdbcTemplate.update(sql, new Object[] {id, name});
//	}

	/** Insertando una tupla con NamedParameterJdbcTemplate
	A diferencia de jdbcTemplate, con NamedParameterJdbcTemplate se puede asignar parametros
	en una query utilizando nombres de parámetros en vez de ?.
	*/
	public void insert(Integer id, String name) {
		String sql = "INSERT INTO circle VALUES( :id , :name )";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id", id);
		namedParameters.addValue("name", name);
		namedParameterJdbcTemplate.update(sql, namedParameters);
	}
	
	public void createTableTriangle() {
		String sql = "CREATE TABLE Triangle (id INTEGER, name CHAR(50))";
		jdbcTemplate.execute(sql);
	}
	

	/** CircleMapper es una clase interna y privada de jdbcDAO. Tiene como función realizar el mapeo entre la tabla circle
	 * de la base de datos y la clase Circle, implementado en este proyecto.*/
	private static final class CircleMapper implements RowMapper<Circle> {

		@Override
		/** Dado un ResultSet y un row number, se retorna una instancia de Circle mapeado con los valores de la
		 * fila actual del resultset dado. rowNum solo especifica la fila actual del resultset. No es necesario
		 * utilizarlo.*/
		public Circle mapRow(ResultSet rs, int rowNum) throws SQLException {
			Circle circle = new Circle();
			circle.setId(rs.getInt("id"));
			circle.setName(rs.getString("name"));
			
			return circle;
		}
	}
}
