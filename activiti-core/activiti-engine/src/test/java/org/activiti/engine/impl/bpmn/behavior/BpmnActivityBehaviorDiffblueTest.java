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
package org.activiti.engine.impl.bpmn.behavior;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class BpmnActivityBehaviorDiffblueTest {
  /**
   * Test {@link BpmnActivityBehavior#dispatchJobCanceledEvents(ExecutionEntity)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link ExecutionEntityImpl#getJobs()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnActivityBehavior#dispatchJobCanceledEvents(ExecutionEntity)}
   */
  @Test
  public void testDispatchJobCanceledEvents_givenArrayList_thenCallsGetJobs() {
    // Arrange
    BpmnActivityBehavior bpmnActivityBehavior = new BpmnActivityBehavior();
    ExecutionEntityImpl activityExecution = mock(ExecutionEntityImpl.class);
    when(activityExecution.getJobs()).thenReturn(new ArrayList<>());
    when(activityExecution.getTimerJobs()).thenReturn(new ArrayList<>());

    // Act
    bpmnActivityBehavior.dispatchJobCanceledEvents(activityExecution);

    // Assert that nothing has changed
    verify(activityExecution).getJobs();
    verify(activityExecution).getTimerJobs();
  }
}
