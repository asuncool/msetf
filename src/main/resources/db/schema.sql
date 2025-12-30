-- Morningstar ETF Data Schema
-- PostgreSQL Database Schema

-- Drop tables if they exist (in reverse order of dependencies)
DROP TABLE IF EXISTS etf_portfolio_holdings CASCADE;
DROP TABLE IF EXISTS etf_portfolio CASCADE;
DROP TABLE IF EXISTS etf_equity_statistics CASCADE;
DROP TABLE IF EXISTS etf_asset_allocation CASCADE;
DROP TABLE IF EXISTS etf_distribution_history CASCADE;
DROP TABLE IF EXISTS etf_document CASCADE;
DROP TABLE IF EXISTS etf_manager CASCADE;
DROP TABLE IF EXISTS etf_risk_statistics CASCADE;
DROP TABLE IF EXISTS etf_historical_performance CASCADE;
DROP TABLE IF EXISTS etf_trailing_performance CASCADE;
DROP TABLE IF EXISTS etf_benchmark CASCADE;
DROP TABLE IF EXISTS etf_risk_rating CASCADE;
DROP TABLE IF EXISTS etf_base_info CASCADE;

-- 1. ETF Base Info Table
CREATE TABLE etf_base_info (
    id SERIAL PRIMARY KEY,
    security_id VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(255),
    legal_name VARCHAR(255),
    category_name VARCHAR(100),
    investment_type VARCHAR(100),
    holding_type VARCHAR(100),
    inception_date DATE,
    currency_id VARCHAR(10),
    currency_name VARCHAR(50),
    close_price DECIMAL(20, 4),
    day_change DECIMAL(20, 4),
    investment_strategy TEXT,
    management_company VARCHAR(255),
    advisor VARCHAR(255),
    sub_advisor VARCHAR(255),
    administrator VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. ETF Risk Rating Table
CREATE TABLE etf_risk_rating (
    id SERIAL PRIMARY KEY,
    security_id VARCHAR(50) NOT NULL,
    effective_date DATE,
    risk_score DECIMAL(5, 2),
    risk_rating VARCHAR(50),
    risk_scale INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (security_id) REFERENCES etf_base_info(security_id) ON DELETE CASCADE
);

-- 3. ETF Benchmark Table
CREATE TABLE etf_benchmark (
    id SERIAL PRIMARY KEY,
    security_id VARCHAR(50) NOT NULL,
    benchmark_name VARCHAR(255),
    benchmark_id VARCHAR(50),
    primary_benchmark BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (security_id) REFERENCES etf_base_info(security_id) ON DELETE CASCADE
);

-- 4. ETF Trailing Performance Table
CREATE TABLE etf_trailing_performance (
    id SERIAL PRIMARY KEY,
    security_id VARCHAR(50) NOT NULL,
    period VARCHAR(50),
    return_type VARCHAR(50),
    return_value DECIMAL(10, 4),
    as_of_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (security_id) REFERENCES etf_base_info(security_id) ON DELETE CASCADE
);

-- 5. ETF Historical Performance Table
CREATE TABLE etf_historical_performance (
    id SERIAL PRIMARY KEY,
    security_id VARCHAR(50) NOT NULL,
    calendar_year INTEGER,
    return_value DECIMAL(10, 4),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (security_id) REFERENCES etf_base_info(security_id) ON DELETE CASCADE
);

-- 6. ETF Risk Statistics Table
CREATE TABLE etf_risk_statistics (
    id SERIAL PRIMARY KEY,
    security_id VARCHAR(50) NOT NULL,
    statistic_type VARCHAR(100),
    period VARCHAR(50),
    statistic_value DECIMAL(20, 6),
    as_of_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (security_id) REFERENCES etf_base_info(security_id) ON DELETE CASCADE
);

-- 7. ETF Manager Table
CREATE TABLE etf_manager (
    id SERIAL PRIMARY KEY,
    security_id VARCHAR(50) NOT NULL,
    manager_name VARCHAR(255),
    start_date DATE,
    tenure_years DECIMAL(5, 2),
    biography TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (security_id) REFERENCES etf_base_info(security_id) ON DELETE CASCADE
);

-- 8. ETF Document Table
CREATE TABLE etf_document (
    id SERIAL PRIMARY KEY,
    security_id VARCHAR(50) NOT NULL,
    document_type VARCHAR(100),
    document_name VARCHAR(255),
    document_url TEXT,
    document_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (security_id) REFERENCES etf_base_info(security_id) ON DELETE CASCADE
);

-- 9. ETF Distribution History Table
CREATE TABLE etf_distribution_history (
    id SERIAL PRIMARY KEY,
    security_id VARCHAR(50) NOT NULL,
    distribution_date DATE,
    ex_date DATE,
    record_date DATE,
    payable_date DATE,
    distribution_amount DECIMAL(20, 6),
    distribution_type VARCHAR(50),
    reinvest_price DECIMAL(20, 6),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (security_id) REFERENCES etf_base_info(security_id) ON DELETE CASCADE
);

-- 10. ETF Asset Allocation Table
CREATE TABLE etf_asset_allocation (
    id SERIAL PRIMARY KEY,
    security_id VARCHAR(50) NOT NULL,
    asset_type VARCHAR(100),
    allocation_percentage DECIMAL(5, 2),
    net_assets DECIMAL(20, 2),
    as_of_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (security_id) REFERENCES etf_base_info(security_id) ON DELETE CASCADE
);

-- 11. ETF Portfolio Table
CREATE TABLE etf_portfolio (
    id SERIAL PRIMARY KEY,
    security_id VARCHAR(50) NOT NULL,
    portfolio_name VARCHAR(255),
    portfolio_date DATE,
    total_holdings INTEGER,
    net_assets DECIMAL(20, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (security_id) REFERENCES etf_base_info(security_id) ON DELETE CASCADE
);

-- 12. ETF Portfolio Holdings Table
CREATE TABLE etf_portfolio_holdings (
    id SERIAL PRIMARY KEY,
    portfolio_id INTEGER NOT NULL,
    security_id VARCHAR(50) NOT NULL,
    holding_name VARCHAR(255),
    holding_ticker VARCHAR(50),
    holding_type VARCHAR(100),
    weight_percentage DECIMAL(8, 4),
    shares DECIMAL(20, 2),
    market_value DECIMAL(20, 2),
    sector VARCHAR(100),
    country VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (portfolio_id) REFERENCES etf_portfolio(id) ON DELETE CASCADE,
    FOREIGN KEY (security_id) REFERENCES etf_base_info(security_id) ON DELETE CASCADE
);

-- 13. ETF Equity Statistics Table
CREATE TABLE etf_equity_statistics (
    id SERIAL PRIMARY KEY,
    security_id VARCHAR(50) NOT NULL,
    statistic_name VARCHAR(100),
    statistic_value DECIMAL(20, 4),
    as_of_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (security_id) REFERENCES etf_base_info(security_id) ON DELETE CASCADE
);

-- Create indexes for better query performance
CREATE INDEX idx_etf_risk_rating_security_id ON etf_risk_rating(security_id);
CREATE INDEX idx_etf_benchmark_security_id ON etf_benchmark(security_id);
CREATE INDEX idx_etf_trailing_performance_security_id ON etf_trailing_performance(security_id);
CREATE INDEX idx_etf_historical_performance_security_id ON etf_historical_performance(security_id);
CREATE INDEX idx_etf_risk_statistics_security_id ON etf_risk_statistics(security_id);
CREATE INDEX idx_etf_manager_security_id ON etf_manager(security_id);
CREATE INDEX idx_etf_document_security_id ON etf_document(security_id);
CREATE INDEX idx_etf_distribution_history_security_id ON etf_distribution_history(security_id);
CREATE INDEX idx_etf_asset_allocation_security_id ON etf_asset_allocation(security_id);
CREATE INDEX idx_etf_portfolio_security_id ON etf_portfolio(security_id);
CREATE INDEX idx_etf_portfolio_holdings_portfolio_id ON etf_portfolio_holdings(portfolio_id);
CREATE INDEX idx_etf_portfolio_holdings_security_id ON etf_portfolio_holdings(security_id);
CREATE INDEX idx_etf_equity_statistics_security_id ON etf_equity_statistics(security_id);

-- Add comments to tables
COMMENT ON TABLE etf_base_info IS 'ETF基础信息表';
COMMENT ON TABLE etf_risk_rating IS 'ETF风险评级表';
COMMENT ON TABLE etf_benchmark IS 'ETF基准信息表';
COMMENT ON TABLE etf_trailing_performance IS 'ETF业绩表现表';
COMMENT ON TABLE etf_historical_performance IS 'ETF历史业绩表';
COMMENT ON TABLE etf_risk_statistics IS 'ETF风险统计表';
COMMENT ON TABLE etf_manager IS 'ETF基金经理表';
COMMENT ON TABLE etf_document IS 'ETF文档信息表';
COMMENT ON TABLE etf_distribution_history IS 'ETF分红历史表';
COMMENT ON TABLE etf_asset_allocation IS 'ETF资产配置表';
COMMENT ON TABLE etf_portfolio IS 'ETF持仓组合表';
COMMENT ON TABLE etf_portfolio_holdings IS 'ETF持仓明细表';
COMMENT ON TABLE etf_equity_statistics IS 'ETF股票统计表';
