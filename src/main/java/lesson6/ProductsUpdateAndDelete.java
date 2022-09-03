package lesson6;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ProductsUpdateAndDelete {

    public static void main( String[] args ) throws IOException {
        SqlSession session = null;
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new
                    SqlSessionFactoryBuilder().build(inputStream);
            session = sqlSessionFactory.openSession();
            db.dao.ProductsMapper productsMapper = session.getMapper(db.dao.ProductsMapper.class);
            db.model.ProductsExample example = new db.model.ProductsExample();

            example.createCriteria().andIdEqualTo(1l);
            List<db.model.Products> list = productsMapper.selectByExample(example);
            System.out.println(productsMapper.countByExample(example));

            db.model.Products products = new db.model.Products();
            products.setTitle("Cherry");
            products.setId(7l);
            productsMapper.insert(products);
            session.commit();

            db.model.ProductsExample example2 = new db.model.ProductsExample();
            example2.createCriteria().andTitleLike("%Cherry%");
            List<db.model.Products> list2 = productsMapper.selectByExample(example2);
            db.model.Products categories2 = list2.get(0);
            categories2.setTitle("Lemon");
            categories2.setId(17l);
            productsMapper.updateByPrimaryKey(categories2);
            session.commit();

            productsMapper.deleteByPrimaryKey(categories2.getId());
            session.commit();

        } finally {
            session.close();
        }


    }

}
