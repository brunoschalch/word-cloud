import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import com.sun.javafx.tk.Toolkit;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javafx.scene.text.TextBuilder;
import javafx.geometry.Bounds;

public class Palabra {

	final private String contenido; //es final?
	private int frecuencia;
	private int x=-2147483648; //este numero es la "señal" de que no se han determinado las coordenadas
	private int y=-2147483648; //este numero es la "señal" de que no se han determinado las coordenadas
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

	public void actualizarFrecuencia() {
		//este metodo se llama cada vez que se encuentra esta palabra en el texto
		frecuencia++;
	}

	public void setCoordenadas(int x, int y) {
		this.x=x;
		this.y=y;
	}

	public void setFontSize(int fontSize) {
		this.fontSize=fontSize;
		//fontSize=30;
		Font fuente = new Font("Consolas", fontSize);
		label.setFont(fuente);

	//	Bounds bounds = TextBuilder.create().text(contenido).font(fuente).build().getLayoutBounds();
	//	width=(int) bounds.getWidth();
	//	height=(int) bounds.getHeight();

		//width = (int)com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().computeStringWidth(contenido, fuente);
		//height = (int)com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(fuente).getLineHeight();
		//	width*=1+(0.1*contenido.length()); //con esto las palabras se enciman menos, sintoma de que las dos lineas de arriba algo mal tienen
		//height*=1.1;//con esto las palabras se enciman menos, sintoma de que las dos lineas de arriba algo mal tienen
		width=(fontSize/2)*contenido.length();
		width*=1.3;
		height=fontSize;


	}

	public String getContenido(){
		return contenido;
	}

	public int getFrecuencia() { return frecuencia; }

	public Label getLabel() { return label; }

	public int getX() {	return x; }

	public int getY(){ return y; }

	public int getWidth() { return width; }

	public int getHeight() { return  height; }

}
