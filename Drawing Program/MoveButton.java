import javax.swing.*;
import java.awt.event.*;
public class MoveButton  extends JButton implements ActionListener {
  protected JPanel drawingPanel;
  protected View view;
  private MouseHandler mouseHandler;
  private MoveCommand moveCommand;
  private UndoManager undoManager;
  private boolean Clicked = false;

  public MoveButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
    super("Move");
    addActionListener(this);
    view = jFrame;
    drawingPanel = jPanel;
    this.undoManager = undoManager;
    mouseHandler = new MouseHandler();
  }

  public void actionPerformed(ActionEvent event) {
    moveCommand = new MoveCommand();
    moveCommand.loadCopies();
    view.refresh();
    drawingPanel.addMouseListener(mouseHandler);
    undoManager.beginCommand(moveCommand);
  }

  private class MouseHandler extends MouseAdapter {
    public void mouseClicked(MouseEvent event) {
      if(!Clicked) {
        moveCommand.setPoint(View.mapPoint(event.getPoint()));
        Clicked = true;
      }
      else {
        moveCommand.move(View.mapPoint(event.getPoint()));
        drawingPanel.removeMouseListener(this);
        undoManager.endCommand(moveCommand);
        Clicked = false;
      }
    }
  }
}
