package com.example.server.dao;

import java.util.Date;
import java.util.Optional;

import com.example.server.entity.Magazine;
import com.example.server.model.response.MagazineResponse;

import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface MagazineDao extends JpaRepository<Magazine, Long> {
    Magazine findByTheme(String theme);

    Magazine findByFinishedAt(Date finishedAt);

    Magazine findByPublishedAt(Date published_at);

    Magazine findByCloseAt(Date close_at);

    Magazine findByCode(String code);

    Optional<Magazine> findById(Long id);

    @Query(value = "SELECT m.* FROM magazine m WHERE m.is_deleted = 0 AND m.id = :id ", nativeQuery = true)
    Magazine findExistedMagazineById(Long id);

    @Query("select m from Magazine m " + "where m.is_deleted = 0 " + "group by m.id")
    Page<Magazine> getNonDelRole(Pageable pageable);

    @Query(value = "Select * " + "FROM magazine m WHERE m.is_deleted = 0 "
            + "AND ((:theme IS NULL) OR LOWER(m.theme) LIKE CONCAT('%',IFNULL(LOWER(:theme), LOWER(m.theme)), '%')) "
            + "AND ((:code IS NULL) OR LOWER (m.code) LIKE CONCAT('%',IFNULL(LOWER(:code),LOWER(m.code)),'%')) "
            + "AND ((:id IS NULL) OR (m.id = :id))"
            + "AND  ((:currDate = 0) OR (:currDate BETWEEN m.created_at AND m.finished_at)) "
            + "group by m.id", nativeQuery = true)
    Page<Magazine> searchMagazineOpening(@Param("id") Long id, @Param("theme") String theme,
            @Param("code") String code/*
                                       * , @Param("openAt") Date openAt, @Param("publishedAt") Date
                                       * publishedAt, @Param("closeAt") Date closeAt
                                       */, @Param("currDate") Date currDate, Pageable pageable);

    @Query(value = "Select * " + "FROM magazine m WHERE m.is_deleted = 0 "
            + "AND ((:theme IS NULL) OR LOWER(m.theme) LIKE CONCAT('%',IFNULL(LOWER(:theme), LOWER(m.theme)), '%')) "
            + "AND ((:code IS NULL) OR LOWER (m.code) LIKE CONCAT('%',IFNULL(LOWER(:code),LOWER(m.code)),'%')) "
            + "AND ((:id IS NULL) OR (m.id = :id))"
            + "AND  ((:currDate = 0) OR (:currDate BETWEEN m.finished_at AND m.published_at)) "
            + "group by m.id", nativeQuery = true)
    Page<Magazine> searchMagazineProcessing(@Param("id") Long id, @Param("theme") String theme,
            @Param("code") String code/*
                                       * , @Param("openAt") Date openAt, @Param("publishedAt") Date
                                       * publishedAt, @Param("closeAt") Date closeAt
                                       */, @Param("currDate") Date currDate, Pageable pageable);

    @Query(value = "Select * " + "FROM magazine m WHERE m.is_deleted = 0 "
            + "AND ((:theme IS NULL) OR LOWER(m.theme) LIKE CONCAT('%',IFNULL(LOWER(:theme), LOWER(m.theme)), '%')) "
            + "AND ((:code IS NULL) OR LOWER (m.code) LIKE CONCAT('%',IFNULL(LOWER(:code),LOWER(m.code)),'%')) "
            + "AND ((:id IS NULL) OR (m.id = :id))"
            + "AND  ((:currDate = 0) OR (:currDate BETWEEN m.published_at AND m.close_at)) "
            + "group by m.id", nativeQuery = true)
    Page<Magazine> searchMagazinePublishing(@Param("id") Long id, @Param("theme") String theme,
            @Param("code") String code/*
                                       * , @Param("openAt") Date openAt, @Param("publishedAt") Date
                                       * publishedAt, @Param("closeAt") Date closeAt
                                       */, @Param("currDate") Date currDate, Pageable pageable);

    @Query(value = "Select * " + "FROM magazine m WHERE m.is_deleted = 0 "
            + "AND ((:theme IS NULL) OR LOWER(m.theme) LIKE CONCAT('%',IFNULL(LOWER(:theme), LOWER(m.theme)), '%')) "
            + "AND ((:code IS NULL) OR LOWER (m.code) LIKE CONCAT('%',IFNULL(LOWER(:code),LOWER(m.code)),'%')) "
            + "AND ((:id IS NULL) OR (m.id = :id))" + "AND  ((:currDate = 0) OR (:currDate > m.close_at)) "
            + "group by m.id", nativeQuery = true)
    Page<Magazine> searchMagazineClosing(@Param("id") Long id, @Param("theme") String theme,
            @Param("code") String code/*
                                       * , @Param("openAt") Date openAt, @Param("publishedAt") Date
                                       * publishedAt, @Param("closeAt") Date closeAt
                                       */, @Param("currDate") Date currDate, Pageable pageable);

    @Query(value = "Select * FROM magazine m " + "Where  m.id = :mId", nativeQuery = true)
    Magazine findMagazineById(@Param("mId") Long id);

    @Query(value = "SELECT COUNT(m.id) FROM magazine m WHERE m.is_deleted = 0 ", nativeQuery = true)
    Long countMagazine();
}
