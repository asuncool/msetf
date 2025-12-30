package com.example.etf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.etf.entity.ETFBenchmark;
import org.apache.ibatis.annotations.Mapper;

/**
 * ETF基准信息 Mapper接口
 */
@Mapper
public interface ETFBenchmarkMapper extends BaseMapper<ETFBenchmark> {
}
