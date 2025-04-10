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
//		Conexion a la BDD
		Statement s = conexionBDD();
		char opc;
		
		do {
		
			opc = menu();
			switch (opc) {
			case '1':
				
				break;
				
			case '2':
				imprimirTabla(s);
				break;
				
			case '5':
				System.out.println("Saliendo... Vuelva pronto!");
				break;
			}
		
		} while (opc != '5');

	}
	
//	---- AÑADIR CLIENTE ----
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
	
//	---- IMPRIMIR CLIENTES ----
	public static void imprimirTabla (Statement s) {
		Scanner sc = new Scanner (System.in);
		
		System.out.println("¿Quieres ver la tabla productos o clientes?");
		String tabla = sc.nextLine();
		
		if (tabla.equalsIgnoreCase("clientes")) {
		
//			'while' hace falta para imprimir todas las filas y columnas
			System.out.println("\n------------------------");
			System.out.println("ID | Nombre | Edad");
			try {
				
				ResultSet rs = s.executeQuery("SELECT * FROM clientes");
				while (rs.next()) {
	
					System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3));
					
				}
				System.out.println("------------------------\n");
				
			} catch (SQLException e) {
	
				e.printStackTrace();
				
			}
		
		} else if (tabla.equalsIgnoreCase("productos")) {
			
			System.out.println("\n------------------------");
			System.out.println("ID | Nombre | Precio");
			try {
				
				ResultSet rs = s.executeQuery("SELECT * FROM productos");
				while (rs.next()) {
	
					System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3));
					
				}
				System.out.println("------------------------\n");
				
			} catch (SQLException e) {
	
				e.printStackTrace();
				
			}
			
		}
		
	}
	
//	---- CONEXION A LA BDD ----
	public static Statement conexionBDD () {

		try {
			
			Connection c = DriverManager.getConnection(url, "root", "");
			Statement s = c.createStatement();
			
			return s;
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		}
		
		return null;
	}

//	---- MENU DEL PROGRAMA ----
	public static char menu () {
		Scanner sc = new Scanner(System.in);
		char opc;
		
		System.out.println("🌿 BIENVENIDO A JOWY'S GROWSHOP 🌿");
		System.out.println("1. Modificar producto");
		System.out.println("2. Consultar tablas");
		System.out.println("3. Comprar producto");
		opc = sc.nextLine().charAt(0);
		
		return opc;
	}
	
}
