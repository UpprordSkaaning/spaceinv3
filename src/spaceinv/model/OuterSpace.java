package spaceinv.model;

import static spaceinv.model.SI.GAME_WIDTH;
import static spaceinv.model.SI.OUTER_SPACE_HEIGHT;

/*
    Used to check if projectiles from gun have left our world
    Non visible class
 */
public class OuterSpace extends GameObject {

    //Outer space should always be created in the same place and have no velocity
    public OuterSpace() {
        super(0,-OUTER_SPACE_HEIGHT, GAME_WIDTH, OUTER_SPACE_HEIGHT,0,0);
    }

}
