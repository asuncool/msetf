package com.example.etf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.etf.entity.ETFPortfolioHolding;
import org.apache.ibatis.annotations.Mapper;

/**
 * ETF持仓明细 Mapper接口
 */
@Mapper
public interface ETFPortfolioHoldingMapper extends BaseMapper<ETFPortfolioHolding> {
}
