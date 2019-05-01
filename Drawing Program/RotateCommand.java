import java.awt.*;
import java.util.*;
public class RotateCommand extends Command {
  private Item item;
  private Double shiftX;
  private Double shiftY;
  private Point originalPoint;
  private Point movePoint;

  public RotateCommand() {
  }

  public void setPoint(Point point) {
    Enumeration enumeration = model.getItems();
    while (enumeration.hasMoreElements()) {
      Item currentItem = (Item)(enumeration.nextElement());
      if (currentItem.includes(point)) {
        originalPoint = point;
        model.markMove(currentItem);
        model.clearCopy();
        item = currentItem;
        break;
      }
    }
  }

  public void loadCopies() {
    Enumeration items = model.getItems();
    while(items.hasMoreElements()) {
      Item current = (Item) items.nextElement();
      model.addCopy(current.getCopy());
    }
  }

  public void move(Point p) {
    if(item != null) {
      Vector<Point> points = item.getCopy();
 

      shiftX = p.getX() - originalPoint.getX();
      shiftY = p.getY() - originalPoint.getY();
      this.movePoint = p;
      for(Point point : points) {
    	point.setLocation(originalPoint.getX() + (point.getX()-originalPoint.getX())*Math.cos(90) - (point.getY()-originalPoint.getY())*Math.sin(90), originalPoint.getY() + (point.getX()-originalPoint.getX())*Math.sin(90) + (point.getY()-originalPoint.getY())*Math.cos(90));
      }
      model.unmarkMove(item);
    }
  }

  public boolean undo() {
    model.unmarkMove(item);
    return true;
  }

  public boolean  redo() {
    execute();
    return true;
  }

  public void execute() {
    if(movePoint != null) {
      move(movePoint);
    }
    else {
      model.getAllCopy();
    }
  }
}
