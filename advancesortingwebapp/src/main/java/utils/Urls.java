package utils;

public class Urls {

    public static String[] addPerson = {"http://localhost:8080/advancesortingwebapp/person", "Add Person", "POST"} ;
    public static String[] updatePerson =   {"http://localhost:8080/advancesortingwebapp/person", "updates a person, requires the person id", "PUT"};
    public static String[] getPersons =   {"http://localhost:8080/advancesortingwebapp/person", "retrieves all person in the added in the database", "GET"};
    public static String[] getPerson =   {"http://localhost:8080/advancesortingwebapp/person", "retrieves a person with id", "GET"};
    public static String[] deletePerosn = {"http://localhost:8080/advancesortingwebapp/person", "delete a person with id", "DELETE"};
}