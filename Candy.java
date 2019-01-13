public class Candy{
  private String color;
  private int colorInt;
  private boolean selected;
  private boolean toCrush;

  public Candy(int col, boolean sel, boolean crush) {
    String[] arrCol = {"RED", "BLUE", "YELLOW", "GREEN", "ORANGE"};
  //  color = arrCol[col];
    colorInt = col;
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

<<<<<<< HEAD
public int getColor(){
  return color;
}
//for testing purposes
public String toString(){
  return ""+color;
}
=======
  public String getColor(){
    return color;
  }

  public int getColorInt() {
    return colorInt;
  }

>>>>>>> d53f5fe387427f956c59194e3540c265aa28047f


}
