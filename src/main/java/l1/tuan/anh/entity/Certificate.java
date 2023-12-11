package l1.tuan.anh.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String serialnumber;    // Số hiệu

    @Column(nullable = false)
    private Date effective = new Date(1,0,0); // Ngày hiệu lực

    @Column(nullable = false)
    private Date expired = new Date(1,0,0);   // Ngày hết hạn

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = true, referencedColumnName = "id")
    @JsonIgnore
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "province", nullable = false, referencedColumnName = "id")
    @JsonIgnore
    private Province province;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public Date getEffective() {
        return effective;
    }

    public void setEffective(int day, int month, int year) {
        LocalDate localDate = LocalDate.of(year, month, day);
        this.effective = Date.valueOf(localDate);
    }


    public void setEffective(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = convertUtilDateToSqlDate(utilDate);
        this.effective = sqlDate;
    }


    public Date getExpired() {
        return expired;
    }

    public void setExpired(int day, int month, int year) {
        LocalDate localDate = LocalDate.of(year, month, day);
        this.expired = Date.valueOf(localDate);
    }
    public Date convertUtilDateToSqlDate(java.util.Date utilDate) {
        return new Date(utilDate.getTime());
    }
    public void setExpired(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = convertUtilDateToSqlDate(utilDate);
        this.expired = sqlDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }
}
