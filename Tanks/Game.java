import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.media.AudioClip;
import java.net.URL;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import java.util.ArrayList;
import javafx.scene.image.ImageView;

public class Game extends Application implements EventHandler<InputEvent>
{
	GraphicsContext gc;
	Image image1;
	Image image2;
	Image heart;
	Image speed;
	Image speedBullet;
	Image manyBullets;
	ImageView iv1 = new ImageView();
	ImageView iv2 = new ImageView();
	Tank tank1 = new Tank(200, 300, 0);
	Tank tank2 = new Tank(800, 300, 180);
	int count = 0;
	AnimateObjects animate;
	Canvas canvas;
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	ArrayList<Bullet> bullets2 = new ArrayList<Bullet>();
	ArrayList<Wall> walls = new ArrayList<Wall>();
	ArrayList<Rectangle2D> wallBoxes = new ArrayList<Rectangle2D>();
	ArrayList<AudioClip> music = new ArrayList<AudioClip>();
	ArrayList<String> powerups = new ArrayList<String>();
	int ammo = 5;
	int ammo2 = 5;
	int reload = 0;
	int reload2 = 0;
	int shoot = 0;
	int shoot2 = 0;
	int lives1 = 3;
	int lives2 = 3;
	int shootSpeed1 = 25;
	int shootSpeed2 = 25;
	boolean shootFast1 = false;
	boolean shootFast2 = false;
	boolean w = false;
	boolean a = false;
	boolean s = false;
	boolean d = false;
	boolean space = false;
	boolean u = false;
	boolean l = false;
	boolean dn = false;
	boolean r = false;
	boolean enter = false;
	boolean restart = false;
	URL resource = getClass().getResource("Rapture.mp3");
	AudioClip clip = new AudioClip(resource.toString());
	URL resource2 = getClass().getResource("QueenOfTheNight.mp3");
	AudioClip clip2 = new AudioClip(resource2.toString());
	URL resource3 = getClass().getResource("Fall.mp3");
	AudioClip clip3 = new AudioClip(resource3.toString());
	URL menuResource = getClass().getResource("danzon.mp3");
	AudioClip menu = new AudioClip(menuResource.toString());
	URL bulletResource = getClass().getResource("Glock-1.wav");
	AudioClip bulletSound = new AudioClip(bulletResource.toString());
	URL explosionResource = getClass().getResource("explosion.mp3");
	AudioClip explosion = new AudioClip(explosionResource.toString());
	URL powerResource = getClass().getResource("powerup.mp3");
	AudioClip powerup = new AudioClip(powerResource.toString());
	URL winResource = getClass().getResource("win.mp3");
	AudioClip win = new AudioClip(winResource.toString());
	int song = (int)(Math.random()*3);
	String powerAvailable = "";
	boolean bulletSpeed1 = false;
	boolean bulletSpeed2 = false;
	int power1 = 0;
	int power2 = 0;
	boolean hasPower1 = false;
	boolean hasPower2 = false;
	boolean playMenu = true;
	boolean playWin = true;
	int countdown = 0;

	public static void main(String[] args)
	{
		launch();
	}
	public void start(Stage stage)
	{
		stage.setTitle("Tanks");
		Group root = new Group();
		canvas = new Canvas(1000, 600);
		root.getChildren().add(canvas);
		Scene scene = new Scene(root, Color.BROWN);
		stage.setScene(scene);
		gc = canvas.getGraphicsContext2D();
		image1 = new Image( "tank1.jpg");
		image2 = new Image( "tank2.jpg");
		heart = new Image("heart.png");
		speed = new Image("speed.jpg");
		manyBullets = new Image("manyBullets.jpg");
		speedBullet = new Image("speedBullet.jpg");
		gc.drawImage( image1, 200, 300 );
		gc.drawImage( image2, 800, 300 );
        iv1.setImage(image1);
        iv1.setX(tank1.getX());
      	iv1.setY(tank1.getY());
      	iv1.setRotate(180);
      	iv2.setImage(image2);
		iv2.setX(tank1.getX());
      	iv2.setY(tank2.getY());
		root.getChildren().add(iv1);
		root.getChildren().add(iv2);
		animate = new AnimateObjects();
		animate.start();
		stage.show();
		scene.addEventHandler(KeyEvent.KEY_PRESSED,this);
		scene.addEventHandler(KeyEvent.KEY_RELEASED,this);
		scene.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
		music.add(clip);
		music.add(clip2);
		music.add(clip3);
		clip.setPriority(2);
		clip2.setPriority(2);
		clip3.setPriority(2);
		explosion.setPriority(1);
		music.get(song).play();
		bulletSound.setVolume(0.1);
		explosion.setVolume(0.3);
		powerup.setVolume(0.6);
		win.setVolume(1);
		powerups.add("life");
		powerups.add("speed");
		powerups.add("speedBullet");
		powerups.add("shootFast");
		powerAvailable = powerups.get((int)(Math.random()*powerups.size()));
	}

