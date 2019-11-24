package com.example.yallp_android.models;

public class NonCompletedAssignments extends CompletedWritings {

    public NonCompletedAssignments(String answerText, int assignedMemberId, int id, int memberId,
                                   int score, boolean scored, int writingId, String writingName,
                                   String assignedMemberName, String memberName) {
        super(answerText, assignedMemberId, id, memberId, score, scored, writingId, writingName, assignedMemberName, memberName);
    }
}
