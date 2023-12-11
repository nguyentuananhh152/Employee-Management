package l1.tuan.anh.service;

import l1.tuan.anh.entity.Province;

import java.util.List;

public interface ProvinceService {
    public List<Province> getAll();

    public Province getById(int id);

    public Province save(Province province);

    public void delete(int id);


}
