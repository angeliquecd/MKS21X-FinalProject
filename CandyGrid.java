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
    System.out.println(cg.toStringDebug());
    System.out.println("COLS: " + cg.checkCols());
    cg.testPop();
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
          candyGrid[a][b]=new Candy(color, false,false);
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
   ArrayList<ArrayList<Integer>> temp;
   ArrayList<Integer> index;
   int x,y,start,end,inarow,above, newcolor;
   temp=checkRows();
   if (!temp.isEmpty()){
     for (int i=0;i<temp.size();i++){
       index=temp.get(i);
       x=index.get(0);
       y=index.get(1);
       inarow=index.get(2);
       start=y;
       int color=candyGrid[x][y].getColorInt();
       int nextcolor=candyGrid[x][y+1].getColorInt();
       while (nextcolor==color && y<candyGrid.length){
         candyGrid[x][y]=new Candy(9,false,false);
         y++;
         nextcolor=candyGrid[x][y].getColorInt();
       }
       end=y;
       for (int a=start;a<end;a++){
         for (int b=x;b>0;b--){
           System.out.println("in for loop");
           above=candyGrid[b-1][a].getColorInt();
           candyGrid[b][a]=new Candy(above,false,false);
         }
         newcolor=randgen.nextInt(4);
         candyGrid[0][a]=new Candy(newcolor,false,false);
       }
       temp=checkRows();
     }
   }
   temp=checkCols();
   if (!temp.isEmpty()){
     for (int i=0;i<temp.size();i++){
       index=temp.get(i);
       x=index.get(0);
       y=index.get(1);
       int color=candyGrid[x][y].getColorInt();
       int nextcolor=candyGrid[x+1][y].getColorInt();
       while (nextcolor==color && x<candyGrid.length){
         candyGrid[x][y]=new Candy(8,false,false);
         x++;
         nextcolor=candyGrid[x][y].getColorInt();
       }
     }
   }
  }

  public void testPopRows() {
    ArrayList<ArrayList<Integer>> temp;
    ArrayList<Integer> index;
    int x,y,inarow;
    temp=checkRows();
    if (!temp.isEmpty()){
      for (int i=0;i<temp.size();i++){
        index=temp.get(i);
        x=index.get(0);
        y=index.get(1);
        inarow=index.get(2);
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
      }
    }
  }

  public void testPopCols() {

  }

  public ArrayList<ArrayList<Integer>> checkRows(){
    int currentcolor;
    int candycolor;
    int inarow;
    ArrayList<ArrayList<Integer>> toreturn = new ArrayList<ArrayList<Integer>>();
    for (int a=0;a<row;a++){
      currentcolor=0;
      candycolor=0;
      inarow=1;
      for (int b=0;b<col;b++){
        candycolor=candyGrid[a][b].getColorInt();
        //System.out.println(candyGrid[a][b].getColorInt());
        if (candycolor!=currentcolor) {
          currentcolor=candycolor;
          //System.out.println(inarow);
          if (inarow>=3){
            ArrayList<Integer> temp = new ArrayList<Integer>();
            temp.add(a); //adds index of row
            temp.add(b-inarow); //adds index of the last candy in the row of candies with the same color
            temp.add(inarow); //number of how many of the same candies are in a row
            toreturn.add(temp);
          }
          inarow=1;}
        else{
          inarow++;}
      }
    }
    return toreturn;
  }

  public ArrayList<ArrayList<Integer>> checkCols(){
    int currentcolor;
    int candycolor;
    int inarow;
    ArrayList<ArrayList<Integer>> toreturn = new ArrayList<ArrayList<Integer>>();
    for (int a=0;a<row;a++){
      currentcolor=0;
      candycolor=0;
      inarow=1;
      for (int b=0;b<col;b++){
        candycolor=candyGrid[b][a].getColorInt();
        if (candycolor!=currentcolor) {
          currentcolor=candycolor;
          if (inarow>=3){
            ArrayList<Integer> temp = new ArrayList<Integer>();
            temp.add(b-inarow); //adds index of row
            temp.add(a); //adds index of the last candy in the col of candies with the same color
            //temp.add(inarow); //number of how many of the same candies are in a row
            toreturn.add(temp);
          }
          inarow=1;}
        else{
          inarow++;}
      }
    }
    return toreturn;}

    private void fillEmptyGrid() {}

}
