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
 * ETF风险评级实体类
 */
@Data
@TableName("etf_risk_rating")
public class ETFRiskRating {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @TableField("security_id")
    private String securityId;
    
    @TableField("effective_date")
    private LocalDate effectiveDate;
    
    @TableField("risk_score")
    private BigDecimal riskScore;
    
    @TableField("risk_rating")
    private String riskRating;
    
    @TableField("risk_scale")
    private Integer riskScale;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
    
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
