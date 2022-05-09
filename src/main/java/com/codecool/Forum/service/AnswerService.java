package com.codecool.Forum.service;

import com.codecool.Forum.exception.AnswerNotFoundException;
import com.codecool.Forum.model.Answer;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.Vote;
import com.codecool.Forum.reporsitory.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerService {

    AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void addNewAnswer(Question question, String message) {
        Answer answer = Answer.builder()
                .message(message)
                .question(question)
                .build();
        answerRepository.save(answer);
    }

    public Answer getAnswerById(Long id) {
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        }
        throw new AnswerNotFoundException("Answer Not Found");
    }

    public Question deleteAnswer(Long id) {
        Answer answer = getAnswerById(id);
        answerRepository.delete(answer);
        return answer.getQuestion();
    }

    public Question voteOnAnswer(Long id, String vote) {
        Answer answer = getAnswerById(id);
        Vote voteDir = Vote.valueOf(vote);
        answer.setNumberOfVotes(answer.getNumberOfVotes() + voteDir.getValue());
        return answer.getQuestion();
    }
}
