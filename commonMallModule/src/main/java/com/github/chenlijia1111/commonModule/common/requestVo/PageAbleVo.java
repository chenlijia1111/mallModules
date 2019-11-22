package com.github.chenlijia1111.commonModule.common.requestVo;

import io.swagger.annotations.ApiModel;

/**
 * 分页查询条件
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/30 0030 上午 10:21
 **/
@ApiModel
public class PageAbleVo {

    private Integer page;

    private Integer limit;


    public Integer getPage() {
        return page == null ? 1 : page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit == null ? 10 : limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
