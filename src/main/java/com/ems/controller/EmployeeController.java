package com.ems.controller;

import com.ems.exceptions.ResourceNotFoundException;
import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("api")
public class EmployeeController
{
    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee)
    {
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee> >  getAllEmployee()
    {
        return ResponseEntity.ok(employeeRepository.findAll());
        //return employeeRepository.findAll();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Integer employeeId)
            throws ResourceNotFoundException
    {
        Employee employee = employeeRepository.findById(employeeId).
                orElseThrow(()->new ResourceNotFoundException("Employee not found for this ID :: " + employeeId));
        return ResponseEntity.ok().body(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Integer employeeId,
    @RequestBody Employee employeeDetails)
            throws ResourceNotFoundException
    {
        Employee employee = employeeRepository.findById(employeeId).
                orElseThrow(()->new ResourceNotFoundException("Employee not found for this ID :: " + employeeId));

        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPhone(employeeDetails.getPhone());
        employee.setDepartment(employeeDetails.getDepartment());

        final Employee updatedEmployee = employeeRepository.save(employee);

        return ResponseEntity.ok().body(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public Map<String,Boolean> deleteEmployee(@PathVariable(value = "id") Integer employeeId)
            throws ResourceNotFoundException
    {
        Employee employee = employeeRepository.findById(employeeId).
                orElseThrow(()->new ResourceNotFoundException("Employee not found for this ID :: " + employeeId));

        employeeRepository.delete(employee);

        Map<String,Boolean> response = new HashMap<>();
        response.put("DELETED",true);


        return response;
    }


}
