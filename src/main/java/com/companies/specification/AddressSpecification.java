package com.companies.specification;

import com.companies.entity.Address;
import com.companies.entity.Address_;
import com.companies.entity.Company;
import com.companies.entity.Company_;
import com.companies.entity.Employee;
import com.companies.entity.Employee_;
import com.companies.entity.Profession;
import com.companies.entity.Profession_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressSpecification {

    //criteria builder 'like' example
    public static Specification<Address> nameLikeOrNull(String namePart, String nameKey) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (namePart == null || namePart.isBlank()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(criteriaBuilder.upper(root.get(nameKey)), "%" + namePart.toUpperCase() + "%");
        };
    }

    //criteria builder 'in' example
    public static Specification<Address> getAddressByIds(List<Long> ids) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (ids == null || ids.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Predicate predicate = root.get("id").in(ids);
            return criteriaBuilder.and(predicate);
        };
    }

    //criteria builder with 'join' example
    public static Specification<Address> getAddressByEmployeeName(String employeeName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (employeeName == null || employeeName.isBlank()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Join<Address, Employee> employeeJoin = root.join(Address_.EMPLOYEES);
            Expression<String> employeeJoinName = employeeJoin.get(Employee_.name);
            Predicate employeeNamePredicate = criteriaBuilder.like(employeeJoinName, "%" + employeeName + "%");
            return criteriaBuilder.and(employeeNamePredicate);
        };
    }

    public static Specification<Address> getAddressByCompanyName(String companyName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (companyName == null || companyName.isBlank()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Join<Address, Company> companyJoin = root.join(Address_.COMPANY);
            Expression<String> companyJoinName = companyJoin.get(Company_.name);
            Predicate companyNamePredicate = criteriaBuilder.like(companyJoinName, "%" + companyName + "%");
            return criteriaBuilder.and(companyNamePredicate);
        };
    }

    //criteria builder 'equal' 'distinct' 'join' example
    public static Specification<Address> getByAddressEmployeeIsBusy(Boolean isBusy) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (isBusy == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Join<Address, Employee> join = root.join(Address_.EMPLOYEES);
            Predicate predicate = criteriaBuilder.equal(join.get(Employee_.IS_BUSY), isBusy);
            criteriaQuery.distinct(true);
            return criteriaBuilder.and(predicate);
        };
    }

    //criteria builder with twice 'join' example
    public static Specification<Address> getByAddressEmployeeCompany(String addressEmployeeCompanyName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (addressEmployeeCompanyName == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Join<Address, Employee> join = root.join(Address_.EMPLOYEES);
            Predicate predicate = criteriaBuilder.equal(join.get(Employee_.COMPANY).get(Company_.NAME), addressEmployeeCompanyName);
            criteriaQuery.distinct(true);
            return criteriaBuilder.and(predicate);
        };
    }

//    public static Specification<Address> getByAddressEmployeeProfession(List<Long> professionIds) {
//        return (root, criteriaQuery, criteriaBuilder) -> {
//
//            criteriaQuery.distinct(true);
//
//            if (professionIds == null || professionIds.isEmpty()) {
//
//                Join<Address, Employee> employeeJoin = root.join(Address_.EMPLOYEES, JoinType.LEFT); // Use LEFT JOIN
//
//                // Use a subquery to check if the employeeJoin is empty (i.e., employees with no professions)
//                Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
//                Root<Employee> subRoot = subquery.from(Employee.class);
//                subquery.select(subRoot.get(Employee_.ID));
//                subquery.where(criteriaBuilder.isEmpty(subRoot.get(Employee_.PROFESSIONS)));
//                return criteriaBuilder.in(employeeJoin.get(Employee_.ID)).value(subquery);
//            } else {
//
//                Join<Address, Employee> employeeJoin = root.join(Address_.EMPLOYEES);
//
//                Subquery<Employee> subquery = criteriaQuery.subquery(Employee.class);
//                Root<Employee> subRoot = subquery.from(Employee.class);
//                subquery.select(subRoot);
//                subquery.where(subRoot.join(Employee_.PROFESSIONS).get(Profession_.ID).in(professionIds));
//
//                return criteriaBuilder.in(employeeJoin).value(subquery);
//            }
//        };
//    }

    //this is an optimized version of the above method
    public static Specification<Address> getByAddressEmployeeProfession(List<Long> professionIds) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);
            if (professionIds == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Join<Address, Employee> employeeJoin = root.join(Address_.EMPLOYEES);
            if (professionIds.isEmpty()) {
                return criteriaBuilder.equal(criteriaBuilder.size(employeeJoin.get(Employee_.PROFESSIONS)), 0);
            } else {
                Join<Employee, Profession> professionJoin = employeeJoin.join(Employee_.PROFESSIONS);
                return professionJoin.get(Profession_.ID).in(professionIds);
            }
        };
    }

    //criteria builder 'greaterThan' example
    public static Specification<Address> getByAddressEmployeeAgeGreaterThan(Integer age) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (age == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Join<Address, Employee> employeeJoin = root.join(Address_.EMPLOYEES);
            Expression<Integer> employeeJoinAge = employeeJoin.get(Employee_.AGE);
            Predicate employeeAgePredicate = criteriaBuilder.greaterThan(employeeJoinAge, age);
            return criteriaBuilder.and(employeeAgePredicate);
        };
    }

    //this method another way for get by greater than
//    public static Specification<Address> getByAgeGreaterThan(Integer age) {
//        return (root, criteriaQuery, criteriaBuilder) -> {
//            Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
//            Root<Address> subRoot = subquery.from(Address.class);
//            Join<Address, Employee> subEmployeeJoin = subRoot.join(Address_.EMPLOYEES);
//
//            subquery.select(subRoot.get(Address_.ID));
//            subquery.where(criteriaBuilder.greaterThan(subEmployeeJoin.get(Employee_.AGE), age));
//
//            return criteriaBuilder.in(root.get(Address_.ID)).value(subquery);
//        };
//    }
}
