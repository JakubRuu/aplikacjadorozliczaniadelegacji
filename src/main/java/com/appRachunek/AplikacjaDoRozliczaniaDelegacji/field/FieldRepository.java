package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field, String> {
    Optional<Field> findByName(String name);

    Optional<Field> findByNameAndPerson_Name(String name, String personName);

}
