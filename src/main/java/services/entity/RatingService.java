package services.entity;

import intarfaces.Entity;
import models.Criterion;
import models.Product;
import models.Rating;
import models.enums.EProduct;
import models.enums.ERating;
import models.enums.EUser;
import models.figures.AuthorizedUser;
import models.figures.Client;
import services.CriterionService;
import services.ServiceHibernate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public final class RatingService extends EntityService {
    private static final CriterionService cs = new CriterionService();

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
        return "INSERT INTO game_shop.rating (" + getColumns() +
                ") VALUES(" +
                getParams() +
                ")";
    }

    @Override
    protected String getUpdateQuery() {
        StringBuilder sb = new StringBuilder("UPDATE game_shop.rating SET ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        for (int i = 1; i < columns.length; i++) {
            sb.append(columns[i])
                    .append("=")
                    .append(params[i])
                    .append((i < columns.length - 1) ? ", " : " ");
        }
        sb.append("WHERE ")
                .append(columns[ERating.id_user_fk.ordinal()])
                .append("=")
                .append(params[ERating.id_user_fk.ordinal()])
                .append(" AND ")
                .append(columns[ERating.id_product_fk.ordinal()])
                .append("=")
                .append(params[ERating.id_product_fk.ordinal()]);
        return sb.toString();
    }

    @Override
    protected String getDeleteQuery() {
        StringBuilder sb = new StringBuilder("DELETE FROM game_shop.rating ");

        String[] columns = getColumns().split(",");
        String[] params = getParams().split(",");

        sb.append("WHERE ")
                .append(columns[ERating.id_user_fk.ordinal()])
                .append("=")
                .append(params[ERating.id_user_fk.ordinal()])
                .append(" AND ")
                .append(columns[ERating.id_product_fk.ordinal()])
                .append("=")
                .append(params[ERating.id_product_fk.ordinal()]);
        return sb.toString();
    }

    @Override
    protected String getSelectQuery(List<Criterion> criterionList) {
        StringBuilder sb = new StringBuilder("SELECT * FROM game_shop.rating");
        if (criterionList.size() == 0) {
            return sb.toString();
        }
        sb.append(" WHERE ");
        return useCriterion(sb, criterionList);
    }

    @Override
    protected List<Rating> getEntities(List<Object[]> resultList) {
        UserService us = new UserService();
        ProductService ps = new ProductService();
        CriterionService cProduct = new CriterionService();
        CriterionService cUser = new CriterionService();

        List<Rating> productList = new ArrayList<>();
        for (Object[] o : resultList) {
            cUser.addCriterion(EUser.id_user, o[ERating.id_user_fk.ordinal()]);
            cProduct.addCriterion(EProduct.id_product, o[ERating.id_product_fk.ordinal()]);
            productList.add(
                    Rating.builder()
                            .client((AuthorizedUser) us.select(cUser.getCriterionList()).get(0))
                            .product((Product) ps.select(cProduct.getCriterionList()).get(0))
                            .review((String) o[ERating.review.ordinal()])
                            .reviewDate((Date) o[ERating.review_date.ordinal()])
                            .build()
            );
        }
        return productList;
    }

    public List<Rating> getProductReview(String idProduct) {
        cs.clear();
        cs.addCriterion(ERating.id_product_fk, idProduct);
        @SuppressWarnings("unchecked")
        List<Rating> ratings = (List<Rating>) select(cs.getCriterionList());
        return ratings;
    }

    public boolean isThere(AuthorizedUser user, Product product) {
        cs.clear();
        cs.addCriterion(ERating.id_user_fk, user.getId());
        cs.addCriterion(ERating.id_product_fk, product.getId());
        return select(cs.getCriterionList()).size() > 0;
    }

    public void update(AuthorizedUser user, Product product, String review) {
        if (isThere(user, product)) {
            cs.clear();
            cs.addCriterion(ERating.id_user_fk, user.getId());
            cs.addCriterion(ERating.id_product_fk, product.getId());
            Rating rating = (Rating) select(cs.getCriterionList()).get(0);
            rating.setReview(review);
            update(rating);
        }
    }
}
