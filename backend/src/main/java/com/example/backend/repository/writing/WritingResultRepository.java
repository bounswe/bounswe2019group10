package com.example.backend.repository.writing;

import com.example.backend.model.writing.WritingResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WritingResultRepository extends JpaRepository<WritingResult, Integer> {
    //Returns the writing results of a specified member
    List<WritingResult> findAllByMemberId(@Param("mem_id") Integer memId);

    @Query(nativeQuery = true, value = "select * from writing_result w where w.assigned_member_id = :mem_id and w.score is not null")
    List<WritingResult> findAllCompleteByAssignedId(@Param("mem_id") Integer memId);

    @Query(nativeQuery = true, value = "select * from writing_result w where w.assigned_member_id = :mem_id and w.score is null")
    List<WritingResult> findAllNonCompleteByAssignedId(@Param("mem_id") Integer memId);

    WritingResult findByWritingIdAndMemberId(Integer writingId, Integer memberId);

}
