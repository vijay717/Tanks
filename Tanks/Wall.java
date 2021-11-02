public class Wall
{
	private double x;
	private double y;
	private double w;
	private double h;
	public Wall(double posX, double posY, double width, double height)
	{
		x=posX;
		y=posY;
		w = width;
		h = height;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getWidth()
	{
		return w;
	}

	public double getHeight()
	{
		return h;
	}
}