import java.io.*;

public abstract class Generador implements Graficable{

	protected Palabra[] palabras;
	protected String texto;
	protected int limite;
	
	//signos en variables -no entiendo este comment

	public Generador(File texto, int limite) {
		this.text=archivoATexto(texto);
		this.limite=limite;
	}

	public String archivoATexto(File input) {
		//aqui se recibe el archivo y se regresa una string

	}
	
	public abstract void iniciar();
	
	public abstract void llenarArreglo();
	
	/*public Scene crearNube(){
	}*/
}
