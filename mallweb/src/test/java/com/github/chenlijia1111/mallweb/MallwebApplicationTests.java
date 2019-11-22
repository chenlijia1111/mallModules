package com.github.chenlijia1111.mallweb;

import com.github.chenlijia1111.commonModule.dao.ProvinceMapper;
import com.github.chenlijia1111.commonModule.entity.Province;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class MallwebApplicationTests {

    @Resource
    private ProvinceMapper provinceMapper;

    @Test
    void contextLoads() {
        List<Province> provinces = provinceMapper.selectAll();
        System.out.println(provinces);
    }

}
