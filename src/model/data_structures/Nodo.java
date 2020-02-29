package model.data_structures;



public class Nodo <T> {

	private T actual;

	private Nodo<T> siguiente;

	public Nodo(T dato)
	{
		actual = dato;
		siguiente = null;
	}

	public T getActual() {
		return actual;
	}

	public void setActual(T actual) {
		this.actual = actual;
	}

	public Nodo<T> getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(Nodo<T> siguiente) {
		this.siguiente = siguiente;
	}

}
