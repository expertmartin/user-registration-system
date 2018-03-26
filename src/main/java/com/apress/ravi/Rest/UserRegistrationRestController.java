package com.apress.ravi.Rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apress.ravi.dto.UserDTO;
import com.apress.ravi.exception.CustomErrorType;
import com.apress.ravi.repository.UserJpaRepository;

@RestController
@RequestMapping("/api/user")
public class UserRegistrationRestController {
	public static final Logger logger = LoggerFactory.getLogger(UserRegistrationRestController.class);
	private UserJpaRepository userJpaRepository;

	@Autowired
	public void setUserJpaRepository(UserJpaRepository userJpaRepository) {
		this.userJpaRepository = userJpaRepository;
	}

	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> listAllUsers() {
		List<UserDTO> users = userJpaRepository.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<List<UserDTO>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") final Long id) {
		Optional<UserDTO> user = userJpaRepository.findById(id);
		ResponseEntity<UserDTO> response = user.map(x -> new ResponseEntity<UserDTO>(x, HttpStatus.CREATED))
				.orElseGet(() -> new ResponseEntity<UserDTO>(new CustomErrorType("User with id " + id + " not found"),
						HttpStatus.NOT_FOUND));

		return response;
	}

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody final UserDTO user) {
		if (userJpaRepository.findByName(user.getName()) != null) {
			return new ResponseEntity<UserDTO>(
					new CustomErrorType(
							"Unable to create new user. A User with name " + user.getName() + " already exist."),
					HttpStatus.CONFLICT);
		}

		userJpaRepository.save(user);
		return new ResponseEntity<UserDTO>(user, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> updateUser(@PathVariable("id") final Long id, @Valid @RequestBody UserDTO user) {
		// fetch user based on id and set it to currentUser object of type UserDTO
		Optional<UserDTO> currentUserOpt = userJpaRepository.findById(id);
		ResponseEntity<UserDTO> response = currentUserOpt.map(u -> {
			u.setName(user.getName());
			u.setAddress(user.getAddress());
			u.setEmail(user.getEmail());
			// save currentUser obejct
			userJpaRepository.saveAndFlush(u);
			return new ResponseEntity<UserDTO>(u, HttpStatus.OK);
		}).orElse(new ResponseEntity<UserDTO>(
				new CustomErrorType("Unable to upate. User with id " + id + " not found."), HttpStatus.NOT_FOUND));

		return response;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") final Long id) {
		Optional<UserDTO> user = userJpaRepository.findById(id);
		ResponseEntity<UserDTO> response = user.map(u -> {
			//userJpaRepository.delete(id);
			userJpaRepository.delete(id);
			return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
		}).orElse(new ResponseEntity<UserDTO>(
				new CustomErrorType("Unable to delete. User with id " + id + " not found."), HttpStatus.NOT_FOUND));

		return response;
	}
}