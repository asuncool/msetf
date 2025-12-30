package com.example.etf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.etf.entity.ETFDistributionHistory;
import org.apache.ibatis.annotations.Mapper;

/**
 * ETF分红历史 Mapper接口
 */
@Mapper
public interface ETFDistributionHistoryMapper extends BaseMapper<ETFDistributionHistory> {
}
