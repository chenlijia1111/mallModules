package com.github.chenlijia1111.generalModule.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统配置
 * @author chenLiJia
 * @since 2020-07-20 14:17:44
 * @version 1.0
 **/
@ApiModel("系统配置")
@Table(name = "s_system_config")
@Setter
@Getter
@Accessors(chain = true)
public class SystemConfig {
    /**
     * key
     */
    @ApiModelProperty("key")
    @PropertyCheck(name = "key")
    @Id
    @Column(name = "system_key")
    private String systemKey;

    /**
     * value
     */
    @ApiModelProperty("value")
    @PropertyCheck(name = "value")
    @Column(name = "system_value")
    private String systemValue;

}
