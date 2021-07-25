package com.registration.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.registration.model.RegisteredObject;

@Repository
public interface RegisteredObjectRepository extends CrudRepository<RegisteredObject, Long> {

//  List<RegisteredObject> findByLastName(String lastName);

  RegisteredObject findById(long id);
}