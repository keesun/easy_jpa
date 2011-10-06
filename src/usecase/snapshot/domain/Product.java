package usecase.snapshot.domain;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    private BigDecimal price;

    private String name;

    @OneToOne(fetch = FetchType.LAZY, optional = true, mappedBy = "product")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    private ProductDetails productDetails;

    @OneToOne(fetch = FetchType.LAZY, optional = true, mappedBy = "product")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    private ProductInfo productInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
