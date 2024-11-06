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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class ShortTypeDiffblueTest {
  /**
   * Test {@link ShortType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ShortType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    ShortType shortType = new ShortType();

    // Act and Assert
    assertNull(shortType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link ShortType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link ValueFields} {@link ValueFields#getLongValue()} return
   * forty-two.</li>
   *   <li>Then calls {@link ValueFields#getLongValue()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ShortType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenValueFieldsGetLongValueReturnFortyTwo_thenCallsGetLongValue() {
    // Arrange
    ShortType shortType = new ShortType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(42L);

    // Act
    shortType.getValue(valueFields);

    // Assert
    verify(valueFields, atLeast(1)).getLongValue();
  }

  /**
   * Test {@link ShortType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor) LongValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ShortType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplLongValueIsNull() {
    // Arrange
    ShortType shortType = new ShortType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    shortType.setValue(null, valueFields);

    // Assert
    assertNull(valueFields.getLongValue());
    assertNull(valueFields.getTextValue());
  }

  /**
   * Test {@link ShortType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor) TextValue is {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ShortType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplTextValueIs1() {
    // Arrange
    ShortType shortType = new ShortType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    shortType.setValue((short) 1, valueFields);

    // Assert
    assertEquals("1", valueFields.getTextValue());
    assertEquals(1L, valueFields.getLongValue().longValue());
  }

  /**
   * Test {@link ShortType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ShortType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new ShortType()).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link ShortType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ShortType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new ShortType()).isAbleToStore(null));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ShortType}
   *   <li>{@link ShortType#getTypeName()}
   *   <li>{@link ShortType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ShortType actualShortType = new ShortType();
    String actualTypeName = actualShortType.getTypeName();

    // Assert
    assertEquals("short", actualTypeName);
    assertTrue(actualShortType.isCachable());
  }
}
