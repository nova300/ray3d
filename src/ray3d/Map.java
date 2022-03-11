package ray3d;
import static org.lwjgl.opengl.GL11.*;

public class Map {

    public static int mapX=9, mapY=8, mapS=64;
    
    public static int[] map=           /* map array */
    {
        1,1,1,1,1,1,1,1,1,
        1,0,0,0,0,1,0,0,1,
        1,1,1,1,0,0,0,0,1,
        1,0,0,1,0,0,0,1,1,
        1,0,0,0,0,0,0,0,1,
        1,0,1,0,0,0,0,0,1,
        1,0,0,0,0,0,0,0,1,
        1,1,1,1,1,1,1,1,1,	
    };
    
    public static int getX(){
        return mapX;
    }
    public static int getY(){
        return mapY;
    }
    public static int getS(){
        return mapS;
    }
    public static int[] getMap(){
        return map;
    }

    public static void drawMap2D(){
        int x,y,xo,yo;
        for (y=0;y<mapY;y++){
            for(x=0;x<mapX;x++){
                if(map[y*mapX+x]==1){
                    glColor3f(1, 1 ,1);
                }   else{
                    glColor3f(0, 0, 0);
                }
                xo = x * mapS;
                yo = y * mapS;
                glBegin(GL_QUADS);
                glVertex2i(xo   +1, yo   +1);
                glVertex2i(xo   +1, yo + mapS-1);
                glVertex2i(xo + mapS-1, yo + mapS-1);
                glVertex2i(xo + mapS-1, yo   +1);
                glEnd();
            }
        }
    }

    
}
