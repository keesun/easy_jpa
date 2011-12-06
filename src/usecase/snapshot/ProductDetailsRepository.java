package usecase.snapshot;

import org.springframework.data.jpa.repository.JpaRepository;
import usecase.snapshot.domain.ProductDetails;

/**
 * @author: keesun
 */
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long> {
}
