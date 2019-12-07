package com.example.backend.repository.message;


import com.example.backend.model.message.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {

    @Query(nativeQuery = true, value = "select * from conversation c where c.member1_id = :mem_id or c.member2_id = :mem_id")
    List<Conversation> getAllByMemberId(@Param("mem_id") Integer memId);


    Conversation getAllByMember1IdAndMember2Id(int member1Id, int member2Id);

}
