package com.scm.crud.repositories;

import com.scm.crud.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Client, Long> {
}
