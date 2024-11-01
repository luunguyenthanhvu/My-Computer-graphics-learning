package com.example.lab4.bai5;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
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
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Bai5 extends GLJPanel implements GLEventListener {

  private GLU glu;
  private GLUT glut;
  private float earthAngle = 0.0f; // Earth rotation angle
  private float earthOrbitAngle = 0.0f; // Earth orbit angle around the Sun
  private float moonAngle = 0.0f; // Moon orbit angle around the Earth
  private boolean isRotating = false; // Flag to control rotation
  private int rotationCount = 0; // Count of rotations completed
  private float sunRotationAngle = 0.0f;
  private boolean isWireframe = false;
  // Date and time variables
  private Calendar calendar;

  public Bai5() {
    this.addGLEventListener(this);
    // Initialize calendar to current date
    calendar = Calendar.getInstance();

    // Add keyboard listener to start rotation
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'd') {
          isRotating = !isRotating; // Toggle rotation on 'd' key press
        } else if (e.getKeyChar() == 'w') {
          isWireframe = true;
        } else if (e.getKeyChar() == 's') {
          isWireframe = false;
        }
      }
    });
  }

  @Override
  public void init(GLAutoDrawable glAutoDrawable) {
    GL2 gl = glAutoDrawable.getGL().getGL2();
    glu = new GLU();

    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    gl.glClearDepth(1.0f);
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthFunc(GL.GL_LEQUAL);
    gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
    gl.glShadeModel(GL_SMOOTH);
    glut = new GLUT(); // Initialize GLUT
  }

  @Override
  public void dispose(GLAutoDrawable glAutoDrawable) {
  }

  @Override
  public void display(GLAutoDrawable glAutoDrawable) {
    GL2 gl = glAutoDrawable.getGL().getGL2();

    // Clear screen and depth buffer
    gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    gl.glLoadIdentity(); // Reset the model view matrix

    // Set camera position
    gl.glTranslatef(0.0f, 0.0f, -20.0f);

    if (isRotating) {
      // Update sun rotation
      sunRotationAngle += 360.0f / 25.0f; // Adjust speed as needed for 25 days rotation
      if (sunRotationAngle >= 360.0f) {
        sunRotationAngle -= 360.0f; // Reset angle to prevent overflow
      }
    }
    // Draw the Sun with rotation
    gl.glPushMatrix();
    gl.glRotatef(sunRotationAngle, 0.0f, 1.0f, 0.0f); // Rotate the Sun around its axis
    gl.glColor3f(1.0f, 1.0f, 0.0f); // Yellow color for the Sun
    GLUquadric sun = glu.gluNewQuadric();
    glu.gluQuadricDrawStyle(sun, isWireframe ? GLU.GLU_FILL : GLU.GLU_LINE);
    glu.gluSphere(sun, 3.0, 32, 32); // Radius 3.0
    gl.glPopMatrix();

    // Update angles if rotating
    if (isRotating) {
      earthOrbitAngle += 360.0f / 365.0f; // Complete orbit in 365 days
      moonAngle += 360.0f / 30.0f; // Complete orbit in 30 days
      earthAngle += 360.0f; // Complete rotation on its axis in 1 day

      // Check if a full rotation has completed
      if (earthAngle >= 360.0f) {
        earthAngle -= 360.0f; // Reset angle to prevent overflow
        rotationCount++; // Increment rotation count

        // Update date every time the earth completes a rotation
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Add one day
      }
    }

    // Draw the Earth
    gl.glPushMatrix();
    gl.glRotatef(earthOrbitAngle, 0.0f, 1.0f, 0.0f); // Orbiting around the Sun
    gl.glTranslatef(6.0f, 0.0f, 0.0f); // Move Earth away from Sun
    gl.glRotatef(earthAngle, 0.0f, 1.0f, 0.0f); // Rotate on its axis
    gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue color for Earth
    GLUquadric earth = glu.gluNewQuadric();
    glu.gluQuadricDrawStyle(earth, isWireframe ? GLU.GLU_FILL : GLU.GLU_LINE);
    glu.gluSphere(earth, 1.0, 32, 32); // Radius 1.0
    gl.glPopMatrix();

    // Draw the Moon
    gl.glPushMatrix();
    gl.glRotatef(earthOrbitAngle, 0.0f, 1.0f, 0.0f); // Keep moon in orbit with Earth
    gl.glTranslatef(6.0f, 0.0f, 0.0f); // Move to Earth's position
    gl.glRotatef(moonAngle, 0.0f, 1.0f, 0.0f); // Orbit around the Earth
    gl.glTranslatef(2.0f, 0.0f, 0.0f); // Move Moon away from Earth
    gl.glColor3f(0.8f, 0.8f, 0.8f); // Gray color for the Moon
    GLUquadric moon = glu.gluNewQuadric();
    glu.gluQuadricDrawStyle(moon, isWireframe ? GLU.GLU_FILL : GLU.GLU_LINE);
    glu.gluSphere(moon, 0.5, 32, 32); // Radius 0.5
    gl.glPopMatrix();

    // Draw rotation count, current date, and current time
    gl.glColor3f(0.0f, 0.0f, 0.0f); // Black color for text
    String dateString = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());
    String rotationString = "Rotations: " + rotationCount;
    drawText(gl, -5.0f, 5.0f, dateString);
    drawText(gl, -5.0f, 4.5f, rotationString);
  }

  private void drawText(GL2 gl, float x, float y, String text) {
    gl.glRasterPos2f(x, y); // Set position for text
    for (char c : text.toCharArray()) {
      glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18, c); // Draw each character
    }
  }

  @Override
  public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
    GL2 gl = glAutoDrawable.getGL().getGL2();
    if (height == 0) {
      height = 1;
    }

    float aspect = (float) width / height;

    gl.glViewport(0, 0, width, height);
    gl.glMatrixMode(GL_PROJECTION);
    gl.glLoadIdentity();
    // Perspective view for 3D
    glu.gluPerspective(45.0, aspect, 0.1, 100.0);
    gl.glMatrixMode(GL_MODELVIEW);
    gl.glLoadIdentity();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      // Create OpenGL canvas
      GLJPanel panel = new Bai5();
      panel.setPreferredSize(new Dimension(1060, 600));

      // Create animator
      FPSAnimator animator = new FPSAnimator(panel, 60, true);
      JFrame frame = new JFrame();
      frame.getContentPane().add(panel);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
          animator.stop();
        }
      });
      frame.pack();
      frame.setResizable(false);
      frame.setVisible(true);
      animator.start(); // Start animation
      panel.requestFocusInWindow(); // Set focus for key events
    });
  }
}
