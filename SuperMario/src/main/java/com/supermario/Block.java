package com.supermario;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Created by KirinTor on 13.05.2016.
 */
public class Block extends Pane {
    ImageView block;
    /**
     * ������ ���� �����
     */
    public enum BlockType {
        PLATFORM, BRICK, BONUS, PIPE_TOP, PIPE_BOTTOM, INVISIBLE_BLOCK, STONE
    }
    /**
     * ������� ���� ���������� ����
     * 
     * @param ��������, ���������� �, ���������� �
     */
    public Block(Image image, BlockType blockType, int x, int y) {
        block = new ImageView(image);
        block.setFitWidth(Game.BLOCK_SIZE);
        block.setFitHeight(Game.BLOCK_SIZE);
        setTranslateX(x);
        setTranslateY(y);

        switch (blockType) {
            case PLATFORM:
                block.setViewport(new Rectangle2D(0, 0, 20, 20));
                break;
            case BRICK:
                block.setViewport(new Rectangle2D(20, 0, 20, 20));
                break;
            case BONUS:
                block.setViewport(new Rectangle2D(384, 0, 16, 16));
                break;
            case PIPE_TOP:
                block.setViewport(new Rectangle2D(0, 128, 32, 16));
                block.setFitWidth(Game.BLOCK_SIZE * 2);
                break;
            case PIPE_BOTTOM:
                block.setViewport(new Rectangle2D(0, 145, 32, 14));
                block.setFitWidth(Game.BLOCK_SIZE * 2);
                break;
            case INVISIBLE_BLOCK:
                block.setViewport(new Rectangle2D(0, 0, 8, 8));
                block.setOpacity(0);
                break;
            case STONE:
                block.setViewport(new Rectangle2D(0, 16, 16, 16));
                break;
        }
        getChildren().add(block);
        Game.platforms.add(this);
        Game.gameRoot.getChildren().add(this);
    }
}



