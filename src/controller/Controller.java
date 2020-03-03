package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.data_structures.Comparendo;
import model.logic.Modelo;
import sun.util.BuddhistCalendar;
import view.View;

public class Controller{

	private Modelo modelo;

	private View view;

	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
	}

	public void run() throws InputMismatchException {

		try {

			Scanner reader = new Scanner(System.in);

			boolean end = false;



			while (!end) {

				view.printMenu();

				int option = reader.nextInt();
				switch (option) {



				case 1:
					if(modelo.darTamano()==0){
						modelo.cargarDatos();
						view.printMessage("Se ha creado");
						view.printMessage("El numero de datos leidos es " + modelo.darTamano());
						Comparendo buscado = (Comparendo) modelo.MostrarCompMayorOBJECTID();
						view.printMessage(buscado.toString());
						view.printMessage("El miramax esta delimitado por las latitudes, " + modelo.darMinimax()[2] + " y " + modelo.darMinimax()[3] + ", y las longitudes " + modelo.darMinimax()[0] + " y " + modelo.darMinimax()[1]);
					}
					else view.printMessage("Los datos ya estan cargados");
					break;
				case 2:
					view.printMessage("Escribir localidad");
					String localidad = reader.next();
					String x = modelo.buscarPorLocalidad(localidad);
					view.printMessage(x);
				case 3:
					view.printMessage("Escribir la fecha en formato: dd/mm/aaaa");
					try {
						Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(reader.next());
						Comparendo[] datos = modelo.buscarPorFecha(date1);
						if (datos.length == 0) view.printMessage("No hay comparendos en ese dia");
						else 
						{
							for (int i = 0; i < datos.length; i++) {
							view.printMessage(datos[i].toString());
						}
						}
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				case 5:
					view.printMessage("Escribir infraccion");
					String infre = reader.next();
					String y = modelo.buscarPorInfraccion(infre);
					view.printMessage(y);
				case 6:
					view.printMessage("Escribir infraccion");
					String inf= reader.next();
					String ye = modelo.consultarPorInfraccion(inf);
					view.printMessage(ye);
				case 8:
					view.printMessage("Dar localidad");
					String datos = reader.next();
					view.printMessage("Dar primera fecha");
					String inicial = reader.next();
					view.printMessage("Dar fecha final");
					String
					
					
				} 
			}
		}


		catch (InputMismatchException e) {

			run();

		}

	}

}