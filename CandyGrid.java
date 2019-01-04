import java.util.*;
import java.io.*;
public class CandyGrid{
  private Candy[][] candyGrid;
  private int seed ;
  private Random randgen;
  private int row;
  private int col;
  public CandyGrid(){
      Random seedgen= new Random();  //will alter later so that user can input a seed instead of one being generated
      seed=seedgen.nextInt();
      Random randgen= new Random(seed);
      row=10;
      col=10;//for now start at ten, will change later for levels
      candyGrid=new Candy[row][col];
      for (int a=0;a<row;a++){
        for (int b=0;b<col;b++){
          int color= randgen.nextInt();
          candyGrid[a][b]=new Candy(color, false,false);
        }
      }
  }

}
