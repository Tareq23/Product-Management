package org.tareq23.curd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.tareq23.curd.dto.DeleteMessageDto;
import org.tareq23.curd.dto.EmployeeDto;
import org.tareq23.curd.service.EmployeeService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin("http://localhost:4200")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/")
    public String home()
    {
        return "Employee-Curd";
    }

    @PostMapping("/save")
    public ResponseEntity<EmployeeDto> save(@RequestBody EmployeeDto employeeDto){
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.save(employeeDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDto>> getAll()
    {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAll());
    }

    @GetMapping("/get-by-mobile/{mobile}")
    public ResponseEntity<Object> getByMobile(@PathVariable("mobile") String mobile)
    {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findByContact(mobile));
    }

    @PutMapping("/update/{mobile}")
    public ResponseEntity<Object> update(@PathVariable("mobile") String mobile, @RequestBody EmployeeDto employeeDto)
    {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.update(mobile, employeeDto));
    }
    @DeleteMapping("/delete/{mobile}")
    public ResponseEntity<Object> delete(@PathVariable("mobile") String mobile)
    {
        boolean res = employeeService.delete(mobile);
        return ResponseEntity.status(res ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(res ? new DeleteMessageDto("Delete Success") : new DeleteMessageDto("Not Found"));
    }

//    @PostMapping("/save")
//    public ResponseEntity<EmployeeDto> save(@RequestBody EmployeeDto employeeDto)
//    {
//        return ResponseEntity.status(HttpStatus.OK).body(employeeService.save(employeeDto));
//    }

}
