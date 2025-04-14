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

public class InactiveExecutionsInActivityMatcherDiffblueTest {
  /**
   * Test {@link InactiveExecutionsInActivityMatcher#isRetained(ExecutionEntity, Object)} with {@code ExecutionEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InactiveExecutionsInActivityMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InactiveExecutionsInActivityMatcher.isRetained(ExecutionEntity, Object)"})
  public void testIsRetainedWithExecutionEntityObject_given42() {
    // Arrange
    InactiveExecutionsInActivityMatcher inactiveExecutionsInActivityMatcher = new InactiveExecutionsInActivityMatcher();
    ExecutionEntityImpl entity = mock(ExecutionEntityImpl.class);
    when(entity.isActive()).thenReturn(false);
    when(entity.getActivityId()).thenReturn("42");
    doNothing().when(entity).addChildExecution(Mockito.<ExecutionEntity>any());
    entity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    boolean actualIsRetainedResult = inactiveExecutionsInActivityMatcher.isRetained(entity, new HashMap<>());

    // Assert
    verify(entity).addChildExecution(isA(ExecutionEntity.class));
    verify(entity, atLeast(1)).getActivityId();
    verify(entity).isActive();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link InactiveExecutionsInActivityMatcher#isRetained(ExecutionEntity, Object)} with {@code ExecutionEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InactiveExecutionsInActivityMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InactiveExecutionsInActivityMatcher.isRetained(ExecutionEntity, Object)"})
  public void testIsRetainedWithExecutionEntityObject_givenNull() {
    // Arrange
    InactiveExecutionsInActivityMatcher inactiveExecutionsInActivityMatcher = new InactiveExecutionsInActivityMatcher();
    ExecutionEntityImpl entity = mock(ExecutionEntityImpl.class);
    when(entity.isActive()).thenReturn(false);
    when(entity.getActivityId()).thenReturn(null);
    doNothing().when(entity).addChildExecution(Mockito.<ExecutionEntity>any());
    entity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    boolean actualIsRetainedResult = inactiveExecutionsInActivityMatcher.isRetained(entity, new HashMap<>());

    // Assert
    verify(entity).addChildExecution(isA(ExecutionEntity.class));
    verify(entity).getActivityId();
    verify(entity).isActive();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link InactiveExecutionsInActivityMatcher#isRetained(ExecutionEntity, Object)} with {@code ExecutionEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link InactiveExecutionsInActivityMatcher#isRetained(ExecutionEntity, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean InactiveExecutionsInActivityMatcher.isRetained(ExecutionEntity, Object)"})
  public void testIsRetainedWithExecutionEntityObject_givenTrue() {
    // Arrange
    InactiveExecutionsInActivityMatcher inactiveExecutionsInActivityMatcher = new InactiveExecutionsInActivityMatcher();
    ExecutionEntityImpl entity = mock(ExecutionEntityImpl.class);
    when(entity.isActive()).thenReturn(true);
    doNothing().when(entity).addChildExecution(Mockito.<ExecutionEntity>any());
    entity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    boolean actualIsRetainedResult = inactiveExecutionsInActivityMatcher.isRetained(entity, new HashMap<>());

    // Assert
    verify(entity).addChildExecution(isA(ExecutionEntity.class));
    verify(entity).isActive();
    assertFalse(actualIsRetainedResult);
  }
}
