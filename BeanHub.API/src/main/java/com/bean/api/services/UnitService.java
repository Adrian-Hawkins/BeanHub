package com.bean.api.services;

import com.bean.api.entities.Unit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UnitService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void saveUnit(Unit unit) {
        entityManager.persist(unit);
    }

    @Transactional
    public void updateUnit(Unit unit) {
        entityManager.merge(unit);
    }

    @Transactional
    public void deleteUnit(Unit unit) {
        entityManager.remove(entityManager.contains(unit) ? unit : entityManager.merge(unit));
    }

    @Transactional(readOnly = false)
    public Unit getUnitById(Integer unitId) {
        return entityManager.find(Unit.class, unitId);
    }

    @Transactional(readOnly = true)
    public Unit getUnitByName(String unitName) {
        String jpql = "SELECT u FROM Unit u WHERE LOWER(u.unitName) = LOWER(:unitName)";
        TypedQuery<Unit> query = entityManager.createQuery(jpql, Unit.class);
        query.setParameter("unitName", unitName);
        List<Unit> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.getFirst();
    }

    @Transactional(readOnly = true)
    public List<Unit> getAllUnits() {
        String jpql = "SELECT u FROM Unit u";
        TypedQuery<Unit> query = entityManager.createQuery(jpql, Unit.class);
        return query.getResultList();
    }
}
