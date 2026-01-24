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
package org.activiti.engine.impl.persistence.entity.data.impl.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ExecutionTreeStringBuilderDiffblueTest {
  /**
   * Test {@link ExecutionTreeStringBuilder#toString()}.
   *
   * <ul>
   *   <li>Then return {@code null : null, parent id null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeStringBuilder#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTreeStringBuilder.toString()"})
  public void testToString_thenReturnNullNullParentIdNull() {
    // Arrange, Act and Assert
    assertEquals(
        "null : null, parent id null\r\n",
        new ExecutionTreeStringBuilder(ExecutionEntityImpl.createWithEmptyRelationshipCollections())
            .toString());
  }

  /**
   * Test {@link ExecutionTreeStringBuilder#internalToString(ExecutionEntity, StringBuilder, String,
   * boolean)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>When {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#isScope()} return {@code
   *       false}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeStringBuilder#internalToString(ExecutionEntity,
   * StringBuilder, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionTreeStringBuilder.internalToString(ExecutionEntity, StringBuilder, String, boolean)"
  })
  public void testInternalToString_givenFalse_whenExecutionEntityImplIsScopeReturnFalse() {
    // Arrange
    ExecutionTreeStringBuilder executionTreeStringBuilder =
        new ExecutionTreeStringBuilder(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.isMultiInstanceRoot()).thenReturn(true);
    when(execution.isScope()).thenReturn(false);
    when(execution.getId()).thenReturn("42");
    when(execution.getActivityId()).thenReturn("42");
    when(execution.getParentId()).thenReturn("42");
    StringBuilder strb = new StringBuilder("Str");

    // Act
    executionTreeStringBuilder.internalToString(execution, strb, "Prefix", true);

    // Assert
    verify(execution).getId();
    verify(execution).getActivityId();
    verify(execution).getParentId();
    verify(execution).isMultiInstanceRoot();
    verify(execution).isScope();
    assertEquals(
        "StrPrefix└── 42 : activityId=42, parent id 42 (multi instance root)\r\n", strb.toString());
  }

  /**
   * Test {@link ExecutionTreeStringBuilder#internalToString(ExecutionEntity, StringBuilder, String,
   * boolean)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>When {@code false}.
   *   <li>Then calls {@link ExecutionEntityImpl#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeStringBuilder#internalToString(ExecutionEntity,
   * StringBuilder, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionTreeStringBuilder.internalToString(ExecutionEntity, StringBuilder, String, boolean)"
  })
  public void testInternalToString_givenTrue_whenFalse_thenCallsGetId() {
    // Arrange
    ExecutionTreeStringBuilder executionTreeStringBuilder =
        new ExecutionTreeStringBuilder(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.isMultiInstanceRoot()).thenReturn(true);
    when(execution.isScope()).thenReturn(true);
    when(execution.getId()).thenReturn("42");
    when(execution.getActivityId()).thenReturn("42");
    when(execution.getParentId()).thenReturn("42");
    StringBuilder strb = new StringBuilder("Str");

    // Act
    executionTreeStringBuilder.internalToString(execution, strb, "Prefix", false);

    // Assert
    verify(execution).getId();
    verify(execution).getActivityId();
    verify(execution).getParentId();
    verify(execution).isMultiInstanceRoot();
    verify(execution).isScope();
    assertEquals(
        "StrPrefix├── 42 : activityId=42, parent id 42 (scope) (multi instance root)\r\n",
        strb.toString());
  }

  /**
   * Test {@link ExecutionTreeStringBuilder#internalToString(ExecutionEntity, StringBuilder, String,
   * boolean)}.
   *
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeStringBuilder#internalToString(ExecutionEntity,
   * StringBuilder, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionTreeStringBuilder.internalToString(ExecutionEntity, StringBuilder, String, boolean)"
  })
  public void testInternalToString_whenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionTreeStringBuilder executionTreeStringBuilder =
        new ExecutionTreeStringBuilder(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    StringBuilder strb = new StringBuilder("Str");

    // Act
    executionTreeStringBuilder.internalToString(execution, strb, "Prefix", true);

    // Assert
    assertEquals(
        "StrPrefix└── null : activityId=null, parent id null (scope)\r\n", strb.toString());
  }

  /**
   * Test {@link ExecutionTreeStringBuilder#internalToString(ExecutionEntity, StringBuilder, String,
   * boolean)}.
   *
   * <ul>
   *   <li>When {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#isScope()} return {@code
   *       true}.
   *   <li>Then calls {@link ExecutionEntityImpl#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeStringBuilder#internalToString(ExecutionEntity,
   * StringBuilder, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionTreeStringBuilder.internalToString(ExecutionEntity, StringBuilder, String, boolean)"
  })
  public void testInternalToString_whenExecutionEntityImplIsScopeReturnTrue_thenCallsGetId() {
    // Arrange
    ExecutionTreeStringBuilder executionTreeStringBuilder =
        new ExecutionTreeStringBuilder(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.isMultiInstanceRoot()).thenReturn(true);
    when(execution.isScope()).thenReturn(true);
    when(execution.getId()).thenReturn("42");
    when(execution.getActivityId()).thenReturn("42");
    when(execution.getParentId()).thenReturn("42");
    StringBuilder strb = new StringBuilder("Str");

    // Act
    executionTreeStringBuilder.internalToString(execution, strb, "Prefix", true);

    // Assert
    verify(execution).getId();
    verify(execution).getActivityId();
    verify(execution).getParentId();
    verify(execution).isMultiInstanceRoot();
    verify(execution).isScope();
    assertEquals(
        "StrPrefix└── 42 : activityId=42, parent id 42 (scope) (multi instance root)\r\n",
        strb.toString());
  }
}
