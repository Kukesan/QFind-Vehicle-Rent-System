package com.uom.qfind.service;

import com.uom.qfind.dto.VehicleDTO;
import com.uom.qfind.model.Vehicle;
import com.uom.qfind.repository.VehicleRepository;
import com.uom.qfind.mapper.VehicleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleMapper vehicleMapper;

    public VehicleDTO createVehicle(VehicleDTO vehicleDTO) {
        vehicleDTO.setId(null);
        
        Vehicle vehicle = vehicleRepository.save(vehicleMapper.bookDtoToBook(vehicleDTO));
        return vehicleMapper.bookToBookDto(vehicle);
    }

    public VehicleDTO getVehicleById(String id) {
        return vehicleRepository.findById(id)
                .map(book -> vehicleMapper.bookToBookDto(book))
                .orElseThrow(ResourceNotFoundException::new);
    }

    public List<VehicleDTO> getAllBooks() {
        return vehicleMapper.booksToBookDtos(vehicleRepository.findAll());
    }

    public VehicleDTO updateVehicle(String id, VehicleDTO vehicleDTO) {
        Optional<Vehicle> bookOptional = vehicleRepository.findById(id);
        if (!bookOptional.isPresent()) throw new ResourceNotFoundException();

        Vehicle vehicle = vehicleMapper.bookDtoToBook(vehicleDTO);
        vehicle.setId(bookOptional.get().getId());
        return vehicleMapper.bookToBookDto(vehicleRepository.save(vehicle));
    }

    public void deleteVehicleById(String id) {
        vehicleRepository.deleteById(id);
    }
}
