package service;

import model.Prescription;
import model.Visit;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class PrescriptionService {
    //Attach a Session to the Transaction
    @Autowired
    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    private PrescribedDrugService prescribedDrugService;

    @Autowired
    private VisitService visitService;

    //Add
    public int savePrescription(Prescription prescription){
        sessionFactory.getCurrentSession().save(prescription);
        return prescription.getId();
    }

    //Delete
    public void deletePrescription(Prescription prescription){ sessionFactory.getCurrentSession().delete(prescription); }

    //Update
    public int updatePrescription(Prescription prescription){
        sessionFactory.getCurrentSession().update(prescription);
        return prescription.getId();
    }

    //Get All
    public List<Prescription> getAllPrescription(){
        List<Prescription> prescriptionList = (List<Prescription>) sessionFactory.getCurrentSession().createQuery("FROM Prescription").list();
        return prescriptionList;
    }

    //Get by ID
    public Prescription getPrescriptionByID(int id){
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Prescription WHERE id=:prescriptionID")
                .setInteger("prescriptionID",id);
        return  (Prescription) query.uniqueResult();
    }

    //Get Prescription by Visit ID
    public List<Prescription> getPrescriptionByVisit(int visitID){
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Prescription WHERE visit.id=:visitID")
                .setInteger("visitID",visitID);
        return  (List<Prescription>) query.list();
    }

    //Get Prescription by Patient ID
    public List<Prescription> getPrescriptionByPatient(int id){
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Prescription WHERE visit.patient.id=:patientID")
                .setInteger("patientID",id);
        return  (List<Prescription>) query.list();
    }

    //Auto-update Prescription after deleting a Patient
    public void autoDeletePrescription(int patientID){
        List<Prescription> toBeDeleted = getPrescriptionByPatient(patientID);
        for (Prescription prescription : toBeDeleted) {
            deletePrescription(prescription);
        }
    }

    //Auto-update Prescription after deleting a Visit
    public void autoDeletePrescriptionByVisit(int visitID){
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Prescription WHERE visit.id=:visitID").setInteger("visitID",visitID);
        List<Prescription> toBeDeleted = query.list();
        for (Prescription prescription: toBeDeleted) {
            deletePrescription(prescription);
        }
    }
}
