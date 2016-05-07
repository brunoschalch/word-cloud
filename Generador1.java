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
	public void llenarArreglo(){ //aqui tambien se van generando las labels y se guardan en el estado de cada palabra, gracias a la agregacion se pueden recuperar con getLabel y usar en el UI en esta clase
		//importante ordenar el arreglo de palabra mas usada a menos usada
	}
}
