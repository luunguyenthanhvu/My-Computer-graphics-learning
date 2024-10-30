package com.example.lab3.bai1;

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
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Squares extends GLJPanel implements GLEventListener {

  private GLU glu;
  private GLUT glut;
  private float rotateX = 0.0f; // Góc xoay quanh trục X
  private float rotateY = 0.0f; // Góc xoay quanh trục Y

  public Squares() {
    this.addGLEventListener(this);

    // thêm lắng nghe sự kiện bàn phím
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_UP:
            rotateX -= 5.0f; // Xoay ngược trục X
            break;
          case KeyEvent.VK_DOWN:
            rotateX += 5.0f; // Xoay theo trục X
            break;
          case KeyEvent.VK_LEFT:
            rotateY -= 5.0f; // Xoay ngược trục Y
            break;
          case KeyEvent.VK_RIGHT:
            rotateY += 5.0f; // Xoay theo trục Y
            break;
        }
      }
    });
  }

  @Override
  public void init(GLAutoDrawable glAutoDrawable) {
    GL2 gl = glAutoDrawable.getGL().getGL2();
    glu = new GLU();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClearDepth(1.0f);
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthFunc(GL.GL_LEQUAL);
    gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
    gl.glShadeModel(GL_SMOOTH);
  }

  @Override
  public void dispose(GLAutoDrawable glAutoDrawable) {

  }

  @Override
  public void display(GLAutoDrawable glAutoDrawable) {
    GL2 gl = glAutoDrawable.getGL().getGL2();

    gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    gl.glLoadIdentity(); // reset the model view matrix

    // translate into the screen
    gl.glTranslatef(0.0f, 0.0f, -6.0f);
    gl.glRotated(rotateX, 1.0f, 0.0f, 0.0f);
    gl.glRotatef(rotateY, 0.0f, 1.0f, 0.0f);

    drawPyramid(gl);
  }

  @Override
  public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
    GL2 gl = glAutoDrawable.getGL().getGL2();
    if (height == 0) {
      height = 1;
    }

    float aspect = width / height;

    gl.glViewport(0, 0, width, height);
    gl.glMatrixMode(GL_PROJECTION);
    gl.glLoadIdentity();
    // góc nhìn phối cảnh cho 3D
    glu.gluPerspective(45.0, aspect, 0.1, 100.0);
    gl.glMatrixMode(GL_MODELVIEW);
    gl.glLoadIdentity();
  }

  public void drawPyramid(GL2 gl) {
    gl.glBegin(GL2.GL_QUADS);
    // Mặt trước (Màu đỏ)
    gl.glColor3f(1.0f, 0.0f, 0.0f);
    gl.glVertex3f(-1.0f, 1.0f, 1.0f);
    gl.glVertex3f(1.0f, 1.0f, 1.0f);
    gl.glVertex3f(1.0f, -1.0f, 1.0f);
    gl.glVertex3f(-1.0f, -1.0f, 1.0f);

    // Mặt phải
    gl.glColor3f(0.0f, 1.0f, 0.0f);
    gl.glVertex3f(1.0f, 1.0f, 1.0f);
    gl.glVertex3f(1.0f, 1.0f, -1.0f);
    gl.glVertex3f(1.0f, -1.0f, -1.0f);
    gl.glVertex3f(1.0f, -1.0f, 1.0f);

    // Mặt sau
    gl.glColor3f(1.0f, 0.0f, 1.0f);
    gl.glVertex3f(1.0f, 1.0f, -1.0f);
    gl.glVertex3f(-1.0f, 1.0f, -1.0f);
    gl.glVertex3f(-1.0f, -1.0f, -1.0f);
    gl.glVertex3f(1.0f, -1.0f, -1.0f);

    // Mặt trái
    gl.glColor3f(1.0f, 1.0f, 1.0f);
    gl.glVertex3f(-1.0f, 1.0f, -1.0f);
    gl.glVertex3f(-1.0f, 1.0f, 1.0f);
    gl.glVertex3f(-1.0f, -1.0f, 1.0f);
    gl.glVertex3f(-1.0f, -1.0f, -1.0f);

    // MẶt đáy
    gl.glColor3f(0.5f, 0.5f, 0.5f);
    gl.glVertex3f(-1.0f, -1.0f, 1.0f);
    gl.glVertex3f(1.0f, -1.0f, 1.0f);
    gl.glVertex3f(1.0f, -1.0f, -1.0f);
    gl.glVertex3f(-1.0f, -1.0f, -1.0f);

    // Mặt đỉnh
    gl.glColor3f(0.5f, 1.5f, 0.5f);
    gl.glVertex3f(-1.0f, 1.0f, 1.0f);
    gl.glVertex3f(1.0f, 1.0f, 1.0f);
    gl.glVertex3f(1.0f, 1.0f, -1.0f);
    gl.glVertex3f(-1.0f, 1.0f, -1.0f);

    gl.glEnd();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        // create the OpenGL rendering canvas
        GLJPanel panel = new Squares();
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
        frame.setTitle("Triangle lab3");
        frame.pack();
        frame.setVisible(true);
        animator.start();
      }
    });
  }
}
