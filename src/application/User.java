package application;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
	private final SimpleIntegerProperty id;
    private final SimpleStringProperty nom;
    private final SimpleStringProperty prenom;
    private final SimpleIntegerProperty age;
    private final SimpleStringProperty job;
    private final SimpleIntegerProperty salary;
    private final SimpleBooleanProperty selected;
    
    public User(int id,String nom, String prenom, int age, String job, int salary) {
        this.id = new SimpleIntegerProperty(id);
		this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.age = new SimpleIntegerProperty(age);
        this.job = new SimpleStringProperty(job);
        this.salary = new SimpleIntegerProperty(salary);
        this.selected = new SimpleBooleanProperty(false);
    }
    
    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }
    
    
    public boolean isSelected() {
        return selected.get();
    }

    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public String getNom() {
        return nom.get();
    }

    public String getPrenom() {
        return prenom.get();
    }

    public int getAge() {
        return age.get();
    }

    public String getJob() {
        return job.get();
    }

    public int getSalary() {
        return salary.get();
    }

    

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public SimpleStringProperty prenomProperty() {
        return prenom;
    }

    public SimpleIntegerProperty ageProperty() {
        return age;
    }

    public SimpleStringProperty jobProperty() {
        return job;
    }

    public SimpleIntegerProperty salaryProperty() {
        return salary;
    }

   
 // Add setters for each property
    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public void setJob(String job) {
        this.job.set(job);
    }

    public void setSalary(int salary) {
        this.salary.set(salary);
    }

    
}