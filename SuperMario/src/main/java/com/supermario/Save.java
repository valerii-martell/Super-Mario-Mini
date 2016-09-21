package com.supermario;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Save {
	
	private double translateX;
    private double translateY;
    private double gameRootLayoutX;
    private double backgroundLayoutX;
    private int levelNumber;
    private int levelCount;
    private int characterType;
    private double volume;
    private int score0;
    private int score1;
    private int score2;
    
    public Save(){
		setTranslateX(0);
		setTranslateY(400);
		setGameRootLayoutX(0);
		setBackgroundLayoutX(0);
		setLevelNumber(0);
		setLevelCount(1);
		setCharacterType(0);
		setVolume(0.5);
		setScore0(0);
		setScore1(0);
		setScore2(0);
    }
	public Save(double translateX, 
			double translateY, 
			double gameRootLayoutX, 
			double backgroundLayoutX,
			int levelNumber,
    		int levelCount,
    		int characterType,
    		double volume,
    		int score0,
    		int score1,
    		int score2){
		setTranslateX(translateX);
		setTranslateY(translateY);
		setGameRootLayoutX(gameRootLayoutX);
		setBackgroundLayoutX(backgroundLayoutX);
		setLevelNumber(levelNumber);
		setLevelCount(levelCount);
		setCharacterType(characterType);
		setVolume(volume);
		setScore0(score0);
		setScore1(score1);
		setScore2(score2);
	}

	/**
	 * @return the translateX
	 */
	public double getTranslateX() {
		return translateX;
	}

	/**
	 * @param translateX the translateX to set
	 */
	@XmlElement
	public void setTranslateX(double translateX) {
		this.translateX = translateX;
	}

	/**
	 * @return the translateY
	 */
	public double getTranslateY() {
		return translateY;
	}

	/**
	 * @param translateY the translateY to set
	 */
	@XmlElement
	public void setTranslateY(double translateY) {
		this.translateY = translateY;
	}

	/**
	 * @return the gameRootLayoutX
	 */
	public double getGameRootLayoutX() {
		return gameRootLayoutX;
	}

	/**
	 * @param gameRootLayoutX the gameRootLayoutX to set
	 */
	@XmlElement
	public void setGameRootLayoutX(double gameRootLayoutX) {
		this.gameRootLayoutX = gameRootLayoutX;
	}

	/**
	 * @return the backgroundLayoutX
	 */
	public double getBackgroundLayoutX() {
		return backgroundLayoutX;
	}

	/**
	 * @param backgroundLayoutX the backgroundLayoutX to set
	 */
	@XmlElement
	public void setBackgroundLayoutX(double backgroundLayoutX) {
		this.backgroundLayoutX = backgroundLayoutX;
	}

	/**
	 * @return the levelNumber
	 */
	public int getLevelNumber() {
		return levelNumber;
	}

	/**
	 * @param levelNumber the levelNumber to set
	 */
	@XmlElement
	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	/**
	 * @return the levelCount
	 */
	public int getLevelCount() {
		return levelCount;
	}
	/**
	 * @param levelCount the levelCount to set
	 */
	@XmlElement
	public void setLevelCount(int levelCount){
		this.levelCount = levelCount;
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
	@XmlElement
	public void setCharacterType(int characterType) {
		this.characterType = characterType;
	}
	/**
	 * @return the volume
	 */
	public double getVolume() {
		return volume;
	}
	/**
	 * @param volume the volume to set
	 */
	@XmlElement
	public void setVolume(double volume) {
		this.volume = volume;
	}
	/**
	 * @return the score0
	 */
	public int getScore0() {
		return score0;
	}
	/**
	 * @param score0 the score0 to set
	 */
	@XmlElement
	public void setScore0(int score0) {
		this.score0 = score0;
	}
	/**
	 * @return the score1
	 */
	public int getScore1() {
		return score1;
	}
	/**
	 * @param score1 the score1 to set
	 */
	@XmlElement
	public void setScore1(int score1) {
		this.score1 = score1;
	}
	/**
	 * @return the score2
	 */
	public int getScore2() {
		return score2;
	}
	/**
	 * @param score2 the score2 to set
	 */
	@XmlElement
	public void setScore2(int score2) {
		this.score2 = score2;
	}	
}