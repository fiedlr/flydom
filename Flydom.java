/*
-> Flydom Source Code
-> Adam Fiedler (c) 2014
*/

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Scanner;

class Flydom extends GameDriver {
    public  final static String VERSION = "0.8"; // game version
    private Window window; // game window (to manipulate with it)
    private Rectangle frame; // game frame (the background resetter)
    private Player player; // the main player
    private int score = 0, credit = 32; // current score and gaining credit
    private BufferedImage bg, start, cg, gameover; // background image
    private int bgPos[] = new int[2]; // background position
    // Runing the game
    public static void main(String[] args) {
        Window w = new Window(900, 700);
    }
    // Assing all the variables
    public Flydom(Window window) {
        // Game window
        this.window     =       window;
        // Game frame
	this.frame      = 	new Rectangle(0, 0, window.getWidth(), window.getHeight());
        // Set up the player
	this.player     = 	new Player(85, 275);
        // Set up the background images
        this.bg         =       getImage("src/includes/background.png");
        this.bgPos[1]   =       (int)this.window.getWidth();
        this.start      =       getImage("src/includes/start.png");
        this.cg         =       getImage("src/includes/cg.png");
        this.gameover   =       getImage("src/includes/gameover.png");
        // Sound
        SoundDriver sound   = new SoundDriver(new String[]{"src/includes/wind.wav"});
        sound.loop(0);
        // Set up the map
        this.createMap();
    }
    private void createMap() {
        // Ground and target
        Grass ground    =       new Grass(0, 7 * this.window.getHeight(), this.window.getWidth(), this.window.getHeight());
        Target landzone =       new Target((int)(Math.random() * this.window.getWidth()), 7 * this.window.getHeight() - 10, 200, 10);
        int currentY = 200, height = this.window.getHeight() - currentY;
        Hill previous = new Hill(0, 0, 335, 100, this.window.getHeight() - 200);
        previous = new Hill(65, previous.getX(1), previous.getY(1), (int)(Math.random()*100+200), 2 * height);
        for (int i = 0; i < 10; i++) {
            previous = new Hill((int)(Math.random() * 70 + 10), previous.getX(1), previous.getY(1), (int)(Math.random()*100+200), 2 * height);
            // Place it on the map
            currentY = previous.getY(1);
        }
    }
    // Incrementing score and credit if hit target (I don't like this solution)
    public void incrementScore() {
        this.score *= 2;
    }
    public void setCredit(int i) {
        this.credit = i;
    }
    public Window getWindow() {
        return this.window;
    }
    // Draw on screen
    public void draw(Graphics2D pen) {
        //test.draw(pen);
        // Reset the frame
        pen.setColor(new Color(132, 183, 250)); pen.fill(this.frame);
        // Move clouds
        pen.drawImage(this.bg, --this.bgPos[0], 0, null);   pen.drawImage(this.bg, --this.bgPos[1], 0, null);
        if (this.bgPos[0] < -this.frame.getWidth())         this.bgPos[0] = (int)this.frame.getWidth();
        else if (this.bgPos[1] < -this.frame.getWidth())    this.bgPos[1] = (int)this.frame.getWidth();
 	// Indicate whether the game is over or not
	if (this.player.isInAir() && !this.player.isHit()) {           
            // Freefall
            this.player.move(Obstacle.getAll(), pen, this);
            // Make random birds
            if ((int)(Math.random() * 40) == 20) {
                new Bird(this.window.getWidth(), (int)Math.ceil((Math.random() + 0.5) * this.window.getHeight()));
            }  
            // Update the score
            this.score+=credit;
            pen.setColor(Color.BLACK);
            pen.setFont(Font.decode("Arial").deriveFont(Font.BOLD, 20));
            pen.drawString("Score: "+this.score, this.window.getWidth() - 200, this.window.getHeight() - 80);
	} else {
            // Draw everything static
            this.player.draw(pen);
            for (Obstacle o : Obstacle.getAll()) o.draw(pen);
            // Show the result
            if (!player.isHit()) {
                // Start of the game
                pen.drawImage(this.start, this.window.getWidth()/2-226, this.window.getHeight()/2-140, null);
            } else if (player.hasOpenParachute() && (player.isHitBy() instanceof Grass || player.isHitBy() instanceof Target)) {
                pen.drawImage(this.cg, 0, 0, null);
                pen.setColor(Color.YELLOW); 
                pen.setFont(Font.decode("Arial").deriveFont(Font.BOLD, 36));
                pen.drawString("Score: "+this.score + (player.isHitBy() instanceof Target ? "(2x)" : ""), this.window.getWidth() / 2 - 94, this.window.getHeight() / 2 + 36);
            } else {
                // Game over
                // this.player.fall(pen);
                pen.drawImage(this.gameover, 0, 0, null);
            }
        }
    }
    // Movement
    public void keyPressed(KeyEvent e) {
        if (player.isHit() && e.getKeyCode() != KeyEvent.VK_R) return;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            
            // Starting the game
            this.player.jump();
            
        }  else if (e.getKeyCode() == KeyEvent.VK_UP) {
            
            // Flying
            this.player.openWings(this);
            
	} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            
            // Opening parachute
           this.player.openParachute(this);
           
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            
            // Move left when parachute open
            this.player.parachuteLeft(this.window);
            
	} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            
            // Move right when parachute right
            this.player.parachuteRight(this.window);
            
	} else if (!this.player.isInAir() && e.getKeyCode() == KeyEvent.VK_R) {
            
            this.window.reset();
            
        }
    }
    // For canceling events
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            
            // Freefall again
            this.player.closeWings(this);
            
	} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            
            // Stop moving left when parachute open
            
	} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            
            // Stop moving right when parachute open
            
	}
    }
    // Loading horizontal sprites
    public static BufferedImage[] loadSprite(String path, int n) {
        // Load sprite
        BufferedImage sprite = getImage(path);
        // Find out smoothness
        int width = sprite.getWidth() / n;
        // Save all animation to an array
        BufferedImage[] images = new BufferedImage[n];
        for (int i = 0; i < n; i++) images[i] = sprite.getSubimage(i * width, 0, width, sprite.getHeight());
        return images;
    }
}