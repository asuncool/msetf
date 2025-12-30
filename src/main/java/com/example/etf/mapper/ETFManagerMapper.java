package com.example.etf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.etf.entity.ETFManager;
import org.apache.ibatis.annotations.Mapper;

/**
 * ETF基金经理 Mapper接口
 */
@Mapper
public interface ETFManagerMapper extends BaseMapper<ETFManager> {
}
