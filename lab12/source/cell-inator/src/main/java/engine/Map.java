package engine;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

@Builder
@AllArgsConstructor
public class Map {
    private int[][] cells;
    private final int height;
    private final int width;
    private final String engineName;

    public Map(int height, int width, String engineName) {
        this.engineName = engineName;
        this.height = height;
        this.width = width;
        cells = IntStream.range(0, height)
                .mapToObj(index -> new int[width])
                .toArray(int[][]::new);
    }

    public int[][] getNeighbourhood(Position position){
        return IntStream.range(-1, 2)
                .mapToObj(i -> IntStream.range(-1,2)
                        .map(j->{
                            int x = position.x + j >= 0 ? (position.x + j < width ? position.x + j : 0) : width - 1;
                            int y = position.y + i >= 0 ? (position.y + i < height ? position.y + i : 0) : height - 1;
                            return cells[y][x];
                        }).toArray())
                .toArray(int[][]::new);
    }

    public void setState(Position position, int state){
        cells[position.y][position.x] = state;
    }

    public int getState(Position position){
        return getState(position.x, position.y);
    }

    public  int getState(int x, int y){
        return cells[y][x];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setCells(int[][] cells) {
        if(Objects.requireNonNull(cells).length != height)
            throw new RuntimeException("Wrong map dimension!");
        Arrays.stream(cells)
                .forEach(row->{
                    if(Objects.requireNonNull(row).length != width)
                        throw new RuntimeException("Wrong map dimension!");
                });
        this.cells = cells;
    }

    public int[][] getCells() {
        return cells;
    }

    public String getEngineName() {
        return engineName;
    }
}
