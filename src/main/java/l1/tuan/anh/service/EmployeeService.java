package l1.tuan.anh.service;

import l1.tuan.anh.entity.Employee;
import l1.tuan.anh.dto.EmployeeDTO;
import l1.tuan.anh.dto.EmployeeSearchDTO;

import java.util.List;

public interface EmployeeService {
    public List<EmployeeDTO> getAllEmployee();

    public Employee save(Employee e);

    public EmployeeDTO updateByCode(EmployeeDTO employee);

    public void deleteById(int id);

    public List<EmployeeDTO> searchByKeyword(String s);
    public List<EmployeeDTO> searchByEmployeeDTOSearch(EmployeeSearchDTO employeeSearchDTO);
    public List<EmployeeDTO> searchByName(String name);
    public Employee searchByCode(String code);
    public List<EmployeeDTO> searchByEmail(String email);
    public List<EmployeeDTO> searchByPhone(String phone);
    public List<EmployeeDTO> searchByAge(int age);
}
