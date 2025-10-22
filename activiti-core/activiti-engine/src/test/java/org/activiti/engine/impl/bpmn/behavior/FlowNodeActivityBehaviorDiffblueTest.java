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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class FlowNodeActivityBehaviorDiffblueTest {
  /**
   * Test {@link FlowNodeActivityBehavior#trigger(DelegateExecution, String, Object)}.
   * <ul>
   *   <li>Given {@link AbstractBpmnActivityBehavior} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNodeActivityBehavior#trigger(DelegateExecution, String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void FlowNodeActivityBehavior.trigger(DelegateExecution, String, Object)"})
  public void testTrigger_givenAbstractBpmnActivityBehavior_thenThrowActivitiException() {
    // Arrange
    AbstractBpmnActivityBehavior abstractBpmnActivityBehavior = new AbstractBpmnActivityBehavior();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> abstractBpmnActivityBehavior
        .trigger(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Signal Name", JSONObject.NULL));
  }

  /**
   * Test {@link FlowNodeActivityBehavior#parseActivityType(FlowNode)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code adhocSubProcess}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowNodeActivityBehavior#parseActivityType(FlowNode)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String FlowNodeActivityBehavior.parseActivityType(FlowNode)"})
  public void testParseActivityType_whenAdhocSubProcess_thenReturnAdhocSubProcess() {
    // Arrange
    AbstractBpmnActivityBehavior abstractBpmnActivityBehavior = new AbstractBpmnActivityBehavior();

    // Act and Assert
    assertEquals("adhocSubProcess", abstractBpmnActivityBehavior.parseActivityType(new AdhocSubProcess()));
  }
}
