import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import acm.graphics.*;
import acm.program.GraphicsProgram;
//BJ Kaious

// This program will demonstrate the use of acm graphics and its integration with
// arrays through a game in which the user uses a net to catch butterflies.
public class Butterflies extends GraphicsProgram {
    // SETTING APPLICATION WIDTH AND SIZE
    public static final int APPLICATION_WIDTH = 900;
    public static final int APPLICATION_HEIGHT = 900;

    public static final int TARGET_SIZE = 100;


    //USED AS A CONSTANT FOR OTHER METHODS TO BE USED
    GImage net;



    public void run() {
        setBackground(Color.BLUE);
        GImage[] butterfly = drawButterflies(10);
        drawNet();
        addKeyListeners();
        animateButterflies(butterfly, 10);



    }
    //SET BACKGROUND COLOR
    public void setBackground(Color color) {
        super.setBackground(color);
    }

    public void drawNet(){
        // CREATE THE NET AND SETTING IT'S LOCATION
        net = new GImage("net.png");
        add(net, APPLICATION_WIDTH -100, APPLICATION_HEIGHT - net.getHeight());

    }

    public GImage[] drawButterflies(int size){
        //SETTING THE TARGETS AS BUTTERFLIES AND USING AMOUNT OF BUTTERFLIES AS PARAMETERS
        GImage[] butterfly = new GImage[size];

        for (int i = 0; i < butterfly.length; i++) {
            //CREATES NEW BUTTERFLIES AND SETS THEIR LOCATION ON RANDOM COORDINATES
            butterfly[i] =  new GImage("butterfly.png", randomCoordinate('x'), randomCoordinate('y'));
            add(butterfly[i]);
        }
        return butterfly;
    }

    public void keyPressed(KeyEvent e) {
        // only goes up and down

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            //COLLISION BOUNDARY GOING UP
            if (net.getLocation().getY() > 0) {
                net.move(0, -10);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            //COLLISION BOUNDARY GOING DOWN
            if (net.getLocation().getY()+220 < APPLICATION_HEIGHT) {
                net.move(0, 10);
            }
        }

    }

    public void animateButterflies(GImage[] butterflies, int size) {
        int collided = 0;
        // WHILE BUTTERFLIES CAUGHT IS LESS THAN THE AMOUNT IN SIZE
        while (collided < size) {
            //BUTTERFLIES MOVING FAST
            for(GImage n : butterflies) {
                n.move(60, 0);
                // WHEN THEY HIT THE WINDOW BOUNDARIES, THEY RESET THEIR LOCATION RANDOMLY
                if (n.getX() > APPLICATION_WIDTH) {
                    n.setLocation(randomCoordinate('x'), randomCoordinate('y'));
                }
                //WHEN BUTTERFLIES AND NET INTERSECT THEY DISAPPEAR
                if (net.getBounds().intersects(n.getBounds()) && n.isVisible()) {
                    collided++;
                    n.setVisible(false);
                }
                //MILLISECOND PAUSE BEFORE WHILE LOOP RESUMES
                pause(50);
            }
        }
        // WHEN ALL BUTTERFLIES ARE CAUGHT, GLABEL TRIGGERS.
        GLabel finish = new GLabel("Game over... or is it?", 0, 50);
        finish.setFont("Italic-50");
        add(finish);


    }

    public int randomCoordinate(char coordinate) {
        if (coordinate != 'x' && coordinate != 'y') {
            throw new IllegalArgumentException();
        }

        if (coordinate == 'x') {
            return new Random().nextInt(APPLICATION_WIDTH - TARGET_SIZE);
        } else if (coordinate == 'y') {
            return new Random().nextInt(APPLICATION_HEIGHT - TARGET_SIZE / 2);
        }
        return 0;
    }


    public static void main(String[] args) {
        new Butterflies().start();
    }
}
