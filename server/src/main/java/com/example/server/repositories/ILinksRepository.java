package com.example.server.repositories;

import com.example.server.models.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILinksRepository extends JpaRepository<Link, Long> {

}
