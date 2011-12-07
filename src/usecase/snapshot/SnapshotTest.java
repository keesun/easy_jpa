package usecase.snapshot;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import usecase.snapshot.domain.Category;
import usecase.snapshot.domain.Product;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 *
 * OneToOne 맵핑을 Lazy로 설정하는 경우를 테스트 합니다.
 *
 * Product 1 -> 1 ProductDetails
 * Product 1 -> 1 ProductInfo
 *
 * 이때, Product에서 ProductDetails와 ProductInfo의 정보가 Lazy loading 되는지 확인합니다.
 *
 * 확인 경과
 * 1. 위에 적어둔 관계 그대로 단방향일 경우에는 제대로 Lazy loading 되고 있다.
 * 2. 양방향으로 맵핑하는 경우 다음과 같은 현상이 발생한다.
 *      2-1. 양방향 관계일 때 mappedBy="product"를 사용해서 외례키를 ProductDetails과 ProductInfo로 넘겨버린 경우에는
 *      다음 링크에서 설명하는 문제가 재현된다. http://whiteship.me/?p=13301
 *      2-2. 양방향 관계일 때 mappedBy 속성을 사용하지 않으면, 외례키가 Product에 그대로 남아있는데,
 *      이때는 단방향 맵핑일 떄와 똑같이 동작한다.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class SnapshotTest {

    @Autowired ProductRepository productRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired DataSource dataSource;
    @Autowired ProductService service;

    @Autowired EntityManagerFactory entityManagerFactory;

    @PersistenceContext EntityManager entityManager;

    @Test
    public void lazyLoading() throws SQLException, IOException, DatabaseUnitException {
        //INSERT Test Data
        assertThat(productRepository, is(notNullValue()));
        IDatabaseConnection connection = new DatabaseDataSourceConnection(dataSource);
        assertThat(connection, is(notNullValue()));

        Resource resource = new ClassPathResource("/usecase/snapshot/testData.xml");
        assertThat(resource.isReadable(), is(true));
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        IDataSet dataSet = builder.build(resource.getFile());

        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);

        //Test
        Product product = productRepository.findOne(1l);
        assertThat(product, is(notNullValue()));
        assertThat(product.getName(), is("keesun"));

        System.out.println("========LAZY LOADING...(ProductDetails)=========");
        product.getProductDetails().getDetails();

        System.out.println("========LAZY LOADING...(ProductInfo)=========");
        product.getProductInfo().getInfo();
    }

    @Test
    public void secondLevelCache(){
        StopWatch stopWatch = new StopWatch();
        service.insertTestData();

        List<Category> categoryList = categoryRepository.findAll();
//        printCache(categoryList);
        for(int i = 0 ; i < 5 ; i++) {
            stopWatch.start("read all <" + i + ">");
            List<Product> productList = productRepository.findAll();

            for(Product p : productList) {
                //Lazy Loading
                Category category = p.getCategory();
                category.getName();

                p.getExceptionalCategory().getName();
            }
            stopWatch.stop();
        }

        System.out.println("=====================");
        System.out.println(stopWatch.prettyPrint());

//        printCache(categoryList);

    }

    private void printCache(List<Category> categoryList) {
        System.out.println("---------------------");
        Cache cache = entityManager.getEntityManagerFactory().getCache();
        for(Category c : categoryList) {
            System.out.println(cache.contains(Category.class, c));
        }
    }

}
