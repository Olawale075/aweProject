package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @GetMapping
    public List<Employee> getAllEmployees(){
        return  employeeRepository.findAll();
    }

    // build create employee REST API
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee )
    {
        return employeeRepository.save(employee);
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById (@PathVariable long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id" + id));
        return ResponseEntity.ok(employee);
    }@PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id ,@RequestBody Employee  employeeDetails){
        Employee updateEmployee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not exist with id" + id));
        updateEmployee.setFirstName(employeeDetails.getFirstName());
        updateEmployee.setLastName(employeeDetails.getLastName());
        updateEmployee.setEmailId(employeeDetails.getEmailId());
        employeeRepository.save(updateEmployee);

        return ResponseEntity.ok(updateEmployee);
    }
    @DeleteMapping ("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable  long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employe not exist with id :" + id));
        employeeRepository.delete(employee);
        return  ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
