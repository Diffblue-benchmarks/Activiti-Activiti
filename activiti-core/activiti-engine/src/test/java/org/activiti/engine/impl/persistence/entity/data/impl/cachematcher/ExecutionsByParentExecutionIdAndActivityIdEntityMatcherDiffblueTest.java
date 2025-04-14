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
package org.activiti.engine.impl.persistence.entity.data.impl.cachematcher;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ExecutionsByParentExecutionIdAndActivityIdEntityMatcherDiffblueTest {
  /**
   * Test {@link ExecutionsByParentExecutionIdAndActivityIdEntityMatcher#isRetained(ExecutionEntity, Object)} with {@code ExecutionEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionsByParentExecutionIdAndActivityIdEntityMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "boolean ExecutionsByParentExecutionIdAndActivityIdEntityMatcher.isRetained(ExecutionEntity, Object)"})
  public void testIsRetainedWithExecutionEntityObject_given42() {
    // Arrange
    ExecutionsByParentExecutionIdAndActivityIdEntityMatcher executionsByParentExecutionIdAndActivityIdEntityMatcher = new ExecutionsByParentExecutionIdAndActivityIdEntityMatcher();
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getParentId()).thenReturn("42");
    doNothing().when(executionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    boolean actualIsRetainedResult = executionsByParentExecutionIdAndActivityIdEntityMatcher.isRetained(executionEntity,
        new HashMap<>());

    // Assert
    verify(executionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntity, atLeast(1)).getParentId();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link ExecutionsByParentExecutionIdAndActivityIdEntityMatcher#isRetained(ExecutionEntity, Object)} with {@code ExecutionEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionsByParentExecutionIdAndActivityIdEntityMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "boolean ExecutionsByParentExecutionIdAndActivityIdEntityMatcher.isRetained(ExecutionEntity, Object)"})
  public void testIsRetainedWithExecutionEntityObject_givenNull() {
    // Arrange
    ExecutionsByParentExecutionIdAndActivityIdEntityMatcher executionsByParentExecutionIdAndActivityIdEntityMatcher = new ExecutionsByParentExecutionIdAndActivityIdEntityMatcher();
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getParentId()).thenReturn(null);
    doNothing().when(executionEntity).addChildExecution(Mockito.<ExecutionEntity>any());
    executionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    boolean actualIsRetainedResult = executionsByParentExecutionIdAndActivityIdEntityMatcher.isRetained(executionEntity,
        new HashMap<>());

    // Assert
    verify(executionEntity).addChildExecution(isA(ExecutionEntity.class));
    verify(executionEntity).getParentId();
    assertFalse(actualIsRetainedResult);
  }
}
