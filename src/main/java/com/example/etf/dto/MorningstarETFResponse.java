package com.example.etf.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Morningstar ETF API 响应 DTO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MorningstarETFResponse {
    
    @JsonProperty("secId")
    private String securityId;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("legalName")
    private String legalName;
    
    @JsonProperty("categoryName")
    private String categoryName;
    
    @JsonProperty("investmentType")
    private String investmentType;
    
    @JsonProperty("holdingTypeId")
    private String holdingType;
    
    @JsonProperty("inceptionDate")
    private String inceptionDate;
    
    @JsonProperty("currency")
    private CurrencyDTO currency;
    
    @JsonProperty("closePrice")
    private BigDecimal closePrice;
    
    @JsonProperty("dayChange")
    private BigDecimal dayChange;
    
    @JsonProperty("investmentStrategy")
    private String investmentStrategy;
    
    @JsonProperty("managementCompany")
    private String managementCompany;
    
    @JsonProperty("advisor")
    private String advisor;
    
    @JsonProperty("subAdvisor")
    private String subAdvisor;
    
    @JsonProperty("administrator")
    private String administrator;
    
    @JsonProperty("riskRating")
    private RiskRatingDTO riskRating;
    
    @JsonProperty("trailingPerformance")
    private List<TrailingPerformanceDTO> trailingPerformance;
    
    @JsonProperty("historicalPerformance")
    private List<HistoricalPerformanceSeriesDTO> historicalPerformance;
    
    @JsonProperty("managers")
    private List<ManagerDTO> managers;
    
    @JsonProperty("distributions")
    private List<DistributionDTO> distributions;
    
    @JsonProperty("portfolio")
    private PortfolioDTO portfolio;
    
    @JsonProperty("benchmarks")
    private List<BenchmarkDTO> benchmarks;
    
    /**
     * 货币信息 DTO
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrencyDTO {
        @JsonProperty("id")
        private String id;
        
        @JsonProperty("name")
        private String name;
    }
    
    /**
     * 行业 DTO
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SectorDTO {
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("weight")
        private BigDecimal weight;
    }
    
    /**
     * 产业 DTO
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IndustryDTO {
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("weight")
        private BigDecimal weight;
    }
    
    /**
     * 风险评级 DTO
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RiskRatingDTO {
        @JsonProperty("effectiveDate")
        private String effectiveDate;
        
        @JsonProperty("riskScore")
        private BigDecimal riskScore;
        
        @JsonProperty("riskRating")
        private String riskRating;
        
        @JsonProperty("riskScale")
        private Integer riskScale;
    }
    
    /**
     * 业绩表现 DTO
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TrailingPerformanceDTO {
        @JsonProperty("period")
        private String period;
        
        @JsonProperty("return")
        private ReturnDTO returnData;
        
        @JsonProperty("asOfDate")
        private String asOfDate;
    }
    
    /**
     * 收益 DTO
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReturnDTO {
        @JsonProperty("type")
        private String type;
        
        @JsonProperty("value")
        private BigDecimal value;
    }
    
    /**
     * 历史业绩系列 DTO
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HistoricalPerformanceSeriesDTO {
        @JsonProperty("year")
        private Integer year;
        
        @JsonProperty("return")
        private BigDecimal returnValue;
    }
    
    /**
     * 基金经理 DTO
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ManagerDTO {
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("startDate")
        private String startDate;
        
        @JsonProperty("tenure")
        private BigDecimal tenure;
        
        @JsonProperty("biography")
        private String biography;
    }
    
    /**
     * 分红 DTO
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DistributionDTO {
        @JsonProperty("distributionDate")
        private String distributionDate;
        
        @JsonProperty("exDate")
        private String exDate;
        
        @JsonProperty("recordDate")
        private String recordDate;
        
        @JsonProperty("payableDate")
        private String payableDate;
        
        @JsonProperty("amount")
        private BigDecimal amount;
        
        @JsonProperty("type")
        private String type;
        
        @JsonProperty("reinvestPrice")
        private BigDecimal reinvestPrice;
    }
    
    /**
     * 持仓组合 DTO
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PortfolioDTO {
        @JsonProperty("date")
        private String date;
        
        @JsonProperty("totalHoldings")
        private Integer totalHoldings;
        
        @JsonProperty("netAssets")
        private BigDecimal netAssets;
        
        @JsonProperty("holdings")
        private List<HoldingDTO> holdings;
        
        @JsonProperty("sectors")
        private List<SectorDTO> sectors;
    }
    
    /**
     * 持仓明细 DTO
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HoldingDTO {
        @JsonProperty("securityId")
        private String securityId;
        
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("ticker")
        private String ticker;
        
        @JsonProperty("holdingType")
        private String holdingType;
        
        @JsonProperty("weight")
        private BigDecimal weight;
        
        @JsonProperty("shares")
        private BigDecimal shares;
        
        @JsonProperty("marketValue")
        private BigDecimal marketValue;
        
        @JsonProperty("sector")
        private String sector;
        
        @JsonProperty("country")
        private String country;
    }
    
    /**
     * 基准 DTO
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BenchmarkDTO {
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("id")
        private String id;
        
        @JsonProperty("isPrimary")
        private Boolean isPrimary;
    }
}
