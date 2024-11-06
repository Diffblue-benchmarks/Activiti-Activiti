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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExecutionTreeStringBuilderDiffblueTest {
  @Mock
  private ExecutionEntity executionEntity;

  @InjectMocks
  private ExecutionTreeStringBuilder executionTreeStringBuilder;

  /**
   * Test {@link ExecutionTreeStringBuilder#toString()}.
   * <ul>
   *   <li>Then return {@code null : null, parent id null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionTreeStringBuilder#toString()}
   */
  @Test
  public void testToString_thenReturnNullNullParentIdNull() {
    // Arrange, Act and Assert
    assertEquals("null : null, parent id null\r\n",
        (new ExecutionTreeStringBuilder(ExecutionEntityImpl.createWithEmptyRelationshipCollections())).toString());
  }

  /**
   * Test
   * {@link ExecutionTreeStringBuilder#internalToString(ExecutionEntity, StringBuilder, String, boolean)}.
   * <ul>
   *   <li>Given {@link ExecutionEntity}
   * {@link ExecutionEntity#isMultiInstanceRoot()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionTreeStringBuilder#internalToString(ExecutionEntity, StringBuilder, String, boolean)}
   */
  @Test
  public void testInternalToString_givenExecutionEntityIsMultiInstanceRootReturnFalse() {
    // Arrange
    when(executionEntity.isScope()).thenReturn(true);
    when(executionEntity.isMultiInstanceRoot()).thenReturn(false);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getParentId()).thenReturn("42");
    when(executionEntity.getActivityId()).thenReturn("42");
    Mockito.<List<? extends ExecutionEntity>>when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
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
    assertEquals("fooPrefix└── 42 : activityId=42, parent id 42 (scope)\r\n", strb.toString());
  }

  /**
   * Test
   * {@link ExecutionTreeStringBuilder#internalToString(ExecutionEntity, StringBuilder, String, boolean)}.
   * <ul>
   *   <li>Given {@link ExecutionEntity} {@link DelegateExecution#isScope()} return
   * {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionTreeStringBuilder#internalToString(ExecutionEntity, StringBuilder, String, boolean)}
   */
  @Test
  public void testInternalToString_givenExecutionEntityIsScopeReturnFalse() {
    // Arrange
    when(executionEntity.isScope()).thenReturn(false);
    when(executionEntity.isMultiInstanceRoot()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getParentId()).thenReturn("42");
    when(executionEntity.getActivityId()).thenReturn("42");
    Mockito.<List<? extends ExecutionEntity>>when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
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
    assertEquals("fooPrefix└── 42 : activityId=42, parent id 42 (multi instance root)\r\n", strb.toString());
  }

  /**
   * Test
   * {@link ExecutionTreeStringBuilder#internalToString(ExecutionEntity, StringBuilder, String, boolean)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is a string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionTreeStringBuilder#internalToString(ExecutionEntity, StringBuilder, String, boolean)}
   */
  @Test
  public void testInternalToString_thenStringBuilderWithFooToStringIsAString() {
    // Arrange
    when(executionEntity.isScope()).thenReturn(true);
    when(executionEntity.isMultiInstanceRoot()).thenReturn(true);
    when(executionEntity.getId()).thenReturn("42");
    when(executionEntity.getParentId()).thenReturn("42");
    when(executionEntity.getActivityId()).thenReturn("42");
    Mockito.<List<? extends ExecutionEntity>>when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
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
    assertEquals("fooPrefix└── 42 : activityId=42, parent id 42 (scope) (multi instance root)\r\n", strb.toString());
  }
}
