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

    Magazine findMagazineByPublishedAt(Date publishedAt);

    Magazine findMagazineByCode(String code);

    Magazine findMagazineById(Long id);

    @Query("select m from Magazine m " +
    "where m.is_deleted = 0 " + 
    "group by m.id")
    Page<Magazine> getNonDelRole(Pageable pageable);

    @Query(value = "Select * FROM magazine m " +
    "Where  ((:mId is null) or (m.id = :mId))", nativeQuery = true)
    Optional<Magazine> findById(@Param("mId") Long id);
}
