package engine;

import java.awt.*;
import java.util.stream.IntStream;

public class Engine {
    private Map map;

    public Engine(Map map) {
        this.map = map;
    }

    public String engineName(){
        return "engine";
    }

    public void step(){
        int[][] nextState = IntStream.range(0, map.getHeight())
                .mapToObj(y->{
                    return IntStream.range(0, map.getWidth())
                            .map(x->{
                                int[][] neighbourhood = map.getNeighbourhood(new Position(x, y));
                                return nextState(neighbourhood[1][1]);
                            })
                            .toArray();
                }).toArray(int[][]::new);
        map.setCells(nextState);
    }

    public Map getMap(){
        return map;
    }

    public void changeStateOnPosition(Position position){
        int currentState = map.getState(position);
        map.setState(position, nextState(currentState));
    }

    public int nextState(int state){
        return (state + 1) % 2;
    }

    public Color colorOnPosition(Position position){
        return colorForState(map.getState(position));
    }

    public Color colorForState(int state){
        switch (state){
            case 0:
                return Color.CYAN;
            case 1:
                return Color.ORANGE;
        }
        return Color.WHITE;
    }
}
