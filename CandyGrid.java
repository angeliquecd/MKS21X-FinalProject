import java.util.*;
import java.io.*;
public class CandyGrid{
  private Candy[][] candyGrid;
  private Random randgen;
  private int row;
  private int col;
  private int points;

  public static void main(String[] args) { //MAIN IS JUST FOR TESTING PURPOSES
    CandyGrid cg = new CandyGrid(5);
    System.out.println(cg.toStringDebug());
    System.out.println("ROWS: " + cg.checkRows());
    System.out.println("COLS: " + cg.checkCols());
    System.out.println(cg.getPoints());

    cg.swipeCandies(1, 2, "VERTICAL", 1);
    System.out.println(cg.toStringDebug());

    cg.pop();
    System.out.println(cg.getPoints());

    System.out.println(cg.toStringDebug());
    cg.swipeCandies(2, 8, "HORIZONTAL", 1);
    System.out.println(cg.toStringDebug());
    cg.pop();
    System.out.println(cg.getPoints());

    System.out.println(cg.toStringDebug());
  }

//Constructor - creates a new 2D grid of randomly colored candies//
  public CandyGrid(int z){
      randgen=new Random(10); //will change later to be a random seed
      row=z; col=z; //decides the size of the grid
      candyGrid=new Candy[row][col];
      int colorbefore=100;
      int colorabove=100;
      int inarow=1;
      for (int a=0;a<row;a++){
        colorbefore=100;
        for (int b=0;b<col;b++){
          int color= randgen.nextInt(6); //the following code is to keep the puzzle from having three in a row to begin with
          if (a>0)colorabove=candyGrid[a-1][b].getColorInt();
          if (color==colorbefore||colorabove==color){//checks if above or below are the same
            inarow++;
          if (inarow>=2){//keeps it from reaching three in a row
            color=(color+randgen.nextInt(4)+1)%6;// gives random color that isn't the previous one
            inarow=0;}
        }
        else inarow=1;
        candyGrid[a][b]=new Candy(color,false);
        colorbefore=color;
      }
    }
  }


//–––– Standard get methods ––––//
  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public Candy[][] getGrid() {
    return candyGrid;
  }

  public int getPoints() {
    return points;
  }
//––––––––––––––––––––––––––––//



//swipeCandies switches a selected candy with the candy next to it in a given direction
  public void swipeCandies(int a, int b, String direction, int dir) { //direction says if swiping is vertical or horizontal, dir says left/right or up/down
    if (direction.equals("VERTICAL")) {
      Candy temp = candyGrid[a][b];
      candyGrid[a][b] = candyGrid[a-dir][b]; //if dir = 1, temp will switch with candy above it, if dir = -1, temp will switch with candy below it
      candyGrid[a-dir][b] = temp;
    }
    if (direction.equals("HORIZONTAL")) {
      Candy temp = candyGrid[a][b];
      candyGrid[a][b] = candyGrid[a][b-dir];//if dir = 1, temp will switch with candy to its left
      candyGrid[a][b-dir] = temp;
    }
  }


//pop() combines other methods into one —> while there are 3 or more of the same candy in a row, crush and replace them
  public void pop(){
    boolean runs =true;
    boolean run=true;
    while(runs||run){ //keeps crushing until there are no more matching candies
      runs=popRows();
      fillEmptyGrid();
      run=popCols();
      fillEmptyGrid();
    }
  }


//Removes matching horizontal candies that have been found by checkRows and shifts down the candies above
  public boolean popRows() {
    ArrayList<Integer> temp;
    int x,y,inarow;
    temp=checkRows();
    if (!temp.isEmpty()){
        x=temp.get(0);
        y=temp.get(1);
        inarow=temp.get(2);
        if (inarow>3&&inarow<6) {
          int col = candyGrid[x][y].getColorInt();
          candyGrid[x][y] = new Candy(col, false, true);
          y++;}
        for (int b = y; b < (y+inarow-1); b++){
          for (int a = x; a >= 0; a--) {
            if (a==0) candyGrid[a][b] = null; //leaves empty spaces after shifting down to fill later
            else {candyGrid[a][b] = candyGrid[a-1][b];}
          }
        }
        points+=inarow*20;
        return true;
      }
      return false; //returns false if there are no more candies to remove
    }
//public void deleterow(){
  //for ()
//}

//Removes matching vertical candies that have been found by checkCols and shifts down the candies above
  public boolean popCols() {
    ArrayList<Integer> temp;
    int x,y,inarow;
    temp=checkCols();
    if (!temp.isEmpty()){
        inarow=temp.get(2);
        x=temp.get(0) + inarow-1;
        y=temp.get(1);
        if (inarow==row){
          for (int a =row-1;a>=0;a--){
            candyGrid[a][y]=null;
          }
        }
        if (inarow>3&&inarow<6) { //doesn't fully work yet
          int color = candyGrid[x][y].getColorInt();
          candyGrid[x][y] = new Candy(color, false, true);
          if (x!=9)x--;
        }
        for (int a = x; a >= 0; a--) {
          if (a-inarow < 0) candyGrid[a][y] = null;
          else {candyGrid[a][y] = candyGrid[a-inarow][y];
          }
        }
      points+=inarow*20;
      return true;
    }
    return false;//returns false if there are no more candies to remove
  }

