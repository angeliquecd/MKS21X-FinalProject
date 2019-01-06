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
public ArrayList<Integer> checkMatches(){
  checkRows();
}
public ArrayList<String> checkCols(){
  int currentcolor;
  int candycolor;
  int inarow;
  ArrayList<String> toreturn = new ArrayList<String>();
  for (int a=0;a<row;a++){
    currentcolor=0;//some value that isn't a color value
    candycolor=0;
    inarow=0;//resets for each new column
    for (int b=0;b<col;b++){
      candycolor=candyGrid[b][a].getColor();//sets color to one you're working with, moves down column
      if (candycolor!=currentcolor) {
        currentcolor=candycolor;//sets up for counting
        if (inarow>=3){//need to find a way to store indices
          toreturn.add(""+a+b);}//adds index of end of string as integer with two parts, row and col
        inarow=0;}//resets row count
      else{//counts candies of same shape
        inarow++;}
    }
  }
  return toreturn;}//returns array of ending indices

public ArrayList<String> checkRows(){//same as col but loops through rows
  int currentcolor;
  int candycolor;
  int inarow;
  ArrayList<String> toreturn = new ArrayList<String>();
  for (int a=0;a<row;a++){
    currentcolor=0;
    candycolor=0;
    inarow=0;
    for (int b=0;b<col;b++){
      candycolor=candyGrid[a][b].getColor();
      if (candycolor!=currentcolor) {
        currentcolor=candycolor;
        if (inarow>=3){
          toreturn.add(""+a+b);}
        inarow=0;}
      else{
        inarow++;}
    }
  }
  return toreturn;}
}
