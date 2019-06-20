package service;

import model.Visit;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Service
public class VisitService {
    //Attach a Session to the Transaction
    @Autowired
    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //Add
    public int saveVisit(Visit visit){
        sessionFactory.getCurrentSession().save(visit);
        return visit.getId();
    }

    //Delete
    public void deleteVisit(Visit visit){ sessionFactory.getCurrentSession().delete(visit); }

    //Update
    public int updateVisit(Visit visit){
        sessionFactory.getCurrentSession().update(visit);
        return visit.getId();
    }

    //Get All
    public List<Visit> getAllVisit(){ return  (List<Visit>) sessionFactory.getCurrentSession().createQuery("FROM Visit").list();}

    //Get by ID
    public Visit getVisitByID(int id){
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Visit WHERE id=:visitID")
                .setInteger("visitID",id);
        return  (Visit) query.uniqueResult();
    }

    //Get Visits by Days, Patient
    public List<Visit> getVisitsBetween2Dates(Date startDate, Date endDate){
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Visit WHERE date>=:startDate AND date<=:endDate")
                .setDate("startDate",startDate).setDate("endDate",endDate);
        return  (List<Visit>) query.list();
    }

    public List<Visit> getVisitsByDate(Date date){
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Visit WHERE date=:dateToSearch")
                .setDate("dateToSearch",date);
        return  (List<Visit>) query.list();
    }

    public List<Visit> getVisitByPatient(int id){
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Visit WHERE patient.id=:patientID")
                .setInteger("patientID",id);
        return  (List<Visit>) query.list();
    }

    //Auto-update Visit after deleting a Patient
    public void autoDeleteVisit(int patientID){
        List<Visit> toBeDeleted = getVisitByPatient(patientID);
        for (Visit visit : toBeDeleted) {
            deleteVisit(visit);
        }
    }

}
