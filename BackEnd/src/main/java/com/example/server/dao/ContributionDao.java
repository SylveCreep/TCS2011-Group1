package com.example.server.dao;

import java.util.Date;
import java.util.List;

import com.example.server.entity.Contribution;
import com.example.server.model.response.ContributionResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionDao extends JpaRepository<Contribution, Long> {

        @Query(value = "SELECT c.* FROM contribution c WHERE c.is_deleted = 0 AND c.id = :id ", nativeQuery = true)
        Contribution findExistedContributionById(Long id);

        @Query(value = "SELECT c.* FROM contribution c " + "LEFT JOIN user u ON u.id = c.user_id "
                        + "WHERE (:code IS NULL OR lower(c.code) LIKE CONCAT('%',lower(:code),'%')) "
                        + "AND (:studentName IS NULL OR lower(u.full_name) LIKE CONCAT('%',lower(:studentName),'%')) "
                        + "AND (:facultyId IS NULL OR c.faculty_id = :facultyId ) "
                        + "AND (:studentId IS NULL OR c.user_id = :studentId ) "
                        + "AND (:hasDate = 0 OR CAST(c.created_at as date) = CAST(:submitDate as date) )"
                        + "AND (:magazineId IS NULL OR c.magazine_id = :magazineId )" + "AND c.is_approved = :status "
                        + "AND c.is_deleted = 0 ", nativeQuery = true)
        Page<Contribution> getContributionList(@Param("code") String code, @Param("studentId") Long studentId,
                        @Param("studentName") String studentName, @Param("facultyId") Long facultyId,
                        @Param("magazineId") Long magazineId, @Param("submitDate") Date submitDate,
                        @Param("hasDate") int hasDate, @Param("status") Integer status, Pageable pageable);

        @Query(value = "SELECT c.* FROM contribution c " + "LEFT JOIN user u ON u.id = c.user_id "
                        + "LEFT JOIN magazine m ON m.id = c.magazine_id " + "WHERE ((:type = 1) OR u.id = :id) "
                        + "AND ((:type = 0) OR m.id = :id) " + "AND c.is_deleted = 0 "
                        + "AND u.is_deleted = 0", nativeQuery = true)
        List<Contribution> getExistedContributionsByUserId(Long id, int type);

        @Query(value = "SELECT c.* FROM contribution c " + "WHERE c.is_deleted = 0 " + "AND c.is_approved = 0 "
                        + "AND c.id NOT IN (SELECT c1.id FROM contribution c1 "
                        + "LEFT JOIN comment cm ON cm.contribution_id = c1.id " + "WHERE cm.is_deleted = 0 " + ") "
                        + "AND (CURDATE() BETWEEN DATE_ADD(c.created_at, INTERVAL 10 DAY ) AND DATE_ADD(c.created_at, INTERVAL 14 DAY )) "
                        + "AND c.expire_notify = 0 ", nativeQuery = true)
        List<Contribution> getContributionHasNoComment();

        @Query(value = "SELECT COUNT(c.id) FROM contribution c " + "WHERE c.is_deleted = 0 "
                        + "AND (:userId IS NULL OR c.user_id = :userId ) ", nativeQuery = true)
        Long countContributionByUserId(@Param("userId") Long userId);

        @Query(value= "SELECT COUNT(c.id) FROM contribution c "+ 
        "WHERE c.is_deleted = 0 "+ 
        "AND (:magazineId IS NULL OR c.magazine_id = :magazineId ) ", nativeQuery = true)
        Long countContributionByMagazineId(@Param("magazineId")Long magazineId);
}
