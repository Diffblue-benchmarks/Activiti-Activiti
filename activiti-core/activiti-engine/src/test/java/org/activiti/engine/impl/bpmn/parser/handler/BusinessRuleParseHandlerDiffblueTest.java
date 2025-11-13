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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.delegate.ActivityBehavior;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class BusinessRuleParseHandlerDiffblueTest {
  /**
   * Test {@link BusinessRuleParseHandler#executeParse(BpmnParse, BusinessRuleTask)} with {@code
   * BpmnParse}, {@code BusinessRuleTask}.
   *
   * <p>Method under test: {@link BusinessRuleParseHandler#executeParse(BpmnParse,
   * BusinessRuleTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BusinessRuleParseHandler.executeParse(BpmnParse, BusinessRuleTask)"})
  public void testExecuteParseWithBpmnParseBusinessRuleTask() {
    // Arrange
    BusinessRuleParseHandler businessRuleParseHandler = new BusinessRuleParseHandler();

    DefaultActivityBehaviorFactory activityBehaviorFactory =
        mock(DefaultActivityBehaviorFactory.class);
    when(activityBehaviorFactory.createBusinessRuleTaskActivityBehavior(
            Mockito.<BusinessRuleTask>any()))
        .thenReturn(mock(ActivityBehavior.class));

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setActivityBehaviorFactory(activityBehaviorFactory);

    // Act
    businessRuleParseHandler.executeParse(bpmnParse, new BusinessRuleTask());

    // Assert
    verify(activityBehaviorFactory)
        .createBusinessRuleTaskActivityBehavior(isA(BusinessRuleTask.class));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link BusinessRuleParseHandler}
   *   <li>{@link BusinessRuleParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BusinessRuleParseHandler.<init>()",
    "Class BusinessRuleParseHandler.getHandledType()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType =
        new BusinessRuleParseHandler().getHandledType();

    // Assert
    Class<BusinessRuleTask> expectedHandledType = BusinessRuleTask.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
