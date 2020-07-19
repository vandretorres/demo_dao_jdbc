package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


		
		PreparedStatement st = null;
	
		try {

			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?) ",
					Statement.RETURN_GENERATED_KEYS);					
					

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5,obj.getDepartment().getId());
			

			int rowsAffected = st.executeUpdate();
			
			// Tratamento abaixo irá retonar o id criado para o novo usuário e atualizar o objeto com o id criado.
			if ( rowsAffected > 0) {
				
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			    DB.closeResultSet(rs);
				
			}
			
			System.out.println(rowsAffected + " rows Affected");	
			
						
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
						
			DB.closeStatement(st);		
		}	


	}

	@Override
	public void upate(Seller obj) {
		
		PreparedStatement st = null;
		
		try {

			st = conn.prepareStatement(
					  "UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ? " );				
					

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5,obj.getDepartment().getId());
			st.setInt(6,obj.getId());
			

			int rowsAffected = st.executeUpdate();			
			
			if ( rowsAffected > 0) {
			
				System.out.println(rowsAffected + " rows Affected");
				
			}
			
							
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
						
			DB.closeStatement(st);		
		}	

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

				// Instanciação dos objetos foram relegados a métodos staticos
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

			// Um departamento pode conter vários funcionários
						// Para evitar que o mesmo departamento seja instanciado N Vezes
						// foi incluido o Map para fazer uma validação se o departamento já exite na memória ou não.
						Map<Integer,Department> map = new HashMap<>();
						
						while(rs.next()) {
							
							
							// alimenta objeto dep com resultado do departamento retornado no ResultSet
							Department dep = map.get(rs.getInt("DepartmentId"));
							
							// valida se departamento já existe na memoria.
							// se nao existir é chamando metodo de instanciacao do objeto department
							if ( dep == null) {
								
								dep = instanciateDepartment(rs);
								
								// adiciona objeto department no Map
								map.put(rs.getInt("DepartmentId"), dep);
								
							}
							
							// Instanciação dos objetos foram relegados a métodos staticos			
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


	@Override
	public List<Seller> findByDepartment(Department department) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement(

					"SELECT seller.*,department.Name as DepName "
							+ "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id "
							+ "WHERE department.Id = ?"					
					);

			st.setInt(1, department.getId());

			rs = st.executeQuery();	

			// Armarzena vendedores na lista
			List<Seller> list = new ArrayList<>();
			
			
			// Um departamento pode conter vários funcionários
			// Para evitar que o mesmo departamento seja instanciado N Vezes
			// foi incluido o Map para fazer uma validação se o departamento já exite na memória ou não.
			Map<Integer,Department> map = new HashMap<>();
			
			while(rs.next()) {
				
				
				// alimenta objeto dep com resultado do departamento retornado no ResultSet
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				// valida se departamento já existe na memoria.
				// se nao existir é chamando metodo de instanciacao do objeto department
				if ( dep == null) {
					
					dep = instanciateDepartment(rs);
					
					// adiciona objeto department no Map
					map.put(rs.getInt("DepartmentId"), dep);
					
				}
				
				// Instanciação dos objetos foram relegados a métodos staticos			
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





	// Metodo resonsável por instanciar o objeto Seller com base no retorno da query
	// chamada do método é tratada por SQLException
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


	// Metodo resonsável por instanciar o objeto Department com base no retorno da query
	// chamada do método é tratada por SQLException
	private static Department instanciateDepartment(ResultSet rs) throws SQLException {

		Department dep = new Department();		
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));	

		return dep;
	}



}
