import java.awt.*;
import java.util.*;
public class MoveCommand extends Command {
  private Item item;
  private Double shiftX;
  private Double shiftY;
  private Point originalPoint;
  private Point movePoint;

  public MoveCommand() {
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
        point.setLocation(point.getX()+shiftX, point.getY()+shiftY);
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
