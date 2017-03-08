/*
-> Flydom Source Code
-> Adam Fiedler (c) 2014
*/

import java.awt.image.BufferedImage;

public class Rock extends Obstacle {
    private static BufferedImage[] bgImage = Flydom.loadSprite("src/includes/rock.png", 1);
    // Set up
    public Rock(int startX, int startY) {
        super(Rock.bgImage, startX, startY);
    }
}