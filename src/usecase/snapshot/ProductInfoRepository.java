package usecase.snapshot;

import org.springframework.data.jpa.repository.JpaRepository;
import usecase.snapshot.domain.ProductInfo;

/**
 * @author: keesun
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
}
