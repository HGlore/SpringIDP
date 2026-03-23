package com.idp.SpringIDP.service;

import com.idp.SpringIDP.entity.Instructions;
import com.idp.SpringIDP.repo.InstructionsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructionsService {

    private final InstructionsRepo instructionRepo;

    public Instructions getInstruction(int id) {
        return instructionRepo.findById(id);
    }

    /* void */
    public void putInstructionsData(Instructions instructionsData) {
        var instructions = instructionRepo.findById(instructionsData.getId());
        instructions.setLine(instructionsData.getLine());

        instructionRepo.save(instructions);
    }
}
