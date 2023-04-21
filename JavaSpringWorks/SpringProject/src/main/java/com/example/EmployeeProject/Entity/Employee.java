package com.example.demo.Entity;



	import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.Table;
	import lombok.Data;

	@Data
	@Entity
	@Table(name="employee")
	public class Employee {
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private long id;
		@Column(name="First Name",nullable=false)
		private String firstName;
		@Column(name="Last name",nullable=false)
		private String lastName;
		@Column(name="Email",nullable=false)
		private String email;

	

	}
