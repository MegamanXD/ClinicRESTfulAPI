package service;

import model.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by CoT on 10/14/17.
 */
@Transactional
@Service
public class UserService {

    @Autowired
    private SessionFactory sessionFactory;

    public List<User> getUserByName(String username){
        Query query = sessionFactory.getCurrentSession().createQuery("from User where name=:username")
                .setString("username", username);
        return query.list();
    }
}
