package spaceinv.model;


import spaceinv.event.EventBus;
import spaceinv.event.ModelEvent;
import spaceinv.model.ships.AbstractShip;
import spaceinv.model.ships.BattleCruiser;
import spaceinv.model.ships.Bomber;
import spaceinv.model.ships.Frigate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 *  SI (Space Invader) class representing the overall
 *  data and logic of the game
 *  (nothing about the look/rendering here)
 */
public class SI {

    // Default values (not to use directly). Make program adaptable
    // by letting other programmers set values if they wish.
    // If not, set default values (as a service)
    public static final int GAME_WIDTH = 500;
    public static final int GAME_HEIGHT = 500;
    public static final int LEFT_LIMIT = 50;
    public static final int RIGHT_LIMIT = 450;
    public static final int SHIP_WIDTH = 20;
    public static final int SHIP_HEIGHT = 20;
    public static final int SHIP_MAX_DX = 3;
    public static final int SHIP_MAX_DY = 0;
    public static final int GUN_WIDTH = 20;
    public static final int GUN_HEIGHT = 20;
    public static final double GUN_MAX_DX = 2;
    public static final double PROJECTILE_WIDTH = 5;
    public static final double PROJECTILE_HEIGHT = 5;
    public static final int GROUND_HEIGHT = 20;
    public static final int OUTER_SPACE_HEIGHT = 32;

    public static final long ONE_SEC = 1_000_000_000;
    public static final long HALF_SEC = 500_000_000;
    public static final long TENTH_SEC = 100_000_000;
    public static final int PROJECTILE_SPEED = 1;

    private static final Random rand = new Random();

    // TODO More references here
    private final Gun gun= new Gun();


    private final List<Positionable> shipBombs = new ArrayList<>();
    private List<AbstractShip> allShips;
    private Positionable gunProjectile;
    private Positionable theGround;
    private Positionable outerSpace;
    private int points;


    // TODO Constructor here

    public SI() {
        theGround = new Ground();
        outerSpace = new OuterSpace();
        allShips = new ArrayList<AbstractShip>();
        //allShips.add(new Bomber(5,5)); //testShip
        int padding = 5;
        for(int numShips = 0; numShips < 10; numShips++){
            allShips.add(new Frigate((SHIP_WIDTH + padding)*numShips, SHIP_HEIGHT));
            allShips.add(new Bomber((SHIP_WIDTH + padding)*numShips, 2*SHIP_HEIGHT + padding));
            allShips.add(new BattleCruiser((SHIP_WIDTH + padding)*numShips,3*(SHIP_HEIGHT+ padding)));
        }
    }


    // Timing. All timing handled here!
    private long lastTimeForMove;
    private long lastTimeForFire;
    private int shipToMove = 0;

    private void reverseShips() {
        for(AbstractShip ship : allShips) {
            ship.reverse();
        }
    }

    // ------ Game loop (called by timer) -----------------

    public void update(long now) {
        //Wincondition
        if(allShips.size() == 0){
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.HAS_WON));
        }

        /*
             Gun actions
         */
        gun.move();
        // Projectile actions, first check movement, then collisions
        if(gunProjectile != null) {
            gunProjectile.move();
            if(gunProjectile.collides(outerSpace)) {
                gunProjectile = null;
            } else {
                for (AbstractShip ship : allShips) {
                    if (gunProjectile.collides(ship)) {
                        gunProjectile = null;
                        points += ship.getValue();
                        allShips.remove(ship);
                        break;
                    }
                }
            }

        }
        //Move one (1) ship
        shipToMove %= allShips.size();
        AbstractShip ship = allShips.get(shipToMove);

        //If a ship reaches the edge reverse all of them
        if(!ship.inBoundsX()) {
            reverseShips();
        }
        ship.move();
        shipToMove++;

        //TODO determine under what conditions a ship should fire
        if(shipBombs.size() < 8) {
            shipBombs.add(ship.fire());
        }


        /*
             Handle the ship bombs
         */
        //Add all colliding bombs to a list and remove them after we have iterated over
        //The whole list of bombs instead of instantly removing them. Otherwise we get exceptions
        List<Positionable> toRemove = new ArrayList<>();
        for(Positionable bomb : shipBombs) {
            bomb.move();
            if(bomb.collides(theGround)) {
                toRemove.add(bomb);
                continue;
            }
            if(bomb.collides(gunProjectile)) {
                gunProjectile = null;
                toRemove.add(bomb);
                continue;
            }
            if(bomb.collides(gun)) {
                System.out.println("GAME OVER!");
            }
        }
        shipBombs.removeAll(toRemove);

    }


    private boolean shipHitRightLimit() {
        return false;
    }

    private boolean shipHitLeftLimit() {
        // TODO
        return false;
    }


    // ---------- Interaction with GUI  -------------------------

    public void fireGun() {
        if(gunProjectile == null) {
            gunProjectile = gun.fire();
        }
    }

    // TODO More methods called by GUI

    public List<Positionable> getPositionables() {
        List<Positionable> ps = new ArrayList<>();
        ps.add(theGround);
        ps.add(gun);
        if(gunProjectile != null) {
            ps.add(gunProjectile);
        }
        ps.addAll(allShips);
        ps.addAll(shipBombs);
        return ps;
    }

    public void setGunSpeed(int velocity) {
        gun.setDx(velocity);
    }

    public int getPoints() {
        return points;
    }

}
