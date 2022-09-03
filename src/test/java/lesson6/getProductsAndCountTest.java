package lesson6;

import lesson5.api.ProductService;
import lesson5.utils.RetrofitUtils;
import okhttp3.ResponseBody;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;


public class getProductsAndCountTest {
    static ProductService productService;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @Test
    void getProductsPositiveTest() throws IOException {
        Response<ResponseBody> response = productService.getProducts()
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        db.dao.ProductsMapper productsMapper = sqlSession.getMapper(db.dao.ProductsMapper.class);
        db.model.ProductsExample productsExample = new db.model.ProductsExample();
        productsExample.createCriteria().andCategory_idIsNotNull();
        List<db.model.Products> list = productsMapper.selectByExample(productsExample);
        System.out.println(productsMapper.countByExample(productsExample));

        sqlSession.commit();
    }

}
