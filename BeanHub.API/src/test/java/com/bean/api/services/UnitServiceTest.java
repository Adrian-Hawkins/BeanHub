package com.bean.api.services;

import com.bean.api.entities.Unit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Unit> query;

    @InjectMocks
    private UnitService unitService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUnit() {
        Unit unit = new Unit();
        unit.setUnitName("Kilogram");

        unitService.saveUnit(unit);

        verify(entityManager, times(1)).persist(unit);
    }

    @Test
    void updateUnit() {
        Unit unit = new Unit();
        unit.setUnitId(1);
        unit.setUnitName("Meter");

        unitService.updateUnit(unit);

        verify(entityManager, times(1)).merge(unit);
    }

    @Test
    void deleteUnit() {
        Unit unit = new Unit();
        unit.setUnitId(1);
        unit.setUnitName("Liter");

        when(entityManager.contains(unit)).thenReturn(true);

        unitService.deleteUnit(unit);

        verify(entityManager, times(1)).remove(unit);
    }

    @Test
    void getUnitById() {
        Integer unitId = 1;
        Unit unit = new Unit();
        unit.setUnitId(unitId);
        unit.setUnitName("Gram");

        when(entityManager.find(Unit.class, unitId)).thenReturn(unit);

        Unit result = unitService.getUnitById(unitId);

        assertNotNull(result);
        assertEquals(unit.getUnitId(), result.getUnitId());
        assertEquals(unit.getUnitName(), result.getUnitName());
    }

    @Test
    void getAllUnits() {
        Unit unit1 = new Unit();
        unit1.setUnitId(1);
        unit1.setUnitName("Kilogram");

        Unit unit2 = new Unit();
        unit2.setUnitId(2);
        unit2.setUnitName("Meter");

        List<Unit> units = Arrays.asList(unit1, unit2);

        when(entityManager.createQuery(anyString(), eq(Unit.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(units);

        List<Unit> result = unitService.getAllUnits();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(units));
    }
}