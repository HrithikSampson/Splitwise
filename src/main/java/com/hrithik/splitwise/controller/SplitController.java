package com.hrithik.splitwise.controller;

import com.hrithik.splitwise.dto.SplitDTO;
import com.hrithik.splitwise.service.SplitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/splits")
@CrossOrigin(origins = "*")
public class SplitController {

    @Autowired
    private SplitService splitService;

    @PostMapping
    public ResponseEntity<SplitDTO> createSplit(
            @RequestParam Long expenseId,
            @RequestParam Long fromUserId,
            @RequestParam Long toUserId,
            @RequestParam BigDecimal amount) {
        SplitDTO createdSplit = splitService.createSplit(expenseId, fromUserId, toUserId, amount);
        return new ResponseEntity<>(createdSplit, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SplitDTO> getSplitById(@PathVariable Long id) {
        SplitDTO split = splitService.getSplitById(id);
        return ResponseEntity.ok(split);
    }

    @GetMapping("/expense/{expenseId}")
    public ResponseEntity<List<SplitDTO>> getSplitsByExpense(@PathVariable Long expenseId) {
        List<SplitDTO> splits = splitService.getSplitsByExpense(expenseId);
        return ResponseEntity.ok(splits);
    }

    @GetMapping("/user/{userId}/debts")
    public ResponseEntity<List<SplitDTO>> getUserDebts(@PathVariable Long userId) {
        List<SplitDTO> debts = splitService.getUserDebts(userId);
        return ResponseEntity.ok(debts);
    }

    @GetMapping("/user/{userId}/credits")
    public ResponseEntity<List<SplitDTO>> getUserCredits(@PathVariable Long userId) {
        List<SplitDTO> credits = splitService.getUserCredits(userId);
        return ResponseEntity.ok(credits);
    }

    @GetMapping("/user/{userId}/unpaid")
    public ResponseEntity<List<SplitDTO>> getUserUnpaidDebts(@PathVariable Long userId) {
        List<SplitDTO> unpaidDebts = splitService.getUserUnpaidDebts(userId);
        return ResponseEntity.ok(unpaidDebts);
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<SplitDTO> markSplitAsPaid(@PathVariable Long id) {
        SplitDTO split = splitService.markSplitAsPaid(id);
        return ResponseEntity.ok(split);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSplit(@PathVariable Long id) {
        splitService.deleteSplit(id);
        return ResponseEntity.noContent().build();
    }
}
