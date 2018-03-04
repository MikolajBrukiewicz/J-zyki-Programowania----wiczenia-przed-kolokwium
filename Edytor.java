import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/*=======  TU PROSZE SIE PODPISAC ==============================
 *      Imie:
 *  Nazwisko:
 *Nr indeksu:
 *==============================================================
 */

class Odcinek {
	int x1, y1, x2, y2;

	/*
	 * Konstruktor tworz¹cy nowy obiekt Odcinek px, py - wspó³rzêdne pocz¹tku
	 * odcinka kx, ky - wspó³rzêdne koñca odcinka
	 */
	public Odcinek(int px, int py, int kx, int ky) {
		x1 = px;
		y1 = py;
		x2 = kx;
		y2 = ky;
	}

	public void przesun(int dx, int dy) {
		x1 += dx;
		y1 += dy;
		x2 += dx;
		y2 += dy;
	}

	public void rysuj(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine(x1, y1, x2, y2);
	}
	
	
	
}

class Rysunek extends JPanel implements  ActionListener, Runnable, MouseListener, KeyListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	LinkedList<Odcinek> lista = new LinkedList<Odcinek>();
	JButton kasuj = new JButton("Wyczysc ekran");
	int xp,yp,xk,yk;
	private JTextField field;
	boolean shift = false, pressed = false;
	
	Rysunek(JTextField a)
	{
		field = a;
		a.setText(Integer.toString(lista.size()));
		kasuj.addActionListener(this);
		add(kasuj);
	}
	
	/**
	 * 
	 */
	
	class Koleczko
	{
		Koleczko(){};
		private int y = -25;
		public void move()
		{
			if(y==375)
				y=-25;
			y+=1;
			repaint();
			
		}
		
		public void draw(Graphics g)
		{
			g.setColor(Color.YELLOW);
			g.fillOval(y, 135, 50, 50); //135 to poprawka z uwagi na obecnosc menu, bez niego dalbym 150
			
			
		}
		
		public void update()
		{
			
		}
	}
	Koleczko cos = new Koleczko();
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		cos.draw(g);
		for(int i=0; i<lista.size(); i++)
		{
			lista.get(i).rysuj(g);
			
		}
		
		
	}

	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		if (source == kasuj)
		{
			lista.clear();
			field.setText(Integer.toString(lista.size()));
			repaint();
		}

	}

	@Override
	public void run() {
		while(true) {
		try {
			cos.move();
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!shift)
		{
		xp=e.getX();
		yp=e.getY();
		}
		else if(shift)
		{
			xk=e.getX();
			yk=e.getY();
			lista.add(new Odcinek(xp, yp, xk, yk));
			field.setText(Integer.toString(lista.size()));
			xp=xk;
			yp=yk;
			repaint();
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!shift)
		{
		xk=e.getX();
		yk=e.getY();
		lista.add(new Odcinek(xp, yp, xk, yk));
		field.setText(Integer.toString(lista.size()));
		repaint();
		}
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_UP:
			for(int i = 0; i<lista.size(); i++)
			{
				lista.get(i).y1-=1;
				lista.get(i).y2-=1;
				repaint();
			}
			break;
		case KeyEvent.VK_DOWN:
			for(int i = 0; i<lista.size(); i++)
			{
				lista.get(i).y1+=1;
				lista.get(i).y2+=1;
				repaint();
			}
			break;
		case KeyEvent.VK_LEFT:
			for(int i = 0; i<lista.size(); i++)
			{
				lista.get(i).x1-=1;
				lista.get(i).x2-=1;
				repaint();
			}
			break;
		case KeyEvent.VK_RIGHT:
			for(int i = 0; i<lista.size(); i++)
			{
				lista.get(i).x1+=1;
				lista.get(i).x2+=1;
				repaint();
			}
			break;
		case KeyEvent.VK_SHIFT:
			shift = true;
			/*if(!pressed)
			{
			xp = MouseInfo.getPointerInfo().getLocation().x;
			yp = MouseInfo.getPointerInfo().getLocation().y;
			pressed=true;
			}*/
			break;
			
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		switch(arg0.getKeyCode())
		{
		case KeyEvent.VK_SHIFT:
			shift=false;
			pressed=false;
			break;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char a = e.getKeyChar();
		switch(a)
		{
		case 'k':
		case 'K':
			lista.clear();
			field.setText(Integer.toString(lista.size()));
			repaint();
			break;
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if(shift == true)
		{
			if(!pressed)
			{
			xp = arg0.getX();
			yp = arg0.getY();
			pressed=true;
			}
		}
		
	}

}

public class Edytor extends JFrame implements KeyListener, MouseListener, MouseMotionListener, ActionListener, Runnable {

	private static final long serialVersionUID = 1L;
	

	private final String OPIS = "Cwiczenie";
	
	JMenu menu = new JMenu("Menu G³ówne");
	JMenuItem[] items = {new JMenuItem("Autor"), new JMenuItem("O programie"), new JMenuItem("Zakoñcz")};
	JMenuBar menubar = new JMenuBar();
	JTextField ilosc = new JTextField(2);
	JLabel opis = new JLabel("Ilosc kresek");
	
	

	
		public Edytor() {
			Rysunek rysunek = new Rysunek(ilosc);
			Thread watek = new Thread(rysunek);
			watek.start();
			setSize(400,400);
			setContentPane(rysunek);
			setTitle("Mikolaj Brukiewicz");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			menu.add(items[0]);
			menu.add(items[1]);
			menu.add(items[2]);
			menubar.add(menu);	
			ilosc.setEditable(false);
			//opis.setSize(25, 100);
			
			
			
		
			rysunek.add(menubar);
			rysunek.add(ilosc);
			rysunek.add(opis);
			setJMenuBar(menubar);
			rysunek.addMouseListener(rysunek);
			rysunek.addKeyListener(rysunek);
			rysunek.addMouseMotionListener(rysunek);
			rysunek.setFocusable(true);
			
			for(int i=0; i<3; i++)
				items[i].addActionListener(this);
			
			setVisible(true);
			
		}

		
	
	// ***obs³uga zdarzeñ przez s³uchacza zdarzeñ KeyListener ***
		public void keyPressed(KeyEvent e) {
			
		}

		public void keyReleased(KeyEvent evt) {
		}

		public void keyTyped(KeyEvent evt) {
		}

		// ***obs³uga zdarzeñ przez s³uchacza zdarzeñ MouseListener
		public void mouseClicked(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {
			
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {


		}

		public void mouseReleased(MouseEvent e) {

			

		}

		// ***obs³uga zdarzeñ przez s³uchacza zdarzeñ MouseMotionListener***
		public void mouseDragged(MouseEvent e) {
			
		}

		public void mouseMoved(MouseEvent e) {

		}

		// ***implementacja interfejsu Runnable***
		@Override
		public void run() {


		}


		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if(source == items[1])
				JOptionPane.showMessageDialog(null, OPIS);
			
			else if(source == items[0])
				JOptionPane.showMessageDialog(null, "Miko³aj Brukiewicz 225954");
			
			else if(source == items[2])
				dispose();
			

		}

	public static void main(String[] args) {
		new Edytor();

	}

}