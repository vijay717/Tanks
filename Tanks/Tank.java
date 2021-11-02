public class Tank
{
	private double x;
	private double y;
	private double a;
	private double speed;
	private double turn;
	private double oldX;
	private double oldY;
	public Tank(double posX, double posY, double angle)
	{
		x=posX;
		y=posY;
		a=angle;
		speed = 1.5;
		turn = 2;
	}

	public void forward()
	{
		x+=speed*Math.cos(Math.toRadians(a));
		y+=speed*Math.sin(Math.toRadians(a));
	}

	public void back()
	{
		x-=speed*Math.cos(Math.toRadians(a));
		y-=speed*Math.sin(Math.toRadians(a));
	}

	public void left()
	{
		a-=turn;
	}

	public void right()
	{
		a+=turn;
	}

	public void revert()
	{
		x = oldX;
		y = oldY;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getAngle()
	{
		return a;
	}

	public double getTurn()
	{
		return turn;
	}

	public void setX(double posX)
	{
		x = posX;
	}

	public void setY(double posY)
	{
		y = posY;
	}

	public void setOldX()
	{
		oldX = x;
	}

	public void setOldY()
	{
		oldY = y;
	}

	public void setAngle(double angle)
	{
		a = angle;
	}

	public void setSpeed(double s)
	{
		speed = s;
	}

	public void setTurn(double t)
	{
		turn = t;
	}
}