public class Candy{
  private int colorInt;
  private boolean selected;
  private boolean toCrush;

  public Candy(int col, boolean sel, boolean crush) {
    colorInt = col;
    selected = sel;
    toCrush = crush;
  }

  public void select() {
    selected = !selected;
  }

  public boolean getSelect() {
    return selected;
  }

  public int getColorInt() {
    return colorInt;
  }

  //for testing purposes
  public String toString(){
    return ""+colorInt;
  }





}
