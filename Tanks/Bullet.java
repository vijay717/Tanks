public class Bullet
{
	private double x;
	private double y;
	private double a;
	private double speed;
	public Bullet(double angle, double posX, double posY)
	{
		x=posX;
		y=posY;
		a=angle;
		speed=5.5;
	}

	public void update()
	{
		x+=speed*Math.cos(Math.toRadians(a));
		y+=speed*Math.sin(Math.toRadians(a));
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public void setSpeed(double s)
	{
		speed = s;
	}
}