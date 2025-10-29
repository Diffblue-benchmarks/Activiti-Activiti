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
   * Method under test: {@link ShortType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    ShortType shortType = new ShortType();

    // Act and Assert
    assertNull(shortType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link ShortType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue2() {
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
   * Method under test: {@link ShortType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue() {
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
   * Method under test: {@link ShortType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue2() {
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
   * Method under test: {@link ShortType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() {
    // Arrange, Act and Assert
    assertFalse((new ShortType()).isAbleToStore(JSONObject.NULL));
    assertTrue((new ShortType()).isAbleToStore(null));
  }

  /**
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
