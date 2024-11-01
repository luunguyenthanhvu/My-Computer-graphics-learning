package com.example.lab4.bai3;

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


public class Bai3 extends GLJPanel implements GLEventListener {

  private GLU glu;
  private GLUT glut = new GLUT();

  private float rotateX = 0.0f; // Góc xoay quanh trục X
  private float rotateY = 0.0f; // Góc xoay quanh trục Y
  private float rotateRightArm = 0.0f;

  public Bai3() {
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

        switch (e.getKeyChar()) {
          case 'p' :
            rotateRightArm += 1.0f;
            break;

        }
        repaint();
        // Vẽ lại khi có thay đổi về góc xoay
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

    gl.glTranslatef(0.0f, 0.0f, -5.0f);
    gl.glRotated(rotateX, 1.0f, 0.0f, 0.0f);
    gl.glRotatef(rotateY, 0.0f, 1.0f, 0.0f);

    drawHead(gl);
    drawBody(gl);
    drawLeftArm(gl);
    drawRightArm(gl);
    drawLeftFoot(gl);
    drawRightFoot(gl);
  }

  public void drawHead(GL2 gl) {
    // Thiết lập màu cho hình khối
    gl.glPushMatrix();
    gl.glColor3f(1.0f, 1.5f, 1.0f);
    gl.glScaled(0.4f, 0.4f, 0.4f);
    gl.glTranslatef(0.0f, 2.85f, 0.0f);
    // Vẽ hình khối
    glut.glutSolidCube(1); // Kích thước của hình khối
    gl.glPopMatrix();
  }
  public void drawBody(GL2 gl) {
    // Thiết lập màu cho thân
    gl.glPushMatrix();
    gl.glColor3f(1.0f, 1.5f, 1.0f);
    gl.glTranslatef(0.0f, 0.0f, 0.0f); // Dịch chuyển thân xuống dưới cái đầu
    gl.glScaled(1.0f, 1.5f, 1.0f); // Thay đổi kích thước thân
    glut.glutSolidCube(1); // Vẽ hình khối cho thân
    gl.glPopMatrix();
  }

  public void drawLeftArm(GL2 gl) {
    // Thiết lập màu cho tay trái

    // tay trái gồm 3 phần cánh tay, cẳng tay, bàn tay

    // cánh tay
    gl.glPushMatrix();
    gl.glColor3f(1.0f, 1.5f, 1.0f);
    gl.glTranslatef(0.75f, 0.5f, 0.0f); // Dịch chuyển cánh tay xuống cạnh thân
    gl.glScaled(0.25f, 0.5f, 0.25f); // Thay đổi kích thước cánh tay
    glut.glutSolidCube(1); // Vẽ hình khối cho cánh tay
    gl.glPopMatrix();

    // cẳng tay
    gl.glPushMatrix();
    gl.glColor3f(1.0f, 1.5f, 1.0f);
    gl.glTranslatef(0.75f, -0.05f, 0.0f); // Dịch chuyển cánh tay xuống cạnh cánh tay
    gl.glScaled(0.25f, 0.5f, 0.25f); // Thay đổi kích thước cẳng tay
    glut.glutSolidCube(1); // Vẽ hình khối cho cẳng tay
    gl.glPopMatrix();


    // bàn tay
    gl.glPushMatrix();
    gl.glColor3f(1.0f, 1.5f, 1.0f);
    gl.glTranslatef(0.75f, -0.45f, 0.0f); // Dịch chuyển cánh tay xuống cạnh cẳng tay
    gl.glScaled(0.2f, 0.2f, 0.25f); // Thay đổi kích thước bàn tay
    glut.glutSolidCube(1); // Vẽ hình khối cho bàn tay
    gl.glPopMatrix();
  }

