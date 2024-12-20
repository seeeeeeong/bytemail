package techmail.domain.question.repository;

import techmail.domain.question.dto.QuestionResDto;
import techmail.domain.question.entity.Question;
import techmail.domain.question.entity.QuestionCategory;
import techmail.domain.user.entity.User;
import techmail.global.repository.Querydsl5RepositorySupport;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static techmail.domain.question.entity.QQuestion.question;
import static techmail.domain.userquestion.entity.QUserQuestion.userQuestion;


public class QuestionQueryRepositoryImpl extends Querydsl5RepositorySupport implements QuestionQueryRepository {

    public QuestionQueryRepositoryImpl() {
        super(Question.class);
    }

    @Override
    public Page<QuestionResDto> selectQuestionPageList(Pageable pageable) {
        return applyPagination(pageable,
                queryFactory ->
                        queryFactory.select(Projections.constructor(
                                        QuestionResDto.class,
                                        question.id,
                                        question.title,
                                        question.content,
                                        question.category
                                ))
                                .from(question),

                queryFactory ->
                        queryFactory.select(question.count())
                                .from(question)
        );
    }

    @Override
    public Optional<QuestionResDto> selectQuestionDetail(Long questionId) {
        return Optional.ofNullable(select(Projections.constructor(
                QuestionResDto.class,
                question.id,
                question.title,
                question.content,
                question.category
                ))
                .from(question)
                .where(question.id.eq(questionId))
                .fetchOne());
    }

    @Override
    public Optional<QuestionResDto> selectQuestionListNotIn(User user) {
        return Optional.ofNullable(
                select(
                        Projections.constructor(
                                QuestionResDto.class,
                                question.id,
                                question.title,
                                question.content,
                                question.category
                        ))
                        .from(question)
                        .where(question.id.notIn(
                                JPAExpressions.select(userQuestion.question.id)
                                        .from(userQuestion)
                                        .where(userQuestion.user.id.eq(user.getId())
                        )))
                        .orderBy(question.id.asc())
                        .limit(1)
                        .fetchFirst()
        );
    }

    @Override
    public List<QuestionResDto> findAllQuestions() {
        return select(
                Projections.constructor(
                        QuestionResDto.class,
                        question.id,
                        question.title,
                        question.content,
                        question.category
                ))
                .from(question)
                .fetch();
    }

    @Override
    public List<QuestionResDto> selectQuestionListByCategory(String category) {
        return select(
                Projections.constructor(
                        QuestionResDto.class,
                        question.id,
                        question.title,
                        question.content,
                        question.category
                ))
                .from(question)
                .where(question.category.eq(QuestionCategory.from(category)))
                .orderBy(question.id.asc())
                .fetch();
    }
}
