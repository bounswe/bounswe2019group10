package com.example.yallp_android.models;

public class WritingListElement {
    private WritingDTO writingDTO;
    private WritingResultDTO writingResultDTO;
    private boolean solved;


    public WritingDTO getWritingDTO() {
        return writingDTO;
    }

    public WritingResultDTO getWritingResultDTO() {
        return writingResultDTO;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public void setWritingDTO(WritingDTO writingDTO) {
        this.writingDTO = writingDTO;
    }

    public void setWritingResultDTO(WritingResultDTO writingResultDTO) {
        this.writingResultDTO = writingResultDTO;
    }
}
