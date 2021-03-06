package net.betterpvp.osFighter.managers.paint.objects;

import java.awt.*;

import net.betterpvp.osFighter.managers.Paint;
import net.betterpvp.osFighter.managers.paint.PaintObject;

public class UpdatingLabel extends PaintObject{

	private String text;
	private Color colour;
	private Font font;

	public UpdatingLabel(String ID, int x, int y, Color colour, Font font) {
		super(ID, x, y, 1, 1);
		this.colour = colour;
		this.font = font;
	}

	public UpdatingLabel(String ID, int x, int y, String text) {
		super(ID, x, y, 1, 1);
		this.colour = Color.WHITE;
		this.font = new Font("Calibri", Font.BOLD, 16);
		this.text = text;
	}

	public UpdatingLabel(String ID, int x, int y) {
		super(ID, x, y, 1, 1);
		this.colour = Color.WHITE;
		this.font = new Font("Calibri", Font.BOLD, 16);

	}

	public UpdatingLabel(String ID, int x, int y, int width, int height) {
		super(ID, x, y, width, height);
		this.colour = Color.WHITE;
		this.font = new Font("Calibri", Font.BOLD, 16);

	}

	@Override
	public Rectangle getRectangle(){
		return new Rectangle(getX(), getY() - 16, getWidth(), getHeight());
	}

	@Override
	public void draw(Graphics2D g) {
		if(text != null){
			g.setFont(font);
			g.setColor(colour);
			g.drawString(text, getX(), getY());
		}

	}

	public void setText(String s){
		this.text = s;
	}

	@Override
	public void update(Paint paint) {
		// TODO Auto-generated method stub

	}


}
