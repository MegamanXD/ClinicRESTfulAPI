package service;

import model.AvailableDrug;
import model.ICD;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class OtherCrapService {
    //Attach a Session to the Transaction
    @Autowired
    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //Get All AvailableDrug
    public List<AvailableDrug> getAllAvailableDrugs(){ return sessionFactory.getCurrentSession().createQuery("FROM AvailableDrug").list();}

    //Get All ICD
    public List<ICD> getAllICDs(){ return sessionFactory.getCurrentSession().createQuery("FROM ICD").list();}
}
