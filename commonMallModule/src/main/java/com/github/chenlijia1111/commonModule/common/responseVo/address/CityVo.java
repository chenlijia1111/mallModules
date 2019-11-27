package com.github.chenlijia1111.commonModule.common.responseVo.address;

import com.github.chenlijia1111.commonModule.entity.Area;
import com.github.chenlijia1111.commonModule.entity.City;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 市地址返回对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 上午 9:38
 **/
@ApiModel
@Setter
@Getter
public class CityVo extends City {

    /**
     * 区列表
     *
     * @since 上午 9:41 2019/11/1 0001
     **/
    @ApiModelProperty(value = "区列表")
    private List<Area> areaList;

}
