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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
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

public class SequentialMultiInstanceBehaviorDiffblueTest {
  /**
   * Test
   * {@link SequentialMultiInstanceBehavior#SequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}.
   * <p>
   * Method under test:
   * {@link SequentialMultiInstanceBehavior#SequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testNewSequentialMultiInstanceBehavior() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    AbstractBpmnActivityBehavior innerActivityBehavior = new AbstractBpmnActivityBehavior();

    // Act
    SequentialMultiInstanceBehavior actualSequentialMultiInstanceBehavior = new SequentialMultiInstanceBehavior(
        activity, innerActivityBehavior);

    // Assert
    assertTrue(actualSequentialMultiInstanceBehavior.activity instanceof AdhocSubProcess);
    assertSame(innerActivityBehavior, actualSequentialMultiInstanceBehavior.getInnerActivityBehavior());
  }

  /**
   * Test
   * {@link SequentialMultiInstanceBehavior#SequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}.
   * <p>
   * Method under test:
   * {@link SequentialMultiInstanceBehavior#SequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testNewSequentialMultiInstanceBehavior2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    EventSubProcessMessageStartEventActivityBehavior innerActivityBehavior = new EventSubProcessMessageStartEventActivityBehavior(
        messageEventDefinition, new DefaultMessageExecutionContext(messageEventDefinition2, new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class)));

    // Act
    SequentialMultiInstanceBehavior actualSequentialMultiInstanceBehavior = new SequentialMultiInstanceBehavior(
        activity, innerActivityBehavior);

    // Assert
    assertTrue(actualSequentialMultiInstanceBehavior.activity instanceof AdhocSubProcess);
    assertSame(innerActivityBehavior, actualSequentialMultiInstanceBehavior.getInnerActivityBehavior());
  }

  /**
   * Test
   * {@link SequentialMultiInstanceBehavior#createInstances(DelegateExecution)}.
   * <p>
   * Method under test:
   * {@link SequentialMultiInstanceBehavior#createInstances(DelegateExecution)}
   */
  @Test
  public void testCreateInstances() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    SequentialMultiInstanceBehavior sequentialMultiInstanceBehavior = new SequentialMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    sequentialMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(0));

    // Act and Assert
    assertEquals(0,
        sequentialMultiInstanceBehavior.createInstances(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test
   * {@link SequentialMultiInstanceBehavior#createInstances(DelegateExecution)}.
   * <p>
   * Method under test:
   * {@link SequentialMultiInstanceBehavior#createInstances(DelegateExecution)}
   */
  @Test
  public void testCreateInstances2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    SequentialMultiInstanceBehavior sequentialMultiInstanceBehavior = new SequentialMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    sequentialMultiInstanceBehavior.setCollectionExpression(new FixedValue(new ArrayList<>()));

    // Act and Assert
    assertEquals(0,
        sequentialMultiInstanceBehavior.createInstances(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test
   * {@link SequentialMultiInstanceBehavior#createInstances(DelegateExecution)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequentialMultiInstanceBehavior#createInstances(DelegateExecution)}
   */
  @Test
  public void testCreateInstances_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    SequentialMultiInstanceBehavior sequentialMultiInstanceBehavior = new SequentialMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    sequentialMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(-1));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> sequentialMultiInstanceBehavior
        .createInstances(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }
}
