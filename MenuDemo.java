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


public class MenuDemo {
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

  public static void setup(Terminal terminal, CandyGrid test){
    //CandyGrid tester= new CandyGrid();
        printpuzzle(test, 10, 10, terminal);
        putString(0,0,terminal,"WELCOME TO CANDY CRUSH!",Terminal.Color.GREEN,Terminal.Color.WHITE);
        putString(0,1,terminal,"To quit, press escape.");
    //    putString(0,2,terminal,"To pause game, press the space bar.");
        putString(0,3,terminal,"Objective: Get 1000 points");
        terminal.moveCursor(10,10);
  }
  public static void setup2(Terminal terminal){
    //CandyGrid tester= new CandyGrid();
      //  printpuzzle(test, 10, 10, terminal);
        putString(0,0,terminal,"WELCOME TO CANDY CRUSH!",Terminal.Color.GREEN,Terminal.Color.WHITE);
        putString(0,1,terminal,"To quit, press escape.");
        putString(0,2,terminal,"To start the game, press the space bar.");
      //  putString(0,3,terminal,"Objective: Get 1000 points");
        terminal.moveCursor(10,10);
  }

  public static void printpuzzle(CandyGrid a, int x , int y, Terminal t){
    putString(0,4,t,"Points: "+a.getPoints());
    int x1 = x;
    int c;
    for (int i =0;i<a.getRow();i++){
      for (int b=0;b<a.getCol();b++){
        c=a.getGrid()[i][b].getColorInt();
        if (c==0) t.applyForegroundColor(Terminal.Color.RED);
        if (c==1) t.applyForegroundColor(Terminal.Color.BLUE);
        if (c==2) t.applyForegroundColor(Terminal.Color.YELLOW);
        if (c==3) t.applyForegroundColor(Terminal.Color.GREEN);
        if (c==4) t.applyForegroundColor(Terminal.Color.WHITE);
        if (c==5) t.applyForegroundColor(Terminal.Color.MAGENTA);
        t.moveCursor(x,y);
        if(a.getGrid()[i][b].getSelect())t.putCharacter('\u2610');
        else t.putCharacter('O');
        x++;
      }
      x=x1;
      y++;
    }
  }

