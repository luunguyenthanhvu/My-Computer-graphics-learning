package com.example.lab4.bai1;

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

public class Bai1a extends GLJPanel implements GLEventListener {

  private GLU glu;
  private GLUT glut;
  private float rotateX = -90.0f; // Góc xoay quanh trục X
  private float rotateY = 0.0f; // Góc xoay quanh trục Y
  private float scaleFactor = 1.0f; // Tỷ lệ ban đầu
  private float scaleSpeed = 0.01f; // Tốc độ tăng tỷ lệ

  public Bai1a() {
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

    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
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

    // Xóa màn hình và bộ đệm độ sâu
    gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    gl.glLoadIdentity(); // reset the model view matrix

    // Thiết lập vị trí của camera
    gl.glTranslatef(0.0f, 0.0f, -10.0f);
    gl.glRotated(rotateX, 1.0f, 0.0f, 0.0f);
    gl.glRotatef(rotateY, 0.0f, 1.0f, 0.0f);

    // Thay đổi kích thước hình trụ
    gl.glScalef(scaleFactor, scaleFactor, scaleFactor);

    // Tạo một đối tượng GLUquadric để vẽ hình trụ
    GLUquadric quadric = glu.gluNewQuadric();
    glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL); // Tùy chọn vẽ hình nón dạng đầy

    // Thiết lập màu cho hình nón
    gl.glColor3f(0.5f, 0.5f, 1.0f);

    // Vẽ hình nón
    glu.gluCylinder(quadric, 1.0, 0.0, 0.75, 32, 32);
    // Đường kính đáy là 1.0, đường kính đỉnh là 0.0 (nón), chiều cao 0.75

    // Dịch chuyển để vẽ hình trụ ngay bên dưới hình nón
    gl.glTranslatef(0.0f, 0.0f, -1.0f);

    // Thiết lập màu cho hình trụ
    gl.glColor3f(0.7f, 0.2f, 0.2f);
    //glu.gluCylinder(quadric, baseRadius, topRadius, height, slices, stacks);
    // Vẽ hình trụ
    glu.gluCylinder(quadric, 0.5, 0.5, 1.0, 32, 32);

    gl.glPushMatrix();
    gl.glTranslatef(0.0f, 0.0f, 0.0f); // Di chuyển tới vị trí đáy
    glu.gluDisk(quadric, 0, 0.5, 20, 1); // Đáy với bán kính 0.5
    gl.glPopMatrix();

// Vẽ nắp của hình trụ
    gl.glPushMatrix();
    gl.glTranslatef(0.0f, 0.0f, 1.0f); // Di chuyển tới vị trí đỉnh
    glu.gluDisk(quadric, 0, 0.5, 20, 1); // Nắp với bán kính 0.5
    gl.glPopMatrix();

    // Tăng tỷ lệ để hình trụ phóng to
    if (scaleFactor <= 2.0) {
      scaleFactor += scaleSpeed;
    }
    // Đặt lại ma trận để không ảnh hưởng đến các hình tiếp theo
    gl.glLoadIdentity();

    // Xóa đối tượng quadric sau khi vẽ xong để giải phóng bộ nhớ
    glu.gluDeleteQuadric(quadric);
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
    // góc nhìn phối cảnh cho 3D
    glu.gluPerspective(45.0, aspect, 0.1, 100.0);
    gl.glMatrixMode(GL_MODELVIEW);
    gl.glLoadIdentity();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      // create the OpenGL rendering canvas
      GLJPanel panel = new Bai1a();
      panel.setPreferredSize(new Dimension(1060, 600));

      // Create a animator
      FPSAnimator animator = new FPSAnimator(panel, 60, true);
      JFrame frame = new JFrame();
      frame.getContentPane().add(panel);
      frame.addWindowFocusListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
          new Thread(() -> {
            if (animator.isStarted()) {
              animator.stop();
            }
            System.exit(0);
          }).start();
        }
      });
      frame.setTitle("Wireframe lab3");
      frame.pack();
      frame.setVisible(true);
      animator.start();
    });
  }
}
