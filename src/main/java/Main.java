import models.Product;
import models.Storage;
import models.Text;
import services.ServiceHibernate;
import services.entity.ProductService;
import services.entity.StorageService;

import java.sql.Date;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ServiceHibernate.start();
        Text text = new Text("dadsasd");
        Product product = new Product("das", Date.valueOf(LocalDate.now()), text, 18, 5000);
        Storage storage = new Storage(product, 10);

        ProductService ps = new ProductService();
        StorageService ss = new StorageService();
        ps.save(product);
        ss.save(storage);
    }
}
