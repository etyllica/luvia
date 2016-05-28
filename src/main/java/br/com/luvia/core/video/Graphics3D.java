package br.com.luvia.core.video;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLBase;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import org.jogamp.glg2d.GLGraphics2D;
import org.lwjgl.BufferUtils;

import br.com.abby.linear.AimPoint;
import br.com.abby.linear.BoundingBox3D;
import br.com.abby.linear.Camera;
import br.com.abby.linear.ColoredPoint3D;
import br.com.abby.linear.Frustrum;
import br.com.etyllica.awt.AWTGraphics;
import br.com.etyllica.core.linear.Point3D;
import br.com.luvia.graphics.Billboard;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.jogamp.opengl.util.texture.Texture;

public class Graphics3D extends AWTGraphics {

	private GLU glu;

	private GLAutoDrawable drawable;

	public static int DEFAULT_RESOLUTION = 16;

	public Graphics3D(int width, int heigth) {
		super(width,heigth);

		glu = new GLU(); // GL Utilities
	}

	public void setGraphics(Graphics2D graphics) {
		this.screen = graphics;
		this.screen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
	}

	public void setGraphics(GLGraphics2D graphics) {
		this.screen = graphics;
		this.screen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public GLAutoDrawable getDrawable() {
		return drawable;
	}

	public void setDrawable(GLAutoDrawable drawable) {
		this.drawable = drawable;
	}	

	public int[] getViewPort() {
		GL2 gl = drawable.getGL().getGL2();

		int viewport[] = new int[4];
		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

		return viewport;
	}

	public void drawLine(Point3D a, Point3D b) {
		GL2 gl = getGL2();

		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(a.getX(), a.getY(), a.getZ());
		gl.glVertex3d(b.getX(), b.getY(), b.getZ());
		gl.glEnd();
	}

	public void drawSphere(ColoredPoint3D point, double radius) {
		drawSphere(radius, point.getX(), point.getY(), point.getZ());
	}
	
	public void drawSphere(AimPoint point, double radius) {

		GL2 gl = drawable.getGL().getGL2();

		GLUquadric sphere = glu.gluNewQuadric();

		glu.gluQuadricDrawStyle(sphere, GLU.GLU_FILL);
		glu.gluQuadricTexture(sphere, true);
		glu.gluQuadricNormals(sphere, GLU.GLU_SMOOTH);

		// draw a sphere
		gl.glPushMatrix();                  
		gl.glTranslated(point.getX(), point.getY(), point.getZ());
		gl.glRotated(point.getAngleY(), 0, 1, 0);
		gl.glRotated(point.getAngleX(), 1, 0, 0);
		glu.gluSphere(sphere, radius, 32, 32);
		gl.glPopMatrix();
	}

	public void drawTile(double x, double y, double tileSize, Texture texture) {

		GL2 gl = drawable.getGL().getGL2();

		texture.enable(gl);
		texture.bind(gl);

		gl.glBegin(GL2.GL_QUADS);

		//(0,0)
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(x*tileSize, 0, y*tileSize);

		//(1,0)
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(x*tileSize+tileSize, 0, y*tileSize);

		//(1,1)
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(x*tileSize+tileSize, 0, y*tileSize+tileSize);

		//(0,1)
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(x*tileSize, 0, y*tileSize+tileSize);

		gl.glEnd();

		texture.disable(gl);
	}

	public double[] getModelView() {

		double modelView[] = new double[16];
		drawable.getGL().getGL2().glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelView, 0);

		return modelView;
	}

	public double[] getProjection() {

		double projection[] = new double[16];

		getGL2().glGetDoublev(GL2.GL_PROJECTION_MATRIX, projection, 0);

		return projection;
	}

	public ColoredPoint3D get3DPointerFromMouse(float mx, float my) {
		return get3DPointerFromMouse(mx, my, 0);
	}
	
	public void get3DPointFrom2D(float mx, float my, double[] out) {
		get3DPointFrom2D(mx, my, 0, out);
	}

