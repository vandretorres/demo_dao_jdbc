package application;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {


		Department depto = new Department(1, "DS");
		System.out.println(depto);

		
		Seller sell = new Seller(1, "Vandre", "vandre@", new Date(), 3000.0,  new Department(1, "DS"));
		
		System.out.println(sell);
		
	}

}
