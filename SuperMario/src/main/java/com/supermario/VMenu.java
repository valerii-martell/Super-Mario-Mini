package com.supermario;


import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

/**
 * Created by KirinTor on 13.05.2016.
 */
public class VMenu extends VBox{
	public VMenu(){
        setSpacing(15);
        setTranslateY(100);
        setTranslateX(50);
    }
	public VMenu(Slider slider, MenuItem back){
        setSpacing(15);
        setTranslateY(100);
        setTranslateX(50);
        getChildren().addAll(slider,back);
    }
	public VMenu(HMenu resultMenu, MenuItem replayGame, MenuItem mainMenu){
        setSpacing(15);
        setTranslateY(50);
        setTranslateX(350);
        if (Game.score==1){
        	replayGame.setTranslateX(150);
            replayGame.setTranslateY(150);
            mainMenu.setTranslateX(150);
            mainMenu.setTranslateY(150);
        	resultMenu.setTranslateX(getTranslateX()-200);
        }
        if (Game.score==2){
        	replayGame.setTranslateX(100);
            replayGame.setTranslateY(150);
            mainMenu.setTranslateX(100);
            mainMenu.setTranslateY(150);
        	resultMenu.setTranslateX(getTranslateX()-250);
        }
        if (Game.score==3){
        	replayGame.setTranslateX(50);
            replayGame.setTranslateY(150);
            mainMenu.setTranslateX(50);
            mainMenu.setTranslateY(150);
        	resultMenu.setTranslateX(getTranslateX()-300);
        }
        if (Game.score==4){
        	replayGame.setTranslateX(0);
            replayGame.setTranslateY(150);
            mainMenu.setTranslateX(0);
            mainMenu.setTranslateY(150);
        	resultMenu.setTranslateX(getTranslateX()-350);
        }
        if (Game.score==5){
        	replayGame.setTranslateX(-100);
            replayGame.setTranslateY(150);
            mainMenu.setTranslateX(-100);
            mainMenu.setTranslateY(150);
        	resultMenu.setTranslateX(getTranslateX()-450);
        }
               
        getChildren().setAll(resultMenu,replayGame,mainMenu);
    }
	public VMenu(HMenu resultMenu, MenuItem replayGame, MenuItem nextLevel, MenuItem mainMenu){
        setSpacing(15);
        setTranslateY(50);
        setTranslateX(350);
        if (Game.score==1){
        	replayGame.setTranslateX(150);
            replayGame.setTranslateY(150);
            nextLevel.setTranslateX(150);
            nextLevel.setTranslateY(150);
            mainMenu.setTranslateX(150);
            mainMenu.setTranslateY(150);
        	resultMenu.setTranslateX(getTranslateX()-200);
        }
        if (Game.score==2){
        	replayGame.setTranslateX(100);
            replayGame.setTranslateY(150);
            nextLevel.setTranslateX(100);
            nextLevel.setTranslateY(150);
            mainMenu.setTranslateX(100);
            mainMenu.setTranslateY(150);
        	resultMenu.setTranslateX(getTranslateX()-250);
        }
        if (Game.score==3){
        	replayGame.setTranslateX(50);
            replayGame.setTranslateY(150);
            nextLevel.setTranslateX(50);
            nextLevel.setTranslateY(150);
            mainMenu.setTranslateX(50);
            mainMenu.setTranslateY(150);
        	resultMenu.setTranslateX(getTranslateX()-300);
        }
        if (Game.score==4){
        	replayGame.setTranslateX(0);
            replayGame.setTranslateY(150);
            nextLevel.setTranslateX(0);
            nextLevel.setTranslateY(150);
            mainMenu.setTranslateX(0);
            mainMenu.setTranslateY(150);
        	resultMenu.setTranslateX(getTranslateX()-350);
        }
        if (Game.score==5){
        	replayGame.setTranslateX(-100);
            replayGame.setTranslateY(150);
            nextLevel.setTranslateX(-100);
            nextLevel.setTranslateY(150);
            mainMenu.setTranslateX(-100);
            mainMenu.setTranslateY(150);
        	resultMenu.setTranslateX(getTranslateX()-450);
        }
               
        getChildren().setAll(resultMenu,replayGame,nextLevel,mainMenu);
    }
    public VMenu(MenuItem...items){
        setSpacing(15);
        setTranslateY(100);
        setTranslateX(50);
        for(MenuItem item : items){
            getChildren().addAll(item);
        }
    }
	public VMenu(MenuItem skin1, 
			MenuAnimItem skin1anim, 
			MenuItem skin2, 
			MenuAnimItem skin2anim, 
			MenuItem skin3,
			MenuAnimItem skin3anim, 
			MenuItem skinBack) {
		        setSpacing(15);
		        setTranslateY(100);
		        setTranslateX(50);
		        getChildren().addAll(skin1,skin1anim,skin2,skin2anim,skin3,skin3anim,skinBack);
		        setAlignment(Pos.CENTER);
	}
	public VMenu(HMenu...hMenus){
        setSpacing(15);
        setTranslateY(0);
        setTranslateX(0);
        for(HMenu HMenu : hMenus){
            getChildren().addAll(HMenu);
        }
    }
	
	public void add(Object object){
		getChildren().add((Node) object);
	}
	public void add(MenuItem item){
		getChildren().add(item);
	}
}
