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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.activiti.bpmn.model.UserTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserTaskActivityBehaviorDiffblueTest {
  @InjectMocks
  private UserTaskActivityBehavior userTaskActivityBehavior;

  /**
   * Test {@link UserTaskActivityBehavior#UserTaskActivityBehavior(UserTask)}.
   * <p>
   * Method under test:
   * {@link UserTaskActivityBehavior#UserTaskActivityBehavior(UserTask)}
   */
  @Test
  public void testNewUserTaskActivityBehavior() {
    // Arrange and Act
    UserTaskActivityBehavior actualUserTaskActivityBehavior = new UserTaskActivityBehavior(new UserTask());

    // Assert
    UserTask userTask = actualUserTaskActivityBehavior.userTask;
    assertNull(userTask.getBehavior());
    assertNull(userTask.getDefaultFlow());
    assertNull(userTask.getFailedJobRetryTimeCycleValue());
    assertNull(userTask.getId());
    assertNull(userTask.getDocumentation());
    assertNull(userTask.getName());
    assertNull(userTask.getAssignee());
    assertNull(userTask.getBusinessCalendarName());
    assertNull(userTask.getCategory());
    assertNull(userTask.getDueDate());
    assertNull(userTask.getExtensionId());
    assertNull(userTask.getFormKey());
    assertNull(userTask.getOwner());
    assertNull(userTask.getPriority());
    assertNull(userTask.getSkipExpression());
    assertNull(userTask.getParentContainer());
    assertNull(userTask.getIoSpecification());
    assertNull(userTask.getLoopCharacteristics());
    assertNull(userTask.getSubProcess());
    assertNull(actualUserTaskActivityBehavior.getMultiInstanceActivityBehavior());
    assertEquals(0, userTask.getXmlColumnNumber());
    assertEquals(0, userTask.getXmlRowNumber());
    assertFalse(userTask.hasMultiInstanceLoopCharacteristics());
    assertFalse(userTask.isForCompensation());
    assertFalse(userTask.isAsynchronous());
    assertFalse(userTask.isNotExclusive());
    assertFalse(userTask.isExtended());
    assertFalse(actualUserTaskActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualUserTaskActivityBehavior.hasMultiInstanceCharacteristics());
    assertTrue(userTask.getBoundaryEvents().isEmpty());
    assertTrue(userTask.getDataInputAssociations().isEmpty());
    assertTrue(userTask.getDataOutputAssociations().isEmpty());
    assertTrue(userTask.getMapExceptions().isEmpty());
    assertTrue(userTask.getExecutionListeners().isEmpty());
    assertTrue(userTask.getIncomingFlows().isEmpty());
    assertTrue(userTask.getOutgoingFlows().isEmpty());
    assertTrue(userTask.getCandidateGroups().isEmpty());
    assertTrue(userTask.getCandidateUsers().isEmpty());
    assertTrue(userTask.getCustomProperties().isEmpty());
    assertTrue(userTask.getFormProperties().isEmpty());
    assertTrue(userTask.getTaskListeners().isEmpty());
    assertTrue(userTask.getAttributes().isEmpty());
    assertTrue(userTask.getExtensionElements().isEmpty());
    assertTrue(userTask.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(userTask.getCustomUserIdentityLinks().isEmpty());
    assertTrue(userTask.isExclusive());
  }

  /**
   * Test
   * {@link UserTaskActivityBehavior#UserTaskActivityBehavior(UserTask, VariablesPropagator)}.
   * <p>
   * Method under test:
   * {@link UserTaskActivityBehavior#UserTaskActivityBehavior(UserTask, VariablesPropagator)}
   */
  @Test
  public void testNewUserTaskActivityBehavior2() {
    // Arrange
    UserTask userTask = new UserTask();

    // Act
    UserTaskActivityBehavior actualUserTaskActivityBehavior = new UserTaskActivityBehavior(userTask,
        new VariablesPropagator(new CopyVariablesCalculator()));

    // Assert
    UserTask userTask2 = actualUserTaskActivityBehavior.userTask;
    assertNull(userTask2.getBehavior());
    assertNull(userTask2.getDefaultFlow());
    assertNull(userTask2.getFailedJobRetryTimeCycleValue());
    assertNull(userTask2.getId());
    assertNull(userTask2.getDocumentation());
    assertNull(userTask2.getName());
    assertNull(userTask2.getAssignee());
    assertNull(userTask2.getBusinessCalendarName());
    assertNull(userTask2.getCategory());
    assertNull(userTask2.getDueDate());
    assertNull(userTask2.getExtensionId());
    assertNull(userTask2.getFormKey());
    assertNull(userTask2.getOwner());
    assertNull(userTask2.getPriority());
    assertNull(userTask2.getSkipExpression());
    assertNull(userTask2.getParentContainer());
    assertNull(userTask2.getIoSpecification());
    assertNull(userTask2.getLoopCharacteristics());
    assertNull(userTask2.getSubProcess());
    assertNull(actualUserTaskActivityBehavior.getMultiInstanceActivityBehavior());
    assertEquals(0, userTask2.getXmlColumnNumber());
    assertEquals(0, userTask2.getXmlRowNumber());
    assertFalse(userTask2.hasMultiInstanceLoopCharacteristics());
    assertFalse(userTask2.isForCompensation());
    assertFalse(userTask2.isAsynchronous());
    assertFalse(userTask2.isNotExclusive());
    assertFalse(userTask2.isExtended());
    assertFalse(actualUserTaskActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualUserTaskActivityBehavior.hasMultiInstanceCharacteristics());
    assertTrue(userTask2.getBoundaryEvents().isEmpty());
    assertTrue(userTask2.getDataInputAssociations().isEmpty());
    assertTrue(userTask2.getDataOutputAssociations().isEmpty());
    assertTrue(userTask2.getMapExceptions().isEmpty());
    assertTrue(userTask2.getExecutionListeners().isEmpty());
    assertTrue(userTask2.getIncomingFlows().isEmpty());
    assertTrue(userTask2.getOutgoingFlows().isEmpty());
    assertTrue(userTask2.getCandidateGroups().isEmpty());
    assertTrue(userTask2.getCandidateUsers().isEmpty());
    assertTrue(userTask2.getCustomProperties().isEmpty());
    assertTrue(userTask2.getFormProperties().isEmpty());
    assertTrue(userTask2.getTaskListeners().isEmpty());
    assertTrue(userTask2.getAttributes().isEmpty());
    assertTrue(userTask2.getExtensionElements().isEmpty());
    assertTrue(userTask2.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(userTask2.getCustomUserIdentityLinks().isEmpty());
    assertTrue(userTask2.isExclusive());
  }

  /**
   * Test {@link UserTaskActivityBehavior#extractCandidates(String)}.
   * <p>
   * Method under test: {@link UserTaskActivityBehavior#extractCandidates(String)}
   */
  @Test
  public void testExtractCandidates() {
    // Arrange and Act
    List<String> actualExtractCandidatesResult = userTaskActivityBehavior.extractCandidates("Str");

    // Assert
    assertEquals(1, actualExtractCandidatesResult.size());
    assertEquals("Str", actualExtractCandidatesResult.get(0));
  }
}
