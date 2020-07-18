package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDao {
	
	
	void insert(Seller obj);
	void upate(Seller obj);
	void deleteById(Integer obj);
	Seller findById(Integer id);
	List<Seller> findAll();

}
