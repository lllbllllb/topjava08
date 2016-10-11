package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;

import java.util.List;

/**
 * gkislin
 * 02.10.2016
 */
@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id")int id);

    @Override
    @Transactional
    User save(User user);

//    @Override
//    User findOne(Integer id);

    @Override
    @Transactional
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.mealList LEFT JOIN FETCH u.roles WHERE u.id=:id")
    User findOne(@Param("id") Integer id);

    @Override
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles ORDER BY u.name, u.email")
    List<User> findAll();

    User getByEmail(String email);


}
