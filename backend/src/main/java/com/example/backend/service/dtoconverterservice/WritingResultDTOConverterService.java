package com.example.backend.service.dtoconverterservice;

import com.example.backend.model.writing.WritingResult;
import com.example.backend.model.writing.WritingResultDTO;
import com.example.backend.service.writing.WritingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WritingResultDTOConverterService {

    @Autowired
    private WritingService writingService;

    public WritingResultDTO apply(WritingResult writing) {
        WritingResultDTO writingResultDTO = new WritingResultDTO();

        writingResultDTO.setId(writing.getId());
        writingResultDTO.setAnswerText(writing.getAnswerText());
        writingResultDTO.setAssignedMemberId(writing.getAssignedMemberId());
        writingResultDTO.setMemberId(writing.getMemberId());
        writingResultDTO.setAssignedMemberId(writing.getAssignedMemberId());
        writingResultDTO.setScore(writing.getScore());
        writingResultDTO.setWritingId(writing.getWritingId());
        writingResultDTO.setScored(writing.isScored());
        writingResultDTO.setWritingName(writing.getWritingName());
        return writingResultDTO;

    }


}
