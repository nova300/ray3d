package ray3d;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.system.*;

import org.lwjgl.opengl.*;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import java.nio.*;

import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.Callbacks.*;


public class Ray3d {        /* this is the main method of the game engine */

    private int width, height;
    private String title;
    private long glfwWindow;
    private static Ray3d window = null;
    
    
    private Ray3d() {
        
        this.width = 1024;
        this.height = 576;
        this.title = "ray3d";

    }

    public static Ray3d get() {
        
        if (Ray3d.window == null){
            Ray3d.window = new Ray3d();
        }

        return Ray3d.window;
    }

    public void run() {
        System.out.println("lwjgl" + Version.getVersion() + "!" );


        init();
        loop();

        // Free the window callbacks and destroy the window
		glfwFreeCallbacks(glfwWindow);
		glfwDestroyWindow(glfwWindow);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();

    }

    public void init () {  
        //setup error callback
        GLFWErrorCallback.createPrint(System.err).set();
        
        if (!GLFW.glfwInit()){
            throw new IllegalStateException("unable to initialize glfw!");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);


        //create window

        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL){
            throw new IllegalStateException("failed to create window!");
        }

        glfwSetKeyCallback(glfwWindow, Keyboard::keyCallback);


		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(glfwWindow, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				glfwWindow,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically  


        // make the opengl context current
        glfwMakeContextCurrent(glfwWindow);
        
        // v-sync
        glfwSwapInterval(1);

        //make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
        GL.createCapabilities();

        //-------------------init----------------------------------------------------
        glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        glOrtho(0, width, height, 0, -1, 1);
        Player.calcDeltas();






    }

    public void loop() {


        while (!glfwWindowShouldClose(glfwWindow)) {

            glfwPollEvents();
            
            
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);            


            //Map.drawMap2D();
            //Player.drawPlayer();
            Player.controller();
            Ray.drawRays3D();



            glfwSwapBuffers(glfwWindow);
            glfwPollEvents();

        }

    }
}
