import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Random;


public class fireworks extends JFrame { //main class
	//GUI elements
	String[] colorChoice= { "Black","Blue", "Green", "Red", "Magenta", "Cyan", "Orange", "pink"};
	String[] pattern= { "None","Flower", "Color Blast", "Insane Box", "Boxed Flower", "Line & Star", "Star Splash", "Crazy Chakri", "Big Bang","The Diamond","Random"};

	Color[] c= { Color.BLACK,Color.BLUE,  Color.GREEN, Color.RED, Color.MAGENTA, Color.CYAN, Color.ORANGE, Color.PINK};//declares an array of integer

	
	//components of GUI
	JButton launch, launch2;
	JSlider velocity, angle, timeFraction;
	JLabel info2, info3;
	JFrame frame = new JFrame(" main frame");
	JPanel panel;
	JComboBox combo1, combo2;
	GraphicsCanvas canvas;
	GraphicsCanvas2 canvas2;


	//variables
	double t, tof, tf;
	int v=75;
	int a=45;
	int h;
	double a1;
	int x1;
	int y1;
	double cos;
	double sin;
	Color ctemp; 
	int ptemp;
	Random random= new Random();
	Boolean multiple= false;

	//constructor
	public fireworks(){
		setTitle("fireworks");
		setLayout(new BorderLayout());//sets layout as border layout
		canvas= new GraphicsCanvas();//creates instance of graphics canvas class
		canvas.setPreferredSize(new Dimension(500,600));//sets preferred size of canvas 
		panel= new framelike();
		panel.setBackground(Color.WHITE);
		JPanel panel2= new JPanel();
		panel2.setLayout(new GridLayout(0,2));
		panel2.add(canvas); 
		canvas2= new GraphicsCanvas2();
		canvas2.setPreferredSize(new Dimension(500,600));
		panel2.add(canvas2);
		panel2.setBackground(Color.WHITE);
		add(panel, BorderLayout.SOUTH);
		add(panel2, BorderLayout.CENTER);
		pack();
		
	}

	public class framelike extends JPanel implements ActionListener, ChangeListener, ItemListener{

	   public framelike() {//constructor definition
		
			this.setLayout(new GridLayout(0,6));
			//definitions for the elements of GUI
			
			JPanel TIME = new JPanel();
			TIME.setLayout(new GridLayout(2,0));

			timeFraction= new JSlider(0,50,0);
			timeFraction.setMajorTickSpacing(10);
			timeFraction.setMinorTickSpacing(2);
			timeFraction.setPaintTicks(true);
			timeFraction.addChangeListener(this);//adds action listener to itself
			TIME.add(timeFraction);
			
			JLabel info1=  new JLabel("TIME");//label to display the content of the text field
			info1.setFont(new Font("Courier New", Font.BOLD, 20));
			TIME.add(info1);
			this.add(TIME);
			
			angle=new JSlider(1,0,90,45);//defines slider with a range of 90 that starts from 0
			angle.setMinorTickSpacing(5);//sets the difference between major ticks
			angle.setMajorTickSpacing(10);//sets the distance between the minor ticks
			angle.setPaintTicks(true);//makes the ticks visible
			angle.addChangeListener(this);//adds change Listener to itself
			JPanel ANGLE = new JPanel();
			
			info3=  new JLabel("angle: 45");//displays the current value of the slider
			info3.setFont(new Font("Courier New", Font.BOLD, 20));
			ANGLE.setLayout(new GridLayout(2,0)); //The JFrame (or JPanel) is splitted right in two "chambers"
			ANGLE.add(angle);
			ANGLE.add(info3);
			add(ANGLE);
			
			JPanel VELOCITY = new JPanel();
			VELOCITY.setLayout(new GridLayout(2,0));
			velocity=new JSlider(50,100,75);//defines slider with a range of 100 that starts from 0
			velocity.setMinorTickSpacing(5);//sets the difference between major ticks
			velocity.setMajorTickSpacing(10);//sets the distance between the minor ticks
			velocity.setPaintTicks(true);//makes the ticks visible
			velocity.addChangeListener(this);//adds change Listener to itself
			VELOCITY.add(velocity);

			info2=  new JLabel("velocity: 75");//displays the current value of the slider
			info2.setFont(new Font("Courier New", Font.BOLD, 20));
			VELOCITY.add(info2);
			this.add(VELOCITY);

			combo1= new JComboBox(colorChoice);
			combo1.addItemListener(this);
			combo1.setFont(new Font("Courier New", Font.BOLD, 20));

			add(combo1);
			
			combo2= new JComboBox(pattern);
			combo2.addItemListener(this);
			combo2.setFont(new Font("Courier New", Font.BOLD, 20));

			add(combo2);

			launch= new JButton("launch");
			launch.addActionListener(this);
			launch.setFont(new Font("Courier New", Font.BOLD, 20));
			
			launch2= new JButton("launch2");
			launch2.addActionListener(this);
			launch2.setFont(new Font("Courier New", Font.BOLD, 20));

			JPanel launchpad= new JPanel();
			launchpad.setLayout(new GridLayout(2,0));
			launchpad.add(launch);
			launchpad.add(launch2);
			this.add(launchpad);
			
			pack();
		}	

