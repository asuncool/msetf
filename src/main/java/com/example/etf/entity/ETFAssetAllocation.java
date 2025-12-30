package com.example.etf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ETF资产配置实体类
 */
@Data
@TableName("etf_asset_allocation")
public class ETFAssetAllocation {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("security_id")
    private String securityId;
    
    @TableField("asset_type")
    private String assetType;
    
    @TableField("allocation_percentage")
    private BigDecimal allocationPercentage;
    
    @TableField("net_assets")
    private BigDecimal netAssets;
    
    @TableField("as_of_date")
    private LocalDate asOfDate;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
    
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
