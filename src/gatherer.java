import bagel.Image;
import bagel.util.Point;

/**
 * This class is the implementation of the gatherer.
 * @author Quang Tri Le.
 */
public class gatherer extends life{

    /**
     * This is the only constructor of gatherer class.
     * @param actorType This should always be "Gatherer".
     * @param point This is the original coordinates of the gatherer.
     * @param image This is the image of the gatherer.
     * @param direction This is the default direction of the gatherer.
     */
    public gatherer(String actorType, Point point, Image image, String direction) {
        super(actorType, point, image);
        this.x = point.x;
        this.y = point.y;
        this.direction = direction;
        this.setVelocity();
        image.drawFromTopLeft(x,y);
    }

    /**
     * This method is used to update the state and movement of the gatherer. It will also check if the gatherer is
     * interacting with anything or not.
     */
    @Override
    public void update(){
        if ((ShadowLife.diff >= ShadowLife.tickRate) && (ShadowLife.diff < (ShadowLife.tickRate + 50)) && this.active) {
            if ((direction.equals("RIGHT")) || (direction.equals("LEFT")))
                this.x += super.getSpeed();

            else
                this.y += super.getSpeed();

            this.interact();
        }

        if (!lifeToDel.contains(this))
            super.getImage().drawFromTopLeft(x, y);
    }

    @Override
    protected void interact(){
        for (int i = 0; i  < ShadowLife.allActors.size(); i++){
            actors object = ShadowLife.allActors.get(i);
            if (this.x == object.getPoint().x && this.y == object.getPoint().y){

                if (object.getActorType().equals("Pool")) {
                    this.interactPool(object);
                }

                else if (object.getActorType().equals("Fence")) {
                    this.interactFence();
                }

                else if (object.getActorType().equals("Tree") ||object.getActorType().equals("GoldenTree")){
                    storage tree = (storage) object;
                    this.interactTree(tree);
                }

                else if (object.getActorType().equals("Hoard") || object.getActorType().equals("Stockpile")){
                    storage storer = (storage) object;
                    this.interactStorage(storer);
                }

                else{
                    this.interactSign(object.getActorType());
                }
            }
        }
    }


    @Override
    protected void interactPool(actors object){
        if (this.direction.equals("LEFT") ||this.direction.equals("RIGHT") ) {
            Point pointUp = new Point(object.getPoint().x, object.getPoint().y - 64);
            Point pointDown = new Point(object.getPoint().x, object.getPoint().y + 64);
            lifeToAdd.add(new gatherer("Gatherer", pointUp, new Image("res/images/gatherer.png"),
                    "UP"));
            lifeToAdd.add(new gatherer("Gatherer", pointDown, new Image("res/images/gatherer.png"),
                    "DOWN"));
        }
        if (this.direction.equals("UP") ||this.direction.equals("DOWN")) {
            Point pointLeft = new Point(object.getPoint().x -64, object.getPoint().y);
            Point pointRight = new Point(object.getPoint().x + 64, object.getPoint().y);
            lifeToAdd.add(new gatherer("Gatherer", pointLeft, new Image("res/images/gatherer.png"),
                    "LEFT"));
            lifeToAdd.add(new gatherer("Gatherer", pointRight, new Image("res/images/gatherer.png"),
                    "RIGHT"));
        }
        lifeToDel.add(this);
    }

    @Override
    protected void interactFence(){
        this.active = false;
        if ((direction.equals("RIGHT")) || (direction.equals("LEFT")))
            this.x -= super.getSpeed();

        else
            this.y -= super.getSpeed();
    }

    @Override
    protected void interactTree(storage tree){
        if (!this.carrying) {
            if (tree.getNumFruit() >= 1) {
                if (tree.getActorType().equals("Tree"))
                    tree.setNumFruit(tree.getNumFruit() -1);
                this.carrying = true;
                this.direction = this.rotate(this.direction, 180, "clockwise");
                this.setVelocity();
            }
        }
    }

    @Override
    protected void interactStorage(storage storer){
        if (this.carrying){
            this.carrying = false;
            storer.setNumFruit(storer.getNumFruit() + 1);

        }
        this.direction = this.rotate(this.direction, 180, "clockwise");
        this.setVelocity();
    }

}
