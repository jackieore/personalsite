class Particle {
  float x, y;    // current position
  float vx, vy;  // velocity
  float g = 0.05; // gravity
  float r = 5;   // radius
  boolean on=false;

  Particle() {
  }
  // Put at original position, fully opaque, with random velocity
  void initialize(float ox, float oy)
  {
    on = true;
    x = ox; 
    y = oy;
    // More vertical than horizontal
    vx = random(-1, 1); 
    vy = random(-5, -2);
  }

  void draw(color c)
  {
    if (!on) return;
    fill(c);
    ellipse(x, y, 2*r, 2*r);
  }

  void update(float ox, float oy)
  {
    // initialize if necessary
    if (!on) { 
      if (random(0, 1) < 0.5) initialize(ox, oy); 
      return;
    }
    // Usual gravity stuff
    vy += g;
    x += vx;
    y += vy;
    // When exit screen or totally transparent, re-initialize
  }
}

