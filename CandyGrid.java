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
    System.out.println("ROWS: " + cg.checkRows2());
    System.out.println(cg.toStringDebug());
    System.out.println("COLS: " + cg.checkCols2());
  //  cg.testPopRows();
    System.out.println(cg.toStringDebug());
    cg.pop();
    System.out.println(cg.toStringDebug());
  }

  public CandyGrid(){
      Random seedgen= new Random();  //will alter later so that user can input a seed instead of one being generated
      seed=seedgen.nextInt();
      Random randgen= new Random(10);
      row=10;
      col=10;//for now start at ten, will change later for levels
      candyGrid=new Candy[row][col];
      for (int a=0;a<row;a++){
        for (int b=0;b<col;b++){
          int color= randgen.nextInt(5);
          candyGrid[a][b]=new Candy(color, false,false);//ad part to create board without three in a row
        }
      }
      // candyGrid[0][row-1] = new Candy(1, false, false);
      // candyGrid[1][row-1] = new Candy(1, false, false);
      // candyGrid[2][row-1] = new Candy(1, false, false);
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


public void pop(){
boolean runs =true;
boolean run=true;
  while(runs||run){
    runs=testPopRows();
    fillEmptyGrid();
    run=testPopCols();
    fillEmptyGrid();
  }
  }

  public boolean testPopRows() {
    ArrayList<Integer> temp;
  //  ArrayList<Integer> index;
    int x,y,inarow;
    temp=checkRows2();
    if (!temp.isEmpty()){
        x=temp.get(0);
        y=temp.get(1);
        inarow=temp.get(2);
        for (int b = y; b < (y+inarow); b++){
          for (int a = x; a >= 0; a--) {
            if (a==0) candyGrid[a][b] = null;
            else {
              candyGrid[a][b] = candyGrid[a-1][b];
            }
            // System.out.println("X: " + a + " Y: " + b);
            // System.out.println(this.toStringDebug());
          }
        }
      return true;
      }
      return false;
    }

  public boolean testPopCols() {
    ArrayList<Integer> temp;
    int x,y,inarow;
    temp=checkCols2();
    if (!temp.isEmpty()){
      for (int i=0;i<temp.size();i++){
        inarow=temp.get(2);
        x=temp.get(0) + inarow-1;
        y=temp.get(1);
        for (int a = x; a >= 0; a--) {
          if (a-inarow < 0) candyGrid[a][y] = null;
          else {candyGrid[a][y] = candyGrid[a-inarow][y];}
        }
      }
      return true;
    }
    return false;
  }
public ArrayList<Integer>checkRows2(){//returns first case of matching that it finds
  int currentcolor;
  int candycolor;
  int inarow;
  ArrayList<Integer> toreturn = new ArrayList<Integer>();
  for (int a=0;a<row;a++){
    currentcolor=0;
    candycolor=0;
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

  public ArrayList<Integer> checkCols2(){
    int currentcolor;
    int candycolor;
    int inarow;
    ArrayList<Integer> toreturn = new ArrayList<Integer>();
    for (int b=0;b<col;b++){
      currentcolor=0;
      candycolor=0;
      inarow=1;
      for (int a=0;a<row;a++){
        candycolor=candyGrid[a][b].getColorInt();
        if (candycolor!=currentcolor) {
          currentcolor=candycolor;
          if (inarow>=3){
            toreturn.add(a-inarow); //adds index of row
            toreturn.add(b); //adds index of the last candy in the col of candies with the same color
            toreturn.add(inarow); //number of how many of the same candies are in a row
          }
          inarow=1;}
        else{
          inarow++;}
      }
    }
    return toreturn;
  }

    private void fillEmptyGrid() {
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
