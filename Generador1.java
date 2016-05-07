import java.io.*;

public class Generador1 extends Generador{

	public Generador1(File texto int limite) {
		super(File texto, int limite);
	}

	//no hay estado.
	@Override
	public void iniciar(){ //no tiene argumentos porque todo está en el estado
		llenarArreglo();
		//ahora iniciar UI


	}

	@Override
	public void llenarArreglo(){
	}
}
