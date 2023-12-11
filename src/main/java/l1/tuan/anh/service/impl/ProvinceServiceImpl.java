package l1.tuan.anh.service.impl;

import l1.tuan.anh.entity.Province;
import l1.tuan.anh.repository.ProvinceRepository;
import l1.tuan.anh.service.ProvinceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;
    @Override
    public List<Province> getAll() {
        return provinceRepository.findAll();
    }

    @Override
    public Province getById(int id) {
        return provinceRepository.findById(id).orElse(null);
    }

    @Override
    public Province save(Province province) {
        return provinceRepository.save(province);
    }

    @Override
    public void delete(int id) {
        provinceRepository.deleteById(id);
    }


}
