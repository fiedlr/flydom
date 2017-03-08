/*
-> Flydom Source Code
-> Adam Fiedler (c) 2014
*/

import java.awt.Color;

public class Target extends Obstacle {
    private static Color bgColor = new Color(255, 88, 23);
    
    public Target(int startX, int startY, int width, int height) {
        super(startX, startY, width, height);
        this.color = Target.bgColor;
    }
}