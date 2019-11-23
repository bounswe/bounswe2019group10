package com.example.backend.service.search;

import com.example.backend.model.member.MemberLanguage;
import com.example.backend.model.quiz.Quiz;
import com.example.backend.model.search.TagSimilarity;
import com.example.backend.model.writing.Writing;
import com.example.backend.repository.search.TagSimilarityRepository;
import com.example.backend.repository.quiz.QuizRepository;
import com.example.backend.repository.writing.WritingRepository;
import com.example.backend.service.language.LanguageService;
import com.example.backend.service.member.JwtUserDetailsService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
public class SearchService {

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.url}")
    private String apiURL;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    WritingRepository writingRepository;

    @Autowired
    TagSimilarityRepository tagSimilarityRepository;

    @Autowired
    LanguageService languageService;

    public List<Quiz> quizSearchResult(String searchTerm, int languageId){

        List<String> tags = quizRepository.getDistinctQuizTypes();

        Comparator<TagSimilarity> similarityComparator = Comparator.comparing(TagSimilarity::getComparator);
        Queue<TagSimilarity> tagQueue = new PriorityQueue<>(similarityComparator);

        tags.forEach(tag -> {

            TagSimilarity tagSimilarity = tagSimilarityRepository.findBySearchTermAndTag(searchTerm, tag);

            if(tagSimilarity == null){
                double similarity = getSimilarity(searchTerm, tag);
                tagSimilarity = new TagSimilarity(searchTerm, tag, similarity);
                tagSimilarityRepository.save(tagSimilarity);
            }

            if(tagSimilarity.getSimilarity() > 0.1){
                tagQueue.add(tagSimilarity);
            }
        });

        List<Quiz> result = new ArrayList<>();

        int languageLevel = languageService.getLanguageLevel(languageId);

        while (!tagQueue.isEmpty()){
            String tag = tagQueue.remove().getTag();
            ArrayList<Quiz> quizArr = (ArrayList<Quiz>) quizRepository.getAllByQuizTypeAndLanguageId(tag, languageId);
            quizArr.forEach(quiz -> {
                if(quiz.getLevel() <= languageLevel){
                    result.add(quiz);
                }
            });
        }

        return result;
    }

    public List<Writing> writingSearchResult(String searchTerm, int languageId){
        List<String> taskTexts = writingRepository.getDistinctTaskTexts();

        Comparator<TagSimilarity> similarityComparator = Comparator.comparing(TagSimilarity::getComparator);
        Queue<TagSimilarity> tagQueue = new PriorityQueue<>(similarityComparator);

        taskTexts.forEach(taskText -> {

            TagSimilarity tagSimilarity = tagSimilarityRepository.findBySearchTermAndTag(searchTerm, taskText);

            if(tagSimilarity == null){
                double similarity = getSimilarity(searchTerm, taskText);
                tagSimilarity = new TagSimilarity(searchTerm, taskText, similarity);
                tagSimilarityRepository.save(tagSimilarity);
            }

            if(tagSimilarity.getSimilarity() > 0.2){
                tagQueue.add(tagSimilarity);
            }
        });

        List<Writing> result = new ArrayList<>();

        while (!tagQueue.isEmpty()){
            String taskText = tagQueue.remove().getTag();
            result.addAll(writingRepository.getAllByTaskTextAndLanguageId(taskText, languageId));
        }

        return result;
    }

    private double getSimilarity(String searchTerm, String tag) {

        searchTerm = searchTerm.replace(" ", "%20");
        tag = tag.replace(" ", "%20");

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://twinword-text-similarity-v1.p.rapidapi.com/similarity/?" +
                        "text1=" + searchTerm +
                        "&text2=" + tag)
                .get()
                .addHeader("x-rapidapi-host", apiURL)
                .addHeader("x-rapidapi-key", apiKey)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException ignored) {

        }

        String body = null;
        try {
            body = response.body().string();
        } catch (IOException ignored) {

        }
        JSONParser jp = new JSONParser(body);

        Object val = null;
        try {
            val = jp.object().get("similarity");
        } catch (ParseException ignored) {

        }

        if(val instanceof  BigDecimal){
            return ((BigDecimal)val).doubleValue();
        }

        if(val instanceof  BigInteger){
            return ((BigInteger) val).doubleValue();
        }

        return 0.0;

    }

}
