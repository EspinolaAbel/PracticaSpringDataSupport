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
		 
		 System.out.println("Nombre del círculo con id igual a 1: " + j.getCircleName(1));
		 
		 System.out.println();
		 System.out.println("Todos los circulos:");
		 for(Circle c: j.getListOfCircles())
			 System.out.println("Circulo "+c.getId()+" con nombre "+c.getName());
		 
//		 j.createTableTriangle();
//		 
//		 j.insert(100, "A houndred circle");
		 
//		 j.insert(5, "Fifth circle");

	}

}
