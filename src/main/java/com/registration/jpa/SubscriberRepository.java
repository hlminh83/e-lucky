package com.registration.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.registration.model.Subscriber;

@Repository
public interface SubscriberRepository extends PagingAndSortingRepository<Subscriber, Long> {

  //already register 
//  List<Subscriber> findByRegisteredObject(long registerObjectId);

  Subscriber findById(long id);
}