package practica.model;

public class Circle {

	private String name;
	private Integer id;

	public Circle(Integer id, String name) {
		this.id=id;
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
