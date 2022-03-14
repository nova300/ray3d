package ray3d;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Math;

public class Ray {

    public static int m[] = Map.getMap();
    public static int r,mp,mx,my,max;
    public static double pi = (float)Math.PI;
    public static double pi2 = pi/2;
    public static double pi3 = 3*pi/2;
    public static double dgr = 0.0174533;       /* one degree in radians */

    public static double xo,yo,rhy,rhx,rvy,rvx;
    public static int mapX = Map.getX();
    public static int mapY = Map.getY();
    public static int mapS = Map.getS();
    public static int map[] = Map.getMap();

    
    public static double dist(double ax, double ay, double bx, double by, double ang){  /* this calculates the distance between two points on the map using the phytagoran theorem */

        return Math.sqrt((bx-ax)*(bx-ax) + (by-ay)*(by-ay));
        
    }


    public static void drawRays3D(){        /* this is the heart of the raycasting code, here we cast the rays and draw the results to the screen */

        double rx=0,ry=0,disT = 0;
        double ra=Player.getPlayerAngle()-dgr*45;   /* initial ray angle is the player angle minus one degree in radians times however many degrees offset you want from the pa */
        
        for(r=0;r<90;r++){

            double pa = Player.getPlayerAngle();
            double px = Player.getX();
            double py = Player.getY();
            
            
            max = 0;                                /* this first if-else block will cast the vertical rays (seen from the top of the 2d map) */
            double disH = 100000, hx=px, hy=py;
            // check horiz lines
            max = 0;
            double aTan=(-1/Math.tan(ra));          
            if(ra>pi){                              /* if the rayangle is more than pi we are looking up */
                ry=(((int)py/mapS)*mapS) -0.0001;   /* division and multiplication is for aligning to tilemap, subtraction is for accuracy */
                rx=(py-ry)*aTan+px; 
                yo=-mapS; 
                xo=-yo*aTan;
            }

            else if(ra<pi){                         /* if the rayangle is less than pi we are looking down */
                ry=(((int)(py/mapS)*mapS) +mapS);
                rx=(py-ry)*aTan+px; 
                yo= mapS; 
                xo=-yo*aTan;
            } 
            else {  /* axis is straight */
                rx = px;
                ry = py;
                max = 8;
            }                
            while(max<8){
                mx = (int) (rx) / mapS;
                my = (int) (ry) / mapS;
                mp = my * mapX + mx; 
                if(mp>0 && mp<(mapX*mapY) && map[mp]==1) {
                    disH = dist(rx, ry, px, py, ra);
                    hx = rx;
                    hy = ry;
                    max = 8;
                }   else { /* cell is not a wall, check the next one */
                    rx+=xo;
                    ry+=yo;
                    max+=1;
                }
            }
            max = 0;
            double disV = 1000, vx=px, vy=py;
            double nTan=(-Math.tan(ra));
            if(ra>pi2 && ra<pi3){ 
                rx=(((int)px/mapS)*mapS) -0.0001; 
                ry=(px-rx)*nTan+py; 
                xo=-mapS; 
                yo=-xo*nTan;
            }

            else if(ra<pi2 || ra>pi3){
                rx=(((int)px/mapS)*mapS)+mapS;
                ry=(px-rx)*nTan+py; 
                xo= mapS; 
                yo=-xo*nTan;
            } 
            else {  /* axis is straight */
                rx = px;
                ry = py;
                max = 8;
            }                
            while(max<8){
                mx = (int) (rx) / mapS;
                my = (int) (ry) / mapS;
                mp = my * mapX + mx; 
                if(mp>0 && mp<(mapX*mapY) && map[mp]==1) {
                    disV = dist(rx, ry, px, py, ra);
                    vx = rx;
                    vy = ry;
                    max = 8;
                }   else { 
                    rx+=xo;
                    ry+=yo;
                    max+=1;
                }
            }
            
            

            if(disV>disH){  /* here we choose if we choose if we are using a horizontal line or a vertical line based on which one lands closest to the player */
                rx=hx;
                ry=hy;
                disT=disH;
                glColor3f(0,1,1);
            }
            if(disV<disH){
                rx=vx;
                ry=vy;
                disT=disV;
                glColor3f(0,0.5f,0.5f); /* set a slightly darker colour for the vertical rays to get a basic shading effect */
            }

            
            /* ----this draws our 3d scene---- */

            float lineH = (64*320)/(float)disT;
            if(lineH>320){
                lineH=320;
            }
            
            double ca=pa-ra;
            if(ca<0){
                ca+=2*pi;
            }
            if(ca<2*pi){
                ca-=2*pi;
            }
            disT=disT*Math.cos(ca);
            
            float lineO =250-lineH/2;


            /*

            UNUSED, used to draw a line as the ray in 2D mode

            glColor3f(1,0,0);
            glLineWidth(1);
            glBegin(GL_LINES);
            glVertex2d(px, py);
            glVertex2d(rx, ry);
            glEnd();
            */

            /* here we draw the lines using the lwjgl gl bindings */
            glLineWidth(8);
            glBegin(GL_LINES);
            glVertex2d(r*8+100,lineO);
            glVertex2d(r*8+100,lineH+lineO);
            glEnd();
            
        
            ra+=dgr;
            if(ra<0){
                ra+=2*pi;
            } 
            if(ra>2*pi) {
                ra-=2*pi;
            }
            
            
            

        }

    }
    
}
