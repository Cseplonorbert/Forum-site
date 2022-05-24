package com.codecool.Forum.service.constants;

import com.codecool.Forum.model.SortBy;
import org.springframework.data.domain.Sort;

public class QuestionServiceConstants {
    public static final Integer MAX_PAGE_SIZE = 50;
    public static final Integer MIN_PAGE_SIZE = 0;
    public static final Integer DEFAULT_PAGE_SIZE = 15;

    public static final Sort.Direction DEFAULT_DIRECTION = Sort.Direction.ASC;
    public static final SortBy DEFAULT_SORT_BY = SortBy.SUBMISSION_TIME;
}
