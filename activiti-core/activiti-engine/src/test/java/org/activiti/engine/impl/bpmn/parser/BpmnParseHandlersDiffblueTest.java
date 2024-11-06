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
import static org.mockito.Mockito.mock;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.handler.AdhocSubProcessParseHandler;
import org.activiti.engine.impl.cfg.BpmnParseFactory;
import org.junit.Test;

public class BpmnParseHandlersDiffblueTest {
  /**
   * Test new {@link BpmnParseHandlers} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link BpmnParseHandlers}
   */
  @Test
  public void testNewBpmnParseHandlers() {
    // Arrange, Act and Assert
    assertTrue((new BpmnParseHandlers()).parseHandlers.isEmpty());
  }

  /**
   * Test {@link BpmnParseHandlers#getHandlersFor(Class)}.
   * <p>
   * Method under test: {@link BpmnParseHandlers#getHandlersFor(Class)}
   */
  @Test
  public void testGetHandlersFor() {
    // Arrange
    BpmnParseHandlers bpmnParseHandlers = new BpmnParseHandlers();
    Class<BaseElement> clazz = BaseElement.class;

    // Act and Assert
    assertNull(bpmnParseHandlers.getHandlersFor(clazz));
  }

  /**
   * Test {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}.
   * <p>
   * Method under test:
   * {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}
   */
  @Test
  public void testParseElement() {
    // Arrange
    BpmnParseHandlers bpmnParseHandlers = new BpmnParseHandlers();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    AdhocSubProcess element = new AdhocSubProcess();

    // Act
    bpmnParseHandlers.parseElement(bpmnParse, element);

    // Assert
    assertSame(element, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}.
   * <ul>
   *   <li>Given {@link BpmnParseFactory}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}
   */
  @Test
  public void testParseElement_givenBpmnParseFactory() {
    // Arrange
    BpmnParseHandlers bpmnParseHandlers = new BpmnParseHandlers();

    BpmnParser parser = new BpmnParser();
    parser.setBpmnParseFactory(mock(BpmnParseFactory.class));
    BpmnParse bpmnParse = new BpmnParse(parser);
    ActivitiListener element = new ActivitiListener();

    // Act
    bpmnParseHandlers.parseElement(bpmnParse, element);

    // Assert that nothing has changed
    assertTrue(element.getAttributes().isEmpty());
    assertTrue(element.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}.
   * <ul>
   *   <li>Given
   * {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}
   */
  @Test
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
    assertSame(element, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}.
   * <ul>
   *   <li>Then {@link BooleanDataObject} (default constructor) ExecutionListeners
   * Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}
   */
  @Test
  public void testParseElement_thenBooleanDataObjectExecutionListenersEmpty() {
    // Arrange
    BpmnParseHandlers bpmnParseHandlers = new BpmnParseHandlers();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    BooleanDataObject element = new BooleanDataObject();

    // Act
    bpmnParseHandlers.parseElement(bpmnParse, element);

    // Assert that nothing has changed
    assertTrue(element.getExecutionListeners().isEmpty());
    assertTrue(element.getAttributes().isEmpty());
    assertTrue(element.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}.
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).</li>
   *   <li>Then {@link ActivitiListener} (default constructor) Attributes
   * Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnParseHandlers#parseElement(BpmnParse, BaseElement)}
   */
  @Test
  public void testParseElement_whenActivitiListener_thenActivitiListenerAttributesEmpty() {
    // Arrange
    BpmnParseHandlers bpmnParseHandlers = new BpmnParseHandlers();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    ActivitiListener element = new ActivitiListener();

    // Act
    bpmnParseHandlers.parseElement(bpmnParse, element);

    // Assert that nothing has changed
    assertTrue(element.getAttributes().isEmpty());
    assertTrue(element.getExtensionElements().isEmpty());
  }
}
