package com.example.backend.service.search;

import com.example.backend.model.quiz.Quiz;
import com.example.backend.model.search.TagSimilarity;
import com.example.backend.repository.TagSimilarityRepository;
import com.example.backend.repository.quiz.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    TagSimilarityRepository tagSimilarityRepository;

    public List<Quiz> quizSearchResult(String searchTerm){

        List<String> tags = quizRepository.getDistinctByQuizType();

        Comparator<TagSimilarity> similarityComparator = Comparator.comparing(TagSimilarity::getSimilarity);
        Queue<TagSimilarity> tagQueue = new PriorityQueue<>(similarityComparator);

        tags.forEach(tag -> {

            TagSimilarity tagSimilarity = tagSimilarityRepository.getBySearch_termAndTag(searchTerm, tag);

            if(tagSimilarity == null){
                double similarity = getSimilarity(searchTerm, tag);
                tagSimilarity = new TagSimilarity(searchTerm, tag, 1-similarity);
                tagSimilarityRepository.save(tagSimilarity);
            }

            if(tagSimilarity.getSimilarity() > 0){
                tagQueue.add(tagSimilarity);
            }
        });

        List<Quiz> result = new ArrayList<>();

        while (!tagQueue.isEmpty()){
            String tag = tagQueue.remove().getTag();
            result.addAll(quizRepository.getAllByQuizType(tag));
        }

        return result;
    }

    //TODO use 3rd party api for similarity calc
    private double getSimilarity(String searchTerm, String tag){
        return searchTerm.length() - tag.length();
    }

}
