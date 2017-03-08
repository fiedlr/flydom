/*
-> Flydom Source Code
-> Adam Fiedler (c) 2014
*/

import java.awt.image.BufferedImage;
import java.util.ArrayList;

class Obstacle extends Element {
    private static ArrayList<Obstacle> list = new ArrayList(); // keep track of all the obstacles
    // Get a list of all
    public static ArrayList<Obstacle> getAll() {
        return list;
    }
    public static void removeAll() {
        list.clear();
    }
    // A dynamic image
    public Obstacle(BufferedImage[] views, int startX, int startY) {
	super(views, startX, startY);
        list.add(this);
    }
    // A shape mode
    public Obstacle(int startX, int startY, int width, int height) {
        super(startX, startY, width, height);
        list.add(this);
    }  
}