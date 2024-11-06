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
package org.activiti.engine.impl.cfg;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.junit.Test;

public class DefaultBpmnParseFactoryDiffblueTest {
  /**
   * Test {@link DefaultBpmnParseFactory#createBpmnParse(BpmnParser)}.
   * <ul>
   *   <li>Given {@link BpmnParseFactory}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultBpmnParseFactory#createBpmnParse(BpmnParser)}
   */
  @Test
  public void testCreateBpmnParse_givenBpmnParseFactory() {
    // Arrange
    DefaultBpmnParseFactory defaultBpmnParseFactory = new DefaultBpmnParseFactory();

    BpmnParser bpmnParser = new BpmnParser();
    bpmnParser.setBpmnParseFactory(mock(BpmnParseFactory.class));

    // Act
    BpmnParse actualCreateBpmnParseResult = defaultBpmnParseFactory.createBpmnParse(bpmnParser);

    // Assert
    assertNull(actualCreateBpmnParseResult.getTargetNamespace());
    assertNull(actualCreateBpmnParseResult.getSequenceFlows());
    assertNull(actualCreateBpmnParseResult.getBpmnModel());
    assertNull(actualCreateBpmnParseResult.getCurrentFlowElement());
    assertNull(actualCreateBpmnParseResult.getCurrentProcess());
    assertNull(actualCreateBpmnParseResult.getCurrentSubProcess());
    assertNull(actualCreateBpmnParseResult.getBpmnParserHandlers());
    assertNull(actualCreateBpmnParseResult.getActivityBehaviorFactory());
    assertNull(actualCreateBpmnParseResult.getListenerFactory());
    assertNull(actualCreateBpmnParseResult.getDeployment());
    assertNull(actualCreateBpmnParseResult.getCurrentProcessDefinition());
    assertTrue(actualCreateBpmnParseResult.getProcessDefinitions().isEmpty());
    assertTrue(actualCreateBpmnParseResult.isValidateProcess());
    assertTrue(actualCreateBpmnParseResult.isValidateSchema());
  }

  /**
   * Test {@link DefaultBpmnParseFactory#createBpmnParse(BpmnParser)}.
   * <ul>
   *   <li>When {@link BpmnParser} (default constructor).</li>
   *   <li>Then return TargetNamespace is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultBpmnParseFactory#createBpmnParse(BpmnParser)}
   */
  @Test
  public void testCreateBpmnParse_whenBpmnParser_thenReturnTargetNamespaceIsNull() {
    // Arrange
    DefaultBpmnParseFactory defaultBpmnParseFactory = new DefaultBpmnParseFactory();

    // Act
    BpmnParse actualCreateBpmnParseResult = defaultBpmnParseFactory.createBpmnParse(new BpmnParser());

    // Assert
    assertNull(actualCreateBpmnParseResult.getTargetNamespace());
    assertNull(actualCreateBpmnParseResult.getSequenceFlows());
    assertNull(actualCreateBpmnParseResult.getBpmnModel());
    assertNull(actualCreateBpmnParseResult.getCurrentFlowElement());
    assertNull(actualCreateBpmnParseResult.getCurrentProcess());
    assertNull(actualCreateBpmnParseResult.getCurrentSubProcess());
    assertNull(actualCreateBpmnParseResult.getBpmnParserHandlers());
    assertNull(actualCreateBpmnParseResult.getActivityBehaviorFactory());
    assertNull(actualCreateBpmnParseResult.getListenerFactory());
    assertNull(actualCreateBpmnParseResult.getDeployment());
    assertNull(actualCreateBpmnParseResult.getCurrentProcessDefinition());
    assertTrue(actualCreateBpmnParseResult.getProcessDefinitions().isEmpty());
    assertTrue(actualCreateBpmnParseResult.isValidateProcess());
    assertTrue(actualCreateBpmnParseResult.isValidateSchema());
  }
}
