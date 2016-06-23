package br.com.luvia.core.controller;

import br.com.abby.linear.AimPoint;
import br.com.etyllica.core.event.KeyEvent;

public class FlyView {

	private AimPoint aim;

	protected double turnSpeed = 5;
	protected double walkSpeed = 2.5;

	protected boolean forwardPressed = false;
	protected boolean backwardPressed = false;
	protected boolean upPressed = false;
	protected boolean downPressed = false;
	protected boolean leftPressed = false;
	protected boolean rightPressed = false;
	protected boolean spacePressed = false;

	public FlyView(float x, float y, float z) {
		aim = new AimPoint(x, y, z);
	}

	public AimPoint getAim() {
		return aim;
	}

	public void setAim(AimPoint aim) {
		this.aim = aim;
	}

	public double getX() {
		return aim.getX();
	}

	public double getY() {
		return aim.getY();
	}

	public double getZ() {
		return aim.getZ();
	}

	public void updateControls(long now) {

		if(forwardPressed) {
			aim.moveXZ(-walkSpeed);
		}

		if(backwardPressed) {
			aim.moveXZ(walkSpeed);	
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

		if(spacePressed) {
			aim.offsetY(+walkSpeed);			
		}

	}

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

		if(event.isKeyDown(KeyEvent.VK_SPACE)) {
			spacePressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_SPACE)) {
			spacePressed = false;
		}
	}

}
