//using map for storage
package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import models.Person;

public class Storage {

     static int count = 0;
     static HashMap<Integer, Person> persons = new HashMap<>();

    public static int addPerson(String name, int age) {
        count += 1;
        persons.put(count, new Person(name, age, count));
        return count;
     }

    public static Optional<Person> findPersonByName(String name) {
        return persons.values().stream().filter(s -> s.getName().equals(name)).findFirst();
    }

    public static Person findPersonById(int id) {
        return persons.get(id);
    }


   public static List<Person> getAllPerson() {
    return new ArrayList<>(persons.values());
   }

   public static Person deletePerson(int id) {
    return persons.remove(id);
   }
}
