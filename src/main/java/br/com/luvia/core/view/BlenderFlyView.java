package br.com.luvia.core.view;

import br.com.abby.core.view.FlyView;
import br.com.etyllica.core.event.KeyEvent;

public class BlenderFlyView extends FlyView {

	public BlenderFlyView(float x, float y, float z) {
		super(x, y, z);
	}

	public void updateKeyboard(KeyEvent event) {
		if(event.isKeyDown(KeyEvent.VK_UP_ARROW)) {
			forwardPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_UP_ARROW)) {
			forwardPressed = false;
		}

		if(event.isKeyDown(KeyEvent.VK_DOWN_ARROW)) {
			backwardPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_DOWN_ARROW)) {
			backwardPressed = false;
		}

		if(event.isKeyDown(KeyEvent.VK_NUMPAD8)) {
			upPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_NUMPAD8)) {
			upPressed = false;
		}

		if(event.isKeyDown(KeyEvent.VK_NUMPAD2)) {
			downPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_NUMPAD2)) {
			downPressed = false;
		}

		if(event.isKeyDown(KeyEvent.VK_NUMPAD6)) {
			rightPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_NUMPAD6)) {
			rightPressed = false;
		}

		if(event.isKeyDown(KeyEvent.VK_NUMPAD4)) {
			leftPressed = true;			
		} else if(event.isKeyUp(KeyEvent.VK_NUMPAD4)) {
			leftPressed = false;
		}

		if(event.isKeyDown(KeyEvent.VK_SPACE)) {
			liftPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_SPACE)) {
			liftPressed = false;
		}
	}
	
}
