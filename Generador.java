import java.io.*;

public abstract class Generador implements Graficable{

	protected Palabra[] palabras;
	protected String[] listaNegra;
	protected String texto;
	protected int limite;
	
	//signos en variables..
	//son public?
	//falta el void de el metodo?
	//falta el limite?
	
	public abstract void iniciar(File texto, File listaNegra, int limite);
	
	public abstract void llenarArreglo();
	
	/*public Scene crearNube(){
	}*/
}
