/*
-> Flydom Source Code
-> Adam Fiedler (c) 2014
*/

import java.awt.Component;
import javax.swing.JFrame;

class Window extends JFrame {
    private Flydom flydom;
    private Component game;
    // Creating a window
    public Window(int width, int height) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(width, height);
        // Add the game
        this.setTitle("Flydom v" + Flydom.VERSION); 
        this.flydom = new Flydom(this);
        this.game = this.add(this.flydom);
        this.setVisible(true);
    }
    public void reset() {
        this.remove(this.game);
        Obstacle.removeAll();
        this.flydom = null;
        this.game = null;
        System.gc();
        this.flydom = new Flydom(this);
        this.game = this.add(this.flydom);
        this.revalidate();
        this.repaint();
        this.game.requestFocusInWindow();
    }
}