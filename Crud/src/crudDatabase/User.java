package crudDatabase;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty email;
    private final IntegerProperty age;

    public User(int id, String name, String email, int age) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.age = new SimpleIntegerProperty(age);
    }

    // Getters for properties
    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getEmail() {
        return email.get();
    }

    public int getAge() {
        return age.get();
    }

    // Property getters
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public IntegerProperty ageProperty() {
        return age;
    }
    
        // Setter methods for properties
    public void setName(String name) {
        this.name.set(name);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setAge(int age) {
        this.age.set(age);
    }

 
}