	public class AnimateObjects extends AnimationTimer
	{
		public void handle(long now)
		{
			gc.setFill( Color.BLACK);
			gc.setStroke( Color.BLACK );
			countdown++;

			if(lives1>0 && lives2>0)
			{
				count++;
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				tank1.setOldX();
				tank1.setOldY();
				tank2.setOldX();
				tank2.setOldY();
				if(count<600)
				{
					switch(powerAvailable)
					{
						case "life":
							gc.drawImage(heart, 500, 300 );
							break;
						case "speed":
							gc.drawImage(speed, 500, 300);
							break;
						case "speedBullet":
							gc.drawImage(speedBullet, 500, 300);
							break;
						case "shootFast":
							gc.drawImage(manyBullets, 500, 300);
							break;
					}
				}
				else{
					powerAvailable="";
				}
				Rectangle2D powerBox = new Rectangle2D(500,300, heart.getWidth(), heart.getHeight());
				if(count>1200) //Random powerups
				{
					powerAvailable = powerups.get((int)(Math.random()*powerups.size()));
					count=0;
				}
				if(hasPower1) //Timer for holding powerups
					power1++;
				if(hasPower2)
					power2++;
				if(power1>500) //Lose powerup
				{
					losePowerups1();
				}
				if(power2>500)
				{
					losePowerups2();
				}
				shoot++;
				shoot2++;
				double oldX = tank1.getX();
				if(ammo<5) //Reloading
				{
					if(reload>60)
					{
						ammo++;
						reload=0;
					}
					reload++;
				}
				else
				{
					reload=0;
				}
				if(ammo2<5)
				{
					if(reload2>60)
					{
						ammo2++;
						reload2=0;
					}
					reload2++;
				}
				else
				{
					reload2=0;
				}

				if(w) //Controls
				{
					tank1.forward();
				}
				if(a)
				{
					tank1.left();
					iv1.setRotate(iv1.getRotate() - tank1.getTurn());
				}
				if(s)
				{
					tank1.back();
				}
				if(d)
				{
					tank1.right();
					iv1.setRotate(iv1.getRotate() + tank1.getTurn());
				}
				if(u)
				{
					tank2.forward();
				}
				if(l)
				{
					tank2.left();
					iv2.setRotate(iv2.getRotate() - tank2.getTurn());
				}
				if(r)
				{
					tank2.right();
					iv2.setRotate(iv2.getRotate() + tank2.getTurn());
				}
				if(dn)
				{
					tank2.back();
				}
				if(space && ammo>0 && shoot>shootSpeed1)
				{
					bulletSound.play();
					if(!shootFast1)
						ammo--;
					Bullet b = new Bullet(tank1.getAngle(), tank1.getX(), tank1.getY());
					bullets.add(b);
					shoot=0;
					if(bulletSpeed1)
						b.setSpeed(10);
				}
				if(enter && ammo2>0 && shoot2>shootSpeed2)
				{
					bulletSound.play();
					if(!shootFast2)
						ammo2--;
					Bullet b = new Bullet(tank2.getAngle(), tank2.getX(), tank2.getY());
					bullets2.add(b);
					shoot2=0;
					if(bulletSpeed2)
						b.setSpeed(10);
				}

				iv1.setX(tank1.getX());
      			iv1.setY(tank1.getY());
				iv2.setX(tank2.getX());
      			iv2.setY(tank2.getY());
				Rectangle2D rect1 = new Rectangle2D(tank1.getX(),tank1.getY(),image1.getWidth(),image1.getHeight() );
				Rectangle2D rect2 = new Rectangle2D(tank2.getX(),tank2.getY(),image2.getWidth(),image2.getHeight() );
				walls.add(new Wall(0,0,50,canvas.getHeight()));
				walls.add(new Wall(0,550,canvas.getWidth(),50));
				walls.add(new Wall(0,0,canvas.getWidth(),50));
				walls.add(new Wall(950,0,50,canvas.getHeight()));
				walls.add(new Wall(360, 180, 30, 240));
				walls.add(new Wall(640, 180, 30, 240));
				if(rect1.intersects(rect2)) //Prevents tanks overlapping
				{
					tank1.revert();
					tank2.revert();
				}

				if(rect1.intersects(powerBox)) //Getting powerups
				{
					if(!powerAvailable.equals(""))
						powerup.play();
					switch(powerAvailable)
					{
						case "life":
							lives1++;
							break;
						case "speed":
							tank1.setSpeed(3);
							tank1.setTurn(4);
							break;
						case "speedBullet":
							bulletSpeed1 = true;
							break;
						case "shootFast":
							shootFast1 = true;
							shootSpeed1 = 20;
							break;
					}
					powerAvailable="";
					count=600; //Powerup won't show until you leave where it appears
					hasPower1 = true;
				}
				if(rect2.intersects(powerBox))
				{
					if(!powerAvailable.equals(""))
						powerup.play();
					switch(powerAvailable)
					{
						case "life":
							lives2++;
							break;
						case "speed":
							tank2.setSpeed(3);
							tank2.setTurn(4);
							break;
						case "speedBullet":
							bulletSpeed2 = true;
							break;
						case "shootFast":
							shootSpeed2 = 20;
							shootFast2 = true;
							break;
					}
					powerAvailable="";
					count=600;
					hasPower2 = true;
				}

				for(int i = 0; i < 6; i++)
				{
					gc.fillRect(walls.get(i).getX(),walls.get(i).getY(),walls.get(i).getWidth(),walls.get(i).getHeight());
					wallBoxes.add(new Rectangle2D(walls.get(i).getX(),walls.get(i).getY(),walls.get(i).getWidth(),walls.get(i).getHeight()));
					if(rect1.intersects(wallBoxes.get(i))) //Crashing into wall
					{
						tank1.revert();
					}
					if(rect2.intersects(wallBoxes.get(i)))
					{
						tank2.revert();
					}
				}

				for(int i = 0; i < bullets.size();i++)
				{
					bullets.get(i).update();
					double bulletX = bullets.get(i).getX();
					double bulletY = bullets.get(i).getY();
					gc.fillOval(bulletX, bulletY, 6,6);
					Rectangle2D bulletOutline = new Rectangle2D(bulletX, bulletY, 6, 6);
					if(bulletOutline.intersects(rect2))
					{
						explosion.play();
						bullets.remove(i);
						i--;
						lives2--;
					}
					for(int j = 0; j < wallBoxes.size(); j++)
					{
						if(i>-1 && bulletOutline.intersects(wallBoxes.get(j)))
						{
							bullets.remove(i);
							i--;
						}
					}
				}

				for(int i = 0; i < bullets2.size();i++)
				{
					bullets2.get(i).update();
					double bulletX = bullets2.get(i).getX();
					double bulletY = bullets2.get(i).getY();
					gc.fillOval(bulletX, bulletY, 6, 6);
					Rectangle2D bulletOutline = new Rectangle2D(bulletX, bulletY, 6, 6);
					if(bulletOutline.intersects(rect1))
					{
						explosion.play();
						bullets2.remove(i);
						i--;
						lives1--;
					}
					for(int j = 0; j < wallBoxes.size(); j++)
					{
						if(i>-1 && bulletOutline.intersects(wallBoxes.get(j)))
						{
							bullets2.remove(i);
							i--;
						}
					}
				}
				gc.setFill( Color.WHITE); //Score
				gc.setStroke( Color.WHITE );
				gc.setLineWidth(1);
				Font font = Font.font("Arial", FontWeight.NORMAL, 20 );
				gc.setFont( font );
				gc.fillText("Lives1: " + lives1, 100, 30 );
				gc.strokeText( "Lives1: " + lives1, 100, 30 );
				gc.fillText("Lives2: " + lives2, 800, 30 );
				gc.strokeText( "Lives2: " + lives2, 800, 30 );
				restart = false;
			}
			else //Game ends
			{
				music.get(song%3).stop();
				if(playMenu)
					menu.play();
				playMenu=false;
				if(playWin)
					win.play();
				playWin=false;
				gc.setFill( Color.YELLOW);
				gc.setStroke( Color.BLACK );
				gc.setLineWidth(1);
				Font font = Font.font("Arial", FontWeight.NORMAL, 80 );
				gc.setFont( font );
				if(lives1>0)
				{
					gc.fillText("Player 1 Wins", 250, 150 );
					gc.strokeText( "Player 1 Wins", 250, 150 );
				}
				else
				{
					gc.fillText("Player 2 Wins", 250, 200 );
					gc.strokeText( "Player 2 Wins", 250, 200 );
				}
				if(restart) //Default settings
				{
					menu.stop();
					song++;
					music.get(song%3).play();
					playWin = true;
					playMenu=true;
					powerAvailable = powerups.get((int)(Math.random()*powerups.size()));
					losePowerups1();
					losePowerups2();
					lives1 = 3;
					lives2 = 3;
					count=0;
					ammo=5;
					ammo2=5;
					reload=0;
					reload2=0;
					tank1.setAngle(0);
					tank1.setX(200);
					tank1.setY(300);
					tank2.setAngle(180);
					tank2.setX(800);
					tank2.setY(300);
					iv1.setRotate(180 );
					iv2.setRotate(0);
					for(int i = 0; i < bullets.size();i++)
					{
						bullets.remove(i);
						i--;
					}
					for(int i = 0; i < bullets2.size();i++)
					{
						bullets2.remove(i);
						i--;
					}
				}
			}
		}
	}

