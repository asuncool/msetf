package com.example.etf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.etf.entity.ETFPortfolio;
import org.apache.ibatis.annotations.Mapper;

/**
 * ETF持仓组合 Mapper接口
 */
@Mapper
public interface ETFPortfolioMapper extends BaseMapper<ETFPortfolio> {
}
