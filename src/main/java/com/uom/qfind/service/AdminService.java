package com.uom.qfind.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.uom.qfind.repository.AdminRepository;
import com.uom.qfind.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;

	public List<Admin> getAllLibrarians() {
		List<Admin> librariansRecords = new ArrayList<>();
		adminRepository.findAll().forEach(librariansRecords::add);
		return librariansRecords;
	}

	public void addAdmin(Admin admin) {
		adminRepository.save(admin);
	}

	public void deleteAdmin(Admin admin) {
		adminRepository.delete(admin);
	}	
	
	public void updateAdmin(Admin admin) {
		adminRepository.save(admin);
	}
	
	public boolean findAdmin(String username, String password) {
		
		Optional<Admin> lib =  adminRepository.findById(username);
	return lib.get().getPassword().contentEquals(password);
				
	
	}
	
}
