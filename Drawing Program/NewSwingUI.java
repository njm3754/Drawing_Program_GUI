import java.awt.Graphics;
import java.awt.Point;
import java.util.*;
public class NewSwingUI implements UIContext {
  private Graphics graphics;
  private static NewSwingUI swingUI;
  private NewSwingUI() {
  }
  public static NewSwingUI getInstance() {
    if (swingUI == null) {
      swingUI = new NewSwingUI();
    }
    return swingUI;
  }
  public  void setGraphics(Graphics graphics) {
    this.graphics = graphics;
  }
  public void draw(Label label) {
    if (label.getStartingPoint() != null) {
      if (label.getText() != null) {
        graphics.drawString(label.getText(), (int) label.getStartingPoint().getX(), (int) label.getStartingPoint().getY());
      }
    }
    int length = graphics.getFontMetrics().stringWidth(label.getText());
    graphics.drawString("_", (int) label.getStartingPoint().getX() + length, (int) label.getStartingPoint().getY());
  }
  public void draw(Line line) {
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    if (line.getPoint1() != null) {
      i1 = Math.round((float) (line.getPoint1().getX()));
      i2 = Math.round((float) (line.getPoint1().getY()));
      if (line.getPoint2() != null) {
        i3 = Math.round((float) (line.getPoint2().getX()));
        i4 = Math.round((float) (line.getPoint2().getY()));
      } else {
        i3 = i1;
        i4 = i2;
      }
      graphics.drawLine(i1, i2, i3, i4);
    }
  }

  

    public void draw(Polygon polygon){
      int x1 = 0;
      int y1 = 0;

      int x2 = 0;
      int y2 = 0;

      java.util.List<Point> drawPoints = polygon.getAllPoints();

      Point prevPoint = drawPoints.get(0);
      if (drawPoints.size() > 0) {
        x1 = Math.round((float) (prevPoint.getX()));
        y1 = Math.round((float) (prevPoint.getY()));
        graphics.drawRect((int)x1-1, (int)y1-1, 3, 3);
        for (Point currentPoint : drawPoints) {
          if (currentPoint != null) {
              if (currentPoint.equals(drawPoints.get(0)) && drawPoints.size()<=1) {
                continue;
              } else {
                x2 = Math.round((float) (currentPoint.getX()));
                y2 = Math.round((float) (currentPoint.getY()));
                graphics.drawLine(x1, y1, x2, y2);
                prevPoint = currentPoint;
                x1 = x2;
                y1 = y2;
              }
          }
        }
      }
    }


    public void draw(Item item) {
    System.out.println( "Cant draw unknown Item \n");
    }
}
