package com.example.etf.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.etf.dto.MorningstarETFResponse;
import com.example.etf.entity.*;
import com.example.etf.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * ETF数据解析服务
 */
@Slf4j
@Service
public class ETFDataParserService {
    
    @Autowired
    private ETFBaseInfoMapper baseInfoMapper;
    
    @Autowired
    private ETFRiskRatingMapper riskRatingMapper;
    
    @Autowired
    private ETFBenchmarkMapper benchmarkMapper;
    
    @Autowired
    private ETFTrailingPerformanceMapper trailingPerformanceMapper;
    
    @Autowired
    private ETFHistoricalPerformanceMapper historicalPerformanceMapper;
    
    @Autowired
    private ETFRiskStatisticsMapper riskStatisticsMapper;
    
    @Autowired
    private ETFManagerMapper managerMapper;
    
    @Autowired
    private ETFDocumentMapper documentMapper;
    
    @Autowired
    private ETFDistributionHistoryMapper distributionHistoryMapper;
    
    @Autowired
    private ETFAssetAllocationMapper assetAllocationMapper;
    
    @Autowired
    private ETFPortfolioMapper portfolioMapper;
    
    @Autowired
    private ETFPortfolioHoldingMapper portfolioHoldingMapper;
    
    @Autowired
    private ETFEquityStatisticsMapper equityStatisticsMapper;
    
