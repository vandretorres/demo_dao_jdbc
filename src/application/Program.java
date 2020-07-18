package application;

import java.sql.Connection;

import db.DB;
import model.entities.Department;

public class Program {

	public static void main(String[] args) {


		Department depto = new Department(1, "DS");
		System.out.println(depto);

	}

}
