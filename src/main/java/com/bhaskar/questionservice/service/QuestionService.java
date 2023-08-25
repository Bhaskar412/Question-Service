package com.bhaskar.questionservice.service;


import com.bhaskar.questionservice.dao.QuestionDao;
import com.bhaskar.questionservice.model.Question;
import com.bhaskar.questionservice.model.QuestionWrapper;
import com.bhaskar.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionDao.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        return  new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
    }

    public  ResponseEntity<String> addQuestion(Question question) {
          questionDao.save(question);
          return  new ResponseEntity<>("Question added", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        questionDao.deleteById(id);
        return  new ResponseEntity<>("Question deleted", HttpStatus.OK);

    }

    public ResponseEntity<String> updateQuestion(Integer id, Question question) {

            Question existingQuestion = questionDao.findById(id).get();
                        existingQuestion.setQuestionTitle(question.getQuestionTitle());
                        existingQuestion.setCategory(question.getCategory());
                        existingQuestion.setOption1(question.getOption1());
                        existingQuestion.setOption2(question.getOption2());
                        existingQuestion.setOption3(question.getOption3());
                        existingQuestion.setOption4(question.getOption4());
                        existingQuestion.setDifficultyLevel(question.getDifficultyLevel());
                        existingQuestion.setRightAnswer(question.getRightAnswer());
                questionDao.save(existingQuestion);
                return  new ResponseEntity<>("Question Updated", HttpStatus.OK);

    }

    public ResponseEntity<Question> updateQuestionByField(Integer id, Map<String,Object> fields) {

        Optional<Question> existingQuestion = questionDao.findById(id);

        if (existingQuestion.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Question.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingQuestion.get(), value);
            });
            Question save = questionDao.save(existingQuestion.get());
            return  new ResponseEntity<>(save,HttpStatus.ACCEPTED);
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQuestions) {
        List<Integer> randomQuestionsByCategory = questionDao.findRandomQuestionsByCategory(category,numQuestions);
        return  new ResponseEntity<>(randomQuestionsByCategory,HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(List<Integer> questionIds) {
List<QuestionWrapper>   wrappers = new ArrayList<>();

        List<Question> questions = new ArrayList<>();
                questionDao.findAllById(questionIds);

                for(Integer id: questionIds){
                    questions.add(questionDao.findById(id).get());
                }

        for (Question q: questions){
            QuestionWrapper questionWrapper = new QuestionWrapper();
            questionWrapper.setQuestionTitle(q.getQuestionTitle());
            questionWrapper.setId(q.getId());
            questionWrapper.setOption1(q.getOption1());
            questionWrapper.setOption2(q.getOption2());
            questionWrapper.setOption3(q.getOption3());
            questionWrapper.setOption4(q.getOption4());
            wrappers.add(questionWrapper);

        }

        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        Integer right = 0;
        for (Response response : responses) {
            Optional<Question> question = questionDao.findById(response.getId());
            if(response.getResponse().equals(question.get().getRightAnswer()))
                right++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);

    }
}
