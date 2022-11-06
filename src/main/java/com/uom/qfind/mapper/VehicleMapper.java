package com.uom.qfind.mapper;

import com.uom.qfind.dto.VehicleDTO;
import com.uom.qfind.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehicleMapper {

    @Autowired
    private BookedMapper bookedMapper;

    public VehicleDTO bookToBookDto(Vehicle vehicle) {
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setId(vehicle.getId());
        vehicleDTO.setOwner(vehicle.getOwner());
        vehicleDTO.setNumber(vehicle.getNumber());
        vehicleDTO.setName(vehicle.getName());

        if (vehicle.getBorrowing() != null) {
            vehicleDTO.setBorrowing(bookedMapper.borrowingToBorrwingDto(vehicle.getBorrowing(), false));
            vehicleDTO.setAvailability(false);

        }else {
            vehicleDTO.setAvailability(true);
        }

        return vehicleDTO;
    }

    public Vehicle bookDtoToBook(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();
        vehicle.setName(vehicleDTO.getName());
        vehicle.setId(vehicleDTO.getId());
        vehicle.setNumber(vehicleDTO.getNumber());
        vehicle.setOwner(vehicleDTO.getOwner());

        return vehicle;
    }

    public List<VehicleDTO> booksToBookDtos(List<Vehicle> vehicles) {
        return vehicles.stream().map(this::bookToBookDto).collect(Collectors.toList());
    }
}
