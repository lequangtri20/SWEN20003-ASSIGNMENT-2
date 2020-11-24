import bagel.Image;
import bagel.util.Point;

/**
 * This is the implementation of the thief.
 * @author Quang Tri Le.
 */
public class thief extends life {
    private boolean consuming = false;

    /**
     * This is the only constructor of thief class.
     * @param actorType This should always be "Thief".
     * @param point This is the original coordinates of the thief.
     * @param image This is the image of the thief.
     * @param direction This is the default direction of the thief.
     */
    public thief (String actorType, Point point, Image image, String direction) {
        super(actorType, point, image);
        this.x = point.x;
        this.y = point.y;
        this.direction = direction;
        this.setVelocity();
        image.drawFromTopLeft(x,y);
    }

    /**
     * This method is used to update the state and movement of the thief. It will also check if the thief is
     * interacting with anything or not.
     */
    @Override
    public void update(){
        if ((ShadowLife.diff >= ShadowLife.tickRate) && (ShadowLife.diff < (ShadowLife.tickRate + 50)) && active) {
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

            /* Since gatherer can't use point.x and point.y to compare (they are final) */
             if (object.getActorType().equals("Gatherer")) {
                life gatherer  = (life)object;
                if (!this.interactGatherer(gatherer))
                    continue;
            }

            if (this.x == object.getPoint().x && this.y == object.getPoint().y){
                if (object.getActorType().equals("Pool")) {
                    this.interactPool(object);
                }

                else if (object.getActorType().equals("Fence")) {
                    this.interactFence();
                }

                else if (object.getActorType().equals("Pad")){
                    this.consuming = true;
                }

                else if (object.getActorType().equals("Tree") || object.getActorType().equals("GoldenTree")){
                    storage tree = (storage) ShadowLife.allActors.get(i);
                    this.interactTree(tree);
                }

                else if (object.getActorType().equals("Hoard") || object.getActorType().equals("Stockpile")) {
                    storage store = (storage) object;
                    this.interactStorage(store);
                }

                else
                    this.interactSign(object.getActorType());
            }
        }
    }

    private boolean interactGatherer(life gatherer){
        if (this.x != gatherer.x || this.y != gatherer.y)
            return false;

        this.direction = this.rotate(this.direction, 270, "clockwise");
        this.setVelocity();
        return true;
    }

    @Override
    protected void interactPool(actors object){
        if (object.getActorType().equals("Pool")) {
            if (this.direction.equals("LEFT") ||this.direction.equals("RIGHT") ) {
                Point pointUp = new Point(object.getPoint().x, object.getPoint().y - 64);
                Point pointDown = new Point(object.getPoint().x, object.getPoint().y + 64);
                lifeToAdd.add(new thief("Thief", pointUp, new Image("res/images/thief.png"),
                        "UP"));
                lifeToAdd.add(new thief("Thief", pointDown, new Image("res/images/thief.png"),
                        "DOWN"));
            }
            if (this.direction.equals("UP") ||this.direction.equals("DOWN")) {
                Point pointLEFT = new Point(object.getPoint().x -64, object.getPoint().y);
                Point pointRIGHT = new Point(object.getPoint().x + 64, object.getPoint().y);
                lifeToAdd.add(new thief("Thief", pointLEFT, new Image("res/images/thief.png"),
                        "LEFT"));
                lifeToAdd.add(new thief("Thief", pointRIGHT, new Image("res/images/thief.png"),
                        "RIGHT"));
            }
            lifeToDel.add(this);
        }
    }

    @Override
    protected void interactStorage(storage storer){
        if (storer.getActorType().equals("Hoard")) {
            this.interactHoard(storer);
        }
        else if (storer.getActorType().equals("Stockpile"))
            this.interactStockpile(storer);
    }

    private void interactHoard(storage hoard){
        if (this.consuming) {
            this.consuming = false;
            if (!this.carrying) {
                if (hoard.getNumFruit() >= 1) {
                    this.carrying = true;
                    hoard.setNumFruit(hoard.getNumFruit() -1);
                } else {
                    this.direction = this.rotate(this.direction, 90, "clockwise");
                    this.setVelocity();
                }
            }
        } else if (this.carrying) {
            this.carrying = false;
            hoard.setNumFruit(hoard.getNumFruit() + 1);
            this.direction = this.rotate(this.direction, 90, "clockwise");
            this.setVelocity();
        }
    }

    private void interactStockpile(storage stock){
        if (!carrying) {
            if (stock.getNumFruit() >= 1) {
                this.carrying = true;
                this.consuming = false;
                stock.setNumFruit(stock.getNumFruit() - 1);
                this.direction = this.rotate(this.direction, 90, "clockwise");
                this.setVelocity();
            }
        } else {
            this.direction = this.rotate(this.direction, 90, "clockwise");
            this.setVelocity();
        }
    }

    @Override
    protected void interactTree(storage tree){
        if (!this.carrying) {
            if (tree.getNumFruit() >= 1) {
                if (tree.getActorType().equals("Tree"))
                    tree.setNumFruit(tree.getNumFruit() - 1);
                this.carrying = true;
            }
        }
    }

}
