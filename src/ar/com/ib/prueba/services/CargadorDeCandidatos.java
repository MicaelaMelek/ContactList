package ar.com.ib.prueba.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.ib.prueba.domain.Candidato;

public class CargadorDeCandidatos {

	List<Candidato> listaDeCandidatos = new ArrayList<Candidato>();
	List<Candidato> listaDeBusquedas;
	Map<Integer, String> errores = new HashMap<Integer,String>();
	InputStream archivo;

	public CargadorDeCandidatos(InputStream inputStream) {
		this.archivo = inputStream;
		cargarObjetos();
	}
	
	//Método que devuelve la lista ordenada por el parametro elegido

	public List<Candidato> listaCandidatos(int parametro) throws IOException {
		if (parametro == 1) {
			Collections.sort(listaDeCandidatos, new Comparator<Candidato>() {
				public int compare(Candidato c1, Candidato c2) {
					return c1.getNombre().toLowerCase().compareTo(c2.getNombre().toLowerCase());
				}
			});
		} else if (parametro == 2) {
			Collections.sort(listaDeCandidatos, new Comparator<Candidato>() {
				public int compare(Candidato c1, Candidato c2) {
					return c1.getApellido().toLowerCase().compareTo(c2.getApellido().toLowerCase());
				}
			});
		}else if(parametro == 3){
			Collections.sort(listaDeCandidatos, new Comparator<Candidato>() {
				public int compare(Candidato c1, Candidato c2) {
					return c1.getFechaDeNacimiento().compareTo(c2.getFechaDeNacimiento());
				}
			});
		}
		else if (parametro == 4){
			Collections.sort(listaDeCandidatos, new Comparator<Candidato>() {
				public int compare(Candidato c1, Candidato c2) {
					return c1.getPuesto().toLowerCase().compareTo(c2.getPuesto().toLowerCase());
				}
			});
		}else{
			return null;
		}
		return listaDeCandidatos;
	}

	private void cargarObjetos() {
		String linea;
		List datos;
		BufferedReader reader;
		int numeroLinea = 1;

		try {
			reader = new BufferedReader(new InputStreamReader((this.archivo), "UTF-8"));

			while ((linea = reader.readLine()) != null) {
				datos = Arrays.asList(linea.split(","));
				Candidato candidato = new Candidato((String) datos.get(0), (String) datos.get(1),
						formatoFecha((String) datos.get(2)), (String) datos.get(3), (String) datos.get(4),
						formatoFecha((String) datos.get(5)));
				if(candidato.getErrores().isEmpty())
				//Si pasa los validadores se agrega el candidato a la lista, de lo contrario se agrega un error
				listaDeCandidatos.add(candidato);
				else{
					errores.put(numeroLinea, candidato.erroresToString());
				}
				numeroLinea++;
			}
			reader.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Date formatoFecha(String fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		String fechaString = fecha;

		try {
			Date date = formato.parse(fechaString);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	private int formatearTelefono(String telefono) {
		int telefonoFormateado = Integer.parseInt(telefono);
		return telefonoFormateado;
	}
	
	public void hashErrores() {
		for (Map.Entry<Integer, String> entry : errores.entrySet()) {
			int linea = entry.getKey();
			String errores = entry.getValue();
			System.out.println("En la linea: " + linea + " se encuentran los siguientes errores:\n" + errores);
		}
	}
	
	public List<Candidato> buscarPorApellido(String apellido) {
		listaDeBusquedas = new ArrayList<Candidato>();

		for (Candidato candidato : listaDeCandidatos) {
			if (candidato.getApellido().toLowerCase() != null
					&& candidato.getApellido().toLowerCase().contains(apellido.toLowerCase())) {
				listaDeBusquedas.add(candidato);
			}
		}
		return listaDeBusquedas;
	}
	
	public List<Candidato> buscarPorPuesto(String puesto) {
		listaDeBusquedas = new ArrayList<Candidato>();

		for (Candidato candidato : listaDeCandidatos) {
			if (candidato.getPuesto().toLowerCase() != null
					&& candidato.getPuesto().toLowerCase().contains(puesto.toLowerCase())) {
				listaDeBusquedas.add(candidato);
			}
		}
		return listaDeBusquedas;
	}
}
