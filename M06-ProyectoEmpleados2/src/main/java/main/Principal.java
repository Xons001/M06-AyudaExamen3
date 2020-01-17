package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

public class Principal {

	public static int leerInteger() {
		Scanner lector = new Scanner(System.in);
		int numero = lector.nextInt();
		return numero;
	}

	public static float leerFloat() {
		Scanner lector = new Scanner(System.in);
		Float numero = lector.nextFloat();
		return numero;
	}
	public static String leerString() {
		Scanner lector = new Scanner(System.in);
		String texto= lector.nextLine();
		return texto;
	}
	public static void main(String[] args) {
		conexionMySQL();
	}

	static String usuari = "root";
	static String contr = "";
	static String url = "jdbc:mysql://localhost:3306/empleados?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	private static void conexionMySQL() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Hola");
			System.out.println("Driver cargado correctamente");
			Connection conn = DriverManager.getConnection(url, usuari, contr);
			System.out.println("Conexion creada correctamente");
			System.out.println("--------------------------------------------------");
			boolean salir = false;
			while (salir == false) {
				System.out.println("==============================================");
				System.out.println("Menu");
				System.out.println("1.-Insertar");
				System.out.println("2.-Update");
				System.out.println("3.-Delete");
				System.out.println("==============================================");

				int pos = leerInteger();

				switch (pos) {
				case 1:
					insertarDatos(conn);
					break;
				case 2:

					break;
				case 3:

					break;

				default:
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("No se ha podido conectar a la base de datos");		
		}
	}

	//Insertar
	public static void insertarDatos(Connection conn) {
		PreparedStatement insertDep, insertEmp;

		try {
			System.out.println("--------------------------------------------------");
			boolean salir = false;
			while (salir == false) {
				System.out.println("==============================================");
				System.out.println("Escoge el numero");
				System.out.println("1.-Departamento");
				System.out.println("2.-Empleado");
				System.out.println("==============================================");

				int pos = leerInteger();

				switch (pos) {
				case 1:
					// Insertar departamento
					insertDep = conn.prepareStatement("insert into departamentos values (?, ?, ?)");
					
					System.out.print("Id del departamento: ");
					int idDep=leerInteger();
					insertDep.setInt(1, idDep);
					
					System.out.print("Nombre del departamento: ");
					String nomDep=leerString();
					insertDep.setString(2, nomDep);
					
					System.out.print("Localizacion del departamento: ");
					String locDep=leerString();
					insertDep.setString(3, locDep);

					insertDep.executeUpdate();
					System.out.println("Departamento insertado");
					salir = true;
					break;
				case 2:
					// Insertar empleado
					insertEmp = conn.prepareStatement("insert into empleados values (?, ?, ?, ?, ?, ?, ?, ?)");

					System.out.print("Id del empleado: ");
					int idEmp = leerInteger();
					insertEmp.setInt(1, idEmp);
					
					System.out.print("Apellido del empleado: ");
					String apellidoEmp = leerString();
					insertEmp.setString(2, apellidoEmp);
					
					System.out.print("Oficio del empleado: ");
					String oficioEmp = leerString();
					insertEmp.setString(3, oficioEmp);
					
					System.out.print("Dir del empleado: ");
					int dirEmp = leerInteger();
					insertEmp.setInt(4, dirEmp);
					
					System.out.print("Fecha de alta del empleado: ");
					String fechaAltaEmp=leerString();
					insertEmp.setDate(5, java.sql.Date.valueOf(fechaAltaEmp));
					
					System.out.println("Salario empleado");
					Float salEmp = leerFloat();
					insertEmp.setFloat(6, salEmp);
					
					System.out.println("Comision empleado");
					Float comEmp = leerFloat();
					insertEmp.setFloat(7, comEmp);
					
					System.out.print("Id del departamento del empleado: ");
					int idDepEmp=leerInteger();
					insertEmp.setInt(8, idDepEmp);

					insertEmp.executeUpdate();
					System.out.println("Empleado insertado");
					salir = true;
					break;
				default:
					break;
				}
			}

			System.out.println("--------------------------------------------------");
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Los datos ya estan insertados o el departamento no existe");
		} catch (SQLException e) {
			System.out.println("No encuentra las bases de datos");
		} 
	}
}