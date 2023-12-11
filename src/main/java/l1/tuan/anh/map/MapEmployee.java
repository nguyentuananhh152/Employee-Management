package l1.tuan.anh.map;

import l1.tuan.anh.dto.EmployeeDTO;
import l1.tuan.anh.entity.Employee;

public class MapEmployee {
    public static EmployeeDTO mapToEntityDTO(Employee e) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setAge(e.getAge());
        employeeDTO.setCode(e.getCode());
        employeeDTO.setEmail(e.getEmail());
        employeeDTO.setName(e.getName());
        employeeDTO.setPhone(e.getPhone());
        employeeDTO.setProvince(e.getProvince());
        employeeDTO.setDistrict(e.getDistrict());
        employeeDTO.setVillage(e.getVillage());
        employeeDTO.setCertificates(e.getCertificates());
        return employeeDTO;
    }

    public static Employee mapToEntity(EmployeeDTO e) {
        Employee employee = new Employee();
        employee.setAge(e.getAge());
        employee.setCode(e.getCode());
        employee.setEmail(e.getEmail());
        employee.setName(e.getName());
        employee.setPhone(e.getPhone());
        employee.setProvince(e.getProvince());
        employee.setDistrict(e.getDistrict());
        employee.setVillage(e.getVillage());
        employee.setCertificates(e.getCertificates());
        return employee;
    }

}
