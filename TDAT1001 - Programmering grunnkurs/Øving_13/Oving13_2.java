import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

import javax.swing.*;
import java.awt.*;

public class Oving13_2 extends GLCanvas implements GLEventListener {

    private static GLCanvas canvas;

    private static String TITLE = "Oving 12";
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 600;

    private static final int FPS = 60;

    private GL2 gl;
    private GLU glu;
    private GLUT glut;

    private float angle = 3.0f;

    public Oving13_2(){this.addGLEventListener(this);}

    public static void main(String[] args) {
        GLCanvas canvas = new Oving13_2();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        final FPSAnimator animator = new FPSAnimator(canvas,FPS);

        final JFrame frame = new JFrame();
        frame.getContentPane().add(canvas);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle(TITLE);
        frame.pack();
        frame.setVisible(true);
        animator.start();

    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL().getGL2();
        glu = new GLU();
        gl.glClearColor(0.0f,0.0f,0.0f,0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT,GL2.GL_NICEST);
        gl.glShadeModel(GL2.GL_SMOOTH);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        gl = glAutoDrawable.getGL().getGL2();

        if (height == 0) height = 1;
        float aspect = (float)width/height;

        gl.glViewport(0,0,width,height);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0,aspect,0.1,100.0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        //---------
        drawKube1(glAutoDrawable);
        drawKube2(glAutoDrawable);
        //---------
        angle += 0.1;

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void drawKube1(GLAutoDrawable glAutoDrawable){
        gl = glAutoDrawable.getGL().getGL2();
        gl.glLoadIdentity();
        gl.glTranslatef(-1.5f,0.0f,-6.0f);
        gl.glRotatef(angle,0.1f,1.0f,-0.1f);

        gl.glBegin(GL2.GL_LINE_LOOP);

        // Top-face
        gl.glColor3f(0.0f, 1.0f, 0.0f); // green
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);

        // Bottom-face
        gl.glColor3f(1.0f, 0.5f, 0.0f); // orange
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);

        // Front-face
        gl.glColor3f(1.0f, 0.0f, 0.0f); // red
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);

        // Back-face
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);

        // Left-face
        gl.glColor3f(0.0f, 0.0f, 1.0f); // blue
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);

        // Right-face
        gl.glColor3f(1.0f, 0.0f, 1.0f); // violet
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);

        gl.glEnd(); // of the color cube
    }

    public void drawKube2(GLAutoDrawable glAutoDrawable){
        gl = glAutoDrawable.getGL().getGL2();
        glut = new GLUT();
        gl.glLoadIdentity();
        gl.glTranslatef(1.5f,0.0f,-6.0f);
        gl.glRotatef(angle,0.1f,1.0f,-0.1f);
        gl.glScalef(2.0f, 2.0f, 2.0f);
        glut.glutWireCube(1.0f);
    }

}
