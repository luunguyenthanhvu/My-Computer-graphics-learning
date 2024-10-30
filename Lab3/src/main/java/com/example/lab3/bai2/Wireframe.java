package com.example.lab3.bai2;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import com.example.lab3.bai1.Squares;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Wireframe extends GLJPanel implements GLEventListener {

  private GLU glu;
  private GLUT glut;

  // Biến lưu vị trí hiện tại của hình cầu
  private float x = -10.0f;
  private float y = 10.0f;

  // Tốc độ di chuyển theo các trục x và y
  private float speedX = 0.05f;
  private float speedY = -0.03f;

  public Wireframe() {
    this.addGLEventListener(this);
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
    // Xử lý khi không sử dụng
  }

  @Override
  public void display(GLAutoDrawable glAutoDrawable) {
    GL2 gl = glAutoDrawable.getGL().getGL2();

    // Xóa màn hình và bộ đệm độ sâu
    gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    gl.glLoadIdentity(); // reset the model view matrix

    // Cập nhật vị trí
    x += speedX;
    y += speedY;

    // Kiểm tra nếu hình cầu đã chạm các giới hạn cửa sổ
    if (x > 10.0f || x < -10.0f) {
      speedX = -speedX; // Đảo chiều di chuyển theo trục X
    }

    if (y < -10.0f || y > 10.0f) {
      speedY = -speedY; // Đảo chiều di chuyển theo trục Y
    }

    // Di chuyển hình cầu tới vị trí hiện tại
    gl.glTranslatef(x, y, -20.0f);

    // Vẽ hình cầu dạng lưới
    drawWireframe(gl);
  }

  public void drawWireframe(GL2 gl) {
    GLUquadric quadric = glu.gluNewQuadric();
    glu.gluQuadricDrawStyle(quadric, GLU.GLU_LINE); // Vẽ dạng lưới (wireframe)
    gl.glColor3f(1.0f, 1.0f, 1.0f);
    glu.gluSphere(quadric, 2.0f, 30, 30);
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
      GLJPanel panel = new Wireframe();
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

