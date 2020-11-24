import bagel.util.Point;
import bagel.*;

/**
 * This is the highest super class that will be inherited by noLife, life and storage classes.
 * @author Quang Tri Le.
 */
public abstract class actors {
    private final String actorType;
    private final Point point;
    private final Image image;

    /**
     * This is the only constructor of actors class.
     * @param actorType is the name of the actor.
     * @param point is the coordinates of the actor.
     * @param image is the image of the actor.
     */
    public actors(String actorType, Point point, Image image){
        this.actorType = actorType;
        this.point = point;
        this.image = image;
    }

    /**
     * This method is used to get the coordinates of the actor.
     * @return Point This returns coordinates of the actor.
     */
    public Point getPoint() {
        return point;
    }

    /**
     * The method will give the Image of the class, and used to drawn to screen.
     * @return Image This returns the image of the actor.
     */
    public Image getImage() {
        return image;
    }

    /**
     * The method will provide the name of the actor.
     * @return String This returns the name of the actor.
     */
    public String getActorType() {
        return actorType;
    }

    /**
     * This is an abstract class for updating status of the actor.
     */
    public abstract void update();
}
