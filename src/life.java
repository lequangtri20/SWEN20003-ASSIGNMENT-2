import bagel.util.Point;
import bagel.*;
import java.util.ArrayList;

/**
 * This class inherits from actors class and is a general class for gatherer and thief.
 * @author Quang Tri Le.
 */
public abstract class life extends actors {
    private int speed = 64;
    protected boolean carrying = false;
    protected boolean active = true;
    protected double x;
    protected double y;
    protected String direction;

    /**
     * lifeToAdd is an array of life objects that will be added into the global array allActors.
     */
    public static ArrayList<actors> lifeToAdd = new ArrayList<actors>();

    /**
     * lifeToDel is an array of life objects that will be deleted from the global array allActors.
     */
    public static ArrayList<actors> lifeToDel = new ArrayList<actors>();

    /**
     * This method is the only constructor of life class.
     * @param actorType This is the name of the life object.
     * @param point This is the coordinates of the life object.
     * @param image This is the image of the life object.
     */
    public life (String actorType, Point point, Image image) {
        super(actorType, point, image);
    }

    /**
     * This method will provide the speed of the life object.
     * @return int This is the current speed of life object.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * This method will update the state of life object.
     */
    public abstract void update();

    /*================================================INTERACTION=====================================================*/

    /* interact() is used by life object to interact with all other actors. It will include sub methods for every type
    of interaction listed below */
    protected abstract void interact();

    protected abstract void interactTree(storage tree);

    protected abstract void interactPool(actors pool);

    protected abstract void interactStorage(storage storer);

    protected void interactFence(){
        this.active = false;
        if ((direction.equals("RIGHT")) || (direction.equals("LEFT")))
            this.x -= speed;

        else
            this.y -= speed;
    }

    protected void interactSign(String actorType){
        if (actorType.equals("SignUp"))
            this.direction = "UP";

        else if (actorType.equals("SignDown"))
            this.direction = "DOWN";

        else if (actorType.equals("SignLeft"))
            this.direction = "LEFT";

        else if (actorType.equals("SignRight"))
            this.direction = "RIGHT";

        this.setVelocity();
    }
    /*================================================================================================================*/

    /**
     * This method sets the sign of speed based on movement direction.
     */
    public void setVelocity(){
        if (direction.equals("RIGHT")||direction.equals("DOWN")) {
            speed = 64;
        }
        else{
            speed = -64;
        }
    }


    /**
     * This method will change the direction of life object.
     * @param dir This is the current direction of life object before changing.
     * @param deg This is the degree that life object needs to rotate.
     * @param way This is either clockwise or anti-clockwise.
     * @return String This is the new direction of life object.
     */
    public String rotate(String dir, int deg, String way){
        ArrayList<String> compass = new ArrayList<String>();
        compass.add("UP");
        compass.add("RIGHT");
        compass.add("DOWN");
        compass.add("LEFT");
        int numIter = deg/90;
        int currentIndex = compass.indexOf(dir);

        for (int i = 0 ; i < numIter; i++){
            if (way.equals("clockwise")){
                currentIndex += 1;
            }

            else if (way.equals("anti-clockwise")){
                currentIndex -= 1;
            }

            if (currentIndex> (compass.size() -1 ))
                currentIndex = 0;

            else if (currentIndex < 0)
                currentIndex = compass.size() -1;
        }
        return compass.get(currentIndex);
    }

}