	public double[] get2DPositionFromPoint(double px, double py, double pz) {

		double[] position = new double[3];

		int[] viewport = getViewPort();
		double[] modelview = getModelView();
		double[] projection = getProjection();

		glu.gluProject(px, py, pz, modelview, 0, projection, 0, viewport, 0, position, 0);

		return position;

	}

	public void get3DPointFrom2D(float mx, float my, float zPlane, double[] out) {
		GL2 gl = getGL2();
		
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
		IntBuffer viewport = BufferUtils.createIntBuffer(16);
		
		gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projection);
		gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, modelview);
		gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport);

		FloatBuffer positionNear = BufferUtils.createFloatBuffer(3);
		FloatBuffer positionFar = BufferUtils.createFloatBuffer(3);

		glu.gluUnProject(mx, viewport.get(3) - my, 0f, modelview, projection, viewport, positionNear);
		glu.gluUnProject(mx, viewport.get(3) - my, 1f, modelview, projection, viewport, positionFar);
		
		Vector3 v1 = new Vector3(positionNear.get(0), positionNear.get(1), positionNear.get(2));
		Vector3 v2 = new Vector3(positionFar.get(0), positionFar.get(1), positionFar.get(2));
		
		float t = (v1.y - zPlane) / (v1.y - v2.y);

		// so here are the desired (x, y) coordinates
		float fX = v1.x + (v2.x - v1.x) * t;
		float fZ = v1.z + (v2.z - v1.z) * t;

		out[0] = fX;
		out[1] = zPlane;
		out[2] = fZ;
	}
	
	public ColoredPoint3D get3DPointerFromMouse(float mx, float my, float zPlane) {

		double[] out = new double[3];
		get3DPointFrom2D(mx, my, zPlane, out);
		
		ColoredPoint3D point = new ColoredPoint3D(out[0], out[1], out[2]);

		return point;
	}
	
	public Ray getCameraRay(int mx, int my) {
		GL2 gl = getGL2();
		
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
		IntBuffer viewport = BufferUtils.createIntBuffer(16);
		
		gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projection);
		gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, modelview);
		gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport);

		FloatBuffer positionNear = BufferUtils.createFloatBuffer(3);
		FloatBuffer positionFar = BufferUtils.createFloatBuffer(3);

		glu.gluUnProject(mx, viewport.get(3) - my, 0f, modelview, projection, viewport, positionNear);
		glu.gluUnProject(mx, viewport.get(3) - my, 1f, modelview, projection, viewport, positionFar);

		Ray ray = new Ray(
		    new Vector3(
		        positionNear.get(0),
		        positionNear.get(1),
		        positionNear.get(2)),
		    new Vector3(
		        positionFar.get(0),
		        positionFar.get(1),
		        positionFar.get(2)));
		
		return ray;
	}

	public void updateCamera(Camera camera) {
		drawable.getGL().getGL2().glMatrixMode(GL2.GL_MODELVIEW);
		drawable.getGL().getGL2().glLoadIdentity();

		glu.gluLookAt( camera.getX(), camera.getY(), camera.getZ(), camera.getTarget().getX(), camera.getTarget().getY(), camera.getTarget().getZ(), 0, 1, 0 );
	}

	public void aimCamera(ColoredPoint3D cameraPoint, double angleX, double angleY, double angleZ) {
		GL2 gl = getGL2();

		gl.glMatrixMode(GL2.GL_MODELVIEW);

		gl.glLoadIdentity();

		gl.glRotated(360-angleX, 1, 0, 0);
		gl.glRotated(360-angleY, 0, 1, 0);
		gl.glRotated(360-angleZ, 0, 0, 1);

		gl.glTranslated(-cameraPoint.getX(), -cameraPoint.getY(), -cameraPoint.getZ());
	}

	public void aimCamera(AimPoint aim) {
		GL2 gl = getGL2();

		gl.glMatrixMode(GL2.GL_MODELVIEW);

		gl.glLoadIdentity();
		
		gl.glRotated(360-aim.getAngleX(), 1, 0, 0);
		gl.glRotated(360-aim.getAngleY(), 0, 1, 0);
		gl.glRotated(360-aim.getAngleZ(), 0, 0, 1);
		
		gl.glTranslated(-aim.getX(), -aim.getY(), -aim.getZ());
	}

	public GLBase getGL() {
		return drawable.getGL();
	}

	public GL2 getGL2() {
		return getGL().getGL2();
	}
	
	public GLBase getGL3() {
		return getGL().getGL3();
	}

	public GLU getGLU() {
		return glu;
	}

	public void drawPoint(Point3D point, Color color) {
		drawSphere(0.01, point.getX(), point.getY(), point.getZ(), 16, color);
	}

	public void drawSphere(double radius, double x,
			double y, double z, int resolution, Color color) {

		final int slices = resolution;
		final int stacks = resolution;

		GL2 gl = getGL2();

		gl.glPushMatrix();

		glSetColor(color);

		gl.glTranslated(x, y, z);

		GLUquadric sphere = generateQuadric(glu);

		glu.gluSphere(sphere, radius, slices, stacks);
		glu.gluDeleteQuadric(sphere);

		gl.glPopMatrix();
	}

	public void drawSphere(double radius, double x,
			double y, double z, int resolution) {

		final int slices = resolution;
		final int stacks = resolution;

		GL2 gl = getGL2();

		gl.glPushMatrix();

		// Draw sphere (possible styles: FILL, LINE, POINT).
		//gl.glColor3f(0.3f, 0.5f, 1f);

		gl.glTranslated(x, y, z);

		GLUquadric sphere = generateQuadric(glu);

		glu.gluSphere(sphere, radius, slices, stacks);
		glu.gluDeleteQuadric(sphere);

		gl.glPopMatrix();		
	}

	private GLUquadric generateQuadric(GLU glu) {		
		GLUquadric quadric = glu.gluNewQuadric();

		// Draw sphere (possible styles: FILL, LINE, POINT)
		glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
		glu.gluQuadricNormals(quadric, GLU.GLU_FLAT);
		glu.gluQuadricOrientation(quadric, GLU.GLU_OUTSIDE);

		return quadric;
	}

	public void drawSphere(double radius) {
		drawSphere(radius, 0, 0, 0, DEFAULT_RESOLUTION);
	}
	
	public void drawSphere(double radius, double x,
			double y, double z) {

		drawSphere(radius, x, y, z, 16);
	}

	public void drawSphere(double radius, int sections, int divisions) {
		drawSphere(radius, 0, 0, 0, sections);
	}

	public void drawWireCube(double size) {
		GL2 gl = getGL2();
		gl.glPolygonMode( GL.GL_FRONT_AND_BACK, GL2.GL_LINE );
		drawCube(size);
		gl.glPolygonMode( GL.GL_FRONT_AND_BACK, GL2.GL_FILL );
	}
	
	public void drawCube(double size) {
		drawCube(size, 0, 0, 0);
	}
	
	public void drawCube(double size, double x, double y, double z) {
		GL2 gl = getGL2();

		gl.glPushMatrix();
		
		gl.glTranslated(x, y+size/2, z);
		
		gl.glPushMatrix();
		drawSquare(gl, size);        // front face
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(180,0,1,0);
		drawSquare(gl, size);        // back face
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(-90,0,1,0);
		drawSquare(gl, size);       // left face
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(90,0,1,0);
		drawSquare(gl, size);       // right face is magenta
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(-90,1,0,0); // top face
		drawSquare(gl, size);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(90,1,0,0); // bottom face
		drawSquare(gl, size);
		gl.glPopMatrix();
		
		gl.glPopMatrix();
	}
	
	public void drawBoundingBox(BoundingBox box) {
		GL2 gl = getGL2();		
		
		gl.glBegin(GL2.GL_LINES);
		drawBoundingLines(gl, box.min, box.max);
		drawBoundingLines(gl, box.max, box.min);
		gl.glEnd();
	}
		
	private void drawBoundingLines(GL2 gl, Vector3 minPoint, Vector3 maxPoint) {
		gl.glVertex3d(minPoint.x, minPoint.y, minPoint.z);
		gl.glVertex3d(maxPoint.x, minPoint.y, minPoint.z);
		
		gl.glVertex3d(minPoint.x, minPoint.y, minPoint.z);
		gl.glVertex3d(minPoint.x, maxPoint.y, minPoint.z);
		
		gl.glVertex3d(minPoint.x, minPoint.y, minPoint.z);
		gl.glVertex3d(minPoint.x, minPoint.y, maxPoint.z);
		
		gl.glVertex3d(maxPoint.x, minPoint.y, maxPoint.z);
		gl.glVertex3d(minPoint.x, minPoint.y, maxPoint.z);
		
		gl.glVertex3d(maxPoint.x, minPoint.y, maxPoint.z);
		gl.glVertex3d(maxPoint.x, minPoint.y, minPoint.z);
		
		gl.glVertex3d(maxPoint.x, minPoint.y, minPoint.z);
		gl.glVertex3d(maxPoint.x, maxPoint.y, minPoint.z);
	}

	public void drawBoundingBox(BoundingBox3D box) {
		GL2 gl = getGL2();
		
		Point3D minPoint = box.getMinPoint();
		Point3D maxPoint = box.getMaxPoint();
		
		gl.glBegin(GL2.GL_LINES);
		drawBoundingLines(gl, minPoint, maxPoint);
		drawBoundingLines(gl, maxPoint, minPoint);
		
		gl.glEnd();
	}

	private void drawBoundingLines(GL2 gl, Point3D minPoint, Point3D maxPoint) {
		gl.glVertex3d(minPoint.getX(), minPoint.getY(), minPoint.getZ());
		gl.glVertex3d(maxPoint.getX(), minPoint.getY(), minPoint.getZ());
		
		gl.glVertex3d(minPoint.getX(), minPoint.getY(), minPoint.getZ());
		gl.glVertex3d(minPoint.getX(), maxPoint.getY(), minPoint.getZ());
		
		gl.glVertex3d(minPoint.getX(), minPoint.getY(), minPoint.getZ());
		gl.glVertex3d(minPoint.getX(), minPoint.getY(), maxPoint.getZ());
		
		gl.glVertex3d(maxPoint.getX(), minPoint.getY(), maxPoint.getZ());
		gl.glVertex3d(minPoint.getX(), minPoint.getY(), maxPoint.getZ());
		
		gl.glVertex3d(maxPoint.getX(), minPoint.getY(), maxPoint.getZ());
		gl.glVertex3d(maxPoint.getX(), minPoint.getY(), minPoint.getZ());
		
		gl.glVertex3d(maxPoint.getX(), minPoint.getY(), minPoint.getZ());
		gl.glVertex3d(maxPoint.getX(), maxPoint.getY(), minPoint.getZ());
	}
	
	public void drawCube() {

		GL2 gl = getGL2();

		gl.glPushMatrix();

		gl.glColor3f(0.3f, 0.5f, 1f);

		gl.glTranslated(0, 0.5, 0);

		gl.glPushMatrix();
		drawSquare(gl, 1);        // front face
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(180,0,1,0);
		drawSquare(gl, 1);        // back face
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(-90,0,1,0);
		drawSquare(gl, 1);       // left face
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(90,0,1,0);
		drawSquare(gl, 1);       // right face is magenta
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(-90,1,0,0); // top face
		drawSquare(gl, 1);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(90,1,0,0); // bottom face
		drawSquare(gl, 1);
		gl.glPopMatrix();

		gl.glPopMatrix();
	}

	public void drawPyramid(double size) {

		GL2 gl = getGL2();

		double halfSize = size/2;

		gl.glPushMatrix();

		gl.glBegin(GL.GL_TRIANGLES);        // Drawing Using Triangles
		gl.glVertex3d(0.0f, halfSize, 0.0f);    // Top Of Triangle (Front)
		gl.glVertex3d(-halfSize, -halfSize, halfSize);  // Left Of Triangle (Front)
		gl.glVertex3d(halfSize, -halfSize, halfSize);   // Right Of Triangle (Front)
		gl.glVertex3d(0.0f, halfSize, 0.0f);    // Top Of Triangle (Right)
		gl.glVertex3d(halfSize, -halfSize, halfSize);   // Left Of Triangle (Right)
		gl.glVertex3d(halfSize, -halfSize, -halfSize);  // Right Of Triangle (Right)
		gl.glVertex3d(0.0f, halfSize, 0.0f);    // Top Of Triangle (Back)
		gl.glVertex3d(halfSize, -halfSize, -halfSize);  // Left Of Triangle (Back)
		gl.glVertex3d(-halfSize, -halfSize, -halfSize); // Right Of Triangle (Back)
		gl.glVertex3d(0.0f, halfSize, 0.0f);    // Top Of Triangle (Left)
		gl.glVertex3d(-halfSize, -halfSize, -halfSize); // Left Of Triangle (Left)
		gl.glVertex3d(-halfSize, -halfSize, halfSize);  // Right Of Triangle (Left)
		gl.glEnd();                         // Finished Drawing The Triangle

		gl.glPopMatrix();
	}
	
	public void drawColoredPyramid(double size) {

		GL2 gl = getGL2();

		double halfSize = size/2;

		gl.glPushMatrix();

		gl.glBegin(GL.GL_TRIANGLES);        // Drawing Using Triangles
		gl.glColor3d(halfSize, 0.0f, 0.0f);     // Red
		gl.glVertex3d(0.0f, halfSize, 0.0f);    // Top Of Triangle (Front)
		gl.glColor3d(0.0f, halfSize, 0.0f);     // Green
		gl.glVertex3d(-halfSize, -halfSize, halfSize);  // Left Of Triangle (Front)
		gl.glColor3d(0.0f, 0.0f, halfSize);     // Blue
		gl.glVertex3d(halfSize, -halfSize, halfSize);   // Right Of Triangle (Front)
		gl.glColor3d(halfSize, 0.0f, 0.0f);     // Red
		gl.glVertex3d(0.0f, halfSize, 0.0f);    // Top Of Triangle (Right)
		gl.glColor3d(0.0f, 0.0f, halfSize);     // Blue
		gl.glVertex3d(halfSize, -halfSize, halfSize);   // Left Of Triangle (Right)
		gl.glColor3d(0.0f, halfSize, 0.0f);     // Green
		gl.glVertex3d(halfSize, -halfSize, -halfSize);  // Right Of Triangle (Right)
		gl.glColor3d(halfSize, 0.0f, 0.0f);     // Red
		gl.glVertex3d(0.0f, halfSize, 0.0f);    // Top Of Triangle (Back)
		gl.glColor3d(0.0f, halfSize, 0.0f);     // Green
		gl.glVertex3d(halfSize, -halfSize, -halfSize);  // Left Of Triangle (Back)
		gl.glColor3d(0.0f, 0.0f, halfSize);     // Blue
		gl.glVertex3d(-halfSize, -halfSize, -halfSize); // Right Of Triangle (Back)
		gl.glColor3d(halfSize, 0.0f, 0.0f);     // Red
		gl.glVertex3d(0.0f, halfSize, 0.0f);    // Top Of Triangle (Left)
		gl.glColor3d(0.0f, 0.0f, halfSize);     // Blue
		gl.glVertex3d(-halfSize, -halfSize, -halfSize); // Left Of Triangle (Left)
		gl.glColor3d(0.0f, halfSize, 0.0f);     // Green
		gl.glVertex3d(-halfSize, -halfSize, halfSize);  // Right Of Triangle (Left)
		gl.glEnd();                         // Finished Drawing The Triangle

		gl.glPopMatrix();
	}

	public void drawSquare(GL2 gl, double size) {

		float halfSize = 1.0f/2;

		gl.glTranslatef(0,0,halfSize);

		gl.glBegin(GL.GL_TRIANGLE_FAN);
		gl.glVertex2f(-halfSize,-halfSize);    // Draw the square (before the
		gl.glVertex2f(halfSize,-halfSize);     //   the translation is applied)
		gl.glVertex2f(halfSize,halfSize);      //   on the xy-plane, with its
		gl.glVertex2f(-halfSize,halfSize);     //   at (0,0,0).
		gl.glEnd();
	}

	/*
	 * Draw camera model	
	 */
	public void drawCamera(Camera camera) {

		GL2 gl = getGL2();

		Color color = camera.getColor();

		gl.glLineWidth(0.5f);
		glSetColor(color);

		//Draw origin point
		drawPoint(camera, color);

		//Draw target point
		drawPoint(camera.getTarget(), color);

		//Draw target line
		drawLine(camera, camera.getTarget());

		//Draw Camera as 3D Model
		gl.glPushMatrix();

		gl.glTranslated(camera.getX(), camera.getY(), camera.getZ());

		gl.glRotated(90, 0, 1, 0);
		gl.glRotated(camera.angleXY()+30, 1, 0, 0);
		gl.glRotated(camera.angleXZ()+180, 0, 0, 1);

		ColoredPoint3D ra = new ColoredPoint3D(-0.1, 0.2, 0.1);
		ColoredPoint3D rb = new ColoredPoint3D(0.1, 0.2, 0.1);
		ColoredPoint3D rc = new ColoredPoint3D(-0.1, 0.2, -0.1);
		ColoredPoint3D rd = new ColoredPoint3D(0.1, 0.2, -0.1);

		drawPoint(ra, color);
		drawPoint(rb, color);
		drawPoint(rc, color);
		drawPoint(rd, color);

		drawLine(ra, rb);
		drawLine(ra, rc);
		drawLine(rd, rb);
		drawLine(rd, rc);

		ColoredPoint3D origin = new ColoredPoint3D();

		drawLine(origin, ra);
		drawLine(origin, rb);
		drawLine(origin, rc);
		drawLine(origin, rd);

		gl.glTranslated(-camera.getX(), -camera.getY(), -camera.getZ());

		gl.glPopMatrix();
	}

	public void glSetColor(Color color) {
		float red = ((float)color.getRed()/255);
		float green = ((float)color.getGreen()/255);
		float blue = ((float)color.getBlue()/255);

		getGL2().glColor3f(red, green, blue);
	}

	/**
	 * Found at http://www.gamedev.net/topic/359467-draw-cylinder-with-triangle-strips/?view=findpost&p=3360321
	 * @param radius
	 * @param height
	 */
	public void drawCylinder(double radius, double height) {
		GL2 gl = getGL2();
		
		double numSteps = DEFAULT_RESOLUTION;
		
		double hl = height * 0.5f;
		double a = 0.0;
		double step = 2*Math.PI / numSteps;
		
		gl.glBegin(GL2.GL_TRIANGLE_STRIP);
		for (int i = 0; i <= numSteps; ++i) {
		    double x = Math.cos(a) * radius;
		    double y = Math.sin(a) * radius;
		    gl.glVertex3d(x,-hl, y);
		    gl.glVertex3d(x, hl, y);

		    a += step;
		}
		gl.glEnd();
	}
	
	public void drawCylinder(double baseRadius, double topRadius, double height) {
		drawCylinder(baseRadius, topRadius, height, 0, 0, 0);
	}
	
	public void drawCylinder(double baseRadius, double topRadius, double height, double x, double y, double z) {
		
		final int slices = DEFAULT_RESOLUTION;
		final int stacks = DEFAULT_RESOLUTION;

		GL2 gl = getGL2();

		gl.glPushMatrix();

		// Draw sphere (possible styles: FILL, LINE, POINT).
		//gl.glColor3f(0.3f, 0.5f, 1f);

		gl.glTranslated(x, y, z);

		GLUquadric cylinder = generateQuadric(glu);

		glu.gluCylinder(cylinder, baseRadius, topRadius, height, slices, stacks);
		glu.gluDeleteQuadric(cylinder);

		gl.glPopMatrix();
	}

	public void drawFrustrum(Frustrum frustrum) {
		GL2 gl = getGL2();
		
		gl.glPushMatrix();
				
		setColor(Color.RED);
		
		gl.glBegin(GL2.GL_LINES);
		
		//Draw Near Plane
		vertex(gl, frustrum.nBottomLeft);
		vertex(gl, frustrum.nBottomRight);
		
		vertex(gl, frustrum.nBottomLeft);
		vertex(gl, frustrum.nTopRight);
		
		vertex(gl, frustrum.nTopLeft);
		vertex(gl, frustrum.nTopRight);
		
		vertex(gl, frustrum.nTopLeft);
		vertex(gl, frustrum.nBottomRight);
		
		//Draw Far Plane
		vertex(gl, frustrum.fBottomLeft);
		vertex(gl, frustrum.fBottomRight);
		
		vertex(gl, frustrum.fBottomLeft);
		vertex(gl, frustrum.fTopRight);
		
		vertex(gl, frustrum.fTopLeft);
		vertex(gl, frustrum.fTopRight);
		
		vertex(gl, frustrum.fTopLeft);
		vertex(gl, frustrum.fBottomRight);
		
		//Draw Sides
		vertex(gl, frustrum.nBottomLeft);
		vertex(gl, frustrum.fBottomLeft);
		
		vertex(gl, frustrum.nBottomRight);
		vertex(gl, frustrum.fBottomRight);
		
		vertex(gl, frustrum.nTopLeft);
		vertex(gl, frustrum.fTopLeft);
		
		vertex(gl, frustrum.nTopRight);
		vertex(gl, frustrum.fTopRight);
		
		gl.glEnd();
		
		gl.glPopMatrix();
	}
	
	public void drawBillboard(Billboard billboard) {
		GL2 gl = getGL2();
		
		Vector3 p00 = new Vector3(); 
		billboard.getP00(p00);
		
		Vector3 p10 = new Vector3();
		billboard.getP10(p10);
		
		Vector3 p11 = new Vector3();
		billboard.getP11(p11);
		
		Vector3 p01 = new Vector3();
		billboard.getP01(p01);
		
		gl.glBegin(GL2.GL_TRIANGLE_STRIP);

		gl.glTexCoord2d(0, 0);
		gl.glVertex3f(p00.x, p00.y, p00.z);

		gl.glTexCoord2d(1, 0);
		gl.glVertex3f(p10.x, p10.y, p10.z);

		gl.glTexCoord2d(0, 1);
		gl.glVertex3f(p01.x, p01.y, p01.z);
		
		gl.glTexCoord2d(1, 1);
		gl.glVertex3f(p11.x, p11.y, p11.z);

		gl.glEnd();
	}
	
	private void drawPlane(GL2 gl) {
		
	}

	private void vertex(GL2 gl, Vector3 vertex) {
		gl.glVertex3f(vertex.x, vertex.y, vertex.z);
	}

	public boolean supportsExtension(String string) {
		return false;
	}

	public float getDeltaTime() {
		return 0;
	}


	public float[] colorAsArray(Color color) {
		float r = (float)color.getRed()/255;
		float g = (float)color.getGreen()/255;
		float b = (float)color.getBlue()/255;
		
		float[] array = new float[]{r,g,b};
		return array;
	}

}
