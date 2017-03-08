/*
-> Flydom Source Code
-> Adam Fiedler (c) 2014
 */

import java.util.TimerTask;

class Action extends TimerTask {
    private Element element;
    private boolean repeat;
    
    public Action(Element e, boolean r) {
        this.element = e;
        this.repeat = r;
    }
 
    public void run() {
        //this.element.animate(this.repeat);
    }
}