  //checkRows() returns first case of matching (3 or more in a row) horizontal candies that it finds in the grid
public ArrayList<Integer>checkRows(){
  int currentcolor, candycolor, inarow;
  boolean special;
  ArrayList<Integer> toreturn = new ArrayList<Integer>();
  for (int a=0;a<row;a++){
    currentcolor=-1;
    candycolor=-1;
    inarow=1;
    special = false;
    for (int b=0;b<col;b++){
      candycolor=candyGrid[a][b].getColorInt();
      if (!special) special = candyGrid[a][b].getSpecial();
      if (candycolor!=currentcolor) {
        currentcolor=candycolor;
        if (inarow>=3){
          if (special) {
            toreturn.add(a);
            toreturn.add(0);
            toreturn.add(col);}
          else{
            toreturn.add(a); //adds index of row
            toreturn.add(b-inarow); //adds index of the last candy in the row of candies with the same color
            toreturn.add(inarow); //number of how many of the same candies are in a row
            return toreturn;}
        }
        inarow=1;
        special=false;
      }
      else{
        inarow++;
        if (b==col-1&&inarow>=3){ //a special case where there is three in a row but in the last column, so the loop terminates before indices are added
          if (special){
            toreturn.add(a);
            toreturn.add(0);
            toreturn.add(row);}
          else{
          toreturn.add(a);
          toreturn.add(b-inarow+1);
          toreturn.add(inarow);}}}}}
  return toreturn;
}


//checkCols() returns first case of matching (3 or more in a row) vertical candies that it finds in the grid
  public ArrayList<Integer> checkCols(){
    int currentcolor, candycolor, inarow;
    boolean special;
    ArrayList<Integer> toreturn = new ArrayList<Integer>();
    for (int b=0;b<col;b++){
      currentcolor=-1;
      candycolor=-1;
      inarow=1;
      special=false;
      for (int a=0;a<row;a++){
        candycolor=candyGrid[a][b].getColorInt();
        if (!special) special = candyGrid[a][b].getSpecial();
        if (candycolor!=currentcolor) {
          currentcolor=candycolor;
          if (inarow>=3){
            if (special){
              toreturn.add(0);
              toreturn.add(b);
              toreturn.add(row);
            }
            else{
              toreturn.add(a-inarow); //adds index of row
              toreturn.add(b); //adds index of the last candy in the col of candies with the same color
              toreturn.add(inarow); //number of how many of the same candies are in a row
              return toreturn;}
          }
          inarow=1;
          special=false;
        }
        else{
          inarow++;
          if (a==row-1&&inarow>=3){ //a special case where there is three in a row but in the last column, so the loop terminates before indices are added
            if (special){
              toreturn.add(0);
              toreturn.add(b);
              toreturn.add(row);
            }
            toreturn.add(a-inarow);
            toreturn.add(b);
            toreturn.add(inarow);}}}}
    return toreturn;
  }

//Fills empty spots in candyGrid after candies have been crushed
  public void fillEmptyGrid() {
    int newcolor;
    for (int x = 0; x < row; x++) {
      for (int y = 0; y < col; y++) {
        newcolor = randgen.nextInt(6);//random color 0-5
        if (candyGrid[x][y]==null) {
          candyGrid[x][y] = new Candy(newcolor, false);}
      }
    }
  }


//-------Following methods are for testing only---------//

//Prints a grid containing all the colorInts of the candies, for debugging/testing purposes
  public String toStringDebug() {
    String output = "";
    for (int a=0; a<row; a++){
      for (int b=0; b<col; b++){
        if (candyGrid[a][b]==null) output += "   ";
        else{output += " " + candyGrid[a][b].getColorInt() + " ";}
      }
      output += "\n";
    }
    return output;
  }

//returns true if two CandyGrids are equal, false if not
  public boolean equals(CandyGrid a){
    if (a.getRow()!=row) return false;
    if (a.getCol()!=col) return false;
    for (int c=0; c<row;c++){
      for (int b=0;b<col;b++){
        if (candyGrid[c][b].getColorInt()!=a.getGrid()[c][b].getColorInt()) return false;
      }
    }
    return true;
  }


}
