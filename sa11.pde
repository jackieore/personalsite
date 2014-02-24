PImage background_image; // background image
PImage bug;     // interactive image 
float iw=200; // interactive image intial width
float ih=100; // interactive image intial height

void setup() 
{
  size(500, 700);
  smooth(); 
  // load images
  background_image = loadImage("rsz_leaves.jpg");
  bug = loadImage("spider.png");
}

void draw() 
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

