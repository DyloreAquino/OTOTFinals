public class LevelMaker {
    // In this Object will be the logic for random arrangement of blobs
    // In this Object will also be the random arrangement of walls
    private int phase;

    public LevelMaker(int phase){
        this.phase = phase;
        setUpLevel();
    }

    public void setUpLevel(){
        switch (phase) {
            case 0:
                break;
            case 1:
                new Wall(10, 10);
                break;
            default:
                break;
        }
    }
}
