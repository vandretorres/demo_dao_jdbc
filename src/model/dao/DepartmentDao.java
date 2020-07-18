package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {
	
	void insert(Department obj);
	void upate(Department obj);
	void deleteById(Integer obj);
	Department findById(Integer id);
	List<Department> findAll();

}
