package l1.tuan.anh.service.impl;

import l1.tuan.anh.entity.Certificate;
import l1.tuan.anh.repository.CertificateRepository;
import l1.tuan.anh.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Certificate> getAll() {
        return certificateRepository.findAll();
    }

    @Override
    public Certificate getById(int id) {
        return certificateRepository.findById(id).orElse(null);
    }

    @Override
    public List<Certificate> getByEmployeeId(int id) {
        String sql = "SELECT * FROM certificate AS c WHERE c.employee_id =" + id;
        return jdbcTemplate.query(sql, new ResultSetExtractor<List<Certificate>>() {
            @Override
            public List<Certificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Certificate> list = new ArrayList<>();
                while (rs.next()) {
                    Certificate certificate = new Certificate();
                    certificate.setId(rs.getInt("id"));
                    certificate.setName(rs.getString("name"));
                    try {
                        certificate.setEffective(rs.getString("effective"));
                        certificate.setExpired(rs.getString("expired"));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    certificate.setSerialnumber(rs.getString("serialnumber"));
                    list.add(certificate);
                }
                return list;
            }
        });
    }
}
