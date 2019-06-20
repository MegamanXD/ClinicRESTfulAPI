package service;

import model.Patient;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Service
public class PatientService {
    //Attach a Session to the Transaction
    @Autowired
    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //Add
    public int savePatient(Patient patient){
        sessionFactory.getCurrentSession().saveOrUpdate(patient);
        return patient.getId();
    }

    //Delete
    public void deletePatient(Patient patient){
        sessionFactory.getCurrentSession().delete(patient);
    }

    //Update
    public int updatePatient(Patient patient){
        sessionFactory.getCurrentSession().update(patient);
        return patient.getId();
    }

    //Get All
    public List<Patient> getAllPatient(){ return sessionFactory.getCurrentSession().createQuery("FROM Patient").list();}

    //Get by ID
    public Patient getPatientByID(int id){
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Patient WHERE id=:patientID")
                .setInteger("patientID",id);
        return (Patient) query.uniqueResult();
    }

    //Get Patients by ID, Name, Birthdate (getByID for patient is already up there)
    public List<Patient> getPatientByName(String name){
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Patient WHERE name LIKE :patientName")
                .setString("patientName","%"+name+"%");
        return (List<Patient>) query.list();
    }

    public List<Patient> getPatientByBirthdate(Date date){
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Patient WHERE birthdate=:patientBirthdate")
                .setDate("patientBirthdate",date);
        return (List<Patient>) query.list();
    }
}
