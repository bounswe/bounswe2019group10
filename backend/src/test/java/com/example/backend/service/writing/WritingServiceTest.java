package com.example.backend.service.writing;

import com.example.backend.model.writing.Suggestion;
import com.example.backend.model.writing.WritingDTO;
import com.example.backend.repository.writing.SuggestionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WritingServiceTest {

    @InjectMocks
    private WritingService writingService;

    @Mock
    private SuggestionRepository suggestionRepository;


    @Test
    public void shouldSuggestAWriting() {
        Suggestion suggestion = new Suggestion();
        suggestion.setLanguageId(1);
        suggestion.setSuggestorUsername("test");
        suggestion.setTaskText("test text");
        suggestion.setWritingName("test writing name");

        WritingDTO writingDTO = new WritingDTO();
        writingDTO.setLanguageId(1);
        writingDTO.setTaskText("test text");
        writingDTO.setWritingName("test writing name");

        when(suggestionRepository.save(suggestion)).thenReturn(suggestion);

        Suggestion actualSuggestion = writingService.addNewWriting(writingDTO, "test");

        assertEquals(suggestion.getSuggestorUsername(),actualSuggestion.getSuggestorUsername());
    }

}