package com.example.backend.model.writing;

import java.util.List;

public class WritingResponse {

    private WritingDTO writingDTO;

    private List<String> usernames;

    public WritingDTO getWritingDTO() {
        return writingDTO;
    }

    public void setWritingDTO(WritingDTO writingDTO) {
        this.writingDTO = writingDTO;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
}