		@Override
		public void actionPerformed(ActionEvent e) {//if any button is pressed this method gets activated
		
			String c= e.getActionCommand();
			if(c.equals("launch")) {//if the launch button is pressed
				multiple=false;
				calculate();//calls the calculate function
			}
			if(c.equals("launch2")) {//if the launch2 button is pressed
				multiple=true;
				calculate();
			}
			
			System.out.println(" the value of multiplle is"+ multiple);
		}

		@Override
		public void stateChanged(ChangeEvent e) {// if any of the sliders are used
			if(e.getSource().equals(velocity)){
				int value= velocity.getValue();
				info2.setText("velocity: " + value);
				v=value;
				System.out.println("velocity"+ v);
			}

			if(e.getSource().equals(timeFraction)){
				double value2= timeFraction.getValue();
				tf=(value2)/100;
				System.out.println("timeFraction"+ tf);
			}

			if(e.getSource().equals(angle)){
				int value1= angle.getValue();
				info3.setText("angle: " + value1);
				a=value1;
				System.out.println("angle"+ a);
			}
		}

		@Override
		public void itemStateChanged(ItemEvent e) {//if a combo box is used 
			if(e.getSource().equals(combo1))
				//if(e.getStateChange()==ItemEvent.SELECTED) {
					ctemp=(c[combo1.getSelectedIndex()]);
				//}	

			if(e.getSource().equals(combo2))
				if(e.getStateChange()==ItemEvent.SELECTED) {
					ptemp=combo2.getSelectedIndex();
				}	
		}
	}

	public class GraphicsCanvas extends JComponent{
		BufferedImage img1, img2, img3, img4, img5,img6, img7;
		
		

		public GraphicsCanvas(){
//importing the images
			try {
				img1=  ImageIO.read(new File("fw3.png"));
			}catch(IOException e) {
				System.out.println(" no file");
			}

			try {
				img4=  ImageIO.read(new File("fw4.png"));
			}catch(IOException e) {
				System.out.println(" no file");
			}		
			try {
				img5=  ImageIO.read(new File("fw5.png"));
			}catch(IOException e) {
				System.out.println(" no file");
			}
			try {
				img6=  ImageIO.read(new File("fw6.png"));
			}catch(IOException e) {
				System.out.println(" no file");
			}
			try {
				img7=  ImageIO.read(new File("sm3.png"));
			}catch(IOException e) {
				System.out.println(" no file");
			}
		}

