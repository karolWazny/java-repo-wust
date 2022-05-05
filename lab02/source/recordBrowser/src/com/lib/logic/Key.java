package com.lib.logic;

public class Key {
    private String string;

    public Key(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String toString(){
        return string;
    }

    @Override
    public boolean equals(Object o){
        if(! (o instanceof Key))
            return false;
        return string.equals(((Key) o).string);
    }

    @Override
    public int hashCode(){
        return string.hashCode();
    }
}
