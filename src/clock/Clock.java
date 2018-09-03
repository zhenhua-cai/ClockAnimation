package clock;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Clock extends Application{


	public void start(Stage stage){
		ClockPane pane=new ClockPane();
		//create handler to handler  clock animation
		EventHandler<ActionEvent> handler=e->{
			pane.setTime();
		};
		//create the animation
		Timeline time=new Timeline(new KeyFrame(Duration.millis(1000),handler));
		time.setCycleCount(Timeline.INDEFINITE);
		time.play();

		Scene scene=new Scene(pane,500,500);
		stage.setScene(scene);
		stage.show();
	}
	private class ClockPane extends Pane{
		int minute;
		int hour;
		int second;
		Calendar calendar;
		public ClockPane(){	
			//initialize the clock pane to current time.
			setTime();
		}
		/**
		 * set the current time and repaint the clock pane.
		 */
		public void setTime(){
			calendar=new GregorianCalendar();
			minute=calendar.get(Calendar.MINUTE);
			hour=calendar.get(Calendar.HOUR_OF_DAY);
			second=calendar.get(Calendar.SECOND);
			paint();
		}
		/**
		 * paint the clock pane.
		 */
		public void paint(){
			double centerX=getWidth()/2,centerY=getHeight()/2,radius=(getWidth()+getHeight())/6;
			
			Circle circle=new Circle();
			circle.setCenterX(centerX);
			circle.setCenterY(centerY);
			circle.setRadius(radius);
			circle.setStroke(Color.BLACK);
			circle.setStrokeWidth(7);
			circle.setFill(null);
			
			double minl=radius*0.6;
			double md=(minute+second/60.0)*2*Math.PI/60;
			Line min=new Line();
			min.setStrokeWidth(3);
			min.setStartX(centerX);
			min.setStartY(centerY);
			min.setEndX(centerX+minl*Math.sin(md));
			min.setEndY(centerY-minl*Math.cos(md));
			
			
			double secl=radius*0.8;
			double sd=(second)*2*Math.PI/60;
			Line sec=new Line();
			sec.setStartX(centerX);
			sec.setStartY(centerY);
			sec.setEndX(centerX+secl*Math.sin(sd));
			sec.setEndY(centerY-secl*Math.cos(sd));
			
			double hourl=radius*0.4;
			double hd=(hour%12+minute/60.0+second/3600.0)*2*Math.PI/12;
			Line hou=new Line();
			hou.setStrokeWidth(5);
			hou.setStartX(centerX);
			hou.setStartY(centerY);
			hou.setEndX(centerX+hourl*Math.sin(hd));
			hou.setEndY(centerY-hourl*Math.cos(hd));
			
			Text text=new Text(hour+":"+minute+":"+second);
			text.setX(centerX-60);
			text.setFont(Font.font("Courier",FontWeight.BOLD,FontPosture.REGULAR,30));
			text.setY(centerY+radius+36);
			getChildren().clear();
			getChildren().addAll(circle,min,sec,hou,text);
		}
		public void setWidth(double w){
			super.setWidth(w);
			paint();
		}
		public void setHeight(double h){
			super.setHeight(h);
			paint();
		}
	}
	public static void main(String[]args){
		launch(args);
	}
}

