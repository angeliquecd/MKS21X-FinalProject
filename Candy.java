public class Candy{
  private String color;
  private boolean selected;
  private boolean toCrush;

  public Candy(int col, boolean sel, boolean crush) {
    String[] arrCol = {"RED", "BLUE", "YELLOW", "GREEN", "ORANGE"};
    color = arrCol[col];
    selected = sel;
    toCrush = crush;
  }

  public Candy(String col, boolean sel, boolean crush) {
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

  public String getColor(){
    return color;
  }



}
