package br.com.luvia.linear;


import static javax.media.opengl.GL.GL_LINEAR;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import br.com.abby.linear.AimPoint;
import br.com.abby.vbo.Face;
import br.com.luvia.core.GL2Drawable;
import br.com.luvia.loader.mesh.vbo.Group;
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

	public List<Vector3f> vertexes = new ArrayList<Vector3f>();
	public List<Vector3f> normals = new ArrayList<Vector3f>();
	public List<Vector2f> textures = new ArrayList<Vector2f>();

	public List<Face> faces = new ArrayList<Face>();

	private List<Group> groups = new ArrayList<Group>();

	private Map<String, Material> materials = new HashMap<String, Material>();

	private boolean drawTexture = true;

	private float scale = 1;

	public Mesh() {
		super(0,0,0);
	}

	public boolean isDrawTexture() {
		return drawTexture;
	}

	public void setDrawTexture(boolean drawTexture) {
		this.drawTexture = drawTexture;
	}

	public Map<String, Material> getMaterials() {
		return materials;
	}

	public void setMaterials(Map<String, Material> materials) {
		this.materials = materials;
	}

	public List<Vector3f> getVertexes() {
		return vertexes;
	}

	public void setVertexes(List<Vector3f> vertexes) {
		this.vertexes = vertexes;
	}

	public void drawWireFrame(GL2 gl) {

		gl.glEnable(GL.GL_CULL_FACE);

		// Turn on wireframe mode
		gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_LINE);
		//gl.glPolygonMode(GL2.GL_BACK, GL2.GL_LINE);
		//gl.glPolygonMode(GL2.GL_BACK, GL2.GL_FILL);

		// Draw Model		
		for(Group group: groups) {

			for(Face face: group.getFaces()) {

				if(face.vertex.length==3) {

					gl.glBegin(GL.GL_TRIANGLES);

				}else{ //TODO Transform all faces in tris

					gl.glBegin(GL2.GL_QUADS);

				}

				for(int i=0;i<face.vertex.length;i++) {

					gl.glVertex3d(face.vertex[i].getX(), face.vertex[i].getY(), face.vertex[i].getZ());
				}

				gl.glEnd();

			}		

		}

		// Turn off wireframe mode
		gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_FILL);
		//gl.glPolygonMode(GL2.GL_BACK, GL2.GL_FILL);

	}

	public void simpleDraw(GL2 gl) {
		
		gl.glPushMatrix();

		gl.glTranslated(x, y, z);
		gl.glRotated(angleX, 1, 0, 0);
		gl.glRotated(angleY, 0, 1, 0);
		gl.glRotated(angleZ, 0, 0, 1);
		gl.glScaled(scale, scale, scale);

		//gl.glTranslated(x, y, z);

		Texture texture = null;

		for(Group group: groups) {

			drawTexture = false;

			if(group.getMaterial()!=null) {

				texture = group.getMaterial().getTextureD();

				if(texture==null) {
					texture = group.getMaterial().getTextureKd();
				}

				if(texture!=null) {
					
					drawTexture = true;
				}//else {//System.err.println("textura não encontrada");}

			}

			if(drawTexture) {

				gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
				// Use linear filter for texture if image is smaller than the original texture
				gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

				texture.enable(gl);
				texture.bind(gl);
				
			} else {
				
				gl.glColor3d((double)color.getRed()/255, (double)color.getGreen()/255, (double)color.getBlue()/255);
				
			}

			for(Face face: group.getFaces()) {

				if(face.vertex.length==3) {

					gl.glBegin(GL.GL_TRIANGLES);

				}else{ //TODO Transform all faces in tris

					gl.glBegin(GL2.GL_QUADS);

				}

				for(int i=0;i<face.vertex.length;i++) {

					if(drawTexture) {
						//gl.glNormal3d(face.normal[i].getX(), face.normal[i].getY(), face.normal[i].getZ());
						gl.glTexCoord2d(face.texture[i].getX(), face.texture[i].getY());
					}

					gl.glVertex3d(face.vertex[i].getX(), face.vertex[i].getY(), face.vertex[i].getZ());
				}

				gl.glEnd();

			}

			//TODO make it better
			if(texture!=null) {

				if(drawTexture) {

					texture.disable(gl);

				}

				texture = null;

			}
		}


		/*for(int i=0;i<vertexes.size(); i++) {

			gl.glPushMatrix();

			float vsize = 0.01f;

			if(vertexSelection.contains(i)) {
				gl.glColor3f(1,1,1);
			}else{
				//gl.glColor3i(0xdd,0x88,0x55);
				gl.glColor3f(0.6f,0.4f,0.4f);
			}

			gl.glTranslated(vertexes.get(i).getX(), vertexes.get(i).getY(), vertexes.get(i).getZ());
			//drawCube(gl, vsize);
			gl.glPopMatrix();
		}*/



		gl.glPopMatrix();
		
	}
	
	@Override
	public void draw(GL2 gl) {

		gl.glEnable(GL.GL_DEPTH_TEST);

		simpleDraw(gl);
		
		gl.glDisable(GL.GL_DEPTH_TEST);



	}

	public Set<Integer> getVertexSelection() {
		return vertexSelection;
	}

	public void setVertexSelection(Set<Integer> vertexSelection) {
		this.vertexSelection = vertexSelection;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

}
