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
   * Test new {@link JPAEntityVariableType} (default constructor).
   * <p>
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

  /**
   * Test getters and setters.
   * <p>
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
   * Test {@link JPAEntityVariableType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given {@link JPAEntityVariableType} (default constructor).</li>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityVariableType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_givenJPAEntityVariableType_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JPAEntityVariableType()).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link JPAEntityVariableType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given {@link JPAEntityVariableType} (default constructor).</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityVariableType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_givenJPAEntityVariableType_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new JPAEntityVariableType()).isAbleToStore(null));
  }

  /**
   * Test {@link JPAEntityVariableType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given SharedInstance.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityVariableType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_givenSharedInstance_whenOne_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(HistoricJPAEntityVariableType.getSharedInstance().isAbleToStore(1));
  }

  /**
   * Test {@link JPAEntityVariableType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ValueFields} {@link ValueFields#getTextValue2()} return
   * {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityVariableType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_givenNull_whenValueFieldsGetTextValue2ReturnNull_thenReturnNull() {
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
   * Test {@link JPAEntityVariableType#getValue(ValueFields)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityVariableType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_thenThrowActivitiException() {
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
   * Test {@link JPAEntityVariableType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityVariableType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    JPAEntityVariableType jpaEntityVariableType = new JPAEntityVariableType();

    // Act and Assert
    assertNull(jpaEntityVariableType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }
}
