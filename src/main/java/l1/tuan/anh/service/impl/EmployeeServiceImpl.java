package l1.tuan.anh.service.impl;

import l1.tuan.anh.entity.Certificate;
import l1.tuan.anh.entity.Employee;
import l1.tuan.anh.dto.EmployeeDTO;
import l1.tuan.anh.dto.EmployeeSearchDTO;
import l1.tuan.anh.map.MapEmployee;
import l1.tuan.anh.repository.EmployeeRepository;
import l1.tuan.anh.service.EmployeeService;
import l1.tuan.anh.validate.Validate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
//@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CertificateServiceImpl certificates;
    public EmployeeServiceImpl() {}

    @Override
    public List<EmployeeDTO> getAllEmployee() {
        return employeeRepository.findAll().stream()
                .map(employee -> MapEmployee.mapToEntityDTO(employee))
                .collect(Collectors.toList());
    }

    @Override
    public Employee save(Employee e) {
        if (validate(e)) {
            return employeeRepository.save(e);
        } else {
            return null;
        }
    }

    @Override
    public EmployeeDTO updateByCode(EmployeeDTO employee) {
        Employee employeeUpdate = searchByCode(employee.getCode());
        if (employeeUpdate != null) {
            if (employeeUpdate.getProvince().equals(employee.getProvince())) {
                return null;
            }
            if (employeeUpdate.getDistrict().equals(employee.getDistrict())) {
                return null;
            }
            if (employeeUpdate.getVillage().equals(employee.getVillage())) {
                return null;
            }
            employeeUpdate.setProvince(employee.getProvince());
            employeeUpdate.setDistrict(employee.getDistrict());
            employeeUpdate.setVillage(employee.getVillage());
            return MapEmployee.mapToEntityDTO(employeeRepository.save(employeeUpdate));
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeDTO> searchByKeyword(String keyword) {
        String jpql = "SELECT * FROM employee AS e WHERE e.name LIKE '%" + keyword + "%'"
                + " OR e.code LIKE '%" + keyword + "%'"
                + " OR e.email LIKE '%" + keyword + "%'"
                + " OR e.phone LIKE '%" + keyword + "%'"
                + " OR e.age LIKE '%" + keyword + "%'"
                + " OR e.province LIKE '%" + keyword + "%'"
                + " OR e.district LIKE '%" + keyword + "%'"
                + " OR e.village LIKE '%" + keyword + "%'";
        List<EmployeeDTO> employeeDTOList = jdbcTemplate.query(jpql, new ResultSetExtractor<List<EmployeeDTO>>() {
            @Override
            public List<EmployeeDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<EmployeeDTO> eList = new ArrayList<>();
                while (rs.next()) {
                    EmployeeDTO e = new EmployeeDTO();
                    e.setCode(rs.getString("code"));
                    e.setName(rs.getString("name"));
                    e.setEmail(rs.getString("email"));
                    e.setPhone(rs.getString("phone"));
                    e.setAge(rs.getInt("age"));
                    e.setProvince(rs.getString("province"));
                    e.setDistrict(rs.getString("district"));
                    e.setVillage(rs.getString("village"));
                    eList.add(e);
                }
                return eList;
            }
        });
        return employeeDTOList;
    }

    @Override
    public List<EmployeeDTO> searchByEmployeeDTOSearch(EmployeeSearchDTO e) {
        String jpql = "SELECT * FROM employee AS e WHERE e.name LIKE '%" + e.getName() + "%'"
                + " AND e.code LIKE '%" + e.getCode() + "%'"
                + " AND e.email LIKE '%" + e.getEmail() + "%'"
                + " AND e.phone LIKE '%" + e.getPhone() + "%'"
                + " AND e.age LIKE '%" + e.getAge() + "%'"
                + " AND e.age LIKE '%" + e.getProvince() + "%'"
                + " AND e.age LIKE '%" + e.getDistrict() + "%'"
                + " AND e.age LIKE '%" + e.getVillage() + "%'";
        List<EmployeeDTO> employeeDTOList = jdbcTemplate.query(jpql, new ResultSetExtractor<List<EmployeeDTO>>() {
            @Override
            public List<EmployeeDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<EmployeeDTO> eList = new ArrayList<>();
                while (rs.next()) {
                    EmployeeDTO e = new EmployeeDTO();
                    e.setCode(rs.getString("code"));
                    e.setName(rs.getString("name"));
                    e.setEmail(rs.getString("email"));
                    e.setPhone(rs.getString("phone"));
                    e.setAge(rs.getInt("age"));
                    e.setProvince(rs.getString("province"));
                    e.setDistrict(rs.getString("district"));
                    e.setVillage(rs.getString("village"));
                    eList.add(e);
                }
                return eList;
            }
        });
        return employeeDTOList;
    }

    @Override
    public List<EmployeeDTO> searchByName(String name) {
        return searchBy("name", name);
    }

    @Override
    public Employee searchByCode(String code) {
        String jpql = "SELECT * FROM employee AS e WHERE e.code LIKE '" + code + "'";
        Employee employee = jdbcTemplate.query(jpql, new ResultSetExtractor<Employee>() {
            @Override
            public Employee extractData(ResultSet rs) throws SQLException, DataAccessException {
                Employee e = new Employee();
                while (rs.next()) {
                    e.setId(rs.getInt("id"));
                    e.setCode(rs.getString("code"));
                    e.setName(rs.getString("name"));
                    e.setEmail(rs.getString("email"));
                    e.setPhone(rs.getString("phone"));
                    e.setAge(rs.getInt("age"));
                    e.setProvince(rs.getString("province"));
                    e.setDistrict(rs.getString("district"));
                    e.setVillage(rs.getString("village"));
//                    e.setCertificates();
                }
                return e;
            }
        });
        employee.setCertificates(certificates.getByEmployeeId(employee.getId()));
        return employee;
    }

    @Override
    public List<EmployeeDTO> searchByEmail(String email) {
        return searchBy("email", email);
    }

    @Override
    public List<EmployeeDTO> searchByPhone(String phone) {
        return searchBy("phone", phone);
    }

    @Override
    public List<EmployeeDTO> searchByAge(int age) {
        return searchBy("age", Integer.toString(age));
    }

    public List<EmployeeDTO> searchBy(String nameofvariable, String variable) {
        String jpql = "SELECT * FROM employee AS e WHERE e." + nameofvariable + " LIKE '%" + variable + "%'";
        List<EmployeeDTO> employeeDTOList = jdbcTemplate.query(jpql, new ResultSetExtractor<List<EmployeeDTO>>() {
            @Override
            public List<EmployeeDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<EmployeeDTO> eList = new ArrayList<>();
                while (rs.next()) {
                    EmployeeDTO e = new EmployeeDTO();
                    e.setCode(rs.getString("code"));
                    e.setName(rs.getString("name"));
                    e.setEmail(rs.getString("email"));
                    e.setPhone(rs.getString("phone"));
                    e.setAge(rs.getInt("age"));
                    e.setProvince(rs.getString("province"));
                    e.setDistrict(rs.getString("district"));
                    e.setVillage(rs.getString("village"));
                    eList.add(e);
                }
                return eList;
            }
        });
        return employeeDTOList;

