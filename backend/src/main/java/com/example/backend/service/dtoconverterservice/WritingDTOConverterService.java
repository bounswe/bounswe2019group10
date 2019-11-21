package com.example.backend.service.dtoconverterservice;

import com.example.backend.model.writing.Writing;
import com.example.backend.model.writing.WritingDTO;
import com.example.backend.service.writing.WritingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WritingDTOConverterService {

    @Autowired
    private WritingService writingService;

    public WritingDTO apply(Writing writing) {
        WritingDTO writingDTO = new WritingDTO();

        writingDTO.setId(writing.getId());
        writingDTO.setLanguageId(writing.getLanguageId());
        writingDTO.setTaskText(writing.getTaskText());

        return writingDTO;

    }

}
