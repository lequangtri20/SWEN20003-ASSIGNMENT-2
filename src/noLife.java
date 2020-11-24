import bagel.Image;
import bagel.util.Point;

/**
 * This class inherits actors class and is a general class for pool, sign, fence, pad.
 * @author Quang Tri Le.
 */
public class noLife extends actors{

    /**
     * This is the only constructor of class noLife.
     * @param actorType This is the name of the noLife object.
     * @param point This is the coordinates of the noLife object.
     * @param image This is the image of the noLife object.
     */
    public noLife(String actorType, Point point, Image image){
        super(actorType, point, image);
    }

    /**
     * This method will keep drawing the actor of noLife class into the screen.
     */
    @Override
    public void update(){
        super.getImage().drawFromTopLeft(super.getPoint().x,super.getPoint().y);
    }

}
