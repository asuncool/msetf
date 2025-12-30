package com.example.etf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ETF持仓组合实体类
 */
@Data
@TableName("etf_portfolio")
public class ETFPortfolio {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("security_id")
    private String securityId;
    
    @TableField("portfolio_name")
    private String portfolioName;
    
    @TableField("portfolio_date")
    private LocalDate portfolioDate;
    
    @TableField("total_holdings")
    private Integer totalHoldings;
    
    @TableField("net_assets")
    private java.math.BigDecimal netAssets;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
    
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