		@Override
		public void paintComponent(Graphics g) {//paint component method for this class
			h= getHeight();//
			int w= getWidth();
			
			int r= random.nextInt(9)+1;//generates a random number between 1 and 10
			g.drawLine(0, h-1, w, h-1);//draws the base line

			cos= Math.cos(a1);
			sin= Math.sin(a1);
			int xcurrent=-100;
			int ycurrent=-100;

			g.setColor(ctemp);
			while(xcurrent<x1) {//draws the projectile path
				double ytemp= ((v*sin*xcurrent)/(v*cos))- 4.9*((Math.pow(xcurrent, 2)/(Math.pow(v*cos, 2))));
				ycurrent= (int) ytemp;
				g.fillRect(xcurrent+1,h-ycurrent,5,5);

				xcurrent+=1;
			}

			switch(ptemp){//defines different patterns
			case 0:
				break;
			case 1:
				g.fillOval((x1-50), h-(y1+50), 100, 100);
				g.fillOval(x1+50, h-(y1+25), 100, 50);
				g.fillOval(x1+50-200, h-(y1+25), 100, 50);
				g.fillOval(x1-25, h-(y1+150), 50, 100);
				g.fillOval(x1-25, h-(y1-50), 50, 100);
				g.setColor(Color.white);
				g.fillOval(x1-12, h-(y1-50), 24, 75);
				g.fillOval(x1-12, h-(y1+125), 24, 75);
				g.fillOval((x1-25), h-(y1+25), 50, 50);

				break;
			case 2:
				g.drawImage(img1,x1-200,h-(y1+200),400,400, this);
				break;
			case 3:
				for(int i=0; i<200; i=i+10) {
					g.drawLine(x1+0-100,(h-y1)+0-100, x1+i-100, (h-y1)+100-i);

					g.drawLine(x1+200-100,(h-y1)+0-100,x1+i-100,(h-y1)+i-100);
					g.drawLine(x1+0-100,(h-y1)+200-100,x1+i-100,(h-y1)+i-100);
					g.drawLine(x1+200-100,(h-y1)+200-100,x1+i-100,(h-y1)+200-i-100);
				}
				break;
			case 4:
				for(int i=0; i<200; i=i+5) {
					g.drawLine(x1+0-100,(h-y1)-100+ i,x1+i-100, (h-y1)+ 200-100);
					g.drawLine(x1+i-100, (h-y1)+ 200-100,x1+200-100, (h-y1)+ 200-i-100);
					g.drawLine(x1+200-100, (h-y1)+ 200-i-100,x1+200-i-100, (h-y1)+ 0-100);
					g.drawLine(x1+200-i-100, (h-y1)+ 0-100,x1+0-100,(h-y1)+  i-100);
				}
				break;
			case 5:
				g.drawImage(img4,x1-125,h-(y1+125),250,250, this);
				break;
			case 6:
				g.drawImage(img5,x1-125,h-(y1+125),250,250, this);
				break;
			case 7:
				g.drawImage(img6,x1-125,h-(y1+125),250,250, this);
				break;
			case 8:
				g.drawImage(img7,x1-125,h-(y1+125),250,250, this);
				break;
			case 9:
				for(int i=0; i<100; i=i+2) {
					g.drawLine(x1, h-y1, x1+100-i, h-y1+3*i/2);
					g.drawLine(x1, h-y1, x1+100-i, h-y1-3*i/2);
					g.drawLine(x1, h-y1, x1-100+i, h-y1+3*i/2);
					g.drawLine(x1, h-y1, x1-100+i, h-y1-3*i/2);
				}
				break;
			case 10:
				ptemp=r;
				repaint();
				break;
			}
		}
	}
	
	public class GraphicsCanvas2 extends JComponent{//second canvas
		BufferedImage Img1, Img2, Img3, Img4, Img5,Img6, Img7;
		
		

		public GraphicsCanvas2(){
			int w= this.getWidth();
		

			try {
				Img1=  ImageIO.read(new File("fw3.png"));
			}catch(IOException e) {
				System.out.println(" no file");
			}

			try {
				Img4=  ImageIO.read(new File("fw4.png"));
			}catch(IOException e) {
				System.out.println(" no file");
			}		
			try {
				Img5=  ImageIO.read(new File("fw5.png"));
			}catch(IOException e) {
				System.out.println(" no file");
			}
			try {
				Img6=  ImageIO.read(new File("fw6.png"));
			}catch(IOException e) {
				System.out.println(" no file");
			}
			try {
				Img7=  ImageIO.read(new File("sm3.png"));
			}catch(IOException e) {
				System.out.println(" no file");
			}
		}

