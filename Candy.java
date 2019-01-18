public class Candy{
  private int colorInt;
  private boolean selected;
  private boolean toCrush;

  public Candy(int col, boolean sel) {
    colorInt = col;
    selected = sel;
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
