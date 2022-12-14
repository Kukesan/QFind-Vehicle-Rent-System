package com.uom.qfind.controller;

import com.uom.qfind.dto.BookedDTO;
import com.uom.qfind.dto.BookedRequestDTO;
import com.uom.qfind.service.BookedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/bookings")
public class BookedController {
    @Autowired
    private BookedService bookedService;

    @GetMapping
    public ResponseEntity<List<BookedDTO>> getBookings() {
        return ResponseEntity.ok(bookedService.getBorrowings());
    }

    @PostMapping
    public ResponseEntity<BookedDTO> bookedVehicle(@RequestBody BookedRequestDTO bookedRequestDTO) throws Exception {
        BookedDTO bookedDTO = bookedService.borrow(bookedRequestDTO);
        return ResponseEntity.ok(bookedDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BookedDTO> updatebookedVehicle(@PathVariable("id") String borrowId, @RequestBody BookedRequestDTO bookedRequestDTO) throws Exception {
        return ResponseEntity.ok(bookedService.updateBorrow(borrowId, bookedRequestDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("id") String borrowId) {
    	bookedService.deleteBorrowingById(borrowId);
        return ResponseEntity.ok().build();
    }
}
