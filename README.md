# MKS21X-FinalProject

Instructions:
To play our Crush game, the user can toggle around a grid of candies using the arrow keys. They are looking for candies they can swap to make a row of three. Once they find these, they can hover over one of the switches, hit enter to select it, and then hit the arrow key in the direction of the swap. The candies will swap and rows of three or more will disappear (be crushed).
To pause, the user can hit the space bar.
To end the game, the user can hit escape.

Development Log:

Day 1 - (1/4/19)
  Experimenting with the TerminalDemo.java code to figure out how to use it in our project.
  We worked on early methods and constructors (set/get methods) for both Candy and CandyGrid classes.

Day 2 - (1/6/19)
We worked on methods that will check for matches of three or more candies of the same color. Indices are stored in a 2D ArrayList that is returned by the checkCols or checkRows methods.

Day 3 - (1/7/19)
We worked on the pop methods that delete candies that are the same color (3 or more in a row/col) and shifts down the candies that are above. Also had a helper method to fill in empty spaces in the CandyGrid.

Day 4 - (1/8/19)
We fixed the pop and check methods. We changed them so that they would only remove one set of candies at a time - so that when used in the game, the methods would be able to work continuously as the game was running. We also started working on the different pages in the game (menu, gameplay, etc).

Day 5 (1/9/19)
We finished the method that builds a puzzle by making it so that the puzzle can only be built such that there are no matches to start with. We started looking more at the terminal side and started writing a method to print the game as little baby circles.

Day 6 (1/10/19)
We finished printpuzzle to display the candyGrid in the terminal (need to find a way to continuously update it during the game). We started working on the different pages and testing out how to use arrow keys (also having a boundary for where the cursor can move). We also worked on the swipe method to switch candies before popping them.

Day 7 (1/12/19)
We worked on connecting the termninal commands to the candy grid by linking the swipe method to the new mode in the terminal. A bug we are encountering is connecting the x and y values to the proper grid coordinates. Sometimes candies that aren't selected move anyways. We also changed the layout a bit to be prettier.
