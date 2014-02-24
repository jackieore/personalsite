import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sa11 extends PApplet {

PImage background_image; // background image
PImage bug;     // interactive image 
float iw=200; // interactive image intial width
float ih=100; // interactive image intial height

public void setup() 
{
  size(500, 700);
  smooth(); 
  // load images
  background_image = loadImage("rsz_leaves.jpg");
  bug = loadImage("spider.png");
}

public void draw() 
{
  // paint background
  imageMode(CORNER); // specify it at the corners, easier for backgrounds
  background(background_image);
  // when the space bar is pressed it paints bug the image at the mouse position
  if (key == ' ') {
    imageMode(CENTER); // specify at the center, easier for sprites
    image(bug, mouseX, mouseY, iw, ih); // note the rescaling
  }
  //when the g key is pressed the image starts to increase in size
  if (key == 'g') {
    imageMode(CENTER); // specify at the center, easier for sprites
    image(bug, mouseX, mouseY, iw, ih); // note the rescaling
    iw++;
    ih++;
  }
  //when the s key is pressed the image starts to decrease in size
  if (key == 's' && iw > 0 && ih > 0) {
    imageMode(CENTER); // specify at the center, easier for sprites
    image(bug, mouseX, mouseY, iw, ih); // note the rescaling
    iw--;
    ih--;
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sa11" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
