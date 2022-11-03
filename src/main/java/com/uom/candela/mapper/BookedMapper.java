package com.uom.candela.mapper;

import com.uom.candela.dto.BookedRequestDTO;
import com.uom.candela.dto.BookedDTO;
import com.uom.candela.model.Booked;
import com.uom.candela.model.User;
import com.uom.candela.model.Vehicle;
import com.uom.candela.repository.VehicleRepository;
import com.uom.candela.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookedMapper {

	@Autowired
	private VehicleMapper vehicleMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private UserMapper userMapper;

	public BookedDTO borrowingToBorrwingDto(Booked booked, boolean eager) {
		BookedDTO bookedDTO = new BookedDTO();
		bookedDTO.setId(booked.getId());

		if (eager && booked.getVehicle() != null) {
			bookedDTO.setVehicle(vehicleMapper.bookToBookDto(booked.getVehicle()));
		}

		if (eager && booked.getUser() != null) {
			bookedDTO.setUser(userMapper.memberToMemberDto(booked.getUser()));
		}

		bookedDTO.setBookedDate(booked.getBookedDate());
		bookedDTO.setReturnDate(booked.getReturnDate());
		return bookedDTO;
	}

	public List<BookedDTO> borrowingsToBorrowingDtos(List<Booked> bookeds) {
		return bookeds.stream().map(borrowing -> this.borrowingToBorrwingDto(borrowing, true))
				.collect(Collectors.toList());
	}

	public Booked borrowDtoToBorrow(BookedRequestDTO bookedRequestDTO) {

		Booked booked = new Booked();
		Optional<User> memberOptional = userRepository.findById(bookedRequestDTO.getMemberId());
		Optional<Vehicle> bookOptional = vehicleRepository.findById(bookedRequestDTO.getBookId());

		if (!memberOptional.isPresent())
			throw new ResourceNotFoundException();
		if (!bookOptional.isPresent())
			throw new ResourceNotFoundException();

		bookOptional.get().setAvailability(true);
		booked.setVehicle(bookOptional.get());
		booked.setReturnDate(null);
		booked.setUser(memberOptional.get());

		if (bookedRequestDTO.isReturnOrnot()) {

			Date returnedDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(returnedDate);
			calendar.add(Calendar.DATE, 7);
			booked.setReturnDate(calendar.getTime());

		}

		return booked;
	}
}
