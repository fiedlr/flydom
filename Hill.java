/*
-> Flydom Source Code
-> Adam Fiedler (c) 2014
*/

import java.awt.Color;

public class Hill extends Obstacle {
    
    public Hill(int angle, int startX, int startY, int width, int height) {
        super(startX, startY, width, height);
        this.color = Color.GRAY;
        this.body.reset();
        this.body.ypoints[1] = startY + (int)(Math.tan(angle * 3.14 / 180) * width);
        this.body.npoints = 4;
    }
}