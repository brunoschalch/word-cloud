import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public interface Graficable{

	public abstract Scene crearNube(); //cambiar to lower case
	
	//methods of an interface are implicitly public (pero igual no esta de mas ponerlo para claridad de que entendemos)
	//hay que poner los signos de variables -que signos de variables?
}
