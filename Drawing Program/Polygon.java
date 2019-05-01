import java.awt.*;
import java.util.*;
public class Polygon extends Item {
  private java.util.List<Point> points = new LinkedList<Point>();

  public Polygon(Point point) {
    this.points.add(point);
  }

  public boolean includes(Point point) {
    for (Point currentPoint : points) {
      if (distance(currentPoint, point) < 10.0) {
        return true;
      }
    }
    return false;
  }

  public Polygon() {
  }

  public void render() {
    uiContext.draw(this);
  }

  public void addPoint(Point point) {
    this.points.add(point);
  }


  public Point getPointN(int pos) {
    return points.get(pos);
  }

  public int getSize() {
    return points.size();
  }

  public java.util.List<Point> getAllPoints() {
    return this.points;
  }

  public Vector getCopy() {
    Vector temp = new Vector<Point>();
    for (int i = 0; i < points.size(); i++) {
      temp.add(points.get(i));
    }
    return temp;
  }
}
