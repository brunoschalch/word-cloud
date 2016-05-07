public class Palabra {

	final private String contenido; //es final?
	private int frecuencia;
	private int x;
	private int y;
	private int fontSize=10; //en pt on en px? por defecto 10
	private int width;
	private int height;
	private Label label;
	
	//signos

	public Palabra(String contenido, Label label) { //al iniciar el objeto Palabra solo se sabe el contenido y con agregacion se define la label, despues se cambiara su frecuencia y se acomodara graficamente con x, y, width, height y fontSize
		this.contenido = contenido;
		this.label=label;
		frecuencia=1;
	}

	public String getContenido(){
		return contenido;
	}

	public void ActualizarFrecuencia() {
		//este metodo se llama cada vez que se encuentra esta palabra en el texto
		frecuencia++;
	}

	public void setCoordenadas(int x, int y) {
		this.x=x;
		this.y=y;
	}

	public void setFontSize(int fontSize) {
		this.fontSize=fontSize;
		label.setFont(new Font("Arial", fontSize));
		height=label.prefHeight(-1)
		width=label.prefWidth(-1)
	}

}
