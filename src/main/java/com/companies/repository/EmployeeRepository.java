package com.companies.repository;

import com.companies.entity.Employee;
import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    @Query("SELECT e FROM Employee e")
    List<Employee> findAllEmployees();

    @Query("SELECT e FROM Employee e ORDER BY e.id ASC")
    List<Employee> findAllEmployeesOrderBy();

    @Query(value = "SELECT e.name, COUNT(e) FROM Employee e GROUP BY e.name")
    List<String> findAllEmployeesGroupByName();

    @Query(value = "SELECT e FROM Employee e WHERE e.name = :name")
    List<Employee> findEmployeesByName(@NotNull String name);

    @Query(value = "SELECT e FROM Employee e WHERE e.salary IS NULL")
    List<Employee> findEmployeesWhereSalaryIsNull();

    @Query(value = "SELECT e FROM Employee e WHERE e.professions IS EMPTY")
    List<Employee> findEmployeesWhereProfessionsIsEmpty();

    @Query(value = "SELECT e FROM Employee e WHERE e.salary IS NOT NULL")
    List<Employee> findEmployeesWhereSalaryIsNotNull();

    @Query(value = "SELECT DISTINCT e FROM Employee e WHERE e.name = :name")
    List<Employee> findEmployeesByNameDistinct(@NotNull String name);

    @Query(value = "SELECT e FROM Employee e WHERE e.name = :name AND e.surname =:surname")
    List<Employee> findEmployeesByNameAndSurname(@NotNull String name, @NotNull String surname);

    @Query(value = "SELECT e FROM Employee e WHERE e.name = :name OR e.surname =:surname")
    List<Employee> findEmployeesByNameOrSurname(@NotNull String name, @NotNull String surname);

    @Query(value = "SELECT e FROM Employee e WHERE e.salary BETWEEN  :salaryFrom AND :salaryTo ORDER BY e.salary ASC")
    List<Employee> findEmployeesBySalaryBetween(@NotNull Double salaryFrom, @NotNull Double salaryTo);

    @Query(value = "SELECT e FROM Employee e WHERE e.isBusy = TRUE")
    List<Employee> findEmployeesByIsBusyConstant();

    @Query("SELECT e FROM Employee e INNER JOIN e.company c where c.name =:name")
    List<Employee> findEmployeesCompanyName(@NotNull String name);

    @Query("SELECT DISTINCT e FROM Employee e INNER JOIN e.professions p WHERE p.id IN :ids")
    List<Employee> findEmployeesByProfessionIds(@NotNull List<Long> ids);

    @Query("SELECT e FROM Employee e WHERE e.id NOT IN (SELECT DISTINCT emp.id FROM Employee emp JOIN emp.professions p)")
    List<Employee> findEmployeesWhereProfessionIsNull();

    @Query("SELECT e FROM Employee e WHERE e.id IN (SELECT DISTINCT e2.id FROM Employee e2 JOIN e2.professions) ORDER BY e.id ASC")
    List<Employee> findAllEmployeesWhereProfessionIsNotNull();
}
