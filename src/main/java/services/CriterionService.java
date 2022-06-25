package services;

import intarfaces.EntityEnum;
import lombok.Getter;
import models.Criterion;
import models.enums.Operator;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CriterionService {
    private final List<Criterion> criterionList = new ArrayList<>();
    public void addCriterion(EntityEnum entityEnum, Object obj) {
        criterionList.add(new Criterion(entityEnum, obj));
    }

    public void addCriterion(EntityEnum entityEnum, Operator operator, Object obj) {
        criterionList.add(new Criterion(entityEnum, operator, obj));
    }

    public void clear(){
        criterionList.clear();
    }
}
