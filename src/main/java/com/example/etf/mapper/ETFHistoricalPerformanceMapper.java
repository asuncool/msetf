package com.example.etf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.etf.entity.ETFHistoricalPerformance;
import org.apache.ibatis.annotations.Mapper;

/**
 * ETF历史业绩 Mapper接口
 */
@Mapper
public interface ETFHistoricalPerformanceMapper extends BaseMapper<ETFHistoricalPerformance> {
}
