package usecase.snapshot.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Product implements Serializable{

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    private BigDecimal price;

    private String name;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    private ProductDetails productDetails;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    private ProductInfo productInfo;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private Category exceptionalCategory;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getExceptionalCategory() {
        return exceptionalCategory;
    }

    public void setExceptionalCategory(Category exceptionalCategory) {
        this.exceptionalCategory = exceptionalCategory;
    }
}
