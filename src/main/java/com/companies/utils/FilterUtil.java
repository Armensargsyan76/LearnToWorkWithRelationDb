package com.companies.utils;

import java.util.HashMap;
import java.util.Map;

public class FilterUtil {

    public static final Map<String, String> addressFilter = new HashMap<>();
    public static final Map<String, String> employeeFilter = new HashMap<>();

    static {
        addressFilter.put("id", "id");
        addressFilter.put("country", "country");
        addressFilter.put("city", "city");
        addressFilter.put("streetName", "streetName");
        addressFilter.put("addressCompanyName", "company.name");
        addressFilter.put("addressEmployeeName", "employees.name");
        addressFilter.put("addressEmployeeCompanyName", "employees.company.name");
        addressFilter.put("isAddressEmployeeBusy", "isAddressEmployeeBusy");

        employeeFilter.put("id", "id");
        employeeFilter.put("country", "address.country");
        employeeFilter.put("city", "address.city");
        employeeFilter.put("streetName", "address.streetName");
        employeeFilter.put("CompanyName", "company.name");
        employeeFilter.put("name", "name");
        employeeFilter.put("surname", "surname");
        employeeFilter.put("age", "age");
        employeeFilter.put("salary", "salary");
        employeeFilter.put("phone", "phone");
        employeeFilter.put("departmentName", "department.name");
        employeeFilter.put("isBusy", "isBusy");
        employeeFilter.put("professionName", "professions.name");
    }

}
