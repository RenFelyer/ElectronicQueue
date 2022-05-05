package ua.ferret.client.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public class EntityID {

	@Id
	@JsonIgnore
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id", columnDefinition = "BINARY(16)")
	protected UUID id;

	@Column(nullable = false, updatable = false)
	protected Date created;
	
	public EntityID() {
		created = new Date();
	}
	
	public UUID getId() {return id;}
	public Date getCreated() {return created;}

}
