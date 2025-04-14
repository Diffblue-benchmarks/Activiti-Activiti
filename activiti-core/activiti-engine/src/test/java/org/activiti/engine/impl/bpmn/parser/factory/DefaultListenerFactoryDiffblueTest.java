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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.List;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.EventListener;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.bpmn.helper.ClassDelegateFactory;
import org.activiti.engine.impl.bpmn.helper.DefaultClassDelegateFactory;
import org.activiti.engine.impl.bpmn.helper.DelegateActivitiEventListener;
import org.activiti.engine.impl.bpmn.helper.DelegateExpressionActivitiEventListener;
import org.activiti.engine.impl.bpmn.listener.DelegateExpressionTransactionDependentExecutionListener;
import org.activiti.engine.impl.delegate.BpmnMessagePayloadMappingProviderFactory;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class DefaultListenerFactoryDiffblueTest {
  /**
   * Test {@link DefaultListenerFactory#DefaultListenerFactory()}.
   * <p>
   * Method under test: {@link DefaultListenerFactory#DefaultListenerFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultListenerFactory.<init>()"})
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
   * Test {@link DefaultListenerFactory#DefaultListenerFactory(ClassDelegateFactory)}.
   * <p>
   * Method under test: {@link DefaultListenerFactory#DefaultListenerFactory(ClassDelegateFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultListenerFactory.<init>(ClassDelegateFactory)"})
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
   * Test {@link DefaultListenerFactory#createTransactionDependentDelegateExpressionExecutionListener(ActivitiListener)}.
   * <p>
   * Method under test: {@link DefaultListenerFactory#createTransactionDependentDelegateExpressionExecutionListener(ActivitiListener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "DelegateExpressionTransactionDependentExecutionListener DefaultListenerFactory.createTransactionDependentDelegateExpressionExecutionListener(ActivitiListener)"})
  public void testCreateTransactionDependentDelegateExpressionExecutionListener() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setExpressionManager(expressionManager);

    // Act
    DelegateExpressionTransactionDependentExecutionListener actualCreateTransactionDependentDelegateExpressionExecutionListenerResult = defaultListenerFactory
        .createTransactionDependentDelegateExpressionExecutionListener(new ActivitiListener());

    // Assert
    verify(expressionManager).createExpression(isNull());
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertEquals("null", actualCreateTransactionDependentDelegateExpressionExecutionListenerResult.getExpressionText());
  }

  /**
   * Test {@link DefaultListenerFactory#createClassDelegateEventListener(EventListener)}.
   * <ul>
   *   <li>Then return {@link DelegateActivitiEventListener}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultListenerFactory#createClassDelegateEventListener(EventListener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivitiEventListener DefaultListenerFactory.createClassDelegateEventListener(EventListener)"})
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
   * Test {@link DefaultListenerFactory#createClassDelegateEventListener(EventListener)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultListenerFactory#createClassDelegateEventListener(EventListener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivitiEventListener DefaultListenerFactory.createClassDelegateEventListener(EventListener)"})
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
   * Test {@link DefaultListenerFactory#createDelegateExpressionEventListener(EventListener)}.
   * <p>
   * Method under test: {@link DefaultListenerFactory#createDelegateExpressionEventListener(EventListener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ActivitiEventListener DefaultListenerFactory.createDelegateExpressionEventListener(EventListener)"})
  public void testCreateDelegateExpressionEventListener() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setExpressionManager(expressionManager);

    EventListener eventListener = new EventListener();
    eventListener.setEntityType(null);

    // Act
    ActivitiEventListener actualCreateDelegateExpressionEventListenerResult = defaultListenerFactory
        .createDelegateExpressionEventListener(eventListener);

    // Assert
    verify(expressionManager).createExpression(isNull());
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertTrue(actualCreateDelegateExpressionEventListenerResult instanceof DelegateExpressionActivitiEventListener);
    assertFalse(actualCreateDelegateExpressionEventListenerResult.isFailOnException());
  }

  /**
   * Test {@link DefaultListenerFactory#createDelegateExpressionEventListener(EventListener)}.
   * <p>
   * Method under test: {@link DefaultListenerFactory#createDelegateExpressionEventListener(EventListener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ActivitiEventListener DefaultListenerFactory.createDelegateExpressionEventListener(EventListener)"})
  public void testCreateDelegateExpressionEventListener2() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setExpressionManager(expressionManager);

    EventListener eventListener = new EventListener();
    eventListener.setEntityType("Entity Type");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> defaultListenerFactory.createDelegateExpressionEventListener(eventListener));
    verify(expressionManager).createExpression(isNull());
    verify(expressionManager).setCustomFunctionProviders(isNull());
  }

  /**
   * Test {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivitiEventListener DefaultListenerFactory.createEventThrowingEventListener(EventListener)"})
  public void testCreateEventThrowingEventListener_thenThrowActivitiIllegalArgumentException() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Class DefaultListenerFactory.getEntityType(String)"})
  public void testGetEntityType_whenEntityType_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DefaultListenerFactory()).getEntityType("Entity Type"));
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Class DefaultListenerFactory.getEntityType(String)"})
  public void testGetEntityType_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new DefaultListenerFactory()).getEntityType(null));
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Class DefaultListenerFactory.getEntityType(String)"})
  public void testGetEntityType_whenTask_thenReturnTask() {
    // Arrange and Act
    Class<?> actualEntityType = (new DefaultListenerFactory()).getEntityType("task");

    // Assert
    Class<Task> expectedEntityType = Task.class;
    assertEquals(expectedEntityType, actualEntityType);
  }
}
