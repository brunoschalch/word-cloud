import java.io.*;

public class Generador2 extends Generador{
	protected String[] listaNegra;


	public Generador2(File texto, File listaNegra, int limite) {
		super(File texto, int limite);
		this.listaNegra=listaNegra;
	}

	@Override
	public void iniciar(){ //no tiene argumentos porque todo está en el estado
	}

	@Override
	public void llenarArreglo(){
	}
}
