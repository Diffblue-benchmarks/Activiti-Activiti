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
package org.activiti.engine.impl.bpmn.helper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class SubProcessVariableSnapshotterDiffblueTest {
  /**
   * Test
   * {@link SubProcessVariableSnapshotter#setVariablesSnapshots(ExecutionEntity, ExecutionEntity)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getParent()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubProcessVariableSnapshotter#setVariablesSnapshots(ExecutionEntity, ExecutionEntity)}
   */
  @Test
  public void testSetVariablesSnapshots_thenCallsGetParent() {
    // Arrange
    SubProcessVariableSnapshotter subProcessVariableSnapshotter = new SubProcessVariableSnapshotter();
    ExecutionEntityImpl sourceExecution = mock(ExecutionEntityImpl.class);
    when(sourceExecution.getVariablesLocal()).thenReturn(new HashMap<>());
    when(sourceExecution.getParent()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    subProcessVariableSnapshotter.setVariablesSnapshots(sourceExecution,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert that nothing has changed
    verify(sourceExecution).getParent();
    verify(sourceExecution).getVariablesLocal();
  }

  /**
   * Test
   * {@link SubProcessVariableSnapshotter#setVariablesSnapshots(ExecutionEntity, ExecutionEntity)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#isMultiInstanceRoot()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubProcessVariableSnapshotter#setVariablesSnapshots(ExecutionEntity, ExecutionEntity)}
   */
  @Test
  public void testSetVariablesSnapshots_thenCallsIsMultiInstanceRoot() {
    // Arrange
    SubProcessVariableSnapshotter subProcessVariableSnapshotter = new SubProcessVariableSnapshotter();
    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getVariablesLocal()).thenReturn(new HashMap<>());
    when(executionEntityImpl.isMultiInstanceRoot()).thenReturn(true);
    ExecutionEntityImpl sourceExecution = mock(ExecutionEntityImpl.class);
    when(sourceExecution.getVariablesLocal()).thenReturn(new HashMap<>());
    when(sourceExecution.getParent()).thenReturn(executionEntityImpl);

    // Act
    subProcessVariableSnapshotter.setVariablesSnapshots(sourceExecution,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert that nothing has changed
    verify(sourceExecution).getParent();
    verify(executionEntityImpl).isMultiInstanceRoot();
    verify(sourceExecution).getVariablesLocal();
    verify(executionEntityImpl).getVariablesLocal();
  }
}
