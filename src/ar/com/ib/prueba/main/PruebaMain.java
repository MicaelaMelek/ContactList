package ar.com.ib.prueba.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import ar.com.ib.prueba.services.CargadorDeCandidatos;
import ar.com.ib.prueba.domain.Candidato;

public class PruebaMain {

	public static void main(String[] args) throws IOException {

		// Modificar en el archivo la ultima fecha para que sea el día de hoy
		String ruta;
		int parametro;
		InputStream inputStream;
		Scanner entrada = new Scanner(System.in);

		// Carga de archivo (Ej de ruta : /home/meme/Documents/candidatos)
		System.out.println("Ingrese la ruta del archivo");
		ruta = entrada.next();

		try {
			inputStream = new FileInputStream(ruta);
		} catch (FileNotFoundException e) {
			System.out.println("El archivo no existe");
			return;
		}

		CargadorDeCandidatos candidateLoader = new CargadorDeCandidatos(inputStream);
		System.out.println(" 1) Buscar un candidato\n 2) Ordenar los candidatos");
		parametro = entrada.nextInt();

		if (parametro == 1) {
			do {
				String busqueda;
				System.out.println("Ingrese el parámetro para buscar\n 1) Apellido\n 2) Puesto\n 0) Salir");
				parametro = entrada.nextInt();

				switch (parametro) {
					case 1:
						System.out.println("Ingrese el apellido: ");
						busqueda = entrada.next();
						imprimirCandidatos(candidateLoader.buscarPorApellido(busqueda));
						break;
					case 2:
						System.out.println("Ingrese el puesto: ");
						busqueda = entrada.next();
						imprimirCandidatos(candidateLoader.buscarPorPuesto(busqueda));
						break;
					case 0:
						System.out.println("Adiós!");
						break;
					default:
						System.out.println("No ha elegido una opción valida! Intentelo nuevamente");
						break;
					}
				} while (parametro != 0);
		} else if (parametro == 2) {
			do {
				System.out.println(
						"Ingrese el parámetro de orden\n 1) Nombre\n 2) Por Apellido\n 3) Fecha de nacimiento\n "
								+ "4) Puesto\n 0) Salir");

				parametro = entrada.nextInt();
				if(parametro == 1 || parametro == 2 || parametro == 3 || parametro == 4){
					imprimirCandidatos(candidateLoader.listaCandidatos(parametro));
					candidateLoader.hashErrores();
				} else if (parametro == 0){
					System.out.println("Adiós!");
				} else {
					System.out.println("No ha elegido una opción valida! Intentelo nuevamente");
				}
			} while (parametro != 0);
		}
	}

	public static void imprimirCandidatos(List<Candidato> candidatos) {
		for (Object candidato : candidatos) {
			System.out.println(candidato.toString());
			System.out.println("");
		}
	}
}
