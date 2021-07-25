package com.registration.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.registration.jpa.SubscriberRepository;
import com.registration.model.Subscriber;

@RestController
@RequestMapping("/api")
public class SubsriberController {

	@Autowired
	SubscriberRepository subscriberRepo;
	 
	@PostMapping("/save")
	public List<Subscriber> save(@RequestBody List<Subscriber> inputs) {
		
		List<Subscriber> output = new ArrayList<Subscriber>(); 
		inputs.forEach((item)-> {
			subscriberRepo.save(item);
			output.add(item);
		});
		return output;
		
	}

	@GetMapping("/subscriber")
    public ResponseEntity<List<Subscriber>> getAllSubscriber(Pageable pageable) {
        Page<Subscriber> page = subscriberRepo.findAll(pageable);
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
        
	}
	
	@PutMapping("/save")
	public List<Subscriber> update(@RequestBody List<Subscriber> inputs) {
		inputs.forEach((item)-> {
			subscriberRepo.save(null);
		});
		return inputs;
	}
}