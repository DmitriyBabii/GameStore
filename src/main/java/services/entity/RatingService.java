package services.entity;

import intarfaces.Entity;
import intarfaces.EntityRealize;
import models.Product;
import models.Rating;
import models.enums.EProduct;
import models.enums.ERating;
import org.hibernate.query.NativeQuery;
import services.ServiceHibernate;

import javax.management.Query;

public final class RatingService extends EntityService {

    @Override
    public void createTable() {
        ServiceHibernate.open();
        StringBuilder stringBuilder = new StringBuilder();
        for (ERating rating : ERating.values()) {
            stringBuilder.append(rating.getQuery());
        }
        ServiceHibernate.getSession()
                .createSQLQuery(stringBuilder.toString())
                .executeUpdate();
        ServiceHibernate.close();
    }

    @Override
    public void save(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            NativeQuery query = ServiceHibernate
                    .getSession()
                    .createSQLQuery(getInsertQuery());
            setAllParams(query, value).executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    public void update(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            NativeQuery query = ServiceHibernate
                    .getSession()
                    .createSQLQuery(getUpdateQuery());
            setAllParams(query, value).executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    public void delete(Entity... entity) {

    }

    @Override
    protected String getColumns() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (ERating p : ERating.values()) {
            sb.append(p)
                    .append((++count < ERating.values().length) ? "," : "");
        }
        return sb.toString();
    }

    @Override
    protected String getParams() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (ERating p : ERating.values()) {
            sb.append(":").append(p)
                    .append((++count < ERating.values().length) ? "," : "");
        }
        return sb.toString();
    }

    @Override
    protected NativeQuery setAllParams(NativeQuery query, Entity entity) {
        Rating rating = (Rating) entity;
        return query.setParameter(ERating.id_user_fk.toString(), rating.getClient().getId())
                .setParameter(ERating.id_product_fk.toString(), rating.getProduct().getId())
                .setParameter(ERating.review.toString(), rating.getReview())
                .setParameter(ERating.review_date.toString(), rating.getReviewDate());
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO gameshop.rating (" + getColumns() +
                ") VALUES(" +
                getParams() +
                ")";
    }

    @Override
    protected String getUpdateQuery() {
        StringBuilder sb = new StringBuilder("UPDATE gameshop.rating SET ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        for (int i = 1; i < columns.length; i++) {
            sb.append(columns[i])
                    .append("=")
                    .append(params[i])
                    .append((i < columns.length - 1) ? ", " : " ");
        }
        sb.append("WHERE ")
                .append(columns[0])
                .append("=")
                .append(params[0]);
        return sb.toString();
    }
}
