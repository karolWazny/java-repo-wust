package engine;

public class Map {
    private int[][] cells;
    private final int height;
    private final int width;

    public Map(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int[][] getCells() {
        return cells;
    }

    public void setCells(int[][] cells) {
        this.cells = cells;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
