class Balloon { 
  float r = 0; //red color
  float g = 0; // green color
  float b = 255; // blue color
  float x, y; // x and y position
  float d; // diameter
  float dul; // diameter upper limit
  float dll = 5; // diameter lower limit
  float dc; // rate of diameter change
  boolean isPopped = false; // isPopped boolean starts as false

    Balloon(float x0, float y0, float d0, float dc0) {
    //method definitions
    x = x0;
    y = y0;
    d = d0;
    dc = dc0;
    dul = d; // the diameter of the balloon starts as the diameter upper limit
  }

  void draw() {
    fill(r, g, b);
    ellipse(x, y, d, d);
  }

  void update() {
    //if the balloon is not popped the diameter changes but is constrains between the diameter lower limit and upper limit
    if (!isPopped) {
      //shrinks diameter
      d -= dc;
      //if gets to lower limit constrain then start change the rate of diameter change to the opposite of itself
      if (d != constrain(d, dll, dul)) {
        d = constrain(d, dll, dul);
        dc *= -1;
      }
      b = 255 * (d-dll)/(dul-dll);
      r = 255-b;
    }
  }

  void checkPop() {
    //if is popped already no need to check if its popped
    if (isPopped) {
      return;
    }
    //the the distance between the mouse position and the x and y of the balloon is less than the radius of the balloon
    // and the red color has a value higher than 150 the boolean isPopped becomes true
    if (dist(mouseX, mouseY, x, y) <= d/2 && r>=150) {
      isPopped = true;
    }
  }
}

