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
package org.activiti.engine.impl.bpmn.parser.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.EventListener;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.bpmn.helper.ClassDelegateFactory;
import org.activiti.engine.impl.bpmn.helper.DefaultClassDelegateFactory;
import org.activiti.engine.impl.bpmn.helper.DelegateActivitiEventListener;
import org.activiti.engine.impl.bpmn.helper.DelegateExpressionActivitiEventListener;
import org.activiti.engine.impl.bpmn.helper.ErrorThrowingEventListener;
import org.activiti.engine.impl.bpmn.helper.MessageThrowingEventListener;
import org.activiti.engine.impl.bpmn.helper.SignalThrowingEventListener;
import org.activiti.engine.impl.bpmn.listener.DelegateExpressionTransactionDependentExecutionListener;
import org.activiti.engine.impl.delegate.BpmnMessagePayloadMappingProviderFactory;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProviderFactory;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultListenerFactoryDiffblueTest {
  @InjectMocks
  private DefaultListenerFactory defaultListenerFactory;

  /**
   * Test {@link DefaultListenerFactory#DefaultListenerFactory()}.
   * <p>
   * Method under test: {@link DefaultListenerFactory#DefaultListenerFactory()}
   */
  @Test
  public void testNewDefaultListenerFactory() {
    // Arrange and Act
    DefaultListenerFactory actualDefaultListenerFactory = new DefaultListenerFactory();

    // Assert
    assertTrue(actualDefaultListenerFactory
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
    assertTrue(actualDefaultListenerFactory
        .getMessagePayloadMappingProviderFactory() instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(actualDefaultListenerFactory.getExpressionManager());
  }

  /**
   * Test
   * {@link DefaultListenerFactory#DefaultListenerFactory(ClassDelegateFactory)}.
   * <p>
   * Method under test:
   * {@link DefaultListenerFactory#DefaultListenerFactory(ClassDelegateFactory)}
   */
  @Test
  public void testNewDefaultListenerFactory2() {
    // Arrange and Act
    DefaultListenerFactory actualDefaultListenerFactory = new DefaultListenerFactory(new DefaultClassDelegateFactory());

    // Assert
    assertTrue(actualDefaultListenerFactory
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
    assertTrue(actualDefaultListenerFactory
        .getMessagePayloadMappingProviderFactory() instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(actualDefaultListenerFactory.getExpressionManager());
  }

  /**
   * Test
   * {@link DefaultListenerFactory#createTransactionDependentDelegateExpressionExecutionListener(ActivitiListener)}.
   * <p>
   * Method under test:
   * {@link DefaultListenerFactory#createTransactionDependentDelegateExpressionExecutionListener(ActivitiListener)}
   */
  @Test
  public void testCreateTransactionDependentDelegateExpressionExecutionListener() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setExpressionManager(new ExpressionManager());
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getImplementation()).thenReturn("Implementation");

    // Act
    DelegateExpressionTransactionDependentExecutionListener actualCreateTransactionDependentDelegateExpressionExecutionListenerResult = defaultListenerFactory
        .createTransactionDependentDelegateExpressionExecutionListener(activitiListener);

    // Assert
    verify(activitiListener).getImplementation();
    assertEquals("Implementation",
        actualCreateTransactionDependentDelegateExpressionExecutionListenerResult.getExpressionText());
  }

  /**
   * Test
   * {@link DefaultListenerFactory#createTransactionDependentDelegateExpressionExecutionListener(ActivitiListener)}.
   * <p>
   * Method under test:
   * {@link DefaultListenerFactory#createTransactionDependentDelegateExpressionExecutionListener(ActivitiListener)}
   */
  @Test
  public void testCreateTransactionDependentDelegateExpressionExecutionListener2() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));

    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setExpressionManager(expressionManager);
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getImplementation()).thenReturn("Implementation");

    // Act
    DelegateExpressionTransactionDependentExecutionListener actualCreateTransactionDependentDelegateExpressionExecutionListenerResult = defaultListenerFactory
        .createTransactionDependentDelegateExpressionExecutionListener(activitiListener);

    // Assert
    verify(activitiListener).getImplementation();
    verify(expressionManager).createExpression(eq("Implementation"));
    assertEquals("null", actualCreateTransactionDependentDelegateExpressionExecutionListenerResult.getExpressionText());
  }

  /**
   * Test
   * {@link DefaultListenerFactory#createClassDelegateEventListener(EventListener)}.
   * <p>
   * Method under test:
   * {@link DefaultListenerFactory#createClassDelegateEventListener(EventListener)}
   */
  @Test
  public void testCreateClassDelegateEventListener() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setMessagePayloadMappingProviderFactory(mock(MessagePayloadMappingProviderFactory.class));

    // Act
    ActivitiEventListener actualCreateClassDelegateEventListenerResult = defaultListenerFactory
        .createClassDelegateEventListener(new EventListener());

    // Assert
    assertTrue(actualCreateClassDelegateEventListenerResult instanceof DelegateActivitiEventListener);
    assertFalse(actualCreateClassDelegateEventListenerResult.isFailOnException());
  }

  /**
   * Test
   * {@link DefaultListenerFactory#createClassDelegateEventListener(EventListener)}.
   * <ul>
   *   <li>Then return {@link DelegateActivitiEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultListenerFactory#createClassDelegateEventListener(EventListener)}
   */
  @Test
  public void testCreateClassDelegateEventListener_thenReturnDelegateActivitiEventListener() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();

    // Act
    ActivitiEventListener actualCreateClassDelegateEventListenerResult = defaultListenerFactory
        .createClassDelegateEventListener(new EventListener());

    // Assert
    assertTrue(actualCreateClassDelegateEventListenerResult instanceof DelegateActivitiEventListener);
    assertFalse(actualCreateClassDelegateEventListenerResult.isFailOnException());
  }

  /**
   * Test
   * {@link DefaultListenerFactory#createClassDelegateEventListener(EventListener)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultListenerFactory#createClassDelegateEventListener(EventListener)}
   */
  @Test
  public void testCreateClassDelegateEventListener_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();

    EventListener eventListener = new EventListener();
    eventListener.setEntityType("Event Listener");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> defaultListenerFactory.createClassDelegateEventListener(eventListener));
  }

  /**
   * Test
   * {@link DefaultListenerFactory#createDelegateExpressionEventListener(EventListener)}.
   * <p>
   * Method under test:
   * {@link DefaultListenerFactory#createDelegateExpressionEventListener(EventListener)}
   */
  @Test
  public void testCreateDelegateExpressionEventListener() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setExpressionManager(new ExpressionManager());
    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getImplementation()).thenReturn("Implementation");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> defaultListenerFactory.createDelegateExpressionEventListener(eventListener));
    verify(eventListener).getEntityType();
    verify(eventListener).getImplementation();
  }

  /**
   * Test
   * {@link DefaultListenerFactory#createDelegateExpressionEventListener(EventListener)}.
   * <p>
   * Method under test:
   * {@link DefaultListenerFactory#createDelegateExpressionEventListener(EventListener)}
   */
  @Test
  public void testCreateDelegateExpressionEventListener2() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));

    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setExpressionManager(expressionManager);
    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getImplementation()).thenReturn("Implementation");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> defaultListenerFactory.createDelegateExpressionEventListener(eventListener));
    verify(eventListener).getEntityType();
    verify(eventListener).getImplementation();
    verify(expressionManager).createExpression(eq("Implementation"));
  }

  /**
   * Test
   * {@link DefaultListenerFactory#createDelegateExpressionEventListener(EventListener)}.
   * <p>
   * Method under test:
   * {@link DefaultListenerFactory#createDelegateExpressionEventListener(EventListener)}
   */
  @Test
  public void testCreateDelegateExpressionEventListener3() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));

    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setExpressionManager(expressionManager);
    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn(null);
    when(eventListener.getImplementation()).thenReturn("Implementation");

    // Act
    ActivitiEventListener actualCreateDelegateExpressionEventListenerResult = defaultListenerFactory
        .createDelegateExpressionEventListener(eventListener);

    // Assert
    verify(eventListener).getEntityType();
    verify(eventListener).getImplementation();
    verify(expressionManager).createExpression(eq("Implementation"));
    assertTrue(actualCreateDelegateExpressionEventListenerResult instanceof DelegateExpressionActivitiEventListener);
    assertFalse(actualCreateDelegateExpressionEventListenerResult.isFailOnException());
  }

  /**
   * Test
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}.
   * <p>
   * Method under test:
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}
   */
  @Test
  public void testCreateEventThrowingEventListener() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setMessagePayloadMappingProviderFactory(mock(MessagePayloadMappingProviderFactory.class));

    EventListener eventListener = new EventListener();
    eventListener.setEntityType("throwSignalEvent");
    eventListener.setImplementationType("throwSignalEvent");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> defaultListenerFactory.createEventThrowingEventListener(eventListener));
  }

  /**
   * Test
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}.
   * <ul>
   *   <li>Given {@code throwGlobalSignalEvent}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}
   */
  @Test
  public void testCreateEventThrowingEventListener_givenThrowGlobalSignalEvent() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setMessagePayloadMappingProviderFactory(mock(MessagePayloadMappingProviderFactory.class));

    EventListener eventListener = new EventListener();
    eventListener.setImplementationType("throwGlobalSignalEvent");

    // Act
    ActivitiEventListener actualCreateEventThrowingEventListenerResult = defaultListenerFactory
        .createEventThrowingEventListener(eventListener);

    // Assert
    assertTrue(actualCreateEventThrowingEventListenerResult instanceof SignalThrowingEventListener);
    assertTrue(actualCreateEventThrowingEventListenerResult.isFailOnException());
  }

  /**
   * Test
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}.
   * <ul>
   *   <li>Then return {@link ErrorThrowingEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}
   */
  @Test
  public void testCreateEventThrowingEventListener_thenReturnErrorThrowingEventListener() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setMessagePayloadMappingProviderFactory(mock(MessagePayloadMappingProviderFactory.class));

    EventListener eventListener = new EventListener();
    eventListener.setImplementationType("throwErrorEvent");

    // Act
    ActivitiEventListener actualCreateEventThrowingEventListenerResult = defaultListenerFactory
        .createEventThrowingEventListener(eventListener);

    // Assert
    assertTrue(actualCreateEventThrowingEventListenerResult instanceof ErrorThrowingEventListener);
    assertTrue(actualCreateEventThrowingEventListenerResult.isFailOnException());
  }

  /**
   * Test
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}.
   * <ul>
   *   <li>Then return {@link MessageThrowingEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}
   */
  @Test
  public void testCreateEventThrowingEventListener_thenReturnMessageThrowingEventListener() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setMessagePayloadMappingProviderFactory(mock(MessagePayloadMappingProviderFactory.class));

    EventListener eventListener = new EventListener();
    eventListener.setImplementationType("throwMessageEvent");

    // Act
    ActivitiEventListener actualCreateEventThrowingEventListenerResult = defaultListenerFactory
        .createEventThrowingEventListener(eventListener);

    // Assert
    assertTrue(actualCreateEventThrowingEventListenerResult instanceof MessageThrowingEventListener);
    assertTrue(actualCreateEventThrowingEventListenerResult.isFailOnException());
  }

  /**
   * Test
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}.
   * <ul>
   *   <li>Then return {@link SignalThrowingEventListener}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}
   */
  @Test
  public void testCreateEventThrowingEventListener_thenReturnSignalThrowingEventListener() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setMessagePayloadMappingProviderFactory(mock(MessagePayloadMappingProviderFactory.class));

    EventListener eventListener = new EventListener();
    eventListener.setImplementationType("throwSignalEvent");

    // Act
    ActivitiEventListener actualCreateEventThrowingEventListenerResult = defaultListenerFactory
        .createEventThrowingEventListener(eventListener);

    // Assert
    assertTrue(actualCreateEventThrowingEventListenerResult instanceof SignalThrowingEventListener);
    assertTrue(actualCreateEventThrowingEventListenerResult.isFailOnException());
  }

  /**
   * Test
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}.
   * <ul>
   *   <li>When {@link EventListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}
   */
  @Test
  public void testCreateEventThrowingEventListener_whenEventListener() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> defaultListenerFactory.createEventThrowingEventListener(new EventListener()));
  }

  /**
   * Test {@link DefaultListenerFactory#getEntityType(String)}.
   * <ul>
   *   <li>When {@code Entity Type}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultListenerFactory#getEntityType(String)}
   */
  @Test
  public void testGetEntityType_whenEntityType_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> defaultListenerFactory.getEntityType("Entity Type"));
  }

  /**
   * Test {@link DefaultListenerFactory#getEntityType(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultListenerFactory#getEntityType(String)}
   */
  @Test
  public void testGetEntityType_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(defaultListenerFactory.getEntityType(null));
  }

  /**
   * Test {@link DefaultListenerFactory#getEntityType(String)}.
   * <ul>
   *   <li>When {@code task}.</li>
   *   <li>Then return {@link Task}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultListenerFactory#getEntityType(String)}
   */
  @Test
  public void testGetEntityType_whenTask_thenReturnTask() {
    // Arrange and Act
    Class<?> actualEntityType = defaultListenerFactory.getEntityType("task");

    // Assert
    Class<Task> expectedEntityType = Task.class;
    assertEquals(expectedEntityType, actualEntityType);
  }
}
