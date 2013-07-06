package com.thu.api.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.thu.api.domain.BaseDomain;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public abstract class BaseResource<T extends BaseDomain> {

	JpaRepository<T, Long> repository;
	
	public BaseResource (JpaRepository<T, Long> repository) {
		this.repository = repository;
	}
	
	@POST
	public Response create(T entity) {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		repository.save(entity);
		return Response.ok(entity.getId().toString()).build();
	}
	
	@GET
	@Path("/{id}")
	public Response get(@PathParam("id") Long id) {
		T entity = repository.findOne(id);
		if (entity != null) {
			return Response.ok(entity).build();
		}
		
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@GET
	@Path("/list")
	public List<T> getList(@DefaultValue("0") @QueryParam("page") Integer page, 
			@DefaultValue("20") @QueryParam("size") Integer size) {
		List<T> entities = repository.findAll(new PageRequest(page, size)).getContent();
		
		return entities;
		/*
		if (entities != null) {
			return Response.ok(entities).build();
		}
		
		return Response.status(Status.NOT_FOUND).build();
		*/
	}
	
	@PUT
	@Path("/{id}")
	public Response update(T entity) {
		if (entity.getId() == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		repository.save(entity);
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		repository.delete(id);
		return Response.ok().build();
	}
	
}
