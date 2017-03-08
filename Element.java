/*
-> Flydom Source Code
-> Adam Fiedler (c) 2014
*/

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

abstract class Element {
    protected Polygon body; // the body of the element
    protected BufferedImage[] views; // the collection of animations
    private boolean animate = false, repeat = false; // an animation indicator
    protected Color color; // possible color if not an image
    protected int skin = 0; // current image
    protected int dX = 0, dY = 0; // speed of the object
    // A dynamic element
    public Element(BufferedImage[] views, int startX, int startY) {
        this.body = new Polygon(
                        new int[] {startX, startX + views[0].getWidth(), startX + views[0].getWidth(), startX}, 
                        new int[] {startY, startY, startY + views[0].getHeight(), startY + views[0].getHeight()}, 4);
        this.views = views;
    }
    // A rectangle element
    public Element(int startX, int startY, int width, int height) {
                this.body = new Polygon(
                        new int[] {startX, startX + width, startX + width, startX}, 
                        new int[] {startY, startY, startY + height, startY + height}, 4);
    }
    // Get X position
    public double getX() {
        return this.body.xpoints[0];
    }
    public int getX(int i) {
        return (int)this.body.xpoints[i];
    }
    // Get Y position
    public double getY() {
        return this.body.ypoints[0];
    }
    public int getY(int i) {
        return (int)this.body.ypoints[i];
    }
    // Translate this polygon
    public void translate(int dX, int dY) {
        this.body.translate(dX, dY);
    }
    // If this intersects something
    public boolean intersects(Element r) {
        return  this.body.contains(r.getX(0), r.getY(0))
        ||      this.body.contains(r.getX(1), r.getY(1))
        ||      this.body.contains(r.getX(2), r.getY(2))
        ||      this.body.contains(r.getX(3), r.getY(3));
    } 
    // Get width of the polygon
    public double getWidth() {
        return this.body.getBounds().getWidth();
    }
    // Get height of the polygon
    public double getHeight() {
        return this.body.getBounds().getHeight();
    }
    // Change the size
    public void setSize(int width, int height) {
        // Reset the polygon
        this.body.reset();
        // Coordinate the points
        this.body.xpoints = new int[] {(int)this.getX(), (int)this.getX() + width, (int)this.getX() + width, (int)this.getX()};
        this.body.ypoints = new int[] {(int)this.getY(), (int)this.getY(), (int)this.getY() + height, (int)this.getY() + height};
        this.body.npoints = 4;
    }
    // Get horizontal speed
    public int getdX() {
        return this.dX;
    }
    // Get vertical speed
    public int getdY() {
        return this.dY;
    }
    // Animate
    public void animate(BufferedImage[] views, boolean repeat) {
        this.skin = 0; // reset animation
        this.animate = true; // start animation
        this.views = views; // select 
        this.setSize(this.views[0].getWidth(), this.views[0].getHeight());
        this.repeat = repeat; // should repeat
    }
    // Drawing out
    public void draw(Graphics2D pen) {
        if (this.views != null) {
            // animate if desired
            this.changeFrame();
            // show the element
            pen.drawImage(this.views[skin], (int)this.getX(), (int)this.getY(), null);
        } else { 
            // fallback to a shape
            pen.setColor(this.color);
            pen.fillPolygon(this.body);
        } 
    }
    // Animation handling
    private void changeFrame() {
        if (this.animate) {
            if (skin < this.views.length - 1)
                this.skin++; // continue on to next frame
            else if (repeat)
                this.skin = 0; // repeat the animation
            else
                this.animate = false;
        }
    }
}