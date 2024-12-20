package techmail.domain.question.controller;

import techmail.domain.question.dto.QuestionResDto;
import techmail.domain.question.service.QuestionService;
import techmail.global.response.ApiResponse;
import techmail.global.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/question")
    public ApiResponse<PageResponse<QuestionResDto>> getQuestionList(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.success(questionService.getQuestionList(pageable));
    }

    @GetMapping("/question/{questionId}")
    public ApiResponse<QuestionResDto> getQuestionDetail(@PathVariable Long questionId) {
        return ApiResponse.success(questionService.getQuestionDetail(questionId));
    }

}