    /**
     * 解析并保存ETF数据
     */
    @Transactional(rollbackFor = Exception.class)
    public void parseAndSaveETFData(MorningstarETFResponse response) {
        log.info("开始解析ETF数据，SecurityId: {}", response.getSecurityId());
        
        try {
            // 1. 保存基础信息
            saveBaseInfo(response);
            
            // 2. 保存风险评级
            saveRiskRatings(response);
            
            // 3. 保存基准信息
            saveBenchmarks(response);
            
            // 4. 保存业绩表现
            saveTrailingPerformance(response);
            
            // 5. 保存历史业绩
            saveHistoricalPerformance(response);
            
            // 6. 保存基金经理
            saveManagers(response);
            
            // 7. 保存分红历史
            saveDistributionHistory(response);
            
            // 8. 保存持仓信息
            savePortfolios(response);
            
            log.info("ETF数据解析完成，SecurityId: {}", response.getSecurityId());
        } catch (Exception e) {
            log.error("ETF数据解析失败，SecurityId: {}, Error: {}", response.getSecurityId(), e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 保存基础信息
     */
    private void saveBaseInfo(MorningstarETFResponse response) {
        log.debug("保存基础信息");
        
        ETFBaseInfo baseInfo = new ETFBaseInfo();
        baseInfo.setSecurityId(response.getSecurityId());
        baseInfo.setName(response.getName());
        baseInfo.setLegalName(response.getLegalName());
        baseInfo.setCategoryName(response.getCategoryName());
        baseInfo.setInvestmentType(response.getInvestmentType());
        baseInfo.setHoldingType(response.getHoldingType());
        baseInfo.setInceptionDate(parseDate(response.getInceptionDate()));
        
        if (response.getCurrency() != null) {
            baseInfo.setCurrencyId(response.getCurrency().getId());
            baseInfo.setCurrencyName(response.getCurrency().getName());
        }
        
        baseInfo.setClosePrice(response.getClosePrice());
        baseInfo.setDayChange(response.getDayChange());
        baseInfo.setInvestmentStrategy(response.getInvestmentStrategy());
        baseInfo.setManagementCompany(response.getManagementCompany());
        baseInfo.setAdvisor(response.getAdvisor());
        baseInfo.setSubAdvisor(response.getSubAdvisor());
        baseInfo.setAdministrator(response.getAdministrator());
        baseInfo.setCreatedAt(LocalDateTime.now());
        baseInfo.setUpdatedAt(LocalDateTime.now());
        
        // 检查是否已存在
        QueryWrapper<ETFBaseInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("security_id", response.getSecurityId());
        ETFBaseInfo existing = baseInfoMapper.selectOne(queryWrapper);
        
        if (existing != null) {
            baseInfo.setId(existing.getId());
            baseInfo.setCreatedAt(existing.getCreatedAt());
            baseInfoMapper.updateById(baseInfo);
            log.debug("更新基础信息，SecurityId: {}", response.getSecurityId());
        } else {
            baseInfoMapper.insert(baseInfo);
            log.debug("插入基础信息，SecurityId: {}", response.getSecurityId());
        }
    }
    
    /**
     * 保存风险评级
     */
    private void saveRiskRatings(MorningstarETFResponse response) {
        if (response.getRiskRating() == null) {
            return;
        }
        
        log.debug("保存风险评级");
        
        MorningstarETFResponse.RiskRatingDTO dto = response.getRiskRating();
        ETFRiskRating riskRating = new ETFRiskRating();
        riskRating.setSecurityId(response.getSecurityId());
        riskRating.setEffectiveDate(parseDate(dto.getEffectiveDate()));
        riskRating.setRiskScore(dto.getRiskScore());
        riskRating.setRiskRating(dto.getRiskRating());
        riskRating.setRiskScale(dto.getRiskScale());
        riskRating.setCreatedAt(LocalDateTime.now());
        riskRating.setUpdatedAt(LocalDateTime.now());
        
        // 检查是否已存在
        QueryWrapper<ETFRiskRating> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("security_id", response.getSecurityId())
                   .eq("effective_date", riskRating.getEffectiveDate());
        ETFRiskRating existing = riskRatingMapper.selectOne(queryWrapper);
        
        if (existing != null) {
            riskRating.setId(existing.getId());
            riskRating.setCreatedAt(existing.getCreatedAt());
            riskRatingMapper.updateById(riskRating);
        } else {
            riskRatingMapper.insert(riskRating);
        }
    }
    
    /**
     * 保存基准信息
     */
    private void saveBenchmarks(MorningstarETFResponse response) {
        if (response.getBenchmarks() == null || response.getBenchmarks().isEmpty()) {
            return;
        }
        
        log.debug("保存基准信息，数量: {}", response.getBenchmarks().size());
        
        for (MorningstarETFResponse.BenchmarkDTO dto : response.getBenchmarks()) {
            ETFBenchmark benchmark = new ETFBenchmark();
            benchmark.setSecurityId(response.getSecurityId());
            benchmark.setBenchmarkName(dto.getName());
            benchmark.setBenchmarkId(dto.getId());
            benchmark.setPrimaryBenchmark(dto.getIsPrimary());
            benchmark.setCreatedAt(LocalDateTime.now());
            benchmark.setUpdatedAt(LocalDateTime.now());
            
            // 检查是否已存在
            QueryWrapper<ETFBenchmark> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("security_id", response.getSecurityId())
                       .eq("benchmark_id", dto.getId());
            ETFBenchmark existing = benchmarkMapper.selectOne(queryWrapper);
            
            if (existing != null) {
                benchmark.setId(existing.getId());
                benchmark.setCreatedAt(existing.getCreatedAt());
                benchmarkMapper.updateById(benchmark);
            } else {
                benchmarkMapper.insert(benchmark);
            }
        }
    }
    
    /**
     * 保存业绩表现
     */
    private void saveTrailingPerformance(MorningstarETFResponse response) {
        if (response.getTrailingPerformance() == null || response.getTrailingPerformance().isEmpty()) {
            return;
        }
        
        log.debug("保存业绩表现，数量: {}", response.getTrailingPerformance().size());
        
        for (MorningstarETFResponse.TrailingPerformanceDTO dto : response.getTrailingPerformance()) {
            ETFTrailingPerformance performance = new ETFTrailingPerformance();
            performance.setSecurityId(response.getSecurityId());
            performance.setPeriod(dto.getPeriod());
            
            if (dto.getReturnData() != null) {
                performance.setReturnType(dto.getReturnData().getType());
                performance.setReturnValue(dto.getReturnData().getValue());
            }
            
            performance.setAsOfDate(parseDate(dto.getAsOfDate()));
            performance.setCreatedAt(LocalDateTime.now());
            performance.setUpdatedAt(LocalDateTime.now());
            
            // 检查是否已存在
            QueryWrapper<ETFTrailingPerformance> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("security_id", response.getSecurityId())
                       .eq("period", dto.getPeriod());
            if (dto.getReturnData() != null) {
                queryWrapper.eq("return_type", dto.getReturnData().getType());
            }
            ETFTrailingPerformance existing = trailingPerformanceMapper.selectOne(queryWrapper);
            
            if (existing != null) {
                performance.setId(existing.getId());
                performance.setCreatedAt(existing.getCreatedAt());
                trailingPerformanceMapper.updateById(performance);
            } else {
                trailingPerformanceMapper.insert(performance);
            }
        }
    }
    
    /**
     * 保存历史业绩
     */
    private void saveHistoricalPerformance(MorningstarETFResponse response) {
        if (response.getHistoricalPerformance() == null || response.getHistoricalPerformance().isEmpty()) {
            return;
        }
        
        log.debug("保存历史业绩，数量: {}", response.getHistoricalPerformance().size());
        
        for (MorningstarETFResponse.HistoricalPerformanceSeriesDTO dto : response.getHistoricalPerformance()) {
            ETFHistoricalPerformance performance = new ETFHistoricalPerformance();
            performance.setSecurityId(response.getSecurityId());
            performance.setCalendarYear(dto.getYear());
            performance.setReturnValue(dto.getReturnValue());
            performance.setCreatedAt(LocalDateTime.now());
            performance.setUpdatedAt(LocalDateTime.now());
            
            // 检查是否已存在
            QueryWrapper<ETFHistoricalPerformance> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("security_id", response.getSecurityId())
                       .eq("calendar_year", dto.getYear());
            ETFHistoricalPerformance existing = historicalPerformanceMapper.selectOne(queryWrapper);
            
            if (existing != null) {
                performance.setId(existing.getId());
                performance.setCreatedAt(existing.getCreatedAt());
                historicalPerformanceMapper.updateById(performance);
            } else {
                historicalPerformanceMapper.insert(performance);
            }
        }
    }
    
    /**
     * 保存基金经理
     */
    private void saveManagers(MorningstarETFResponse response) {
        if (response.getManagers() == null || response.getManagers().isEmpty()) {
            return;
        }
        
        log.debug("保存基金经理，数量: {}", response.getManagers().size());
        
        for (MorningstarETFResponse.ManagerDTO dto : response.getManagers()) {
            ETFManager manager = new ETFManager();
            manager.setSecurityId(response.getSecurityId());
            manager.setManagerName(dto.getName());
            manager.setStartDate(parseDate(dto.getStartDate()));
            manager.setTenureYears(dto.getTenure());
            manager.setBiography(dto.getBiography());
            manager.setCreatedAt(LocalDateTime.now());
            manager.setUpdatedAt(LocalDateTime.now());
            
            // 检查是否已存在
            QueryWrapper<ETFManager> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("security_id", response.getSecurityId())
                       .eq("manager_name", dto.getName());
            ETFManager existing = managerMapper.selectOne(queryWrapper);
            
            if (existing != null) {
                manager.setId(existing.getId());
                manager.setCreatedAt(existing.getCreatedAt());
                managerMapper.updateById(manager);
            } else {
                managerMapper.insert(manager);
            }
        }
    }
    
    /**
     * 保存分红历史
     */
    private void saveDistributionHistory(MorningstarETFResponse response) {
        if (response.getDistributions() == null || response.getDistributions().isEmpty()) {
            return;
        }
        
        log.debug("保存分红历史，数量: {}", response.getDistributions().size());
        
        for (MorningstarETFResponse.DistributionDTO dto : response.getDistributions()) {
            ETFDistributionHistory distribution = new ETFDistributionHistory();
            distribution.setSecurityId(response.getSecurityId());
            distribution.setDistributionDate(parseDate(dto.getDistributionDate()));
            distribution.setExDate(parseDate(dto.getExDate()));
            distribution.setRecordDate(parseDate(dto.getRecordDate()));
            distribution.setPayableDate(parseDate(dto.getPayableDate()));
            distribution.setDistributionAmount(dto.getAmount());
            distribution.setDistributionType(dto.getType());
            distribution.setReinvestPrice(dto.getReinvestPrice());
            distribution.setCreatedAt(LocalDateTime.now());
            distribution.setUpdatedAt(LocalDateTime.now());
            
            // 检查是否已存在
            QueryWrapper<ETFDistributionHistory> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("security_id", response.getSecurityId())
                       .eq("distribution_date", distribution.getDistributionDate());
            ETFDistributionHistory existing = distributionHistoryMapper.selectOne(queryWrapper);
            
            if (existing != null) {
                distribution.setId(existing.getId());
                distribution.setCreatedAt(existing.getCreatedAt());
                distributionHistoryMapper.updateById(distribution);
            } else {
                distributionHistoryMapper.insert(distribution);
            }
        }
    }
    
    /**
     * 保存持仓信息
     */
    private void savePortfolios(MorningstarETFResponse response) {
        if (response.getPortfolio() == null) {
            return;
        }
        
        log.debug("保存持仓信息");
        
        MorningstarETFResponse.PortfolioDTO portfolioDTO = response.getPortfolio();
        
        ETFPortfolio portfolio = new ETFPortfolio();
        portfolio.setSecurityId(response.getSecurityId());
        portfolio.setPortfolioName("Main Portfolio");
        portfolio.setPortfolioDate(parseDate(portfolioDTO.getDate()));
        portfolio.setTotalHoldings(portfolioDTO.getTotalHoldings());
        portfolio.setNetAssets(portfolioDTO.getNetAssets());
        portfolio.setCreatedAt(LocalDateTime.now());
        portfolio.setUpdatedAt(LocalDateTime.now());
        
        // 检查是否已存在
        QueryWrapper<ETFPortfolio> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("security_id", response.getSecurityId())
                   .eq("portfolio_date", portfolio.getPortfolioDate());
        ETFPortfolio existing = portfolioMapper.selectOne(queryWrapper);
        
        Long portfolioId;
        if (existing != null) {
            portfolio.setId(existing.getId());
            portfolio.setCreatedAt(existing.getCreatedAt());
            portfolioMapper.updateById(portfolio);
            portfolioId = existing.getId();
        } else {
            portfolioMapper.insert(portfolio);
            portfolioId = portfolio.getId();
        }
        
        // 保存持仓明细
        savePortfolioHoldings(response.getSecurityId(), portfolioId, portfolioDTO.getHoldings());
    }
    
    /**
     * 保存持仓明细
     */
    private void savePortfolioHoldings(String securityId, Long portfolioId, List<MorningstarETFResponse.HoldingDTO> holdings) {
        if (holdings == null || holdings.isEmpty()) {
            return;
        }
        
        log.debug("保存持仓明细，数量: {}", holdings.size());
        
        for (MorningstarETFResponse.HoldingDTO dto : holdings) {
            ETFPortfolioHolding holding = new ETFPortfolioHolding();
            holding.setPortfolioId(portfolioId);
            holding.setSecurityId(securityId);
            holding.setHoldingName(dto.getName());
            holding.setHoldingTicker(dto.getTicker());
            holding.setHoldingType(dto.getHoldingType());
            holding.setWeightPercentage(dto.getWeight());
            holding.setShares(dto.getShares());
            holding.setMarketValue(dto.getMarketValue());
            holding.setSector(dto.getSector());
            holding.setCountry(dto.getCountry());
            holding.setCreatedAt(LocalDateTime.now());
            holding.setUpdatedAt(LocalDateTime.now());
            
            // 检查是否已存在
            QueryWrapper<ETFPortfolioHolding> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("portfolio_id", portfolioId)
                       .eq("holding_name", dto.getName());
            ETFPortfolioHolding existing = portfolioHoldingMapper.selectOne(queryWrapper);
            
            if (existing != null) {
                holding.setId(existing.getId());
                holding.setCreatedAt(existing.getCreatedAt());
                portfolioHoldingMapper.updateById(holding);
            } else {
                portfolioHoldingMapper.insert(holding);
            }
        }
    }
    
    /**
     * 解析日期字符串
     */
    private LocalDate parseDate(String dateString) {
        if (!StringUtils.hasText(dateString)) {
            return null;
        }
        
        try {
            // 支持多种日期格式
            DateTimeFormatter[] formatters = {
                DateTimeFormatter.ISO_LOCAL_DATE,
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            };
            
            for (DateTimeFormatter formatter : formatters) {
                try {
                    return LocalDate.parse(dateString, formatter);
                } catch (Exception e) {
                    // 尝试下一个格式
                }
            }
            
            log.warn("无法解析日期: {}", dateString);
            return null;
        } catch (Exception e) {
            log.error("日期解析错误: {}", dateString, e);
            return null;
        }
    }
}
