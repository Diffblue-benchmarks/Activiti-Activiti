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
import org.activiti.bpmn.model.InclusiveGateway;
import org.activiti.engine.impl.bpmn.behavior.InclusiveGatewayActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.junit.Test;

public class InclusiveGatewayParseHandlerDiffblueTest {
  /**
   * Test
   * {@link InclusiveGatewayParseHandler#executeParse(BpmnParse, InclusiveGateway)}
   * with {@code BpmnParse}, {@code InclusiveGateway}.
   * <p>
   * Method under test:
   * {@link InclusiveGatewayParseHandler#executeParse(BpmnParse, InclusiveGateway)}
   */
  @Test
  public void testExecuteParseWithBpmnParseInclusiveGateway() {
    // Arrange
    InclusiveGatewayParseHandler inclusiveGatewayParseHandler = new InclusiveGatewayParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    InclusiveGateway gateway = new InclusiveGateway();

    // Act
    inclusiveGatewayParseHandler.executeParse(bpmnParse, gateway);

    // Assert
    assertTrue(gateway.getBehavior() instanceof InclusiveGatewayActivityBehavior);
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link InclusiveGatewayParseHandler}
   *   <li>{@link InclusiveGatewayParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new InclusiveGatewayParseHandler()).getHandledType();

    // Assert
    Class<InclusiveGateway> expectedHandledType = InclusiveGateway.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
