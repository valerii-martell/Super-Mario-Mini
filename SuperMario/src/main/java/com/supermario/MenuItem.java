package com.supermario;


import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Created by KirinTor on 13.05.2016.
 */
public class MenuItem extends StackPane{
	
	public boolean isChoos=false;
	public Rectangle bg = new Rectangle(200,20,Color.WHITE);
	public FillTransition st = new FillTransition(Duration.seconds(0.5),bg);
	Text text;
	
    public  MenuItem(String name){
        bg.setOpacity(0.5);

        text = new Text(name);
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Arial",FontWeight.BOLD,14));

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg,text);
        setOnMouseEntered(event -> {
            st.setFromValue(Color.DARKGRAY);
            st.setToValue(Color.DARKGOLDENROD);
            st.setCycleCount(Animation.INDEFINITE);
            st.setAutoReverse(true);
            st.play();
        });
        setOnMouseExited(event -> {
        	if (!isChoos){
	            st.stop();
	            bg.setFill(Color.WHITE);
        	}
        });
    }

	/**
	 * @return the text
	 */
	public Text getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(Text text) {
		this.text = text;
	}
}