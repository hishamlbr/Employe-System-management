package application;

public class userAtt {
    private final int id;
    private final String name;
    private final String prenom;
    private final String date;

    public userAtt(int id, String name, String prenom, String date) {
        this.id = id;
        this.name = name;
        this.prenom = prenom;
        this.date = date;  // Use the existing Date object from the result set
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getDate() {
        return date;
    }
}