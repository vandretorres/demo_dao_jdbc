package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {

		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void upate(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement(

					"SELECT seller.*,department.Name as DepName "
							+ "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id "
							+ "WHERE seller.Id = ?"					
					);

			st.setInt(1, id);

			rs = st.executeQuery();	

			if ( rs.next()) {

				// Instancia��o dos objetos foram relegados a m�todos staticos
				Department dep = instanciateDepartment(rs);
				Seller seller = instancieateSeller(rs, dep);
				return seller;

			}
			return null;

		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);			

		}

	}

	

	@Override
	public List<Seller> findAll() {

		Statement st = null;
		ResultSet rs = null;

		try {

			st = conn.createStatement();

			rs = st.executeQuery(
					"SELECT seller.*,department.Name as DepName "
							+ "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id "
							+ "order by Id"										
					);


			List<Seller> list = new ArrayList<Seller>();

			while(rs.next()) {

				// Instancia��o dos objetos foram relegados a m�todos staticos
				Department dep = instanciateDepartment(rs);
				Seller seller = instancieateSeller(rs, dep);
				list.add(seller);				
			}

			if ( list.size() > 0)
			{
				return list;
			}else {
				return null;
			}

		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);			

		}
	}


	
	// Metodo resons�vel por instanciar o objeto Seller com base no retorno da query
	// chamada do m�todo � tratada por SQLException
	private Seller instancieateSeller(ResultSet rs, Department dep) throws SQLException {

		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setDepartment(dep);
		
		return seller;
		
	}
	
	
	// Metodo resons�vel por instanciar o objeto Department com base no retorno da query
	// chamada do m�todo � tratada por SQLException
	private static Department instanciateDepartment(ResultSet rs) throws SQLException {

		Department dep = new Department();		
			dep.setId(rs.getInt("DepartmentId"));
			dep.setName(rs.getString("DepName"));	
			
		return dep;
	}



}
