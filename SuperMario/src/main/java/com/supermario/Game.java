package com.supermario;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import  javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Created by KirinTor on 13.05.2016.
 */
public class Game extends Application {
    
	public static ArrayList<Block> platforms = new ArrayList<>();
    private HashMap<KeyCode,Boolean> keys = new HashMap<>();
    
    Image backgroundImg = new Image(getClass().getResourceAsStream("background.png"));
    Image menuImg = new Image(getClass().getResourceAsStream("menu.jpg"));
    Image blocksImg = new Image(getClass().getResourceAsStream("blocks.png"));
    ImageView backgroundIV;
    public static final int BLOCK_SIZE = 45;
    public static final int MARIO_SIZE = 40;

    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();

    public Character player;
    public Stage primaryStage;
    Scene scene = new Scene(appRoot,1200,620);
    
    private int levelWidth;
    private boolean isPause = false;
    public static boolean isFinish = false;
    public static boolean isRepeat = false;
    public static boolean isAgain = false;
    public static double volume;
    public static int score=5;
    
    Save save = new Save();
    
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
        	if(!isPause){
            update();               
        }}
    };
    
    private void initMenu(){
        backgroundIV = new ImageView(menuImg);
        backgroundIV.setFitHeight(620);
        backgroundIV.setFitWidth(1200);
        backgroundIV.setLayoutX(0);
        appRoot.getChildren().add(backgroundIV);
        
        Sound mainTheme = new Sound("menu.wav", save.getVolume());
        
        MenuItem newGame = new MenuItem("NEW GAME");
        MenuItem continueGame = new MenuItem("CONTINUE GAME");
        MenuItem loadGame = new MenuItem("LOAD GAME");
        MenuItem options = new MenuItem("OPTIONS");
        MenuItem exitGame = new MenuItem("EXIT");
        VMenu mainMenu = new VMenu(
                newGame,continueGame,loadGame,options,exitGame
        );
        MenuItem playerSkin = new MenuItem("PLAYER SKIN");
        MenuItem optionsVolume = new MenuItem("VOLUME");
        MenuItem optionsBack = new MenuItem("BACK");
        VMenu optionsMenu = new VMenu(
                playerSkin,optionsVolume,optionsBack
        );
        MenuBox menuBox = new MenuBox(mainMenu);
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5),menuBox);
        MenuItem skin1 = new MenuItem("MARIO");
        MenuAnimItem skin1anim = new MenuAnimItem(new Character(0));
        HMenu skin1Menu = new HMenu(skin1, skin1anim);
        MenuItem skin2 = new MenuItem("LUIGI");
        MenuAnimItem skin2anim = new MenuAnimItem(new Character(1));
        HMenu skin2Menu = new HMenu(skin2, skin2anim);
        MenuItem skin3 = new MenuItem("DEUTSCH");
        MenuAnimItem skin3anim = new MenuAnimItem(new Character(2));
        HMenu skin3Menu = new HMenu(skin3, skin3anim);
        MenuItem skinBack = new MenuItem("BACK");
        MenuAnimItem backAnim = new MenuAnimItem(new Character(3));
        HMenu skinBackMenu = new HMenu(skinBack, backAnim);
        VMenu skinMenu = new VMenu(
        		skin1Menu,skin2Menu,skin3Menu,skinBackMenu
        );
        Slider volumeSlider = new Slider(0.0,1.0,save.getVolume());
        volumeSlider.valueProperty().addListener((ov,old_val,new_val)->{
    		mainTheme.mediaPlayer.setVolume(new_val.doubleValue());
    		save.setVolume(mainTheme.mediaPlayer.getVolume());
        });
        MenuItem volumeBack = new MenuItem("BACK");
        VMenu volumeMenu = new VMenu(
        		volumeSlider,volumeBack
        );
        MenuItem gameStart = new MenuItem("START NEW GAME");
        MenuItem gameLevel = new MenuItem("CHOOSE LEVEL");
        MenuItem gameBack = new MenuItem("BACK");
        VMenu newGameMenu = new VMenu(
                gameStart,gameLevel,gameBack
        );
        VMenu chooseLevel = new VMenu();
        for (int i =1; i<=save.getLevelCount(); i++){
            int j=i;
        	int score=0;
        	MenuItem level = new MenuItem("LEVEL "+Integer.toString(i));
    		if (i==1){score=save.getScore0();}
    		if (i==2){score=save.getScore1();}
    		if (i==3){score=save.getScore2();}
        	level.setOnMouseClicked(event-> {
            	mainTheme.mediaPlayer.stop();
            	platforms.clear();
                gameRoot.getChildren().clear();
                appRoot.getChildren().clear();
            	ft.setFromValue(1);
                ft.setToValue(0);
                ft.setOnFinished(evt ->   menuBox.setVisible(false));
                ft.play();
                save.setLevelNumber(j-1);
            	initContent();
        		if (j==save.getLevelCount()){isAgain=false;}
            });
        	HMenu hMenu = new HMenu(level, score);
        	chooseLevel.add(hMenu);
        }
        
        newGame.setOnMouseClicked(event->menuBox.setVMenu(newGameMenu));
        loadGame.setOnMouseClicked(event-> {
            gameRoot.getChildren().clear();
            appRoot.getChildren().clear();
        	platforms.clear();
        	mainTheme.mediaPlayer.stop();
        	loadGame();
        	ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(evt ->  menuBox.setVisible(false));
            ft.play();
        	initContent();
        });
        continueGame.setOnMouseClicked(event-> {
        	mainTheme.mediaPlayer.stop();
            gameRoot.getChildren().clear();
            appRoot.getChildren().clear();
        	platforms.clear();
        	// Try to load last opened person file.
            File file = getPlayerFilePath();
            if (file != null) {
                loadPlayerDataFromFile(file);
            }
        	ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(evt ->   menuBox.setVisible(false));
            ft.play();
        	initContent();
        });
        options.setOnMouseClicked(event->menuBox.setVMenu(optionsMenu));
        exitGame.setOnMouseClicked(event-> System.exit(0));
        playerSkin.setOnMouseClicked(event->menuBox.setVMenu(skinMenu));
        optionsVolume.setOnMouseClicked(event->menuBox.setVMenu(volumeMenu));
        skin1.setOnMouseClicked(event-> {
        	skin1.isChoos=true; save.setCharacterType(0);
        	skin2.isChoos=false; skin2.st.stop(); skin2.bg.setFill(Color.WHITE);
        	skin3.isChoos=false; skin3.st.stop(); skin3.bg.setFill(Color.WHITE);
        });
        skin2.setOnMouseClicked(event-> {
        	skin1.isChoos=false; skin1.st.stop(); skin1.bg.setFill(Color.WHITE);
        	skin2.isChoos=true; save.setCharacterType(1);
        	skin3.isChoos=false; skin3.st.stop(); skin3.bg.setFill(Color.WHITE);
        });
        skin3.setOnMouseClicked(event-> {
        	skin1.isChoos=false; skin1.st.stop(); skin1.bg.setFill(Color.WHITE);
        	skin2.isChoos=false; skin2.st.stop(); skin2.bg.setFill(Color.WHITE);
        	skin3.isChoos=true; save.setCharacterType(2);
        });
        skinBack.setOnMouseClicked(event->menuBox.setVMenu(optionsMenu));
        volumeBack.setOnMouseClicked(event->menuBox.setVMenu(optionsMenu));
        optionsBack.setOnMouseClicked(event->menuBox.setVMenu(mainMenu));
        gameStart.setOnMouseClicked(event-> {
            gameRoot.getChildren().clear();
            appRoot.getChildren().clear();
        	platforms.clear();
        	mainTheme.mediaPlayer.stop();
        	ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(evt ->   menuBox.setVisible(false));
            ft.play();
            save.setLevelNumber(0);
            save.setTranslateX(0);
            save.setTranslateY(400);
            save.setGameRootLayoutX(0);
            save.setBackgroundLayoutX(0);
            save.setScore0(0);
            save.setScore1(0);
            save.setScore2(0);
            save.setLevelNumber(0);
            save.setLevelCount(1);
        	initContent();
        });
        gameLevel.setOnMouseClicked(event-> menuBox.setVMenu(chooseLevel));
        gameBack.setOnMouseClicked(event-> menuBox.setVMenu(mainMenu));
        appRoot.getChildren().addAll(menuBox);
        
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setOnFinished(evt ->   menuBox.setVisible(true));
        ft.play();
    }
    
    @SuppressWarnings("unused")
	private void initContent(){
    	volume=save.getVolume();
        gameRoot.getChildren().clear();
        appRoot.getChildren().clear();
        backgroundIV = new ImageView(backgroundImg);
        backgroundIV.setFitHeight(14*BLOCK_SIZE);
        backgroundIV.setFitWidth(212*BLOCK_SIZE);
        
        new Sound ("start.wav", save.getVolume());
        timer.start();
        
        levelWidth = LevelData.levels[save.getLevelNumber()][0].length()*BLOCK_SIZE;
        for(int i = 0; i < LevelData.levels[save.getLevelNumber()].length; i++){
            String line = LevelData.levels[save.getLevelNumber()][i];
            char[] chars = line.toCharArray();
            for(int j = 0; j < chars.length;j++){
                switch (chars[j]){
                    case '0':
                        break;
                    case '1':
                        Block platformFloor = new Block(blocksImg, Block.BlockType.PLATFORM, j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                    case '2':
                        Block brick = new Block(blocksImg, Block.BlockType.BRICK,j*BLOCK_SIZE,i*BLOCK_SIZE);
                        break;
                    case '3':
                        Block bonus = new Block(blocksImg, Block.BlockType.BONUS,j*BLOCK_SIZE,i*BLOCK_SIZE);
                        break;
                    case '4':
                        Block stone = new Block(blocksImg, Block.BlockType.STONE,j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                    case '5':
                        Block PipeTopBlock = new Block(blocksImg, Block.BlockType.PIPE_TOP,j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                    case '6':
                        Block PipeBottomBlock = new Block(blocksImg, Block.BlockType.PIPE_BOTTOM,j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                    case '*':
                        Block InvisibleBlock = new Block(blocksImg, Block.BlockType.INVISIBLE_BLOCK,j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                }
            }
        }
        player = new Character(save.getCharacterType());
        player.setTranslateX(save.getTranslateX());
        player.setTranslateY(save.getTranslateY());
        gameRoot.setLayoutX(save.getGameRootLayoutX());
        backgroundIV.setLayoutX(save.getBackgroundLayoutX());
        player.translateXProperty().addListener((obs,old,newValue)->{
            int offset = newValue.intValue();
            if(offset>640 && offset<levelWidth-640){
                gameRoot.setLayoutX(-(offset-640));
                backgroundIV.setLayoutX(-(offset-640));
            }
        });
        gameRoot.getChildren().add(player);
        MenuBox pauseMenuBox=initPause();
        appRoot.getChildren().addAll(backgroundIV,gameRoot,pauseMenuBox);
        
        FadeTransition ft = new FadeTransition(Duration.seconds(0.2),pauseMenuBox);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
            	if(!isPause){           		
            		ft.setFromValue(0);
            		ft.setToValue(1);
            		ft.setOnFinished(evt ->   pauseMenuBox.setVisible(true));
            		ft.play();
                    player.animation.stop();  
                    new Sound("pause.wav", save.getVolume());
            		isPause=true;
            	}else{
            		ft.setFromValue(1);
            		ft.setToValue(0);
            		ft.setOnFinished(evt ->   pauseMenuBox.setVisible(false));
            		ft.play();
            		isPause=false;
            	}
                }else{
                keys.put(event.getCode(), true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() != KeyCode.ESCAPE) {
                keys.put(event.getCode(), false);
                player.animation.stop();  
            }
        });
    }
    
    private MenuBox initPause(){
    	MenuItem resumeGame = new MenuItem("RESUME GAME");
        MenuItem saveGame = new MenuItem("SAVE GAME");
        MenuItem saveGameTo = new MenuItem("SAVE GAME TO...");
        MenuItem mainMenu = new MenuItem("MAIN MENU");
        MenuItem exitGame = new MenuItem("EXIT");
        VMenu pauseMenu = new VMenu(
                resumeGame,saveGame,saveGameTo,mainMenu,exitGame
        );
        MenuBox pauseMenuBox = new MenuBox(pauseMenu);
        pauseMenuBox.setVisible(false);

        FadeTransition ft = new FadeTransition(Duration.seconds(0.2),pauseMenuBox);
        resumeGame.setOnMouseClicked(event->{
        	ft.setFromValue(1);
    		ft.setToValue(0);
    		ft.setOnFinished(evt ->   pauseMenuBox.setVisible(false));
    		ft.play(); 
    		isPause=false;
        });
        saveGame.setOnMouseClicked(event->{
        		 save.setTranslateX(player.getTranslateX());
        		 save.setTranslateY(player.getTranslateY());
        		 save.setGameRootLayoutX(gameRoot.getLayoutX());
        		 save.setBackgroundLayoutX(backgroundIV.getLayoutX());
        		 save.setLevelNumber(save.getLevelNumber());
        	saveGame();
        });
        saveGameTo.setOnMouseClicked(event->{
		   		 save.setTranslateX(player.getTranslateX());
		   		 save.setTranslateY(player.getTranslateY());
		   		 save.setGameRootLayoutX(gameRoot.getLayoutX());
		   		 save.setBackgroundLayoutX(backgroundIV.getLayoutX());
		   		 save.setLevelNumber(save.getLevelNumber());
        	saveGameTo();
        });
        mainMenu.setOnMouseClicked(event->{
        	gameRoot.getChildren().clear();
        	appRoot.getChildren().clear();
        	initMenu();
    		isPause=false;
    		timer.stop();
        });
        exitGame.setOnMouseClicked(event-> System.exit(0));
    	return pauseMenuBox;
    }
    
    public void initFinish(){
        gameRoot.getChildren().remove(player);
        timer.stop();
    	HMenu resultMenu = new HMenu(score);
    	MenuItem replayGame = new MenuItem("REPLAY");
        MenuItem nextLevel = new MenuItem("NEXT LEVEL");
        MenuItem mainMenu = new MenuItem("MAIN MENU");
        VMenu finalMenu = new VMenu(
                resultMenu,replayGame,nextLevel,mainMenu
        );
        MenuBox finishMenuBox = new MenuBox(finalMenu);
        appRoot.getChildren().add(finishMenuBox);
        FadeTransition ft = new FadeTransition(Duration.seconds(0.2),finishMenuBox);
        ft.setFromValue(0);
		ft.setToValue(1);
		ft.setOnFinished(evt ->   finishMenuBox.setVisible(true));
		ft.play();
		if (save.getLevelNumber()==0){save.setScore0(score);}
		if (save.getLevelNumber()==1){save.setScore1(score);}
		if (save.getLevelNumber()==2){save.setScore2(score);}
		score=5;
		if (!isAgain){
			save.setLevelCount(save.getLevelCount()+1);
			isAgain=true;
		}
		
        replayGame.setOnMouseClicked(event->{
        	save.setTranslateX(0);
        	save.setTranslateY(400);
        	save.setGameRootLayoutX(0);
        	save.setBackgroundLayoutX(0);
        	gameRoot.getChildren().clear();
            appRoot.getChildren().clear();
        	platforms.clear();
        	ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(evt ->   finishMenuBox.setVisible(false));
            ft.play();
        	initContent();
        	isFinish=false;
        });
        nextLevel.setOnMouseClicked(event->{
            save.setLevelNumber(save.getLevelNumber()+1);
        	save.setTranslateX(0);
        	save.setTranslateY(400);
        	save.setGameRootLayoutX(0);
        	save.setBackgroundLayoutX(0);
        	gameRoot.getChildren().clear();
            appRoot.getChildren().clear();
        	platforms.clear();
        	ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(evt ->   finishMenuBox.setVisible(false));
            ft.play();
        	initContent();
        	isFinish=false;
        	isAgain=false;
        });
        mainMenu.setOnMouseClicked(event->{
        	gameRoot.getChildren().clear();
        	appRoot.getChildren().clear();
        	platforms.clear();
            save.setLevelNumber(save.getLevelNumber()+1);
        	initMenu();
        	isFinish=false;
        });
    }
    
    public void initFinal(){
        gameRoot.getChildren().remove(player);
        timer.stop();
    	HMenu resultMenu = new HMenu(score);
    	MenuItem replayGame = new MenuItem("REPLAY");
        MenuItem mainMenu = new MenuItem("MAIN MENU");
        VMenu finalMenu = new VMenu(
                resultMenu,replayGame,mainMenu
        );
        MenuBox finalMenuBox = new MenuBox(finalMenu);
        appRoot.getChildren().add(finalMenuBox);
        FadeTransition ft = new FadeTransition(Duration.seconds(0.2),finalMenuBox);
        ft.setFromValue(0);
		ft.setToValue(1);
		ft.setOnFinished(evt ->   finalMenuBox.setVisible(true));
		ft.play();
		if (save.getLevelNumber()==0){save.setScore0(score);}
		if (save.getLevelNumber()==1){save.setScore1(score);}
		if (save.getLevelNumber()==2){save.setScore2(score);}
		score=5;
        
        replayGame.setOnMouseClicked(event->{
        	save.setTranslateX(0);
        	save.setTranslateY(400);
        	save.setGameRootLayoutX(0);
        	save.setBackgroundLayoutX(0);
        	gameRoot.getChildren().clear();
            appRoot.getChildren().clear();
        	platforms.clear();
        	ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(evt ->   finalMenuBox.setVisible(false));
            ft.play();
        	initContent();
        	isFinish=false;
        });
        mainMenu.setOnMouseClicked(event->{
        	gameRoot.getChildren().clear();
        	appRoot.getChildren().clear();
        	platforms.clear();
        	initMenu();
        	isFinish=false;
        });
    }

    private void update(){
        if(isPressed(KeyCode.UP) && player.getTranslateY()>=5){
            player.jumpPlayer();
        }
        if(isPressed(KeyCode.LEFT) && player.getTranslateX()>=5){
            player.setScaleX(-1);
            player.animation.play();
            player.moveX(-5);
        }
        if(isPressed(KeyCode.RIGHT) && player.getTranslateX()+40 <=levelWidth-5){
            player.setScaleX(1);
            player.animation.play();
            player.moveX(5);
        }
        if(player.playerVelocity.getY()<10){
           player.playerVelocity = player.playerVelocity.add(0,1);
        }
        player.moveY((int)player.playerVelocity.getY());
        if ((isFinish)&&(save.getLevelNumber()!=2)){
            new Sound ("finish.wav", save.getVolume());
        	initFinish();
        }
        if ((isFinish)&&(save.getLevelNumber()==2)){
        	save.setLevelCount(3);
        	new Sound ("final.wav", save.getVolume());
        	initFinal();
        }
    }
    
    private boolean isPressed(KeyCode key){
        return keys.getOrDefault(key,false);
    }
    
    /**
     * Возвращает preference файла адресатов, то есть, последний открытый файл.
     * Этот preference считывается из реестра, специфичного для конкретной
     * операционной системы. Если preference не был найден, то возвращается null.
     * 
     * @return
     */
    public File getPlayerFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Game.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Задаёт путь текущему загруженному файлу. Этот путь сохраняется
     * в реестре, специфичном для конкретной операционной системы.
     * 
     * @param file - файл или null, чтобы удалить путь
     */
    public void setPlayerFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Game.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Обновление заглавия сцены.
            primaryStage.setTitle("Super Mario - " + file.getName());
        } else {
            prefs.remove("filePath");
            
            // Обновление заглавия сцены.
            primaryStage.setTitle("Super Mario");
        }
    }
    
	public void loadPlayerDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(Save.class);
            Unmarshaller um = context.createUnmarshaller();

            //Чтение XML из файла и демаршализация.
            
            save = (Save) um.unmarshal(file);
            //saveData.addAll(wrapper.getSaves());

            // Сохраняем путь к файлу в реестре.
            setPlayerFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void savePlayerDataToFile(File file) {
    	try {
            JAXBContext context = JAXBContext.newInstance(Save.class);
            Marshaller m = context.createMarshaller();
            
            //output pretty printed
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Маршаллируем и сохраняем XML в файл.
            m.marshal(save, file);

            // Сохраняем путь к файлу в реестре.
            setPlayerFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }
    
    /**
     * Открывает FileChooser, чтобы пользователь имел возможность
     * выбрать файл для загрузки игры.
     */
    private void loadGame() {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог загрузки файла
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            loadPlayerDataFromFile(file);
        }
    }
    
    /**
     * Сохраняет файл в файл игрового прогресса, который в настоящее время открыт.
     * Если файл не открыт, то отображается диалог "Сохранить в...".
     */
    private void saveGame() {
        File playerFile = getPlayerFilePath();
        if (playerFile != null) {
            savePlayerDataToFile(playerFile);
        } else {
            saveGameTo();
        }
    }
    
    /**
     * Открывает FileChooser, чтобы пользователь имел возможность
     * выбрать файл, куда будет сохранен прогресс игра
     */
    private void saveGameTo() {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог сохранения файла
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            savePlayerDataToFile(file);
        }
    }
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
    	this.primaryStage.setTitle("Super Mario");
        this.primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));

        initMenu();
         
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
