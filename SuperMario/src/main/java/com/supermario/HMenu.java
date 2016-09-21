package com.supermario;


import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * Created by KirinTor on 14.05.2016.
 */
public class HMenu extends HBox{
    public HMenu(MenuItem...items){
        setSpacing(15);
        setTranslateY(100);
        setTranslateX(50);
        for(MenuItem item : items){
            getChildren().addAll(item);
        }
    }
	public HMenu(MenuItem skin, MenuAnimItem skinAnim){
		setSpacing(15);
		setTranslateY(100);
		setTranslateX(50);
		getChildren().addAll(skin,skinAnim);
		setAlignment(Pos.CENTER);
	}
	public HMenu(MenuItem level, int score){
		setSpacing(5);
		setAlignment(Pos.CENTER);
		getChildren().add(level);
		if (score==0){
			
		}
		int gold=score;
		int gray=5-score;
        for(int i=0;i<gold;i++){
        	getChildren().add(new MenuImageItem(1));
        }
        for(int i=0;i<gray;i++){
        	getChildren().add(new MenuImageItem(2));
        }
        
	}	
	public HMenu(int result){
        setSpacing(15);
        setTranslateY(100);
        setTranslateX(50);
        setAlignment(Pos.CENTER);
        for(int i=0;i<result;i++){
        	getChildren().add(new MenuImageItem(0));
        }
	}	
	public void add(MenuItem item){
		getChildren().add(item);
	}
}
