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
   * Test getters and setters.
   * <p>
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

  /**
   * Test {@link CustomObjectType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>Then calls {@link ValueFields#getCachedValue()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CustomObjectType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_givenNull_thenCallsGetCachedValue() {
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
   * Test {@link CustomObjectType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CustomObjectType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    Class<Object> theClass = Object.class;
    CustomObjectType customObjectType = new CustomObjectType("Type Name", theClass);

    // Act and Assert
    assertNull(customObjectType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link CustomObjectType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CustomObjectType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_givenJavaLangObject_whenNull_thenReturnTrue() {
    // Arrange
    Class<Object> theClass = Object.class;

    // Act and Assert
    assertTrue((new CustomObjectType("Type Name", theClass)).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link CustomObjectType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CustomObjectType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_givenJavaLangObject_whenNull_thenReturnTrue2() {
    // Arrange
    Class<Object> theClass = Object.class;

    // Act and Assert
    assertTrue((new CustomObjectType("Type Name", theClass)).isAbleToStore(null));
  }

  /**
   * Test {@link CustomObjectType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor) CachedValue is {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CustomObjectType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplCachedValueIsNull() {
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
   * Test {@link CustomObjectType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>When {@link ValueFields} {@link ValueFields#setCachedValue(Object)} does
   * nothing.</li>
   *   <li>Then calls {@link ValueFields#setCachedValue(Object)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CustomObjectType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_whenValueFieldsSetCachedValueDoesNothing_thenCallsSetCachedValue() {
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
}
