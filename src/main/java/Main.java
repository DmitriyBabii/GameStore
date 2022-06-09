import models.Product;
import models.Storage;
import models.Text;
import services.ServiceHibernate;

import java.sql.Date;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Text text = new Text(
                "It`s the best game!"
        );
        Product product = new Product("Witcher 3", Date.valueOf(LocalDate.now()), text, 18, 1200);
        Storage storage = new Storage(product, 12);
        final ServiceHibernate sr = new ServiceHibernate();
    }
}
