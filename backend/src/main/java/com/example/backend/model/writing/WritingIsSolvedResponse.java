package com.example.backend.model.writing;

public class WritingIsSolvedResponse {

    private WritingDTO writingDTO;
    private boolean isSolved;
    private WritingResultDTO writingResultDTO;


    public WritingDTO getWritingDTO() {
        return writingDTO;
    }

    public void setWritingDTO(WritingDTO writingDTO) {
        this.writingDTO = writingDTO;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public WritingResultDTO getWritingResultDTO() {
        return writingResultDTO;
    }

    public void setWritingResultDTO(WritingResultDTO writingResultDTO) {
        this.writingResultDTO = writingResultDTO;
    }
}
