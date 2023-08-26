package com.bhaskar.questionservice.repository;


import com.bhaskar.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {


    List<Question> findByCategory(String category);

  /*  @Query(value = "SELECT q.id FROM question q WHERE q.category=:category ORDER BY RAND() LIMIT :numQ",nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int numQ);*/
  @Query(value = "SELECT q.id FROM question q WHERE q.category=:category ORDER BY RAND() LIMIT :numQ",nativeQuery = true)
  List<Integer> findRandomQuestionsByCategory(String category, int numQ);



  @Query(value = "SELECT DISTINCT category FROM question",nativeQuery = true)
    List<String> getCategories();
}
