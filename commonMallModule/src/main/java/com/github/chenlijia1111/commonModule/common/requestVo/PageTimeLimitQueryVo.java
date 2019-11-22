package com.github.chenlijia1111.commonModule.common.requestVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 分页时间范围查询参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/30 0030 上午 10:24
 **/
@ApiModel
public class PageTimeLimitQueryVo extends PageAbleVo {

    /**
     * 开始时间
     *
     * @since 上午 10:25 2019/10/30 0030
     **/
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
