package model;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table
public class Patient{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column private String name;
    @Column private String address;
    @Column private String gender;
    @Temporal(TemporalType.DATE) private Date birthdate;

    //Make Date more intuitive
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    //Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }

    public void setBirthdate(String birthdate) {
        try {
            this.birthdate = dateFormat.parse(birthdate);
        } catch (ParseException e) {
            System.out.println("Input: invalid!");
        }
    }

    public void setGender(String gender) {
        if (gender.equalsIgnoreCase("female") || gender.equalsIgnoreCase("male"))
            this.gender = gender;
        else{
            System.out.println("Wrong gender inputted. Set gender to: null");
            this.gender = null;
        }
    }

    //Getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public Date getBirthdate() {
        return birthdate;
    }
    public String getGender() {
        return gender;
    }

    //toString
    @Override
    public String toString() {
        return birthdate==null ?null:"Patient\t\tID " + id + ", Name: " + name + ", Birthdate: " + dateFormat.format(birthdate) +
                " ||    Address: " + address + ", Gender: " + gender;
    }
}
