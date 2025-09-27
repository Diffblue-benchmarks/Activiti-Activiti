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
package org.activiti.engine.impl.bpmn.parser;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.DataObject;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.handler.AdhocSubProcessParseHandler;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class BpmnParseHandlersDiffblueTest {
  /**
   * Test new {@link BpmnParseHandlers} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link BpmnParseHandlers}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParseHandlers.<init>()"})
  public void testNewBpmnParseHandlers() {
    // Arrange, Act and Assert
    assertTrue(new BpmnParseHandlers().parseHandlers.isEmpty());
  }

  /**
   * Test {@link BpmnParseHandlers#getHandlersFor(Class)}.
   *
   * <p>Method under test: {@link BpmnParseHandlers#getHandlersFor(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List BpmnParseHandlers.getHandlersFor(Class)"})
  public void testGetHandlersFor() {
    // Arrange
    BpmnParseHandlers bpmnParseHandlers = new BpmnParseHandlers();
    Class<BaseElement> clazz = BaseElement.class;

    // Act and Assert
    assertNull(bpmnParseHandlers.getHandlersFor(clazz));
  }

  /**
   * Test {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}.
   *
   * <p>Method under test: {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParseHandlers.parseElement(BpmnParse, BaseElement)"})
  public void testParseElement() {
    // Arrange
    BpmnParseHandlers bpmnParseHandlers = new BpmnParseHandlers();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    AdhocSubProcess element = new AdhocSubProcess();

    // Act
    bpmnParseHandlers.parseElement(bpmnParse, element);

    // Assert
    FlowElement currentFlowElement = bpmnParse.getCurrentFlowElement();
    assertTrue(currentFlowElement instanceof AdhocSubProcess);
    assertSame(element, currentFlowElement);
  }

  /**
   * Test {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParseHandlers.parseElement(BpmnParse, BaseElement)"})
  public void testParseElement_givenDefaultActivityBehaviorFactory() {
    // Arrange
    BpmnParseHandlers bpmnParseHandlers = new BpmnParseHandlers();
    bpmnParseHandlers.addHandler(new AdhocSubProcessParseHandler());

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    AdhocSubProcess element = new AdhocSubProcess();

    // Act
    bpmnParseHandlers.parseElement(bpmnParse, element);

    // Assert
    FlowElement currentFlowElement = bpmnParse.getCurrentFlowElement();
    assertTrue(currentFlowElement instanceof AdhocSubProcess);
    assertSame(element, currentFlowElement);
  }

  /**
   * Test {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}.
   *
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParseHandlers.parseElement(BpmnParse, BaseElement)"})
  public void testParseElement_whenActivitiListener() {
    // Arrange
    BpmnParseHandlers bpmnParseHandlers = new BpmnParseHandlers();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act
    bpmnParseHandlers.parseElement(bpmnParse, new ActivitiListener());

    // Assert that nothing has changed
    assertNull(bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}.
   *
   * <ul>
   *   <li>When {@link DataObject} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParseHandlers.parseElement(BpmnParse, BaseElement)"})
  public void testParseElement_whenDataObject() {
    // Arrange
    BpmnParseHandlers bpmnParseHandlers = new BpmnParseHandlers();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act
    bpmnParseHandlers.parseElement(bpmnParse, new DataObject());

    // Assert that nothing has changed
    assertNull(bpmnParse.getCurrentFlowElement());
  }
}
