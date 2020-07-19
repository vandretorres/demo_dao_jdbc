package application;

import java.util.List;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

	
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		
		System.out.println("=== TEST 1 :: Seller findById ======");
		Seller list = sellerDao.findById(2);		
		System.out.println(list);
		
		DB.closeConnection();	
		
	}

}
