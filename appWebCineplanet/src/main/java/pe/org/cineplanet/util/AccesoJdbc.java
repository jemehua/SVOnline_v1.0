package pe.org.cineplanet.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccesoJdbc {

	private Connection con;
	private static AccesoJdbc instancia;
	private String url = "jdbc:oracle:thin:@172.16.19.33:1521/bdprueba2";
	private String username = "desempeno";
	private String password = "desempeno123";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	
//	private String url = "jdbc:oracle:thin:@localhost:1521/XE";
//	private String username = "desempeno_local";
//	private String password = "desempeno123";
//	private String driver = "oracle.jdbc.driver.OracleDriver";

	private AccesoJdbc() {
		try {

			Class.forName(driver);

		} catch (Exception e) {
			System.out.println("Parametros de conexion incorrectos.");
		}
	}

	public static synchronized AccesoJdbc getInstance() {
		if (instancia == null)
			instancia = new AccesoJdbc();
		return instancia;
	}

	public synchronized Connection getConnection() throws SQLException {

		if (con == null || con.isClosed())
			con = DriverManager.getConnection(url, username, password);
		return con;
	}

	public synchronized void close() throws SQLException {

		if (con != null && !con.isClosed())
			con.close();
	}

}
