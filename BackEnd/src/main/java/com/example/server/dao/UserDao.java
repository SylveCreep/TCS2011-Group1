package com.example.server.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

import com.example.server.entity.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findById(int id);

    @Query("Select u from User u "+
    "where u.is_deleted = 0 "+
    "group by u.id ")
    List<User> getNonDeletedUser(Pageable pageable);

    @Query("Select u from User u "+
    "left join u.faculty f "+ 
    "left join u.role r "+ 
    "where u.is_deleted = 0 "+ 
    "and (:userId = 0 or u.id = :userId) " + 
    "and (:roleId = 0 or r.id = :roleId) " + 
    "and (:facultyId = 0 or f.id = :facultyId) " +
    "group by u.id")
    List<User> searchUserByRoleAndFac(@Param("userId") int userId,@Param("roleId") int roleId, @Param("facultyId") int facultyId ,Pageable pageable);
}
