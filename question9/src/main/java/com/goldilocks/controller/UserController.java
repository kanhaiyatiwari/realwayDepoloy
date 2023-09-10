package com.goldilocks.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.goldilocks.entyty.User;
import com.goldilocks.entyty.UserDto;
import com.goldilocks.repo.UserRepo;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

	private UserRepo urepo;
	
	@Autowired
	public UserController(UserRepo urepo) {
		this.urepo=urepo;
	}
	
@PostMapping("/register")
public ResponseEntity<String> register(@RequestBody User user)throws Exception{
	
	if(user==null)return new ResponseEntity<>("empty data", HttpStatus.BAD_REQUEST);
	Optional<User >opt = urepo.findByEmail(user.getEmail());
	
	
	if(opt.isPresent()) {
		return new ResponseEntity<>("user is already present", HttpStatus.BAD_REQUEST);
	}
	
	urepo.save(user);
	return new ResponseEntity<>("user registerd successfully ", HttpStatus.OK);

}
@PostMapping("/login")
public ResponseEntity<String> login(@RequestBody  UserDto user) throws Exception{
	Optional<User >opt = urepo.findByEmail(user.getEmail());
	
	if(opt.isPresent()) {
		return new ResponseEntity<>("hi "+opt.get().getName()+" logged successfully ", HttpStatus.OK);
	}
	throw new Exception("Somthig  went wrong ");
}

@GetMapping("/view")
public ResponseEntity<List<User>> view() throws Exception{
	List<User>opt = urepo.findAll();
	if(opt.size()==0)throw new Exception("there is no user in databasse");
	else return new ResponseEntity<>(opt, HttpStatus.OK);
}
}
