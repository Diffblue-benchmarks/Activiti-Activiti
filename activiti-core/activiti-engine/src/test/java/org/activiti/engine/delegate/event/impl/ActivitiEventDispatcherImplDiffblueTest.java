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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Map;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.BaseEntityEventListener;
import org.activiti.engine.impl.bpmn.helper.DelegateActivitiEventListener;
import org.activiti.engine.impl.bpmn.helper.DelegateExpressionActivitiEventListener;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class ActivitiEventDispatcherImplDiffblueTest {
  /**
   * Method under test:
   * {@link ActivitiEventDispatcherImpl#addEventListener(ActivitiEventListener)}
   */
  @Test
  public void testAddEventListener() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = new ActivitiEventDispatcherImpl();
    BaseEntityEventListener listenerToAdd = new BaseEntityEventListener(true);

    // Act
    activitiEventDispatcherImpl.addEventListener(listenerToAdd);

    // Assert
    List<ActivitiEventListener> activitiEventListenerList = activitiEventDispatcherImpl.eventSupport.eventListeners;
    assertEquals(1, activitiEventListenerList.size());
    assertSame(listenerToAdd, activitiEventListenerList.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiEventDispatcherImpl#addEventListener(ActivitiEventListener)}
   */
  @Test
  public void testAddEventListener2() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = new ActivitiEventDispatcherImpl();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression expression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type),
        "Expression Text");

    Class<Object> entityClass = Object.class;
    DelegateExpressionActivitiEventListener listenerToAdd = new DelegateExpressionActivitiEventListener(expression,
        entityClass);

    // Act
    activitiEventDispatcherImpl.addEventListener(listenerToAdd);

    // Assert
    List<ActivitiEventListener> activitiEventListenerList = activitiEventDispatcherImpl.eventSupport.eventListeners;
    assertEquals(1, activitiEventListenerList.size());
    assertSame(listenerToAdd, activitiEventListenerList.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiEventDispatcherImpl#addEventListener(ActivitiEventListener, ActivitiEventType[])}
   */
  @Test
  public void testAddEventListener3() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = new ActivitiEventDispatcherImpl();
    BaseEntityEventListener listenerToAdd = new BaseEntityEventListener(true);

    // Act
    activitiEventDispatcherImpl.addEventListener(listenerToAdd, ActivitiEventType.ENTITY_CREATED);

    // Assert
    ActivitiEventSupport activitiEventSupport = activitiEventDispatcherImpl.eventSupport;
    Map<ActivitiEventType, List<ActivitiEventListener>> activitiEventTypeListMap = activitiEventSupport.typedListeners;
    assertEquals(1, activitiEventTypeListMap.size());
    List<ActivitiEventListener> getResult = activitiEventTypeListMap.get(ActivitiEventType.ENTITY_CREATED);
    assertEquals(1, getResult.size());
    assertTrue(activitiEventSupport.eventListeners.isEmpty());
    assertSame(listenerToAdd, getResult.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiEventDispatcherImpl#addEventListener(ActivitiEventListener, ActivitiEventType[])}
   */
  @Test
  public void testAddEventListener4() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = new ActivitiEventDispatcherImpl();
    BaseEntityEventListener listenerToAdd = new BaseEntityEventListener(true);
    activitiEventDispatcherImpl.addEventListener(listenerToAdd, ActivitiEventType.ENTITY_CREATED);
    BaseEntityEventListener listenerToAdd2 = new BaseEntityEventListener(true);

    // Act
    activitiEventDispatcherImpl.addEventListener(listenerToAdd2, ActivitiEventType.ENTITY_CREATED);

    // Assert
    ActivitiEventSupport activitiEventSupport = activitiEventDispatcherImpl.eventSupport;
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
   * {@link ActivitiEventDispatcherImpl#addEventListener(ActivitiEventListener, ActivitiEventType[])}
   */
  @Test
  public void testAddEventListener5() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = new ActivitiEventDispatcherImpl();
    BaseEntityEventListener listenerToAdd = new BaseEntityEventListener(true);

    // Act
    activitiEventDispatcherImpl.addEventListener(listenerToAdd);

    // Assert
    ActivitiEventSupport activitiEventSupport = activitiEventDispatcherImpl.eventSupport;
    List<ActivitiEventListener> activitiEventListenerList = activitiEventSupport.eventListeners;
    assertEquals(1, activitiEventListenerList.size());
    assertTrue(activitiEventSupport.typedListeners.isEmpty());
    assertSame(listenerToAdd, activitiEventListenerList.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiEventDispatcherImpl#addEventListener(ActivitiEventListener, ActivitiEventType[])}
   */
  @Test
  public void testAddEventListener6() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = new ActivitiEventDispatcherImpl();
    BaseEntityEventListener listenerToAdd = new BaseEntityEventListener(true);

    // Act
    activitiEventDispatcherImpl.addEventListener(listenerToAdd, ActivitiEventType.ENTITY_CREATED,
        ActivitiEventType.ENTITY_INITIALIZED, ActivitiEventType.ENTITY_CREATED);

    // Assert
    ActivitiEventSupport activitiEventSupport = activitiEventDispatcherImpl.eventSupport;
    Map<ActivitiEventType, List<ActivitiEventListener>> activitiEventTypeListMap = activitiEventSupport.typedListeners;
    assertEquals(2, activitiEventTypeListMap.size());
    List<ActivitiEventListener> getResult = activitiEventTypeListMap.get(ActivitiEventType.ENTITY_CREATED);
    assertEquals(1, getResult.size());
    List<ActivitiEventListener> getResult2 = activitiEventTypeListMap.get(ActivitiEventType.ENTITY_INITIALIZED);
    assertEquals(1, getResult2.size());
    assertTrue(activitiEventSupport.eventListeners.isEmpty());
    assertSame(listenerToAdd, getResult.get(0));
    assertSame(listenerToAdd, getResult2.get(0));
  }

  /**
   * Method under test:
   * {@link ActivitiEventDispatcherImpl#dispatchEvent(ActivitiEvent)}
   */
  @Test
  public void testDispatchEvent() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = new ActivitiEventDispatcherImpl();
    ActivitiEvent event = mock(ActivitiEvent.class);
    when(event.getType()).thenReturn(ActivitiEventType.ENTITY_CREATED);

    // Act
    activitiEventDispatcherImpl.dispatchEvent(event);

    // Assert
    verify(event, atLeast(1)).getType();
  }

  /**
   * Method under test:
   * {@link ActivitiEventDispatcherImpl#dispatchEvent(ActivitiEvent)}
   */
  @Test
  public void testDispatchEvent2() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = new ActivitiEventDispatcherImpl();
    activitiEventDispatcherImpl.addEventListener(new BaseEntityEventListener(true), ActivitiEventType.ENTITY_CREATED);
    ActivitiEvent event = mock(ActivitiEvent.class);
    when(event.getType()).thenReturn(ActivitiEventType.ENTITY_CREATED);

    // Act
    activitiEventDispatcherImpl.dispatchEvent(event);

    // Assert
    verify(event, atLeast(1)).getType();
  }

  /**
   * Method under test:
   * {@link ActivitiEventDispatcherImpl#dispatchEvent(ActivitiEvent)}
   */
  @Test
  public void testDispatchEvent3() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = new ActivitiEventDispatcherImpl();
    ActivitiEvent event = mock(ActivitiEvent.class);
    when(event.getType()).thenReturn(ActivitiEventType.ENTITY_DELETED);

    // Act
    activitiEventDispatcherImpl.dispatchEvent(event);

    // Assert
    verify(event, atLeast(1)).getType();
  }

  /**
   * Method under test:
   * {@link ActivitiEventDispatcherImpl#dispatchEvent(ActivitiEvent)}
   */
  @Test
  public void testDispatchEvent4() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = new ActivitiEventDispatcherImpl();
    activitiEventDispatcherImpl.addEventListener(new DelegateActivitiEventListener("Class Name", null));
    ActivitiEvent event = mock(ActivitiEvent.class);
    when(event.getType()).thenReturn(ActivitiEventType.ENTITY_CREATED);

    // Act
    activitiEventDispatcherImpl.dispatchEvent(event);

    // Assert
    verify(event, atLeast(1)).getType();
  }

  /**
   * Method under test:
   * {@link ActivitiEventDispatcherImpl#extractBpmnModelFromEvent(ActivitiEvent)}
   */
  @Test
  public void testExtractBpmnModelFromEvent() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = new ActivitiEventDispatcherImpl();

    // Act and Assert
    assertNull(activitiEventDispatcherImpl.extractBpmnModelFromEvent(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiEventDispatcherImpl#setEnabled(boolean)}
   *   <li>{@link ActivitiEventDispatcherImpl#isEnabled()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ActivitiEventDispatcherImpl activitiEventDispatcherImpl = new ActivitiEventDispatcherImpl();

    // Act
    activitiEventDispatcherImpl.setEnabled(true);

    // Assert that nothing has changed
    assertTrue(activitiEventDispatcherImpl.isEnabled());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link ActivitiEventDispatcherImpl}
   */
  @Test
  public void testNewActivitiEventDispatcherImpl() {
    // Arrange and Act
    ActivitiEventDispatcherImpl actualActivitiEventDispatcherImpl = new ActivitiEventDispatcherImpl();

    // Assert
    ActivitiEventSupport activitiEventSupport = actualActivitiEventDispatcherImpl.eventSupport;
    assertTrue(activitiEventSupport.eventListeners.isEmpty());
    assertTrue(activitiEventSupport.typedListeners.isEmpty());
    assertTrue(actualActivitiEventDispatcherImpl.isEnabled());
  }
}
