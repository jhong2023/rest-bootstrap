package com.thu.api.resource;

import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thu.api.domain.Person;
import com.thu.api.repository.PersonRepository;


@Path("/person")
@Service
public class PersonResource extends BaseResource<Person>{
	
	@Autowired
	public PersonResource(PersonRepository repository) {
		super(repository);
	}
}
