package com.supermario;

import javafx.geometry.Point2D;import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Created by KirinTor on 13.05.2016.
 */
public class Character extends Pane{
    Image marioImg = new Image(getClass().getResourceAsStream("mario.png"));
    ImageView imageView = new ImageView(marioImg);
    int count = 3;
    int columns = 3;
    int width = 16;
    int height = 16;
    public SpriteAnimation animation;
    public Point2D playerVelocity = new Point2D(0,0);
    private boolean canJump = true;
    int characterType;
    
    /**
     * Створює ігрового персонажа
     * 
     * @param Тип зовнішнього вигляду персонажа
     */
    public Character(int characterType){
    	this.characterType=characterType;
    	imageView.setFitHeight(Game.MARIO_SIZE);
        imageView.setFitWidth(Game.MARIO_SIZE);
    	if (characterType==0){
    		int offsetX=96;
    		int offsetY=33;
    		imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
            animation = new SpriteAnimation(this.imageView,Duration.millis(200),count,columns,offsetX,offsetY,width,height);
    	}
    	if (characterType==1){
    		int offsetX=96;
    		int offsetY=81;
    		imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
            animation = new SpriteAnimation(this.imageView,Duration.millis(200),count,columns,offsetX,offsetY,width,height);
    	}
    	if (characterType==2){
    		int offsetX=96;
    		int offsetY=225;
    		imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
            animation = new SpriteAnimation(this.imageView,Duration.millis(200),count,columns,offsetX,offsetY,width,height);
    	} 
    	if (characterType==3){
    		int offsetX=31;
    		int offsetY=25;
    		imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
            animation = new SpriteAnimation(this.imageView,Duration.millis(200),count,columns,offsetX,offsetY,width,height);
    	} 
        getChildren().addAll(this.imageView);
    }
    /**
     * Рух персонажа по осі ОХ
     * 
     * @param value of mooving
     */
    public void moveX(int value){
        boolean movingRight = value > 0;
        for(int i = 0; i<Math.abs(value); i++) {
            for (Node platform : Game.platforms) {
                if(this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (this.getTranslateX() + Game.MARIO_SIZE == platform.getTranslateX()){
                            this.setTranslateX(this.getTranslateX() - 1);
                            return;
                        }
                    } else {
                        if (this.getTranslateX() == platform.getTranslateX() + Game.BLOCK_SIZE) {
                            this.setTranslateX(this.getTranslateX() + 1);
                            return;
                        }
                    }
                }
            }
            this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));
        }
    }
    public void moveY(int value){
        boolean movingDown = value >0;
        for(int i = 0; i < Math.abs(value); i++){
            for(Block platform :Game.platforms){
                if(getBoundsInParent().intersects(platform.getBoundsInParent())){
                    if(movingDown){
                        if(this.getTranslateY()+ Game.MARIO_SIZE == platform.getTranslateY()){
                            this.setTranslateY(this.getTranslateY()-1);
                            canJump = true;
                            return;
                        }
                    }
                    else{
                        if(this.getTranslateY() == platform.getTranslateY()+ Game.BLOCK_SIZE){
                            this.setTranslateY(this.getTranslateY()+1);
                            playerVelocity = new Point2D(0,10);
                            return;
                        }
                    }
                }
            }
            this.setTranslateY(this.getTranslateY() + (movingDown?1:-1));
            if(this.getTranslateY()>640){
                this.setTranslateX(0);
                this.setTranslateY(400);
                Game.gameRoot.setLayoutX(0);
                new Sound ("falls.wav", Game.volume);
                if (Game.score>1){
                	Game.score--;
                }
            }
            if((this.getTranslateX()>9180.0)){
                Game.isFinish=true;
            }
        }
    }
    public void jumpPlayer(){
        if(canJump){
            playerVelocity = playerVelocity.add(0,-30);
            canJump = false;
            new Sound ("jump.wav", Game.volume);
        }
    }
	/**
	 * @return the characterType
	 */
	public int getCharacterType() {
		return characterType;
	}
	/**
	 * @param characterType the characterType to set
	 */
	public void setCharacterType(int characterType) {
		this.characterType = characterType;
	}
    
}