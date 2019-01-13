import java.util.*;
import java.io.*;
public class CandyGrid{
  private Candy[][] candyGrid;
  private int seed ;
  private Random randgen;
  private int row;
  private int col;

  public static void main(String[] args) { //MAIN IS JUST FOR TESTING PURPOSES
    CandyGrid cg = new CandyGrid();
    System.out.println(cg.toStringDebug());
    System.out.println("ROWS: " + cg.checkRows());
    //System.out.println(cg.toStringDebug());
    System.out.println("COLS: " + cg.checkCols());
    //cg.testPopRows();
    //System.out.println(cg.toStringDebug());
    cg.pop();
    System.out.println(cg.toStringDebug());
    System.out.println(cg.getSeed());
  }

  public CandyGrid(){
      Random seedgen=new Random();  //will alter later so that user can input a seed instead of one being generated
      seed=seedgen.nextInt();
      randgen=new Random(10); //will change later to be a random seed
      row=10;
      col=10;//for now start at ten, will change later for levels
      candyGrid=new Candy[row][col];
      int colorbefore=100;
      int colorabove=100;
      int inarow=1;
      for (int a=0;a<row;a++){
        colorbefore=100;
        for (int b=0;b<col;b++){
          int color= randgen.nextInt(6);//following is to keep the puzzle from having three in a row to begin with
          if (a>0)colorabove=candyGrid[a-1][b].getColorInt();
          if (color==colorbefore||colorabove==color){//checks if above or below are the same
            inarow++;
          if (inarow>=2){//keeps it from reaching three in a row
            color=(color+randgen.nextInt(4)+1)%6;// gives random number that isn't the previous one
            inarow=0;
          }}
          else inarow=1;
          candyGrid[a][b]=new Candy(color,false,false);
          colorbefore=color;
        }
      }
  }


//–––– Standard get methods ––––//
  public int getSeed() {
    return seed;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public Candy[][] getGrid() {
    return candyGrid;
  }
//––––––––––––––––––––––––––––//


//prints a grid containing all the colorInts of the candies, for debugging/testing purposes
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
    while(runs||run){
      //System.out.println("\nROWS: " + checkRows());
      runs=popRows();
      //System.out.println("TEST after rows\n" + this.toStringDebug());
      fillEmptyGrid();
      //System.out.println("AFTER FILL: \n" + toStringDebug());
      //System.out.println("\nCOL: " + checkCols());
      run=popCols();
      //System.out.println("TEST after cols\n" + this.toStringDebug());
      fillEmptyGrid();
      //System.out.println("AFTER FILL: \n" + toStringDebug());
    }
  }


//popRows removes candies of 3 or more in a row and shifts all candies above them down
  public boolean popRows() {
    ArrayList<Integer> temp;
    int x,y,inarow;
    temp=checkRows();
    if (!temp.isEmpty()){
        x=temp.get(0);
        y=temp.get(1);
        inarow=temp.get(2);
        for (int b = y; b < (y+inarow); b++){
          for (int a = x; a >= 0; a--) {
            if (a==0) candyGrid[a][b] = null; //leaves empty spaces to fill later
            else {candyGrid[a][b] = candyGrid[a-1][b];}
          }
        }
        return true;
      }
      return false; //returns false if there are no more candies to remove
    }


//popCols() removes columns of the same candy and shifts all candies down
  public boolean popCols() {
    ArrayList<Integer> temp;
    int x,y,inarow;
    temp=checkCols();
    if (!temp.isEmpty()){
        inarow=temp.get(2);
        x=temp.get(0) + inarow-1;
        y=temp.get(1);
        for (int a = x; a >= 0; a--) {
          if (a-inarow < 0) candyGrid[a][y] = null;
          else {candyGrid[a][y] = candyGrid[a-inarow][y];}
        }
      return true;
    }
    return false;//returns false if there are no more candies to remove
  }

public ArrayList<Integer>checkRows(){//returns first case of matching that it finds
  int currentcolor;
  int candycolor;
  int inarow;
  ArrayList<String> toreturn = new ArrayList<String>();
  for (int a=0;a<row;a++){
    currentcolor=-1;
    candycolor=-1;
    inarow=1;
    for (int b=0;b<col;b++){
      candycolor=candyGrid[a][b].getColorInt();
      //System.out.println(candyGrid[a][b].getColorInt());
      if (candycolor!=currentcolor) {
        currentcolor=candycolor;
        if (inarow>=3){
          toreturn.add(a); //adds index of row
          toreturn.add(b-inarow); //adds index of the last candy in the row of candies with the same color
          toreturn.add(inarow); //number of how many of the same candies are in a row
          return toreturn;
        }
        inarow=1;}
      else{
        inarow++;
        if (b==col-1&&inarow>=3){ //a special case where there is three in a row but in the last column, so the loop terminates before indices are added
          toreturn.add(a); //adds index of row
          toreturn.add(b-inarow+1); //adds index of the last candy in the row of candies with the same color
          toreturn.add(inarow); //number of how many of the same candies are in a row
          return toreturn;
}}}}}

//checkRows() returns first case of matching horizontal candies that it finds in the grid
  public ArrayList<Integer>checkRows(){
    int currentcolor;
    int candycolor;
    int inarow;
    ArrayList<Integer> toreturn = new ArrayList<Integer>();
    for (int a=0;a<row;a++){
      currentcolor=-1;
      candycolor=-1;
      inarow=1;
      for (int b=0;b<col;b++){
        candycolor=candyGrid[a][b].getColorInt();
        //System.out.println(candyGrid[a][b].getColorInt());
        if (candycolor!=currentcolor) {
          currentcolor=candycolor;
          if (inarow>=3){
            toreturn.add(a); //adds index of row
            toreturn.add(b-inarow); //adds index of the last candy in the row of candies with the same color
            toreturn.add(inarow); //number of how many of the same candies are in a row
            return toreturn;
          }
          inarow=1;}
        else{
          inarow++;
          if (b==col-1&&inarow>=3){ //a special case where there is three in a row but in the last column, so the loop terminates before indices are added
            toreturn.add(a); //adds index of row
            toreturn.add(b-inarow+1); //adds index of the last candy in the row of candies with the same color
            toreturn.add(inarow); //number of how many of the same candies are in a row
            return toreturn;
          }
        }
      }
    }
    return toreturn;
  }


  //checkCols() returns first case of matching vertical candies that it finds in the grid
  public ArrayList<Integer> checkCols(){
    int currentcolor;
    int candycolor;
    int inarow;
    ArrayList<Integer> toreturn = new ArrayList<Integer>();
    for (int b=0;b<col;b++){
      currentcolor=-1;
      candycolor=-1;
      inarow=1;
      for (int a=0;a<row;a++){
        candycolor=candyGrid[a][b].getColorInt();
        if (candycolor!=currentcolor) {
          currentcolor=candycolor;
          if (inarow>=3){
            toreturn.add(a-inarow); //adds index of row
            toreturn.add(b); //adds index of the last candy in the col of candies with the same color
            toreturn.add(inarow); //number of how many of the same candies are in a row
            return toreturn;
          }
          inarow=1;}
        else{
          inarow++;}
      }
    }
    return toreturn;
  }

//helper method to fill empty spots in candyGrid
  private void fillEmptyGrid() {
    int newcolor;
    for (int x = 0; x < row; x++) {
      for (int y = 0; y < col; y++) {
        newcolor = randgen.nextInt(6);
        if (candyGrid[x][y]==null) {
          candyGrid[x][y] = new Candy(newcolor, false, false);
        }
      }
    }
  }


}
