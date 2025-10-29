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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContext;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.el.ExpressionManager;
import org.junit.Test;

public class AbstractBpmnActivityBehaviorDiffblueTest {
  /**
   * Method under test:
   * {@link AbstractBpmnActivityBehavior#hasLoopCharacteristics()}
   */
  @Test
  public void testHasLoopCharacteristics() {
    // Arrange, Act and Assert
    assertFalse((new AbstractBpmnActivityBehavior()).hasLoopCharacteristics());
  }

  /**
   * Method under test:
   * {@link AbstractBpmnActivityBehavior#hasLoopCharacteristics()}
   */
  @Test
  public void testHasLoopCharacteristics2() {
    // Arrange
    AbstractBpmnActivityBehavior abstractBpmnActivityBehavior = new AbstractBpmnActivityBehavior();
    AdhocSubProcess activity = new AdhocSubProcess();
    abstractBpmnActivityBehavior.setMultiInstanceActivityBehavior(
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior()));

    // Act and Assert
    assertTrue(abstractBpmnActivityBehavior.hasLoopCharacteristics());
  }

  /**
   * Method under test:
   * {@link AbstractBpmnActivityBehavior#hasLoopCharacteristics()}
   */
  @Test
  public void testHasLoopCharacteristics3() {
    // Arrange
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act and Assert
    assertFalse((new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition,
        new DefaultMessageExecutionContext(messageEventDefinition2, new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class))))
        .hasLoopCharacteristics());
  }

  /**
   * Method under test:
   * {@link AbstractBpmnActivityBehavior#hasMultiInstanceCharacteristics()}
   */
  @Test
  public void testHasMultiInstanceCharacteristics() {
    // Arrange, Act and Assert
    assertFalse((new AbstractBpmnActivityBehavior()).hasMultiInstanceCharacteristics());
  }

  /**
   * Method under test:
   * {@link AbstractBpmnActivityBehavior#hasMultiInstanceCharacteristics()}
   */
  @Test
  public void testHasMultiInstanceCharacteristics2() {
    // Arrange
    AbstractBpmnActivityBehavior abstractBpmnActivityBehavior = new AbstractBpmnActivityBehavior();
    AdhocSubProcess activity = new AdhocSubProcess();
    abstractBpmnActivityBehavior.setMultiInstanceActivityBehavior(
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior()));

    // Act and Assert
    assertTrue(abstractBpmnActivityBehavior.hasMultiInstanceCharacteristics());
  }

  /**
   * Method under test:
   * {@link AbstractBpmnActivityBehavior#hasMultiInstanceCharacteristics()}
   */
  @Test
  public void testHasMultiInstanceCharacteristics3() {
    // Arrange
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act and Assert
    assertFalse((new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition,
        new DefaultMessageExecutionContext(messageEventDefinition2, new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class))))
        .hasMultiInstanceCharacteristics());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link AbstractBpmnActivityBehavior#setMultiInstanceActivityBehavior(MultiInstanceActivityBehavior)}
   *   <li>{@link AbstractBpmnActivityBehavior#getMultiInstanceActivityBehavior()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    AbstractBpmnActivityBehavior abstractBpmnActivityBehavior = new AbstractBpmnActivityBehavior();
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior multiInstanceActivityBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());

    // Act
    abstractBpmnActivityBehavior.setMultiInstanceActivityBehavior(multiInstanceActivityBehavior);

    // Assert that nothing has changed
    assertSame(multiInstanceActivityBehavior, abstractBpmnActivityBehavior.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link AbstractBpmnActivityBehavior}
   */
  @Test
  public void testNewAbstractBpmnActivityBehavior() {
    // Arrange and Act
    AbstractBpmnActivityBehavior actualAbstractBpmnActivityBehavior = new AbstractBpmnActivityBehavior();

    // Assert
    assertNull(actualAbstractBpmnActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualAbstractBpmnActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualAbstractBpmnActivityBehavior.hasMultiInstanceCharacteristics());
  }
}
