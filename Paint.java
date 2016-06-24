/*
Max Bugay
*/
package paint;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Paint extends JApplet {
    /*
    Creates window and content inside of it.
    */
   public static void main(String[] args) {
      JFrame PaintWindow = new JFrame("Paint"); //New Window
      PaintPanel paint = new PaintPanel(); //New Panel
      PaintWindow.setContentPane(paint);//Put panel in window
      PaintWindow.setSize(600,600); //Size of Window
      PaintWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit
      PaintWindow.setVisible(true); //Visible Window
   }
   /*
   Constructor for Paint Program
   */
   public Paint() {
      setContentPane(new PaintPanel()); 
   }
   /*
   Builds Panel inside of window using JPanel with MouseListener and MouseMotionListener
   */
   public static class PaintPanel extends JPanel implements MouseListener, MouseMotionListener { 
      private final static int BLACK = 0, RED = 1, ORANGE = 2, YELLOW = 3, GREEN = 4, BLUE = 5, MAGENTA = 6; //ints for colors
      private int currentColor = BLACK; //starting color
      private int prevX, prevY; //x and y coordinates
      private boolean dragging; //whether or not the user is dragging mouse      
      private Graphics drawing; //for the color the user will be drawing
      PaintPanel() {
         setBackground(Color.WHITE); //background
         addMouseListener(this); //mouselistener
         addMouseMotionListener(this); //mousemotionlistener
      }
      /*
      Produces the options for the colors
      */
      public void paintComponent(Graphics g) {
         super.paintComponent(g); //sets up for drawing
         int width = getWidth();  //width of window
         int height = getHeight(); //height of window
         int space = (height - 50) / 7; //spacing for colors
         g.setColor(Color.DARK_GRAY); //behind colors, makes them look separated
         g.drawRect(0, 0, width-1, height-1);
         g.drawRect(0, 1, width-3, height-3);
         g.drawRect(0, 2, width-5, height-5);
         g.fillRect(0, 0, 50, height);
         g.setColor(Color.WHITE); //drawboard
         g.fillRect(0, height-50, 50, 50);
         g.setColor(Color.BLACK); //for new board button
         g.drawRect(0, height-50, 50, 50);
         g.drawString("NEW", width-575, height-25); //new slate
         g.setColor(Color.BLACK);
         g.fillRect(0, 3 + 0*space, 50, space-3); //black color
         g.setColor(Color.RED);
         g.fillRect(0, 3 + 1*space, 50, space-3); //red color
         g.setColor(Color.ORANGE);
         g.fillRect(0, 3 + 2*space, 50, space-3); //orange color
         g.setColor(Color.YELLOW);
         g.fillRect(0, 3 + 3*space, 50, space-3); //yellow color
         g.setColor(Color.GREEN);
         g.fillRect(0, 3 + 4*space, 50, space-3); //green color
         g.setColor(Color.BLUE);
         g.fillRect(0, 3 + 5*space, 50, space-3); //blue color
         g.setColor(Color.MAGENTA);
         g.fillRect(0, 3 + 6*space, 50, space-3); //magenta color
         g.setColor(Color.WHITE);
         g.drawRect(0, 1 + currentColor*space, 50, space); //border around color selected
         g.drawRect(0, 2 + currentColor*space, 50, space-2);
      }
      /*
      Gives options to change and select a new color
      */
      private void changeColor(int y) { //color selector
         int height = getHeight();
         int space = (height - 50) / 7;
         int newColor = y / space;
         if (newColor < 0 || newColor > 6) //no more than 7 colors
            return;
         Graphics g = getGraphics();
         g.setColor(Color.DARK_GRAY);
         g.drawRect(0, 1 + currentColor*space, 50, space);
         g.drawRect(0, 2 + currentColor*space, 50, space-2);
         currentColor = newColor;
         g.setColor(Color.WHITE);
         g.drawRect(0, 1 + currentColor*space, 50, space);
         g.drawRect(0, 2 + currentColor*space, 50, space-2);
         g.dispose(); //dispose of previous color
      }
      /*
      Sets Color to the color chosen by the user
      */
      private void setColor() { //chosen color gets set
         drawing = getGraphics();
         switch (currentColor) {
         case BLACK:
            drawing.setColor(Color.BLACK);
            break;
         case RED:
            drawing.setColor(Color.RED);
            break;
         case ORANGE:
            drawing.setColor(Color.ORANGE);
            break;
         case YELLOW:
            drawing.setColor(Color.YELLOW);
            break;
         case GREEN:
            drawing.setColor(Color.GREEN);
            break;
         case BLUE:
            drawing.setColor(Color.BLUE);
            break;
         case MAGENTA:
            drawing.setColor(Color.MAGENTA);
            break;
         }
      }
      /*
      Detects when the mouse is pressed by the user
      */
      public void mousePressed(MouseEvent e) { 
         int x = e.getX(); //x coordinate
         int y = e.getY(); //y coordinate
         int width = getWidth();
         int height = getHeight();
         if (dragging == true) //continues if pressed and dragging
            return;
         if (x < 55) { //within range of color
            if (y > 500) 
               repaint();
            else
               changeColor(y);
         }
         else if (x > 55 && x < width-10 && y > 5 && y < height-5) { //drawing area border
            prevX = x;
            prevY = y;
            dragging = true;
            setColor();
         }
      }
      /*
      Detects when the mouse is released by the user
      */
      public void mouseReleased(MouseEvent e) { 
         if (dragging == false) //stopped dragging
            return;  
         dragging = false;
         drawing.dispose(); //dispose of color selected until mouse is pressed 
         drawing = null; //no drawing
      }
      /*
      Detects when the mouse is dragged and whether it is pressed to draw or released to not draw
      */
      public void mouseDragged(MouseEvent e) {
         if (dragging == false) //still
            return;
         int x = e.getX(); 
         int y = e.getY(); 
         if (x < 55) //left side border, stops from drawing over color selector
            x = 55;  
         if (x > getWidth()-10) //right side border      
            x = getWidth()-10;
         if (y < 5) //top border
            y = 5;
         if (y > getHeight()-5) //bottom border
            y = getHeight()-5;
         drawing.drawLine(prevX, prevY, x, y);
         prevX = x; 
         prevY = y;
      }
      public void mouseEntered(MouseEvent evt) { } //all required for mouselistner and mousemotionlistener
      public void mouseExited(MouseEvent evt) { } 
      public void mouseClicked(MouseEvent evt) { }
      public void mouseMoved(MouseEvent evt) { } 
   } 
}