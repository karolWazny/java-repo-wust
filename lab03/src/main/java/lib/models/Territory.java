package lib.models;

public class Territory {
    private String name;
    private String geonameCode;

    public Territory(String name, String geonameCode) {
        this.name = name;
        this.geonameCode = geonameCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeonameCode() {
        return geonameCode;
    }

    public void setGeonameCode(String geonameCode) {
        this.geonameCode = geonameCode;
    }

    @Override
    public String toString(){
        return name;
    }
}
