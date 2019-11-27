package com.github.chenlijia1111.commonModule.biz;

import com.github.chenlijia1111.commonModule.common.responseVo.address.CityVo;
import com.github.chenlijia1111.commonModule.common.responseVo.address.ProvinceVo;
import com.github.chenlijia1111.commonModule.entity.Area;
import com.github.chenlijia1111.commonModule.entity.City;
import com.github.chenlijia1111.commonModule.entity.Province;
import com.github.chenlijia1111.commonModule.service.AddressServiceI;
import com.github.chenlijia1111.utils.common.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 地址
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/27 0027 上午 10:29
 **/
@Service
public class AddressBiz {

    @Autowired
    private AddressServiceI addressService;//地址


    /**
     * 列出所有省市区
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:50 2019/11/1 0001
     **/
    public Result listAllAddress() {

        //列出所有地址
        List<Province> provinces = addressService.listByCondition(new Province());
        List<City> cities = addressService.listByCondition(new City());
        List<Area> areas = addressService.listByCondition(new Area());

        //转为Vo对象
        List<ProvinceVo> provinceVoList = provinces.stream().map(e -> {
            ProvinceVo provinceVo = new ProvinceVo();
            BeanUtils.copyProperties(e, provinceVo);
            return provinceVo;
        }).collect(Collectors.toList());

        List<CityVo> cityVoList = cities.stream().map(e -> {
            CityVo cityVo = new CityVo();
            BeanUtils.copyProperties(e, cityVo);
            return cityVo;
        }).collect(Collectors.toList());


        //开始组装地址  市编码->区
        Map<String, List<Area>> areasMap = areas.stream().collect(Collectors.groupingBy(e -> e.getcCode()));

        //填充市的区
        for (CityVo cityVo : cityVoList) {
            List<Area> areaList = areasMap.get(cityVo.getCode());
            cityVo.setAreaList(areaList);
        }

        //省编码->市
        Map<String, List<CityVo>> citiesMap = cityVoList.stream().collect(Collectors.groupingBy(e -> e.getpCode()));
        //填充省的市
        for (ProvinceVo provinceVo : provinceVoList) {
            if (Objects.equals(provinceVo.getName(), "北京市")) {
                System.out.println("北京");
            }
            List<CityVo> cityVos = citiesMap.get(provinceVo.getCode());

            provinceVo.setCityList(cityVos);
        }

        return Result.success("查询成功", provinceVoList);
    }

}
