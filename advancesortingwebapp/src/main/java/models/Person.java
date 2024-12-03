package models;

public class Person {
    String name;
    int age;
    int id;
    public Person(String name, int age, int id) {
        this.age = age;
        this.name = name;
        this.id = id;
    }

    public int getAge() {
        return age;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
}