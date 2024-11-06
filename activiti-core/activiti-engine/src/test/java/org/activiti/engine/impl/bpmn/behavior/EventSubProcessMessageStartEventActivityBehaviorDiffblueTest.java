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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.ValuedDataObject;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContext;
import org.activiti.engine.impl.bpmn.parser.factory.MessageExecutionContext;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.el.ExpressionManager;
import org.junit.Test;

public class EventSubProcessMessageStartEventActivityBehaviorDiffblueTest {
  /**
   * Test
   * {@link EventSubProcessMessageStartEventActivityBehavior#EventSubProcessMessageStartEventActivityBehavior(MessageEventDefinition, MessageExecutionContext)}.
   * <p>
   * Method under test:
   * {@link EventSubProcessMessageStartEventActivityBehavior#EventSubProcessMessageStartEventActivityBehavior(MessageEventDefinition, MessageExecutionContext)}
   */
  @Test
  public void testNewEventSubProcessMessageStartEventActivityBehavior() {
    // Arrange
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    ExpressionManager expressionManager = new ExpressionManager();

    // Act
    EventSubProcessMessageStartEventActivityBehavior actualEventSubProcessMessageStartEventActivityBehavior = new EventSubProcessMessageStartEventActivityBehavior(
        messageEventDefinition, new DefaultMessageExecutionContext(messageEventDefinition2, expressionManager,
            mock(MessagePayloadMappingProvider.class)));

    // Assert
    MessageExecutionContext messageExecutionContext = actualEventSubProcessMessageStartEventActivityBehavior.messageExecutionContext;
    assertTrue(messageExecutionContext instanceof DefaultMessageExecutionContext);
    MessageEventDefinition messageEventDefinition3 = actualEventSubProcessMessageStartEventActivityBehavior.messageEventDefinition;
    assertNull(messageEventDefinition3.getId());
    assertNull(messageEventDefinition3.getCorrelationKey());
    assertNull(messageEventDefinition3.getMessageExpression());
    assertNull(messageEventDefinition3.getMessageRef());
    assertNull(actualEventSubProcessMessageStartEventActivityBehavior.getMultiInstanceActivityBehavior());
    assertEquals(0, messageEventDefinition3.getXmlColumnNumber());
    assertEquals(0, messageEventDefinition3.getXmlRowNumber());
    assertFalse(actualEventSubProcessMessageStartEventActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualEventSubProcessMessageStartEventActivityBehavior.hasMultiInstanceCharacteristics());
    assertTrue(messageEventDefinition3.getFieldExtensions().isEmpty());
    assertTrue(messageEventDefinition3.getAttributes().isEmpty());
    assertTrue(messageEventDefinition3.getExtensionElements().isEmpty());
    assertSame(expressionManager, ((DefaultMessageExecutionContext) messageExecutionContext).getExpressionManager());
  }

  /**
   * Test
   * {@link EventSubProcessMessageStartEventActivityBehavior#processDataObjects(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()} add {@link BooleanDataObject} (default
   * constructor).</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubProcessMessageStartEventActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects_whenArrayListAddBooleanDataObject_thenReturnSizeIsOne() {
    // Arrange
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    EventSubProcessMessageStartEventActivityBehavior eventSubProcessMessageStartEventActivityBehavior = new EventSubProcessMessageStartEventActivityBehavior(
        messageEventDefinition, new DefaultMessageExecutionContext(messageEventDefinition2, new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class)));

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());
    dataObjects.add(new BooleanDataObject());

    // Act
    Map<String, Object> actualProcessDataObjectsResult = eventSubProcessMessageStartEventActivityBehavior
        .processDataObjects(dataObjects);

    // Assert
    assertEquals(1, actualProcessDataObjectsResult.size());
    assertNull(actualProcessDataObjectsResult.get(null));
  }

  /**
   * Test
   * {@link EventSubProcessMessageStartEventActivityBehavior#processDataObjects(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubProcessMessageStartEventActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects_whenArrayList_thenReturnEmpty() {
    // Arrange
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    EventSubProcessMessageStartEventActivityBehavior eventSubProcessMessageStartEventActivityBehavior = new EventSubProcessMessageStartEventActivityBehavior(
        messageEventDefinition, new DefaultMessageExecutionContext(messageEventDefinition2, new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class)));

    // Act and Assert
    assertTrue(eventSubProcessMessageStartEventActivityBehavior.processDataObjects(new ArrayList<>()).isEmpty());
  }

  /**
   * Test
   * {@link EventSubProcessMessageStartEventActivityBehavior#processDataObjects(Collection)}.
   * <ul>
   *   <li>When {@link LinkedHashSet#LinkedHashSet()} add {@link BooleanDataObject}
   * (default constructor).</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubProcessMessageStartEventActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects_whenLinkedHashSetAddBooleanDataObject_thenReturnSizeIsOne() {
    // Arrange
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    EventSubProcessMessageStartEventActivityBehavior eventSubProcessMessageStartEventActivityBehavior = new EventSubProcessMessageStartEventActivityBehavior(
        messageEventDefinition, new DefaultMessageExecutionContext(messageEventDefinition2, new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class)));

    LinkedHashSet<ValuedDataObject> dataObjects = new LinkedHashSet<>();
    dataObjects.add(new BooleanDataObject());

    // Act
    Map<String, Object> actualProcessDataObjectsResult = eventSubProcessMessageStartEventActivityBehavior
        .processDataObjects(dataObjects);

    // Assert
    assertEquals(1, actualProcessDataObjectsResult.size());
    assertNull(actualProcessDataObjectsResult.get(null));
  }

  /**
   * Test
   * {@link EventSubProcessMessageStartEventActivityBehavior#processDataObjects(Collection)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubProcessMessageStartEventActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects_whenNull_thenReturnEmpty() {
    // Arrange
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act and Assert
    assertTrue((new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition,
        new DefaultMessageExecutionContext(messageEventDefinition2, new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class))))
        .processDataObjects(null)
        .isEmpty());
  }
}
