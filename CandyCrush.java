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


public class CandyCrush {

  public static void putString(int r, int c,Terminal t, String s){
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

  public static void setup1(Terminal terminal){
    terminal.setCursorVisible(false);
    putString(0,0,terminal,"WELCOME TO CANDY CRUSH!",Terminal.Color.GREEN,Terminal.Color.WHITE);
    putString(0,1,terminal,"To quit, press escape.");
    putString(0,2, terminal, "Choose a difficulty to start: 1,2 or 3");
    terminal.moveCursor(10,10);
  }

  public static void setup2(Terminal terminal, CandyGrid test, int move, int obj){
    printpuzzle(test, 10, 10, terminal, move);
    putString(0,0,terminal,"WELCOME TO CANDY CRUSH!",Terminal.Color.GREEN,Terminal.Color.WHITE);
    putString(0,1,terminal,"To quit, press escape. To go back, press backspace");
    putString(0,3,terminal,"Objective: Get "+obj+" points");
    terminal.moveCursor(10,10);
  }

  public static void printpuzzle(CandyGrid a, int x , int y, Terminal t, int move){
    putString(0,4,t,"Points: "+a.getPoints());
    putString(0,5,t,"You have " + move + " moves left ");
    int x1 = x;
    int c;
    for (int i =0;i<a.getRow();i++){
      for (int b=0;b<a.getCol();b++){
        t.moveCursor(x,y);
        if (a.getGrid()[i][b]==null) t.putCharacter(' ');
        else {
          c=a.getGrid()[i][b].getColorInt(); //determines what color it should print each candy
          if (c==0) t.applyForegroundColor(Terminal.Color.RED);
          if (c==1) t.applyForegroundColor(Terminal.Color.BLUE);
          if (c==2) t.applyForegroundColor(Terminal.Color.YELLOW);
          if (c==3) t.applyForegroundColor(Terminal.Color.GREEN);
          if (c==4) t.applyForegroundColor(Terminal.Color.WHITE);
          if (c==5) t.applyForegroundColor(Terminal.Color.MAGENTA);
          t.putCharacter('O');}
        x++;
      }
      x=x1;
      y++;
    }
  }

  public static void highlight(CandyGrid test, Terminal t){
    ArrayList<Integer> rows, cols;
    rows = test.checkRows();
    cols = test.checkCols();
    int x = 10;
    int y = 10;
    if(rows.size()>0) {
      for(int a = 0; a < rows.get(2); a++) {
        t.moveCursor(x+rows.get(1), y+rows.get(0));
        t.applyBackgroundColor(Terminal.Color.CYAN);
        x++;
      }
    }
    x=10;
    y=10;
    if(cols.size()>0) {
      for(int b = 0; b < cols.get(2); b++) {
        t.moveCursor(x+rows.get(1), y+rows.get(0));
        t.applyBackgroundColor(Terminal.Color.CYAN);
        y++;
      }
    }
  }

  public static void pop2(CandyGrid test, int x1, int y1, Terminal t, int move) throws InterruptedException{
    boolean runs =true;
    boolean run=true;
    while(runs||run){
      printpuzzle(test, x1, y1, t, move);
      Thread.sleep(500); //delay
      //highlight(test, t);
      runs=test.popRows(); //crushes rows
      printpuzzle(test, x1, y1, t, move);
      Thread.sleep(500); //delay
      test.fillEmptyGrid();
      printpuzzle(test, x1, y1, t, move);
      Thread.sleep(500); //delay
      run=test.popCols(); //crushes columns
      printpuzzle(test, x1, y1, t, move);
      Thread.sleep(500); //delay
      test.fillEmptyGrid();
      printpuzzle(test, x1, y1, t, move);
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

    //long lastTime =  System.currentTimeMillis();
    //long currentTime = lastTime;
    //long timer = 0;
    //Random numgen = new Random();

    int moves = 10; //number of moves
    int objective=1000; //points objective
    int x = 10; int y = 10;//starting point for where to print the grid

    CandyGrid tester= new CandyGrid(10);//creates new puzzle

    setup1(terminal);

    while(running){

			terminal.applySGR(Terminal.SGR.ENTER_UNDERLINE);
			terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
			terminal.applyForegroundColor(Terminal.Color.DEFAULT);
			terminal.applySGR(Terminal.SGR.RESET_ALL);
			terminal.applyBackgroundColor(Terminal.Color.DEFAULT); //color of highlight on words and squares where cursor moves
			terminal.applyForegroundColor(Terminal.Color.DEFAULT); //color of writing on screen

      Key key = terminal.readInput();
      if (key != null) {
        //only for the game mode.
        if (key.getKind() == Key.Kind.Escape) {
          terminal.exitPrivateMode();
          running = false;
        }

        if (mode.equals("SETUP")){ //setup screen
          terminal.setCursorVisible(false);
          if (key!=null){
            if (key.getCharacter()=='1'){
              terminal.clearScreen();
              tester= new CandyGrid(10);//creates new puzzle
              moves = 10;
              objective=1000;
              setup2(terminal, tester, moves, objective);
              mode="GAME";
            }
            if (key.getCharacter()=='2'){
              terminal.clearScreen();
              tester= new CandyGrid(15);//creates new puzzle
              moves = 8;
              objective=800;
              setup2(terminal, tester, moves, objective);
              mode="GAME";
            }
            if (key.getCharacter()=='3'){
              terminal.clearScreen();
              tester=new CandyGrid(20);
              moves = 6;
              objective=800;
              setup2(terminal, tester, moves, objective);
              mode="GAME";
            }
          }
        }

        if(mode.equals("GAME")){//game play with unselected candy
          terminal.setCursorVisible(true);
          if (key!=null){
            if(key.getKind()==Key.Kind.Backspace) {
              mode="SETUP";
              terminal.clearScreen();
              setup1(terminal);
            }
            if (key.getKind()==Key.Kind.ArrowLeft){
              if (x>10) {
                x--;
                terminal.moveCursor(x,y);
                putString(0,6,terminal, "x:"+x+"y: "+y);
                terminal.moveCursor(x,y);
              }
            }
            if (key.getKind()==Key.Kind.ArrowRight){
              if (x<9+tester.getCol()) {
                x++;
                terminal.moveCursor(x,y);
                putString(0,6,terminal, "x:"+x+"y: "+y);
                terminal.moveCursor(x,y);
              }
            }
            if (key.getKind()==Key.Kind.ArrowUp){
              if (y>10) {
                y--;
                terminal.moveCursor(x,y);
                putString(0,6,terminal, "x:"+x+"y: "+y);
                terminal.moveCursor(x,y);
              }
            }
            if (key.getKind()==Key.Kind.ArrowDown){
              if (y<9+tester.getRow()) {
                y++;
                terminal.moveCursor(x,y);
                putString(0,6,terminal, "x:"+x+"y: "+y);
                terminal.moveCursor(x,y);
              }
            }
            if (key.getKind()==Key.Kind.Enter) mode="SELECTED";
          }
        }

        if (mode.equals("SELECTED")){//once a candy has been selected
          terminal.setCursorVisible(true);
          if (key!=null){
            int beforex, beforey;
            if (key.getKind()==Key.Kind.ArrowLeft){
              beforex=x;
              beforey=y;
              tester.swipeCandies(y-10,x-10,"HORIZONTAL",1);
              terminal.setCursorVisible(false);
              //printpuzzle(tester, 10, 10, terminal, moves);
              pop2(tester, 10, 10, terminal, moves);
              moves--;
              printpuzzle(tester, 10, 10, terminal, moves);
              terminal.setCursorVisible(true);
              terminal.moveCursor(beforex,beforey);
              mode="GAME";
            }
            if (key.getKind()==Key.Kind.ArrowRight){
              beforex=x;
              beforey=y;
              tester.swipeCandies(y-10,x-10,"HORIZONTAL",-1);
              terminal.setCursorVisible(false);
              //printpuzzle(tester, 10, 10, terminal, moves);
              pop2(tester, 10, 10, terminal, moves);
              moves--;
              printpuzzle(tester, 10, 10, terminal, moves);
              terminal.setCursorVisible(true);
              terminal.moveCursor(beforex,beforey);
              mode="GAME";
            }
            if (key.getKind()==Key.Kind.ArrowUp){
              beforex=x;
              beforey=y;
              tester.swipeCandies(y-10,x-10,"VERTICAL",1);
              terminal.setCursorVisible(false);
              //printpuzzle(tester, 10, 10, terminal, moves);
              pop2(tester, 10, 10, terminal, moves);
              moves--;
              printpuzzle(tester, 10, 10, terminal, moves);
              terminal.setCursorVisible(true);
              terminal.moveCursor(beforex,beforey);
              mode="GAME";
            }
            if (key.getKind()==Key.Kind.ArrowDown){
              beforex=x;
              beforey=y;
              tester.swipeCandies(y-10,x-10,"VERTICAL",-1);
              terminal.setCursorVisible(false);
              //printpuzzle(tester, 10, 10, terminal, moves);
              pop2(tester, 10, 10, terminal, moves);
              moves--;
              printpuzzle(tester, 10, 10, terminal, moves);
              terminal.setCursorVisible(true);
              terminal.moveCursor(beforex,beforey);
              mode="GAME";
            }
          }
          if (tester.getPoints()>=objective) { //once you reach 1000 points, you win the game
            mode="WIN";
            terminal.clearScreen();
          }
          if (moves<=0 && tester.getPoints()<objective) {
            terminal.clearScreen();
            mode="LOSE";
          }
        }

        if(mode.equals("LOSE")){//lose screen
          terminal.setCursorVisible(false);
          terminal.clearScreen();
          terminal.applySGR(Terminal.SGR.ENTER_BOLD,Terminal.SGR.ENTER_BLINK);
          putString(10,10,terminal, "You lost. Press backspace to return to the menu screen or escape to exit.",Terminal.Color.RED,Terminal.Color.WHITE);
          terminal.applySGR(Terminal.SGR.RESET_ALL);
          if (key!= null && key.getKind() == Key.Kind.Escape) { //to exit screen
            terminal.clearScreen();
            terminal.exitPrivateMode();
            running = false;
          }
          if(key.getKind()==Key.Kind.Backspace) {
            mode="SETUP";
            terminal.clearScreen();
            setup1(terminal);
          }
        }


        if(mode.equals("WIN")) { //win screen
          terminal.setCursorVisible(false);
          terminal.clearScreen();
          terminal.applySGR(Terminal.SGR.ENTER_BOLD,Terminal.SGR.ENTER_BLINK);
          putString(10, 10, terminal, "CONGRATULATIONS, YOU WON! Press backspace to return to the menu screen or escape to exit.",Terminal.Color.GREEN,Terminal.Color.WHITE);
          terminal.applySGR(Terminal.SGR.RESET_ALL);
          if (key!= null && key.getKind() == Key.Kind.Escape) { //to exit screen
            terminal.clearScreen();
            terminal.exitPrivateMode();
            running = false;
          }
          if(key.getKind()==Key.Kind.Backspace) {
            mode="SETUP";
            terminal.clearScreen();
            setup1(terminal);
          }
        }//if mode 3
      }// if key!=null
    }//while loop
  }//main method
}//clas
