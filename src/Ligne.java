import java.util.ArrayList;

public class Ligne {
	ArrayList<ArrayList<String>> arrets;
	String name;
	String num;
	String color;
	String type;
	ArrayList<ArrayList<Double>> labels;
	
	public Ligne(ArrayList<ArrayList<String>> arrets) {
		super();
		this.arrets = arrets;
	}
	
	public ArrayList<ArrayList<String>> getArrets() {
		return arrets;
	}
	public void setArrets(ArrayList<ArrayList<String>> arrets) {
		this.arrets = arrets;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ArrayList<ArrayList<Double>> getLabels() {
		return labels;
	}
	public void setLabels(ArrayList<ArrayList<Double>> labels) {
		this.labels = labels;
	}
	
}
