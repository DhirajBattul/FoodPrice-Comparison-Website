package com.example.ComparisonService.DTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CachedPriceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer productMappingId;
    private String platform;
    private BigDecimal price;
    private Boolean isAvailable;
    private LocalDateTime fetchedAt;

    public CachedPriceDTO() {}

    public CachedPriceDTO(Integer productMappingId, String platform,
                          BigDecimal price, Boolean isAvailable) {
        this.productMappingId = productMappingId;
        this.platform = platform;
        this.price = price;
        this.isAvailable = isAvailable;
        this.fetchedAt = LocalDateTime.now();
    }

    public Integer getProductMappingId() { return productMappingId; }
    public void setProductMappingId(Integer productMappingId) { this.productMappingId = productMappingId; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }

    public LocalDateTime getFetchedAt() { return fetchedAt; }
    public void setFetchedAt(LocalDateTime fetchedAt) { this.fetchedAt = fetchedAt; }

    @Override
    public String toString() {
        return "CachedPriceDTO [productMappingId=" + productMappingId +
               ", platform=" + platform + ", price=" + price +
               ", isAvailable=" + isAvailable + ", fetchedAt=" + fetchedAt + "]";
    }
}