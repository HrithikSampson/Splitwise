package com.hrithik.splitwise.controller;

import com.hrithik.splitwise.dto.SettlementDTO;
import com.hrithik.splitwise.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/settlements")
@CrossOrigin(origins = "*")
public class SettlementController {

    @Autowired
    private SettlementService settlementService;

    @PostMapping
    public ResponseEntity<SettlementDTO> createSettlement(
            @RequestParam Long fromUserId,
            @RequestParam Long toUserId,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) Long groupId) {
        SettlementDTO createdSettlement = settlementService.createSettlement(fromUserId, toUserId, amount, groupId);
        return new ResponseEntity<>(createdSettlement, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SettlementDTO> getSettlementById(@PathVariable Long id) {
        SettlementDTO settlement = settlementService.getSettlementById(id);
        return ResponseEntity.ok(settlement);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<SettlementDTO>> getSettlementsByGroup(@PathVariable Long groupId) {
        List<SettlementDTO> settlements = settlementService.getSettlementsByGroup(groupId);
        return ResponseEntity.ok(settlements);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SettlementDTO>> getUserSettlements(@PathVariable Long userId) {
        List<SettlementDTO> settlements = settlementService.getUserSettlements(userId);
        return ResponseEntity.ok(settlements);
    }

    @GetMapping
    public ResponseEntity<List<SettlementDTO>> getAllSettlements() {
        List<SettlementDTO> settlements = settlementService.getAllSettlements();
        return ResponseEntity.ok(settlements);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSettlement(@PathVariable Long id) {
        settlementService.deleteSettlement(id);
        return ResponseEntity.noContent().build();
    }
}
