package com.example.etf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.etf.entity.ETFRiskRating;
import org.apache.ibatis.annotations.Mapper;

/**
 * ETF风险评级 Mapper接口
 */
@Mapper
public interface ETFRiskRatingMapper extends BaseMapper<ETFRiskRating> {
}
