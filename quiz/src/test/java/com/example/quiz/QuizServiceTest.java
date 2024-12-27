package com.example.quiz;


import com.example.quiz.dto.QuizDto;
import com.example.quiz.service.QuizService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class QuizServiceTest {

    @Autowired
    private QuizService quizService;

    @Test
    void testQuiz(){

        QuizDto quiz = new QuizDto("질문1",true,"테스터1");

        int result = quizService.addQuiz(quiz);
        System.out.println(quiz);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void listAllTest(){
        QuizDto quiz = new QuizDto("질문1",true,"테스터1");
        quizService.addQuiz(quiz);

        List<QuizDto> result = quizService.listAll();

        System.out.println(result);
        System.out.println(quiz.getId());

        assertThat(result.size()).isEqualTo(quiz.getId());
    }

    @Test
    void findQuizTest(){

        QuizDto quiz = new QuizDto("1+1=2?",true,"테스터1");
        quizService.addQuiz(quiz);

        QuizDto dto = quizService.findQuiz(quiz.getId());

        assertThat(dto.getQuestion()).isEqualTo(quiz.getQuestion());
        assertThat(dto.getAuthor()).isEqualTo(quiz.getAuthor());
    }

    @Test
    void changeQuizTest(){
        QuizDto quiz = new QuizDto("3+3=2?",true,"테스터1");
        quizService.addQuiz(quiz);

        quiz.setAnswer(false);
        int result = quizService.changeQuiz(quiz);

        assertThat(result).isEqualTo(1);
        //변경한 퀴즈의 답이 false로 변경된것이 맞는지 확인
        QuizDto check = quizService.findQuiz(quiz.getId());
        assertThat(check.getAnswer()).isFalse();

    }

    @Test
    void removeQuizTest(){
        QuizDto quiz = new QuizDto("3+3=6?",true,"테스터1");
        quizService.addQuiz(quiz);

        int result1 = quizService.removeQuiz(quiz.getId());

        QuizDto result2 = quizService.findQuiz(quiz.getId());

        assertThat(result1).isEqualTo(1);
        assertThat(result2).isNull();
    }

    @Test
    void randomQuizTest(){
        quizService.removeALL();

        List<QuizDto> addList = new ArrayList<>();

        addList.add(new QuizDto("1+1=2",true,"테스터1"));
        addList.add(new QuizDto("1+1=3",false,"테스터2"));
        addList.add(new QuizDto("1+6=7",true,"테스터3"));
        addList.add(new QuizDto("10+10=20",true,"테스터4"));
        addList.add(new QuizDto("8+3=11",true,"테스터5"));
        addList.add(new QuizDto("6+13=12",false,"테스터6"));

        for(QuizDto list : addList){
            quizService.addQuiz(list);
        }

        QuizDto randQuiz = quizService.randomQuiz();
        System.out.println(addList);
        System.out.println(randQuiz);

        assertThat(addList).contains(randQuiz);
    }

    @Test
    void playQuizTest(){
        quizService.removeALL();

        QuizDto quiz = new QuizDto("1+1=2",true,"테스터1");
        quizService.addQuiz(quiz);

        Map map = new HashMap();
        map.put("id", quiz.getId());
        map.put("answer", true);

        quizService.playQuiz(map);

        boolean result = quizService.playQuiz(map);

        assertThat(result).isTrue();

        map.put("id", quiz.getId());
        map.put("answer", false);

        result = quizService.playQuiz(map);
        assertThat(result).isFalse();
    }
}
