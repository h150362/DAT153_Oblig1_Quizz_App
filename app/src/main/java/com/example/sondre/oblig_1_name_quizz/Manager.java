package com.example.sondre.oblig_1_name_quizz;

import java.util.ArrayList;
import java.util.List;


public class Manager {


    private static Manager instance;
    public static Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }

        return instance;
    }


    private List<Person> people;


    public Manager() {
        this.people = new ArrayList<Person>();


    }

    public void savePerson(Person person){

    }

    public List<Person> retrievePeople(){
        List<Person> data;
        return null;
    }
    public List<Person> getPeople() {
        return this.people;
    }

}
