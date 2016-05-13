import java.io.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class Generador2 extends Generador{
	private String[] listaNegra;


	public Generador2(File texto, File listaNegra, int limite, Color colorPalabra, Color colorFondo) {
		super(texto, limite, colorPalabra, colorFondo);
		this.listaNegra=archivoAArreglo(listaNegra);

	}


	@Override
	public void llenarArreglo(){ //aqui tambien se van generando las labels y se guardan en el estado de cada palabra, gracias a la agregacion se pueden recuperar con getLabel y usar en el UI en esta clase

		String[] textoSeparado = texto.toLowerCase().split("\\W+"); //pasa todo a minusculas y despues busca todas las no palabras con W y con + junta los que estan seguidos para separador, ver http://regexr.com/3dcpk

		if (palabras==null) {
			//agregar el primer elemento manualmente
			Label etiqueta = new Label(textoSeparado[0]);
			palabras = new Palabra[1];
			palabras[0] = new Palabra(textoSeparado[0], etiqueta);
		}

			for (int i=1; i<textoSeparado.length; i++) { //importante el uno para no contar la primera palabra dos veces
				String buscar= textoSeparado[i];
				if (!checarListaNegra(buscar)) { //solo se hace la operacion por cada palabra si no esta en la lista negra
					boolean yaExiste = false;
					for (Palabra palabraActual : palabras) {
						if (palabraActual.getContenido().equals(buscar)) {
							yaExiste = true;
							palabraActual.actualizarFrecuencia();
						//	System.out.println("se actualizo"+ palabraActual.getContenido());
						}
					}
					if (!yaExiste) {
						//no se encontro la palabra (String buscar) en el gran arreglo de palabras entonces hay que crearla
						agregarPalabra(buscar);
					}
				}
			}
		}




	public boolean checarListaNegra(String buscar) {
		boolean coincidencia=false;
		for (String palabra : listaNegra) {
			if(palabra.equals(buscar)) {
				coincidencia=true;
			}
		}
		return coincidencia;
	}

	public String[] archivoAArreglo(File input) {

		String lectura="";
		try {
					FileReader fileReader = new FileReader(input);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			while((line = reader.readLine()) != null) {
				lectura+=line;
			}
			reader.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}

		String[] regresar = lectura.split("\\W+");
		return regresar;
	}

}
