package com.registration; 

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.registration.jpa.RegisterRepository;
import com.registration.jpa.RegisteredObjectRepository;
import com.registration.jpa.SubscriberRepository;
import com.registration.model.RegisteredObject;

//import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
 * @SpringBootApplication
public class AccessingDataJpaApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccessingDataJpaApplication.class, args);
  }

}
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
//		

		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(SubscriberRepository repository) {
	  return (args) -> {
	    // save a few customers
//	    repository.save(new Subscriber("1111", "mih1hc", "Jack", "Bauer"));
	  };
	}
	
	@Bean
	public CommandLineRunner vaccination(RegisteredObjectRepository repository) {
	  return (args) -> {
	    // save a few customers
//		  RegisteredObject ro = new RegisteredObject();
//		  ro.setName("vacinnation");
//		  repository.save(ro);
	  };
	}
	
	
//	@Bean
//	public CommandLineRunner generateData(RegisterRepository repository) {
//	  return (args) -> {
//	    // save a few customers
////	    repository.save(new Subscriber("1111", "mih1hc", "Jack", "Bauer"));
//	  };
//	}
}