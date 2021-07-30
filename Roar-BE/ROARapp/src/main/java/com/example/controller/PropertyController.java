package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Properties;
import com.example.demo.User;
import com.example.mail.MailMessage;
import com.example.mail.MailService;
import com.example.repository.PropRepository;
import com.example.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "prop")
public class PropertyController {

	private byte[] bytes;

	@Autowired
	private PropRepository propRepository;

	@Autowired
	private MailService notificationService;
	@Autowired
	private MailMessage message;

	
	// To Get all Properties or find by state 
	
	@GetMapping("/get")
	public ResponseEntity<List<Properties>> getAllProperties(@RequestParam(required = false) String state) {
		try {
			List<Properties> properties = new ArrayList<Properties>();

			if (state == null)
				propRepository.findAll().forEach(properties::add);
			else
				propRepository.findByStateContaining(state).forEach(properties::add);

			if (properties.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(properties, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


		// To upload an image to the Database in MySQL

	@PostMapping("/upload")
	public void uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
		this.bytes = file.getBytes();
	}

	
	// To add a Property
	@PostMapping("/add")
	public void createProperty(@RequestBody Properties properties) throws IOException {
		properties.setPicByte(this.bytes);
		propRepository.save(properties);
		this.bytes = null;
	}

	
	// to delete a User by id
	@DeleteMapping(path = { "/{id}" })
	@PreAuthorize("hasRole('ADMIN')or hasRole('MODERATOR')")
	public Properties deleteProperty(@PathVariable("id") long id) {
		Properties book = propRepository.getOne(id);
		propRepository.deleteById(id);
		return book;
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasRole('ADMIN')or hasRole('MODERATOR')")
	public void updateProperty(@RequestBody Properties book) {
		propRepository.save(book);
	}


	
	// Mail API to send a User request
	
	@GetMapping("/send-request")
	public ResponseEntity<List<Properties>> RequestEmail(@RequestParam(required = false) String gmail) {
		try {
			List<Properties> properties = new ArrayList<Properties>();

			// bookRepository.findByGmailContaining(gmail).forEach(book::add);

			System.out.println(gmail);

			message.setEmailAddress(gmail); // Receiver's email address
			message.setSubject("Request to Rent");
			message.setBodyText(
					"Hello" + gmail + ", I am intrested in Renting your space. " + "Would you like to continue? ");
			/*
			 * Here we will call sendEmail() for Sending mail to the sender.
			 */
			try {
				notificationService.sendEmail(message);
			} catch (MailException mailException) {
				System.out.println(mailException);
			}

			if (properties.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {

				return new ResponseEntity<>(properties, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	// Mail API to send a User approval
	@GetMapping("/send-approval")
	public ResponseEntity<List<User>> ApproveEmail(@RequestParam(required = false) String email) {
		try {
			List<User> book = new ArrayList<User>();

			// bookRepository.findByGmailContaining(gmail).forEach(book::add);

			System.out.println(email);

			message.setEmailAddress(email); // Receiver's email address
			message.setSubject("Response to Request");
			message.setBodyText("Hello " + email + ", I am willing to continue.");
			/*
			 * Here we will call sendEmail() for Sending mail to the sender.
			 */
			try {
				notificationService.sendEmail(message);
			} catch (MailException mailException) {
				System.out.println(mailException);
			}

			if (book.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {

				return new ResponseEntity<>(book, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	// Mail API to send a User denial
	@GetMapping("/send-denial")
	public ResponseEntity<List<Properties>> DenyEmail(@RequestParam(required = false) String gmail) {
		try {
			List<Properties> properties = new ArrayList<Properties>();

			// bookRepository.findByGmailContaining(gmail).forEach(book::add);

			System.out.println(gmail);

			message.setEmailAddress(gmail); // Receiver's email address
			message.setSubject("Response to Request");
			message.setBodyText("Hello" + gmail + ", I am not willing to continue. Good Luck with your search");
			/*
			 * Here we will call sendEmail() for Sending mail to the sender.
			 */
			try {
				notificationService.sendEmail(message);
			} catch (MailException mailException) {
				System.out.println(mailException);
			}

			if (properties.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {

				return new ResponseEntity<>(properties, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
}