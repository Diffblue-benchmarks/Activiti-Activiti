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

public class IntegerTypeDiffblueTest {
  /**
   * Test {@link IntegerType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegerType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    IntegerType integerType = new IntegerType();

    // Act and Assert
    assertNull(integerType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link IntegerType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link ValueFields} {@link ValueFields#getLongValue()} return
   * forty-two.</li>
   *   <li>Then calls {@link ValueFields#getLongValue()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegerType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenValueFieldsGetLongValueReturnFortyTwo_thenCallsGetLongValue() {
    // Arrange
    IntegerType integerType = new IntegerType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(42L);

    // Act
    integerType.getValue(valueFields);

    // Assert
    verify(valueFields, atLeast(1)).getLongValue();
  }

  /**
   * Test {@link IntegerType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor) LongValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegerType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplLongValueIsNull() {
    // Arrange
    IntegerType integerType = new IntegerType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    integerType.setValue(null, valueFields);

    // Assert
    assertNull(valueFields.getLongValue());
    assertNull(valueFields.getTextValue());
  }

  /**
   * Test {@link IntegerType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor) TextValue is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegerType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplTextValueIs42() {
    // Arrange
    IntegerType integerType = new IntegerType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    integerType.setValue(42, valueFields);

    // Assert
    assertEquals("42", valueFields.getTextValue());
    assertEquals(42L, valueFields.getLongValue().longValue());
  }

  /**
   * Test {@link IntegerType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegerType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenFortyTwo_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new IntegerType()).isAbleToStore(42));
  }

  /**
   * Test {@link IntegerType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegerType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new IntegerType()).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link IntegerType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegerType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new IntegerType()).isAbleToStore(null));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link IntegerType}
   *   <li>{@link IntegerType#getTypeName()}
   *   <li>{@link IntegerType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    IntegerType actualIntegerType = new IntegerType();
    String actualTypeName = actualIntegerType.getTypeName();

    // Assert
    assertEquals("integer", actualTypeName);
    assertTrue(actualIntegerType.isCachable());
  }
}
