package l1.tuan.anh.service;


import l1.tuan.anh.entity.Certificate;

import java.util.List;

public interface CertificateService {
    public List<Certificate> getAll();

    public Certificate getById(int id);

    public List<Certificate> getByEmployeeId(int id);
}
