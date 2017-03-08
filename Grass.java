/*
-> Flydom Source Code
-> Adam Fiedler (c) 2014
*/

import java.awt.Color;

public class Grass extends Obstacle {
    private static Color bgColor = new Color(119, 171, 63);
    // Set up
    public Grass(int startX, int startY, int width, int height) {
        super(startX, startY, width, height);
        this.color = Grass.bgColor;
    }  
}