		@Override
		public void paintComponent(Graphics g) {
			
			h= getHeight();
			int w= getWidth();
			
			int r= random.nextInt(9)+1;
			g.drawLine(0, h-1, w, h-1);
			int mx= w-x1;

			
			System.out.println("i am the paint component method");
			cos= Math.cos(a1);
			sin= Math.sin(a1);
			int xcurrent=-100;
			int ycurrent=-100;

			if(multiple==true) {//the following is painted only if the user clicks the launch2 button
				
			g.setColor(ctemp);
			
			while(xcurrent<x1) {
				double ytemp= ((v*sin*xcurrent)/(v*cos))- 4.9*((Math.pow(xcurrent, 2)/(Math.pow(v*cos, 2))));
				ycurrent= (int) ytemp;
				g.fillRect(w-xcurrent+1,h-ycurrent,5,5);

				xcurrent+=1;
			}

			switch(ptemp){
			case 0:
				break;
			case 1:
				g.fillOval((mx-50), h-(y1+50), 100, 100);
				g.fillOval(mx+50, h-(y1+25), 100, 50);
				g.fillOval(mx+50-200, h-(y1+25), 100, 50);
				g.fillOval(mx-25, h-(y1+150), 50, 100);
				g.fillOval(mx-25, h-(y1-50), 50, 100);
				g.setColor(Color.white);
				g.fillOval(mx-12, h-(y1-50), 24, 75);
				g.fillOval(mx-12, h-(y1+125), 24, 75);
				g.fillOval((mx-25), h-(y1+25), 50, 50);

				break;
			case 2:
				g.drawImage(Img1,mx-200,h-(y1+200),400,400, this);
				break;
			case 3:
				for(int i=0; i<200; i=i+10) {
					g.drawLine(mx+0-100,(h-y1)+0-100, mx+i-100, (h-y1)+100-i);

					g.drawLine(mx+200-100,(h-y1)+0-100,mx+i-100,(h-y1)+i-100);
					g.drawLine(mx+0-100,(h-y1)+200-100,mx+i-100,(h-y1)+i-100);
					g.drawLine(mx+200-100,(h-y1)+200-100,mx+i-100,(h-y1)+200-i-100);
				}
				break;
			case 4:
				for(int i=0; i<200; i=i+5) {
					g.drawLine(mx+0-100,(h-y1)-100+ i,mx+i-100, (h-y1)+ 200-100);
					g.drawLine(mx+i-100, (h-y1)+ 200-100,mx+200-100, (h-y1)+ 200-i-100);
					g.drawLine(mx+200-100, (h-y1)+ 200-i-100,mx+200-i-100, (h-y1)+ 0-100);
					g.drawLine(mx+200-i-100, (h-y1)+ 0-100,mx+0-100,(h-y1)+  i-100);
				}
				break;
			case 5:
				g.drawImage(Img4,mx-125,h-(y1+125),250,250, this);
				break;
			case 6:
				g.drawImage(Img5,mx-125,h-(y1+125),250,250, this);
				break;
			case 7:
				g.drawImage(Img6,mx-125,h-(y1+125),250,250, this);
				break;
			case 8:
				g.drawImage(Img7,mx-125,h-(y1+125),250,250, this);
				break;
			case 9:
				for(int i=0; i<100; i=i+2) {
					g.drawLine(mx, h-y1, mx+100-i, h-y1+3*i/2);
					g.drawLine(mx, h-y1, mx+100-i, h-y1-3*i/2);
					g.drawLine(mx, h-y1, mx-100+i, h-y1+3*i/2);
					g.drawLine(mx, h-y1, mx-100+i, h-y1-3*i/2);
				}
				break;
			case 10:
				ptemp=r;
				repaint();
				break;
			}
		}
	}
}
	//calculates the x and y coordinate of the projectile at time t
	public void calculate() {

		a1=a*3.14/180;// converts angle from degrees to radians
		tof= (v*Math.sin(a1))/4.9;
		System.out.println(" tof : " + tof);
		t= tof*tf;
		System.out.println("time at which firework"+ t);
		double x=  (v* Math.cos(a1)*t);
		double y=  (v*Math.sin(a1)*t- 4.9*Math.pow(t, 2));
		x1=(int)x;
		y1= (int)y;

		repaint();	
	}

	public static void main(String[] args) {//the main method
		fireworks diwali= new fireworks();
		diwali.setVisible(true);
	
		diwali.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

