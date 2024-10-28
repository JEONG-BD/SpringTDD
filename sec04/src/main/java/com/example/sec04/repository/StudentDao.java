package com.example.sec04.repository;

import com.example.sec04.domain.CollegeStudent;
import org.hibernate.mapping.Collection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends CrudRepository<CollegeStudent, Integer> {

    public CollegeStudent findByEmailAddress(String emailAddress);
}
