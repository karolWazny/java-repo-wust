package engine;

public class Engine {
    private Map map;

    public Engine(Map map) {
        this.map = map;
    }

    public String engineName(){
        return "engine";
    }
    public void step(){}
    public Map getMap(){
        return map;
    }
}
