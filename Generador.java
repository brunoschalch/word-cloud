import java.io.*;

public abstract class Generador implements Graficable{

	protected Palabra[] palabras;

	protected String texto;
	protected int limite;
	
	//signos en variables..
	//son public?
	//falta el void de el metodo?
	//falta el limite?

	public Generador(File texto, File listaNegra, int limite) {
		this.text=texto;
		this.listaNegra=listaNegra;
		this.limite=limite;
	}
	
	public abstract void iniciar();
	
	public abstract void llenarArreglo();
	
	/*public Scene crearNube(){
	}*/
}
