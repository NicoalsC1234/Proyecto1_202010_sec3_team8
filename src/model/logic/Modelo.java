package model.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public Comparendo buscarPorLocalidad(String Localidad)
{

	Nodo<Comparendo> es = darPrimero();
	while(es != null)
	{
		if(es.getActual().LOCALIDAD.equalsIgnoreCase(Localidad)) return es.getActual();
		es = es.getSiguiente();
	}
	return null;
}

}



