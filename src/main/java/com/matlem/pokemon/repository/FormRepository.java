package com.matlem.pokemon.repository;

import com.matlem.pokemon.domain.Form;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Form entity.
 */

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {

}
