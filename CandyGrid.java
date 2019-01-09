import java.util.*;
import java.io.*;
public class CandyGrid{
  private Candy[][] candyGrid;
  private int seed ;
  private Random randgen;
  private int row;
  private int col;

  public static void main(String[] args) {
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

  public int getSeed() {
    return seed;
  }

  public CandyGrid(){
      Random seedgen= new Random();  //will alter later so that user can input a seed instead of one being generated
      seed=seedgen.nextInt();
      randgen= new Random(10);
      row=10;
      col=10;//for now start at ten, will change later for levels
      candyGrid=new Candy[row][col];
      int colorbefore=100;
      int colorabove=100;
      int inarow=1;
      for (int a=0;a<row;a++){
        colorbefore=100;
        for (int b=0;b<col;b++){
          int color= randgen.nextInt(5);
          if (a>0)colorabove=candyGrid[a-1][b].getColorInt();
          if (color==colorbefore||colorabove==color){
            inarow++;
          if (inarow>=2){
            color=(color+randgen.nextInt(3)+1)%5;//should actually just give random number that isn't the previous one
            inarow=0;
          }}
          else inarow=1;
          candyGrid[a][b]=new Candy(color, false,false);//ad part to create board without three in a row
          colorbefore=color;
        }
      }
  }

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


  public void pop(){ //combines other methods into one —> while there are 3 or more of the same candy in a row, crush and replace them
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

  public boolean popRows() { //removes rows of the same candy and shifts all candies down
    ArrayList<Integer> temp;
    int x,y,inarow;
    temp=checkRows();
    if (!temp.isEmpty()){
        x=temp.get(0);
        y=temp.get(1);
        inarow=temp.get(2);
        for (int b = y; b < (y+inarow); b++){
          for (int a = x; a >= 0; a--) {
            if (a==0) candyGrid[a][b] = null;
            else {candyGrid[a][b] = candyGrid[a-1][b];}
          }
        }
        return true;
      }
      return false; //returns false if there are no more candies to remove
    }

  public boolean popCols() {//removes columns of the same candy and shifts all candies down
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

    private void fillEmptyGrid() { //helper method to fill empty spots in candyGrid
      int newcolor;
      for (int x = 0; x < row; x++) {
        for (int y = 0; y < col; y++) {
          newcolor = randgen.nextInt(4);
          if (candyGrid[x][y]==null) {
            candyGrid[x][y] = new Candy(newcolor, false, false);
          }
        }
      }
    }

}
