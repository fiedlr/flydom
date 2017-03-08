/*
-> Flydom Source Code
-> Adam Fiedler (c) 2014
*/

import java.awt.image.BufferedImage;

public class Tree extends Obstacle {
    private static final BufferedImage[] bgImage = Flydom.loadSprite("src/includes/tree.png", 1);
    
    public Tree(int startX, int startY) {
        super(startX, startY, Tree.bgImage[0].getWidth()/2, Tree.bgImage[0].getHeight()/2);
    }
}