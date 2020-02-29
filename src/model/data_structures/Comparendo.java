package model.data_structures;

import java.util.Date;

public class Comparendo {
	
	public int OBJECTID;

	public Date FECHA_HORA;
	
	public String MEDIO_DETE;
	
	public String CLASE_VEHI;
	
	public String TIPO_SERVI;
	
	public String INFRACCION;
	
	public String DES_INFRAC;
	
	public String LOCALIDAD;
	
	public double longitud;
	
	public double latitud;
	
	public int compareTo(Comparendo comparendo )
	{
		return 0;
		
	}
	
	public String toString()
	{
		return "Los datos del comparendo son : " + OBJECTID + ", " + FECHA_HORA + ", " + MEDIO_DETE + ", " + CLASE_VEHI + ", " + TIPO_SERVI + ", "
	    + INFRACCION + ", " + DES_INFRAC + ", " + LOCALIDAD;
	}
	
}
