package services.entity;

import intarfaces.Entity;
import models.Criterion;
import models.Rating;
import models.enums.EProduct;
import models.enums.ERating;
import models.enums.EUser;
import models.figures.Client;
import org.hibernate.query.NativeQuery;
import services.CriterionService;
import services.ServiceHibernate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
            Rating rating = (Rating) value;
            ServiceHibernate.getSession().createSQLQuery(getInsertQuery())
                    .setParameter(ERating.id_user_fk.toString(), rating.getClient().getId())
                    .setParameter(ERating.id_product_fk.toString(), rating.getProduct().getId())
                    .setParameter(ERating.review.toString(), rating.getReview())
                    .setParameter(ERating.review_date.toString(), rating.getReviewDate())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    public void update(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            Rating rating = (Rating) value;
            ServiceHibernate.getSession().createSQLQuery(getUpdateQuery())
                    .setParameter(ERating.id_user_fk.toString(), rating.getClient().getId())
                    .setParameter(ERating.id_product_fk.toString(), rating.getProduct().getId())
                    .setParameter(ERating.review.toString(), rating.getReview())
                    .setParameter(ERating.review_date.toString(), rating.getReviewDate())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    @Override
    public void delete(Entity... entity) {
        ServiceHibernate.open();
        for (Entity value : entity) {
            Rating rating = (Rating) value;
            ServiceHibernate.getSession()
                    .createSQLQuery(getDeleteQuery())
                    .setParameter(ERating.id_user_fk.toString(), rating.getClient().getId())
                    .setParameter(ERating.id_product_fk.toString(), rating.getProduct().getId())
                    .executeUpdate();
        }
        ServiceHibernate.close();
    }

    public List<Rating> select(List<Criterion> criterionList) {
        ServiceHibernate.open();
        NativeQuery query = ServiceHibernate.getSession().createSQLQuery(getSelectQuery(criterionList));
        for (Criterion criterion : criterionList) {
            query.setParameter(criterion.getParameter().toString(), criterion.getValue());
        }
        List<Object[]> resultList = query.list();
        ServiceHibernate.close();

        return getRatings(resultList);
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

    @Override
    protected String getDeleteQuery() {
        StringBuilder sb = new StringBuilder("DELETE FROM gameshop.rating ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        sb.append("WHERE ").append(columns[0]).append("=").append(params[0])
                .append(" AND ").append(columns[1]).append("=").append(params[1]);
        return sb.toString();
    }

    protected String getSelectQuery(List<Criterion> criterionList) {
        StringBuilder sb = new StringBuilder("SELECT * FROM gameshop.rating WHERE ");
        for (int i = 0; i < criterionList.size(); i++) {
            sb.append(criterionList.get(i).getParameter())
                    .append("=:")
                    .append(criterionList.get(i).getParameter())
                    .append((i + 1) < criterionList.size() ? " AND " : "");
        }
        return sb.toString();
    }

    protected List<Rating> getRatings(List<Object[]> resultList) {
        UserService us = new UserService();
        ProductService ps = new ProductService();
        CriterionService cProduct = new CriterionService();
        CriterionService cUser = new CriterionService();

        List<Rating> productList = new ArrayList<>();
        for (Object[] o : resultList) {
            cUser.addCriterion(EUser.id_user, (String) o[0]);
            cProduct.addCriterion(EProduct.id_product, (String) o[1]);
            productList.add(
                    Rating.builder()
                            .client((Client) us.select(cUser.getCriterionList()).get(0))
                            .product(ps.select(cProduct.getCriterionList()).get(0))
                            .review((String) o[2])
                            .reviewDate((Date) o[3])
                            .build()
            );
        }
        return productList;
    }
}
