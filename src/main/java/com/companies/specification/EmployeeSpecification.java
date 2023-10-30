package com.companies.specification;

import com.companies.entity.Employee;
import com.companies.entity.Employee_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeSpecification {

    //criteria builder 'or' example
    public static Specification<Employee> getNameOrSurname(String name, String surname) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (name == null && surname == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Predicate predicateName = criteriaBuilder.equal(root.get("name"), name);
            Predicate predicateSurName = criteriaBuilder.equal(root.get("surname"), surname);
            Predicate nameOrSurnamePredicate = criteriaBuilder.or(predicateName, predicateSurName);
            return criteriaBuilder.and(nameOrSurnamePredicate);
        };
    }

    //criteria builder 'and' example
    public static Specification<Employee> getNameAndSurname(String name, String surname) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (name == null && surname == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Predicate predicateName = criteriaBuilder.equal(root.get("name"), name);
            Predicate predicateSurName = criteriaBuilder.equal(root.get("surname"), surname);
            Predicate nameOrSurnamePredicate = criteriaBuilder.or(predicateName, predicateSurName);
            return criteriaBuilder.and(nameOrSurnamePredicate);
        };
    }

    //criteria builder 'not' example
    public static Specification<Employee> getNotValue(String value, String nameKey) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (value == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Predicate predicate = criteriaBuilder.equal(root.get(nameKey), value);
            Predicate whereNotPredicate = criteriaBuilder.not(predicate);
            return criteriaBuilder.and(whereNotPredicate);
        };
    }

    //criteria builder 'lessThan' example
    public static Specification<Employee> getByLessThenAge(Integer age) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (age == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Expression<Integer> ageExpression = root.get("age");
            Predicate lessThen = criteriaBuilder.lessThan(ageExpression, age);
            return criteriaBuilder.and(lessThen);
        };
    }

    //criteria builder 'between' example
    public static Specification<Employee> getByBetweenSalary(Double startSalary, Double endSalary) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (startSalary == null && endSalary ==null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Predicate betweenSalary = criteriaBuilder.between(root.get(Employee_.SALARY).as(Double.class),
                    startSalary, endSalary);
            return criteriaBuilder.and(betweenSalary);
        };
    }
}
