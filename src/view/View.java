package view;

import model.logic.Modelo;

public class View 
{
	    /**
	     * Metodo constructor
	     */
	    public View()
	    {
	    	
	    }
	    
		public void printMenu()
		{
			System.out.println("1. Crear estructura de datos");
			System.out.println("2. Consultar por localidad");
			System.out.println("3. Consultar datos por fecha");
			System.out.println("5: Consultar por infraccion");
			
		}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
		public void printModelo(Modelo modelo)
		{
			System.out.print(modelo);
		}
}
