package br.com.luvia.loader.block;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import br.com.abby.core.loader.MeshLoader;
import br.com.abby.core.loader.VBOLoader;
import br.com.abby.core.model.Model;
import br.com.etyllica.util.PathHelper;
import br.com.luvia.graphics.Block;
import br.com.luvia.loader.BlockLoader;
import junit.framework.TestCase;

public class BlockLoaderTest extends TestCase {
	
	private BlockLoader loader;
	
	@Before
	public void setUp() {
		
		String url = PathHelper.currentFileDirectory().toString()+"../";
		
		loader = BlockLoader.getInstance();
		loader.setUrl(url);
		
		MeshLoader.getInstance().setUrl(url);
		MeshLoader.getInstance().addLoader(MeshLoader.OBJ, new VBOLoader() {
			@Override
			public Model loadModel(URL url, String path) throws FileNotFoundException, IOException {
				return new Model(path);
			}
		});
	}

	@Test
	public void testSave() {
		String path = "test_block.block";
       
		try {
			Block block = loader.loadBlock(path);
			loader.saveBlock(block, "test_saved.block");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLoad() {
		
		String path = "test_block.block";
		
		Block block = null;
		
		try {
			block = loader.loadBlock(path);
			assertNotNull(block);
		} catch (IOException e) {
			fail();
		}
		
		assertEquals(1, block.getColumns());
		assertEquals(1, block.getRows());
		assertEquals(1, block.getHeight());
		
		assertEquals(1, block.getInstances().size());
		
		assertEquals("stone/stone.obj", block.getInstances().get(0).getModel().getPath());
		
		assertEquals(1f, block.getInstances().get(0).transform.val[0]);
		assertEquals(0f, block.getInstances().get(0).transform.val[1]);
		assertEquals(0f, block.getInstances().get(0).transform.val[2]);
		assertEquals(0f, block.getInstances().get(0).transform.val[3]);
		
		assertEquals(0f, block.getInstances().get(0).transform.val[4]);
		assertEquals(1f, block.getInstances().get(0).transform.val[5]);
		assertEquals(0f, block.getInstances().get(0).transform.val[6]);
		assertEquals(0f, block.getInstances().get(0).transform.val[7]);
		
		assertEquals(0f, block.getInstances().get(0).transform.val[8]);
		assertEquals(0f, block.getInstances().get(0).transform.val[9]);
		assertEquals(1f, block.getInstances().get(0).transform.val[10]);
		assertEquals(0f, block.getInstances().get(0).transform.val[11]);
		
		assertEquals(0f, block.getInstances().get(0).transform.val[12]);
		assertEquals(0f, block.getInstances().get(0).transform.val[13]);
		assertEquals(0f, block.getInstances().get(0).transform.val[14]);
		assertEquals(1f, block.getInstances().get(0).transform.val[15]);
	}
	
}
