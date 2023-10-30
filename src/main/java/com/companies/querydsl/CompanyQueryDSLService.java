package com.companies.querydsl;

import com.companies.dto.company.CompanyFilterRequestDto;
import com.companies.entity.Company;
import com.companies.entity.QCompany;
import com.companies.mapper.CompanyMapper;
import com.companies.utils.ListWrapper;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.companies.entity.QAddress.address;
import static com.companies.entity.QDepartment.department;
import static com.companies.entity.QEmployee.employee;
import static com.companies.entity.QProfession.profession;
import static com.companies.utils.MakeResponseService.makeResponseForList;

@Service
@RequiredArgsConstructor
public class CompanyQueryDSLService {

    @PersistenceContext
    private EntityManager entityManager;
    private final CompanyMapper companyMapper;
    private final QCompany company = QCompany.company;

    public ListWrapper<?> getByFilterType(CompanyFilterRequestDto requestDto) {

        switch (requestDto.getFilterType()) {

            case "getByName" -> {
                List<Company> companies = getByName(requestDto.getName());
                return makeResponseForList(companies.stream().map(companyMapper::toResponseDto)
                        .collect(Collectors.toList()), (long) companies.size());

            }
            case "getByNameOrderByAsc" -> {
                List<Company> companies = getByNameOrderByAsc();
                return makeResponseForList(companies.stream().map(companyMapper::toResponseDto)
                        .collect(Collectors.toList()), (long) companies.size());
            }
            case "getGroupByCompanyName" -> {
                List<String> companyNames = getGroupByCompanyName();
                return makeResponseForList(new ArrayList<>(companyNames), (long) companyNames.size());
            }
            case "getByNameContains" -> {
                List<Company> companies = getByNameContains(requestDto.getName());
                return makeResponseForList(companies.stream().map(companyMapper::toResponseDto)
                        .collect(Collectors.toList()), (long) companies.size());
            }
            case "getByMultipleFilters" -> {
                List<Company> companies = getByMultipleFilters(requestDto.getName(), requestDto.getEmployeeCount());
                return makeResponseForList(companies.stream().map(companyMapper::toResponseDto)
                        .collect(Collectors.toList()), (long) companies.size());
            }
            case "getByEmployeeCountBetween" -> {
                List<Company> companies = getByEmployeeCountBetween(requestDto.getStartEmployeeCount(), requestDto.getEndEmployeeCount());
                return makeResponseForList(companies.stream().map(companyMapper::toResponseDto)
                        .collect(Collectors.toList()), (long) companies.size());
            }
            case "getByNameOrEmployeeCount" -> {
                List<Company> companies = getByNameOrEmployeeCount(requestDto.getName(), requestDto.getEmployeeCount());
                return makeResponseForList(companies.stream().map(companyMapper::toResponseDto)
                        .collect(Collectors.toList()), (long) companies.size());
            }
            case "getByNameAndEmployeeCount" -> {
                List<Company> companies = getByNameAndEmployeeCount(requestDto.getName(), requestDto.getEmployeeCount());
                return makeResponseForList(companies.stream().map(companyMapper::toResponseDto)
                        .collect(Collectors.toList()), (long) companies.size());
            }
            case "getByAddressCountry" -> {
                List<Company> companies = getByAddressCountry(requestDto.getAddressCountry());
                return makeResponseForList(companies.stream().map(companyMapper::toResponseDto)
                        .collect(Collectors.toList()), (long) companies.size());
            }
            case "getByDepartmentIds" -> {
                List<Company> companies = getByDepartmentIds(requestDto.getDepartmentIds());
                return makeResponseForList(companies.stream().map(companyMapper::toResponseDto)
                        .collect(Collectors.toList()), (long) companies.size());
            }
            case "getByEmployeeProfessionIds" -> {
                List<Company> companies = getByEmployeeProfessionIds(requestDto.getCompanyEmployeeProfessionIds());
                return makeResponseForList(companies.stream().map(companyMapper::toResponseDto)
                        .collect(Collectors.toList()), (long) companies.size());
            }
            default -> {
                return null;
            }
        }
    }

    public List<Company> getByName(String name) {

        JPAQuery<Company> query = new JPAQuery<>(entityManager);
        return query.from(company)
                .where(company.name.eq(name))
                .fetch();
    }

    public List<Company> getByNameOrderByAsc() {

        JPAQuery<Company> query = new JPAQuery<>(entityManager);
        return query.from(company)
                .orderBy(company.name.asc())
                .fetch();
    }

    public List<String> getGroupByCompanyName() {

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.select(company.name).from(company)
                .groupBy(company.name)
                .fetch();
    }

    public List<Company> getByNameContains(String name) {

        JPAQuery<Company> query = new JPAQuery<>(entityManager);
        return query.from(company)
                .where(company.name.contains(name))
                .fetch();
    }

    public List<Company> getByMultipleFilters(String name, int employeeCount) {

        JPAQuery<Company> query = new JPAQuery<>(entityManager);
        return query.from(company)
                .where(company.name.eq(name), company.employeeCount.eq(employeeCount))
                .fetch();
    }

    public List<Company> getByEmployeeCountBetween(Integer from, Integer to) {

        JPAQuery<Company> query = new JPAQuery<>(entityManager);
        return query.from(company)
                .where(company.employeeCount.between(from, to))
                .fetch();
    }

    public List<Company> getByNameOrEmployeeCount(String name, int employeeCount) {

        JPAQuery<Company> query = new JPAQuery<>(entityManager);
        return query.from(company)
                .where(company.name.eq(name).or(company.employeeCount.eq(employeeCount)))
                .fetch();
    }

    public List<Company> getByNameAndEmployeeCount(String name, int employeeCount) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(company)
                .where(company.name.eq(name).and(company.employeeCount.eq(employeeCount)))
                .fetch();
    }

    public List<Company> getByAddressCountry(String addressCountry) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory
                .selectFrom(company)
                .innerJoin(company.address, address)
                .where(address.country.eq(addressCountry))
                .fetch();
    }

    public List<Company> getByDepartmentIds(List<Long> departmentIds) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory
                .selectFrom(company)
                .innerJoin(company.departments, department)
                .where(department.id.in(departmentIds))
                .distinct()
                .fetch();
    }

    public List<Company> getByEmployeeProfessionIds(List<Long> professionIds) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory
                .selectFrom(company)
                .innerJoin(company.employees, employee)
                .innerJoin(employee.professions, profession)
                .where(profession.id.in(professionIds))
                .distinct()
                .fetch();
    }
}
