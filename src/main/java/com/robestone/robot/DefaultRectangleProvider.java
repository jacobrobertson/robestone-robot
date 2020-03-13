package com.robestone.robot;

import java.awt.Rectangle;

public class DefaultRectangleProvider implements RectangleProvider {
	
	private Rectangle rectangle;

	public DefaultRectangleProvider(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public Rectangle getRectangle() {
		return this.rectangle;
	}
}
