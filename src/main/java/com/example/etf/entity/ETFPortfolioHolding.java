package com.example.etf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ETF持仓明细实体类
 */
@Data
@TableName("etf_portfolio_holdings")
public class ETFPortfolioHolding {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("portfolio_id")
    private Long portfolioId;
    
    @TableField("security_id")
    private String securityId;
    
    @TableField("holding_name")
    private String holdingName;
    
    @TableField("holding_ticker")
    private String holdingTicker;
    
    @TableField("holding_type")
    private String holdingType;
    
    @TableField("weight_percentage")
    private BigDecimal weightPercentage;
    
    @TableField("shares")
    private BigDecimal shares;
    
    @TableField("market_value")
    private BigDecimal marketValue;
    
    @TableField("sector")
    private String sector;
    
    @TableField("country")
    private String country;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
    
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
