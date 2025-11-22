package com.hrithik.splitwise.service;

import com.hrithik.splitwise.dto.SettlementDTO;
import com.hrithik.splitwise.entities.Group;
import com.hrithik.splitwise.entities.Settlement;
import com.hrithik.splitwise.entities.User;
import com.hrithik.splitwise.repository.GroupRepository;
import com.hrithik.splitwise.repository.SettlementRepository;
import com.hrithik.splitwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SettlementService {

    @Autowired
    private SettlementRepository settlementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    public SettlementDTO createSettlement(Long fromUserId, Long toUserId, BigDecimal amount, Long groupId) {
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new IllegalArgumentException("From user not found with id: " + fromUserId));
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new IllegalArgumentException("To user not found with id: " + toUserId));

        Settlement settlement = new Settlement();

        if (groupId != null) {
            Group group = groupRepository.findById(groupId)
                    .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));
        }

        settlement = settlementRepository.save(settlement);
        return settlement.toDTO();
    }

    public SettlementDTO getSettlementById(Long id) {
        Settlement settlement = settlementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Settlement not found with id: " + id));
        return settlement.toDTO();
    }

    public List<SettlementDTO> getSettlementsByGroup(Long groupId) {
        return settlementRepository.findByGroupId(groupId).stream()
                .map(Settlement::toDTO)
                .collect(Collectors.toList());
    }

    public List<SettlementDTO> getUserSettlements(Long userId) {
        return settlementRepository.findByFromUserIdOrToUserId(userId, userId).stream()
                .map(Settlement::toDTO)
                .collect(Collectors.toList());
    }

    public List<SettlementDTO> getAllSettlements() {
        return settlementRepository.findAll().stream()
                .map(Settlement::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteSettlement(Long id) {
        if (!settlementRepository.existsById(id)) {
            throw new IllegalArgumentException("Settlement not found with id: " + id);
        }
        settlementRepository.deleteById(id);
    }
}
