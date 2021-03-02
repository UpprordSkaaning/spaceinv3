package spaceinv.model.ships;


/*
 *   Type of space ship
 */
public class BattleCruiser extends AbstractShip {

    // Default value
    private static final int BATTLE_CRUISER_POINTS = 100;

    public BattleCruiser (double x, double y) {
        super(x,y);
    }

    public int getValue() {return BATTLE_CRUISER_POINTS; }
}
