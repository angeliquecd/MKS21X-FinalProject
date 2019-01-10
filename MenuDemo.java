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
import java.io.*;
import java.util.*;


public class MenuDemo {

  public static void putString(int r, int c,Terminal t, String s){
    t.moveCursor(r,c);
    for(int i = 0; i < s.length();i++){
      t.putCharacter(s.charAt(i));
    }
  }

  public static void putString(int r, int c,Terminal t,
        String s, Terminal.Color forg, Terminal.Color back ){
    t.moveCursor(r,c);
    t.applyBackgroundColor(forg);
    t.applyForegroundColor(Terminal.Color.BLACK);

    for(int i = 0; i < s.length();i++){
      t.putCharacter(s.charAt(i));
    }
    t.applyBackgroundColor(Terminal.Color.DEFAULT);
    t.applyForegroundColor(Terminal.Color.DEFAULT);
  }

  public static void printpuzzle(CandyGrid a, int x , int y, Terminal t){
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
        t.putCharacter('O');
        x++;
        }
      x=x1;
      y++;}
    }

  public static void main(String[] args) {

    Terminal terminal = TerminalFacade.createTextTerminal();
    terminal.enterPrivateMode();

    TerminalSize size = terminal.getTerminalSize();
    terminal.setCursorVisible(true);

    boolean running = true;
    int mode = 0;
    long lastTime =  System.currentTimeMillis();
    long currentTime = lastTime;
    long timer = 0;

    int x = 11;
    int y = 10;

    Random numgen = new Random();

    //terminal.moveCursor(x, y);
    // for (int a = 0; a < 10; a++) {
    //   for (int b = 0; b < 10; b++) {
    //     terminal.moveCursor(x, y);
    //     x++;
    //     int c = numgen.nextInt(5);
    //     //System.out.println(c);
    //     if (c==0) terminal.applyForegroundColor(Terminal.Color.RED);
    //     if (c==1) terminal.applyForegroundColor(Terminal.Color.BLUE);
    //     if (c==2) terminal.applyForegroundColor(Terminal.Color.YELLOW);
    //     if (c==3) terminal.applyForegroundColor(Terminal.Color.GREEN);
    //     if (c==4) terminal.applyForegroundColor(Terminal.Color.WHITE);
    //     if (c==5) terminal.applyForegroundColor(Terminal.Color.MAGENTA);
    //     terminal.putCharacter('O');
    //   }
    //   x=11;
    //   y++;
    // }
    setup(terminal);
    while(running){

    //  terminal.moveCursor(x,y);
			//terminal.applyBackgroundColor(Terminal.Color.WHITE); //cursor color
			//terminal.applyForegroundColor(Terminal.Color.BLACK);//cursor color
			//applySGR(a,b) for multiple modifiers (bold,blink) etc.
			terminal.applySGR(Terminal.SGR.ENTER_UNDERLINE);
			terminal.putCharacter('\u00a4');
			//terminal.putCharacter(' ');
			terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
			terminal.applyForegroundColor(Terminal.Color.DEFAULT);
			terminal.applySGR(Terminal.SGR.RESET_ALL);


			terminal.applyBackgroundColor(Terminal.Color.DEFAULT); //color of highlight on words and squares where cursor moves
			terminal.applyForegroundColor(Terminal.Color.DEFAULT); //color of writing on screen




      Key key = terminal.readInput();
      if (key != null)
      {

        //YOU CAN PUT DIFFERENT SETS OF BUTTONS FOR DIFFERENT MODES!!!

        //only for the game mode.
        if(mode == 0){
          if (key.getKind() == Key.Kind.Escape) {
            terminal.exitPrivateMode();
            running = false;
          }
        }

        //for all modes
        if (key.getCharacter() == ' ') {
          mode++;
          mode%=3;//3 modes
          terminal.clearScreen();
          lastTime = System.currentTimeMillis();
          currentTime = System.currentTimeMillis();
        }
      }

      terminal.applySGR(Terminal.SGR.ENTER_BOLD);
      putString(1,1,terminal, "This is mode "+mode,Terminal.Color.WHITE,Terminal.Color.RED);
      terminal.applySGR(Terminal.SGR.RESET_ALL);

      CandyGrid tester= new CandyGrid();

      if(mode==0){//game play with unselected candy
        lastTime = currentTime;
        currentTime = System.currentTimeMillis();
        timer += (currentTime -lastTime);//add the amount of time since the last frame.
      putString(3,5,terminal, "Time: "+timer,Terminal.Color.WHITE,Terminal.Color.RED);
      //Game stuff
      if (key.getKind()==key.Kind.ArrowLeft){

      }
      if (key.getKind()==key.Kind.ArrowLeft){

      }
      if (key.getKind()==key.Kind.ArrowLeft){

      }
      if (key.getKind()==key.Kind.ArrowLeft){

      }
      }
      if (mode==1){//once a candy has been selected

      }

      if(mode==2){//pause screen

        terminal.applySGR(Terminal.SGR.ENTER_BOLD,Terminal.SGR.ENTER_BLINK);
        putString(1,3,terminal, "Not game, just a pause!",Terminal.Color.RED,Terminal.Color.WHITE);
        putString(1,7,terminal, "Press G to return to game", Terminal.Color.BLUE,Terminal.Color.WHITE);
        terminal.applySGR(Terminal.SGR.RESET_ALL);

        if (key!=null && key.getCharacter()== 'g') {
          mode=0;
          terminal.clearScreen();
        }

      }

    }
}
    public static void setup(Terminal terminal){
      CandyGrid tester= new CandyGrid();
          printpuzzle(tester, 11, 10, terminal);
    }
}
