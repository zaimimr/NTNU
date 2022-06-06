import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;

public class Oving13_1 extends GLCanvas implements GLEventListener {
    //Definerer konstanter
    private static String TITLE = "Oving 13";
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 600;
    private static final int FPS = 60;
    //Setup openGL Graphics Render
    private GLU glu;
    private float angle = 0.0f;
    //Points
    private double[][] punkter = {{0.0,2.0,0.0},{1.5,1.5,0.0},{2.0,0.0,0.0},{1.5,-1.5,0.0},{0.0,-2.0,0.0},{-1.5,-1.5,0.0},{-2.0,0.0,0.0},{-1.5,1.5,0.0}};

    public Oving13_1(){this.addGLEventListener(this);}

    public static void main(String[] args) {
        GLCanvas canvas = new Oving13_1();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);

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
        GL2 gl = glAutoDrawable.getGL().getGL2();
        glu = new GLU();                         // get GL Utilities
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
        gl.glClearDepth(1.0f);      // set clear depth value to farthest
        gl.glEnable(GL2.GL_DEPTH_TEST); // enables depth testing
        gl.glDepthFunc(GL2.GL_LEQUAL);  // the type of depth test to do
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST); // best perspective correction
        gl.glShadeModel(GL2.GL_SMOOTH); // blends colors nicely, and smoothes out lighting
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        if(height == 0) height = 1;
        float aspect = (float)width / height;

        gl.glViewport(0,0,width,height);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0,aspect,0.1,100.0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        //--------Tegn Point------------
        gl.glLoadIdentity();
        gl.glTranslatef(-6.0f,4.0f,-18.0f);
        gl.glRotated(angle, 0.0f,1.0f,0.0f);

        gl.glBegin(GL2.GL_POINT);

        //Point
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[0],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[1],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[2],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[3],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[4],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[5],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[6],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[7],0);

        gl.glEnd();

        //--------Tegn Lines------------
        gl.glLoadIdentity();
        gl.glTranslatef(-3.0f,6.0f,-18.0f);
        gl.glRotated(angle, 0.0f,1.0f,0.0f);

        gl.glBegin(GL2.GL_LINES);

        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[0],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[1],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[2],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[3],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[4],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[5],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[6],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[7],0);

        gl.glEnd();

        //--------Tegn lines strip------------
        gl.glLoadIdentity();
        gl.glTranslatef(1.5f,4.0f,-18.0f);
        gl.glRotated(angle, 0.0f,1.0f,0.0f);

        gl.glBegin(GL2.GL_LINE_STRIP);

        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[0],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[1],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[2],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[3],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[4],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[5],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[6],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[7],0);

        gl.glEnd();

        //--------Tegn line loop------------
        gl.glLoadIdentity();
        gl.glTranslatef(6.0f,4.0f,-18.0f);
        gl.glRotated(angle, 0.0f,1.0f,0.0f);

        gl.glBegin(GL2.GL_LINE_LOOP);

        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[0],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[1],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[2],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[3],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[4],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[5],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[6],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[7],0);

        gl.glEnd();

        //--------Tegn triangle------------
        gl.glLoadIdentity();
        gl.glTranslatef(-5.5f,-1.5f,-18.0f);
        gl.glRotated(angle, 0.0f,1.0f,0.0f);

        gl.glBegin(GL2.GL_TRIANGLES);

        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[0],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[1],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[2],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[3],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[4],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[5],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[6],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[7],0);

        gl.glEnd();

        //--------Tegn triangle strip------------
        gl.glLoadIdentity();
        gl.glTranslatef(-1.0f,-1.5f,-18.0f);
        gl.glRotated(angle, 0.0f,1.0f,0.0f);

        gl.glBegin(GL2.GL_TRIANGLE_STRIP);

        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[0],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[1],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[2],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[3],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[4],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[5],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[6],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[7],0);

        gl.glEnd();

        //--------Tegn triangle_fan------------
        gl.glLoadIdentity();
        gl.glTranslatef(3.0f,-1.5f,-18.0f);
        gl.glRotated(angle, 0.0f,1.0f,0.0f);

        gl.glBegin(GL2.GL_TRIANGLE_FAN);

        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[0],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[1],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[2],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[3],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[4],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[5],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[6],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[7],0);

        gl.glEnd();

        //--------Tegn quads------------
        gl.glLoadIdentity();
        gl.glTranslatef(7.5f,-1.5f,-18.0f);
        gl.glRotated(angle, 0.0f,1.0f,0.0f);

        gl.glBegin(GL2.GL_QUADS);

        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[0],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[1],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[2],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[3],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[4],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[5],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[6],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[7],0);

        gl.glEnd();

        //--------Tegn quad strip------------
        gl.glLoadIdentity();
        gl.glTranslatef(-3.0f,-5.5f,-18.0f);
        gl.glRotated(angle, 0.0f,1.0f,0.0f);

        gl.glBegin(GL2.GL_QUAD_STRIP);

        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[0],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[1],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[2],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[3],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[4],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[5],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[6],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[7],0);

        gl.glEnd();

        //--------Tegn polygon------------
        gl.glLoadIdentity();
        gl.glTranslatef(1.5f,-5.5f,-18.0f);
        gl.glRotated(angle, 0.0f,1.0f,0.0f);

        gl.glBegin(GL2.GL_POLYGON);

        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[0],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[1],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[2],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[3],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[4],0);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3dv(punkter[5],0);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3dv(punkter[6],0);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3dv(punkter[7],0);

        gl.glEnd();

        angle += 1.5f;

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }




}
