import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
class View extends JFrame {
  private UIContext uiContext;
  private JPanel drawingPanel;
  private JPanel buttonPanel;
  private JPanel filePanel;
  private JButton lineButton;
  private JButton rotateButton;
  private JButton polygonButton;
  private JButton deleteButton;
  private JButton labelButton;
  private JButton selectButton;
  private JButton moveButton;
  private JButton saveButton;
  private JButton openButton;
  private JButton undoButton;
  private JButton redoButton;
  private static UndoManager undoManager;
    private String fileName;
  // other buttons to be added as needed;
  private static Model model;
  public UIContext getUI() {
    return uiContext;
  }
  private void setUI(UIContext uiContext) {
    this.uiContext = uiContext;
  }
  public static void setModel(Model model) {
    View.model = model;
  }
  public static void setUndoManager(UndoManager undoManager) {
    View.undoManager = undoManager;
  }
  private class DrawingPanel extends JPanel {
    private MouseListener currentMouseListener;
    private KeyListener currentKeyListener;
    private FocusListener currentFocusListener;
    private MouseMotionListener currentMouseMotionListener;
    public DrawingPanel() {
      setLayout(null);
    }
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      (NewSwingUI.getInstance()).setGraphics(g);
      g.setColor(Color.BLUE);
      Enumeration enumeration = model.getItems();
      while (enumeration.hasMoreElements()) {
        ((Item) enumeration.nextElement()).render();
      }
      g.setColor(Color.RED);
      enumeration = model.getSelectedItems();
      while (enumeration.hasMoreElements()) {
        ((Item) enumeration.nextElement()).render();
      }
      g.setColor(Color.GRAY);
      enumeration = model.getMoveItems();
      while (enumeration.hasMoreElements()) {
        ((Item) enumeration.nextElement()).render();
      }
      g.setColor(Color.BLACK);
      enumeration = model.getAllCopy();
      while (enumeration.hasMoreElements()) {
        Point point = (Point) enumeration.nextElement();
        g.drawRect((int)point.getX()-1, (int)point.getY()-1, 3, 3);
      }
    }

    public void addMouseListener(MouseListener newListener) {
      removeMouseListener(currentMouseListener);
      currentMouseListener =  newListener;
      super.addMouseListener(newListener);
    }
    public void addMouseMotionListener(MouseMotionListener newListener) {
      removeMouseMotionListener(currentMouseMotionListener);
      currentMouseMotionListener = newListener;
      super.addMouseMotionListener(newListener);
    }
    public void addKeyListener(KeyListener newListener) {
      removeKeyListener(currentKeyListener);
      currentKeyListener =  newListener;
      super.addKeyListener(newListener);
    }
    public void addFocusListener(FocusListener newListener) {
      removeFocusListener(currentFocusListener);
      currentFocusListener =  newListener;
      super.addFocusListener(newListener);
    }
  }
  public void clearCopy() {
    model.clearCopy();
    drawingPanel.repaint();
  }
  public void setFileName(String fileName) {
    this.fileName = fileName;
    setTitle("Drawing Program 1.1  " + fileName);
  }
  public String getFileName() {
    return fileName;
  }

  public View() {
    super("Drawing Program 1.1  Untitled");
    fileName = null;
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent event) {
        System.exit(0);
      }
    });
    model.setUI(NewSwingUI.getInstance());
    drawingPanel = new DrawingPanel();
    buttonPanel = new JPanel();
    Container contentpane = getContentPane();
    contentpane.add(buttonPanel, "North");
    contentpane.add(drawingPanel);
    lineButton    = new LineButton   (undoManager, this, drawingPanel);
    rotateButton = new RotateButton (undoManager, this, drawingPanel);
    polygonButton = new PolygonButton(undoManager, this, drawingPanel);
    labelButton   = new LabelButton  (undoManager, this, drawingPanel);
    selectButton  = new SelectButton (undoManager, this, drawingPanel);
    moveButton    = new MoveButton   (undoManager, this, drawingPanel);
    deleteButton  = new DeleteButton (undoManager);
    saveButton    = new SaveButton   (undoManager, this);
    openButton    = new OpenButton   (undoManager, this);
    undoButton    = new UndoButton   (undoManager);
    redoButton    = new RedoButton   (undoManager);
    buttonPanel.add(lineButton);
    buttonPanel.add(rotateButton);
    buttonPanel.add(polygonButton);
    buttonPanel.add(labelButton);
    buttonPanel.add(selectButton);
    buttonPanel.add(moveButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(openButton);
    buttonPanel.add(undoButton);
    buttonPanel.add(redoButton);
    this.setSize(800, 400);
  }
  public void refresh() {
    // code to access the Model update the contents of the drawing panel.
    drawingPanel.repaint();
  }
  public static Point mapPoint(Point point){
    // maps a point on the drawing panel to a point
    // on the figure being created. Perhaps this
    // should be in drawing panel
    return point;
  }
}
