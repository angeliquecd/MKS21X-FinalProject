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
  }

  public CandyGrid(){
      Random seedgen= new Random();  //will alter later so that user can input a seed instead of one being generated
      seed=seedgen.nextInt();
      Random randgen= new Random(seed);
      row=10;
      col=10;//for now start at ten, will change later for levels
      candyGrid=new Candy[row][col];
      for (int a=0;a<row;a++){
        for (int b=0;b<col;b++){
          int color= randgen.nextInt(5);
          candyGrid[a][b]=new Candy(color, false,false);
        }
      }
  }

  public String toStringDebug() {
    String output = "";
    for (int a=0; a<row; a++){
      for (int b=0; b<col; b++){
        output += " O ";
      }
      output += "\n";
    }
    return output;
  }


   public ArrayList<Integer> checkMatches(){
     checkRows();
   }

  public ArrayList<Integer> checkRows(){
    int currentcolor;
    int candycolor;
    int inarow;
    ArrayList<Integer> toreturn = new ArrayList<Integer>();
    for (int a=0;a<row;a++){
      currentcolor=0;
      candycolor=0;
      inarow=0;
      for (int b=0;b<col;b++){
        candycolor=candyGrid[a][b].getColorInt();
        if (candycolor!=currentcolor) {
          currentcolor=candycolor;
          if (inarow>=3){
            toreturn.add(b);}
          inarow=0;}
        else{
          inarow++;}
      }
    }
    return toreturn;
  }

  public ArrayList<Integer> checkCols(){
    int currentcolor;
    int candycolor;
    int inarow;
    ArrayList<Integer> toreturn = new ArrayList<Integer>();
    for (int a=0;a<row;a++){
      currentcolor=0;
      candycolor=0;
      inarow=0;
      for (int b=0;b<col;b++){
        candycolor=candyGrid[b][a].getColorInt();
        if (candycolor!=currentcolor) {
          currentcolor=candycolor;
          if (inarow>=3){
            toreturn.add(b);}
          inarow=0;}
        else{
          inarow++;}
      }
    }
    return toreturn;}


}
