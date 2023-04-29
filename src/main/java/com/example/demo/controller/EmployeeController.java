package com.example.demo.controller;

import com.example.demo.EmployeeModelAssembler;
import com.example.demo.database.LoadDatabase;
import com.example.demo.entity.Employee;
import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    private final EmployeeRepository repository;

    private final EmployeeModelAssembler assembler;


    public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler employeeModelAssembler) {
        this.repository = repository;
        this.assembler = employeeModelAssembler;
    }

    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> all() {
         List<EntityModel<Employee>> employees = repository.findAll().stream()
                .map(assembler::toModel).collect(Collectors.toList());

         return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }


    @PostMapping("/employees")
    ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {

       EntityModel<Employee> entityModel = assembler.toModel(repository.save(newEmployee));

       return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
               .body(entityModel);
    }


    @GetMapping("/employees/{id}")
    public EntityModel<Employee> one(@PathVariable Long id) {
        log.info("exec one");
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

        return assembler.toModel(employee);
    }


    @PutMapping("/employees/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        Employee updateEmployee = repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                }).orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });

        EntityModel<Employee> entityModel = assembler.toModel(updateEmployee);

        return ResponseEntity.ok().body(entityModel);


    }


    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }


}
