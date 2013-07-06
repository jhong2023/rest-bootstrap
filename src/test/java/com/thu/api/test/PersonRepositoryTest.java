package com.thu.api.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.thu.api.domain.Address;
import com.thu.api.domain.Person;
import com.thu.api.repository.PersonRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-repository-context.xml")
@Transactional
public class PersonRepositoryTest {

	@Autowired
	PersonRepository personRepository;
	Person person;
	Address address;

	@Before
	public void setUp() {
		person = new Person();
		person.setName("foobar");
		address = new Address();
		address.setStreet("Beautify Street");
		address.setCity("Redmond");
		person.setAddress(address);
	}

	@Test
	public void testInsertAndFind() {
		person = personRepository.save(person);
		assertEquals(person, personRepository.findOne(person.getId()));
		assertEquals(address.getId(), person.getAddress().getId());
		System.out.println("Person Id: " + person.getId());
		System.out.println("Address Id: " + person.getAddress().getId());
		
		List<Person> people = personRepository.findAll();
		
		System.out.println("User: " +  people.get(0).getName());
		
		// Find by Name
		List<Person> r = personRepository.findByName("foobar");
		System.out.println("Found person by name [foobar]: " +  r.size());
		r = personRepository.findByName("xxxx");
		System.out.println("Found person by name [xxxx]: " + r.size());
		
		// Find by City
		r = personRepository.findByAddressCity("Redmond");
		System.out.println("Found person by name [Redmond]: " +  r.size());
		r = personRepository.findByAddressCity("xxxx");
		System.out.println("Found person by name [xxxx]: " + r.size());
		
		// Pagination
		List<Person> list = personRepository.findAll(new PageRequest(0, 10)).getContent();
		System.out.println("Found person on page 0:" + list.size() );
		list = personRepository.findAll(new PageRequest(1, 10)).getContent();
		System.out.println("Found person on page 1:" + list.size() );
		
		
	}
	
}
