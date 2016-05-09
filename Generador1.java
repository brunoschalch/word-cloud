import java.io.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Generador1 extends Generador{

	public Generador1(File texto int limite) {
		super(File texto, int limite);
	}

	@Override
	public void llenarArreglo(){ //aqui tambien se van generando las labels y se guardan en el estado de cada palabra, gracias a la agregacion se pueden recuperar con getLabel y usar en el UI en esta clase
		//importante ordenar el arreglo de palabra mas usada a menos usada
		String[] textoSeparado = texto.toLowerCase().split("\\W+"); //pasa todo a minusculas y despues busca todas las no palabras con W y con + junta los que estan seguidos para separador, ver http://regexr.com/3dcpk

			if (palabras!=null) {
				for (String buscar : textoSeparado) {
					boolean yaExiste = false;
					for (Palabra palabraActual : palabras) {
						if (palabraActual.getContenido().equals(buscar)) {
							yaExiste=true;
							palabraActual.actualizarFrecuencia();
						}
					}
					if (!yaExiste) {
						//no se encontro la palabra en el gran arreglo de palabras entonces hay que crearla
					}
				}
			}

	}


}
