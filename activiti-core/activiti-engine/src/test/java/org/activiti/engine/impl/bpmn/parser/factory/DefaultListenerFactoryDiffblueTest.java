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
   * Method under test:
   * {@link DefaultListenerFactory#createClassDelegateEventListener(EventListener)}
   */
  @Test
  public void testCreateClassDelegateEventListener() {
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
   * Method under test:
   * {@link DefaultListenerFactory#createClassDelegateEventListener(EventListener)}
   */
  @Test
  public void testCreateClassDelegateEventListener2() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();

    EventListener eventListener = new EventListener();
    eventListener.setEntityType("Event Listener");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> defaultListenerFactory.createClassDelegateEventListener(eventListener));
  }

  /**
   * Method under test:
   * {@link DefaultListenerFactory#createClassDelegateEventListener(EventListener)}
   */
  @Test
  public void testCreateClassDelegateEventListener3() {
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
   * Method under test:
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}
   */
  @Test
  public void testCreateEventThrowingEventListener() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> defaultListenerFactory.createEventThrowingEventListener(new EventListener()));
  }

  /**
   * Method under test:
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}
   */
  @Test
  public void testCreateEventThrowingEventListener2() {
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
   * Method under test:
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}
   */
  @Test
  public void testCreateEventThrowingEventListener3() {
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
   * Method under test:
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}
   */
  @Test
  public void testCreateEventThrowingEventListener4() {
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
   * Method under test:
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}
   */
  @Test
  public void testCreateEventThrowingEventListener5() {
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
   * Method under test:
   * {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}
   */
  @Test
  public void testCreateEventThrowingEventListener6() {
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
   * Method under test: {@link DefaultListenerFactory#getEntityType(String)}
   */
  @Test
  public void testGetEntityType() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> defaultListenerFactory.getEntityType("Entity Type"));
    assertNull(defaultListenerFactory.getEntityType(null));
  }

  /**
   * Method under test: {@link DefaultListenerFactory#getEntityType(String)}
   */
  @Test
  public void testGetEntityType2() {
    // Arrange and Act
    Class<?> actualEntityType = defaultListenerFactory.getEntityType("task");

    // Assert
    Class<Task> expectedEntityType = Task.class;
    assertEquals(expectedEntityType, actualEntityType);
  }

  /**
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
}
