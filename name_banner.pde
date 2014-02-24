//code updated from http://www.jaboston.com/tutorials/Tutorial_4/anything/anything_pde/anything_pde.pde
PFont font; // declares a new font variable
int ypos; // this will be sued for our yposition
int xpos; // this will be used for our xposition
int inc;  // This will be used for moving our shapes and letters along
color col; // this is for changing the color
boolean el; // this boolean value is used as a 'switch' to runOnce();!;


void setup() {
  col = color(0, 0, 200);  // setting the color to 200 points into the blue value
  font = loadFont("AmericanTypewriter-48.vlw"); // can be specified locally or by a web address
  size(900, 100); // create a long 'banner' canvas to play with
  smooth(); // smooth so that text and shapes look clearer
  textSize(60); //our textsize is 60
  ypos=50; // y position is 70
  frameRate(120); // framerate (fps) is equal to 120
  el = false; // the starting value for this boolean is false
}



void draw() {
  background(255); // white background
  if (el == true) { // if el is true
    col = color(int(random(255)), int(random(255)), int(random(255))); // set the col (color) variable to something random
    el = false; // set el to false
  }
  inc++; // add one to inc
  xpos=inc/12; // the xposition is equal to the increment divided by 12.

  if (mousePressed) { // if the mouse has been pressed
    inc = 0; // variable inc is set to 0.
    el = true; // boolean el is now true
  }
  if (inc>600) {
    fill(col); //col is equal to three integer variables therefore we can use this instead of typing three integer variables
    inc = 600; // set to a constant
    stroke(0); // makes the stroke black
    line(50, ypos+20, 400+xpos*8, ypos+20);
  }


  fill(col, 150);
  text("K", 2*xpos, ypos); // This will move the J along until inc reaches 1000. the xposition is being multiplied by two and 
  text("A", 3*xpos, ypos); // this is multiplying the x position by 3 which creates spacing between the J and the A
  text("Y", 4*xpos, ypos);
  text("A", 5*xpos, ypos);
  text("'", 6*xpos, ypos);
  text("S", 6.5*xpos, ypos);
  text("P", 8*xpos, ypos);
  text("R", 9*xpos, ypos);
  text("O", 10*xpos, ypos);
  text("J", 11*xpos, ypos);
  text("E", 11.5*xpos, ypos);
  text("C", 12.5*xpos, ypos);
  text("T", 13.5*xpos, ypos);
  text("S", 14.5*xpos, ypos);
  strokeWeight(5);
  line(50, ypos+20, 400+xpos*8, ypos+20);
  //  println(inc);
  //  println(xpos*2);
  //  println(xpos*3);
}

