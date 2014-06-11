package br.com.luvia.linear;


import static javax.media.opengl.GL.GL_LINEAR;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import br.com.abby.animation.skeletal.Joint;
import br.com.abby.linear.Point3D;
import br.com.abby.material.DiffuseMaterial;
import br.com.abby.vbo.Face;
import br.com.luvia.loader.mesh.vbo.Group;

import com.jogamp.opengl.util.texture.Texture;

/**
 * 
 * @author yuripourre
 * @license LGPLv3
 *
 */

public class Model extends Point3D {

	public List<Vector3f> vertexes = new ArrayList<Vector3f>();
	public List<Vector3f> normals = new ArrayList<Vector3f>();
	public List<Vector2f> textures = new ArrayList<Vector2f>();

	public List<Face> faces = new ArrayList<Face>();
	private List<Group> groups = new ArrayList<Group>();
	private Map<String, DiffuseMaterial> materials = new HashMap<String, DiffuseMaterial>();

	private boolean drawTexture = true;

	private float zoom = 1;
	
	private Joint rootJoint;
	
	public Model() {
		super(0,0,0);				
	}

	public boolean isDrawTexture() {
		return drawTexture;
	}

	public void setDrawTexture(boolean drawTexture) {
		this.drawTexture = drawTexture;
	}

	public Map<String, DiffuseMaterial> getMaterials() {
		return materials;
	}

	public void setMaterials(Map<String, DiffuseMaterial> materials) {
		this.materials = materials;
	}

	public List<Vector3f> getVertexes() {
		return vertexes;
	}

	public void setVertexes(List<Vector3f> vertexes) {
		this.vertexes = vertexes;
	}
	
	public void drawWireFrame(GL2 gl){
				
		gl.glEnable(GL.GL_CULL_FACE);
		 
		// Turn on wireframe mode
		gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_LINE);
		//gl.glPolygonMode(GL2.GL_BACK, GL2.GL_LINE);
		//gl.glPolygonMode(GL2.GL_BACK, GL2.GL_FILL);

		// Draw Model		
		for(Group group: groups){
			
			for(Face face: group.getFaces()){

				if(face.vertex.length==3){

					gl.glBegin(GL.GL_TRIANGLES);

				}else{ //TODO Transform all faces in tris

					gl.glBegin(GL2.GL_QUADS);

				}

				for(int i=0;i<face.vertex.length;i++){
					
					gl.glVertex3d(face.vertex[i].getX(), face.vertex[i].getY(), face.vertex[i].getZ());
				}

				gl.glEnd();

			}		
		
		}

		// Turn off wireframe mode
		gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_FILL);
		//gl.glPolygonMode(GL2.GL_BACK, GL2.GL_FILL);
		
	}

	public void draw(GL2 gl) {

		gl.glEnable(GL.GL_DEPTH_TEST);

		Texture texture = null;

		gl.glPushMatrix();
		gl.glTranslated(x, y, z);
		
		for(Group group: groups){

			if(group.getMaterial()!=null){
				
				texture = group.getMaterial().getTextureD();

				if(texture==null){
					texture = group.getMaterial().getTextureKd();
				}
				
				if(texture==null){
					//System.err.println("textura não encontrada");
					drawTexture = false;					
				}

			}
			//map = "";

			if(texture!=null){
								
				if(drawTexture){
					
					gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
					// Use linear filter for texture if image is smaller than the original texture
					gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

					texture.enable(gl);
					texture.bind(gl);

				}else{
					
					texture.disable(gl);
					
				}

			}

			for(Face face: group.getFaces()){

				if(face.vertex.length==3){

					gl.glBegin(GL.GL_TRIANGLES);

				}else{ //TODO Transform all faces in tris

					gl.glBegin(GL2.GL_QUADS);

				}

				for(int i=0;i<face.vertex.length;i++){
					
					if(drawTexture){
						//gl.glNormal3d(face.normal[i].getX(), face.normal[i].getY(), face.normal[i].getZ());
						gl.glTexCoord2d(face.texture[i].getX(), face.texture[i].getY());
					}
					
					gl.glVertex3d(face.vertex[i].getX(), face.vertex[i].getY(), face.vertex[i].getZ());
				}

				gl.glEnd();

			}
		}
		
		gl.glPopMatrix();

	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public float getZoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}
	
	public Joint getRootJoint() {
		return rootJoint;
	}

	public void setRootJoint(Joint rootJoint) {
		this.rootJoint = rootJoint;
	}
		
}
