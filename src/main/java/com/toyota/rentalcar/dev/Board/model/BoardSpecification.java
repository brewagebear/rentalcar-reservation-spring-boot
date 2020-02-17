package com.toyota.rentalcar.dev.Board.model;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardSpecification {

    public enum SearchKey{
        TITLE("t"),
        WRITER("w"),
        CONTENT("c"),
        TITLE_WITH_CONTENT("tc");

        private final String value;

        SearchKey(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static Specification<Board> getArticleByCategoryAndHeader(final BoardCategory category, final BoardType type){
        return ((root, query, criteriaBuilder) -> {
           List<Predicate> predicates = new ArrayList<>();

           Predicate predicateForCategory = criteriaBuilder.equal(root.<Board>get("boardCategory"), category);
           Predicate predicateForHeader = criteriaBuilder.equal(root.<Board>get("boardType"), type);
           predicates.add(criteriaBuilder.and(predicateForCategory, predicateForHeader));

           return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    public static Specification<Board> getPredicateWithKeyword(Map<String, Object> filter) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            filter.forEach((key, value) -> {
                String likeValueString = "%" + value + "%";
                switch (key){
                    case "t":
                        predicates.add(criteriaBuilder.like(root.get("title").as(String.class), likeValueString));
                        break;
                    case "w":
                        predicates.add(criteriaBuilder.like(root.get("userName").as(String.class), likeValueString));
                        break;
                    case "c":
                        predicates.add(criteriaBuilder.like(root.get("content").as(String.class), likeValueString));
                        break;
                    case "tc":
                        Predicate predicateForTitle = criteriaBuilder.like(root.get("title").as(String.class), likeValueString);
                        Predicate predicateForContent = criteriaBuilder.like(root.get("content").as(String.class), likeValueString);
                        predicates.add(criteriaBuilder.and(predicateForTitle, predicateForContent));
                        break;
                    default:
                        break;
                }
            });
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
