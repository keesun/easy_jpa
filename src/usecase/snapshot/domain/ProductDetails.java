package usecase.snapshot.domain;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
public class ProductDetails {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    private String details;

//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "productDetails", optional = false)
//    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
}
