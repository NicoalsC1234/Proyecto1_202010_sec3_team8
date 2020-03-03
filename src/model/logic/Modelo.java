package model.logic;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.sun.corba.se.impl.orbutil.RepositoryIdUtility;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import model.data_structures.Cola;
import model.data_structures.Comparendo;
import model.data_structures.Nodo;
import model.logic.*;


/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {

	public static String PATH = "./data/comparendos_dei_2018_small.geojson";

	private Cola<Comparendo> cola;


	public Modelo()
	{
		cola = new Cola<Comparendo>();
	}

	public int darTamano()
	{
		return cola.darTamano();
	}

	public void enqueue(Comparendo dato)
	{	
		cola.enqueue(dato);
	}

	public Nodo<Comparendo> darPrimero()
	{
		return cola.darPrimero();
	}

	public Nodo<Comparendo> darUltimo()
	{
		return cola.darUltimo();
	}

	public Nodo<Comparendo> dequeue()
	{
		return cola.dequeue();
	}


	public void cargarDatos() {
		if(cola.esVacio()){
			JsonReader reader;
			try {
				reader = new JsonReader(new FileReader(PATH));
				JsonElement elem = JsonParser.parseReader(reader);
				JsonArray e2 = elem.getAsJsonObject().get("features").getAsJsonArray();


				SimpleDateFormat parser=new SimpleDateFormat("yyyy/MM/dd");

				for(JsonElement e: e2) {
					Comparendo c = new Comparendo();
					c.OBJECTID = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();

					String s = e.getAsJsonObject().get("properties").getAsJsonObject().get("FECHA_HORA").getAsString();	
					c.FECHA_HORA = parser.parse(s); 

					c.MEDIO_DETE = e.getAsJsonObject().get("properties").getAsJsonObject().get("MEDIO_DETE").getAsString();
					c.CLASE_VEHI = e.getAsJsonObject().get("properties").getAsJsonObject().get("CLASE_VEHI").getAsString();
					c.TIPO_SERVI = e.getAsJsonObject().get("properties").getAsJsonObject().get("TIPO_SERVI").getAsString();
					c.INFRACCION = e.getAsJsonObject().get("properties").getAsJsonObject().get("INFRACCION").getAsString();
					c.DES_INFRAC = e.getAsJsonObject().get("properties").getAsJsonObject().get("DES_INFRAC").getAsString();	
					c.LOCALIDAD = e.getAsJsonObject().get("properties").getAsJsonObject().get("LOCALIDAD").getAsString();

					c.longitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
							.get(0).getAsDouble();

					c.latitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
							.get(1).getAsDouble();

					enqueue(c);
				}
			}

			catch (FileNotFoundException | ParseException e) {
				e.printStackTrace();
			}

		}

	}

	public Comparable[] copiarComparendos()
	{
		Comparable<Comparendo>[] copiaComp = new Comparable[cola.darTamano()];
		Nodo<Comparendo> x = null;

		for(int i = 0; i < cola.darTamano(); i++)
		{
			x = cola.darPrimero();
			copiaComp[i] =(Comparable<Comparendo>) x.getActual();
			x = x.getSiguiente();
		}
		return copiaComp;		

	}


	public double[] darMinimax()
	{
		double[] mini = new double[4];

		Nodo<Comparendo> es = cola.primero;
		double lo1 = es.getActual().longitud;
		double la1 = es.getActual().latitud;
		double lo2 = es.getActual().longitud;
		double la2 = es.getActual().latitud;
		while(es!=null){
			if(es.getActual().longitud > lo1)
			{
				lo1 = es.getActual().longitud;
				mini[0] = lo1;
			}
			else if(es.getActual().longitud < lo2){
				mini[1] = lo2;
				lo2 = es.getActual().longitud;
			}
			if(es.getActual().latitud > la1)
			{
				la1 = es.getActual().latitud;
				mini[2] = la1;
			}
			else if(es.getActual().latitud < la2){
				mini[3] = la2;
				la2 = es.getActual().latitud;
			}
			es = es.getSiguiente();
		}
		return mini;
	}




	public Comparendo MostrarCompMayorOBJECTID()
	{
		Comparendo mayor = null;
		int idMayor = 0;

		Nodo<Comparendo> es = darPrimero();
		while(es!=null) {
			if(es.getActual().OBJECTID>idMayor) {
				idMayor = es.getActual().OBJECTID;
				mayor = es.getActual();
			}
			es = es.getSiguiente();
		}


		return mayor;
	}
	// Parte 1A

	public String buscarPorLocalidad(String Localidad)
	{

		Nodo<Comparendo> es = darPrimero();
		while(es != null)
		{
			if(es.getActual().LOCALIDAD.equalsIgnoreCase(Localidad)) return es.getActual().toString();
			es = es.getSiguiente();
		}
		return "No existe información al respecto";
	}


	// Parte 2A
	public Comparendo[] buscarPorFecha(Date fecha)
	{
		Cola<Comparendo> porFecha = new Cola<Comparendo>();
		Nodo<Comparendo> es = cola.primero;
		while(es != null)
		{
			if(es.getActual().FECHA_HORA.compareTo(fecha) == 0)
			{
				porFecha.enqueue(es.getActual());
			}
			es = es.getSiguiente();
		}
		int x = 0;
		Comparendo[] hola = new Comparendo[porFecha.darTamano()];
		while(x !=  porFecha.darTamano()){
			hola[x] = porFecha.dequeue().getActual();
			x++;
		}
		//ORDENAMIENTO
		Merge.sort((Comparable[]) hola);
		return hola;
	}

	// Parte 3A
	public String compararPorCodigoInfraccion(String FECHA1, String FECHA2)
	{
		

	}

	// Parte 1B
	public String buscarPorInfraccion(String Infraccion)
	{

		Nodo<Comparendo> es = darPrimero();
		while(es != null)
		{
			if(es.getActual().INFRACCION.equalsIgnoreCase(Infraccion)) return es.getActual().toString();
			es = es.getSiguiente();
		}
		return "No existe información al respecto";
	}

	// Parte 2B

	public String consultarPorInfraccion(String Infraccion)
	{
		String rta = "";
		Cola<Comparendo> porInfra = new Cola<Comparendo>();
		Nodo<Comparendo> es = cola.primero;
		Nodo<Comparendo> a = porInfra.primero;
		while(es != null)
		{
			if(es.getActual().INFRACCION.equalsIgnoreCase(Infraccion))
			{
				porInfra.enqueue(es.getActual());
			}
			es = es.getSiguiente();
		}
		Comparendo[]  x= new Comparendo[porInfra.darTamano()]; 
		es = porInfra.primero;
		for (int i = 0; i < porInfra.darTamano(); i++) {
			x[i]=es.getActual();
			es = es.getSiguiente();
		}
		//ORDENAMIENTO SEGUN FECHA HORA
		Merge.sort(x);
		for (int h = 0; h < x.length; h++) {
			rta += x[h].toString()+"\n";
		}
		return rta;
	}

	// Parte 3B 
	public void compararPorServi()
	{


	}





	// Parte 1C

	public String mostrarCompInfLoc(String localidad, Date inicial, Date fechaFinal)
	{
		String mandar = "";
		Cola<Comparendo> porLocalidad = new Cola<Comparendo>();
		Nodo<Comparendo> es = cola.primero;
		while(es != null)
		{
			if(es.getActual().LOCALIDAD.compareTo(localidad) == 0)
			{
				porLocalidad.enqueue(es.getActual());
			}
			es = es.getSiguiente();
		}
		Nodo<Comparendo> s = porLocalidad.darPrimero();
		Cola<Comparendo> l = new Cola<Comparendo>();
		while(s != null)
		{
			if(s.getActual().FECHA_HORA.compareTo(inicial) >= 0 && s.getActual().FECHA_HORA.compareTo(fechaFinal) <= 0)
			{
				l.enqueue(s.getActual());
			}
			s = s.getSiguiente();
		}

		Nodo<Comparendo> p = l.darPrimero();
		int x = 0;
		String [] arreglo = new String[l.darTamano()]; 

		for(int i = 0; i < l.darTamano(); i++)
		{
			arreglo[i] = p.getActual().INFRACCION;
			p = p.getSiguiente();
		}
		Merge.sort(arreglo);
		String cca = arreglo[0];
		int contador = 1;
		for (int i = 1; i < arreglo.length; i++) {
			if(arreglo[i].equals(cca)) contador ++;
			else 
			{
				mandar = "\n" + cca + "      | " +contador;
				cca = arreglo[i];

			}
		}
		return mandar;
	}

	// Parte 2C

	public void  consultarNCods(int n, Date inicial, Date fechaFinal )
	{
		Cola<Comparendo> porLocalidad = new Cola<Comparendo>();

		Nodo<Comparendo> es = cola.primero;
		while(es != null)
		{
			if(es.getActual().FECHA_HORA.compareTo(inicial) >= 0 && es.getActual().FECHA_HORA.compareTo(fechaFinal) <= 0)
			{
				porLocalidad.enqueue(es.getActual());
			}
			es = es.getSiguiente();
		}

		String[] copiaComp = new String[cola.darTamano()];
		Nodo<Comparendo> s = porLocalidad.primero;

		for(int i = 0; i < porLocalidad.darTamano(); i++)
		{

			copiaComp[i] = s.getActual();
			s = s.getSiguiente();
		}
		//ORDENAMIENTO SEGUN POR INFRACCION
		Merge.sort(copiaComp);
		String cca = copiaComp[0];
		int contador = 0;
		for (int i = 1; i < copiaComp.length; i++) {
			
			if(copiaComp[i].equals(n)){
				
				contador ++;
			}
			else 
			{

			}
		}
	}

//	public static class Comparador{
//		public static Comparator<String> strings=
//				(i1, i2) -> i1.compareTo(i2);
//				public static Comparator<Comparendo> dates=
//						(i1, i2) -> i1.FECHA_HORA.compareTo(i2.FECHA_HORA);
//						public static Comparator<Comparendo> infraccion=
//								(i1, i2) -> i1.INFRACCION.compareTo(i2.INFRACCION);

	//							public static Comparator<Comparendo> infraccionAlReves=
	//									(i1, i2) -> i2.INFRACCION.compareTo(i1.INFRACCION);
	//}



}












