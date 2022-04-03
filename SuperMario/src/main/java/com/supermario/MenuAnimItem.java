package com.supermario;


import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class MenuAnimItem extends StackPane{
	
	public MenuAnimItem(Character player){    
	    
		player.setTranslateX(30);
	    player.setTranslateY(10);
	    if (player.getCharacterType()!=3){
	    	player.animation.play();
	    }else{
	    	player.animation.stop();
	    }
        
        setAlignment(Pos.CENTER);
        getChildren().addAll(player);
	}	
}
