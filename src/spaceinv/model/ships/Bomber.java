package spaceinv.model.ships;

/*
 *   Type of space ship
 */
public class Bomber extends AbstractShip{

    // Default value
    public static final int BOMBER_POINTS = 200;

    public Bomber(double x, double y) {
        super(x,y);
    }

    public int getValue() { return BOMBER_POINTS; }

}
