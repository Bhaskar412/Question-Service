package com.bhaskar.questionservice.controller;


import com.bhaskar.questionservice.model.Question;
import com.bhaskar.questionservice.model.QuestionWrapper;
import com.bhaskar.questionservice.model.Response;
import com.bhaskar.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }


    @GetMapping("category/{cat}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable("cat") String category){
        return  questionService.getQuestionsByCategory(category);

    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        //System.out.println(question);
                 return questionService.addQuestion(question);

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id){
        return questionService.deleteQuestion(id);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable Integer id, @RequestBody Question question){
        return questionService.updateQuestion(id,question);
    }

    @PatchMapping("updateByFields/{id}")
    public  ResponseEntity<Question> updateQuestionByField(@PathVariable Integer id, @RequestBody Map<String, Object> fields){
        return questionService.updateQuestionByField(id,fields);
    }

    @GetMapping("generate")
public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category,@RequestParam Integer numQuestions){
        return questionService.getQuestionsForQuiz(category,numQuestions);
    }

    @PostMapping("getQuestions")
    public  ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
         return questionService.getQuestionsFromIds(questionIds);
    }

    @PostMapping("getscore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }

}
