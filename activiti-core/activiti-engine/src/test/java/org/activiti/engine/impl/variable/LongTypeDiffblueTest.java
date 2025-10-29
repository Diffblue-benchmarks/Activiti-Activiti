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
   * Method under test: {@link LongType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    LongType longType = new LongType();

    // Act and Assert
    assertNull(longType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link LongType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue2() {
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
   * Method under test: {@link LongType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue() {
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
   * Method under test: {@link LongType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue2() {
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
   * Method under test: {@link LongType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue3() {
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
   * Method under test: {@link LongType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() {
    // Arrange, Act and Assert
    assertFalse((new LongType()).isAbleToStore(JSONObject.NULL));
    assertTrue((new LongType()).isAbleToStore(null));
  }

  /**
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
