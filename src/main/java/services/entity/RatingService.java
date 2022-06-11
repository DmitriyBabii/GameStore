package services.entity;

import intarfaces.Entity;
import intarfaces.EntityService;
import models.Product;
import models.Rating;
import models.enums.EProduct;
import models.enums.ERating;
import models.enums.EStorage;
import org.hibernate.query.NativeQuery;
import services.ServiceHibernate;

public class RatingService implements EntityService {

    @Override
    public void createTable() {
        ServiceHibernate.open();
        StringBuilder stringBuilder = new StringBuilder();
        for (ERating rating : ERating.values()) {
            stringBuilder.append(rating.getQuery());
        }
        ServiceHibernate.getSession().createSQLQuery(stringBuilder.toString()).executeUpdate();
        ServiceHibernate.close();
    }

    @Override
    public void save(Entity entity) {
        try {
            Rating rating = (Rating) entity;
            StringBuilder sb = new StringBuilder("INSERT INTO gameshop.rating ");
            sb.append(getColumns())
                    .append(" VALUES")
                    .append(getParams())
                    .append(";");

            ServiceHibernate.open();
            NativeQuery query = ServiceHibernate.getSession().createSQLQuery(sb.toString());
            query.setParameter(ERating.id_user_fk.toString(), rating.getClient().getId());
            query.setParameter(ERating.id_product_fk.toString(), rating.getProduct().getId());
            query.setParameter(ERating.review.toString(), rating.getReview());
            query.setParameter(ERating.review_date.toString(),rating.getReviewDate());
            query.executeUpdate();
            ServiceHibernate.close();

        } catch (ClassCastException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(Entity entity) {

    }

    @Override
    public void delete(Entity entity) {

    }

    @Override
    public String getColumns() {
        StringBuilder sb = new StringBuilder("(");
        int count = 0;
        for (ERating p : ERating.values()) {
            sb.append(p)
                    .append((++count < ERating.values().length) ? "," : ")");
        }
        return sb.toString();
    }

    @Override
    public String getParams() {
        StringBuilder sb = new StringBuilder("(");
        int count = 0;
        for (ERating p : ERating.values()) {
            sb.append(":").append(p)
                    .append((++count < ERating.values().length) ? "," : ")");
        }
        return sb.toString();
    }
}