	public void losePowerups1()
	{
		bulletSpeed1 = false;
		tank1.setSpeed(1.5);
		tank1.setTurn(2);
		hasPower1 = false;
		shootSpeed1=25;
		shootFast1=false;
		power1=0;
	}

	public void losePowerups2()
	{
		bulletSpeed2 = false;
		tank2.setSpeed(1.5);
		tank2.setTurn(2);
		hasPower2 = false;
		shootSpeed2=25;
		shootFast2=false;
		power2=0;
	}

	public void handle(final InputEvent event)
	{
		if (event.getEventType()== KeyEvent.KEY_PRESSED){
			if (((KeyEvent)event).getCode() == KeyCode.A )
				a = true;
			if (((KeyEvent)event).getCode() == KeyCode.D )
				d = true;
			if (((KeyEvent)event).getCode() == KeyCode.W )
				w=true;
			if (((KeyEvent)event).getCode() == KeyCode.S )
				s= true;
			if (((KeyEvent)event).getCode() == KeyCode.LEFT )
				l = true;
			if (((KeyEvent)event).getCode() == KeyCode.RIGHT )
				r = true;
			if (((KeyEvent)event).getCode() == KeyCode.UP )
				u=true;
			if (((KeyEvent)event).getCode() == KeyCode.DOWN )
				dn = true;
			if (((KeyEvent)event).getCode() == KeyCode.SPACE)
				space = true;
			if (((KeyEvent)event).getCode() == KeyCode.ENTER)
			{
				enter = true;
			}
		}

		if(event.getEventType()== KeyEvent.KEY_RELEASED)
		{
			if (((KeyEvent)event).getCode() == KeyCode.W )
				w=false;
			if (((KeyEvent)event).getCode() == KeyCode.A )
				a=false;
			if (((KeyEvent)event).getCode() == KeyCode.S )
				s=false;
			if (((KeyEvent)event).getCode() == KeyCode.D )
				d=false;
			if (((KeyEvent)event).getCode() == KeyCode.UP )
				u=false;
			if (((KeyEvent)event).getCode() == KeyCode.LEFT )
				l=false;
			if (((KeyEvent)event).getCode() == KeyCode.RIGHT )
				r=false;
			if (((KeyEvent)event).getCode() == KeyCode.DOWN )
				dn=false;
			if (((KeyEvent)event).getCode() == KeyCode.SPACE)
				space = false;
			if (((KeyEvent)event).getCode() == KeyCode.ENTER)
				enter = false;
		}

		if (event instanceof MouseEvent){
			restart=true;
		}
	}
}