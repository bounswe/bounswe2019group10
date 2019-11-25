package com.example.yallp_android.models;

public class WritingExerciseElement {
    private WritingDTO writingDTO;
    private String[] usernames;

    public String[] getUsernames() {        return usernames;    }

    public void setUsernames(String[] usernames) {        this.usernames = usernames;    }

    public WritingDTO getWritingDTO() {
        return writingDTO;
    }

    public void setWritingDTO(WritingDTO writingDTO) {
        this.writingDTO = writingDTO;
    }

}

