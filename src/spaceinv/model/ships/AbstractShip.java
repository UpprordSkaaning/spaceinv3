package spaceinv.model.ships;

import spaceinv.model.*;

//We add an abstract ship class even though it isn't particularly useful because object orientation.
public abstract class AbstractShip extends GameObject implements Shootable {

    private double accelerationConstant = 1.05; //Determines how much the ship should accelerate when hitting a wall,
    public AbstractShip(double x, double y) {
        super(x,y, SI.SHIP_WIDTH,SI.SHIP_HEIGHT,(double)SI.SHIP_MAX_DX/10,SI.SHIP_MAX_DY);
    }

    public Projectile fire() {
        return Shooter.fire(this, SI.PROJECTILE_SPEED);
    }
    //Reverses the direction of the ship and accelerates it
    public void reverse() {
        this.setDx(Math.min(Math.abs(this.getDx()*accelerationConstant),SI.SHIP_MAX_DX)*-Math.signum(this.getDx()));
        this.setY(this.getY() + this.getHeight());
    }

    public abstract int getValue();


}
