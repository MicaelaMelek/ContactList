package ar.com.ib.prueba.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Candidato {

	String nombre;
	String apellido;
	Date fechaDeNacimiento;
	String telefono;
	String puesto;
	Date fechaDePostulacion;
	List<String> errores = new ArrayList<String>();
	String[] puestos = new String[] { "programador", "analista funcional", "tester" };

	public Candidato(String nombre, String apellido, Date fechaDeNacimiento, String telefono, String puesto,
			Date fechaDePostulacion) {
		setNombre(nombre);
		setApellido(apellido);
		setFechaDeNacimiento(fechaDeNacimiento);
		setTelefono(telefono);
		setPuesto(puesto);
		setFechaDePostulacion(fechaDePostulacion);
	}

	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if (validarString(nombre))
			this.nombre = nombre;
		else
			errores.add("El nombre "+nombre+" no es valido");
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		if (validarString(apellido))
			this.apellido = apellido;
		else
			errores.add("El apellido "+apellido+" no es valido");
	}

	public Date getFechaDeNacimiento() {
		return fechaDeNacimiento;
	}

	public void setFechaDeNacimiento(Date fechaDeNacimiento) {
		if (validarFecha(fechaDeNacimiento))
			this.fechaDeNacimiento = fechaDeNacimiento;
		else
			errores.add("La fecha de nacimiento "+fechaDeNacimiento+" no es valida");
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		if (validarTelefono(telefono))
			this.telefono = telefono;
		else
			errores.add("El número de telefono: "+telefono+" no es valido");
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		if (validarPuesto(puesto))
			this.puesto = puesto;
		else 
			errores.add("El puesto "+puesto+" no es valido");
	}

	public Date getFechaDePostulacion() {
		return fechaDePostulacion;
	}

	public void setFechaDePostulacion(Date fechaDePostulacion) {
		if (validarPostulacion(fechaDePostulacion))
			this.fechaDePostulacion = fechaDePostulacion;
		else
			errores.add("la fecha "+fechaDePostulacion+" no es valida");
	}

	public List<String> getErrores() {
		return errores;
	}


	public void setErrores(List<String> errores) {
		this.errores = errores;
	}
	
	private boolean validarString(String str) {
		return (str.matches("[a-zA-Z][a-zA-Z ]*") && (str.length() >= 3 && str.length() <= 50));
	}

	private boolean validarFecha(Date fecha) {

		Calendar fechaNacimiento = Calendar.getInstance();
		fechaNacimiento.setTime(fecha);
		Calendar hoy = Calendar.getInstance();
		int edad = hoy.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
		if (hoy.get(Calendar.DAY_OF_YEAR) < fechaNacimiento.get(Calendar.DAY_OF_YEAR)) {
			edad--;
		}

		return (edad >= 20 && edad <= 40);
	}

	private boolean validarTelefono(String telefono) {
		return (telefono.matches("^[0-9]+$") && (telefono.length() >= 8 && telefono.length() <= 12));
	}

	private boolean validarPuesto(String puesto) {
		return(validarString(puesto) && Arrays.asList(puestos).contains(puesto.toLowerCase()));
	}

	private boolean validarPostulacion(Date fecha) {
		Calendar fechaPostulacion = Calendar.getInstance();
		fechaPostulacion.setTime(fecha);
		Date hoy = Calendar.getInstance().getTime();
		SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyy");

		return (formato.format(fecha).equals(formato.format(hoy)));
	}
	
	public String toString() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		String candidatoString = "Nombre: " + nombre + "\n" + "Apellido: " + apellido + "\n" + "Fecha de nacimiento: "
				+formato.format(fechaDeNacimiento) + "\n" + "Teléfono: " + telefono + "\n" + "Puesto: " + puesto + "\n"
				+ "Fecha de postulación: " + formato.format(fechaDePostulacion);
		return candidatoString;
	}
	
	public String erroresToString(){
		StringBuilder sb = new StringBuilder();
		for (String error: getErrores())
		{
		    sb.append(error);
		    sb.append("\n");
		}

		return(sb.toString());
	}
}
