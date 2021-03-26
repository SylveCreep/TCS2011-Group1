package com.example.server.dao;

import java.util.Date;
import java.util.Optional;

import com.example.server.entity.Magazine;

import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface MagazineDao extends JpaRepository<Magazine, Long> {
    Magazine findByTheme(String theme);

    Magazine findByOpenAt(Date open_at);

    Magazine findByPublishedAt(Date published_at);

    Magazine findByCloseAt(Date close_at);

    Magazine findByCode(String code);

    Optional<Magazine> findById(Long id);

    @Query("select m from Magazine m " +
    "where m.is_deleted = 0 " + 
    "group by m.id")
    Page<Magazine> getNonDelRole(Pageable pageable);

    /*@Query(value = "Select * FROM magazine m " +
    "Where  ((:mId is null) or (m.id = :mId))", nativeQuery = true)
    Optional<Magazine> findById(@Param("mId") Long id);*/
}
