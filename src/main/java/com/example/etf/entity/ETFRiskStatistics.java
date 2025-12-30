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
 * ETF风险统计实体类
 */
@Data
@TableName("etf_risk_statistics")
public class ETFRiskStatistics {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("security_id")
    private String securityId;
    
    @TableField("statistic_type")
    private String statisticType;
    
    @TableField("period")
    private String period;
    
    @TableField("statistic_value")
    private BigDecimal statisticValue;
    
    @TableField("as_of_date")
    private LocalDate asOfDate;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
    
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