//        String sql = "SELECT e.id as employee_id, e.code, e.name, e.email, e.phone, e.age, e.province, e.district, e.village," +
//                "c.id as c_id, c.name as c_name, c.serialnumber as c_serialnumber, c.effective as c_effective, c.expired as c_expired, c.province as c_province_id " +
//                "FROM employee e " +
//                "LEFT JOIN certificate c ON e.id = c.employee_id";
//
//        return jdbcTemplate.query(sql, new RowMapper<EmployeeDTO>() {
//            @Override
//            public EmployeeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
//                String employeeId = rs.getString("code");
//                EmployeeDTO employee = new EmployeeDTO();
//
//                if (employee.getCode() == null || !employee.getCode().equals(employeeId)) {
//                    employee.setCode(rs.getString("code"));
//                    employee.setName(rs.getString("name"));
//                    employee.setEmail(rs.getString("email"));
//                    employee.setPhone(rs.getString("phone"));
//                    employee.setAge(rs.getInt("age"));
//                    employee.setProvince(rs.getString("province"));
//                    employee.setDistrict(rs.getString("district"));
//                    employee.setVillage(rs.getString("village"));
//                    // Khởi tạo danh sách chứng chỉ
//                    employee.setCertificates(new ArrayList<>());
//                }
//
//                // Xử lý thông tin chứng chỉ từ ResultSet của bảng Certificate
//                String name = rs.getString("c_name");
//                String serialnumber = rs.getString("c_serialnumber");
//                String effective = rs.getString("c_effective");
//                String expired =rs.getString("c_expired");
//                int province_id = rs.getInt("c_province_id");
//                if (name != null) {
//                    // Thêm thông tin chứng chỉ vào danh sách certificates của EmployeeDTO
//                    Certificate certificate = new Certificate();
//                    certificate.setName(name);
//                    certificate.setSerialnumber(serialnumber);
////                    try {
////                        certificate.setEffective(effective);
////                        certificate.setExpired(expired);
////                    } catch (ParseException e) {
////                        throw new RuntimeException(e);
////                    }
////                    Province province = new Province();
////                    province.setId(province_id);
////                    certificate.setProvince(province);
//                    employee.getCertificates().add(certificate);
//                }
//
//                return employee;
//            }
//        });
    }

    

    public boolean validate(Employee e) {
        if (!Validate.isValidName(e.getName())
            || !Validate.isValidEmail(e.getEmail())
            || !Validate.isValidCode(e.getCode())
            || !Validate.isValidPhoneNumber(e.getPhone())
            || !Validate.isValidAge(e.getAge())
            || !Validate.isValidCertificate(e.getCertificates())) {
            return false;
        }
        return true;
    }
}
