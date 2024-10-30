package com.example.lab1;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_NICEST;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class JOGL2Template extends GLJPanel implements GLEventListener {

  // Setup OpenGL Graphics Renderer
  /**
   * For the GL Utility
   */
  private GLU glu;

  /**
   * OpenGL Utility Toolkit
   */
  private GLUT glut;

  /**
   * Constructor to set up the GUI for this componentS
   */
  public JOGL2Template() {
    this.addGLEventListener(this);
  }

  /**
   * Called back immediately after the OpenGL context is initialized. Can be used to perform
   * one-time initialization. Run only once.
   *
   * @param glAutoDrawable
   */
  @Override
  public void init(GLAutoDrawable glAutoDrawable) {
    // Get the OpenGL graphics context
    GL2 gl = glAutoDrawable.getGL().getGL2();

    // get GL Utilities
    glu = new GLU();

    // set background color
    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    // set clear depth value to the farthest
    gl.glClearDepth(1.0f);
    // enable depth testing
    gl.glEnable(GL.GL_DEPTH_TEST);
    // the type of depth to do
    gl.glDepthFunc(GL.GL_LEQUAL);
    // best perspective correction
    gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    // blends colors nicely and smoothes out lighting
    gl.glShadeModel(GL_SMOOTH);
  }

  @Override
  public void dispose(GLAutoDrawable glAutoDrawable) {

  }

  /**
   * Called back by the animator to perform rendering.
   *
   * @param glAutoDrawable
   */
  @Override
  public void display(GLAutoDrawable glAutoDrawable) {
    GL2 gl = glAutoDrawable.getGL().getGL2();

    gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    gl.glLoadIdentity(); // reset the model view matrix

    // translate into the screen
    gl.glTranslatef(0.0f, 0.0f, -6.0f);
    gl.glBegin(GL_TRIANGLES);
    gl.glVertex3f(0.0f, 1.0f, 0.0f);
    gl.glVertex3f(-1.0f, -1.0f, 0.0f);
    gl.glVertex3f(1.0f, -1.0f, 0.0f);
    gl.glEnd();
  }

  /**
   * Call back handler for window re-size event. Also called when the drawable is first set to
   * visible.
   *
   * @param glAutoDrawable
   * @param x
   * @param y
   * @param width
   * @param height
   */
  @Override
  public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
    GL2 gl = glAutoDrawable.getGL().getGL2();
    if (height == 0) {
      height = 1;
    }

    float aspect = width / height;

    // set the view port (display area) to cover the entire window
    gl.glViewport(0, 0, width, height);

    // setup perspective projection, with aspect ratio matches viewport
    gl.glMatrixMode(GL_PROJECTION);
    // reset projection matrix
    gl.glLoadIdentity();
    glu.gluPerspective(45.0, aspect, 0.1, 100.0); // fovy, aspect,zNear, zFar

    // Enable the model-view transform

    gl.glMatrixMode(GL_MODELVIEW);

    gl.glLoadIdentity(); // reset
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        // create the OpenGL rendering canvas
        GLJPanel panel = new JOGL2Template();
        panel.setPreferredSize(new Dimension(640, 480));

        // Create a animator
        FPSAnimator animator = new FPSAnimator(panel, 60, true);
        JFrame frame = new JFrame();
        frame.getContentPane().add(panel);
        frame.addWindowFocusListener(new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            new Thread() {
              public void run() {
                if (animator.isStarted()) {
                  animator.stop();
                }
                System.exit(0);
              }
            }.start();
          }
        });
        frame.setTitle("JOGL2 Template");
        frame.pack();
        frame.setVisible(true);
        animator.start();
      }
    });
  }
}
