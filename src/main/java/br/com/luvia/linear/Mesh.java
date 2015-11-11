package br.com.luvia.linear;


import static javax.media.opengl.GL.GL_LINEAR;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import br.com.abby.core.loader.MeshLoader;
import br.com.abby.core.vbo.Face;
import br.com.abby.core.vbo.Group;
import br.com.abby.core.vbo.VBO;
import br.com.abby.linear.AimPoint;
import br.com.luvia.core.GL2Drawable;
import br.com.luvia.material.Material;

import com.jogamp.opengl.util.texture.Texture;

/**
 * 
 * @author yuripourre
 * @license LGPLv3
 *
 */

public class Mesh extends AimPoint implements GL2Drawable {

	private Set<Integer> vertexSelection = new HashSet<Integer>();

	private VBO vbo;

	private Map<Group, Material> materials = new HashMap<Group, Material>();

	private boolean drawTexture = true;

	private float scale = 1;

	private int[] indexes = new int[16];

	public Mesh() {
		super(0,0,0);
	}

	public Mesh(VBO vbo) {
		super(0,0,0);

		this.vbo = vbo;
		loadMaterials();
	}

	public Mesh(String path) {
		super(0,0,0);

		loadVBO(path);
		loadMaterials();
	}

	protected void loadVBO(String path) {
		vbo = MeshLoader.getInstance().loadModel(path);
	}

	protected void loadMaterials() {
		//For each group in VBO
		for(Group group: vbo.getGroups()) {
			//If has a material
			if(group.getMaterial() != null) {
				materials.put(group, new Material(group.getMaterial()));	
			}
		}
	}

	public boolean isDrawTexture() {
		return drawTexture;
	}

	public void setDrawTexture(boolean drawTexture) {
		this.drawTexture = drawTexture;
	}

	public List<Vector3f> getVertexes() {
		return vbo.getVertices();
	}

	public void wireframeRender(GL2 gl) {

		gl.glPushMatrix();

		// Turn on wireframe mode
		gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_LINE);

		setupModel(gl);
		setupColor(gl);

		drawTexture = false;

		// Draw Model
		for(Group group: vbo.getGroups()) {

			for(Face face: group.getFaces()) {
				int vertices = face.vertexIndex.length;
				setupIndexes(vertices);

				gl.glBegin(GL2.GL_TRIANGLE_STRIP);
				drawWireFrameFace(gl, face);
				gl.glEnd();
			}
		}

