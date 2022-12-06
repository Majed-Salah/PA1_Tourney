package edu.ucdenver.tournament;

/**
 * The Player class contains variables relating to a players characteristics like name, age, height and weight. The class
 * only has getter functions for the attributes, and that is all that is needed in this class because we are simple keeping
 * a record of players for a Team and LineUp.
 * */
public class Player {
    private String name;
    private int age;
    private double height;
    private double weight;

    public Player(String name, int age, double height, double weight){
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    public String getName(){
        return this.name;
    }

    public int getAge(){
        return age;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }
}