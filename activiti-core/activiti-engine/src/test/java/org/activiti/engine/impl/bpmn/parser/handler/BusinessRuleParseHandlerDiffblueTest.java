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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParseHandlers;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultListenerFactory;
import org.activiti.engine.impl.delegate.ActivityBehavior;
import org.junit.Test;
import org.mockito.Mockito;

public class BusinessRuleParseHandlerDiffblueTest {
  /**
   * Test
   * {@link BusinessRuleParseHandler#executeParse(BpmnParse, BusinessRuleTask)}
   * with {@code BpmnParse}, {@code BusinessRuleTask}.
   * <p>
   * Method under test:
   * {@link BusinessRuleParseHandler#executeParse(BpmnParse, BusinessRuleTask)}
   */
  @Test
  public void testExecuteParseWithBpmnParseBusinessRuleTask() {
    // Arrange
    BusinessRuleParseHandler businessRuleParseHandler = new BusinessRuleParseHandler();
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = mock(DefaultActivityBehaviorFactory.class);
    when(defaultActivityBehaviorFactory.createBusinessRuleTaskActivityBehavior(Mockito.<BusinessRuleTask>any()))
        .thenReturn(mock(ActivityBehavior.class));
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(defaultActivityBehaviorFactory);
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    // Act
    businessRuleParseHandler.executeParse(bpmnParse, new BusinessRuleTask());

    // Assert
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
    verify(defaultActivityBehaviorFactory).createBusinessRuleTaskActivityBehavior(isA(BusinessRuleTask.class));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BusinessRuleParseHandler}
   *   <li>{@link BusinessRuleParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new BusinessRuleParseHandler()).getHandledType();

    // Assert
    Class<BusinessRuleTask> expectedHandledType = BusinessRuleTask.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
