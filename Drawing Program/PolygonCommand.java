import java.awt.*;
import java.text.*;
public class PolygonCommand extends Command {
  private Polygon polygon;
  private int pointCount;
  private boolean complete = false;
  private Point initialPoint;
  private Point currentPoint;
  private Line mouseLine;

  public PolygonCommand() {
    pointCount = 0;
  }

  public PolygonCommand(Point point) {
    polygon = new Polygon(point);
    initialPoint = point;
    pointCount = 1;
  }

  public void addPoint(Point point) {
    pointCount++;
    polygon.addPoint(point);
  }

  public void setCurrentPoint(Point point){
    if (!complete) {
      currentPoint = point;
      model.unSelect(mouseLine);
      model.removeItem(mouseLine);
      Point prevPoint = polygon.getPointN(polygon.getSize()-1);
      mouseLine = new Line(prevPoint, currentPoint);
      model.addItem(mouseLine);
      model.markSelected(mouseLine);
    }
  }

  public void execute() {
    model.addItem(polygon);
  }
  public boolean undo() {
    model.removeItem(polygon);
    return true;
  }
  public boolean redo() {
    execute();
    return true;
  }

  public boolean end() {
    if (initialPoint == null) {
      undo();
      return false;
    }
    if (polygon.getPointN(polygon.getSize()-1).equals(initialPoint)) {
      model.unSelect(mouseLine);
      model.removeItem(mouseLine);
      complete = true;
    }
    return true;
  }
}
