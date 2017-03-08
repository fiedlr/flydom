/*
-> Flydom Source Code
-> Adam Fiedler (c) 2014
*/

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;

class Player extends Element {
    protected int dX = 5, dY = 22; // default speeds
    private boolean inAir = false; // player jumped or not
    private boolean openWings = false; // wings open or not
    private boolean openParachute = false; // parachute open or not
    private Obstacle hitBy; // stores what player hit to determine the result
    private static BufferedImage[][] bgImages = {
        Flydom.loadSprite("src/includes/jumping.png", 7),
        Flydom.loadSprite("src/includes/rollingwings.png", 4),
        Flydom.loadSprite("src/includes/closingwings.png", 4),
        Flydom.loadSprite("src/includes/parachuteopen.png", 5)
    };
    // Sets up the player
    public Player(int startX, int startY) {
	super(Player.bgImages[0], startX, startY);
    }
    // Detects if player in air
    public boolean isInAir() {
        return this.inAir;
    }
    // Determines whether a player is hit or not (does NOT change their state)
    public boolean isHit() {
	return (this.hitBy != null);
    }
    // Determines what obstacle player hit
    public Obstacle isHitBy() {
        return this.hitBy;
    }
    // Detects if parachute's open
    public boolean hasOpenParachute() {
        return this.openParachute;
    }
    // If needs to be manually reset
    public void setInAir(boolean s) {
        this.inAir = s;
    }
    // Starts the game
    public void jump() {
        if (!this.inAir) {
            // Changes
            this.inAir = true;
            this.translate(50, -150);
            // Animation
            this.animate(Player.bgImages[0], false);
        }
    }
    // Moves the player depending on their relative position
    public void move(ArrayList<Obstacle> obstacles, Graphics2D pen, Flydom f) {
        // sail if open
        if (this.openParachute && this.getX() + this.getWidth() < f.getWindow().getWidth()) this.translate(this.dX, 0);
        // draw the player
        this.draw(pen);
        // show the environment
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle o = obstacles.get(i);
            // has to be on screen, else delete for memory
            if (o.getX() > -o.getWidth() || o.getY() > -o.getHeight()) {
                // move environment top left, otherwise let move everything its way
                o.translate(!(o instanceof Grass) && !(o instanceof Target) ? -Math.abs(this.dX)+o.getdX() : 0, -(Math.abs(this.dY)+o.getdY()));
                o.draw(pen);
                // if anything hits player end the game (only one ending object to avoid bugs)
                if (this.hitBy == null && o.intersects(this)) {
                    // Congratz
                    if (o instanceof Target) f.incrementScore();
                    this.hitBy = o;
                    this.inAir = false;
                    System.out.println("Player hit by " + o + "[id=" + i + "]");
                }
            } else {
                // Garbage collector for any unused objects
                obstacles.remove(i);
                System.out.println("Object " + o + "[id="+i+"] removed from memory.");
                i--;
            }
        }
    }
    // Opens wings
    public void openWings(Flydom f) {
	// Animation, slower and flipped speeds
	if (this.inAir == true && !this.openParachute && !this.openWings) {
            f.setCredit(2); // less score for flying without risks
            // Physics
            this.openWings = true;
            this.dX = 11; this.dY = 10; // flip speeds
            // Animation
            this.animate(Player.bgImages[1], false);
            System.out.println("Wings open");
	}
    }
    // Closes wings
    public void closeWings(Flydom f) {
        // Animation and faster vertical speed
	if (this.inAir == true && !this.openParachute && this.openWings) {
            f.setCredit(32); // risks, more credit
            // Physics
            this.openWings = false;
            this.dX = 5; this.dY = 22;
            // Animation
            this.animate(Player.bgImages[2], false);
            System.out.println("Wings closed");
        }     
    }
    // Opens the parachute
    public void openParachute(Flydom f) {
        // Animation and slow horizontal speed + vertical speed and stop frame movement
        if (this.inAir == true && !this.openParachute) {
            f.setCredit(0);
            this.animate(Player.bgImages[3], false);
            this.openParachute = true;
            this.dX = 3; this.dY = 5;
            System.out.println("Parachute open");
        }
    }
    // Sails left
    public void parachuteLeft(JFrame w) {
        // Move left if parachute's open
        if (this.inAir && this.openParachute) {
           if (this.getX() - 5 >= 0) {
               this.translate(-5, 0);
           }
        }
    }
    // Sails right
    public void parachuteRight(JFrame w) {
        // Move right if parachute's open
	if (this.inAir && this.openParachute) {
           if (this.getX() + this.getWidth() + 10 <= w.getWidth() - this.getWidth()) {
               this.translate(10, 0);
           }
        }
    }
    // Animation for falling
    public void fall() {
        // Stop all action
        this.inAir = false;
        // Animation
    }
}