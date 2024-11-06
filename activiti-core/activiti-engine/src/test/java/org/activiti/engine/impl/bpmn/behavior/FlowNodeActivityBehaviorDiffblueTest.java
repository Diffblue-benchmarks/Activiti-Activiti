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
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContext;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FlowNodeActivityBehaviorDiffblueTest {
  @InjectMocks
  private AbstractBpmnActivityBehavior abstractBpmnActivityBehavior;

  /**
   * Test
   * {@link FlowNodeActivityBehavior#trigger(DelegateExecution, String, Object)}.
   * <p>
   * Method under test:
   * {@link FlowNodeActivityBehavior#trigger(DelegateExecution, String, Object)}
   */
  @Test
  public void testTrigger() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> abstractBpmnActivityBehavior
        .trigger(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Signal Name", JSONObject.NULL));
  }

  /**
   * Test {@link FlowNodeActivityBehavior#parseActivityType(FlowNode)}.
   * <p>
   * Method under test:
   * {@link FlowNodeActivityBehavior#parseActivityType(FlowNode)}
   */
  @Test
  public void testParseActivityType() {
    // Arrange
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    EventSubProcessMessageStartEventActivityBehavior eventSubProcessMessageStartEventActivityBehavior = new EventSubProcessMessageStartEventActivityBehavior(
        messageEventDefinition, new DefaultMessageExecutionContext(messageEventDefinition2, new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class)));

    // Act and Assert
    assertEquals("adhocSubProcess",
        eventSubProcessMessageStartEventActivityBehavior.parseActivityType(new AdhocSubProcess()));
  }

  /**
   * Test {@link FlowNodeActivityBehavior#parseActivityType(FlowNode)}.
   * <ul>
   *   <li>Then return {@code adhocSubProcess}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FlowNodeActivityBehavior#parseActivityType(FlowNode)}
   */
  @Test
  public void testParseActivityType_thenReturnAdhocSubProcess() {
    // Arrange
    AbstractBpmnActivityBehavior abstractBpmnActivityBehavior = new AbstractBpmnActivityBehavior();

    // Act and Assert
    assertEquals("adhocSubProcess", abstractBpmnActivityBehavior.parseActivityType(new AdhocSubProcess()));
  }
}
