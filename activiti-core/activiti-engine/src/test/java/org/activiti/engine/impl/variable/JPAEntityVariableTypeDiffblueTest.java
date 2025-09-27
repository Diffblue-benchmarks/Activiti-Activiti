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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JPAEntityVariableTypeDiffblueTest {
  /**
   * Test new {@link JPAEntityVariableType} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link JPAEntityVariableType}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JPAEntityVariableType.<init>()"})
  public void testNewJPAEntityVariableType() {
    // Arrange and Act
    JPAEntityVariableType actualJpaEntityVariableType = new JPAEntityVariableType();

    // Assert
    assertFalse(actualJpaEntityVariableType.isCachable());
    assertEquals(JPAEntityVariableType.TYPE_NAME, actualJpaEntityVariableType.getTypeName());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link JPAEntityVariableType#setForceCacheable(boolean)}
   *   <li>{@link JPAEntityVariableType#getTypeName()}
   *   <li>{@link JPAEntityVariableType#isCachable()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String JPAEntityVariableType.getTypeName()",
    "boolean JPAEntityVariableType.isCachable()",
    "void JPAEntityVariableType.setForceCacheable(boolean)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JPAEntityVariableType jpaEntityVariableType = new JPAEntityVariableType();

    // Act
    jpaEntityVariableType.setForceCacheable(true);
    String actualTypeName = jpaEntityVariableType.getTypeName();

    // Assert
    assertTrue(jpaEntityVariableType.isCachable());
    assertEquals(JPAEntityVariableType.TYPE_NAME, actualTypeName);
  }

  /**
   * Test {@link JPAEntityVariableType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>Given {@link JPAEntityVariableType} (default constructor) ForceCacheable is {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityVariableType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JPAEntityVariableType.isAbleToStore(Object)"})
  public void testIsAbleToStore_givenJPAEntityVariableTypeForceCacheableIsTrue_thenReturnTrue() {
    // Arrange
    JPAEntityVariableType jpaEntityVariableType = new JPAEntityVariableType();
    jpaEntityVariableType.setForceCacheable(true);

    // Act and Assert
    assertTrue(jpaEntityVariableType.isAbleToStore(null));
  }

  /**
   * Test {@link JPAEntityVariableType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>Given {@link JPAEntityVariableType} (default constructor).
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityVariableType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JPAEntityVariableType.isAbleToStore(Object)"})
  public void testIsAbleToStore_givenJPAEntityVariableType_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new JPAEntityVariableType().isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link JPAEntityVariableType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityVariableType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JPAEntityVariableType.getValue(ValueFields)"})
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
   *
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JPAEntityVariableType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JPAEntityVariableType.getValue(ValueFields)"})
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    JPAEntityVariableType jpaEntityVariableType = new JPAEntityVariableType();

    // Act and Assert
    assertNull(
        jpaEntityVariableType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }
}
