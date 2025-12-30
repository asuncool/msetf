package com.example.etf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.etf.entity.ETFAssetAllocation;
import org.apache.ibatis.annotations.Mapper;

/**
 * ETF资产配置 Mapper接口
 */
@Mapper
public interface ETFAssetAllocationMapper extends BaseMapper<ETFAssetAllocation> {
}
