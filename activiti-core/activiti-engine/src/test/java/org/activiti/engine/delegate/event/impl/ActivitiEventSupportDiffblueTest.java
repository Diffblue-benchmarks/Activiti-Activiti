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
package org.activiti.engine.delegate.event.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.List;
import java.util.Map;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.BaseEntityEventListener;
import org.activiti.engine.impl.bpmn.helper.DelegateExpressionActivitiEventListener;
import org.activiti.engine.impl.bpmn.helper.ErrorThrowingEventListener;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class ActivitiEventSupportDiffblueTest {
  /**
   * Method under test:
   * {@link ActivitiEventSupport#addEventListener(ActivitiEventListener)}
   */
  @Test
  public void testAddEventListener() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    BaseEntityEventListener listenerToAdd = new BaseEntityEventListener(true);

    // Act
    activitiEventSupport.addEventListener(listenerToAdd);

    // Assert
    List<ActivitiEventListener> activitiEventListenerList = activitiEventSupport.eventListeners;
    assertEquals(1, activitiEventListenerList.size());
    assertSame(listenerToAdd, activitiEventListenerList.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiEventSupport#addEventListener(ActivitiEventListener)}
   */
  @Test
  public void testAddEventListener2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ActivitiEventSupport()).addEventListener(null));
  }

  /**
   * Method under test:
   * {@link ActivitiEventSupport#addEventListener(ActivitiEventListener)}
   */
  @Test
  public void testAddEventListener3() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression expression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type),
        "Expression Text");

    Class<Object> entityClass = Object.class;
    DelegateExpressionActivitiEventListener listenerToAdd = new DelegateExpressionActivitiEventListener(expression,
        entityClass);

    // Act
    activitiEventSupport.addEventListener(listenerToAdd);

    // Assert
    List<ActivitiEventListener> activitiEventListenerList = activitiEventSupport.eventListeners;
    assertEquals(1, activitiEventListenerList.size());
    assertSame(listenerToAdd, activitiEventListenerList.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiEventSupport#addEventListener(ActivitiEventListener, ActivitiEventType[])}
   */
  @Test
  public void testAddEventListener4() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    BaseEntityEventListener listenerToAdd = new BaseEntityEventListener(true);

    // Act
    activitiEventSupport.addEventListener(listenerToAdd, ActivitiEventType.ENTITY_CREATED);

    // Assert
    Map<ActivitiEventType, List<ActivitiEventListener>> activitiEventTypeListMap = activitiEventSupport.typedListeners;
    assertEquals(1, activitiEventTypeListMap.size());
    List<ActivitiEventListener> getResult = activitiEventTypeListMap.get(ActivitiEventType.ENTITY_CREATED);
    assertEquals(1, getResult.size());
    assertTrue(activitiEventSupport.eventListeners.isEmpty());
    assertSame(listenerToAdd, getResult.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiEventSupport#addEventListener(ActivitiEventListener, ActivitiEventType[])}
   */
  @Test
  public void testAddEventListener5() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    BaseEntityEventListener listenerToAdd = new BaseEntityEventListener(true);
    activitiEventSupport.addEventListener(listenerToAdd, ActivitiEventType.ENTITY_CREATED);
    BaseEntityEventListener listenerToAdd2 = new BaseEntityEventListener(true);

    // Act
    activitiEventSupport.addEventListener(listenerToAdd2, ActivitiEventType.ENTITY_CREATED);

    // Assert
    Map<ActivitiEventType, List<ActivitiEventListener>> activitiEventTypeListMap = activitiEventSupport.typedListeners;
    assertEquals(1, activitiEventTypeListMap.size());
    List<ActivitiEventListener> getResult = activitiEventTypeListMap.get(ActivitiEventType.ENTITY_CREATED);
    assertEquals(2, getResult.size());
    ActivitiEventListener getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof BaseEntityEventListener);
    assertTrue(activitiEventSupport.eventListeners.isEmpty());
    assertSame(listenerToAdd, getResult2);
    assertSame(listenerToAdd2, getResult.get(1));
  }

  /**
   * Method under test:
   * {@link ActivitiEventSupport#addEventListener(ActivitiEventListener, ActivitiEventType[])}
   */
  @Test
  public void testAddEventListener6() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ActivitiEventSupport()).addEventListener(null, ActivitiEventType.ENTITY_CREATED));
  }

  /**
   * Method under test:
   * {@link ActivitiEventSupport#addEventListener(ActivitiEventListener, ActivitiEventType[])}
   */
  @Test
  public void testAddEventListener7() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    BaseEntityEventListener listenerToAdd = new BaseEntityEventListener(true);

    // Act
    activitiEventSupport.addEventListener(listenerToAdd, null);

    // Assert
    List<ActivitiEventListener> activitiEventListenerList = activitiEventSupport.eventListeners;
    assertEquals(1, activitiEventListenerList.size());
    assertTrue(activitiEventSupport.typedListeners.isEmpty());
    assertSame(listenerToAdd, activitiEventListenerList.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiEventSupport#addEventListener(ActivitiEventListener, ActivitiEventType[])}
   */
  @Test
  public void testAddEventListener8() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    BaseEntityEventListener listenerToAdd = new BaseEntityEventListener(true);

    // Act
    activitiEventSupport.addEventListener(listenerToAdd);

    // Assert
    List<ActivitiEventListener> activitiEventListenerList = activitiEventSupport.eventListeners;
    assertEquals(1, activitiEventListenerList.size());
    assertTrue(activitiEventSupport.typedListeners.isEmpty());
    assertSame(listenerToAdd, activitiEventListenerList.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiEventSupport#addEventListener(ActivitiEventListener, ActivitiEventType[])}
   */
  @Test
  public void testAddEventListener9() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    BaseEntityEventListener listenerToAdd = new BaseEntityEventListener(true);

    // Act
    activitiEventSupport.addEventListener(listenerToAdd, ActivitiEventType.ENTITY_CREATED, null,
        ActivitiEventType.ENTITY_CREATED);

    // Assert
    Map<ActivitiEventType, List<ActivitiEventListener>> activitiEventTypeListMap = activitiEventSupport.typedListeners;
    assertEquals(2, activitiEventTypeListMap.size());
    List<ActivitiEventListener> getResult = activitiEventTypeListMap.get(null);
    assertEquals(1, getResult.size());
    List<ActivitiEventListener> getResult2 = activitiEventTypeListMap.get(ActivitiEventType.ENTITY_CREATED);
    assertEquals(1, getResult2.size());
    assertTrue(activitiEventSupport.eventListeners.isEmpty());
    assertSame(listenerToAdd, getResult.get(0));
    assertSame(listenerToAdd, getResult2.get(0));
  }

  /**
   * Method under test: {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent)}
   */
  @Test
  public void testDispatchEvent() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ActivitiEventSupport()).dispatchEvent(null));
  }

  /**
   * Method under test: {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent)}
   */
  @Test
  public void testDispatchEvent2() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    activitiEventSupport.addEventListener(new ErrorThrowingEventListener());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> activitiEventSupport.dispatchEvent(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Method under test: {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent)}
   */
  @Test
  public void testDispatchEvent3() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    activitiEventSupport.addEventListener(new ErrorThrowingEventListener(), ActivitiEventType.ENTITY_CREATED);
    activitiEventSupport.addEventListener(new BaseEntityEventListener(true));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> activitiEventSupport.dispatchEvent(new ActivitiActivityEventImpl(ActivitiEventType.ENTITY_CREATED)));
  }

  /**
   * Method under test: {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent)}
   */
  @Test
  public void testDispatchEvent4() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    activitiEventSupport.addEventListener(new ErrorThrowingEventListener(), ActivitiEventType.ENTITY_CREATED);
    activitiEventSupport.addEventListener(new BaseEntityEventListener(true));

    ActivitiActivityCancelledEventImpl event = new ActivitiActivityCancelledEventImpl();
    event.setActivityId("42");
    event.setActivityName(
        "No execution context active and event is not related to an execution. No compensation event can"
            + " be thrown.");
    event.setActivityType(
        "No execution context active and event is not related to an execution. No compensation event can"
            + " be thrown.");
    event.setBehaviorClass(
        "No execution context active and event is not related to an execution. No compensation event can"
            + " be thrown.");
    event.setCause(JSONObject.NULL);
    event.setExecutionId("42");
    event.setProcessDefinitionId("42");
    event.setProcessInstanceId("42");
    event.setReason("Just cause");
    event.setType(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> activitiEventSupport.dispatchEvent(event));
  }

  /**
   * Method under test:
   * {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent, ActivitiEventListener)}
   */
  @Test
  public void testDispatchEvent5() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    ActivitiActivityCancelledEventImpl event = new ActivitiActivityCancelledEventImpl();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> activitiEventSupport.dispatchEvent(event, new ErrorThrowingEventListener()));
  }

  /**
   * Method under test:
   * {@link ActivitiEventSupport#addTypedEventListener(ActivitiEventListener, ActivitiEventType)}
   */
  @Test
  public void testAddTypedEventListener() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    BaseEntityEventListener listener = new BaseEntityEventListener(true);

    // Act
    activitiEventSupport.addTypedEventListener(listener, ActivitiEventType.ENTITY_CREATED);

    // Assert
    Map<ActivitiEventType, List<ActivitiEventListener>> activitiEventTypeListMap = activitiEventSupport.typedListeners;
    assertEquals(1, activitiEventTypeListMap.size());
    List<ActivitiEventListener> getResult = activitiEventTypeListMap.get(ActivitiEventType.ENTITY_CREATED);
    assertEquals(1, getResult.size());
    assertSame(listener, getResult.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiEventSupport#addTypedEventListener(ActivitiEventListener, ActivitiEventType)}
   */
  @Test
  public void testAddTypedEventListener2() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    BaseEntityEventListener listenerToAdd = new BaseEntityEventListener(true);
    activitiEventSupport.addEventListener(listenerToAdd, ActivitiEventType.ENTITY_CREATED);
    BaseEntityEventListener listener = new BaseEntityEventListener(true);

    // Act
    activitiEventSupport.addTypedEventListener(listener, ActivitiEventType.ENTITY_CREATED);

    // Assert
    Map<ActivitiEventType, List<ActivitiEventListener>> activitiEventTypeListMap = activitiEventSupport.typedListeners;
    assertEquals(1, activitiEventTypeListMap.size());
    List<ActivitiEventListener> getResult = activitiEventTypeListMap.get(ActivitiEventType.ENTITY_CREATED);
    assertEquals(2, getResult.size());
    ActivitiEventListener getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof BaseEntityEventListener);
    assertSame(listenerToAdd, getResult2);
    assertSame(listener, getResult.get(1));
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link ActivitiEventSupport}
   */
  @Test
  public void testNewActivitiEventSupport() {
    // Arrange and Act
    ActivitiEventSupport actualActivitiEventSupport = new ActivitiEventSupport();

    // Assert
    assertTrue(actualActivitiEventSupport.eventListeners.isEmpty());
    assertTrue(actualActivitiEventSupport.typedListeners.isEmpty());
  }
}
