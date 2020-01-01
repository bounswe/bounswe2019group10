package com.example.yallp_android.models;

public class NonCompletedAssignments extends CompletedWritings {

    public NonCompletedAssignments(String answerText, int assignedMemberId, int id, int memberId,
                                   int score, boolean scored, boolean image, int writingId, String writingName,
                                   String assignedMemberName, String memberName, String imageUrl) {
        super(answerText, assignedMemberId, id, memberId, score, scored,image,writingId, writingName, assignedMemberName, memberName,imageUrl);
    }
}
