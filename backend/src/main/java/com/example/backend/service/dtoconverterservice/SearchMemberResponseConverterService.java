package com.example.backend.service.dtoconverterservice;

import com.example.backend.model.member.Member;
import com.example.backend.model.search.SearchMemberResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchMemberResponseConverterService {

    public SearchMemberResponse apply(Member member){
        SearchMemberResponse searchMemberResponse = new SearchMemberResponse();
        searchMemberResponse.setUsername(member.getUsername());
        searchMemberResponse.setId(member.getId());
        return searchMemberResponse;
    }


    public List<SearchMemberResponse> applyAll(List<Member> memberList){
        List<SearchMemberResponse> list = new ArrayList<>();

        memberList.forEach(member -> {
            list.add(apply(member));
        });

        return list;
    }


}
