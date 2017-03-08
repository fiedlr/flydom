import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Character.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

public abstract class GameDriver extends Canvas implements KeyListener {
    protected BufferedImage back;
    protected Timer timer;
    protected int rate = 35;

    public GameDriver() {
        setBackground(Color.WHITE); // default background
        setVisible(true); // visibility 
	this.timer = new Timer(); 
        this.timer.scheduleAtFixedRate(this.new GameTimer(), 0, this.rate); // run every second to frame
	addKeyListener(this); // key strokes
	setFocusable(true);
    }
    
    private class GameTimer extends TimerTask {
        public void run() {
            repaint();
        }
    }
   
    public void setRate(int value) {
        if (value > 0)
        this.rate = value;
    }
   
    public void paint(Graphics window) {
	if (back == null) back  =   (BufferedImage)(createImage(getWidth(),getHeight()));
	Graphics2D graphToBack  =   (Graphics2D) back.createGraphics();
	draw(graphToBack);
	Graphics2D win2D        =   (Graphics2D) window;
	win2D.drawImage(back, null, 0, 0);
    }

    public void update(Graphics window) {
        this.paint(window);
    }
   
    public static BufferedImage getImage(String name) {
  	BufferedImage img = null;
        try {
            img = ImageIO.read(new File(name));
        } catch (IOException e) {
            System.out.println(e); 
        }
        return img;
    }
    
    public abstract void draw(Graphics2D win);

    public void keyPressed(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}
}