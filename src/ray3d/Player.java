package ray3d;

import static org.lwjgl.opengl.GL11.*;
import java.awt.event.KeyEvent;
import org.joml.Math;



public class Player {
    private static double px=300, py=300;       /*  */
    
    private static double panSpeed = 0.01;

    private static double pdx,pdy,pa;

    private static double pi = Math.PI;

    public static int r,mx,my,mp,dof;

    public static double rx,ry,ra,ro,xo,yo;

    

    public static double getPlayerAngle(){  /* these are methods to return properties of the player for use in other classes */
        return pa;
    }

    public static double getX(){
        return px;
    }

    public static double getY(){
        return py;
    } 

    public static void drawPlayer(){        /* this draws the player and the looking direction in 2d mode */
        controller();
        glColor3f(1,1,0);
        glPointSize(8);
        glBegin(GL_POINTS);
        glVertex2d(px, py);
        glEnd();

        glLineWidth(3);
        glBegin(GL_LINES);
        glVertex2d(px, py);
        glVertex2d(px+pdx*5, py+pdy*5);
        glEnd();
    }

    public static void calcDeltas(){        /* this calculates the deltas of the player direction */
        pdx = Math.cos(pa) * 5; 
        pdy = Math.sin(pa) * 5;
    }
    
    
    
    /* this method handles player movement and input, for left and right we pan the camera by panSpeed in radians, 
    for forards and back we add or subtract the deltas of player direction to the player position */
    public static void controller(){            
        if(Keyboard.iskeyPressed(KeyEvent.VK_A)){ pa -=panSpeed;
            if(pa<0) { pa += 2 * pi; }
            calcDeltas();
        } 
        if(Keyboard.iskeyPressed(KeyEvent.VK_D)){ pa +=panSpeed; 
            if(pa>2*pi) { pa -= 2 * pi; }
            calcDeltas();
        }
        if(Keyboard.iskeyPressed(KeyEvent.VK_W)){ px += pdx/4; py += pdy/4; }
        if(Keyboard.iskeyPressed(KeyEvent.VK_S)){ px -= pdx/4; py -= pdy/4; }
    }

    
}
