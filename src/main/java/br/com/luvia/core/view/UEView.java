package br.com.luvia.core.view;

import br.com.abby.core.view.FlyView;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.MouseButton;
import br.com.etyllica.core.event.PointerEvent;

public class UEView extends FlyView {

	int mx, my, rmx, rmy;
	float startX, startY;
	double startAngleX, startAngleY;

	private boolean mouseLeft = false;
	private boolean mouseRight = false;

	public UEView(float x, float y, float z) {
		super(x, y, z);
	}

	@Override
	public void updateKeyboard(KeyEvent event) {
		if(event.isKeyDown(KeyEvent.VK_W)) {
			forwardPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_W)) {
			forwardPressed = false;
		}

		if(event.isKeyDown(KeyEvent.VK_S)) {
			backwardPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_S)) {
			backwardPressed = false;
		}

		if(event.isKeyDown(KeyEvent.VK_E)) {
			liftPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_E)) {
			liftPressed = false;
		}

		if(event.isKeyDown(KeyEvent.VK_Q)) {
			divePressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_Q)) {
			divePressed = false;
		}

		if(event.isKeyDown(KeyEvent.VK_D)) {
			strafeRightPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_D)) {
			strafeRightPressed = false;
		}

		if(event.isKeyDown(KeyEvent.VK_A)) {
			strafeLeftPressed = true;			
		} else if(event.isKeyUp(KeyEvent.VK_A)) {
			strafeLeftPressed = false;
		}
		
		if(event.isKeyDown(KeyEvent.VK_UP_ARROW)) {
			upPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_UP_ARROW)) {
			upPressed = false;
		}

		if(event.isKeyDown(KeyEvent.VK_DOWN_ARROW)) {
			downPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_DOWN_ARROW)) {
			downPressed = false;
		}

		if(event.isKeyDown(KeyEvent.VK_RIGHT_ARROW)) {
			rightPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_RIGHT_ARROW)) {
			rightPressed = false;
		}

		if(event.isKeyDown(KeyEvent.VK_LEFT_ARROW)) {
			leftPressed = true;			
		} else if(event.isKeyUp(KeyEvent.VK_LEFT_ARROW)) {
			leftPressed = false;
		}

		/*if(event.isKeyDown(KeyEvent.VK_SPACE)) {
			spacePressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_SPACE)) {
			spacePressed = false;
		}*/
	}

	@Override
	public void updateMouse(PointerEvent event) {
		mx = event.getX();
		my = event.getY();

		if (event.isButtonDown(MouseButton.MOUSE_BUTTON_LEFT)) {
			if(!mouseLeft) {
				mouseLeft = true;
			}
		} else if (event.isButtonUp(MouseButton.MOUSE_BUTTON_LEFT)) {
			mouseLeft = false;
		}

		if (event.isButtonDown(MouseButton.MOUSE_BUTTON_RIGHT)) {
			if(!mouseRight) {
				mouseRight = true;
				rmx = mx;
				rmy = my;
				startX = aim.x;
				startY = aim.y;
				startAngleX = aim.getAngleX();
				startAngleY = aim.getAngleY();
			}
		} else if (event.isButtonUp(MouseButton.MOUSE_BUTTON_RIGHT)) {
			mouseRight = false;
		}
	}

	@Override
	public void update(long now) {
		if(forwardPressed) {
			aim.z += walkSpeed;
		}

		if(backwardPressed) {
			aim.z -= walkSpeed;	
		}
		
		if(strafeRightPressed) {
			aim.x -= walkSpeed;
		}

		if(strafeLeftPressed) {
			aim.x += walkSpeed;	
		}

		if(upPressed) {
			aim.offsetAngleX(turnSpeed);
		}

		if(downPressed) {
			aim.offsetAngleX(-turnSpeed);
		}

		if(leftPressed) {
			aim.offsetAngleY(+turnSpeed);			
		}

		if(rightPressed) {
			aim.offsetAngleY(-turnSpeed);			
		}

		if(liftPressed) {
			aim.y += walkSpeed;			
		}
		
		if(divePressed) {
			aim.y -= walkSpeed;
		}

		if (mouseRight) {
			if(!mouseLeft) {
				aim.setAngleY(startAngleY-((mx-rmx)/3)%360);
				aim.setAngleX(startAngleX-((my-rmy)/3)%360);
			} else {
				aim.x = ((startX-(mx-rmx)/3));
				aim.y = ((startY-(my-rmy)/3));
			}
		}

	}

}
