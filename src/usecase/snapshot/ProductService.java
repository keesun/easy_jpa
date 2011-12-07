package usecase.snapshot;

import me.whiteship.domain.Book;
import org.hsqldb.lib.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usecase.snapshot.ProductRepository;
import usecase.snapshot.domain.Category;
import usecase.snapshot.domain.Product;
import usecase.snapshot.domain.ProductDetails;
import usecase.snapshot.domain.ProductInfo;

import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: keesun
 */
@Service
@Transactional(readOnly = true)
public class ProductService {

    @Autowired ProductRepository repository;
    @Autowired ProductDetailsRepository productDetailsRepository;
    @Autowired ProductInfoRepository productInfoRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired EntityManagerFactory entityManagerFactory;

    private static final int CATEGORY_COUNT = 5;
    private static final int PRODUCT_COUNT = 50;

    public List<Product> getAll() {
        return repository.findAll();
    }

    public void insertTestData() {
        List<Long> prdIdList = new ArrayList<Long>();
        List<Long> categoryIdList = new ArrayList<Long>();

        for(int i = 0 ; i < CATEGORY_COUNT ; i++) {
            Category category = new Category();
            category.setName("cate" + i);
            categoryRepository.save(category);
            categoryIdList.add(category.getId());
        }

        for(int i = 0 ; i < PRODUCT_COUNT ; i++) {
            Product product = new Product();
            product.setPrice(new BigDecimal(i));
            product.setName("Book" + i);
            product.setCategory(categoryRepository.findOne(categoryIdList.get((int) (Math.random() * CATEGORY_COUNT))));
            product.setExceptionalCategory(categoryRepository.findOne(categoryIdList.get((int) (Math.random() * CATEGORY_COUNT))));

            ProductDetails productDetails = new ProductDetails();
            productDetails.setDetails("Detail" + i);
            productDetailsRepository.save(productDetails);
            product.setProductDetails(productDetails);

            ProductInfo productInfo = new ProductInfo();
            productInfo.setInfo("Info" + i);
            productInfoRepository.save(productInfo);
            product.setProductInfo(productInfo);

            repository.save(product);
            prdIdList.add(product.getId());
        }

    }

    @Transactional
    public void evictCacheData() {
        entityManagerFactory.getCache().evict(Category.class);
    }

    public void evictAllCache() {
        entityManagerFactory.getCache().evictAll();
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
