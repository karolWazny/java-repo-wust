package engine;

import javax.script.Invocable;
import javax.script.ScriptException;
import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
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
        try {
            String color = (String) invocable.invokeFunction("colorForState", state);
            return Color.decode(color);
        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return Color.WHITE;
    }

    public int nextState(int state){
        try {
            return ((Double)invocable.invokeFunction("cycleThroughStates", state)).intValue();
        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int stateFromNeighbourhood(int[][] neighbourhood){
        try {
            int[] cells = new int[9];
            IntStream.range(0, 3)
                    .forEach(i -> IntStream.range(0, 3)
                            .forEach(j -> {
                                cells[i * 3 + j] = neighbourhood[i][j];
                            }));
            //System.out.println(Arrays.stream(cells).boxed().collect(Collectors.toList()));
            return (int) invocable.invokeFunction("evaluateState", (Object) cells);
        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void step(){
        int[][] nextState = IntStream.range(0, map.getHeight())
                .mapToObj(y-> IntStream.range(0, map.getWidth())
                        .map(x->{
                            int[][] neighbourhood = map.getNeighbourhood(new Position(x, y));
                            return stateFromNeighbourhood(neighbourhood);
                        })
                        .toArray()).toArray(int[][]::new);
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
