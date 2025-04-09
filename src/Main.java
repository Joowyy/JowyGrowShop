import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
	
//	URL MySQL Global
	static String url = "jdbc:mysql://localhost:3306/jowygrowshop";

	public static void main(String[] args) {

		Statement s = conexionBDD();
		imprimirClientes(s);
		//añadirCliente(s);

	}
	
	public static void añadirCliente (Statement s) {
		Scanner sc = new Scanner (System.in);
		boolean clienteAñadido = false;
		
		do {
		
//			Pedimos info del usuario
			System.out.println("Dime el nombre del cliente -> ");
			String nombreCliente = sc.nextLine();
			System.out.println("Dime su edad -> ");
			int edadCliente = sc.nextInt();
		
			if (edadCliente <= 0 || edadCliente >= 150) {
			
				System.out.println("Introduce una edad válida");
				break;
				
			}
		
//			Creamos el comando
			String comandoSQL = String.format("INSERT INTO clientes (nombre, edad) VALUES ('%s', '%s');", nombreCliente, edadCliente);
		
//			Ejecutamos el comando en la base
			try {
			
				s.execute(comandoSQL);
				clienteAñadido = true;
			
			} catch (SQLException e) {

				e.printStackTrace();
			
			}
		
		} while (!clienteAñadido);
		
	}
	
	public static void imprimirClientes (Statement s) {
		
//		'while' hace falta para imprimir todas las filas y columnas
		System.out.println("ID | Nombre | Edad");
		try {
			
			ResultSet rs = s.executeQuery("SELECT * FROM clientes");
			while (rs.next()) {

				System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3));
				
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		}
		
	}
	
	public static Statement conexionBDD () {
		
//		|||| LEER BASE DE DATOS ||||
//		Pasamos la URL, Usuario y contraseña
		Connection c;
		try {
			
			c = DriverManager.getConnection(url, "root", "");
			Statement s = c.createStatement();
			
			return s;
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		}
		
		return null;
	}

}
