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

public class LongTypeDiffblueTest {
  /**
   * Test {@link LongType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given forty-two.</li>
   *   <li>Then calls {@link ValueFields#getLongValue()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_givenFortyTwo_thenCallsGetLongValue() {
    // Arrange
    LongType longType = new LongType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(42L);

    // Act
    longType.getValue(valueFields);

    // Assert
    verify(valueFields).getLongValue();
  }

  /**
   * Test {@link LongType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    LongType longType = new LongType();

    // Act and Assert
    assertNull(longType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link LongType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor) LongValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplLongValueIsNull() {
    // Arrange
    LongType longType = new LongType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    longType.setValue(null, valueFields);

    // Assert
    assertNull(valueFields.getLongValue());
    assertNull(valueFields.getTextValue());
  }

  /**
   * Test {@link LongType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor) TextValue is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplTextValueIs42() {
    // Arrange
    LongType longType = new LongType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    longType.setValue(42L, valueFields);

    // Assert
    assertEquals("42", valueFields.getTextValue());
    assertEquals(42L, valueFields.getLongValue().longValue());
  }

  /**
   * Test {@link LongType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>When {@link ValueFields} {@link ValueFields#setLongValue(Long)} does
   * nothing.</li>
   *   <li>Then calls {@link ValueFields#setLongValue(Long)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_whenValueFieldsSetLongValueDoesNothing_thenCallsSetLongValue() {
    // Arrange
    LongType longType = new LongType();
    ValueFields valueFields = mock(ValueFields.class);
    doNothing().when(valueFields).setLongValue(Mockito.<Long>any());
    doNothing().when(valueFields).setTextValue(Mockito.<String>any());

    // Act
    longType.setValue(42L, valueFields);

    // Assert
    verify(valueFields).setLongValue(eq(42L));
    verify(valueFields).setTextValue(eq("42"));
  }

  /**
   * Test {@link LongType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new LongType()).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link LongType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new LongType()).isAbleToStore(null));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link LongType}
   *   <li>{@link LongType#getTypeName()}
   *   <li>{@link LongType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    LongType actualLongType = new LongType();
    String actualTypeName = actualLongType.getTypeName();

    // Assert
    assertEquals("long", actualTypeName);
    assertTrue(actualLongType.isCachable());
  }
}
