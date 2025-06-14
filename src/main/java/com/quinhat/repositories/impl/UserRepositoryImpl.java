package com.quinhat.repositories.impl;

import com.quinhat.dto.AdminUserDTO;
import com.quinhat.pojo.User;
import com.quinhat.repositories.UserRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.quinhat.mapper.AdminUserMapper;
import jakarta.persistence.NoResultException;

import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.query.MutationQuery;

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
    public List<AdminUserDTO> getAllUsers() {
        Session s = this.factory.getObject().getCurrentSession();
        List<User> users = s.createQuery("FROM User u", User.class).getResultList();
        return users.stream()
                .map(AdminUserMapper::toDTO)
                .toList();
    }

    private boolean isPlainPassword(String password) {
        return password != null && !password.startsWith("$2a$") && !password.startsWith("$2b$");
    }

    @Override
    public void save(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(u);
    }

    @Override
    public void delete(List<Integer> ids) {
        Session session = this.factory.getObject().getCurrentSession();
        MutationQuery query = session.createMutationQuery("DELETE FROM User u WHERE u.id IN :ids");
        query.setParameter("ids", ids);
        query.executeUpdate();
    }

    @Override
    public List<AdminUserDTO> getUsersPaginated(int page, int size) {
        Session s = this.factory.getObject().getCurrentSession();
        List<User> users = s.createQuery("FROM User u", User.class)
                .setFirstResult(page * size) // offset
                .setMaxResults(size) // limit
                .getResultList();
        return users.stream()
                .map(AdminUserMapper::toDTO)
                .toList();
    }

    @Override
    public long countUsers() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("SELECT COUNT(u.id) FROM User u", Long.class)
                .getSingleResult();
    }

    @Override
    public User getUserByEmail(String email) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            return s.createQuery("FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        Session s = this.factory.getObject().getCurrentSession();

        user.setPassword(newPassword);

        s.merge(user);
    }

    @Override
    public void update(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.merge(u);
    }

    @Override
    public User findById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        User user = s.get(User.class, id);  // Lấy entity từ DB
        if (user == null) {
            return null;
        }
        return user;
    }

}
