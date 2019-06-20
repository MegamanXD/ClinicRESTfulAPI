package service;

import model.PrescribedDrug;
import model.Prescription;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class PrescribedDrugService {
    //Attach a Session to the Transaction
    @Autowired
    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //Add
    public void savePrescribedDrug(PrescribedDrug prescribedDrug){ sessionFactory.getCurrentSession().save(prescribedDrug); }

    //Delete
    public void deletePrescribedDrug(PrescribedDrug prescribedDrug){ sessionFactory.getCurrentSession().delete(prescribedDrug); }

    //Update
    public void updatePrescribedDrug(PrescribedDrug prescribedDrug){ sessionFactory.getCurrentSession().update(prescribedDrug); }

    //Get All
    public List<PrescribedDrug> getAllPrescribedDrug(){ return  (List<PrescribedDrug>) sessionFactory.getCurrentSession().createQuery("FROM PrescribedDrug").list();}

    //Get List of Prescribed Drugs by Prescriptions
    public List<PrescribedDrug> getPrescribedDrugByPrescription(Prescription prescription){
        Query query = sessionFactory.getCurrentSession().createQuery("FROM PrescribedDrug WHERE prescription.id=:prescriptionID")
                .setInteger("prescriptionID", prescription.getId());
        return (List<PrescribedDrug>) query.list();
    }
}
