package org.tareq23.curd.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tareq23.curd.dto.EmployeeDto;
import org.tareq23.curd.entity.Employee;
import org.tareq23.curd.repository.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public EmployeeDto save(EmployeeDto employeeDto)
    {
        try{
            Employee employee = employeeRepository.save(dtoToEntity(employeeDto));
            return entityToDto(employee);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return employeeDto;
        }
    }

    public EmployeeDto findByContact(String contact)
    {
        try{
            return modelMapper.map(employeeRepository.findByContactNumber(contact), EmployeeDto.class);
        }
        catch(Exception ex){
            return modelMapper.map(contact, EmployeeDto.class);
        }
    }

    public EmployeeDto update(String contact, EmployeeDto employeeDto)
    {
        try{
            Employee employee = employeeRepository.findByContactNumber(contact);
            System.out.println("employeeDto: "+employeeDto.toString());
            System.out.println("Employee: "+employee.toString());
            employeeDto.setContactNumber(contact);
            Employee updateEmployee = dtoToEntity(employeeDto);
            updateEmployee.setId(employee.getId());
            updateEmployee.setContactNumber(contact);
            System.out.println("updateEmployee: "+updateEmployee.toString());
            return modelMapper.map(employeeRepository.save(updateEmployee), EmployeeDto.class);
        }
        catch(Exception ex){
            System.out.println("Exception: "+ex.getMessage());
            return modelMapper.map(contact, EmployeeDto.class);
        }
    }

    @Transactional
    public boolean delete(String contact)
    {
        try{
            return employeeRepository.deleteByContactNumber(contact) >= 1;
        }
        catch(Exception ex){
            System.out.println("Exception: "+ex.getMessage());
            return false;
        }

    }

    public List<EmployeeDto> findAll()
    {
        try{
            return employeeRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return List.of();
        }
    }

    private EmployeeDto entityToDto(Employee employee)
    {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    private Employee dtoToEntity(EmployeeDto employeeDto)
    {
        return modelMapper.map(employeeDto, Employee.class);
    }

}
