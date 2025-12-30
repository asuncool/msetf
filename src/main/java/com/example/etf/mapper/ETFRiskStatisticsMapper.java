package com.example.etf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.etf.entity.ETFRiskStatistics;
import org.apache.ibatis.annotations.Mapper;

/**
 * ETF风险统计 Mapper接口
 */
@Mapper
public interface ETFRiskStatisticsMapper extends BaseMapper<ETFRiskStatistics> {
}
