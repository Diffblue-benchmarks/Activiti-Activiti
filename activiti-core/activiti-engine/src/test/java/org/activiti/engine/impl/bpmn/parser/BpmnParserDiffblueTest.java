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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultListenerFactory;
import org.activiti.engine.impl.bpmn.parser.factory.ListenerFactory;
import org.activiti.engine.impl.cfg.BpmnParseFactory;
import org.junit.Test;
import org.mockito.Mockito;

public class BpmnParserDiffblueTest {
  /**
   * Test {@link BpmnParser#createParse()}.
   * <ul>
   *   <li>Then return {@link BpmnParse#BpmnParse(BpmnParser)} with parser is
   * {@link BpmnParser} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParser#createParse()}
   */
  @Test
  public void testCreateParse_thenReturnBpmnParseWithParserIsBpmnParser() {
    // Arrange
    BpmnParseFactory bpmnParseFactory = mock(BpmnParseFactory.class);
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    when(bpmnParseFactory.createBpmnParse(Mockito.<BpmnParser>any())).thenReturn(bpmnParse);

    BpmnParser bpmnParser = new BpmnParser();
    bpmnParser.setBpmnParseFactory(bpmnParseFactory);

    // Act
    BpmnParse actualCreateParseResult = bpmnParser.createParse();

    // Assert
    verify(bpmnParseFactory).createBpmnParse(isA(BpmnParser.class));
    assertSame(bpmnParse, actualCreateParseResult);
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BpmnParser}
   *   <li>{@link BpmnParser#setActivityBehaviorFactory(ActivityBehaviorFactory)}
   *   <li>{@link BpmnParser#setBpmnParseFactory(BpmnParseFactory)}
   *   <li>{@link BpmnParser#setBpmnParserHandlers(BpmnParseHandlers)}
   *   <li>{@link BpmnParser#setListenerFactory(ListenerFactory)}
   *   <li>{@link BpmnParser#getActivityBehaviorFactory()}
   *   <li>{@link BpmnParser#getBpmnParseFactory()}
   *   <li>{@link BpmnParser#getBpmnParserHandlers()}
   *   <li>{@link BpmnParser#getListenerFactory()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    BpmnParser actualBpmnParser = new BpmnParser();
    DefaultActivityBehaviorFactory activityBehaviorFactory = new DefaultActivityBehaviorFactory();
    actualBpmnParser.setActivityBehaviorFactory(activityBehaviorFactory);
    BpmnParseFactory bpmnParseFactory = mock(BpmnParseFactory.class);
    actualBpmnParser.setBpmnParseFactory(bpmnParseFactory);
    BpmnParseHandlers bpmnParserHandlers = new BpmnParseHandlers();
    actualBpmnParser.setBpmnParserHandlers(bpmnParserHandlers);
    DefaultListenerFactory listenerFactory = new DefaultListenerFactory();
    actualBpmnParser.setListenerFactory(listenerFactory);
    ActivityBehaviorFactory actualActivityBehaviorFactory = actualBpmnParser.getActivityBehaviorFactory();
    BpmnParseFactory actualBpmnParseFactory = actualBpmnParser.getBpmnParseFactory();
    BpmnParseHandlers actualBpmnParserHandlers = actualBpmnParser.getBpmnParserHandlers();
    ListenerFactory actualListenerFactory = actualBpmnParser.getListenerFactory();

    // Assert that nothing has changed
    assertTrue(actualBpmnParserHandlers.parseHandlers.isEmpty());
    assertSame(bpmnParserHandlers, actualBpmnParserHandlers);
    assertSame(activityBehaviorFactory, actualActivityBehaviorFactory);
    assertSame(listenerFactory, actualListenerFactory);
    assertSame(bpmnParseFactory, actualBpmnParseFactory);
  }
}
