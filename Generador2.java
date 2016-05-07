import java.io.*;

public class Generador2 extends Generador{
	protected String[] listaNegra;


	public Generador2(File texto, File listaNegra, int limite) {
		super(File texto, int limite);
		this.listaNegra=archivoATexto(listaNegra);
	}

	@Override
	public void iniciar(){ //no tiene argumentos porque todo está en el estado
		llenarArreglo();
		//ahora iniciar UI
	}

	@Override
	public void llenarArreglo(){

	}

}
