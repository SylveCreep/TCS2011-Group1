package com.example.server.dao;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.server.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
        User findByEmail(String email);

        Optional<User> findById(Long id);

        User findUserById(Long id);

        @Query("Select u from User u " + "where u.is_deleted = 0 " + "group by u.id ")
        Page<User> getNonDeletedUser(Pageable pageable);

        @Query(value = "Select * from user u " + "LEFT JOIN faculty f ON f.id = u.faculty_id "
                        + "LEFT JOIN role r ON r.id = u.role_id " + "where u.is_deleted = 0 " +
                        // "AND f.is_deleted = 0 "+
                        "AND r.is_deleted = 0 "
                        + "AND ((:fullName IS NULL) OR LOWER(u.full_name) LIKE CONCAT('%',IFNULL(LOWER(:fullName),LOWER(u.full_name)),'%')) "
                        + "AND ((:email IS NULL) OR LOWER(u.email) LIKE CONCAT('%',IFNULL(LOWER(:email),LOWER(u.email)),'%')) "
                        + "AND ((:hasDate = 0) OR (u.date_of_birth BETWEEN :startDate AND :endDate)) "
                        + "AND ((:roleName IS NULL) OR LOWER(r.name) LIKE CONCAT('%',IFNULL(LOWER(:roleName),LOWER(r.name)),'%')) "
                        + "AND ((:facultyName IS NULL) OR LOWER(f.faculty_name) LIKE CONCAT('%',IFNULL(LOWER(:facultyName),LOWER(f.faculty_name)),'%')) "
                        + "AND ((:code IS NULL) OR LOWER(u.code) LIKE CONCAT('%',IFNULL(LOWER(:code),LOWER(u.code)),'%')) "
                        + "AND ((:gender is null) or (u.gender = :gender)) "
                        + "AND ((:userId is null) or (u.id = :userId)) "
                        + "AND ((:roleId is null) or (r.id = :roleId)) "
                        + "AND ((:facultyId is null) or (f.id = :facultyId)) " + "group by u.id", nativeQuery = true)
        Page<User> searchUserByRoleAndFac(@Param("userId") Long userId, @Param("roleId") Long roleId,
                        @Param("facultyId") Long facultyId, @Param("fullName") String fullName,
                        @Param("roleName") String roleName, @Param("facultyName") String facultyName,
                        @Param("email") String email, @Param("startDate") Date startDate,
                        @Param("endDate") Date endDate, @Param("hasDate") int hasDate, @Param("gender") Integer gender,
                        @Param("code") String code, Pageable pageable);

        @Query(value = "Select * FROM user u " + "LEFT JOIN faculty f ON f.id = u.faculty_id "
                        + "WHERE u.is_deleted = 0 "
                        + "AND ((f.manager_id IS NULL) OR (u.id <> f.manager_id AND f.is_deleted = 0)) ", nativeQuery = true)
        List<User> searchUserNotIsManager();

        @Query(value = "Select u.id FROM user u " + "LEFT JOIN role r ON r.id = u.role_id " + "Where u.is_deleted = 0 "
                        + "AND u.role_id = r.id " + "AND r.name = 'Marketing Coordinator' ", nativeQuery = true)
        List<Long> searchMC();

        @Query(value = "Select * FROM user u " + "Where  ((:userId is null) or (u.id = :userId))"
                        + "AND u.is_deleted = 0 ", nativeQuery = true)
        User findByUserId(@Param("userId") Long userId);

        @Query(value = "SELECT u.* FROM user u WHERE u.is_deleted = 0 AND u.id = :id ", nativeQuery = true)
        User findExistedUserById(Long id);

        @Query(value = "SELECT u.* FROM user u WHERE u.is_deleted = 0 AND u.email = :email ", nativeQuery = true)
        User findExistedUserByEmail(String email);

        @Query(value = "SELECT u.* FROM user u WHERE u.is_deleted = 0 AND u.current_session = :session ", nativeQuery = true)
        User findExistedUserByCurrenSession(String session);

        @Query(value = "SELECT u.* FROM user u " + "WHERE u.is_deleted = 0 " + "AND u.role_id = :roleId "
                        + "AND ((:facultyId IS NULL) OR u.faculty_id = :facultyId)", nativeQuery = true)
        User findUserManagerByFacultyIdAndRoleId(@Param("facultyId") Long facultyId, @Param("roleId") Long roleId);

        @Query(value = "SELECT u.* FROM user u " + "WHERE u.is_deleted = 0 " + "AND u.role_id = :roleId "
                        + "AND u.faculty_id IS NOT NULL ", nativeQuery = true)
        List<User> findMCByRoleId(@Param("roleId") Long roleId);

        @Query(value = "SELECT u.* FROM user u WHERE u.is_deleted = 0 AND u.reset_password_key = :key ", nativeQuery = true)
        User findExistedUserByPasswordKey(String key);

        @Query(value = "SELECT COUNT(u.id) FROM user u WHERE u.is_deleted = 0 AND u.role_id = 4 ", nativeQuery = true)
        Long countExistedStudent();

        @Query(value = "SELECT u.code as code, u.id as userId, u.full_name as fullName, u.background_avatar as avatar, f.faculty_name as facultyName, f.id as facultyId, COUNT(c.id) as total "
                        + "FROM user u " + "INNER JOIN contribution c ON u.id =  c.user_id "
                        + "LEFT JOIN faculty f ON f.id = u.faculty_id " + "WHERE u.is_deleted = 0 "
                        + "AND c.is_deleted = 0 " + "AND u.role_id = 4 "
                        + "AND ((:magazineId IS NULL ) OR c.magazine_id = :magazineId )" + "GROUP BY u.id "
                        + "ORDER BY total desc " + "limit :limit ", nativeQuery = true)
        List<Object> getTopUserWithMostContributions(@Param("magazineId") Long magazineId,
                        @Param("limit") Integer limit);

}
