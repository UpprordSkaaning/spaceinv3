package spaceinv.model;

public abstract class GameObject implements Positionable {

    private double x;
    private double y;
    private double dx;
    private double dy;
    private final double width;
    private final double height;
    public final double maxVel = Integer.MAX_VALUE;

    public boolean inBoundsX() {
        return 0 < getX()+getDx() && getX()+getDx()+width < SI.GAME_WIDTH;
    }
    public boolean inBoundsY() {
        return SI.GROUND_HEIGHT < getY() + getDy()  && getY() + getDy() + height < SI.GAME_HEIGHT;
    }

    public GameObject (double x, double y, double width, double height, double dx, double dy) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    //If velocity is not specified the object is considered stationary
    public GameObject (double x, double y, double width, double height) {
        this(x,y,width,height,0,0);
    }


    public void move() {
        if(inBoundsX()) {
            setX(getX() + getDx());
        }
        setY(getY() + getDy());

    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDx () { return dx; }

    public void setDx(double dx) { this.dx = dx; }

    public double getDy() { return dy; }

    public void setDy(double dy) { this.dy = dy; }

    //TODO This method is really ugly, try find a better algorithm for this.
    public boolean collides(Positionable other) {
        //You can't hit what you can't see
        if(other == null) {
            return false;
        }
        //We have to take the velocity of the objects into account when calculating collisions,
        //Otherwise they might pass right through each other.
        if(this.getX() + this.getWidth()  < other.getX()) {
            return false;
        } else if(other.getX() + other.getWidth() < this.getX()) {
            return false;
        } else if(this.getY() + this.getHeight() < other.getY()) {
            return false;
        } else if(other.getY() + other.getHeight() < this.getY()) {
            return false;
        }
        return true;
    }
}
