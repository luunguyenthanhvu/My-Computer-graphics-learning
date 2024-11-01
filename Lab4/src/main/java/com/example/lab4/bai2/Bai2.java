package com.example.lab4.bai2;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Bai2 extends GLJPanel implements GLEventListener {

  private GLU glu;
  private GLUT glut;

  private float rotateX = 0.0f; // Góc xoay quanh trục X
  private float rotateY = 0.0f; // Góc xoay quanh trục Y

  public Bai2(){
    glu = new GLU();
    glut = new GLUT();
    this.addGLEventListener(this);
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        // Xử lý khi nhấn các phím mũi tên để xoay hình
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
        repaint(); // Vẽ lại khi có thay đổi về góc xoay
      }
    });
    this.setFocusable(true); // Đảm bảo panel nhận sự kiện bàn phím
  }

  @Override
  public void init(GLAutoDrawable glAutoDrawable) {
    GL2 gl = glAutoDrawable.getGL().getGL2();
    gl.glClearColor(0.8f, 0.8f, 0.8f, 1.0f);  // Màu nền xám
    gl.glEnable(GL2.GL_DEPTH_TEST);            // Bật kiểm tra chiều sâu
  }

  @Override
  public void dispose(GLAutoDrawable glAutoDrawable) {

  }

  @Override

  public void display(GLAutoDrawable glAutoDrawable) {
    GL2 gl = glAutoDrawable.getGL().getGL2();
    gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
    gl.glLoadIdentity();
    glu.gluLookAt(0.0, 0.0, 500.0,  // Vị trí camera (nhìn từ phía trước)
        0.0, 0.0, 0.0,    // Tâm nhìn của camera (điểm giữa của bàn và ấm trà)
        0.0, 1.0, 0.0);   // Hướng lên của trục y


    drawTable(gl);
    drawTeapot(gl);

    gl.glFlush();
  }


  public void drawTeapot( GL2 gl) {

    GLUT glut = new GLUT();                  // Khởi tạo đối tượng GLUT
    gl.glPushMatrix();
    gl.glTranslated(5.0, 90.0, 0.0);         // Đặt ấm trà cao hơn mặt bàn
    gl.glColor3f(1.0f, 0.0f, 0.0f);          // Đặt màu đỏ cho ấm trà
    gl.glScaled(1.0, 1.0, 1);              // Giảm kích thước ấm trà
    glut.glutSolidTeapot(20);                // Bán kính cơ bản của ấm trà là 20
    gl.glPopMatrix();
  }

  public void drawTable( GL2 gl) {
    gl.glColor3f(0,0,0);
    // Vẽ mặt bàn trên cùng
    gl.glPushMatrix();
    gl.glTranslated(0.0, 50.0, 0.0);             // Đặt mặt bàn trên cùng ở độ cao y = 50
    gl.glScaled(2.0, 0.1, 1.0);                  // Kích thước mặt bàn: 200x10x100
    glut.glutSolidCube(100);                     // Kích thước cơ bản của Cube là 100
    gl.glPopMatrix();

    // Vẽ mặt bàn thứ hai (dưới mặt bàn trên)
    gl.glPushMatrix();
    gl.glTranslated(0.0, 20.0, 0.0);             // Đặt mặt bàn dưới ở độ cao y = 20
    gl.glScaled(2.0, 0.1, 1.0);                  // Kích thước mặt bàn: 200x10x100
    glut.glutSolidCube(100);                     // Kích thước cơ bản của Cube là 100
    gl.glPopMatrix();

    // Vẽ 4 chân bàn (Cylinders)
    for (int i = -1; i <= 1; i += 2) {           // Duyệt qua các vị trí chân bàn
      for (int j = -1; j <= 1; j += 2) {
        gl.glPushMatrix();
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glTranslated(i * 90.0, -10.0, j * 40.0); // Vị trí chân bàn
        gl.glRotated(-90, 1.0, 0.0, 0.0);           // Xoay để đặt dọc
        GLUquadric quad = glu.gluNewQuadric();
        glu.gluCylinder(quad, 5.0, 5.0, 50.0, 30, 30); // Chân bàn cao 50, bán kính 5
        glu.gluDeleteQuadric(quad);
        gl.glPopMatrix();
      }
    }
  }

  @Override
  public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
    GL2 gl = glAutoDrawable.getGL().getGL2();
    if (height == 0) height = 1;             // Tránh chia cho 0
    float aspect = (float) width / height;

    gl.glViewport(0, 0, width, height);
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    glu.gluPerspective(45.0, aspect, 1.0, 500.0);
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() ->{
      GLJPanel panel = new Bai2();
      panel.setPreferredSize(new Dimension(640, 480));
      FPSAnimator animator = new FPSAnimator(panel, 60, true);
      JFrame frame = new JFrame();
      frame.getContentPane().add(panel);
      frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          new Thread(() -> {
            if (animator.isStarted()) {
              animator.stop();
            }
            System.exit(0);
          }).start();
        }
      });

      frame.setTitle("Teapot with Table");
      frame.pack();
      frame.setVisible(true);
      animator.start(); // Bắt
    });

  }
}