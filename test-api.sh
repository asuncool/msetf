#!/bin/bash

# Test script for Morningstar ETF Data Parser API
# This script demonstrates how to use the API to parse and store ETF data

API_URL="http://localhost:8080/api/etf"

echo "=========================================="
echo "Morningstar ETF Data Parser API Test"
echo "=========================================="
echo ""

# Test 1: Health Check
echo "Test 1: Health Check"
echo "-------------------"
echo "GET ${API_URL}/health"
echo ""
curl -s -X GET ${API_URL}/health | jq '.'
echo ""
echo ""

# Test 2: Parse ETF Data
echo "Test 2: Parse ETF Data"
echo "-------------------"
echo "POST ${API_URL}/parse"
echo ""
curl -s -X POST ${API_URL}/parse \
  -H "Content-Type: application/json" \
  -d @sample-data.json | jq '.'
echo ""
echo ""

echo "=========================================="
echo "Test completed!"
echo "=========================================="
