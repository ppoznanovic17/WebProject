package project.city;

import java.io.Serializable;

public class City implements Serializable {

    private int id;
    private String name;

    public City(String name){
        this.name = name;

    }

    public City() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
