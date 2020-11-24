import bagel.Font;
import bagel.Image;
import bagel.util.Point;

/**
 * This class inherits actors class and is a general class for tree, golden tree, hoard and stockpile.
 * @author Quang Tri Le.
 */
public class storage extends actors {
    private int numFruit;
    private static final Font font = new Font("res/VeraMono.ttf", 24);


    /**
     * This method is the only constructor of storage class.
     * @param actorType This is the name of the storage object.
     * @param point This is the coordinates of the storage object.
     * @param image This is the image of the storage object.
     * @param numFruit This is the number of fruits of storage object.
     */
    public storage(String actorType, Point point, Image image, int numFruit){
        super(actorType, point, image);
        this.numFruit = numFruit;
    }

    /**
     * This method will provide number of fruits of storage object.
     * @return int This is the number of fruits of storage object.
     */
    public int getNumFruit() {
        return numFruit;
    }

    /**
     * This method will set the number of fruits of storage object.
     * @param numFruit This is the new number of fruits of storage object.
     */
    public void setNumFruit(int numFruit) {
        this.numFruit = numFruit;
    }

    /**
     * This method will keep drawing the image of storage object along with its representation of number of fruits.
     */
    @Override
    public void update(){
        super.getImage().drawFromTopLeft(super.getPoint().x,super.getPoint().y);

        /* Print number of fruits if the object is not GoldenTree */
        if (!this.getActorType().equals("GoldenTree"))
            font.drawString(Integer.toString(numFruit), super.getPoint().x,super.getPoint().y);
    }
}
