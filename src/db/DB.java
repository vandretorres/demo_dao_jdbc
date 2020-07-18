package db;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class DB {


	// Atributo que armazena a conexão com banco de dados
	private static Connection conn = null;



	// Cria conexão com banco de dados
	public static Connection getConnection() {

		if ( conn == null) {

			Properties props = loadProperties();			
			String url = props.getProperty("dburl");

			try {

				conn = DriverManager.getConnection(url,props);

			} catch (SQLException e) {

				throw new DbException(e.getMessage());
			}

		}
		return conn;

	}


	// Fecha conexão com banco de dados
	public static void closeConnection() {


		if ( conn != null) {

			try {

				conn.close();

			} catch (SQLException e) {

				throw new DbException(e.getMessage());

			}			
		}

	}


	private static Properties loadProperties() {

		try (FileInputStream fs = new FileInputStream("db.properties")){
			Properties props = new Properties();
			props.load(fs);
			return props;			
		}
		catch(IOException e){

			throw new DbException(e.getMessage());

		}

	}

	// Fecha conexão do Statementt lançando exceção para Classe especifica
	public static void closeStatement(Statement st) {

		try {

			if ( st != null)
			{
				st.close();
			}

		} catch (SQLException e) {

			throw new DbException(e.getMessage());
		}		
	}

	
	// FEcha conexão do ResultSet lançando exceção para Classe especifica
	public static void closeResultSet(ResultSet rs ) {
		try {

			if ( rs != null) {
				rs.close();
			}

		} catch (SQLException e) {

			throw new DbException(e.getMessage());
		}	
	}


}
