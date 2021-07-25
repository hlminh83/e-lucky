package com.registration.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.registration.jpa.RegisterRepository;
import com.registration.model.Register;

@RestController
@RequestMapping("/api")
public class RegisterController {

	@Autowired
	RegisterRepository repo;

	@PostMapping("/register")
	public ResponseEntity<Register> save(@RequestBody Register input) {
		repo.save(input);
		return new ResponseEntity<>(input, HttpStatus.OK);
	}

	@GetMapping("/register")
    public ResponseEntity<List<Register>> getAllRegisters(Pageable pageable) {
        Page<Register> page = repo.findAll(pageable);
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
        
	}

//	@PutMapping("/save")
//	public List<Subscriber> update(@RequestBody List<Subscriber> inputs) {
//
//	}
}