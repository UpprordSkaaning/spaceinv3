package spaceinv.model;


import static spaceinv.model.SI.*;

/*
 *    A Gun for the game
 *    Can only fire one projectile at the time
 */
public class Gun extends GameObject implements Shootable {

    public Gun() {
        super((double)GAME_WIDTH/2,GAME_HEIGHT-GROUND_HEIGHT-GUN_HEIGHT, GUN_WIDTH, GUN_HEIGHT);
    }

    public Projectile fire() {
        return Shooter.fire(this,-PROJECTILE_SPEED);
    }






}
