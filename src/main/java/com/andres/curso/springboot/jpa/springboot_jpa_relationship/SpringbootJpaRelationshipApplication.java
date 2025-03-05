package com.andres.curso.springboot.jpa.springboot_jpa_relationship;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.andres.curso.springboot.jpa.springboot_jpa_relationship.entities.Address;
import com.andres.curso.springboot.jpa.springboot_jpa_relationship.entities.Client;
import com.andres.curso.springboot.jpa.springboot_jpa_relationship.entities.Invoice;
import com.andres.curso.springboot.jpa.springboot_jpa_relationship.repositories.ClientRepository;
import com.andres.curso.springboot.jpa.springboot_jpa_relationship.repositories.InvoiceRepository;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		oneToMany();
	}

	@Transactional
	public void oneToMany(){

		Client client = new Client("Leonard2","Hernandez2");

		Address address1 = new Address("Calle 1", 1);
		Address address2 = new Address("Calle 2", 2);
		
		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);

		System.out.println(client);

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
