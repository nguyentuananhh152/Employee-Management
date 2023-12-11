package l1.tuan.anh.controller;


import jakarta.websocket.server.PathParam;
import l1.tuan.anh.entity.District;
import l1.tuan.anh.entity.Province;
import l1.tuan.anh.entity.Village;
import l1.tuan.anh.repository.DistrictRepository;
import l1.tuan.anh.service.impl.ProvinceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController()
public class ProvinceController {
    @Autowired
    private ProvinceServiceImpl provinceService;


    @GetMapping("/province/get-all")
    public List<Province> getAllProvince() {
        return provinceService.getAll();
    }

    @GetMapping("/province/get-by-id")
    public Province getById(@PathParam("id") int id) {
        return provinceService.getById(id);
    }

    @PostMapping("/province/save")
    public void save(@RequestBody Province province) {
        if (provinceService.getById(province.getId()) == null) {
            provinceService.save(province);
        }
    }

    @PutMapping("/province/update")
    public void update(@RequestBody Province province) {
        Province provinceUpdate = provinceService.getById(province.getId());
        if (provinceUpdate != null) {
            provinceService.save(province);
        }
    }

    @DeleteMapping("/province/delete-by-id")
    public void delete(@PathParam("id") int id) {
        provinceService.delete(id);
    }

    @PostMapping("/test/create")
    public void create() {
        Village village1 = new Village();
        village1.setCode("village1");
        village1.setName("village 1");

        Village village2 = new Village();
        village2.setCode("village2");
        village2.setName("village 2");

        Village village3 = new Village();
        village3.setCode("village3");
        village3.setName("village 3");

        Village village4 = new Village();
        village4.setCode("village4");
        village4.setName("village 4");

        List<Village> listVillage1 = new ArrayList<>();
        listVillage1.add(village1);
        listVillage1.add(village2);

        List<Village> listVillage2 = new ArrayList<>();
        listVillage2.add(village3);
        listVillage2.add(village4);

        District district1 = new District();
        district1.setName("district 1");
        district1.setCode("district1");
        district1.setVillages(listVillage1);

        District district2 = new District();
        district2.setName("district 2");
        district2.setCode("district2");
        district2.setVillages(listVillage2);

        village1.setDistrict(district1);
        village2.setDistrict(district1);
        village3.setDistrict(district2);
        village4.setDistrict(district2);

        List<District> districts = new ArrayList<>();
        districts.add(district1);
        districts.add(district2);

        Province province = new Province();
        province.setName("province haha");
        province.setDistricts(districts);

        district1.setProvince(province);
        district2.setProvince(province);

        provinceService.save(province);
    }
}