  public static void main(String[] args) {
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

     int x = 10;
     int y = 10;
    Random numgen = new Random();
    CandyGrid tester= new CandyGrid();//creates new puzzle
    setup2(terminal);
    while(running){
    //  terminal.moveCursor(x,y);
			//terminal.applyBackgroundColor(Terminal.Color.WHITE); //cursor color
			//terminal.applyForegroundColor(Terminal.Color.BLACK);//cursor color
			//applySGR(a,b) for multiple modifiers (bold,blink) etc.
			terminal.applySGR(Terminal.SGR.ENTER_UNDERLINE);
//			terminal.putCharacter('\u00a4');
			//terminal.putCharacter(' ');
			terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
			terminal.applyForegroundColor(Terminal.Color.DEFAULT);
			terminal.applySGR(Terminal.SGR.RESET_ALL);


			terminal.applyBackgroundColor(Terminal.Color.DEFAULT); //color of highlight on words and squares where cursor moves
			terminal.applyForegroundColor(Terminal.Color.DEFAULT); //color of writing on screen




      Key key = terminal.readInput();
      if (key != null)
      {
        //only for the game mode.
          if (key.getKind() == Key.Kind.Escape) {
            terminal.exitPrivateMode();
            running = false;}

        if (key.getCharacter() == ' ') {//to switch to pause
          mode=0;
          running=false;
          terminal.clearScreen();
          setup(terminal,tester);
          running=true;}


      terminal.applySGR(Terminal.SGR.RESET_ALL);



      if(mode==0){//game play with unselected candy
        //Game stuff
        terminal.setCursorVisible(true);
        if (key!=null){
          if (key.getKind()==Key.Kind.ArrowLeft){
            if (x>10) {
              x--;
              terminal.moveCursor(x,y);}            }
          if (key.getKind()==Key.Kind.ArrowRight){
            if (x<19) {
              x++;
              terminal.moveCursor(x,y);}
          }
          if (key.getKind()==Key.Kind.ArrowUp){
            if (y>10) {
              y--;
              terminal.moveCursor(x,y);}
          }
          if (key.getKind()==Key.Kind.ArrowDown){
            if (y<19) {
              y++;
              terminal.moveCursor(x,y);}
          }
          if (key.getKind()==Key.Kind.Enter){
            mode=1;}
        }
      }
      if (mode==1){//once a candy has been selected
        terminal.setCursorVisible(true);
        if (key!=null){
          int beforex, beforey;
        //  CandyGrid before;
          CandyGrid evenbefore;
        int pointy;
        if (key.getKind()==Key.Kind.ArrowLeft){
          beforex=x;
          beforey=y;
          evenbefore=tester;
          pointy=tester.getPoints();
          //putString(0,1,terminal,""+x+y);
          tester.swipeCandies(y-10,x-10,"HORIZONTAL",1);
          terminal.setCursorVisible(false);
          printpuzzle(tester, 10, 10, terminal);
          //before=tester
          tester.pop();
          if (pointy==tester.getPoints()) tester=evenbefore;//if no pop is done, it switches it back
          printpuzzle(tester, 10, 10, terminal);
          terminal.setCursorVisible(true);
          terminal.moveCursor(beforex,beforey);
          mode=0;}
        if (key.getKind()==Key.Kind.ArrowRight){
          beforex=x;
          beforey=y;
          evenbefore=tester;
          pointy=tester.getPoints();
          tester.swipeCandies(y-10,x-10,"HORIZONTAL",-1);
          terminal.setCursorVisible(false);
          printpuzzle(tester, 10, 10, terminal);
          //before=tester;
          tester.pop();
          if (pointy==tester.getPoints()) tester=evenbefore;//if no pop is done, it switches it back
          printpuzzle(tester, 10, 10, terminal);
          terminal.setCursorVisible(true);
          terminal.moveCursor(beforex,beforey);
          mode=0;}
        if (key.getKind()==Key.Kind.ArrowUp){
          beforex=x;
          beforey=y;
          evenbefore=tester;
          pointy=tester.getPoints();
          tester.swipeCandies(y-10,x-10,"VERTICAL",1);
          terminal.setCursorVisible(false);
          printpuzzle(tester, 10, 10, terminal);
        //  before=tester;
          tester.pop();
          if (pointy==tester.getPoints()) tester=evenbefore;//if no pop is done, it switches it back
          printpuzzle(tester, 10, 10, terminal);
          terminal.setCursorVisible(true);
          terminal.moveCursor(beforex,beforey);
          mode=0;}
        if (key.getKind()==Key.Kind.ArrowDown){
          beforex=x;
          beforey=y;
        pointy=tester.getPoints();
          evenbefore=tester;
          tester.swipeCandies(y-10,x-10,"VERTICAL",-1);
          terminal.setCursorVisible(false);
          printpuzzle(tester, 10, 10, terminal);
        //  before=tester;
          tester.pop();
          if (pointy==tester.getPoints()) tester=evenbefore;
          printpuzzle(tester, 10, 10, terminal);
          terminal.setCursorVisible(true);
          terminal.moveCursor(beforex,beforey);
          mode=0;}
        }//if key!=null
          if (tester.getPoints()>=1000) { //once you reach 1000 points, you win the game
            mode = 3;
            terminal.clearScreen();}

      }//if mode==1

    //  if(mode==2){//pause screen
      //  terminal.setCursorVisible(false);
      //  terminal.applySGR(Terminal.SGR.ENTER_BOLD,Terminal.SGR.ENTER_BLINK);
        //putString(1,3,terminal, "Welcome to Candy Crush!",Terminal.Color.RED,Terminal.Color.WHITE);
        //putString(1,7,terminal, "Press the space bar to begin.", Terminal.Color.BLUE,Terminal.Color.WHITE);
        //erminal.applySGR(Terminal.SGR.RESET_ALL);

        //if (key!=null && key.getCharacter()== 'g') {
          //mode=0;
          //terminal.clearScreen();
      //  }

    //  if(mode==3) { //win screen
      //  terminal.setCursorVisible(false);
        //terminal.clearScreen();
      //  terminal.applySGR(Terminal.SGR.ENTER_BOLD,Terminal.SGR.ENTER_BLINK);
        //putString(1, 3, terminal, "CONGRATULATIONS, YOU WON!",Terminal.Color.RED,Terminal.Color.WHITE);
        //terminal.applySGR(Terminal.SGR.RESET_ALL);
        //if (key!= null && Key.getKind() == Key.Kind.Escape) {
          //terminal.clearScreen();
          //terminal.exitPrivateMode();
          //running = false;}
      }//if mode 3

    }//while loop
  }//main method
}//clas
