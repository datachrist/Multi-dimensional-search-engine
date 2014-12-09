package multidimensionalsearch;



import java.util.ArrayList;


/**
 * @author vaishali
 *
 */

public class Node {
	long id;
	ArrayList<Long> name;
	Double price;
	boolean flag = false;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ArrayList<Long> getName() {
		return name;
	}
	public void setName(ArrayList<Long> name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}


 

	public int compareToPrice(Object o) {
	// TODO Auto-generated method stub
	if(price < ((Node)o).price){
		if(id== ((Node)o).id )
			return 0;
		else
			return -1;
	}
	else if(price == ((Node)o).price){
		if(id!= ((Node)o).id )
			return -1;
		else
			return 0;
	}
	else{
		if(id== ((Node)o).id )
			return 0;
		else
			return 1;
	}
		
	}
	
	
}
