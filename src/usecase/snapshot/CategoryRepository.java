package usecase.snapshot;

import org.springframework.data.jpa.repository.JpaRepository;
import usecase.snapshot.domain.Category;

/**
 * @author: keesun
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
