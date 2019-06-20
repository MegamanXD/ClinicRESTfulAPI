package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CoT on 11/18/17.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //implement the hibernate access here

        List<model.User> users = userService.getUserByName(s);
        model.User modelUser = users.get(0);

        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        for(model.User user: users){
            GrantedAuthority role = new GrantedAuthority() {
                public String getAuthority() {
                    return "ROLE_ADMIN";
                }
            };
            list.add(role);
        }


        User user = new User(s, modelUser.getPassword(),
                true, true, true,
                true, list );


        return user;

    }
}
