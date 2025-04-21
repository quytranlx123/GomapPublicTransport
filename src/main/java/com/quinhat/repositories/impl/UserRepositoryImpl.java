package com.quinhat.repositories.impl;

import com.quinhat.pojo.User;
import com.quinhat.repositories.UserRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("User.findByUsername", User.class);
        q.setParameter("username", username);
        try {
            return (User) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User addUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(u);
        return u;
    }

    @Override
    public User updateUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.merge(u);
        return u;
    }

    @Override
    public boolean authenticate(String username, String password) {
        User u = this.getUserByUsername(username);
        return u != null && this.passwordEncoder.matches(password, u.getPassword());
    }

    @Override
    public boolean deleteUser(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            User user = s.get(User.class, id);
            if (user == null) {
                return false;
            }

            // Update UserNotification
            String hql1 = "UPDATE UserNotification un SET un.userId = null WHERE un.userId = :user";
            int updated1 = s.createQuery(hql1)
                    .setParameter("user", user)
                    .executeUpdate();

            System.out.println("Updated " + updated1 + " UserNotifications.");

            // Update TrafficReport
            String hql2 = "UPDATE TrafficReport tr SET tr.userId = null WHERE tr.userId = :user";
            int updated2 = s.createQuery(hql2)
                    .setParameter("user", user)
                    .executeUpdate();

            System.out.println("Updated " + updated2 + " TrafficReports.");

            // Xoá User
            s.delete(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public void save(User user) {
        Session s = this.factory.getObject().getCurrentSession();

        // Nếu password chưa mã hoá, hoặc người dùng nhập lại mới thì mã hoá
        if (user.getId() == null || isPlainPassword(user.getPassword())) {
            String password = user.getPassword();
            user.setEncryptedPassword(password);
        }

        if (user.getAvatar() == null || user.getAvatar().isEmpty()) {
            user.setAvatar(null); // Hoặc set URL mặc định
        }

        if (user.getId() == null) {
            s.persist(user);
        } else {
            s.merge(user);
        }
    }

    private boolean isPlainPassword(String password) {
        return password != null && !password.startsWith("$2a$") && !password.startsWith("$2b$");
    }

}
