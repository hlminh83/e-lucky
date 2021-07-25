package com.registration.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.registration.model.Register;

@Repository
public interface RegisterRepository extends PagingAndSortingRepository<Register, Long> {

	List<Register> findByStatus(short status);

	Register findById(long id);

	@Query(value = "select max(registerNumber) from Register where status = 1 or status = 0 ")
	int findMaxRegisterNumber();

	@Query(value = "select distinct reg from Register reg left join reg.subscriber ")
	List<Register> getAllRegistration();
}