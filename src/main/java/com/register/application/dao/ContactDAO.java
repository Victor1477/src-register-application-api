package com.register.application.dao;

import com.register.application.entity.Contact;
import com.register.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactDAO extends JpaRepository<Contact, Integer> {

    @Query("SELECT c FROM Contact as c WHERE user = :user")
    public List<Contact> findAllByUser(@Param("user") User user);

    @Query("SELECT c FROM Contact as c WHERE c.id=:id AND user=:user")
    public Contact findByUser(@Param("id") Integer id, @Param("user") User user);
}
