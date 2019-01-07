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
        output += " " + candyGrid[a][b].getColorInt() + " ";
      }
      output += "\n";
    }
    return output;
  }


public void pop(){
   ArrayList<ArrayList<Integer>> temp;
   ArrayList<Integer> index;
   int y;
   int x;
   temp=checkRows();
   if (!temp.isEmpty()){
     for (int i=0;i<temp.size();i++){
       index=temp.get(i);
       System.out.println(index);
       x=index.get(0);
       y=index.get(1);
       System.out.println("x: "+x+" y: "+y);
       int color=candyGrid[x][y].getColorInt();
       int nextcolor=candyGrid[x][y+1].getColorInt();
       while (nextcolor==color && y<candyGrid.length-1){
         System.out.println("in while loop");
         System.out.println("x: "+x+"y: "+y);
         candyGrid[x][y]=new Candy(9,false,false);
         y++;
         nextcolor=candyGrid[x][y+1].getColorInt();
       }
     }
   }
   temp=checkCols();
   if (!temp.isEmpty()){

   }
   }
   // }

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
            //temp.add(inarow); //number of how many of the same candies are in a row
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


}
