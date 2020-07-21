package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.dao.SystemConfigMapper;
import com.github.chenlijia1111.commonModule.entity.SystemConfig;
import com.github.chenlijia1111.commonModule.service.SystemConfigServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 系统配置
 *
 * @author chenLiJia
 * @since 2020-07-20 14:17:54
 **/
@Service
public class SystemConfigServiceImpl implements SystemConfigServiceI {


    @Resource
    private SystemConfigMapper systemConfigMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2020-07-20 14:17:54
     **/
    @Override
    public Result add(SystemConfig params) {

        int i;

        //判断是否存在，如果存在就覆盖
        SystemConfig systemConfig = systemConfigMapper.selectByPrimaryKey(params.getSystemKey());
        if (Objects.nonNull(systemConfig)) {
            //存在，覆盖
            systemConfig.setSystemValue(params.getSystemValue());
            i = systemConfigMapper.updateByPrimaryKeySelective(systemConfig);
        } else {
            i = systemConfigMapper.insertSelective(params);
        }
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 2020-07-20 14:17:54
     **/
    @Override
    public Result update(SystemConfig params) {

        int i = systemConfigMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 按条件编辑
     *
     * @param entity
     * @param condition
     * @return
     */
    @Override
    public Result update(SystemConfig entity, Example condition) {
        if (Objects.nonNull(entity) && Objects.nonNull(condition)) {
            int i = systemConfigMapper.updateByExampleSelective(entity, condition);
            return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
        }
        return Result.success("操作成功");
    }

    /**
     * 条件查询
     *
     * @param condition
     * @return
     * @since 2020-07-20 14:17:54
     **/
    @Override
    public List<SystemConfig> listByCondition(SystemConfig condition) {

        PropertyCheckUtil.transferObjectNotNull(condition, true);
        return systemConfigMapper.select(condition);
    }

    /**
     * 条件查询
     *
     * @param condition
     * @return
     * @since 2020-07-20 14:17:54
     **/
    @Override
    public List<SystemConfig> listByCondition(Example condition) {

        if (null != condition) {
            List<SystemConfig> list = systemConfigMapper.selectByExample(condition);
            return list;
        }
        return new ArrayList<>();
    }

    /**
     * 主键查询
     *
     * @param key
     * @return
     */
    @Override
    public SystemConfig findByKey(String key) {
        if (StringUtils.isNotEmpty(key)) {
            return systemConfigMapper.selectByPrimaryKey(key);
        }
        return null;
    }
}
