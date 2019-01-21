//API : http://mabe02.github.io/lanterna/apidocs/2.1/
import com.googlecode.lanterna.terminal.Terminal.SGR;
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.LanternaException;
import com.googlecode.lanterna.input.CharacterPattern;
import com.googlecode.lanterna.input.InputDecoder;
import com.googlecode.lanterna.input.InputProvider;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.KeyMappingProfile;
import com.googlecode.lanterna.screen.Screen;
import java.io.*;
import java.util.*;
/*remaining bugs:
bottom row and first column still spaz
has stopped catching certain column matches that go into bottom row
doesn't always catch power-ups
generally spazzy
*/

public class CandyCrush{

  public static void putString(int r, int c,Terminal t, String s){
      t.applyBackgroundColor(Terminal.Color.DEFAULT);
      t.moveCursor(r,c);
      for(int i = 0; i < s.length();i++){
        t.putCharacter(s.charAt(i));}
  }

  public static void putString(int r, int c,Terminal t,
        String s, Terminal.Color forg, Terminal.Color back ){
    t.moveCursor(r,c);
    t.applyBackgroundColor(forg);
    t.applyForegroundColor(Terminal.Color.BLACK);
    for(int i = 0; i < s.length();i++){
      t.putCharacter(s.charAt(i));}
    t.applyBackgroundColor(Terminal.Color.DEFAULT);
    t.applyForegroundColor(Terminal.Color.DEFAULT);
  }

//Initial setup for main menu screen
  public static void setupMenu(Terminal terminal){
    terminal.setCursorVisible(false);
    putString(0,0,terminal,"WELCOME TO CANDY CRUSH!",Terminal.Color.GREEN,Terminal.Color.WHITE);
    putString(0,1,terminal,"To quit, press escape.");
    putString(0,2,terminal,"To view instructions, press \"I\"");
    putString(0,3, terminal, "Choose a difficulty to start: 1, 2, or 3");
    terminal.moveCursor(10,10);
  }

//Setup for instructions screens
  public static void setupInstructions(Terminal t){
    t.setCursorVisible(false);
    putString(0,0,t,"Press backspace to go back to the menu screen");
    putString(0,2,t,"The goal of Candy Crush is to crush candies by connecting 3 or\nmore candies of "+
    "the same color vertically or horizontally. By\ncrushing candies, you can gain points. Try to reach the "+
    "\nobjective number of points before you run out of moves. You \ncan swipe candies around by first selecting them. "+
    "Move using \nthe arrow keys to the candy you would like to select, then \npress enter to select it. "+
    "Once you have selected a candy, use \nthe arrow keys again to indicate which direction you would " +
    "\nlike to swipe it in. If you get more than 3 candies in a row, \nyou will create a super candy. These "+
    "super candies can allow \nyou to clear a whole row or column of candies!");
  }

//Setup after a level has been chosen
  public static void setupGame(Terminal terminal, CandyGrid test, int move, int obj){
    printpuzzle(test, 10, 10, terminal, move);
    putString(0,0,terminal,"WELCOME TO CANDY CRUSH!",Terminal.Color.GREEN,Terminal.Color.WHITE);
    putString(0,1,terminal,"To quit, press escape. To go back, press backspace");
    putString(0,3,terminal,"Objective: Get "+obj+" points");
    terminal.moveCursor(10,10);
  }

//Prints out puzzle with correctly colored candies, points objective, and number of moves, will be continually updated throughout game
  public static void printpuzzle(CandyGrid a, int x , int y, Terminal t, int move){
    putString(0,4,t,"Points: "+a.getPoints());
    putString(0,5,t,"You have " + move + " moves left ");
    int x1 = x;
    int c;
    for (int i =0;i<a.getRow();i++){
      for (int b=0;b<a.getCol();b++){
        t.moveCursor(x,y);
        t.applyBackgroundColor(Terminal.Color.DEFAULT);
        if (a.getGrid()[i][b]==null) t.putCharacter(' ');
        else {
          c=a.getGrid()[i][b].getColorInt(); //determines what color it should print each candy
          if (c==0) t.applyForegroundColor(Terminal.Color.RED);
          if (c==1) t.applyForegroundColor(Terminal.Color.BLUE);
          if (c==2) t.applyForegroundColor(Terminal.Color.YELLOW);
          if (c==3) t.applyForegroundColor(Terminal.Color.GREEN);
          if (c==4) t.applyForegroundColor(Terminal.Color.WHITE);
          if (c==5) t.applyForegroundColor(Terminal.Color.MAGENTA);
          if (a.getGrid()[i][b].getSpecial()) t.putCharacter('\u25CB');
          else {t.putCharacter('\u25CF');}
        }
        x++;
      }
      x=x1;
      y++;
    }
  }

//Highlights horizontally matching candies that are about to be crushed
  public static void highlightRow(CandyGrid test, Terminal t){
    ArrayList<Integer> rows;
    rows = test.checkRows();
    int x = 10;
    int y = 10;
    if(rows.size()>0) { //loops from the first index of row of matching candies to end
      for(int a = 0; a < rows.get(2); a++) {
        t.moveCursor(x+rows.get(1), y+rows.get(0));
        t.applyBackgroundColor(Terminal.Color.CYAN);
        t.putCharacter('\u25CF');
        x++;
      }
    }
  }

//Highlights vertically matching candies that are about to be crushed
  public static void highlightCol(CandyGrid test, Terminal t) {
    ArrayList<Integer> cols;
    cols = test.checkCols();
    int x = 10;
    int y = 10;
    if(cols.size()>0) {
      for(int a = 0; a < cols.get(2); a++) {
        t.moveCursor(x+cols.get(1), y+cols.get(0));
        t.applyBackgroundColor(Terminal.Color.CYAN);
        t.putCharacter('\u25CF');
        y++;
      }
    }
  }

//Similar to pop() in CandyGrid.java but with delays and printing puzzle updates in the terminal
  public static void popDelay(CandyGrid test, int x1, int y1, Terminal t, int move) throws InterruptedException{
    boolean runs =true;
    boolean run=true;
    while(runs||run){
      printpuzzle(test, x1, y1, t, move);
      highlightRow(test, t);
      Thread.sleep(500); //delays so that you can see what is happening in the grid
      runs=test.popRows(); //crushes rows
      if (runs){
        printpuzzle(test, x1, y1, t, move);
        Thread.sleep(500); //delay
        test.fillEmptyGrid();
        printpuzzle(test, x1, y1, t, move);}
      highlightCol(test, t);
      Thread.sleep(500); //delay
      run=test.popCols(); //crushes columns
      if (run){
        printpuzzle(test, x1, y1, t, move);
        test.fillEmptyGrid();
        printpuzzle(test, x1, y1, t, move);}
    }
  }

