package spaceinv.model;

/*
   Must be implemented by anything that can be positioned in the world.
   Used by GUI. This must be fulfilled by any object the GUI shall render
 */
public interface Positionable {

    double getX();      // x and x is upper left corner (y-axis pointing down)

    double getY();

    double getDy(); //To be removed, for testing only

    double getWidth();

    double getHeight();

    void move();

    boolean collides(Positionable other);

}
