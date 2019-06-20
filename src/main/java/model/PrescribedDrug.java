package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "prescribed_drug")
public class PrescribedDrug{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne @JsonIgnore private Prescription prescription;
    @Column(name = "drug_name") private String drugName;
    @Column private byte quantity;
    @Column private String dose;
    @Column(name = "how_to_use") private String howToUse;

    //Setters
    public void setPrescription(Prescription prescription) { this.prescription = prescription; }
    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }
    public void setDose(String dose) {
        this.dose = dose;
    }
    public void setHowToUse(String howToUse) {
        this.howToUse = howToUse;
    }
    public void setQuantity(byte quantity) {
        this.quantity = quantity;
    }

    //Getters
    public Prescription getPrescription() { return prescription; }
    public String getDrugName() {
        return drugName;
    }
    public String getDose() {
        return dose;
    }
    public String getHowToUse() {
        return howToUse;
    }
    public byte getQuantity() {
        return quantity;
    }

    //toString
    @Override
    public String toString() {
        return "Name: " + drugName + ", Quantity: " + quantity + ", Dose: " + dose + ", How To Use: " + howToUse +
                " ||   Prescription ID: " + (prescription==null?"null":prescription.getId());
    }
}

