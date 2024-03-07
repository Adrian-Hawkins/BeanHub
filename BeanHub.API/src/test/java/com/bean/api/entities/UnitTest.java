package com.bean.api.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UnitTest {

    @InjectMocks
    private Unit unit;

    @BeforeEach
    public void setUp() {
        unit = new Unit();
    }

    @Test
    public void testUnitId() {
        unit.setUnitId(1);
        assertEquals(1, unit.getUnitId());
    }

    @Test
    public void testUnitName() {
        unit.setUnitName("test_unit");
        assertEquals("test_unit", unit.getUnitName());
    }
}
