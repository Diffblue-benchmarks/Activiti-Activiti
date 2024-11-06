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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContext;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class ParallelMultiInstanceBehaviorDiffblueTest {
  /**
   * Test
   * {@link ParallelMultiInstanceBehavior#ParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}.
   * <p>
   * Method under test:
   * {@link ParallelMultiInstanceBehavior#ParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testNewParallelMultiInstanceBehavior() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    AbstractBpmnActivityBehavior originalActivityBehavior = new AbstractBpmnActivityBehavior();

    // Act
    ParallelMultiInstanceBehavior actualParallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        originalActivityBehavior);

    // Assert
    assertTrue(actualParallelMultiInstanceBehavior.activity instanceof AdhocSubProcess);
    assertSame(originalActivityBehavior, actualParallelMultiInstanceBehavior.getInnerActivityBehavior());
  }

  /**
   * Test
   * {@link ParallelMultiInstanceBehavior#ParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}.
   * <p>
   * Method under test:
   * {@link ParallelMultiInstanceBehavior#ParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testNewParallelMultiInstanceBehavior2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    EventSubProcessMessageStartEventActivityBehavior originalActivityBehavior = new EventSubProcessMessageStartEventActivityBehavior(
        messageEventDefinition, new DefaultMessageExecutionContext(messageEventDefinition2, new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class)));

    // Act
    ParallelMultiInstanceBehavior actualParallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        originalActivityBehavior);

    // Assert
    assertTrue(actualParallelMultiInstanceBehavior.activity instanceof AdhocSubProcess);
    assertSame(originalActivityBehavior, actualParallelMultiInstanceBehavior.getInnerActivityBehavior());
  }

  /**
   * Test
   * {@link ParallelMultiInstanceBehavior#createInstances(DelegateExecution)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ParallelMultiInstanceBehavior#createInstances(DelegateExecution)}
   */
  @Test
  public void testCreateInstances_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(-1));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> parallelMultiInstanceBehavior
        .createInstances(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }
}