		// Turn off wireframe mode
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
		gl.glPopMatrix();
	}

	private void setupIndexes(int vertices) {
		if(vertices == 3) {
			indexes[0] = 0;
			indexes[1] = 1;
			indexes[2] = 2;
		} else if(vertices == 4) { //TODO Transform all faces in tris
			indexes[0] = 0;
			indexes[1] = 1;
			indexes[2] = 3;
			indexes[3] = 2;
		}
	}

	public void texturedRender(GL2 gl, Set<Face> set) {
		gl.glPushMatrix();

		setupTextured(gl);
		setupModel(gl);
		setupColor(gl);
		
		Texture texture = null;

		for(Group group: vbo.getGroups()) {

			drawTexture = false;
			texture = setupTexture(gl, texture, group);

			drawSetFaces(gl, group, set);

			disableTexture(gl, texture);
		}

		gl.glPopMatrix();
	}
	
	public void texturedRender(GL2 gl) {
		setupModel(gl);
		setupColor(gl);

		simpleDraw(gl);
	}

	public void simpleDraw(GL2 gl) {
		Texture texture = null;

		for(Group group: vbo.getGroups()) {

			drawTexture = false;
			texture = setupTexture(gl, texture, group);

			drawFaces(gl, group);

			disableTexture(gl, texture);
		}
	}

	private void setupTextured(GL2 gl) {
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glCullFace(GL.GL_BACK);
	}

	private void drawFaces(GL2 gl, Group group) {

		for(Face face: group.getFaces()) {

			int vertices = face.vertexIndex.length;
			setupIndexes(vertices);

			gl.glBegin(GL2.GL_TRIANGLE_STRIP);
			drawTexturedFace(gl,face);
			gl.glEnd();
		}
	}
	
	private void drawSetFaces(GL2 gl, Group group, Set<Face> set) {

		for(Face face: group.getFaces()) {
			if(!set.contains(face)) {
				continue;
			}
			
			int vertices = face.vertexIndex.length;
			setupIndexes(vertices);

			gl.glBegin(GL2.GL_TRIANGLE_STRIP);
			drawTexturedFace(gl,face);
			gl.glEnd();
		}

	}

	private Texture setupTexture(GL2 gl, Texture texture, Group group) {
		if(group.getMaterial() != null) {
			texture = loadTexture(group);
		}

		if(drawTexture) {
			gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			// Use linear filter for texture if image is smaller than the original texture
			gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);

			texture.enable(gl);
			texture.bind(gl);
		} else {
			setupColor(gl);
		}

		return texture;
	}

	private void setupColor(GL2 gl) {
		gl.glColor3d((double)color.getRed()/255, (double)color.getGreen()/255, (double)color.getBlue()/255);
	}

	private void disableTexture(GL2 gl, Texture texture) {
		//TODO make it better
		if(texture != null) {
			if(drawTexture) {
				texture.disable(gl);
			}
			texture = null;
		}
	}

	private Texture loadTexture(Group group) {
		Texture texture;
		
		String texturePath = group.getMaterial().getMapD();
		texture = materials.get(group).getTextureD();

		if(texture == null) {
			texturePath = group.getMaterial().getMapKd();
			texture = materials.get(group).getTextureKd();
		}

		if(texture != null) {
			drawTexture = true;
		} else {
			if (!texturePath.isEmpty()) {
				System.err.println("texture not found: "+texturePath);	
			}
		}

		return texture;
	}

	private void setupModel(GL2 gl) {
		gl.glTranslated(x, y, z);
		gl.glRotated(angleX, 1, 0, 0);
		gl.glRotated(angleY, 0, 1, 0);
		gl.glRotated(angleZ, 0, 0, 1);
		gl.glScaled(scale, scale, scale);
	}

	private void drawWireFrameFace(GL2 gl, Face face) {
		for(int i = 0; i < face.vertexIndex.length; i++) {
			int index = face.vertexIndex[indexes[i]];
			Vector3f vertex = vbo.getVertices().get(index);
			gl.glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());					
		}
	}

	private void drawTexturedFace(GL2 gl, Face face) {
		
		int count = face.vertexIndex.length;
		
		if(count >= indexes.length) {
			//Face with many sizes
			count = 16;
		}
		
		for(int i = 0; i < count; i++) {

			int index = indexes[i];

			int vertexIndex = face.vertexIndex[index];

			//Set normals if has it
			if(!vbo.getNormals().isEmpty() && face.normalIndex!=null) {
				Vector3f normal = vbo.getNormals().get(face.normalIndex[index]);
				gl.glNormal3d(normal.getX(), normal.getY(), normal.getZ());
			}

			if(drawTexture) {
				Vector2f texture = vbo.getTextures().get(face.textureIndex[index]);
				gl.glTexCoord2d(texture.getX(), texture.getY());
			}

			Vector3f vertex = vbo.getVertices().get(vertexIndex);
			gl.glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());
		}
	}

	@Override
	public void draw(GL2 gl) {
		gl.glPushMatrix();
		gl.glEnable(GL.GL_DEPTH_TEST);
		setupTextured(gl);
		texturedRender(gl);
		gl.glDisable(GL.GL_DEPTH_TEST);
		gl.glPopMatrix();
	}
	
	public void draw(GL2 gl, Set<Face> faces) {
		gl.glEnable(GL.GL_DEPTH_TEST);
		texturedRender(gl, faces);
		gl.glDisable(GL.GL_DEPTH_TEST);
	}

	public void drawAsWireFrame(GL2 gl) {
		gl.glEnable(GL.GL_DEPTH_TEST);
		wireframeRender(gl);
		gl.glDisable(GL.GL_DEPTH_TEST);
	}

	public Set<Integer> getVertexSelection() {
		return vertexSelection;
	}

	public void setVertexSelection(Set<Integer> vertexSelection) {
		this.vertexSelection = vertexSelection;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

}
