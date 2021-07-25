package com.registration.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.registration.jpa.RegisterRepository;
import com.registration.jpa.SubscriberRepository;
import com.registration.model.Register;
import com.registration.model.RegisteredObject;
import com.registration.model.Subscriber;

@Controller
public class HomeController {

	@Autowired
	SubscriberRepository subscriberRepo;

	@Autowired
	RegisterRepository registerRepo;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("randomRegister", new RandomRegister());
		short doneStatus = 1;
		List<Register> registers = registerRepo.findByStatus(doneStatus);
		

		long countDone = registerRepo.findByStatus(doneStatus).size();
		model.addAttribute("registers", registers);

		model.addAttribute("pending", registerRepo.count() - countDone);
		return "index";
	}

	@PostMapping("/initialize")
	public String initialize(Model model) {
//		model.addAttribute("subscribers", repo.findAll());
		registerRepo.deleteAll();
		List<Register> output = new ArrayList();
		subscriberRepo.findAll().forEach((item) -> {
			Register reg = new Register();
			reg.setRegisteredDate(new Date());
			reg.setRegisterNumber(0);
			short status = 0;
			reg.setStatus(status);
			reg.setSubscriber(item);
			// default
			reg.setTarget(new RegisteredObject(1l));
			registerRepo.save(reg);
			output.add(reg);
		});

		model.addAttribute("registers", output);
		return "redirect:/";
	}

	@PostMapping("/random")
	public String random(@RequestParam(name = "numberNeeded") String numberNeeded, Model model) {

		short status = 0;
		List<Register> registers = registerRepo.findByStatus(status);

		int idx = registerRepo.findMaxRegisterNumber() + 1;

		Integer max = registerRepo.findByStatus(status).size(); // find ones who not registered
//		int numbersNeeded = 50;

		long numbersNeeded = Long.valueOf(numberNeeded);
		if (max < numbersNeeded) {
			throw new IllegalArgumentException("Can't ask for more numbers than are available");
		}
		Random rng = new Random(); // Ideally just create one instance globally
		// Note: use LinkedHashSet to maintain insertion order
		Set<Integer> generated = new HashSet<Integer>();
		List<Integer> results = new ArrayList<Integer>();

		while (generated.size() < numbersNeeded) {
			int size = generated.size();
			Integer next = rng.nextInt(max) + 1;
			// As we're adding to a set, this will automatically do a containment check
			generated.add(next);
			if (generated.size() > size) {
				// insert successfully
				results.add(next);

				Register register = registers.get(next - 1);

				register.setRegisterNumber(idx);
				register.setStatus(Short.valueOf("1"));
				registerRepo.save(register);

				idx++;
			}
		}
		model.addAttribute("registers", registers);
		model.addAttribute("pending", registers.size() - numbersNeeded);
		return "redirect:/";
	}

	@PostMapping("/upload-csv-file")
	public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) {

		// validate file
		if (file.isEmpty()) {
			model.addAttribute("message", "Please select a CSV file to upload.");
			model.addAttribute("status", false);
		} else {

			// parse CSV file to create a list of `Subscriber` objects
			try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

				// create csv bean reader
				@SuppressWarnings({ "unchecked", "rawtypes" })
				CsvToBean<Subscriber> csvToBean = new CsvToBeanBuilder(reader).withType(Subscriber.class)
						.withIgnoreLeadingWhiteSpace(true).build();

				CsvToBeanBuilder<Subscriber> beanBuilder = new CsvToBeanBuilder<>(
						new InputStreamReader(file.getInputStream()));

				beanBuilder.withType(Subscriber.class);
				// build methods returns a list of Beans
//				beanBuilder.build().parse().forEach((e -> System.out.println(e));

//				Map<String, String> columnMapping = new HashMap<String, String>();
//				columnMapping.put("Id", "id");
//				columnMapping.put("Fname", "fname");
//				columnMapping.put("Lname", "lname");
//
//				HeaderColumnNameTranslateMappingStrategy<Subscriber> strategy = new HeaderColumnNameTranslateMappingStrategy<Subscriber>();
//				strategy.setType(Subscriber.class);
//				strategy.setColumnMapping(columnMapping);

				// convert `CsvToBean` object to list of Subscriber
				List<Subscriber> subscribers = csvToBean.parse();

				// save users list on model
				model.addAttribute("subscribers", subscribers);
				model.addAttribute("status", true);

				// clean all current subscribers
				subscriberRepo.deleteAll();
				subscribers.forEach((item) -> {
					item.setId(null);
					subscriberRepo.save(item);
				});
				// save

			} catch (Exception ex) {
				model.addAttribute("message", "An error occurred while processing the CSV file.");
				model.addAttribute("status", false);
			}
		}

		return "index";
	}

	public static void main(String[] args) {

		Integer max = 10; // find ones who not registered
		int numbersNeeded = 5;

		if (max < numbersNeeded) {
			throw new IllegalArgumentException("Can't ask for more numbers than are available");
		}
		Random rng = new Random(); // Ideally just create one instance globally
		// Note: use LinkedHashSet to maintain insertion order
		Set<Integer> generated = new HashSet<Integer>();
		List<Integer> results = new ArrayList<Integer>();

		while (generated.size() < numbersNeeded) {
			int size = generated.size();
			Integer next = rng.nextInt(max) + 1;
			// As we're adding to a set, this will automatically do a containment check
			generated.add(next);
			if (generated.size() > size) {
				// insert successfully
				results.add(next);

			}
		}
	}
}