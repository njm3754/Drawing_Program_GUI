import javax.swing.*;
import java.awt.event.*;
public class RotateButton  extends JButton implements ActionListener {
  protected JPanel drawingPanel;
  protected View view;
  private MouseHandler mouseHandler;
  private RotateCommand rotateCommand;
  private UndoManager undoManager;
  private boolean Clicked = false;

  public RotateButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
    super("Rotate");
    addActionListener(this);
    view = jFrame;
    drawingPanel = jPanel;
    this.undoManager = undoManager;
    mouseHandler = new MouseHandler();
  }

  public void actionPerformed(ActionEvent event) {
	rotateCommand = new RotateCommand();
	rotateCommand.loadCopies();
    view.refresh();
    drawingPanel.addMouseListener(mouseHandler);
    undoManager.beginCommand(rotateCommand);
  }

  private class MouseHandler extends MouseAdapter {
    public void mouseClicked(MouseEvent event) {
      if(!Clicked) {
    	rotateCommand.setPoint(View.mapPoint(event.getPoint()));
        Clicked = true;
      }
      else {
    	rotateCommand.move(View.mapPoint(event.getPoint()));
        drawingPanel.removeMouseListener(this);
        undoManager.endCommand(rotateCommand);
        Clicked = false;
      }
    }
  }
}
