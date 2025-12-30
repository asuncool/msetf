package com.example.etf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.etf.entity.ETFEquityStatistics;
import org.apache.ibatis.annotations.Mapper;

/**
 * ETF股票统计 Mapper接口
 */
@Mapper
public interface ETFEquityStatisticsMapper extends BaseMapper<ETFEquityStatistics> {
}
