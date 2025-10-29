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
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class JPAEntityVariableTypeDiffblueTest {
  /**
   * Method under test: {@link JPAEntityVariableType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() {
    // Arrange, Act and Assert
    assertFalse((new JPAEntityVariableType()).isAbleToStore(JSONObject.NULL));
    assertTrue((new JPAEntityVariableType()).isAbleToStore(null));
    assertFalse(HistoricJPAEntityVariableType.getSharedInstance().isAbleToStore(1));
  }

  /**
   * Method under test: {@link JPAEntityVariableType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    JPAEntityVariableType jpaEntityVariableType = new JPAEntityVariableType();

    // Act and Assert
    assertNull(jpaEntityVariableType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link JPAEntityVariableType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue2() {
    // Arrange
    JPAEntityVariableType jpaEntityVariableType = new JPAEntityVariableType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getTextValue2()).thenThrow(new ActivitiException("An error occurred"));
    when(valueFields.getTextValue()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jpaEntityVariableType.getValue(valueFields));
    verify(valueFields).getTextValue();
    verify(valueFields).getTextValue2();
  }

  /**
   * Method under test: {@link JPAEntityVariableType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue3() {
    // Arrange
    JPAEntityVariableType jpaEntityVariableType = new JPAEntityVariableType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getTextValue2()).thenReturn(null);
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jpaEntityVariableType.getValue(valueFields);

    // Assert
    verify(valueFields).getTextValue();
    verify(valueFields).getTextValue2();
    assertNull(actualValue);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link JPAEntityVariableType#setForceCacheable(boolean)}
   *   <li>{@link JPAEntityVariableType#getTypeName()}
   *   <li>{@link JPAEntityVariableType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JPAEntityVariableType jpaEntityVariableType = new JPAEntityVariableType();

    // Act
    jpaEntityVariableType.setForceCacheable(true);
    String actualTypeName = jpaEntityVariableType.getTypeName();

    // Assert that nothing has changed
    assertTrue(jpaEntityVariableType.isCachable());
    assertEquals(JPAEntityVariableType.TYPE_NAME, actualTypeName);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link JPAEntityVariableType}
   */
  @Test
  public void testNewJPAEntityVariableType() {
    // Arrange and Act
    JPAEntityVariableType actualJpaEntityVariableType = new JPAEntityVariableType();

    // Assert
    assertFalse(actualJpaEntityVariableType.isCachable());
    assertEquals(JPAEntityVariableType.TYPE_NAME, actualJpaEntityVariableType.getTypeName());
  }
}
