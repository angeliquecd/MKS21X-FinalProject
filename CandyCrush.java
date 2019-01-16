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

  public static void setup(Terminal terminal, CandyGrid test, int move){
    printpuzzle(test, 10, 10, terminal, move);
    putString(0,0,terminal,"WELCOME TO CANDY CRUSH!",Terminal.Color.GREEN,Terminal.Color.WHITE);
    putString(0,1,terminal,"To quit, press escape.");
    putString(0,3,terminal,"Objective: Get 1000 points");
    terminal.moveCursor(10,10);
  }

  public static void setup2(Terminal terminal){
    putString(0,0,terminal,"WELCOME TO CANDY CRUSH!",Terminal.Color.GREEN,Terminal.Color.WHITE);
    putString(0,1,terminal,"To quit, press escape.");
    putString(0,2,terminal,"To start the game, press the space bar.");
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
          c=a.getGrid()[i][b].getColorInt();
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

  public static void pop2(CandyGrid test, int x1, int y1, Terminal t, int move){
    boolean runs =true;
    boolean run=true;
    t.setCursorVisible(false);
    while(runs||run){
      //System.out.println("\nROWS: " + checkRows());
      printpuzzle(test, x1, y1, t, move);
      Thread.sleep(1000);
      runs=test.popRows();
      //Time.wait(1);
      printpuzzle(test, x1, y1, t, move);
      //System.out.println("TEST after rows\n" + this.toStringDebug());
      test.fillEmptyGrid();
      printpuzzle(test, x1, y1, t, move);
      //System.out.println("AFTER FILL: \n" + toStringDebug());
      //System.out.println("\nCOL: " + checkCols());
      run=test.popCols();
      printpuzzle(test, x1, y1, t, move);
      //System.out.println("TEST after cols\n" + this.toStringDebug());
      test.fillEmptyGrid();
      printpuzzle(test, x1, y1, t, move);
      //System.out.println("AFTER FILL: \n" + toStringDebug());
    }
    t.setCursorVisible(true);
  }

  public static void main(String[] args) { //throws InterruptedException
    Terminal terminal = TerminalFacade.createTextTerminal();
    terminal.enterPrivateMode();

    TerminalSize size = terminal.getTerminalSize();
    terminal.setCursorVisible(true);

    Screen scr = new Screen(terminal, size);

    boolean running = true;
    int mode = 2;
    long lastTime =  System.currentTimeMillis();
    long currentTime = lastTime;
    long timer = 0;
    int moves = 10;

    int x = 10;
    int y = 10;
    Random numgen = new Random();
    CandyGrid tester= new CandyGrid();//creates new puzzle
    setup2(terminal);
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

        if (key.getCharacter() == ' ') {//to switch to game play
          mode=0;
          running=false;
          terminal.clearScreen();
          terminal.setCursorVisible(false);
          setup(terminal,tester, moves);
          running=true;
        }

        terminal.applySGR(Terminal.SGR.RESET_ALL);

        if(mode==0){//game play with unselected candy
          terminal.setCursorVisible(true);
          if (key!=null){
            if (key.getKind()==Key.Kind.ArrowLeft){
              if (x>10) {
                x--;
                terminal.moveCursor(x,y);
              }
            }
            if (key.getKind()==Key.Kind.ArrowRight){
              if (x<19) {
                x++;
                terminal.moveCursor(x,y);
              }
            }
            if (key.getKind()==Key.Kind.ArrowUp){
              if (y>10) {
                y--;
                terminal.moveCursor(x,y);
              }
            }
            if (key.getKind()==Key.Kind.ArrowDown){
              if (y<19) {
                y++;
                terminal.moveCursor(x,y);
              }
            }
            if (key.getKind()==Key.Kind.Enter) mode=1;
          }
        }

        if (mode==1){//once a candy has been selected
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
              mode=0;
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
              mode=0;
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
              mode=0;
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
              mode=0;
            }
          }
          if (tester.getPoints()>=1000) { //once you reach 1000 points, you win the game
            mode = 3;
            terminal.clearScreen();
          }
          if (moves<=0 && tester.getPoints()<1000) {
            terminal.clearScreen();
            mode=2;
          }
        }

        if(mode==2){//lose screen
          terminal.setCursorVisible(false);
          terminal.clearScreen();
          terminal.applySGR(Terminal.SGR.ENTER_BOLD,Terminal.SGR.ENTER_BLINK);
          putString(10,10,terminal, "You lost",Terminal.Color.RED,Terminal.Color.WHITE);
          terminal.applySGR(Terminal.SGR.RESET_ALL);
          if (key!= null && key.getKind() == Key.Kind.Escape) { //to exit screen
            terminal.clearScreen();
            terminal.exitPrivateMode();
            running = false;
          }
        }


        if(mode==3) { //win screen
          terminal.setCursorVisible(false);
          terminal.clearScreen();
          terminal.applySGR(Terminal.SGR.ENTER_BOLD,Terminal.SGR.ENTER_BLINK);
          putString(10, 10, terminal, "CONGRATULATIONS, YOU WON!",Terminal.Color.GREEN,Terminal.Color.WHITE);
          terminal.applySGR(Terminal.SGR.RESET_ALL);
          if (key!= null && key.getKind() == Key.Kind.Escape) { //to exit screen
            terminal.clearScreen();
            terminal.exitPrivateMode();
            running = false;
          }
        }//if mode 3
      }// if key!=null
    }//while loop
  }//main method
}//clas
