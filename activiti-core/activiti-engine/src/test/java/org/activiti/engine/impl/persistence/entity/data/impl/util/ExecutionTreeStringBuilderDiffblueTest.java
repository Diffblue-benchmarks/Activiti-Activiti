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
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExecutionTreeStringBuilderDiffblueTest {
  @Mock private ExecutionEntity executionEntity;

  @InjectMocks private ExecutionTreeStringBuilder executionTreeStringBuilder;

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
   * Test {@link ExecutionTreeStringBuilder#toString()}.
   *
   * <ul>
   *   <li>Then throw {@link StackOverflowError}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionTreeStringBuilder#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutionTreeStringBuilder.toString()"})
  public void testToString_thenThrowStackOverflowError() {
    // Arrange
    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setMultiInstanceRoot(true);
    executionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    ExecutionEntityImpl executionEntity2 =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity2.addChildExecution(executionEntity);

    // Act and Assert
    assertThrows(
        StackOverflowError.class,
        () -> new ExecutionTreeStringBuilder(executionEntity2).toString());
  }

  /**
   * Test {@link ExecutionTreeStringBuilder#internalToString(ExecutionEntity, StringBuilder, String,
   * boolean)}.
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
  public void testInternalToString() {
    // Arrange
    ExecutionTreeStringBuilder executionTreeStringBuilder =
        new ExecutionTreeStringBuilder(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    StringBuilder strb = new StringBuilder("foo");

    // Act
    executionTreeStringBuilder.internalToString(execution, strb, "Prefix", true);

    // Assert
    assertEquals(
        "fooPrefix└── null : activityId=null, parent id null (scope)\r\n", strb.toString());
  }

  /**
   * Test {@link ExecutionTreeStringBuilder#internalToString(ExecutionEntity, StringBuilder, String,
   * boolean)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntity} {@link ExecutionEntity#isScope()} return {@code true}.
   *   <li>Then calls {@link ExecutionEntity#getId()}.
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
  public void testInternalToString_givenExecutionEntityIsScopeReturnTrue_thenCallsGetId() {
    // Arrange
    when(executionEntity.isScope()).thenReturn(true);
    when(executionEntity.isMultiInstanceRoot()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getParentId()).thenReturn("42");
    when(executionEntity.getActivityId()).thenReturn("42");
    org.mockito.Mockito.<List<? extends ExecutionEntity>>when(executionEntity.getExecutions())
        .thenReturn(new ArrayList<>());
    StringBuilder strb = new StringBuilder("foo");

    // Act
    executionTreeStringBuilder.internalToString(executionEntity, strb, "Prefix", true);

    // Assert
    verify(executionEntity).getId();
    verify(executionEntity).getParentId();
    verify(executionEntity).isScope();
    verify(executionEntity).getExecutions();
    verify(executionEntity).isMultiInstanceRoot();
    verify(executionEntity).getActivityId();
    assertEquals(
        "fooPrefix└── 42 : activityId=42, parent id 42 (scope) (multi instance root)\r\n",
        strb.toString());
  }
}
