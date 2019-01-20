public class Candy{
  private int colorInt;
  private boolean selected;
  private boolean special;

  public Candy(int col, boolean sel) {
    colorInt = col;
    selected = sel;
    special = false;
  }

  public Candy(int col, boolean sel, boolean spec) {
    colorInt = col;
    selected = sel;
    special = spec;
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

  public boolean getSpecial() {
    return special;
  }

  //for testing purposes
  public String toString(){
    return ""+colorInt;
  }





}
