public class Candy{
  private int color;
  private boolean selected;
  private boolean toCrush;

  public Candy(int col, boolean sel, boolean crush) {
    color = col;
    selected = sel;
    toCrush = crush;
  }

  public void select() {
    selected = true;
  }

  public void crush() {
    toCrush = true;
  }

  public boolean getCrush() {
    return toCrush;
  }

  public boolean getSelect() {
    return selected;
  }





}
