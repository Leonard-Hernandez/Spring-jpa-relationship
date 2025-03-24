package com.andres.curso.springboot.jpa.springboot_jpa_relationship;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.andres.curso.springboot.jpa.springboot_jpa_relationship.entities.Address;
import com.andres.curso.springboot.jpa.springboot_jpa_relationship.entities.Client;
import com.andres.curso.springboot.jpa.springboot_jpa_relationship.entities.ClientDetails;
import com.andres.curso.springboot.jpa.springboot_jpa_relationship.entities.Course;
import com.andres.curso.springboot.jpa.springboot_jpa_relationship.entities.Invoice;
import com.andres.curso.springboot.jpa.springboot_jpa_relationship.entities.Student;
import com.andres.curso.springboot.jpa.springboot_jpa_relationship.repositories.ClientRepository;
import com.andres.curso.springboot.jpa.springboot_jpa_relationship.repositories.CourseRepository;
import com.andres.curso.springboot.jpa.springboot_jpa_relationship.repositories.InvoiceRepository;
import com.andres.curso.springboot.jpa.springboot_jpa_relationship.repositories.StudentRepository;
import com.andres.curso.springboot.jpa.springboot_jpa_relationship.repositories.ClientDetailsRepository;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private ClientDetailsRepository clientDetailsRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		manyToManyRemoveFind();
	}

	@Transactional
	public void manyToManyBidirectional() {

		Student student = new Student("Andres", "Guzman");
		Student student2 = new Student("leonard", "hernandez");

		Course course = courseRepository.findById(16L).get();
		Course course2 = courseRepository.findById(17L).get();

		student.addCourse(course);
		student.addCourse(course2);

		student2.addCourse(course2);

		studentRepository.saveAll(List.of(student, student2)).forEach(System.out::println);

	}

	@Transactional
	public void manyToMany() {

		Student student = new Student("Andres", "Guzman");
		Student student2 = new Student("leonard", "hernandez");

		Course course = courseRepository.findById(16L).get();
		Course course2 = courseRepository.findById(17L).get();

		student.addCourse(course);
		student.addCourse(course2);

		student2.addCourse(course2);

		studentRepository.saveAll(List.of(student, student2)).forEach(System.out::println);

	}

	@Transactional
	public void manyToManyFind() {

		Student student = studentRepository.findById(6L).get();
		Student student2 = studentRepository.findById(7L).get();

		Course course = courseRepository.findById(16L).get();
		Course course2 = courseRepository.findById(17L).get();

		student.addCourse(course);
		student.addCourse(course2);

		student2.addCourse(course2);

		studentRepository.saveAll(List.of(student, student2)).forEach(System.out::println);

	}

	@Transactional
	public void manyToManyRemoveFind() {

		Student student = studentRepository.findById(6L).get();
		Student student2 = studentRepository.findById(7L).get();

		Course course = courseRepository.findById(16L).get();
		Course course2 = courseRepository.findById(17L).get();

		student.addCourse(course);
		student.addCourse(course2);

		student2.addCourse(course2);

		studentRepository.saveAll(List.of(student, student2)).forEach(System.out::println);

		Optional<Student> optionalStudent = studentRepository.findWithAll(1L);
		if (optionalStudent.isPresent()) {

			Student studentDB = optionalStudent.get();
			Optional<Course> optionalCourse = courseRepository.findById(13L);

			if (optionalCourse.isPresent()) {

				studentDB.getCourses().remove(optionalCourse.get());

				System.out.println(studentDB);
			}

		}

	}

	@Transactional
	public void oneToOne() {

		Client client = new Client("juan", "carlo");
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		client.setClientDetails(clientDetails);
		Client savedClient = clientRepository.save(client);

		System.out.println(savedClient);

	}

	@Transactional
	public void oneToOnebidirectional() {

		Client client = new Client("juan", "carlo");
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		client.setClientDetails(clientDetails);
		Client savedClient = clientRepository.save(client);

		System.out.println(savedClient);

	}

	@Transactional
	public void oneToOnebidirectionalFindByid() {

		Optional<Client> optionalClient = clientRepository.findWithAll(2L);

		optionalClient.ifPresentOrElse(c -> {
			ClientDetails clientDetails = new ClientDetails(true, 5400);
			c.setClientDetails(clientDetails);
			Client savedClient = clientRepository.save(c);

			System.out.println(savedClient);

		},
				() -> {
					System.out.println("Client not found");
				});

	}

	@Transactional
	public void oneToOneFindById() {

		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);

		Optional<Client> client = clientRepository.findWithAll(2L);

		client.ifPresentOrElse(c -> {

			c.setClientDetails(clientDetails);
			Client savedClient = clientRepository.save(c);

			System.out.println(savedClient);

		},
				() -> {
					System.out.println("Client not found");
				});

	}

	@Transactional
	public void oneToManyInvoiceBidirectional() {

		Client client = new Client("Leonard2", "Hernandez2");

		Invoice invoice1 = new Invoice("Factura 1", 1000L);
		Invoice invoice2 = new Invoice("Factura 2", 2000L);

		client.addInvoice(invoice1).addInvoice(invoice2);

		invoice1.setClient(client);
		invoice2.setClient(client);

		Client savedClient = clientRepository.save(client);

		System.out.println(savedClient);

	}

	@Transactional
	public void oneToManyInvoiceBidirectionalFindById() {

		Optional<Client> optionalClient = clientRepository.findOnWithInvoice(1L);

		optionalClient.ifPresentOrElse(client -> {
			Invoice invoice1 = new Invoice("Factura 1", 1000L);
			Invoice invoice2 = new Invoice("Factura 2", 2000L);

			client.addInvoice(invoice1).addInvoice(invoice2);

			invoice1.setClient(client);
			invoice2.setClient(client);

			Client savedClient = clientRepository.save(client);

			System.out.println(savedClient);
		}, () -> {
			System.out.println("Client not found");
		});

	}

	@Transactional
	public void removeInvoiceBidirectional() {

		Client client = new Client("Andres", "Guzman");

		Invoice invoice1 = new Invoice("Factura 1", 1000L);
		Invoice invoice2 = new Invoice("Factura 2", 2000L);

		client.addInvoice(invoice1).addInvoice(invoice2);

		Client savedClient = clientRepository.save(client);

		System.out.println(savedClient);

		Optional<Client> optionalClient = clientRepository.findWithAll(savedClient.getId());

		optionalClient.ifPresentOrElse(client2 -> {

			client2.removeInvoice(invoice1);
			System.out.println(clientRepository.save(client2));

		}, () -> {
			System.out.println("Client not found");
		});

	}

	@Transactional
	public void removeInvoiceBidirectionalFindById() {

		Optional<Client> optionalClientDB = clientRepository.findWithAll(8L);

		optionalClientDB.ifPresentOrElse(client -> {

			Optional<Invoice> optionalInvoice = invoiceRepository.findById(9L);

			optionalInvoice.ifPresentOrElse(invoice -> {

				client.removeInvoice(invoice);
				client.getInvoices().remove(invoice);
				invoice.setClient(null);

				Client savedClient = clientRepository.save(client);

				System.out.println(savedClient);
			}, () -> {
				System.out.println("Invoice not found");
			});

		}, () -> {
			System.out.println("Client not found");
		});

	}

	@Transactional
	public void oneToMany() {

		Client client = new Client("Leonard2", "Hernandez2");

		Address address1 = new Address("Calle 1", 1);
		Address address2 = new Address("Calle 2", 2);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		Client savedClient = clientRepository.save(client);

		System.out.println(savedClient);

	}

	@SuppressWarnings("unlikely-arg-type")
	@Transactional
	public void removeAddress() {

		Optional<Client> clientOptional = clientRepository.findWithAll(6L);

		clientOptional.ifPresentOrElse(c -> {
			c.getAddresses().remove(0);
			Client savedClient2 = clientRepository.save(c);

			System.out.println(savedClient2);

		},
				() -> {
					System.out.println("Client not found");
				});

	}

	@Transactional
	public void oneToManyFindById() {

		Optional<Client> client = clientRepository.findById(2L);

		client.ifPresentOrElse(c -> {
			Address address1 = new Address("Calle 1", 1);
			Address address2 = new Address("Calle 2", 2);

			c.getAddresses().add(address1);
			c.getAddresses().add(address2);

			Client savedClient = clientRepository.save(c);

			System.out.println(savedClient);
		},
				() -> {
					System.out.println("Client not found");
				});

	}

	@Transactional
	public void manyToOne() {

		Client client = new Client("Andres", "Guzman");
		clientRepository.save(client);

		Invoice invoice = new Invoice("Factura 1", 1000L, client);
		invoiceRepository.save(invoice);

		System.out.println(invoice);

	}

	@Transactional
	public void manyToOneFindById() {

		Optional<Client> client = clientRepository.findById(1L);

		client.ifPresentOrElse(c -> {

			Invoice invoice = new Invoice("Factura 1", 1000L, c);
			invoiceRepository.save(invoice);

			System.out.println(invoice);
			return;

		},
				() -> {
					System.out.println("Client not found");
				});

	}

}