  public void drawRightArm(GL2 gl) {
    // Thiết lập màu cho tay trái

    // tay trái gồm 3 phần cánh tay, cẳng tay, bàn tay

    // cánh tay
    gl.glPushMatrix();
    gl.glColor3f(1.0f, 1.5f, 1.0f);
    gl.glTranslatef(-0.75f, 0.5f, 0.0f); // Dịch chuyển cánh tay xuống cạnh thân
    gl.glScaled(0.25f, 0.5f, 0.25f); // Thay đổi kích thước cánh tay
    // xoay tay
    gl.glRotatef(1.0f, 0.0f, 0.0f, 1.0f);
    glut.glutSolidCube(1); // Vẽ hình khối cho cánh tay
    gl.glPopMatrix();

    // cẳng tay
    gl.glPushMatrix();
    gl.glColor3f(1.0f, 1.5f, 1.0f);
    gl.glTranslatef(-0.75f, -0.05f, 0.0f); // Dịch chuyển cánh tay xuống cạnh cánh tay
    gl.glScaled(0.25f, 0.5f, 0.25f); // Thay đổi kích thước cẳng tay
    glut.glutSolidCube(1); // Vẽ hình khối cho cẳng tay
    gl.glPopMatrix();


    // bàn tay
    gl.glPushMatrix();
    gl.glColor3f(1.0f, 1.5f, 1.0f);
    gl.glTranslatef(-0.75f, -0.45f, 0.0f); // Dịch chuyển cánh tay xuống cạnh cẳng tay
    gl.glScaled(0.2f, 0.2f, 0.25f); // Thay đổi kích thước bàn tay
    glut.glutSolidCube(1); // Vẽ hình khối cho bàn tay
    gl.glPopMatrix();
  }

  public void drawLeftFoot(GL2 gl) {
    // Thiết lập màu cho chân trái

    gl.glPushMatrix();
    gl.glColor3f(1.0f, 1.5f, 1.0f);
    gl.glTranslatef(-0.25f, -1.15f, 0.0f); // Dịch chuyển cánh tay xuống cạnh thân
    gl.glScaled(0.35f, 0.5f, 0.35f); // Thay đổi kích thước cánh tay
    glut.glutSolidCube(1); // Vẽ hình khối cho cánh tay
    gl.glPopMatrix();

    // cẳng tay
    gl.glPushMatrix();
    gl.glColor3f(1.0f, 1.5f, 1.0f);
    gl.glTranslatef(-0.25f, -1.75f, 0.0f); // Dịch chuyển cánh tay xuống cạnh thân
    gl.glScaled(0.35f, 0.5f, 0.35f); // Thay đổi kích thước cánh tay
    glut.glutSolidCube(1); // Vẽ hình khối cho cánh tay
    gl.glPopMatrix();

  }

  public void drawRightFoot(GL2 gl) {

    gl.glPushMatrix();
    gl.glColor3f(1.0f, 1.5f, 1.0f);
    gl.glTranslatef(0.25f, -1.15f, 0.0f); // Dịch chuyển cánh tay xuống cạnh thân
    gl.glScaled(0.35f, 0.5f, 0.35f); // Thay đổi kích thước cánh tay
    glut.glutSolidCube(1); // Vẽ hình khối cho cánh tay
    gl.glPopMatrix();

    // cẳng tay
    gl.glPushMatrix();
    gl.glColor3f(1.0f, 1.5f, 1.0f);
    gl.glTranslatef(0.25f, -1.75f, 0.0f); // Dịch chuyển cánh tay xuống cạnh thân
    gl.glScaled(0.35f, 0.5f, 0.35f); // Thay đổi kích thước cánh tay
    glut.glutSolidCube(1); // Vẽ hình khối cho cánh tay
    gl.glPopMatrix();
  }
  @Override
  public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
    GL2 gl = glAutoDrawable.getGL().getGL2();
    if (height == 0) {
      height = 1;             // Tránh chia cho 0
    }
    float aspect = (float) width / height;

    gl.glViewport(0, 0, width, height);
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    glu.gluPerspective(45.0, aspect, 1.0, 500.0);
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      GLJPanel panel = new Bai3();
      panel.setPreferredSize(new Dimension(1000, 600));
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

      frame.setTitle("Lang Coc");
      frame.pack();
      frame.setVisible(true);
      animator.start(); // Bắt
    });

  }
}