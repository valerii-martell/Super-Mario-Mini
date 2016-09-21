package com.supermario;


import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class MenuImageItem extends StackPane{

	public static final int IMG_SIZE1 = 128;
	public static final int IMG_SIZE2 = 16;
	
	Image Img;
    ImageView ImgV;
	
	public MenuImageItem(int type){
		if (type==0){
			Img = new Image(getClass().getResourceAsStream("star1.png")); //url    
			ImgV = new ImageView(Img);
			ImgV.setFitWidth(IMG_SIZE1);
			ImgV.setFitHeight(IMG_SIZE1);	
		}
		if (type==1){
			Img = new Image(getClass().getResourceAsStream("star1.png")); //url    
			ImgV = new ImageView(Img);
			ImgV.setFitWidth(IMG_SIZE2);
			ImgV.setFitHeight(IMG_SIZE2);	
		}
		if (type==2){
			Img = new Image(getClass().getResourceAsStream("star2.png")); //url    
			ImgV = new ImageView(Img);
			ImgV.setFitWidth(IMG_SIZE2);
			ImgV.setFitHeight(IMG_SIZE2);  
		}	    
        setAlignment(Pos.CENTER);
        getChildren().add(ImgV);
	}	
}
