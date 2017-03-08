/*
-> Flydom Source Code
-> Adam Fiedler (c) 2014
*/

import java.awt.image.BufferedImage;

class Bird extends Obstacle {
    private static final BufferedImage[] bgImage = Flydom.loadSprite("src/includes/bird.png", 2);
    // Set up a bird
    public Bird(int startX, int startY) {
        super(bgImage, startX, startY);
        // Random vertical speed
        this.dX = -(int)(Math.random()*20+5);
        this.animate(bgImage, true);
    }
}