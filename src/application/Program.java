package application;

import java.util.Date;
import java.util.List;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

	
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		
		System.out.println("=== TEST 1 :: Seller findById ======");
		Seller seller = sellerDao.findById(2);		
		System.out.println(seller);
		
		
		System.out.println();
		System.out.println("=== TEST 2 :: Seller findByDepartment ======");
		Department department = new Department(2,null);
		List<Seller> list = sellerDao.findByDepartment(department);
		
		for (Seller obj : list) {
			
			System.out.println(obj);
		}
		
		System.out.println();
		System.out.println("=== TEST 3 :: Seller findByAll ======");
		department = new Department(1,null);
		List<Seller> list2 = sellerDao.findAll();
		
		for (Seller obj : list2) {
			
			System.out.println(obj);
		}
		
		
		System.out.println();
		System.out.println("=== TEST 4 ::  Insert new Seller ======");
		Seller newSeller = new Seller(null, "Vandre", "vandre@gmail.com", new Date(), 4000.00, department);
		//sellerDao.insert(newSeller);
		System.out.println(newSeller);
		
		
		System.out.println();
		System.out.println("=== TEST 4 :: Seller Seller Update ======");
		seller = sellerDao.findById(1);
		seller.setName("Marta Waine");
		sellerDao.upate(seller);
		
				
		DB.closeConnection();	
		
	}

}
