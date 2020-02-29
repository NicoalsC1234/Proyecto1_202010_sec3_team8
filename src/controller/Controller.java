package controller;

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
					view.printMessage("deme localidad o lo mato");
					String localidad = reader.next();
					Comparendo x = modelo.buscarPorLocalidad(localidad);
					if(x !=null)
					{
						view.printMessage("El comparendo es" + x.toString());
					}
					else view.printMessage("No hay una infraccion con esa localidad");

				} 
			}
		}


		catch (InputMismatchException e) {

			run();

		}

	}

}