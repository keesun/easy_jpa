package usecase.snapshot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import usecase.snapshot.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
