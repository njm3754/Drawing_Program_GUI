public interface UIContext {
  public abstract void draw(Line line);
  public abstract void draw(Polygon polygon);
  public abstract void draw(Label label);
  public abstract void draw(Item item);
}
