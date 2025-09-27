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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.EventListener;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.core.el.ActivitiElContext;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.CustomPropertiesResolver;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.delegate.TransactionDependentTaskListener;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.bpmn.helper.ClassDelegate;
import org.activiti.engine.impl.bpmn.helper.ClassDelegateFactory;
import org.activiti.engine.impl.bpmn.helper.DefaultClassDelegateFactory;
import org.activiti.engine.impl.bpmn.helper.DelegateActivitiEventListener;
import org.activiti.engine.impl.bpmn.helper.DelegateExpressionActivitiEventListener;
import org.activiti.engine.impl.bpmn.listener.DelegateExpressionCustomPropertiesResolver;
import org.activiti.engine.impl.bpmn.listener.DelegateExpressionTransactionDependentExecutionListener;
import org.activiti.engine.impl.bpmn.listener.DelegateExpressionTransactionDependentTaskListener;
import org.activiti.engine.impl.bpmn.listener.ExpressionCustomPropertiesResolver;
import org.activiti.engine.impl.bpmn.listener.ExpressionExecutionListener;
import org.activiti.engine.impl.delegate.BpmnMessagePayloadMappingProviderFactory;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.task.Task;
import org.activiti.examples.bpmn.executionlistener.MyCustomPropertiesResolver;
import org.activiti.examples.bpmn.tasklistener.CurrentTaskTransactionDependentTaskListener;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class DefaultListenerFactoryDiffblueTest {
  /**
   * Test {@link DefaultListenerFactory#DefaultListenerFactory()}.
   *
   * <p>Method under test: {@link DefaultListenerFactory#DefaultListenerFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultListenerFactory.<init>()"})
  public void testNewDefaultListenerFactory() {
    // Arrange and Act
    DefaultListenerFactory actualDefaultListenerFactory = new DefaultListenerFactory();

    // Assert
    assertTrue(
        actualDefaultListenerFactory.getMessageExecutionContextFactory()
            instanceof DefaultMessageExecutionContextFactory);
    assertTrue(
        actualDefaultListenerFactory.getMessagePayloadMappingProviderFactory()
            instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(actualDefaultListenerFactory.getExpressionManager());
  }

  /**
   * Test {@link DefaultListenerFactory#DefaultListenerFactory(ClassDelegateFactory)}.
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#DefaultListenerFactory(ClassDelegateFactory)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultListenerFactory.<init>(ClassDelegateFactory)"})
  public void testNewDefaultListenerFactory2() {
    // Arrange and Act
    DefaultListenerFactory actualDefaultListenerFactory =
        new DefaultListenerFactory(new DefaultClassDelegateFactory());

    // Assert
    assertTrue(
        actualDefaultListenerFactory.getMessageExecutionContextFactory()
            instanceof DefaultMessageExecutionContextFactory);
    assertTrue(
        actualDefaultListenerFactory.getMessagePayloadMappingProviderFactory()
            instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(actualDefaultListenerFactory.getExpressionManager());
  }

  /**
   * Test {@link DefaultListenerFactory#createClassDelegateTaskListener(ActivitiListener)}.
   *
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).
   *   <li>Then return {@link ClassDelegate}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createClassDelegateTaskListener(ActivitiListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TaskListener DefaultListenerFactory.createClassDelegateTaskListener(ActivitiListener)"
  })
  public void testCreateClassDelegateTaskListener_whenActivitiListener_thenReturnClassDelegate() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();

    // Act
    TaskListener actualCreateClassDelegateTaskListenerResult =
        defaultListenerFactory.createClassDelegateTaskListener(new ActivitiListener());

    // Assert
    assertTrue(actualCreateClassDelegateTaskListenerResult instanceof ClassDelegate);
    assertNull(((ClassDelegate) actualCreateClassDelegateTaskListenerResult).getClassName());
    assertNull(
        ((ClassDelegate) actualCreateClassDelegateTaskListenerResult)
            .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link
   * DefaultListenerFactory#createTransactionDependentDelegateExpressionTaskListener(ActivitiListener)}.
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createTransactionDependentDelegateExpressionTaskListener(ActivitiListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "TransactionDependentTaskListener DefaultListenerFactory.createTransactionDependentDelegateExpressionTaskListener(ActivitiListener)"
  })
  public void testCreateTransactionDependentDelegateExpressionTaskListener() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any()))
        .thenReturn(new FixedValue(new CurrentTaskTransactionDependentTaskListener()));
    doNothing()
        .when(expressionManager)
        .setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultListenerFactory defaultListenerFactory =
        new DefaultListenerFactory(new DefaultClassDelegateFactory());
    defaultListenerFactory.setExpressionManager(expressionManager);

    // Act
    TransactionDependentTaskListener
        actualCreateTransactionDependentDelegateExpressionTaskListenerResult =
            defaultListenerFactory.createTransactionDependentDelegateExpressionTaskListener(
                new ActivitiListener());
    org.activiti.bpmn.model.Task task = new org.activiti.bpmn.model.Task();
    HashMap<String, Object> executionVariables = new HashMap<>();
    actualCreateTransactionDependentDelegateExpressionTaskListenerResult.notify(
        "42", "42", task, executionVariables, new HashMap<>());

    // Assert
    verify(expressionManager).createExpression(null);
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertTrue(
        actualCreateTransactionDependentDelegateExpressionTaskListenerResult
            instanceof DelegateExpressionTransactionDependentTaskListener);
  }

  /**
   * Test {@link DefaultListenerFactory#createClassDelegateExecutionListener(ActivitiListener)}.
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createClassDelegateExecutionListener(ActivitiListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionListener DefaultListenerFactory.createClassDelegateExecutionListener(ActivitiListener)"
  })
  public void testCreateClassDelegateExecutionListener() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setExpressionManager(new ExpressionManager());

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("Expression");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    // Act
    ExecutionListener actualCreateClassDelegateExecutionListenerResult =
        defaultListenerFactory.createClassDelegateExecutionListener(activitiListener);

    // Assert
    assertTrue(actualCreateClassDelegateExecutionListenerResult instanceof ClassDelegate);
    assertNull(((ClassDelegate) actualCreateClassDelegateExecutionListenerResult).getClassName());
    assertNull(
        ((ClassDelegate) actualCreateClassDelegateExecutionListenerResult)
            .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultListenerFactory#createClassDelegateExecutionListener(ActivitiListener)}.
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createClassDelegateExecutionListener(ActivitiListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionListener DefaultListenerFactory.createClassDelegateExecutionListener(ActivitiListener)"
  })
  public void testCreateClassDelegateExecutionListener2() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("Expression");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    // Act
    ExecutionListener actualCreateClassDelegateExecutionListenerResult =
        defaultListenerFactory.createClassDelegateExecutionListener(activitiListener);

    // Assert
    assertTrue(actualCreateClassDelegateExecutionListenerResult instanceof ClassDelegate);
    assertNull(((ClassDelegate) actualCreateClassDelegateExecutionListenerResult).getClassName());
    assertNull(
        ((ClassDelegate) actualCreateClassDelegateExecutionListenerResult)
            .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultListenerFactory#createClassDelegateExecutionListener(ActivitiListener)}.
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createClassDelegateExecutionListener(ActivitiListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionListener DefaultListenerFactory.createClassDelegateExecutionListener(ActivitiListener)"
  })
  public void testCreateClassDelegateExecutionListener3() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setExpressionManager(new ExpressionManager());

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    // Act
    ExecutionListener actualCreateClassDelegateExecutionListenerResult =
        defaultListenerFactory.createClassDelegateExecutionListener(activitiListener);

    // Assert
    assertTrue(actualCreateClassDelegateExecutionListenerResult instanceof ClassDelegate);
    assertNull(((ClassDelegate) actualCreateClassDelegateExecutionListenerResult).getClassName());
    assertNull(
        ((ClassDelegate) actualCreateClassDelegateExecutionListenerResult)
            .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultListenerFactory#createClassDelegateExecutionListener(ActivitiListener)}.
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createClassDelegateExecutionListener(ActivitiListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionListener DefaultListenerFactory.createClassDelegateExecutionListener(ActivitiListener)"
  })
  public void testCreateClassDelegateExecutionListener4() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("Expression");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    // Act
    ExecutionListener actualCreateClassDelegateExecutionListenerResult =
        defaultListenerFactory.createClassDelegateExecutionListener(activitiListener);

    // Assert
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualCreateClassDelegateExecutionListenerResult instanceof ClassDelegate);
    assertNull(((ClassDelegate) actualCreateClassDelegateExecutionListenerResult).getClassName());
    assertNull(
        ((ClassDelegate) actualCreateClassDelegateExecutionListenerResult)
            .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultListenerFactory#createClassDelegateExecutionListener(ActivitiListener)}.
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createClassDelegateExecutionListener(ActivitiListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionListener DefaultListenerFactory.createClassDelegateExecutionListener(ActivitiListener)"
  })
  public void testCreateClassDelegateExecutionListener5() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doThrow(new ActivitiIllegalArgumentException("An error occurred"))
        .when(customFunctionProvider)
        .addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("Expression");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    // Act
    ExecutionListener actualCreateClassDelegateExecutionListenerResult =
        defaultListenerFactory.createClassDelegateExecutionListener(activitiListener);

    // Assert
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualCreateClassDelegateExecutionListenerResult instanceof ClassDelegate);
    assertNull(((ClassDelegate) actualCreateClassDelegateExecutionListenerResult).getClassName());
    assertNull(
        ((ClassDelegate) actualCreateClassDelegateExecutionListenerResult)
            .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultListenerFactory#createClassDelegateExecutionListener(ActivitiListener)}.
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createClassDelegateExecutionListener(ActivitiListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionListener DefaultListenerFactory.createClassDelegateExecutionListener(ActivitiListener)"
  })
  public void testCreateClassDelegateExecutionListener6() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();
    defaultListenerFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("Expression");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    // Act
    ExecutionListener actualCreateClassDelegateExecutionListenerResult =
        defaultListenerFactory.createClassDelegateExecutionListener(activitiListener);

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualCreateClassDelegateExecutionListenerResult instanceof ClassDelegate);
    assertNull(((ClassDelegate) actualCreateClassDelegateExecutionListenerResult).getClassName());
    assertNull(
        ((ClassDelegate) actualCreateClassDelegateExecutionListenerResult)
            .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultListenerFactory#createClassDelegateExecutionListener(ActivitiListener)}.
   *
   * <ul>
   *   <li>Given {@link DefaultListenerFactory#DefaultListenerFactory()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createClassDelegateExecutionListener(ActivitiListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionListener DefaultListenerFactory.createClassDelegateExecutionListener(ActivitiListener)"
  })
  public void testCreateClassDelegateExecutionListener_givenDefaultListenerFactory() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    // Act
    ExecutionListener actualCreateClassDelegateExecutionListenerResult =
        defaultListenerFactory.createClassDelegateExecutionListener(activitiListener);

    // Assert
    assertTrue(actualCreateClassDelegateExecutionListenerResult instanceof ClassDelegate);
    assertNull(((ClassDelegate) actualCreateClassDelegateExecutionListenerResult).getClassName());
    assertNull(
        ((ClassDelegate) actualCreateClassDelegateExecutionListenerResult)
            .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultListenerFactory#createClassDelegateExecutionListener(ActivitiListener)}.
   *
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createClassDelegateExecutionListener(ActivitiListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionListener DefaultListenerFactory.createClassDelegateExecutionListener(ActivitiListener)"
  })
  public void testCreateClassDelegateExecutionListener_whenActivitiListener() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();

    // Act
    ExecutionListener actualCreateClassDelegateExecutionListenerResult =
        defaultListenerFactory.createClassDelegateExecutionListener(new ActivitiListener());

    // Assert
    assertTrue(actualCreateClassDelegateExecutionListenerResult instanceof ClassDelegate);
    assertNull(((ClassDelegate) actualCreateClassDelegateExecutionListenerResult).getClassName());
    assertNull(
        ((ClassDelegate) actualCreateClassDelegateExecutionListenerResult)
            .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultListenerFactory#createExpressionExecutionListener(ActivitiListener)}.
   *
   * <ul>
   *   <li>Then return {@link ExpressionExecutionListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createExpressionExecutionListener(ActivitiListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionListener DefaultListenerFactory.createExpressionExecutionListener(ActivitiListener)"
  })
  public void testCreateExpressionExecutionListener_thenReturnExpressionExecutionListener() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any()))
        .thenReturn(new FixedValue(JSONObject.NULL));
    doNothing()
        .when(expressionManager)
        .setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultListenerFactory defaultListenerFactory =
        new DefaultListenerFactory(new DefaultClassDelegateFactory());
    defaultListenerFactory.setExpressionManager(expressionManager);

    // Act
    ExecutionListener actualCreateExpressionExecutionListenerResult =
        defaultListenerFactory.createExpressionExecutionListener(new ActivitiListener());
    actualCreateExpressionExecutionListenerResult.notify(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(expressionManager).createExpression(null);
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertTrue(
        actualCreateExpressionExecutionListenerResult instanceof ExpressionExecutionListener);
    assertEquals(
        "null",
        ((ExpressionExecutionListener) actualCreateExpressionExecutionListenerResult)
            .getExpressionText());
  }

  /**
   * Test {@link
   * DefaultListenerFactory#createTransactionDependentDelegateExpressionExecutionListener(ActivitiListener)}.
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createTransactionDependentDelegateExpressionExecutionListener(ActivitiListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DelegateExpressionTransactionDependentExecutionListener DefaultListenerFactory.createTransactionDependentDelegateExpressionExecutionListener(ActivitiListener)"
  })
  public void testCreateTransactionDependentDelegateExpressionExecutionListener() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any()))
        .thenReturn(new FixedValue(JSONObject.NULL));
    doNothing()
        .when(expressionManager)
        .setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultListenerFactory defaultListenerFactory =
        new DefaultListenerFactory(new DefaultClassDelegateFactory());
    defaultListenerFactory.setExpressionManager(expressionManager);

    // Act
    DelegateExpressionTransactionDependentExecutionListener
        actualCreateTransactionDependentDelegateExpressionExecutionListenerResult =
            defaultListenerFactory.createTransactionDependentDelegateExpressionExecutionListener(
                new ActivitiListener());

    // Assert
    verify(expressionManager).createExpression(null);
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertEquals(
        "null",
        actualCreateTransactionDependentDelegateExpressionExecutionListenerResult
            .getExpressionText());
  }

  /**
   * Test {@link DefaultListenerFactory#createClassDelegateEventListener(EventListener)}.
   *
   * <ul>
   *   <li>Then return {@link DelegateActivitiEventListener}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createClassDelegateEventListener(EventListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiEventListener DefaultListenerFactory.createClassDelegateEventListener(EventListener)"
  })
  public void testCreateClassDelegateEventListener_thenReturnDelegateActivitiEventListener() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();

    // Act
    ActivitiEventListener actualCreateClassDelegateEventListenerResult =
        defaultListenerFactory.createClassDelegateEventListener(new EventListener());

    // Assert
    assertTrue(
        actualCreateClassDelegateEventListenerResult instanceof DelegateActivitiEventListener);
    assertFalse(actualCreateClassDelegateEventListenerResult.isFailOnException());
  }

  /**
   * Test {@link DefaultListenerFactory#createClassDelegateEventListener(EventListener)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createClassDelegateEventListener(EventListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiEventListener DefaultListenerFactory.createClassDelegateEventListener(EventListener)"
  })
  public void testCreateClassDelegateEventListener_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();

    EventListener eventListener = new EventListener();
    eventListener.setEntityType("Event Listener");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> defaultListenerFactory.createClassDelegateEventListener(eventListener));
  }

  /**
   * Test {@link DefaultListenerFactory#createDelegateExpressionEventListener(EventListener)}.
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createDelegateExpressionEventListener(EventListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiEventListener DefaultListenerFactory.createDelegateExpressionEventListener(EventListener)"
  })
  public void testCreateDelegateExpressionEventListener() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any()))
        .thenReturn(new FixedValue(JSONObject.NULL));
    doNothing()
        .when(expressionManager)
        .setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultListenerFactory defaultListenerFactory =
        new DefaultListenerFactory(new DefaultClassDelegateFactory());
    defaultListenerFactory.setExpressionManager(expressionManager);

    EventListener eventListener = new EventListener();
    eventListener.setEntityType(null);

    // Act
    ActivitiEventListener actualCreateDelegateExpressionEventListenerResult =
        defaultListenerFactory.createDelegateExpressionEventListener(eventListener);

    // Assert
    verify(expressionManager).createExpression(null);
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertTrue(
        actualCreateDelegateExpressionEventListenerResult
            instanceof DelegateExpressionActivitiEventListener);
    assertFalse(actualCreateDelegateExpressionEventListenerResult.isFailOnException());
  }

  /**
   * Test {@link DefaultListenerFactory#createDelegateExpressionEventListener(EventListener)}.
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createDelegateExpressionEventListener(EventListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiEventListener DefaultListenerFactory.createDelegateExpressionEventListener(EventListener)"
  })
  public void testCreateDelegateExpressionEventListener2() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any()))
        .thenReturn(new FixedValue(JSONObject.NULL));
    doNothing()
        .when(expressionManager)
        .setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultListenerFactory defaultListenerFactory =
        new DefaultListenerFactory(new DefaultClassDelegateFactory());
    defaultListenerFactory.setExpressionManager(expressionManager);

    EventListener eventListener = new EventListener();
    eventListener.setEntityType("Entity Type");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> defaultListenerFactory.createDelegateExpressionEventListener(eventListener));
    verify(expressionManager).createExpression(null);
    verify(expressionManager).setCustomFunctionProviders(isNull());
  }

  /**
   * Test {@link DefaultListenerFactory#createEventThrowingEventListener(EventListener)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createEventThrowingEventListener(EventListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ActivitiEventListener DefaultListenerFactory.createEventThrowingEventListener(EventListener)"
  })
  public void testCreateEventThrowingEventListener_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> defaultListenerFactory.createEventThrowingEventListener(new EventListener()));
  }

  /**
   * Test {@link
   * DefaultListenerFactory#createClassDelegateCustomPropertiesResolver(ActivitiListener)}.
   *
   * <ul>
   *   <li>Then return {@link ClassDelegate}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createClassDelegateCustomPropertiesResolver(ActivitiListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CustomPropertiesResolver DefaultListenerFactory.createClassDelegateCustomPropertiesResolver(ActivitiListener)"
  })
  public void testCreateClassDelegateCustomPropertiesResolver_thenReturnClassDelegate() {
    // Arrange
    DefaultListenerFactory defaultListenerFactory = new DefaultListenerFactory();

    // Act
    CustomPropertiesResolver actualCreateClassDelegateCustomPropertiesResolverResult =
        defaultListenerFactory.createClassDelegateCustomPropertiesResolver(new ActivitiListener());

    // Assert
    assertTrue(actualCreateClassDelegateCustomPropertiesResolverResult instanceof ClassDelegate);
    assertNull(
        ((ClassDelegate) actualCreateClassDelegateCustomPropertiesResolverResult).getClassName());
    assertNull(
        ((ClassDelegate) actualCreateClassDelegateCustomPropertiesResolverResult)
            .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultListenerFactory#createExpressionCustomPropertiesResolver(ActivitiListener)}.
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createExpressionCustomPropertiesResolver(ActivitiListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CustomPropertiesResolver DefaultListenerFactory.createExpressionCustomPropertiesResolver(ActivitiListener)"
  })
  public void testCreateExpressionCustomPropertiesResolver() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any()))
        .thenReturn(new FixedValue(new HashMap<>()));
    doNothing()
        .when(expressionManager)
        .setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultListenerFactory defaultListenerFactory =
        new DefaultListenerFactory(new DefaultClassDelegateFactory());
    defaultListenerFactory.setExpressionManager(expressionManager);

    // Act
    CustomPropertiesResolver actualCreateExpressionCustomPropertiesResolverResult =
        defaultListenerFactory.createExpressionCustomPropertiesResolver(new ActivitiListener());
    Map<String, Object> actualCustomPropertiesMap =
        actualCreateExpressionCustomPropertiesResolverResult.getCustomPropertiesMap(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(expressionManager).createExpression(null);
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertTrue(
        actualCreateExpressionCustomPropertiesResolverResult
            instanceof ExpressionCustomPropertiesResolver);
    assertEquals(
        "{}",
        ((ExpressionCustomPropertiesResolver) actualCreateExpressionCustomPropertiesResolverResult)
            .getExpressionText());
    assertTrue(actualCustomPropertiesMap.isEmpty());
  }

  /**
   * Test {@link
   * DefaultListenerFactory#createDelegateExpressionCustomPropertiesResolver(ActivitiListener)}.
   *
   * <p>Method under test: {@link
   * DefaultListenerFactory#createDelegateExpressionCustomPropertiesResolver(ActivitiListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CustomPropertiesResolver DefaultListenerFactory.createDelegateExpressionCustomPropertiesResolver(ActivitiListener)"
  })
  public void testCreateDelegateExpressionCustomPropertiesResolver() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any()))
        .thenReturn(new FixedValue(new MyCustomPropertiesResolver()));
    doNothing()
        .when(expressionManager)
        .setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultListenerFactory defaultListenerFactory =
        new DefaultListenerFactory(new DefaultClassDelegateFactory());
    defaultListenerFactory.setExpressionManager(expressionManager);

    // Act
    CustomPropertiesResolver actualCreateDelegateExpressionCustomPropertiesResolverResult =
        defaultListenerFactory.createDelegateExpressionCustomPropertiesResolver(
            new ActivitiListener());
    Map<String, Object> actualCustomPropertiesMap =
        actualCreateDelegateExpressionCustomPropertiesResolverResult.getCustomPropertiesMap(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(expressionManager).createExpression(null);
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertTrue(
        actualCreateDelegateExpressionCustomPropertiesResolverResult
            instanceof DelegateExpressionCustomPropertiesResolver);
    assertEquals(1, actualCustomPropertiesMap.size());
    assertNull(actualCustomPropertiesMap.get("customProp1"));
  }

  /**
   * Test {@link DefaultListenerFactory#getEntityType(String)}.
   *
   * <ul>
   *   <li>When {@code Entity Type}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultListenerFactory#getEntityType(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Class DefaultListenerFactory.getEntityType(String)"})
  public void testGetEntityType_whenEntityType_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DefaultListenerFactory().getEntityType("Entity Type"));
  }

  /**
   * Test {@link DefaultListenerFactory#getEntityType(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultListenerFactory#getEntityType(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Class DefaultListenerFactory.getEntityType(String)"})
  public void testGetEntityType_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new DefaultListenerFactory().getEntityType(null));
  }

  /**
   * Test {@link DefaultListenerFactory#getEntityType(String)}.
   *
   * <ul>
   *   <li>When {@code task}.
   *   <li>Then return {@link Task}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultListenerFactory#getEntityType(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Class DefaultListenerFactory.getEntityType(String)"})
  public void testGetEntityType_whenTask_thenReturnTask() {
    // Arrange and Act
    Class<?> actualEntityType = new DefaultListenerFactory().getEntityType("task");

    // Assert
    Class<Task> expectedEntityType = Task.class;
    assertEquals(expectedEntityType, actualEntityType);
  }
}
