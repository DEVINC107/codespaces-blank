class vec2 {
  public final int x;
  public final int y;
  
  public vec2(int x, int y) {
    this.x = x;
    this.y = y;
  }
  public boolean equals(int x, int y) {
    return (x==this.x) && (y==this.y);
  }
  public boolean equals(vec2 other) {
    return equals(other.x, other.y);
  }
  public vec2 move(int x, int y) {
    return new vec2(this.x + x, this.y + y);
  }
  public vec2 move(vec2 vel) {
    return new vec2(this.x + vel.x, this.y + vel.y);
  }
  public double distance(vec2 point) {
    return Math.sqrt(Math.pow(x- point.x, 2) + Math.pow(y - point.y, 2));
  }
  public String info() {
    return String.format("x: %d y: %d", x, y);
  }
  public vec2 mult(vec2 other) {
    return new vec2(this.x * other.x, this.y * other.y);
  }
  public vec2 unit() {
    return new vec2((int) Math.signum(this.x), (int) Math.signum(this.y));
  }
  public vec2 add(vec2 other) {
    return new vec2(other.x + this.x, other.y + this.y);
  }
  public vec2 subtract(vec2 other) {
    return new vec2(other.x - this.x, other.y - this.y);
  }
}