package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table
public class Visit{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE) private Patient patient;
    @Temporal(TemporalType.DATE) private Date date;
    @Column private String problems;
    @Column private String labTest;

    //Make Date more intuitive
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    //Setters
    public void setDate(String date) {
        try {
            this.date = dateFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("Input: invalid!<br>");
        }
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setLabTest(String labTest) {
        this.labTest = labTest;
    }

    //Getters
    public int getId() {
        return id;
    }
    public Date getDate() {
        return date;
    }
    public Patient getPatient() {
        return patient;
    }
    public String getProblems() {
        return problems;
    }
    public String getLabTest() {
        return labTest;
    }

    //toString
    @Override
    public String toString() {
        return date==null?null:patient + "\nVisit\t\tID: " + id + ", Date: " + dateFormat.format(date) + " ||    Problems: " +
                problems + ", Lab test: " + labTest;
    }
}

