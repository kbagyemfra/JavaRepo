package com.example.demo;

import javax.persistence.*;

@Entity
@Table(name = "property")
public class Properties {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;
	
	// Change 1
	@Column(name = "state")
	private String state;

	@Column(name = "author")
	private String author;
	
	@Column(name = "price")
	private String price;
	
	@Column(name = "gmail")
	private String gmail;

	@Column(name = "picByte", length = 1000000000)
	private byte[] picByte;
	
	
	
	public Properties() {

	}

	public Properties(String name, String state, String author, String price) {
		this.name = name;
		this.state = state;
		this.author = author;
		this.price = price;
		
	}
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}
	
	
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", state=" + state + ", name=" + name + ", author=" + author + "]";
	}
	
}