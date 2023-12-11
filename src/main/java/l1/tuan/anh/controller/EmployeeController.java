package l1.tuan.anh.controller;

import jakarta.websocket.server.PathParam;
import l1.tuan.anh.dto.EmployeeDTO;
import l1.tuan.anh.dto.EmployeeSearchDTO;
import l1.tuan.anh.entity.*;
import l1.tuan.anh.map.MapEmployee;
import l1.tuan.anh.repository.CertificateRepository;
import l1.tuan.anh.service.impl.EmployeeServiceImpl;
import l1.tuan.anh.service.impl.ProvinceServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController()
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;
    @Autowired
    private ProvinceServiceImpl provinceService;

    @GetMapping("/employee/get-all")
    public List<EmployeeDTO> getAllEmployee() {
        return employeeService.getAllEmployee();
    }

    @GetMapping("/employee/get-by-code")
    public EmployeeDTO getByCode(@PathParam("code") String code) {
        return MapEmployee.mapToEntityDTO(employeeService.searchByCode(code));
    }

    @PostMapping("/employee/save")
    public ResponseEntity<String> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        if (employeeService.save(MapEmployee.mapToEntity(employeeDTO)) != null) {
            return ResponseEntity.ok("Save success");
        }
        return ResponseEntity.badRequest().body("Invalid Employee");
    }

    @PutMapping("/employee/update")
    public ResponseEntity<String> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        if (employeeService.updateByCode(employeeDTO) != null) {
            return ResponseEntity.ok("Update success");
        }
        return ResponseEntity.badRequest().body("Invalid Employee <province, district, village>");
    }



    @PostMapping("/test")
    public ResponseEntity<String> createNewE() {
        Province province1 = new Province();
        province1.setName("province 1");

        Province province2 = new Province();
        province2.setName("province 2");

        Province province3 = new Province();
        province3.setName("province 3");

        Province province4 = new Province();
        province4.setName("province 4");

        District district = new District();
        district.setCode("01");
        district.setName("hahaha");

        Village village = new Village();
        village.setCode("village01");
        village.setName("Village 1");

        village.setDistrict(district);
        district.setProvince(province1);

        Certificate certificate1 = new Certificate();
        certificate1.setName("certificate 1");
        certificate1.setEffective(10, 12, 2023);
        certificate1.setExpired(20, 2, 2016);
        certificate1.setProvince(province1);
        certificate1.setSerialnumber("1");

        Certificate certificate2 = new Certificate();
        certificate2.setName("certificate 2");
        certificate2.setEffective(10, 12, 2023);
        certificate2.setEffective(10, 12, 2023);
        certificate2.setExpired(20, 2, 2026);
        certificate2.setProvince(province2);
        certificate2.setSerialnumber("2");

        Certificate certificate3 = new Certificate();
        certificate3.setName("certificate 3");
        certificate3.setEffective(10, 12, 2023);
        certificate3.setExpired(20, 2, 2025);
        certificate3.setProvince(province3);
        certificate3.setSerialnumber("3");

        Certificate certificate4 = new Certificate();
        certificate4.setName("certificate 4");
        certificate4.setEffective(10, 12, 2023);
        certificate4.setExpired(20, 2, 2026);
        certificate4.setProvince(province4);
        certificate4.setSerialnumber("4");

        Certificate certificate5 = new Certificate();
        certificate5.setName("certificate 5");
        certificate5.setEffective(10, 12, 2023);
        certificate5.setExpired(20, 2, 2022);
        certificate5.setProvince(province1);
        certificate5.setSerialnumber("5");

        province1.addCertificate(certificate1);
        province2.addCertificate(certificate2);
        province1.addCertificate(certificate5);
        province3.addCertificate(certificate3);
        province4.addCertificate(certificate4);

        provinceService.save(province1);
        provinceService.save(province2);
        provinceService.save(province3);
        provinceService.save(province4);

        // trùng 2 province
        List<Certificate> certificateList1 = new ArrayList<>();
        certificateList1.add(certificate1);
        certificateList1.add(certificate2);
        certificateList1.add(certificate3);
        certificateList1.add(certificate4);
        certificateList1.add(certificate5);

        // 4 certificate còn hạn
        List<Certificate> certificateList2 = new ArrayList<>();
        certificateList2.add(certificate2);
        certificateList2.add(certificate3);
        certificateList2.add(certificate4);
        certificateList2.add(certificate5);

        // ok
        List<Certificate> certificateList3 = new ArrayList<>();
        certificateList3.add(certificate1);
        certificateList3.add(certificate2);
        certificateList3.add(certificate3);
        certificateList3.add(certificate4);

        Employee employee = new Employee();
        employee.setName("employee 1");
        employee.setEmail("nguyentuananh001@gmail.com");
        employee.setPhone("01234567899");
        employee.setCode("A00001");
        employee.setAge(20);
        employee.setProvince(province1.getName());
        employee.setDistrict("new district 001");
        employee.setVillage("new village 001");
        employee.setCertificates(certificateList3);

        Employee employee2 = new Employee();
        employee2.setName("Employee 2");
        employee2.setEmail("nguyentuananh001@gmail.com");
        employee2.setPhone("01234567899");
        employee2.setCode("A00002");
        employee2.setAge(20);
        employee2.setProvince(province1.getName());
        employee2.setDistrict("new district 002");
        employee2.setVillage("new village 002");
//        employee2.setCertificates();

        Employee employee3 = new Employee();
        employee3.setName("Employee 3");
        employee3.setEmail("nguyentuananh003@gmail.com");
        employee3.setPhone("01234567899");
        employee3.setCode("A00003");
        employee3.setAge(20);
        employee3.setProvince(province1.getName());
        employee3.setDistrict("new district 003");
        employee3.setVillage("new village 003");
//        employee3.setCertificates(certificateList3);

        try {
            if (employeeService.save(employee) != null
                && employeeService.save(employee2) != null
                && employeeService.save(employee3) != null) {
                return ResponseEntity.ok("Save success");
            }
            return ResponseEntity.badRequest().body("Error, invalid information");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/employee/delete")
    public void deleteById(@PathParam("id") int id) {
        employeeService.deleteById(id);
    }
    @GetMapping("/search")
    public List<EmployeeDTO> searchByKeyword(
            @PathParam("keyword") String keyword        // /search?keyword=...
//            @PathVariable("keyword") String keyword   // /search/keyword=...
    ) {
        return employeeService.searchByKeyword(keyword);
    }

    @GetMapping("/search-by-employeeSearchDTO")
    public List<EmployeeDTO> searchByName(@RequestBody EmployeeSearchDTO e) {
        return employeeService.searchByEmployeeDTOSearch(e);
    }

    @GetMapping("/search-by-name")
    public List<EmployeeDTO> searchByName(@PathParam("name") String name) {
        return employeeService.searchByName(name);
    }

    @GetMapping("/export-excel")
    public ResponseEntity<byte[]> exportToExcel() throws IOException {
        // Tạo workbook và sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        //
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("STT");
//        headerRow.createCell(1).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Code");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Phone");
        headerRow.createCell(5).setCellValue("Age");

        List<EmployeeDTO> list = employeeService.getAllEmployee();
        int index = 1;
        for (EmployeeDTO e : list) {
            Row dataRow = sheet.createRow(index);
            dataRow.createCell(0).setCellValue(index);
            dataRow.createCell(1).setCellValue(e.getName());
            dataRow.createCell(2).setCellValue(e.getCode());
            dataRow.createCell(3).setCellValue(e.getEmail());
            dataRow.createCell(4).setCellValue(e.getPhone());
            dataRow.createCell(5).setCellValue(e.getAge());
            index++;
        }

        // Ghi workbook vào ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        list = null;
        // Đặt thông tin header cho file Excel
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.xlsx");

        // Trả về file Excel dưới dạng byte array
        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }


}
