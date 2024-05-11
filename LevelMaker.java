import java.util.*;

public class LevelMaker {
    // In this Object will be the logic for random arrangement of blobs
    // In this Object will also be the random arrangement of walls
    private int phase; 

    private Floor floor;
    private Wall wall;

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

    public void setUpLevel(int level, ArrayList<Drawable> drawables, ArrayList<Wall> walls){
        setUpWalls(level, drawables, walls);
    }

    private void setUpWalls(int level, ArrayList<Drawable> drawables, ArrayList<Wall> walls){
        int wallLevel1[] = 
        {
            0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0,
            1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0,
            1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1,
            0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0,
            1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1,
            1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1,
            0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0,
            1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0
        };

        int columnCounter = 1;
        int rowCounter = 1;
        for (int i: wallLevel1) {
            if (columnCounter > 16){
                columnCounter = 1;
                rowCounter++;
            }
            if (i == 1){
                wall = new Wall(columnCounter, rowCounter);
                drawables.add(wall);
                walls.add(wall);
            }
            columnCounter++;
        }
    }

}
