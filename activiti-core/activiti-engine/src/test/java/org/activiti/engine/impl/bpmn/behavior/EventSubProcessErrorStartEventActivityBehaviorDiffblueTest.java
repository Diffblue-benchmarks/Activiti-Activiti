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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.ValuedDataObject;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContext;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.el.ExpressionManager;
import org.junit.Test;

public class EventSubProcessErrorStartEventActivityBehaviorDiffblueTest {
  /**
   * Test
   * {@link EventSubProcessErrorStartEventActivityBehavior#processDataObjects(Collection)}.
   * <p>
   * Method under test:
   * {@link EventSubProcessErrorStartEventActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects() {
    // Arrange
    EventSubProcessErrorStartEventActivityBehavior eventSubProcessErrorStartEventActivityBehavior = new EventSubProcessErrorStartEventActivityBehavior();
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    eventSubProcessErrorStartEventActivityBehavior
        .setMultiInstanceActivityBehavior(new ParallelMultiInstanceBehavior(activity,
            new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition,
                new DefaultMessageExecutionContext(messageEventDefinition2, new ExpressionManager(),
                    mock(MessagePayloadMappingProvider.class)))));

    // Act and Assert
    assertTrue(eventSubProcessErrorStartEventActivityBehavior.processDataObjects(new ArrayList<>()).isEmpty());
  }

  /**
   * Test
   * {@link EventSubProcessErrorStartEventActivityBehavior#processDataObjects(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()} add {@link BooleanDataObject} (default
   * constructor).</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubProcessErrorStartEventActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects_whenArrayListAddBooleanDataObject_thenReturnSizeIsOne() {
    // Arrange
    EventSubProcessErrorStartEventActivityBehavior eventSubProcessErrorStartEventActivityBehavior = new EventSubProcessErrorStartEventActivityBehavior();

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());
    dataObjects.add(new BooleanDataObject());

    // Act
    Map<String, Object> actualProcessDataObjectsResult = eventSubProcessErrorStartEventActivityBehavior
        .processDataObjects(dataObjects);

    // Assert
    assertEquals(1, actualProcessDataObjectsResult.size());
    assertNull(actualProcessDataObjectsResult.get(null));
  }

  /**
   * Test
   * {@link EventSubProcessErrorStartEventActivityBehavior#processDataObjects(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubProcessErrorStartEventActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects_whenArrayList_thenReturnEmpty() {
    // Arrange
    EventSubProcessErrorStartEventActivityBehavior eventSubProcessErrorStartEventActivityBehavior = new EventSubProcessErrorStartEventActivityBehavior();

    // Act and Assert
    assertTrue(eventSubProcessErrorStartEventActivityBehavior.processDataObjects(new ArrayList<>()).isEmpty());
  }

  /**
   * Test
   * {@link EventSubProcessErrorStartEventActivityBehavior#processDataObjects(Collection)}.
   * <ul>
   *   <li>When {@link LinkedHashSet#LinkedHashSet()} add {@link BooleanDataObject}
   * (default constructor).</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubProcessErrorStartEventActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects_whenLinkedHashSetAddBooleanDataObject_thenReturnSizeIsOne() {
    // Arrange
    EventSubProcessErrorStartEventActivityBehavior eventSubProcessErrorStartEventActivityBehavior = new EventSubProcessErrorStartEventActivityBehavior();

    LinkedHashSet<ValuedDataObject> dataObjects = new LinkedHashSet<>();
    dataObjects.add(new BooleanDataObject());

    // Act
    Map<String, Object> actualProcessDataObjectsResult = eventSubProcessErrorStartEventActivityBehavior
        .processDataObjects(dataObjects);

    // Assert
    assertEquals(1, actualProcessDataObjectsResult.size());
    assertNull(actualProcessDataObjectsResult.get(null));
  }

  /**
   * Test
   * {@link EventSubProcessErrorStartEventActivityBehavior#processDataObjects(Collection)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubProcessErrorStartEventActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects_whenNull_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new EventSubProcessErrorStartEventActivityBehavior()).processDataObjects(null).isEmpty());
  }

  /**
   * Test new {@link EventSubProcessErrorStartEventActivityBehavior} (default
   * constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link EventSubProcessErrorStartEventActivityBehavior}
   */
  @Test
  public void testNewEventSubProcessErrorStartEventActivityBehavior() {
    // Arrange and Act
    EventSubProcessErrorStartEventActivityBehavior actualEventSubProcessErrorStartEventActivityBehavior = new EventSubProcessErrorStartEventActivityBehavior();

    // Assert
    assertNull(actualEventSubProcessErrorStartEventActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualEventSubProcessErrorStartEventActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualEventSubProcessErrorStartEventActivityBehavior.hasMultiInstanceCharacteristics());
  }
}
