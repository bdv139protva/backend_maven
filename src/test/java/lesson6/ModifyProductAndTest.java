package lesson6;

import com.github.javafaker.Faker;
import lesson5.api.ProductService;
import lesson5.dto.Product;
import lesson5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;


public class ModifyProductAndTest {

    static ProductService productService;
    Product product = null;
    Product productMod = null;
    Faker faker = new Faker();
    int id;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp() throws IOException {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));
    }

    @Test
    void modifyProductPositiveTest() throws IOException {
        Response<Product> response = productService.createProduct(product)
                .execute();
        id =  response.body().getId();
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        db.dao.ProductsMapper productsMapper = sqlSession.getMapper(db.dao.ProductsMapper.class);

        db.model.Products selected = productsMapper.selectByPrimaryKey((long) id);
        System.out.println("ID: " + selected.getId() + "\ntitle: " + selected.getTitle() + "\nprice: " + selected.getPrice() + "\ncategory id: " + selected.getCategory_id());
        sqlSession.commit();

        productMod = new Product()
                .withId(id)
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));

        Response<Product> responseMod = productService.modifyProduct(productMod)
                .execute();
        assertThat(responseMod.isSuccessful(), CoreMatchers.is(true));

        db.model.Products modify = productsMapper.selectByPrimaryKey((long) id);
        System.out.println("ID: " + modify.getId() + "\ntitle: " + modify.getTitle() + "\nprice: " + modify.getPrice() + "\ncategory id: " + modify.getCategory_id());
        sqlSession.commit();
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }
}


