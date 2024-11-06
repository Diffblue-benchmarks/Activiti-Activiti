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

public class StringTypeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link StringType#StringType(int)}
   *   <li>{@link StringType#getTypeName()}
   *   <li>{@link StringType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    StringType actualStringType = new StringType(3);
    String actualTypeName = actualStringType.getTypeName();

    // Assert
    assertEquals("string", actualTypeName);
    assertTrue(actualStringType.isCachable());
  }

  /**
   * Test {@link StringType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ValueFields} {@link ValueFields#getTextValue()} return
   * {@code 42}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_given42_whenValueFieldsGetTextValueReturn42_thenReturn42() {
    // Arrange
    StringType stringType = new StringType(3);
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = stringType.getValue(valueFields);

    // Assert
    verify(valueFields).getTextValue();
    assertEquals("42", actualValue);
  }

  /**
   * Test {@link StringType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    StringType stringType = new StringType(3);

    // Act and Assert
    assertNull(stringType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link StringType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor) TextValue is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplTextValueIs42() {
    // Arrange
    StringType stringType = new StringType(3);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    stringType.setValue("42", valueFields);

    // Assert
    assertEquals("42", valueFields.getTextValue());
  }

  /**
   * Test {@link StringType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>When {@link ValueFields} {@link ValueFields#setTextValue(String)} does
   * nothing.</li>
   *   <li>Then calls {@link ValueFields#setTextValue(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_whenValueFieldsSetTextValueDoesNothing_thenCallsSetTextValue() {
    // Arrange
    StringType stringType = new StringType(3);
    ValueFields valueFields = mock(ValueFields.class);
    doNothing().when(valueFields).setTextValue(Mockito.<String>any());

    // Act
    stringType.setValue("42", valueFields);

    // Assert
    verify(valueFields).setTextValue(eq("42"));
  }

  /**
   * Test {@link StringType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_when42_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new StringType(3)).isAbleToStore("42"));
  }

  /**
   * Test {@link StringType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new StringType(3)).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link StringType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new StringType(3)).isAbleToStore(null));
  }

  /**
   * Test {@link StringType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenValue_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new StringType(3)).isAbleToStore("Value"));
  }
}
