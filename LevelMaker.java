import java.util.*;

public class LevelMaker {
    // In this Object will be the logic for random arrangement of blobs
    // In this Object will also be the random arrangement of walls
    private int phase; 

    private Floor floor;

    public LevelMaker(int phase){
        this.phase = phase;
    }

    public void setUpFloor(ArrayList<Drawable> drawables){
        for (int row = 1; row <= 10; row++){
            for (int column = 1; column <= 16; column++){
                floor = new Floor(column, row);
                drawables.add(floor);
            }
        }
    }
}
