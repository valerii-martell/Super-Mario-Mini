package com.supermario;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by KirinTor on 13.05.2016.
 */
public class MenuBox extends Pane{
    static VMenu VMenu;
    
    public MenuBox(VMenu VMenu){
        MenuBox.VMenu = VMenu;

        setVisible(false);
        Rectangle bg = new Rectangle(1200,620,Color.LIGHTBLUE);
        bg.setOpacity(0.4);
        getChildren().addAll(bg, VMenu);
    }
    public void setVMenu(VMenu VMenu){
        getChildren().remove(MenuBox.VMenu);
        MenuBox.VMenu = VMenu;
        getChildren().add(MenuBox.VMenu);
    }
}
