package com.bobalalu.blog.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Babatunde Obalalu - bobalalu@yahoo.com - +2348034627801
 */
@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p FROM posts p WHERE lcase(p.title) LIKE %:title%")
    List<Post> findByTitle(@Param("title") String title);
}