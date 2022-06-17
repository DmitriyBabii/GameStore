package services;

import intarfaces.EntityEnum;
import lombok.Getter;
import models.Criterion;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CriterionService {
    private final List<Criterion> criterionList = new ArrayList<>();

    public void addCriterion(Criterion criterion) {
        criterionList.add(criterion);
    }

    public void addCriterion(EntityEnum entityEnum, Object obj) {
        criterionList.add(new Criterion(entityEnum, obj));
    }
}
