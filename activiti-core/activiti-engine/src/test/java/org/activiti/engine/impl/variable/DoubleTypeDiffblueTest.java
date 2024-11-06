/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.engine.impl.variable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class DoubleTypeDiffblueTest {
  /**
   * Test {@link DoubleType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>Then return doubleValue is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link DoubleType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_givenTen_thenReturnDoubleValueIsTen() {
    // Arrange
    DoubleType doubleType = new DoubleType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getDoubleValue()).thenReturn(10.0d);

    // Act
    Object actualValue = doubleType.getValue(valueFields);

    // Assert
    verify(valueFields).getDoubleValue();
    assertEquals(10.0d, ((Double) actualValue).doubleValue(), 0.0);
  }

  /**
   * Test {@link DoubleType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DoubleType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    DoubleType doubleType = new DoubleType();

    // Act and Assert
    assertNull(doubleType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link DoubleType#setValue(Object, ValueFields)}.
   * <p>
   * Method under test: {@link DoubleType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue() {
    // Arrange
    DoubleType doubleType = new DoubleType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    doubleType.setValue(10.0d, valueFields);

    // Assert
    assertEquals(10.0d, valueFields.getDoubleValue().doubleValue(), 0.0);
  }

  /**
   * Test {@link DoubleType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>When {@link ValueFields} {@link ValueFields#setDoubleValue(Double)} does
   * nothing.</li>
   *   <li>Then calls {@link ValueFields#setDoubleValue(Double)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DoubleType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_whenValueFieldsSetDoubleValueDoesNothing_thenCallsSetDoubleValue() {
    // Arrange
    DoubleType doubleType = new DoubleType();
    ValueFields valueFields = mock(ValueFields.class);
    doNothing().when(valueFields).setDoubleValue(Mockito.<Double>any());

    // Act
    doubleType.setValue(10.0d, valueFields);

    // Assert
    verify(valueFields).setDoubleValue(eq(10.0d));
  }

  /**
   * Test {@link DoubleType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DoubleType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new DoubleType()).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link DoubleType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DoubleType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new DoubleType()).isAbleToStore(null));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DoubleType}
   *   <li>{@link DoubleType#getTypeName()}
   *   <li>{@link DoubleType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DoubleType actualDoubleType = new DoubleType();
    String actualTypeName = actualDoubleType.getTypeName();

    // Assert
    assertEquals("double", actualTypeName);
    assertTrue(actualDoubleType.isCachable());
  }
}
