package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Prescription{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE) private Visit visit;
    @Column private String diagnosis;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PrescribedDrug> drugs = new ArrayList<PrescribedDrug>();

    //Setters
    public void setId(int id) { this.id = id; }
    public void setVisit(Visit visit) {
        this.visit = visit;
    }
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    public void addMedicine(PrescribedDrug drug) { this.drugs.add(drug);}
    public void setDrugs(List<PrescribedDrug> drugs) {
        this.drugs = drugs;
    }

    //Getters
    public int getId() { return id; }
    public Visit getVisit() {
        return visit;
    }
    public String getDiagnosis() {
        return diagnosis;
    }
    public List<PrescribedDrug> getPrescribedDrug() {
        return drugs;
    }

    //toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("                                                    ***Background***" +
                "\n---------------------------------------------------------------------------------------------------------------\n"
                + visit + "\nDiagnosis:                          " + diagnosis + "\n---------------------------------------------------------------------------------------------------------------\n");

                sb.append("                                                      ***Medicine***\n");
        if (drugs.size() == 0)
            sb.append("It's empty. Almost too empty ... \n");
        else {
            for (PrescribedDrug prescribedDrug : drugs) {
                sb.append(prescribedDrug + "\n");
            }
        }
        return  sb.toString();
    }
}
