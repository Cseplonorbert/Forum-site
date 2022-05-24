package com.codecool.Forum.service;

import com.codecool.Forum.exception.AnswerNotFoundException;
import com.codecool.Forum.model.Answer;
import com.codecool.Forum.model.Question;
import com.codecool.Forum.model.Vote;
import com.codecool.Forum.reporsitory.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer add(Question question, Answer answer) {
        return answerRepository.save(Answer.builder()
                .message(answer.getMessage())
                .question(question)
                .build());
    }

    public List<Answer> getAnswersByQuestionId(Long id) {
        return answerRepository.findAnswersByQuestionId(id);
    }

    public Answer getAnswerById(Long id) {
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        }
        throw new AnswerNotFoundException(id);
    }

    public Question deleteAnswer(Long id) {
        Answer answer = getAnswerById(id);
        answerRepository.delete(answer);
        return answer.getQuestion();
    }

    public Question voteOnAnswer(Long id, String vote) {
        Answer answer = getAnswerById(id);
        Vote voteDir = Vote.valueOf(vote);
        answer.setVoteNumber(answer.getVoteNumber() + voteDir.getValue());
        return answer.getQuestion();
    }

    public Question update(Long id, String message) {
        Answer answer = getAnswerById(id);
        answer.setMessage(message);
        answerRepository.save(answer);
        return answer.getQuestion();
    }
}
