package model;

import javax.persistence.*;

@Entity
@Table(name="available_drugs")
public class AvailableDrug {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column private String name;

    //Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    //toString
    @Override
    public String toString() {
        return "AvailableDrug{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
