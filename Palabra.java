public class Palabra {

	final private String contenido; //es final?
	private int frecuencia;
	private int x;
	private int y;
	private int fontSize;
	private int width;
	private int height;
	
	//signos

	public Palabra(String contenido, int frecuencia, int x, int y, int fontSize, int width, int height) {
		this.contenido = contenido;
		this.frecuencia = frecuencia;
		this.x = x;
		this.y = y;
		this.fontSize = fontSize;
		this.width = width;
		this.height = height;
	}

	public String getContenido(){
		return contenido;
	}
	
	//tu método mágico aquí

}
