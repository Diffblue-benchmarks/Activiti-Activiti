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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.List;
import java.util.Map;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.BaseEntityEventListener;
import org.activiti.engine.impl.bpmn.helper.DelegateActivitiEventListener;
import org.activiti.engine.impl.bpmn.helper.ErrorThrowingEventListener;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ActivitiEventSupportDiffblueTest {
  /**
   * Test new {@link ActivitiEventSupport} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link ActivitiEventSupport}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiEventSupport.<init>()"})
  public void testNewActivitiEventSupport() {
    // Arrange and Act
    ActivitiEventSupport actualActivitiEventSupport = new ActivitiEventSupport();

    // Assert
    assertTrue(actualActivitiEventSupport.eventListeners.isEmpty());
    assertTrue(actualActivitiEventSupport.typedListeners.isEmpty());
  }

  /**
   * Test {@link ActivitiEventSupport#addEventListener(ActivitiEventListener)} with {@code
   * listenerToAdd}.
   *
   * <p>Method under test: {@link ActivitiEventSupport#addEventListener(ActivitiEventListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiEventSupport.addEventListener(ActivitiEventListener)"})
  public void testAddEventListenerWithListenerToAdd() {
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
   * Test {@link ActivitiEventSupport#addEventListener(ActivitiEventListener, ActivitiEventType[])}
   * with {@code listenerToAdd}, {@code types}.
   *
   * <p>Method under test: {@link ActivitiEventSupport#addEventListener(ActivitiEventListener,
   * ActivitiEventType[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventSupport.addEventListener(ActivitiEventListener, ActivitiEventType[])"
  })
  public void testAddEventListenerWithListenerToAddTypes() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    BaseEntityEventListener listenerToAdd = new BaseEntityEventListener(true);

    // Act
    activitiEventSupport.addEventListener(listenerToAdd, ActivitiEventType.ENTITY_CREATED);

    // Assert
    Map<ActivitiEventType, List<ActivitiEventListener>> activitiEventTypeListMap =
        activitiEventSupport.typedListeners;
    assertEquals(1, activitiEventTypeListMap.size());
    List<ActivitiEventListener> getResult =
        activitiEventTypeListMap.get(ActivitiEventType.ENTITY_CREATED);
    assertEquals(1, getResult.size());
    assertTrue(activitiEventSupport.eventListeners.isEmpty());
    assertSame(listenerToAdd, getResult.get(0));
  }

  /**
   * Test {@link ActivitiEventSupport#addEventListener(ActivitiEventListener, ActivitiEventType[])}
   * with {@code listenerToAdd}, {@code types}.
   *
   * <p>Method under test: {@link ActivitiEventSupport#addEventListener(ActivitiEventListener,
   * ActivitiEventType[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventSupport.addEventListener(ActivitiEventListener, ActivitiEventType[])"
  })
  public void testAddEventListenerWithListenerToAddTypes2() {
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
   * Test {@link ActivitiEventSupport#addEventListener(ActivitiEventListener, ActivitiEventType[])}
   * with {@code listenerToAdd}, {@code types}.
   *
   * <p>Method under test: {@link ActivitiEventSupport#addEventListener(ActivitiEventListener,
   * ActivitiEventType[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventSupport.addEventListener(ActivitiEventListener, ActivitiEventType[])"
  })
  public void testAddEventListenerWithListenerToAddTypes3() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ActivitiEventSupport().addEventListener(null, ActivitiEventType.ENTITY_CREATED));
  }

  /**
   * Test {@link ActivitiEventSupport#addEventListener(ActivitiEventListener, ActivitiEventType[])}
   * with {@code listenerToAdd}, {@code types}.
   *
   * <p>Method under test: {@link ActivitiEventSupport#addEventListener(ActivitiEventListener,
   * ActivitiEventType[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventSupport.addEventListener(ActivitiEventListener, ActivitiEventType[])"
  })
  public void testAddEventListenerWithListenerToAddTypes4() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    activitiEventSupport.addEventListener(
        new BaseEntityEventListener(true), ActivitiEventType.ENTITY_CREATED);
    BaseEntityEventListener listenerToAdd = new BaseEntityEventListener(true);

    // Act
    activitiEventSupport.addEventListener(listenerToAdd, ActivitiEventType.ENTITY_CREATED);

    // Assert
    Map<ActivitiEventType, List<ActivitiEventListener>> activitiEventTypeListMap =
        activitiEventSupport.typedListeners;
    assertEquals(1, activitiEventTypeListMap.size());
    List<ActivitiEventListener> getResult =
        activitiEventTypeListMap.get(ActivitiEventType.ENTITY_CREATED);
    assertEquals(2, getResult.size());
    assertTrue(activitiEventSupport.eventListeners.isEmpty());
    assertSame(listenerToAdd, getResult.get(1));
  }

  /**
   * Test {@link ActivitiEventSupport#addEventListener(ActivitiEventListener, ActivitiEventType[])}
   * with {@code listenerToAdd}, {@code types}.
   *
   * <p>Method under test: {@link ActivitiEventSupport#addEventListener(ActivitiEventListener,
   * ActivitiEventType[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventSupport.addEventListener(ActivitiEventListener, ActivitiEventType[])"
  })
  public void testAddEventListenerWithListenerToAddTypes5() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    BaseEntityEventListener listenerToAdd = new BaseEntityEventListener(true);

    // Act
    activitiEventSupport.addEventListener(listenerToAdd, new ActivitiEventType[] {});

    // Assert
    List<ActivitiEventListener> activitiEventListenerList = activitiEventSupport.eventListeners;
    assertEquals(1, activitiEventListenerList.size());
    assertTrue(activitiEventSupport.typedListeners.isEmpty());
    assertSame(listenerToAdd, activitiEventListenerList.get(0));
  }

  /**
   * Test {@link ActivitiEventSupport#addEventListener(ActivitiEventListener, ActivitiEventType[])}
   * with {@code listenerToAdd}, {@code types}.
   *
   * <p>Method under test: {@link ActivitiEventSupport#addEventListener(ActivitiEventListener,
   * ActivitiEventType[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventSupport.addEventListener(ActivitiEventListener, ActivitiEventType[])"
  })
  public void testAddEventListenerWithListenerToAddTypes6() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    BaseEntityEventListener listenerToAdd = new BaseEntityEventListener(true);

    // Act
    activitiEventSupport.addEventListener(
        listenerToAdd, ActivitiEventType.ENTITY_CREATED, null, ActivitiEventType.ENTITY_CREATED);

    // Assert
    Map<ActivitiEventType, List<ActivitiEventListener>> activitiEventTypeListMap =
        activitiEventSupport.typedListeners;
    assertEquals(2, activitiEventTypeListMap.size());
    List<ActivitiEventListener> getResult = activitiEventTypeListMap.get(null);
    assertEquals(1, getResult.size());
    assertEquals(getResult, activitiEventTypeListMap.get(ActivitiEventType.ENTITY_CREATED));
    assertSame(listenerToAdd, getResult.get(0));
  }

  /**
   * Test {@link ActivitiEventSupport#addEventListener(ActivitiEventListener)} with {@code
   * listenerToAdd}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventSupport#addEventListener(ActivitiEventListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiEventSupport.addEventListener(ActivitiEventListener)"})
  public void testAddEventListenerWithListenerToAdd_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ActivitiEventSupport().addEventListener(null));
  }

  /**
   * Test {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent)} with {@code event}.
   *
   * <p>Method under test: {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiEventSupport.dispatchEvent(ActivitiEvent)"})
  public void testDispatchEventWithEvent() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    activitiEventSupport.addEventListener(
        new BaseEntityEventListener(true), ActivitiEventType.ENTITY_CREATED);
    activitiEventSupport.addEventListener(new BaseEntityEventListener(false));

    ActivitiActivityCancelledEventImpl event = new ActivitiActivityCancelledEventImpl();
    event.setActivityId("42");
    event.setActivityName("Activity Name");
    event.setActivityType("Activity Type");
    event.setBehaviorClass("Behavior Class");
    event.setCause(JSONObject.NULL);
    event.setExecutionId("42");
    event.setProcessDefinitionId("42");
    event.setProcessInstanceId("42");
    event.setReason("Just cause");
    event.setType(ActivitiEventType.ENTITY_CREATED);

    // Act and Assert
    activitiEventSupport.dispatchEvent(event);
  }

  /**
   * Test {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent, ActivitiEventListener)} with
   * {@code event}, {@code listener}.
   *
   * <p>Method under test: {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent,
   * ActivitiEventListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventSupport.dispatchEvent(ActivitiEvent, ActivitiEventListener)"
  })
  public void testDispatchEventWithEventListener() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    ActivitiActivityCancelledEventImpl event = new ActivitiActivityCancelledEventImpl();

    // Act and Assert
    activitiEventSupport.dispatchEvent(event, new BaseEntityEventListener(true));
  }

  /**
   * Test {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent, ActivitiEventListener)} with
   * {@code event}, {@code listener}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent,
   * ActivitiEventListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventSupport.dispatchEvent(ActivitiEvent, ActivitiEventListener)"
  })
  public void testDispatchEventWithEventListener_thenThrowActivitiException() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    ActivitiActivityCancelledEventImpl event = new ActivitiActivityCancelledEventImpl();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> activitiEventSupport.dispatchEvent(event, new ErrorThrowingEventListener()));
  }

  /**
   * Test {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent, ActivitiEventListener)} with
   * {@code event}, {@code listener}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent,
   * ActivitiEventListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventSupport.dispatchEvent(ActivitiEvent, ActivitiEventListener)"
  })
  public void testDispatchEventWithEventListener_whenJavaLangObject_thenDoesNotThrow() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    ActivitiEntityEventImpl event =
        new ActivitiEntityEventImpl(JSONObject.NULL, ActivitiEventType.ENTITY_CREATED);
    Class<Object> entityClass = Object.class;

    // Act and Assert
    activitiEventSupport.dispatchEvent(
        event, new DelegateActivitiEventListener("Class Name", entityClass));
  }

  /**
   * Test {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent)} with {@code event}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventSupport} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiEventSupport.dispatchEvent(ActivitiEvent)"})
  public void testDispatchEventWithEvent_givenActivitiEventSupport() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();

    // Act and Assert
    activitiEventSupport.dispatchEvent(new ActivitiActivityCancelledEventImpl());
  }

  /**
   * Test {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent)} with {@code event}.
   *
   * <ul>
   *   <li>Given {@code ENTITY_CREATED}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiEventSupport.dispatchEvent(ActivitiEvent)"})
  public void testDispatchEventWithEvent_givenEntityCreated_thenDoesNotThrow() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    activitiEventSupport.addEventListener(new BaseEntityEventListener(false));

    ActivitiActivityCancelledEventImpl event = new ActivitiActivityCancelledEventImpl();
    event.setActivityId("42");
    event.setActivityName("Activity Name");
    event.setActivityType("Activity Type");
    event.setBehaviorClass("Behavior Class");
    event.setCause(JSONObject.NULL);
    event.setExecutionId("42");
    event.setProcessDefinitionId("42");
    event.setProcessInstanceId("42");
    event.setReason("Just cause");
    event.setType(ActivitiEventType.ENTITY_CREATED);

    // Act and Assert
    activitiEventSupport.dispatchEvent(event);
  }

  /**
   * Test {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent)} with {@code event}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiEventSupport.dispatchEvent(ActivitiEvent)"})
  public void testDispatchEventWithEvent_givenNull() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    activitiEventSupport.addEventListener(new BaseEntityEventListener(false));

    ActivitiActivityCancelledEventImpl event = new ActivitiActivityCancelledEventImpl();
    event.setActivityId("42");
    event.setActivityName("Activity Name");
    event.setActivityType("Activity Type");
    event.setBehaviorClass("Behavior Class");
    event.setCause(JSONObject.NULL);
    event.setExecutionId("42");
    event.setProcessDefinitionId("42");
    event.setProcessInstanceId("42");
    event.setReason("Just cause");
    event.setType(null);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> activitiEventSupport.dispatchEvent(event));
  }

  /**
   * Test {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent)} with {@code event}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventSupport#dispatchEvent(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiEventSupport.dispatchEvent(ActivitiEvent)"})
  public void testDispatchEventWithEvent_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    activitiEventSupport.addEventListener(new BaseEntityEventListener(false));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> activitiEventSupport.dispatchEvent(null));
  }

  /**
   * Test {@link ActivitiEventSupport#addTypedEventListener(ActivitiEventListener,
   * ActivitiEventType)}.
   *
   * <p>Method under test: {@link ActivitiEventSupport#addTypedEventListener(ActivitiEventListener,
   * ActivitiEventType)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventSupport.addTypedEventListener(ActivitiEventListener, ActivitiEventType)"
  })
  public void testAddTypedEventListener() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    BaseEntityEventListener listener = new BaseEntityEventListener(true);

    // Act
    activitiEventSupport.addTypedEventListener(listener, ActivitiEventType.ENTITY_CREATED);

    // Assert
    Map<ActivitiEventType, List<ActivitiEventListener>> activitiEventTypeListMap =
        activitiEventSupport.typedListeners;
    assertEquals(1, activitiEventTypeListMap.size());
    List<ActivitiEventListener> getResult =
        activitiEventTypeListMap.get(ActivitiEventType.ENTITY_CREATED);
    assertEquals(1, getResult.size());
    assertSame(listener, getResult.get(0));
  }

  /**
   * Test {@link ActivitiEventSupport#addTypedEventListener(ActivitiEventListener,
   * ActivitiEventType)}.
   *
   * <p>Method under test: {@link ActivitiEventSupport#addTypedEventListener(ActivitiEventListener,
   * ActivitiEventType)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventSupport.addTypedEventListener(ActivitiEventListener, ActivitiEventType)"
  })
  public void testAddTypedEventListener2() {
    // Arrange
    ActivitiEventSupport activitiEventSupport = new ActivitiEventSupport();
    BaseEntityEventListener listenerToAdd = new BaseEntityEventListener(true);
    activitiEventSupport.addEventListener(listenerToAdd, ActivitiEventType.ENTITY_CREATED);
    BaseEntityEventListener listener = new BaseEntityEventListener(true);

    // Act
    activitiEventSupport.addTypedEventListener(listener, ActivitiEventType.ENTITY_CREATED);

    // Assert
    Map<ActivitiEventType, List<ActivitiEventListener>> activitiEventTypeListMap =
        activitiEventSupport.typedListeners;
    assertEquals(1, activitiEventTypeListMap.size());
    List<ActivitiEventListener> getResult =
        activitiEventTypeListMap.get(ActivitiEventType.ENTITY_CREATED);
    assertEquals(2, getResult.size());
    ActivitiEventListener getResult2 = getResult.get(0);
    assertTrue(getResult2 instanceof BaseEntityEventListener);
    assertSame(listenerToAdd, getResult2);
    assertSame(listener, getResult.get(1));
  }
}
