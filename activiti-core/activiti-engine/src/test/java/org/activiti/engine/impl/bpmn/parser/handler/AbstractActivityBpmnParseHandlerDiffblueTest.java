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
package org.activiti.engine.impl.bpmn.parser.handler;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.engine.impl.bpmn.behavior.AdhocSubProcessActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractActivityBpmnParseHandlerDiffblueTest {
  /**
   * Test {@link AbstractActivityBpmnParseHandler#parse(BpmnParse, BaseElement)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then {@link AdhocSubProcess} (default constructor) Behavior {@link AdhocSubProcessActivityBehavior}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractActivityBpmnParseHandler#parse(BpmnParse, BaseElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractActivityBpmnParseHandler.parse(BpmnParse, BaseElement)"})
  public void testParse_givenNull_thenAdhocSubProcessBehaviorAdhocSubProcessActivityBehavior() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    AdhocSubProcess element = new AdhocSubProcess();
    element.setLoopCharacteristics(null);

    // Act
    adhocSubProcessParseHandler.parse(bpmnParse, element);

    // Assert
    Object behavior = element.getBehavior();
    assertTrue(behavior instanceof AdhocSubProcessActivityBehavior);
    assertNull(((AdhocSubProcessActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }
}