  public static void main(String[] args) throws InterruptedException {
    Terminal terminal = TerminalFacade.createTextTerminal();
    terminal.enterPrivateMode();

    TerminalSize size = terminal.getTerminalSize();
    terminal.setCursorVisible(false);

    Screen scr = new Screen(terminal, size);

    boolean running = true;
    String mode = "SETUP"; //determines the mode
    int moves = 10; //number of moves
    int objective=800; //points objective
    int x = 10; int y = 10;//starting point for where to print the grid

    CandyGrid tester= new CandyGrid(10);//creates new puzzle

    setupMenu(terminal);

    while(running){

			terminal.applySGR(Terminal.SGR.ENTER_UNDERLINE);
			terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
			terminal.applyForegroundColor(Terminal.Color.DEFAULT);
			terminal.applySGR(Terminal.SGR.RESET_ALL);
			terminal.applyBackgroundColor(Terminal.Color.DEFAULT); //color of highlight on words and squares where cursor moves
			terminal.applyForegroundColor(Terminal.Color.DEFAULT); //color of writing on screen

      Key key = terminal.readInput();
      if (key != null) {

        if (key.getKind() == Key.Kind.Escape) {
          terminal.exitPrivateMode();
          running = false;}

        if (mode.equals("SETUP")){ //setup screen
          terminal.setCursorVisible(false);
          if (key!=null){
            if (key.getCharacter()=='i'){
              terminal.clearScreen();
              mode="INSTRUCTIONS";
            }
            objective=800;
            if (key.getCharacter()=='1'){ //triggers setup for different levels
              terminal.clearScreen();
              tester= new CandyGrid(10);//creates new puzzle
              moves = 10;
              setupGame(terminal, tester, moves, objective);
              popDelay(tester, 10, 10, terminal, moves);
              mode="GAME";
            }
            if (key.getCharacter()=='2'){
              terminal.clearScreen();
              tester= new CandyGrid(12);//creates new puzzle
              moves = 8;
              setupGame(terminal, tester, moves, objective);
              popDelay(tester, 10, 10, terminal, moves);
              mode="GAME";
            }
            if (key.getCharacter()=='3'){
              terminal.clearScreen();
              tester=new CandyGrid(15);
              moves = 6;
              setupGame(terminal, tester, moves, objective);
              popDelay(tester, 10, 10, terminal, moves);
              mode="GAME";
            }
            terminal.moveCursor(10, 10);
            x=10;
            y=10;
          }
        }

        if(mode.equals("INSTRUCTIONS")){
          terminal.setCursorVisible(false);
          setupInstructions(terminal);
          if(key!=null){
            if(key.getKind()==Key.Kind.Backspace) {
              terminal.clearScreen();
              mode="SETUP";
              setupMenu(terminal);
            }
          }
        }

        if(mode.equals("GAME")){ //game play with unselected candy
          terminal.setCursorVisible(true);
          if (key!=null){
            if(key.getKind()==Key.Kind.Backspace) {
              mode="SETUP";
              terminal.clearScreen();
              setupMenu(terminal);
            }
            if (key.getKind()==Key.Kind.ArrowLeft){
              if (x>10) {
                x--;
                terminal.moveCursor(x,y);
                terminal.moveCursor(x,y);
              }
            }
            if (key.getKind()==Key.Kind.ArrowRight){
              if (x<9+tester.getCol()) {
                x++;
                terminal.moveCursor(x,y);
                terminal.moveCursor(x,y);
              }
            }
            if (key.getKind()==Key.Kind.ArrowUp){
              if (y>10) {
                y--;
                terminal.moveCursor(x,y);
                terminal.moveCursor(x,y);
              }
            }
            if (key.getKind()==Key.Kind.ArrowDown){
              if (y<9+tester.getRow()) {
                y++;
                terminal.moveCursor(x,y);
                terminal.moveCursor(x,y);
              }
            }
            if (key.getKind()==Key.Kind.Enter) mode="SELECTED";
          }
        }

        if (mode.equals("SELECTED")){ //game play once a candy has been selected
          terminal.setCursorVisible(true);
          if (key!=null){
            int beforex, beforey;
            if (key.getKind()==Key.Kind.ArrowLeft && x>10){ //candies are switched in the specified direction, then matching candies are crushed
              beforex=x;
              beforey=y;
              tester.swipeCandies(y-10,x-10,"HORIZONTAL",1);
              terminal.setCursorVisible(false);
              popDelay(tester, 10, 10, terminal, moves);
              moves--;
              terminal.setCursorVisible(true);
              terminal.moveCursor(beforex,beforey);
              mode="GAME";
            }
            if (key.getKind()==Key.Kind.ArrowRight && x<9+tester.getCol()){
              beforex=x;
              beforey=y;
              tester.swipeCandies(y-10,x-10,"HORIZONTAL",-1);
              terminal.setCursorVisible(false);
              popDelay(tester, 10, 10, terminal, moves);
              moves--;
              terminal.setCursorVisible(true);
              terminal.moveCursor(beforex,beforey);
              mode="GAME";
            }
            if (key.getKind()==Key.Kind.ArrowUp && y>10){
              beforex=x;
              beforey=y;
              tester.swipeCandies(y-10,x-10,"VERTICAL",1);
              terminal.setCursorVisible(false);
              popDelay(tester, 10, 10, terminal, moves);
              moves--;
              terminal.setCursorVisible(true);
              terminal.moveCursor(beforex,beforey);
              mode="GAME";
            }
            if (key.getKind()==Key.Kind.ArrowDown && y<9+tester.getRow()){
              beforex=x;
              beforey=y;
              tester.swipeCandies(y-10,x-10,"VERTICAL",-1);
              terminal.setCursorVisible(false);
              popDelay(tester, 10, 10, terminal, moves);
              moves--;
              terminal.setCursorVisible(true);
              terminal.moveCursor(beforex,beforey);
              mode="GAME";
            }
          }
          if (tester.getPoints()>=objective) { //once you reach 1000 points, you win the game
            mode="WIN";
            terminal.clearScreen();
          }
          if (moves<=0 && tester.getPoints()<objective) { //if you run out of moves, you lose
            terminal.clearScreen();
            mode="LOSE";
          }
        }

        if(mode.equals("LOSE")){ //lose screen
          terminal.setCursorVisible(false);
          terminal.clearScreen();
          putString(0,10,terminal, "You lost. Press backspace to return to the menu screen or escape to exit.",Terminal.Color.RED,Terminal.Color.WHITE);
          terminal.applySGR(Terminal.SGR.RESET_ALL);
          if (key!= null && key.getKind() == Key.Kind.Escape) { //to exit screen
            terminal.clearScreen();
            terminal.exitPrivateMode();
            running = false;
          }
          if(key.getKind()==Key.Kind.Backspace) { //to go back to menu screen
            mode="SETUP";
            terminal.clearScreen();
            setupMenu(terminal);
          }
        }


        if(mode.equals("WIN")) { //win screen
          terminal.setCursorVisible(false);
          terminal.clearScreen();
          terminal.applySGR(Terminal.SGR.ENTER_BOLD,Terminal.SGR.ENTER_BLINK);
          putString(0, 10, terminal, "CONGRATULATIONS, YOU WON! Press backspace to return to the menu screen or escape to exit.",Terminal.Color.GREEN,Terminal.Color.WHITE);
          terminal.applySGR(Terminal.SGR.RESET_ALL);
          if (key!= null && key.getKind() == Key.Kind.Escape) { //to exit screen
            terminal.clearScreen();
            terminal.exitPrivateMode();
            running = false;
          }
          if(key.getKind()==Key.Kind.Backspace) {
            mode="SETUP";
            terminal.clearScreen();
            setupMenu(terminal);
          }
        } //Win Screen mode
      } //if key!=null
    } //while loop
  } //main method
} //class
