package com.andres.curso.springboot.jpa.springboot_jpa_relationship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.andres.curso.springboot.jpa.springboot_jpa_relationship.entities.Client;
import com.andres.curso.springboot.jpa.springboot_jpa_relationship.entities.Invoice;
import com.andres.curso.springboot.jpa.springboot_jpa_relationship.repositories.ClientRepository;
import com.andres.curso.springboot.jpa.springboot_jpa_relationship.repositories.InvoiceRepository;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner{
	
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		manyToOne();
	}

	public void manyToOne(){

		Client client = new Client("Andres", "Guzman");
		clientRepository.save(client);

		Invoice invoice = new Invoice("Factura 1", 1000L, client);
		invoiceRepository.save(invoice);

		System.out.println(invoice);

	}

}
