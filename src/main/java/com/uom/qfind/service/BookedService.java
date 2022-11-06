package com.uom.qfind.service;

import com.uom.qfind.dto.BookedDTO;
import com.uom.qfind.dto.BookedRequestDTO;
import com.uom.qfind.model.User;
import com.uom.qfind.model.Vehicle;
import com.uom.qfind.repository.BookedRepository;
import com.uom.qfind.repository.UserRepository;
import com.uom.qfind.repository.VehicleRepository;
import com.uom.qfind.mapper.BookedMapper;
import com.uom.qfind.model.Booked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookedService {
	@Autowired
	private BookedRepository bookedRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookedMapper bookedMapper;

	public List<BookedDTO> getBorrowings() {
		return bookedMapper.borrowingsToBorrowingDtos(bookedRepository.findAll());
	}

	public BookedDTO borrow(BookedRequestDTO bookedRequestDTO) throws Exception {
		Vehicle vehicle = vehicleRepository.findById(bookedRequestDTO.getBookId()).orElseThrow(ResourceNotFoundException::new);
		User user = userRepository.findById(bookedRequestDTO.getMemberId())
				.orElseThrow(ResourceNotFoundException::new);

		if (vehicle.getBorrowing() != null || user.getBorrowing() != null) {
			throw new Error("Bad request");
		}

		Date bookedDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(bookedDate);
		calendar.add(Calendar.DATE, 7);

		Booked booked = new Booked();
		booked.setVehicle(vehicle);
		booked.setUser(user);
		booked.setBookedDate(bookedDate);
		booked.setReturnDate(null);
		return bookedMapper.borrowingToBorrwingDto(bookedRepository.save(booked), true);
	}

	public BookedDTO updateBorrow(String borrowId, BookedRequestDTO bookedRequestDTO) {

		Optional<Booked> borrowingOptional = bookedRepository.findById(borrowId);

		if (!borrowingOptional.isPresent())
			throw new ResourceNotFoundException();

		Booked booked = bookedMapper.borrowDtoToBorrow(bookedRequestDTO);

		booked.setId(borrowingOptional.get().getId());
		booked.setBookedDate(borrowingOptional.get().getBookedDate());

		return bookedMapper.borrowingToBorrwingDto(bookedRepository.save(booked), true);
	}

	public void deleteBorrowingById(String borrowId) {

		bookedRepository.deleteById(borrowId);

	}
}
