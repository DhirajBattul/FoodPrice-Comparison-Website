package com.example.ComparisonService.DTO;

public class ComparisonResponse {
	private ProductDTO zomatoResult;
    private ProductDTO swiggyResult;
    private String bestPriceSource; // "Zomato", "Swiggy", or "Equal"
    private String message;
    
    
    
	public ProductDTO getZomatoResult() {
		return zomatoResult;
	}
	public void setZomatoResult(ProductDTO zomatoResult) {
		this.zomatoResult = zomatoResult;
	}
	public ProductDTO getSwiggyResult() {
		return swiggyResult;
	}
	public void setSwiggyResult(ProductDTO swiggyResult) {
		this.swiggyResult = swiggyResult;
	}
	public String getBestPriceSource() {
		return bestPriceSource;
	}
	public void setBestPriceSource(String bestPriceSource) {
		this.bestPriceSource = bestPriceSource;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ComparisonResponse [zomatoResult=" + zomatoResult + ", swiggyResult=" + swiggyResult
				+ ", bestPriceSource=" + bestPriceSource + ", message=" + message + "]";
	}
	public ComparisonResponse(ProductDTO zomatoResult, ProductDTO swiggyResult, String bestPriceSource,
			String message) {
		super();
		this.zomatoResult = zomatoResult;
		this.swiggyResult = swiggyResult;
		this.bestPriceSource = bestPriceSource;
		this.message = message;
	}
	public ComparisonResponse() {
		super();
	}
	
	
    
    
}
