package engine;

import javax.script.Invocable;
import javax.script.ScriptException;
import java.awt.*;
import java.util.stream.IntStream;

public class Engine {
    private Map map;
    private Invocable invocable;

    public Engine(Invocable invocable, Map map) {
        this.invocable = invocable;
        this.map = map;
    }

    public String engineName(){
        try {
            return (String) invocable.invokeFunction("engineName");
        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "engine";
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

    public int nextState(int state){
        return (state + 1) % 2;
    }

    private int stateFromNeighbourhood(int[][] neighbourhood){
        return (neighbourhood[1][1] + 1) % 2;
    }

    public void step(){
        int[][] nextState = IntStream.range(0, map.getHeight())
                .mapToObj(y->{
                    return IntStream.range(0, map.getWidth())
                            .map(x->{
                                int[][] neighbourhood = map.getNeighbourhood(new Position(x, y));
                                return stateFromNeighbourhood(neighbourhood);
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


    public Color colorOnPosition(Position position){
        return colorForState(map.getState(position));
    }
}
