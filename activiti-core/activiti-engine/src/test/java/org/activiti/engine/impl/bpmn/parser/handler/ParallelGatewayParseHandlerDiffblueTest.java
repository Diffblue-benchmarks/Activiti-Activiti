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
import static org.junit.Assert.assertTrue;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.ParallelGateway;
import org.activiti.engine.impl.bpmn.behavior.ParallelGatewayActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.junit.Test;

public class ParallelGatewayParseHandlerDiffblueTest {
  /**
   * Test
   * {@link ParallelGatewayParseHandler#executeParse(BpmnParse, ParallelGateway)}
   * with {@code BpmnParse}, {@code ParallelGateway}.
   * <p>
   * Method under test:
   * {@link ParallelGatewayParseHandler#executeParse(BpmnParse, ParallelGateway)}
   */
  @Test
  public void testExecuteParseWithBpmnParseParallelGateway() {
    // Arrange
    ParallelGatewayParseHandler parallelGatewayParseHandler = new ParallelGatewayParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    ParallelGateway gateway = new ParallelGateway();

    // Act
    parallelGatewayParseHandler.executeParse(bpmnParse, gateway);

    // Assert
    assertTrue(gateway.getBehavior() instanceof ParallelGatewayActivityBehavior);
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link ParallelGatewayParseHandler}
   *   <li>{@link ParallelGatewayParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new ParallelGatewayParseHandler()).getHandledType();

    // Assert
    Class<ParallelGateway> expectedHandledType = ParallelGateway.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}