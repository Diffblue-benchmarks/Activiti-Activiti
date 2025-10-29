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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class CustomObjectTypeDiffblueTest {
  /**
   * Method under test: {@link CustomObjectType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    Class<Object> theClass = Object.class;
    CustomObjectType customObjectType = new CustomObjectType("Type Name", theClass);

    // Act and Assert
    assertNull(customObjectType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link CustomObjectType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue2() {
    // Arrange
    Class<Object> theClass = Object.class;
    CustomObjectType customObjectType = new CustomObjectType("Type Name", theClass);
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getCachedValue()).thenReturn(JSONObject.NULL);

    // Act
    customObjectType.getValue(valueFields);

    // Assert
    verify(valueFields).getCachedValue();
  }

  /**
   * Method under test: {@link CustomObjectType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() {
    // Arrange
    Class<Object> theClass = Object.class;

    // Act and Assert
    assertTrue((new CustomObjectType("Type Name", theClass)).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Method under test: {@link CustomObjectType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore2() {
    // Arrange
    Class<Object> theClass = Object.class;

    // Act and Assert
    assertTrue((new CustomObjectType("Type Name", theClass)).isAbleToStore(null));
  }

  /**
   * Method under test: {@link CustomObjectType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue() {
    // Arrange
    Class<Object> theClass = Object.class;
    CustomObjectType customObjectType = new CustomObjectType("Type Name", theClass);
    Object object = JSONObject.NULL;
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    customObjectType.setValue(object, valueFields);

    // Assert
    assertSame(object, valueFields.getCachedValue());
  }

  /**
   * Method under test: {@link CustomObjectType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue2() {
    // Arrange
    Class<Object> theClass = Object.class;
    CustomObjectType customObjectType = new CustomObjectType("Type Name", theClass);
    ValueFields valueFields = mock(ValueFields.class);
    doNothing().when(valueFields).setCachedValue(Mockito.<Object>any());

    // Act
    customObjectType.setValue(JSONObject.NULL, valueFields);

    // Assert
    verify(valueFields).setCachedValue(isA(Object.class));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CustomObjectType#CustomObjectType(String, Class)}
   *   <li>{@link CustomObjectType#getTypeName()}
   *   <li>{@link CustomObjectType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    Class<Object> theClass = Object.class;

    // Act
    CustomObjectType actualCustomObjectType = new CustomObjectType("Type Name", theClass);
    String actualTypeName = actualCustomObjectType.getTypeName();

    // Assert
    assertEquals("Type Name", actualTypeName);
    assertTrue(actualCustomObjectType.isCachable());
  }
}
