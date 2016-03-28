package practica.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import practica.DAO.jdbcDAO;

public class JDBCmain {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("Spring.xml");
		
		jdbcDAO j = (jdbcDAO) context.getBean("jdbcDAO");
		Circle circle = j.getCircle(1);
		 
		 System.out.println(circle.getName());
		 
		 System.out.println("Cantidad de filas en la tabla: "+ j.getNumberOfRows());
	}

}
