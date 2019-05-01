import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class PolygonButton  extends JButton implements ActionListener {
  protected JPanel drawingPanel;
  protected View view;
  private MouseHandler mouseHandler;
  private PolygonCommand polygonCommand;
  private UndoManager undoManager;

  public PolygonButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
    super("Polygon");
    this.undoManager = undoManager;
    addActionListener(this);
    view = jFrame;
    drawingPanel = jPanel;
    mouseHandler = new MouseHandler();
  }

  public void actionPerformed(ActionEvent event) {
    view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    // Change cursor when button is clicked
    drawingPanel.addMouseListener(mouseHandler);
    drawingPanel.addMouseMotionListener(mouseHandler);
  // Start listening for mouseclicks on the drawing panel
  }

  private class MouseHandler extends MouseAdapter implements MouseMotionListener{
    private int pointCount = 0;
    private Point firstPoint;

    private Point rangePt1;
    private Point rangePt2;
    private Point rangePt3;
    private Point rangePt4;
    private Point rangePt5;
    private Point rangePt6;
    private Point rangePt7;
    private Point rangePt8;
    private Point rangePt9;

    //make a 3x3 grid of points around firstPoint. Hitting it exactly to end
    //the command is extremely difficult.
    public void makeRange() {
      rangePt1 = new Point((int)firstPoint.getX()-1, (int)firstPoint.getY()-1);
      rangePt2 = new Point((int)firstPoint.getX()+0, (int)firstPoint.getY()-1);
      rangePt3 = new Point((int)firstPoint.getX()+1, (int)firstPoint.getY()-1);
      rangePt4 = new Point((int)firstPoint.getX()-1, (int)firstPoint.getY()+0);
      rangePt5 = new Point((int)firstPoint.getX()+0, (int)firstPoint.getY()+0);
      rangePt6 = new Point((int)firstPoint.getX()+1, (int)firstPoint.getY()+0);
      rangePt7 = new Point((int)firstPoint.getX()-1, (int)firstPoint.getY()+1);
      rangePt8 = new Point((int)firstPoint.getX()+0, (int)firstPoint.getY()+1);
      rangePt9 = new Point((int)firstPoint.getX()+1, (int)firstPoint.getY()+1);
    }

    //check if clicked point is within 3x3 range of firstPoint
    public boolean rangeCheck(Point point) {
      if (point.equals(rangePt1)) {
        return true;
      }
      if (point.equals(rangePt2)) {
        return true;
      }
      if (point.equals(rangePt3)) {
        return true;
      }
      if (point.equals(rangePt4)) {
        return true;
      }
      if (point.equals(rangePt5)) {
        return true;
      }
      if (point.equals(rangePt6)) {
        return true;
      }
      if (point.equals(rangePt7)) {
        return true;
      }
      if (point.equals(rangePt8)) {
        return true;
      }
      if (point.equals(rangePt9)) {
        return true;
      }
      return false;
    }


    public void mouseClicked(MouseEvent event) {
    if (++pointCount == 1) {
        firstPoint = View.mapPoint(event.getPoint());
        makeRange();
        polygonCommand = new PolygonCommand(View.mapPoint(event.getPoint()));
        undoManager.beginCommand(polygonCommand);
    } else if (rangeCheck(View.mapPoint(event.getPoint()))) {
        pointCount = 0;
        polygonCommand.addPoint(firstPoint);
        drawingPanel.removeMouseListener(this);
        drawingPanel.removeMouseMotionListener(this);
        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        undoManager.endCommand(polygonCommand);
      } else  {
        polygonCommand.addPoint(View.mapPoint(event.getPoint()));
        undoManager.endCommand(polygonCommand);
      }
    }
    public void mouseMoved(MouseEvent event) {
      if (polygonCommand != null) {
        polygonCommand.setCurrentPoint(View.mapPoint(event.getPoint()));
      }
    }
  }
}
