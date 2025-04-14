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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ch.qos.logback.core.util.COWArrayList;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.CompensateEventDefinition;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.IntermediateCatchEvent;
import org.activiti.bpmn.model.ManualTask;
import org.activiti.bpmn.model.MapExceptionEntry;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.ReceiveTask;
import org.activiti.bpmn.model.ScriptTask;
import org.activiti.bpmn.model.SendTask;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.Task;
import org.activiti.bpmn.model.TaskWithFieldExtensions;
import org.activiti.bpmn.model.TerminateEventDefinition;
import org.activiti.bpmn.model.ThrowEvent;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.bpmn.model.Transaction;
import org.activiti.bpmn.model.UserTask;
import org.activiti.core.el.ActivitiElContext;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.BoundaryMessageEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.CallActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.IntermediateCatchMessageEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.IntermediateThrowMessageEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.ServiceTaskDelegateExpressionActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ServiceTaskExpressionActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ShellActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.TerminateEndEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ThrowMessageEndEventActivityBehavior;
import org.activiti.engine.impl.bpmn.helper.ClassDelegate;
import org.activiti.engine.impl.bpmn.helper.ClassDelegateFactory;
import org.activiti.engine.impl.bpmn.helper.DefaultClassDelegateFactory;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.delegate.ActivityBehavior;
import org.activiti.engine.impl.delegate.BpmnMessagePayloadMappingProvider;
import org.activiti.engine.impl.delegate.BpmnMessagePayloadMappingProviderFactory;
import org.activiti.engine.impl.delegate.DefaultThrowMessageJavaDelegate;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProviderFactory;
import org.activiti.engine.impl.delegate.ThrowMessageDelegate;
import org.activiti.engine.impl.delegate.ThrowMessageDelegateExpression;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.test.bpmn.event.message.MessageThrowCatchEventTest;
import org.activiti.engine.test.bpmn.event.message.MessageThrowCatchEventTest.TestThrowMessageDelegate;
import org.activiti.engine.test.bpmn.event.message.MessageThrowCatchEventTest.TestThrowMessageDelegateFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class DefaultActivityBehaviorFactoryDiffblueTest {
  /**
   * Test {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory()}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultActivityBehaviorFactory.<init>()"})
  public void testNewDefaultActivityBehaviorFactory() {
    // Arrange and Act
    DefaultActivityBehaviorFactory actualDefaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Assert
    assertTrue(actualDefaultActivityBehaviorFactory
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
    assertTrue(actualDefaultActivityBehaviorFactory
        .getMessagePayloadMappingProviderFactory() instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(actualDefaultActivityBehaviorFactory.getExpressionManager());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory(ClassDelegateFactory)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory(ClassDelegateFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultActivityBehaviorFactory.<init>(ClassDelegateFactory)"})
  public void testNewDefaultActivityBehaviorFactory2() {
    // Arrange and Act
    DefaultActivityBehaviorFactory actualDefaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory(
        new DefaultClassDelegateFactory());

    // Assert
    assertTrue(actualDefaultActivityBehaviorFactory
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
    assertTrue(actualDefaultActivityBehaviorFactory
        .getMessagePayloadMappingProviderFactory() instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(actualDefaultActivityBehaviorFactory.getExpressionManager());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createTaskActivityBehavior(Task)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createTaskActivityBehavior(Task)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.TaskActivityBehavior DefaultActivityBehaviorFactory.createTaskActivityBehavior(Task)"})
  public void testCreateTaskActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertNull(
        defaultActivityBehaviorFactory.createTaskActivityBehavior(new Task()).getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createManualTaskActivityBehavior(ManualTask)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createManualTaskActivityBehavior(ManualTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.ManualTaskActivityBehavior DefaultActivityBehaviorFactory.createManualTaskActivityBehavior(ManualTask)"})
  public void testCreateManualTaskActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createManualTaskActivityBehavior(new ManualTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createReceiveTaskActivityBehavior(ReceiveTask)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createReceiveTaskActivityBehavior(ReceiveTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.ReceiveTaskActivityBehavior DefaultActivityBehaviorFactory.createReceiveTaskActivityBehavior(ReceiveTask)"})
  public void testCreateReceiveTaskActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createReceiveTaskActivityBehavior(new ReceiveTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createUserTaskActivityBehavior(UserTask)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createUserTaskActivityBehavior(UserTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior DefaultActivityBehaviorFactory.createUserTaskActivityBehavior(UserTask)"})
  public void testCreateUserTaskActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory(
        new DefaultClassDelegateFactory());

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createUserTaskActivityBehavior(new UserTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createUserTaskActivityBehavior(UserTask)}.
   * <ul>
   *   <li>Given {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createUserTaskActivityBehavior(UserTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior DefaultActivityBehaviorFactory.createUserTaskActivityBehavior(UserTask)"})
  public void testCreateUserTaskActivityBehavior_givenDefaultActivityBehaviorFactory() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createUserTaskActivityBehavior(new UserTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Expression DefaultActivityBehaviorFactory.getSkipExpressionFromServiceTask(ServiceTask)"})
  public void testGetSkipExpressionFromServiceTask() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("not empty");

    // Act
    Expression actualSkipExpressionFromServiceTask = defaultActivityBehaviorFactory
        .getSkipExpressionFromServiceTask(serviceTask);

    // Assert
    assertTrue(actualSkipExpressionFromServiceTask instanceof JuelExpression);
    assertEquals("not empty", actualSkipExpressionFromServiceTask.getExpressionText());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Expression DefaultActivityBehaviorFactory.getSkipExpressionFromServiceTask(ServiceTask)"})
  public void testGetSkipExpressionFromServiceTask2() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("not empty");

    // Act
    Expression actualSkipExpressionFromServiceTask = defaultActivityBehaviorFactory
        .getSkipExpressionFromServiceTask(serviceTask);

    // Assert
    assertTrue(actualSkipExpressionFromServiceTask instanceof JuelExpression);
    assertEquals("not empty", actualSkipExpressionFromServiceTask.getExpressionText());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Expression DefaultActivityBehaviorFactory.getSkipExpressionFromServiceTask(ServiceTask)"})
  public void testGetSkipExpressionFromServiceTask3() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider3 = mock(CustomFunctionProvider.class);
    doThrow(new ActivitiException("An error occurred")).when(customFunctionProvider3)
        .addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider3);
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("not empty");

    // Act
    Expression actualSkipExpressionFromServiceTask = defaultActivityBehaviorFactory
        .getSkipExpressionFromServiceTask(serviceTask);

    // Assert
    verify(customFunctionProvider3).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualSkipExpressionFromServiceTask instanceof JuelExpression);
    assertEquals("not empty", actualSkipExpressionFromServiceTask.getExpressionText());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Expression DefaultActivityBehaviorFactory.getSkipExpressionFromServiceTask(ServiceTask)"})
  public void testGetSkipExpressionFromServiceTask_givenEmptyString() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("");

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.getSkipExpressionFromServiceTask(serviceTask));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}.
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Expression DefaultActivityBehaviorFactory.getSkipExpressionFromServiceTask(ServiceTask)"})
  public void testGetSkipExpressionFromServiceTask_thenCallsAddCustomFunctions() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("not empty");

    // Act
    Expression actualSkipExpressionFromServiceTask = defaultActivityBehaviorFactory
        .getSkipExpressionFromServiceTask(serviceTask);

    // Assert
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualSkipExpressionFromServiceTask instanceof JuelExpression);
    assertEquals("not empty", actualSkipExpressionFromServiceTask.getExpressionText());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}.
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Expression DefaultActivityBehaviorFactory.getSkipExpressionFromServiceTask(ServiceTask)"})
  public void testGetSkipExpressionFromServiceTask_thenCallsAddCustomFunctions2() {
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

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("not empty");

    // Act
    Expression actualSkipExpressionFromServiceTask = defaultActivityBehaviorFactory
        .getSkipExpressionFromServiceTask(serviceTask);

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualSkipExpressionFromServiceTask instanceof JuelExpression);
    assertEquals("not empty", actualSkipExpressionFromServiceTask.getExpressionText());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}.
   * <ul>
   *   <li>Then return {@link FixedValue}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Expression DefaultActivityBehaviorFactory.getSkipExpressionFromServiceTask(ServiceTask)"})
  public void testGetSkipExpressionFromServiceTask_thenReturnFixedValue() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    FixedValue fixedValue = new FixedValue(JSONObject.NULL);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(fixedValue);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getSkipExpression()).thenReturn("Skip Expression");

    // Act
    Expression actualSkipExpressionFromServiceTask = defaultActivityBehaviorFactory
        .getSkipExpressionFromServiceTask(serviceTask);

    // Assert
    verify(serviceTask, atLeast(1)).getSkipExpression();
    verify(expressionManager).createExpression(eq("Skip Expression"));
    assertTrue(actualSkipExpressionFromServiceTask instanceof FixedValue);
    assertEquals("null", actualSkipExpressionFromServiceTask.getExpressionText());
    assertSame(fixedValue, actualSkipExpressionFromServiceTask);
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Expression DefaultActivityBehaviorFactory.getSkipExpressionFromServiceTask(ServiceTask)"})
  public void testGetSkipExpressionFromServiceTask_whenServiceTask_thenReturnNull() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.getSkipExpressionFromServiceTask(new ServiceTask()));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClassDelegate DefaultActivityBehaviorFactory.createClassDelegateServiceTask(ServiceTask)"})
  public void testCreateClassDelegateServiceTask() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("not empty");

    // Act
    ClassDelegate actualCreateClassDelegateServiceTaskResult = defaultActivityBehaviorFactory
        .createClassDelegateServiceTask(serviceTask);

    // Assert
    assertNull(actualCreateClassDelegateServiceTaskResult.getClassName());
    assertNull(actualCreateClassDelegateServiceTaskResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClassDelegate DefaultActivityBehaviorFactory.createClassDelegateServiceTask(ServiceTask)"})
  public void testCreateClassDelegateServiceTask2() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("not empty");

    // Act
    ClassDelegate actualCreateClassDelegateServiceTaskResult = defaultActivityBehaviorFactory
        .createClassDelegateServiceTask(serviceTask);

    // Assert
    assertNull(actualCreateClassDelegateServiceTaskResult.getClassName());
    assertNull(actualCreateClassDelegateServiceTaskResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClassDelegate DefaultActivityBehaviorFactory.createClassDelegateServiceTask(ServiceTask)"})
  public void testCreateClassDelegateServiceTask3() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn("Expression");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getImplementation()).thenReturn("Implementation");
    when(serviceTask.getSkipExpression()).thenReturn("Skip Expression");
    when(serviceTask.getMapExceptions()).thenReturn(new ArrayList<>());
    when(serviceTask.getFieldExtensions()).thenReturn(fieldExtensionList);

    // Act
    ClassDelegate actualCreateClassDelegateServiceTaskResult = defaultActivityBehaviorFactory
        .createClassDelegateServiceTask(serviceTask);

    // Assert
    verify(serviceTask).getMapExceptions();
    verify(serviceTask).getId();
    verify(fieldExtension, atLeast(1)).getExpression();
    verify(fieldExtension).getFieldName();
    verify(serviceTask).getImplementation();
    verify(serviceTask, atLeast(1)).getSkipExpression();
    verify(serviceTask).getFieldExtensions();
    verify(expressionManager, atLeast(1)).createExpression(Mockito.<String>any());
    assertEquals("Implementation", actualCreateClassDelegateServiceTaskResult.getClassName());
    assertNull(actualCreateClassDelegateServiceTaskResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClassDelegate DefaultActivityBehaviorFactory.createClassDelegateServiceTask(ServiceTask)"})
  public void testCreateClassDelegateServiceTask4() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    DefaultClassDelegateFactory classDelegateFactory = mock(DefaultClassDelegateFactory.class);
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());

    when(classDelegateFactory.create(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<List<FieldDeclaration>>any(), Mockito.<Expression>any(), Mockito.<List<MapExceptionEntry>>any()))
        .thenReturn(classDelegate);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory(
        classDelegateFactory);
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn("Expression");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getImplementation()).thenReturn("Implementation");
    when(serviceTask.getSkipExpression()).thenReturn("Skip Expression");
    when(serviceTask.getMapExceptions()).thenReturn(new ArrayList<>());
    when(serviceTask.getFieldExtensions()).thenReturn(fieldExtensionList);

    // Act
    ClassDelegate actualCreateClassDelegateServiceTaskResult = defaultActivityBehaviorFactory
        .createClassDelegateServiceTask(serviceTask);

    // Assert
    verify(serviceTask).getMapExceptions();
    verify(serviceTask).getId();
    verify(fieldExtension, atLeast(1)).getExpression();
    verify(fieldExtension).getFieldName();
    verify(serviceTask).getImplementation();
    verify(serviceTask, atLeast(1)).getSkipExpression();
    verify(serviceTask).getFieldExtensions();
    verify(classDelegateFactory).create(eq("42"), eq("Implementation"), isA(List.class), isA(Expression.class),
        isA(List.class));
    verify(expressionManager, atLeast(1)).createExpression(Mockito.<String>any());
    assertSame(classDelegate, actualCreateClassDelegateServiceTaskResult);
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClassDelegate DefaultActivityBehaviorFactory.createClassDelegateServiceTask(ServiceTask)"})
  public void testCreateClassDelegateServiceTask5() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider3 = mock(CustomFunctionProvider.class);
    doThrow(new ActivitiException("An error occurred")).when(customFunctionProvider3)
        .addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider3);
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("not empty");

    // Act
    ClassDelegate actualCreateClassDelegateServiceTaskResult = defaultActivityBehaviorFactory
        .createClassDelegateServiceTask(serviceTask);

    // Assert
    verify(customFunctionProvider3).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertNull(actualCreateClassDelegateServiceTaskResult.getClassName());
    assertNull(actualCreateClassDelegateServiceTaskResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClassDelegate DefaultActivityBehaviorFactory.createClassDelegateServiceTask(ServiceTask)"})
  public void testCreateClassDelegateServiceTask_givenEmptyString() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("");

    // Act
    ClassDelegate actualCreateClassDelegateServiceTaskResult = defaultActivityBehaviorFactory
        .createClassDelegateServiceTask(serviceTask);

    // Assert
    assertNull(actualCreateClassDelegateServiceTaskResult.getClassName());
    assertNull(actualCreateClassDelegateServiceTaskResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClassDelegate DefaultActivityBehaviorFactory.createClassDelegateServiceTask(ServiceTask)"})
  public void testCreateClassDelegateServiceTask_thenCallsAddCustomFunctions() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("not empty");

    // Act
    ClassDelegate actualCreateClassDelegateServiceTaskResult = defaultActivityBehaviorFactory
        .createClassDelegateServiceTask(serviceTask);

    // Assert
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertNull(actualCreateClassDelegateServiceTaskResult.getClassName());
    assertNull(actualCreateClassDelegateServiceTaskResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClassDelegate DefaultActivityBehaviorFactory.createClassDelegateServiceTask(ServiceTask)"})
  public void testCreateClassDelegateServiceTask_thenCallsAddCustomFunctions2() {
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

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("not empty");

    // Act
    ClassDelegate actualCreateClassDelegateServiceTaskResult = defaultActivityBehaviorFactory
        .createClassDelegateServiceTask(serviceTask);

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertNull(actualCreateClassDelegateServiceTaskResult.getClassName());
    assertNull(actualCreateClassDelegateServiceTaskResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <ul>
   *   <li>Then return ClassName is {@code Implementation}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClassDelegate DefaultActivityBehaviorFactory.createClassDelegateServiceTask(ServiceTask)"})
  public void testCreateClassDelegateServiceTask_thenReturnClassNameIsImplementation() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(new ExpressionManager());
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn("Expression");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getImplementation()).thenReturn("Implementation");
    when(serviceTask.getSkipExpression()).thenReturn("Skip Expression");
    when(serviceTask.getMapExceptions()).thenReturn(new ArrayList<>());
    when(serviceTask.getFieldExtensions()).thenReturn(fieldExtensionList);

    // Act
    ClassDelegate actualCreateClassDelegateServiceTaskResult = defaultActivityBehaviorFactory
        .createClassDelegateServiceTask(serviceTask);

    // Assert
    verify(serviceTask).getMapExceptions();
    verify(serviceTask).getId();
    verify(fieldExtension, atLeast(1)).getExpression();
    verify(fieldExtension).getFieldName();
    verify(serviceTask).getImplementation();
    verify(serviceTask, atLeast(1)).getSkipExpression();
    verify(serviceTask).getFieldExtensions();
    assertEquals("Implementation", actualCreateClassDelegateServiceTaskResult.getClassName());
    assertNull(actualCreateClassDelegateServiceTaskResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClassDelegate DefaultActivityBehaviorFactory.createClassDelegateServiceTask(ServiceTask)"})
  public void testCreateClassDelegateServiceTask_whenServiceTask() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act
    ClassDelegate actualCreateClassDelegateServiceTaskResult = defaultActivityBehaviorFactory
        .createClassDelegateServiceTask(new ServiceTask());

    // Assert
    assertNull(actualCreateClassDelegateServiceTaskResult.getClassName());
    assertNull(actualCreateClassDelegateServiceTaskResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ServiceTaskDelegateExpressionActivityBehavior DefaultActivityBehaviorFactory.createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)"})
  public void testCreateServiceTaskDelegateExpressionActivityBehavior() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("");

    // Act
    ServiceTaskDelegateExpressionActivityBehavior actualCreateServiceTaskDelegateExpressionActivityBehaviorResult = defaultActivityBehaviorFactory
        .createServiceTaskDelegateExpressionActivityBehavior(serviceTask);

    // Assert
    verify(expressionManager).createExpression(isNull());
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertNull(actualCreateServiceTaskDelegateExpressionActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ServiceTaskDelegateExpressionActivityBehavior DefaultActivityBehaviorFactory.createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)"})
  public void testCreateServiceTaskDelegateExpressionActivityBehavior_givenNull() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression(null);

    // Act
    ServiceTaskDelegateExpressionActivityBehavior actualCreateServiceTaskDelegateExpressionActivityBehaviorResult = defaultActivityBehaviorFactory
        .createServiceTaskDelegateExpressionActivityBehavior(serviceTask);

    // Assert
    verify(expressionManager).createExpression(isNull());
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertNull(actualCreateServiceTaskDelegateExpressionActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}.
   * <ul>
   *   <li>Given {@code Skip Expression}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ServiceTaskDelegateExpressionActivityBehavior DefaultActivityBehaviorFactory.createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)"})
  public void testCreateServiceTaskDelegateExpressionActivityBehavior_givenSkipExpression() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("Skip Expression");

    // Act
    ServiceTaskDelegateExpressionActivityBehavior actualCreateServiceTaskDelegateExpressionActivityBehaviorResult = defaultActivityBehaviorFactory
        .createServiceTaskDelegateExpressionActivityBehavior(serviceTask);

    // Assert
    verify(expressionManager, atLeast(1)).createExpression(Mockito.<String>any());
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertNull(actualCreateServiceTaskDelegateExpressionActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createDefaultServiceTaskBehavior(ServiceTask)"})
  public void testCreateDefaultServiceTaskBehavior() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("");

    // Act
    ActivityBehavior actualCreateDefaultServiceTaskBehaviorResult = defaultActivityBehaviorFactory
        .createDefaultServiceTaskBehavior(serviceTask);

    // Assert
    assertTrue(actualCreateDefaultServiceTaskBehaviorResult instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) actualCreateDefaultServiceTaskBehaviorResult)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createDefaultServiceTaskBehavior(ServiceTask)"})
  public void testCreateDefaultServiceTaskBehavior2() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("");

    // Act
    ActivityBehavior actualCreateDefaultServiceTaskBehaviorResult = defaultActivityBehaviorFactory
        .createDefaultServiceTaskBehavior(serviceTask);

    // Assert
    assertTrue(actualCreateDefaultServiceTaskBehaviorResult instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) actualCreateDefaultServiceTaskBehaviorResult)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createDefaultServiceTaskBehavior(ServiceTask)"})
  public void testCreateDefaultServiceTaskBehavior3() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider3 = mock(CustomFunctionProvider.class);
    doThrow(new ActivitiException("An error occurred")).when(customFunctionProvider3)
        .addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider3);
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("");

    // Act
    ActivityBehavior actualCreateDefaultServiceTaskBehaviorResult = defaultActivityBehaviorFactory
        .createDefaultServiceTaskBehavior(serviceTask);

    // Assert
    verify(customFunctionProvider3).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualCreateDefaultServiceTaskBehaviorResult instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) actualCreateDefaultServiceTaskBehaviorResult)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}.
   * <ul>
   *   <li>Given {@code ${defaultServiceTaskBehavior}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createDefaultServiceTaskBehavior(ServiceTask)"})
  public void testCreateDefaultServiceTaskBehavior_givenDefaultServiceTaskBehavior() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("${defaultServiceTaskBehavior}");

    // Act
    ActivityBehavior actualCreateDefaultServiceTaskBehaviorResult = defaultActivityBehaviorFactory
        .createDefaultServiceTaskBehavior(serviceTask);

    // Assert
    verify(expressionManager, atLeast(1)).createExpression(eq("${defaultServiceTaskBehavior}"));
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertTrue(actualCreateDefaultServiceTaskBehaviorResult instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) actualCreateDefaultServiceTaskBehaviorResult)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}.
   * <ul>
   *   <li>Given {@code not empty}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createDefaultServiceTaskBehavior(ServiceTask)"})
  public void testCreateDefaultServiceTaskBehavior_givenNotEmpty() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("not empty");

    // Act
    ActivityBehavior actualCreateDefaultServiceTaskBehaviorResult = defaultActivityBehaviorFactory
        .createDefaultServiceTaskBehavior(serviceTask);

    // Assert
    assertTrue(actualCreateDefaultServiceTaskBehaviorResult instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) actualCreateDefaultServiceTaskBehaviorResult)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createDefaultServiceTaskBehavior(ServiceTask)"})
  public void testCreateDefaultServiceTaskBehavior_givenNull() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression(null);

    // Act
    ActivityBehavior actualCreateDefaultServiceTaskBehaviorResult = defaultActivityBehaviorFactory
        .createDefaultServiceTaskBehavior(serviceTask);

    // Assert
    assertTrue(actualCreateDefaultServiceTaskBehaviorResult instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) actualCreateDefaultServiceTaskBehaviorResult)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}.
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createDefaultServiceTaskBehavior(ServiceTask)"})
  public void testCreateDefaultServiceTaskBehavior_thenCallsAddCustomFunctions() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("");

    // Act
    ActivityBehavior actualCreateDefaultServiceTaskBehaviorResult = defaultActivityBehaviorFactory
        .createDefaultServiceTaskBehavior(serviceTask);

    // Assert
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualCreateDefaultServiceTaskBehaviorResult instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) actualCreateDefaultServiceTaskBehaviorResult)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}.
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createDefaultServiceTaskBehavior(ServiceTask)"})
  public void testCreateDefaultServiceTaskBehavior_thenCallsAddCustomFunctions2() {
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

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("");

    // Act
    ActivityBehavior actualCreateDefaultServiceTaskBehaviorResult = defaultActivityBehaviorFactory
        .createDefaultServiceTaskBehavior(serviceTask);

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualCreateDefaultServiceTaskBehaviorResult instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) actualCreateDefaultServiceTaskBehaviorResult)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}.
   * <ul>
   *   <li>Then calls {@link ExpressionManager#createExpression(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createDefaultServiceTaskBehavior(ServiceTask)"})
  public void testCreateDefaultServiceTaskBehavior_thenCallsCreateExpression() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("");

    // Act
    ActivityBehavior actualCreateDefaultServiceTaskBehaviorResult = defaultActivityBehaviorFactory
        .createDefaultServiceTaskBehavior(serviceTask);

    // Assert
    verify(expressionManager).createExpression(eq("${defaultServiceTaskBehavior}"));
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertTrue(actualCreateDefaultServiceTaskBehaviorResult instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) actualCreateDefaultServiceTaskBehaviorResult)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createServiceTaskExpressionActivityBehavior(ServiceTask)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createServiceTaskExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ServiceTaskExpressionActivityBehavior DefaultActivityBehaviorFactory.createServiceTaskExpressionActivityBehavior(ServiceTask)"})
  public void testCreateServiceTaskExpressionActivityBehavior() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("");

    // Act
    ServiceTaskExpressionActivityBehavior actualCreateServiceTaskExpressionActivityBehaviorResult = defaultActivityBehaviorFactory
        .createServiceTaskExpressionActivityBehavior(serviceTask);

    // Assert
    verify(expressionManager).createExpression(isNull());
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertNull(actualCreateServiceTaskExpressionActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createServiceTaskExpressionActivityBehavior(ServiceTask)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createServiceTaskExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ServiceTaskExpressionActivityBehavior DefaultActivityBehaviorFactory.createServiceTaskExpressionActivityBehavior(ServiceTask)"})
  public void testCreateServiceTaskExpressionActivityBehavior_givenNull() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression(null);

    // Act
    ServiceTaskExpressionActivityBehavior actualCreateServiceTaskExpressionActivityBehaviorResult = defaultActivityBehaviorFactory
        .createServiceTaskExpressionActivityBehavior(serviceTask);

    // Assert
    verify(expressionManager).createExpression(isNull());
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertNull(actualCreateServiceTaskExpressionActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createServiceTaskExpressionActivityBehavior(ServiceTask)}.
   * <ul>
   *   <li>Given {@code Skip Expression}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createServiceTaskExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ServiceTaskExpressionActivityBehavior DefaultActivityBehaviorFactory.createServiceTaskExpressionActivityBehavior(ServiceTask)"})
  public void testCreateServiceTaskExpressionActivityBehavior_givenSkipExpression() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setSkipExpression("Skip Expression");

    // Act
    ServiceTaskExpressionActivityBehavior actualCreateServiceTaskExpressionActivityBehaviorResult = defaultActivityBehaviorFactory
        .createServiceTaskExpressionActivityBehavior(serviceTask);

    // Assert
    verify(expressionManager, atLeast(1)).createExpression(Mockito.<String>any());
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertNull(actualCreateServiceTaskExpressionActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createWebServiceActivityBehavior(SendTask)} with {@code sendTask}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createWebServiceActivityBehavior(SendTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.WebServiceActivityBehavior DefaultActivityBehaviorFactory.createWebServiceActivityBehavior(SendTask)"})
  public void testCreateWebServiceActivityBehaviorWithSendTask() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createWebServiceActivityBehavior(new SendTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createWebServiceActivityBehavior(ServiceTask)} with {@code serviceTask}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createWebServiceActivityBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.WebServiceActivityBehavior DefaultActivityBehaviorFactory.createWebServiceActivityBehavior(ServiceTask)"})
  public void testCreateWebServiceActivityBehaviorWithServiceTask() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createWebServiceActivityBehavior(new ServiceTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(SendTask)} with {@code sendTask}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(SendTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.MailActivityBehavior DefaultActivityBehaviorFactory.createMailActivityBehavior(SendTask)"})
  public void testCreateMailActivityBehaviorWithSendTask() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertNull(
        defaultActivityBehaviorFactory.createMailActivityBehavior(new SendTask()).getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(ServiceTask)} with {@code serviceTask}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.MailActivityBehavior DefaultActivityBehaviorFactory.createMailActivityBehavior(ServiceTask)"})
  public void testCreateMailActivityBehaviorWithServiceTask() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createMailActivityBehavior(new ServiceTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(String, List)} with {@code taskId}, {@code fields}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.MailActivityBehavior DefaultActivityBehaviorFactory.createMailActivityBehavior(String, List)"})
  public void testCreateMailActivityBehaviorWithTaskIdFields() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createMailActivityBehavior("42", new ArrayList<>())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(String, List)} with {@code taskId}, {@code fields}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.MailActivityBehavior DefaultActivityBehaviorFactory.createMailActivityBehavior(String, List)"})
  public void testCreateMailActivityBehaviorWithTaskIdFields2() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory(
        new DefaultClassDelegateFactory());

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createMailActivityBehavior("42", new ArrayList<>())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(SendTask)} with {@code sendTask}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(SendTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createMuleActivityBehavior(SendTask)"})
  public void testCreateMuleActivityBehaviorWithSendTask_thenThrowActivitiException() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createMuleActivityBehavior(new SendTask()));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(ServiceTask)} with {@code serviceTask}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createMuleActivityBehavior(ServiceTask)"})
  public void testCreateMuleActivityBehaviorWithServiceTask_thenThrowActivitiException() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createMuleActivityBehavior(new ServiceTask()));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(TaskWithFieldExtensions, List)} with {@code task}, {@code fieldExtensions}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ActivityBehavior DefaultActivityBehaviorFactory.createMuleActivityBehavior(TaskWithFieldExtensions, List)"})
  public void testCreateMuleActivityBehaviorWithTaskFieldExtensions_givenFieldExtension() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    SendTask task = new SendTask();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createMuleActivityBehavior(task, fieldExtensions));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(TaskWithFieldExtensions, List)} with {@code task}, {@code fieldExtensions}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ActivityBehavior DefaultActivityBehaviorFactory.createMuleActivityBehavior(TaskWithFieldExtensions, List)"})
  public void testCreateMuleActivityBehaviorWithTaskFieldExtensions_givenFieldExtension2() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    SendTask task = new SendTask();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());
    fieldExtensions.add(new FieldExtension());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createMuleActivityBehavior(task, fieldExtensions));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(TaskWithFieldExtensions, List)} with {@code task}, {@code fieldExtensions}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ActivityBehavior DefaultActivityBehaviorFactory.createMuleActivityBehavior(TaskWithFieldExtensions, List)"})
  public void testCreateMuleActivityBehaviorWithTaskFieldExtensions_whenArrayList() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    SendTask task = new SendTask();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createMuleActivityBehavior(task, new ArrayList<>()));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)} with {@code sendTask}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createCamelActivityBehavior(SendTask)"})
  public void testCreateCamelActivityBehaviorWithSendTask() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("camelBehaviorClass");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    SendTask sendTask = new SendTask();
    sendTask.setFieldExtensions(fieldExtensions);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(sendTask));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)} with {@code sendTask}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createCamelActivityBehavior(SendTask)"})
  public void testCreateCamelActivityBehaviorWithSendTask2() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("");
    fieldExtension.setFieldName("camelBehaviorClass");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    SendTask sendTask = new SendTask();
    sendTask.setFieldExtensions(fieldExtensions);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(sendTask));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)} with {@code sendTask}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createCamelActivityBehavior(SendTask)"})
  public void testCreateCamelActivityBehaviorWithSendTask_givenArrayListAddFieldExtension() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    SendTask sendTask = new SendTask();
    sendTask.setFieldExtensions(fieldExtensions);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(sendTask));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)} with {@code sendTask}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) StringValue is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createCamelActivityBehavior(SendTask)"})
  public void testCreateCamelActivityBehaviorWithSendTask_givenFieldExtensionStringValueIs42() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("42");
    fieldExtension.setFieldName("camelBehaviorClass");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    SendTask sendTask = new SendTask();
    sendTask.setFieldExtensions(fieldExtensions);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(sendTask));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)} with {@code sendTask}.
   * <ul>
   *   <li>When {@link SendTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createCamelActivityBehavior(SendTask)"})
  public void testCreateCamelActivityBehaviorWithSendTask_whenSendTask() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(new SendTask()));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)} with {@code serviceTask}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createCamelActivityBehavior(ServiceTask)"})
  public void testCreateCamelActivityBehaviorWithServiceTask() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("not empty");
    fieldExtension.setFieldName("camelBehaviorClass");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    fieldExtensions.add(null);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setFieldExtensions(fieldExtensions);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(serviceTask));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)} with {@code serviceTask}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ActivityBehavior DefaultActivityBehaviorFactory.createCamelActivityBehavior(ServiceTask)"})
  public void testCreateCamelActivityBehaviorWithServiceTask_whenServiceTask() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(new ServiceTask()));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)} with {@code task}, {@code fieldExtensions}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ActivityBehavior DefaultActivityBehaviorFactory.createCamelActivityBehavior(TaskWithFieldExtensions, List)"})
  public void testCreateCamelActivityBehaviorWithTaskFieldExtensions() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    SendTask task = new SendTask();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("camelBehaviorClass");
    fieldExtension.setStringValue("not empty");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    fieldExtensions.add(null);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(task, fieldExtensions));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)} with {@code task}, {@code fieldExtensions}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ActivityBehavior DefaultActivityBehaviorFactory.createCamelActivityBehavior(TaskWithFieldExtensions, List)"})
  public void testCreateCamelActivityBehaviorWithTaskFieldExtensions_givenFieldExtension() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    SendTask task = new SendTask();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(task, fieldExtensions));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)} with {@code task}, {@code fieldExtensions}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ActivityBehavior DefaultActivityBehaviorFactory.createCamelActivityBehavior(TaskWithFieldExtensions, List)"})
  public void testCreateCamelActivityBehaviorWithTaskFieldExtensions_whenArrayList() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    SendTask task = new SendTask();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(task, new ArrayList<>()));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createShellActivityBehavior(ServiceTask)}.
   * <ul>
   *   <li>Then calls {@link FieldExtension#getExpression()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createShellActivityBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ShellActivityBehavior DefaultActivityBehaviorFactory.createShellActivityBehavior(ServiceTask)"})
  public void testCreateShellActivityBehavior_thenCallsGetExpression() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn("Expression");
    when(fieldExtension.getFieldName()).thenReturn("wait");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setFieldExtensions(fieldExtensions);

    // Act
    ShellActivityBehavior actualCreateShellActivityBehaviorResult = defaultActivityBehaviorFactory
        .createShellActivityBehavior(serviceTask);

    // Assert
    verify(fieldExtension, atLeast(1)).getExpression();
    verify(fieldExtension).getFieldName();
    verify(expressionManager).createExpression(eq("Expression"));
    assertNull(actualCreateShellActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createShellActivityBehavior(ServiceTask)}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createShellActivityBehavior(ServiceTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ShellActivityBehavior DefaultActivityBehaviorFactory.createShellActivityBehavior(ServiceTask)"})
  public void testCreateShellActivityBehavior_whenServiceTask() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createShellActivityBehavior(new ServiceTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createBusinessRuleTaskActivityBehavior(BusinessRuleTask)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createBusinessRuleTaskActivityBehavior(BusinessRuleTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ActivityBehavior DefaultActivityBehaviorFactory.createBusinessRuleTaskActivityBehavior(BusinessRuleTask)"})
  public void testCreateBusinessRuleTaskActivityBehavior_thenThrowActivitiException() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ArrayList<String> inputVariables = new ArrayList<>();
    inputVariables.add("Business Rule Task");

    ArrayList<String> ruleNames = new ArrayList<>();
    ruleNames.add("Business Rule Task");

    BusinessRuleTask businessRuleTask = new BusinessRuleTask();
    businessRuleTask.setClassName("not empty");
    businessRuleTask.setInputVariables(inputVariables);
    businessRuleTask.setRuleNames(ruleNames);
    businessRuleTask.setResultVariableName(null);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createBusinessRuleTaskActivityBehavior(businessRuleTask));
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createScriptTaskActivityBehavior(ScriptTask)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ScriptTask} (default constructor) ScriptFormat is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createScriptTaskActivityBehavior(ScriptTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.ScriptTaskActivityBehavior DefaultActivityBehaviorFactory.createScriptTaskActivityBehavior(ScriptTask)"})
  public void testCreateScriptTaskActivityBehavior_givenNull_whenScriptTaskScriptFormatIsNull() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    ScriptTask scriptTask = new ScriptTask();
    scriptTask.setScriptFormat(null);

    // Act and Assert
    assertNull(
        defaultActivityBehaviorFactory.createScriptTaskActivityBehavior(scriptTask).getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createScriptTaskActivityBehavior(ScriptTask)}.
   * <ul>
   *   <li>Given {@code Script Task}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createScriptTaskActivityBehavior(ScriptTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.ScriptTaskActivityBehavior DefaultActivityBehaviorFactory.createScriptTaskActivityBehavior(ScriptTask)"})
  public void testCreateScriptTaskActivityBehavior_givenScriptTask() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    ScriptTask scriptTask = new ScriptTask();
    scriptTask.setScriptFormat("Script Task");

    // Act and Assert
    assertNull(
        defaultActivityBehaviorFactory.createScriptTaskActivityBehavior(scriptTask).getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createScriptTaskActivityBehavior(ScriptTask)}.
   * <ul>
   *   <li>When {@link ScriptTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createScriptTaskActivityBehavior(ScriptTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.ScriptTaskActivityBehavior DefaultActivityBehaviorFactory.createScriptTaskActivityBehavior(ScriptTask)"})
  public void testCreateScriptTaskActivityBehavior_whenScriptTask() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createScriptTaskActivityBehavior(new ScriptTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createSequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createSequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "SequentialMultiInstanceBehavior DefaultActivityBehaviorFactory.createSequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)"})
  public void testCreateSequentialMultiInstanceBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    AdhocSubProcess activity = new AdhocSubProcess();
    AbstractBpmnActivityBehavior innerActivityBehavior = new AbstractBpmnActivityBehavior();

    // Act
    SequentialMultiInstanceBehavior actualCreateSequentialMultiInstanceBehaviorResult = defaultActivityBehaviorFactory
        .createSequentialMultiInstanceBehavior(activity, innerActivityBehavior);

    // Assert
    assertEquals("loopCounter", actualCreateSequentialMultiInstanceBehaviorResult.getCollectionElementIndexVariable());
    assertNull(actualCreateSequentialMultiInstanceBehaviorResult.getCollectionElementVariable());
    assertNull(actualCreateSequentialMultiInstanceBehaviorResult.getCollectionVariable());
    assertNull(actualCreateSequentialMultiInstanceBehaviorResult.getLoopDataOutputRef());
    assertNull(actualCreateSequentialMultiInstanceBehaviorResult.getOutputDataItem());
    assertNull(actualCreateSequentialMultiInstanceBehaviorResult.getCollectionExpression());
    assertNull(actualCreateSequentialMultiInstanceBehaviorResult.getCompletionConditionExpression());
    assertNull(actualCreateSequentialMultiInstanceBehaviorResult.getLoopCardinalityExpression());
    assertFalse(actualCreateSequentialMultiInstanceBehaviorResult.hasLoopDataOutputRef());
    assertFalse(actualCreateSequentialMultiInstanceBehaviorResult.hasOutputDataItem());
    AbstractBpmnActivityBehavior innerActivityBehavior2 = actualCreateSequentialMultiInstanceBehaviorResult
        .getInnerActivityBehavior();
    assertSame(innerActivityBehavior, innerActivityBehavior2);
    assertSame(actualCreateSequentialMultiInstanceBehaviorResult,
        innerActivityBehavior2.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createSequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createSequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "SequentialMultiInstanceBehavior DefaultActivityBehaviorFactory.createSequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)"})
  public void testCreateSequentialMultiInstanceBehavior2() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    AdhocSubProcess activity = new AdhocSubProcess();
    AbstractBpmnActivityBehavior innerActivityBehavior = mock(AbstractBpmnActivityBehavior.class);
    doNothing().when(innerActivityBehavior)
        .setMultiInstanceActivityBehavior(Mockito.<MultiInstanceActivityBehavior>any());

    // Act
    SequentialMultiInstanceBehavior actualCreateSequentialMultiInstanceBehaviorResult = defaultActivityBehaviorFactory
        .createSequentialMultiInstanceBehavior(activity, innerActivityBehavior);

    // Assert
    verify(innerActivityBehavior).setMultiInstanceActivityBehavior(isA(MultiInstanceActivityBehavior.class));
    assertEquals("loopCounter", actualCreateSequentialMultiInstanceBehaviorResult.getCollectionElementIndexVariable());
    assertNull(actualCreateSequentialMultiInstanceBehaviorResult.getCollectionElementVariable());
    assertNull(actualCreateSequentialMultiInstanceBehaviorResult.getCollectionVariable());
    assertNull(actualCreateSequentialMultiInstanceBehaviorResult.getLoopDataOutputRef());
    assertNull(actualCreateSequentialMultiInstanceBehaviorResult.getOutputDataItem());
    assertNull(actualCreateSequentialMultiInstanceBehaviorResult.getCollectionExpression());
    assertNull(actualCreateSequentialMultiInstanceBehaviorResult.getCompletionConditionExpression());
    assertNull(actualCreateSequentialMultiInstanceBehaviorResult.getLoopCardinalityExpression());
    assertFalse(actualCreateSequentialMultiInstanceBehaviorResult.hasLoopDataOutputRef());
    assertFalse(actualCreateSequentialMultiInstanceBehaviorResult.hasOutputDataItem());
    assertSame(innerActivityBehavior, actualCreateSequentialMultiInstanceBehaviorResult.getInnerActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ParallelMultiInstanceBehavior DefaultActivityBehaviorFactory.createParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)"})
  public void testCreateParallelMultiInstanceBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    AdhocSubProcess activity = new AdhocSubProcess();
    AbstractBpmnActivityBehavior innerActivityBehavior = new AbstractBpmnActivityBehavior();

    // Act
    ParallelMultiInstanceBehavior actualCreateParallelMultiInstanceBehaviorResult = defaultActivityBehaviorFactory
        .createParallelMultiInstanceBehavior(activity, innerActivityBehavior);

    // Assert
    assertEquals("loopCounter", actualCreateParallelMultiInstanceBehaviorResult.getCollectionElementIndexVariable());
    assertNull(actualCreateParallelMultiInstanceBehaviorResult.getCollectionElementVariable());
    assertNull(actualCreateParallelMultiInstanceBehaviorResult.getCollectionVariable());
    assertNull(actualCreateParallelMultiInstanceBehaviorResult.getLoopDataOutputRef());
    assertNull(actualCreateParallelMultiInstanceBehaviorResult.getOutputDataItem());
    assertNull(actualCreateParallelMultiInstanceBehaviorResult.getCollectionExpression());
    assertNull(actualCreateParallelMultiInstanceBehaviorResult.getCompletionConditionExpression());
    assertNull(actualCreateParallelMultiInstanceBehaviorResult.getLoopCardinalityExpression());
    assertFalse(actualCreateParallelMultiInstanceBehaviorResult.hasLoopDataOutputRef());
    assertFalse(actualCreateParallelMultiInstanceBehaviorResult.hasOutputDataItem());
    AbstractBpmnActivityBehavior innerActivityBehavior2 = actualCreateParallelMultiInstanceBehaviorResult
        .getInnerActivityBehavior();
    assertSame(innerActivityBehavior, innerActivityBehavior2);
    assertSame(actualCreateParallelMultiInstanceBehaviorResult,
        innerActivityBehavior2.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ParallelMultiInstanceBehavior DefaultActivityBehaviorFactory.createParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)"})
  public void testCreateParallelMultiInstanceBehavior2() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    AdhocSubProcess activity = new AdhocSubProcess();
    AbstractBpmnActivityBehavior innerActivityBehavior = mock(AbstractBpmnActivityBehavior.class);
    doNothing().when(innerActivityBehavior)
        .setMultiInstanceActivityBehavior(Mockito.<MultiInstanceActivityBehavior>any());

    // Act
    ParallelMultiInstanceBehavior actualCreateParallelMultiInstanceBehaviorResult = defaultActivityBehaviorFactory
        .createParallelMultiInstanceBehavior(activity, innerActivityBehavior);

    // Assert
    verify(innerActivityBehavior).setMultiInstanceActivityBehavior(isA(MultiInstanceActivityBehavior.class));
    assertEquals("loopCounter", actualCreateParallelMultiInstanceBehaviorResult.getCollectionElementIndexVariable());
    assertNull(actualCreateParallelMultiInstanceBehaviorResult.getCollectionElementVariable());
    assertNull(actualCreateParallelMultiInstanceBehaviorResult.getCollectionVariable());
    assertNull(actualCreateParallelMultiInstanceBehaviorResult.getLoopDataOutputRef());
    assertNull(actualCreateParallelMultiInstanceBehaviorResult.getOutputDataItem());
    assertNull(actualCreateParallelMultiInstanceBehaviorResult.getCollectionExpression());
    assertNull(actualCreateParallelMultiInstanceBehaviorResult.getCompletionConditionExpression());
    assertNull(actualCreateParallelMultiInstanceBehaviorResult.getLoopCardinalityExpression());
    assertFalse(actualCreateParallelMultiInstanceBehaviorResult.hasLoopDataOutputRef());
    assertFalse(actualCreateParallelMultiInstanceBehaviorResult.hasOutputDataItem());
    assertSame(innerActivityBehavior, actualCreateParallelMultiInstanceBehaviorResult.getInnerActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createSubprocessActivityBehavior(SubProcess)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createSubprocessActivityBehavior(SubProcess)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.SubProcessActivityBehavior DefaultActivityBehaviorFactory.createSubprocessActivityBehavior(SubProcess)"})
  public void testCreateSubprocessActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createSubprocessActivityBehavior(new SubProcess())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createEventSubProcessErrorStartEventActivityBehavior(StartEvent)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createEventSubProcessErrorStartEventActivityBehavior(StartEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.EventSubProcessErrorStartEventActivityBehavior DefaultActivityBehaviorFactory.createEventSubProcessErrorStartEventActivityBehavior(StartEvent)"})
  public void testCreateEventSubProcessErrorStartEventActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createEventSubProcessErrorStartEventActivityBehavior(new StartEvent())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.EventSubProcessMessageStartEventActivityBehavior DefaultActivityBehaviorFactory.createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)"})
  public void testCreateEventSubProcessMessageStartEventActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    StartEvent startEvent = new StartEvent();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createEventSubProcessMessageStartEventActivityBehavior(startEvent, new MessageEventDefinition())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.EventSubProcessMessageStartEventActivityBehavior DefaultActivityBehaviorFactory.createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)"})
  public void testCreateEventSubProcessMessageStartEventActivityBehavior2() {
    // Arrange
    MessageExecutionContextFactory messageExecutionContextFactory = mock(MessageExecutionContextFactory.class);
    when(messageExecutionContextFactory.create(Mockito.<MessageEventDefinition>any(),
        Mockito.<MessagePayloadMappingProvider>any(), Mockito.<ExpressionManager>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setMessageExecutionContextFactory(messageExecutionContextFactory);
    StartEvent startEvent = new StartEvent();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultActivityBehaviorFactory
        .createEventSubProcessMessageStartEventActivityBehavior(startEvent, new MessageEventDefinition()));
    verify(messageExecutionContextFactory).create(isA(MessageEventDefinition.class),
        isA(MessagePayloadMappingProvider.class), isNull());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createAdhocSubprocessActivityBehavior(SubProcess)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createAdhocSubprocessActivityBehavior(SubProcess)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.AdhocSubProcessActivityBehavior DefaultActivityBehaviorFactory.createAdhocSubprocessActivityBehavior(SubProcess)"})
  public void testCreateAdhocSubprocessActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createAdhocSubprocessActivityBehavior(new SubProcess())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)} with {@code callActivity}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivityBehavior DefaultActivityBehaviorFactory.createCallActivityBehavior(CallActivity)"})
  public void testCreateCallActivityBehaviorWithCallActivity() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    CallActivity callActivity = new CallActivity();
    callActivity.setCalledElement("not empty");

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior(callActivity);

    // Assert
    assertEquals("not empty", actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)} with {@code callActivity}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivityBehavior DefaultActivityBehaviorFactory.createCallActivityBehavior(CallActivity)"})
  public void testCreateCallActivityBehaviorWithCallActivity2() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    CallActivity callActivity = new CallActivity();
    callActivity.setCalledElement("");

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior(callActivity);

    // Assert
    assertEquals("", actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)} with {@code callActivity}.
   * <ul>
   *   <li>Given {@code ${U}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivityBehavior DefaultActivityBehaviorFactory.createCallActivityBehavior(CallActivity)"})
  public void testCreateCallActivityBehaviorWithCallActivity_givenU() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    CallActivity callActivity = new CallActivity();
    callActivity.setCalledElement("${U}");

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior(callActivity);

    // Assert
    assertNull(actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)} with {@code callActivity}.
   * <ul>
   *   <li>Given {@code ${U}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivityBehavior DefaultActivityBehaviorFactory.createCallActivityBehavior(CallActivity)"})
  public void testCreateCallActivityBehaviorWithCallActivity_givenU2() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    CallActivity callActivity = new CallActivity();
    callActivity.setCalledElement("${U}");

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior(callActivity);

    // Assert
    verify(expressionManager).createExpression(eq("${U}"));
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertNull(actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)} with {@code callActivity}.
   * <ul>
   *   <li>Given {@code ${U}\$+\{+.+\}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivityBehavior DefaultActivityBehaviorFactory.createCallActivityBehavior(CallActivity)"})
  public void testCreateCallActivityBehaviorWithCallActivity_givenU3() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    CallActivity callActivity = new CallActivity();
    callActivity.setCalledElement("${U}\\$+\\{+.+\\}");

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior(callActivity);

    // Assert
    verify(expressionManager).createExpression(eq("${U}\\$+\\{+.+\\}"));
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertNull(actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)} with {@code callActivity}.
   * <ul>
   *   <li>Given {@code ${U}${U}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivityBehavior DefaultActivityBehaviorFactory.createCallActivityBehavior(CallActivity)"})
  public void testCreateCallActivityBehaviorWithCallActivity_givenUU() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    CallActivity callActivity = new CallActivity();
    callActivity.setCalledElement("${U}${U}");

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior(callActivity);

    // Assert
    verify(expressionManager).createExpression(eq("${U}${U}"));
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertNull(actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)} with {@code callActivity}.
   * <ul>
   *   <li>When {@link CallActivity} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivityBehavior DefaultActivityBehaviorFactory.createCallActivityBehavior(CallActivity)"})
  public void testCreateCallActivityBehaviorWithCallActivity_whenCallActivity() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior(new CallActivity());

    // Assert
    assertNull(actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)} with {@code calledElement}, {@code mapExceptions}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivityBehavior DefaultActivityBehaviorFactory.createCallActivityBehavior(String, List)"})
  public void testCreateCallActivityBehaviorWithCalledElementMapExceptions() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior("Called Element", mapExceptions);

    // Assert
    assertEquals("Called Element", actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)} with {@code calledElement}, {@code mapExceptions}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivityBehavior DefaultActivityBehaviorFactory.createCallActivityBehavior(String, List)"})
  public void testCreateCallActivityBehaviorWithCalledElementMapExceptions2() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior("Called Element", mapExceptions);

    // Assert
    assertEquals("Called Element", actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)} with {@code calledElement}, {@code mapExceptions}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CallActivityBehavior DefaultActivityBehaviorFactory.createCallActivityBehavior(String, List)"})
  public void testCreateCallActivityBehaviorWithCalledElementMapExceptions_whenArrayList() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior("Called Element", new ArrayList<>());

    // Assert
    assertEquals("Called Element", actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)} with {@code expression}, {@code mapExceptions}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "CallActivityBehavior DefaultActivityBehaviorFactory.createCallActivityBehavior(Expression, List)"})
  public void testCreateCallActivityBehaviorWithExpressionMapExceptions() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    FixedValue expression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior(expression, mapExceptions);

    // Assert
    assertNull(actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)} with {@code expression}, {@code mapExceptions}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "CallActivityBehavior DefaultActivityBehaviorFactory.createCallActivityBehavior(Expression, List)"})
  public void testCreateCallActivityBehaviorWithExpressionMapExceptions2() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    FixedValue expression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior(expression, mapExceptions);

    // Assert
    assertNull(actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)} with {@code expression}, {@code mapExceptions}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "CallActivityBehavior DefaultActivityBehaviorFactory.createCallActivityBehavior(Expression, List)"})
  public void testCreateCallActivityBehaviorWithExpressionMapExceptions_whenArrayList() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    FixedValue expression = new FixedValue(JSONObject.NULL);

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior(expression, new ArrayList<>());

    // Assert
    assertNull(actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createTransactionActivityBehavior(Transaction)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createTransactionActivityBehavior(Transaction)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.TransactionActivityBehavior DefaultActivityBehaviorFactory.createTransactionActivityBehavior(Transaction)"})
  public void testCreateTransactionActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createTransactionActivityBehavior(new Transaction())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createIntermediateCatchEventActivityBehavior(IntermediateCatchEvent)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createIntermediateCatchEventActivityBehavior(IntermediateCatchEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.IntermediateCatchEventActivityBehavior DefaultActivityBehaviorFactory.createIntermediateCatchEventActivityBehavior(IntermediateCatchEvent)"})
  public void testCreateIntermediateCatchEventActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createIntermediateCatchEventActivityBehavior(new IntermediateCatchEvent())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "IntermediateCatchMessageEventActivityBehavior DefaultActivityBehaviorFactory.createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)"})
  public void testCreateIntermediateCatchMessageEventActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    IntermediateCatchEvent intermediateCatchEvent = new IntermediateCatchEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();

    // Act
    IntermediateCatchMessageEventActivityBehavior actualCreateIntermediateCatchMessageEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createIntermediateCatchMessageEventActivityBehavior(intermediateCatchEvent, messageEventDefinition);

    // Assert
    MessageExecutionContext messageExecutionContext = actualCreateIntermediateCatchMessageEventActivityBehaviorResult
        .getMessageExecutionContext();
    assertTrue(messageExecutionContext instanceof DefaultMessageExecutionContext);
    assertTrue(((DefaultMessageExecutionContext) messageExecutionContext)
        .getMessagePayloadMappingProvider() instanceof BpmnMessagePayloadMappingProvider);
    assertNull(actualCreateIntermediateCatchMessageEventActivityBehaviorResult.getMultiInstanceActivityBehavior());
    assertNull(((DefaultMessageExecutionContext) messageExecutionContext).getExpressionManager());
    assertSame(messageEventDefinition,
        actualCreateIntermediateCatchMessageEventActivityBehaviorResult.getMessageEventDefinition());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "IntermediateCatchMessageEventActivityBehavior DefaultActivityBehaviorFactory.createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)"})
  public void testCreateIntermediateCatchMessageEventActivityBehavior2() {
    // Arrange
    MessageExecutionContextFactory messageExecutionContextFactory = mock(MessageExecutionContextFactory.class);
    when(messageExecutionContextFactory.create(Mockito.<MessageEventDefinition>any(),
        Mockito.<MessagePayloadMappingProvider>any(), Mockito.<ExpressionManager>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setMessageExecutionContextFactory(messageExecutionContextFactory);
    IntermediateCatchEvent intermediateCatchEvent = new IntermediateCatchEvent();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultActivityBehaviorFactory
        .createIntermediateCatchMessageEventActivityBehavior(intermediateCatchEvent, new MessageEventDefinition()));
    verify(messageExecutionContextFactory).create(isA(MessageEventDefinition.class),
        isA(MessagePayloadMappingProvider.class), isNull());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createIntermediateCatchTimerEventActivityBehavior(IntermediateCatchEvent, TimerEventDefinition)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createIntermediateCatchTimerEventActivityBehavior(IntermediateCatchEvent, TimerEventDefinition)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.IntermediateCatchTimerEventActivityBehavior DefaultActivityBehaviorFactory.createIntermediateCatchTimerEventActivityBehavior(IntermediateCatchEvent, TimerEventDefinition)"})
  public void testCreateIntermediateCatchTimerEventActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    IntermediateCatchEvent intermediateCatchEvent = new IntermediateCatchEvent();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateCatchTimerEventActivityBehavior(intermediateCatchEvent, new TimerEventDefinition())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createIntermediateCatchSignalEventActivityBehavior(IntermediateCatchEvent, SignalEventDefinition, Signal)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createIntermediateCatchSignalEventActivityBehavior(IntermediateCatchEvent, SignalEventDefinition, Signal)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.IntermediateCatchSignalEventActivityBehavior DefaultActivityBehaviorFactory.createIntermediateCatchSignalEventActivityBehavior(IntermediateCatchEvent, SignalEventDefinition, Signal)"})
  public void testCreateIntermediateCatchSignalEventActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    IntermediateCatchEvent intermediateCatchEvent = new IntermediateCatchEvent();
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateCatchSignalEventActivityBehavior(intermediateCatchEvent, signalEventDefinition,
            new Signal("42", "Name"))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.IntermediateThrowSignalEventActivityBehavior DefaultActivityBehaviorFactory.createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)"})
  public void testCreateIntermediateThrowSignalEventActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    ThrowEvent throwEvent = new ThrowEvent();
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateThrowSignalEventActivityBehavior(throwEvent, signalEventDefinition, new Signal("42", "Name"))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.IntermediateThrowSignalEventActivityBehavior DefaultActivityBehaviorFactory.createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)"})
  public void testCreateIntermediateThrowSignalEventActivityBehavior2() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    ThrowEvent throwEvent = new ThrowEvent();

    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    signalEventDefinition.setSignalRef(null);

    Signal signal = new Signal("42", "Name");
    signal.setScope(Signal.SCOPE_PROCESS_INSTANCE);

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateThrowSignalEventActivityBehavior(throwEvent, signalEventDefinition, signal)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.IntermediateThrowSignalEventActivityBehavior DefaultActivityBehaviorFactory.createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)"})
  public void testCreateIntermediateThrowSignalEventActivityBehavior3() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    ThrowEvent throwEvent = new ThrowEvent();
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    Signal signal = mock(Signal.class);
    when(signal.getName()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultActivityBehaviorFactory
        .createIntermediateThrowSignalEventActivityBehavior(throwEvent, signalEventDefinition, signal));
    verify(signal).getName();
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.IntermediateThrowSignalEventActivityBehavior DefaultActivityBehaviorFactory.createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)"})
  public void testCreateIntermediateThrowSignalEventActivityBehavior_givenEmptyString() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    ThrowEvent throwEvent = new ThrowEvent();

    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    signalEventDefinition.setSignalRef("");

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateThrowSignalEventActivityBehavior(throwEvent, signalEventDefinition, null)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}.
   * <ul>
   *   <li>Given {@code not empty}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.IntermediateThrowSignalEventActivityBehavior DefaultActivityBehaviorFactory.createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)"})
  public void testCreateIntermediateThrowSignalEventActivityBehavior_givenNotEmpty() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    ThrowEvent throwEvent = new ThrowEvent();

    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    signalEventDefinition.setSignalRef("not empty");

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateThrowSignalEventActivityBehavior(throwEvent, signalEventDefinition, null)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.IntermediateThrowSignalEventActivityBehavior DefaultActivityBehaviorFactory.createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)"})
  public void testCreateIntermediateThrowSignalEventActivityBehavior_givenNull() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    ThrowEvent throwEvent = new ThrowEvent();

    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    signalEventDefinition.setSignalRef(null);

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateThrowSignalEventActivityBehavior(throwEvent, signalEventDefinition, null)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createErrorEndEventActivityBehavior(EndEvent, ErrorEventDefinition)}.
   * <ul>
   *   <li>Then return ErrorRef is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createErrorEndEventActivityBehavior(EndEvent, ErrorEventDefinition)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.ErrorEndEventActivityBehavior DefaultActivityBehaviorFactory.createErrorEndEventActivityBehavior(EndEvent, ErrorEventDefinition)"})
  public void testCreateErrorEndEventActivityBehavior_thenReturnErrorRefIsNull() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    EndEvent endEvent = new EndEvent();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createErrorEndEventActivityBehavior(endEvent, new ErrorEventDefinition())
        .getErrorRef());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "TerminateEndEventActivityBehavior DefaultActivityBehaviorFactory.createTerminateEndEventActivityBehavior(EndEvent)"})
  public void testCreateTerminateEndEventActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new TerminateEventDefinition());

    EndEvent endEvent = new EndEvent();
    endEvent.setEventDefinitions(eventDefinitions);

    // Act
    TerminateEndEventActivityBehavior actualCreateTerminateEndEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createTerminateEndEventActivityBehavior(endEvent);

    // Assert
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateAll());
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateMultiInstance());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}.
   * <ul>
   *   <li>Given {@link CancelEventDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "TerminateEndEventActivityBehavior DefaultActivityBehaviorFactory.createTerminateEndEventActivityBehavior(EndEvent)"})
  public void testCreateTerminateEndEventActivityBehavior_givenCancelEventDefinition() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    EndEvent endEvent = new EndEvent();
    endEvent.addEventDefinition(new CancelEventDefinition());

    // Act
    TerminateEndEventActivityBehavior actualCreateTerminateEndEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createTerminateEndEventActivityBehavior(endEvent);

    // Assert
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateAll());
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateMultiInstance());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "TerminateEndEventActivityBehavior DefaultActivityBehaviorFactory.createTerminateEndEventActivityBehavior(EndEvent)"})
  public void testCreateTerminateEndEventActivityBehavior_givenNull() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    EndEvent endEvent = new EndEvent();
    endEvent.setEventDefinitions(null);

    // Act
    TerminateEndEventActivityBehavior actualCreateTerminateEndEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createTerminateEndEventActivityBehavior(endEvent);

    // Assert
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateAll());
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateMultiInstance());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}.
   * <ul>
   *   <li>Then calls {@link TerminateEventDefinition#isTerminateAll()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "TerminateEndEventActivityBehavior DefaultActivityBehaviorFactory.createTerminateEndEventActivityBehavior(EndEvent)"})
  public void testCreateTerminateEndEventActivityBehavior_thenCallsIsTerminateAll() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    TerminateEventDefinition eventDefinition = mock(TerminateEventDefinition.class);
    when(eventDefinition.isTerminateAll()).thenThrow(new ActivitiException("An error occurred"));

    EndEvent endEvent = new EndEvent();
    endEvent.addEventDefinition(eventDefinition);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createTerminateEndEventActivityBehavior(endEvent));
    verify(eventDefinition).isTerminateAll();
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}.
   * <ul>
   *   <li>Then calls {@link COWArrayList#size()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "TerminateEndEventActivityBehavior DefaultActivityBehaviorFactory.createTerminateEndEventActivityBehavior(EndEvent)"})
  public void testCreateTerminateEndEventActivityBehavior_thenCallsSize() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    COWArrayList<EventDefinition> eventDefinitions = mock(COWArrayList.class);
    when(eventDefinitions.size()).thenThrow(new ActivitiException("An error occurred"));

    EndEvent endEvent = new EndEvent();
    endEvent.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createTerminateEndEventActivityBehavior(endEvent));
    verify(eventDefinitions).size();
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}.
   * <ul>
   *   <li>When {@link EndEvent} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "TerminateEndEventActivityBehavior DefaultActivityBehaviorFactory.createTerminateEndEventActivityBehavior(EndEvent)"})
  public void testCreateTerminateEndEventActivityBehavior_whenEndEvent() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act
    TerminateEndEventActivityBehavior actualCreateTerminateEndEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createTerminateEndEventActivityBehavior(new EndEvent());

    // Assert
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateAll());
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateMultiInstance());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createBoundaryEventActivityBehavior(BoundaryEvent, boolean)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createBoundaryEventActivityBehavior(BoundaryEvent, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.BoundaryEventActivityBehavior DefaultActivityBehaviorFactory.createBoundaryEventActivityBehavior(BoundaryEvent, boolean)"})
  public void testCreateBoundaryEventActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertTrue(
        defaultActivityBehaviorFactory.createBoundaryEventActivityBehavior(new BoundaryEvent(), true).isInterrupting());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createBoundaryCancelEventActivityBehavior(CancelEventDefinition)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createBoundaryCancelEventActivityBehavior(CancelEventDefinition)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.BoundaryCancelEventActivityBehavior DefaultActivityBehaviorFactory.createBoundaryCancelEventActivityBehavior(CancelEventDefinition)"})
  public void testCreateBoundaryCancelEventActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.createBoundaryCancelEventActivityBehavior(new CancelEventDefinition())
        .isInterrupting());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createBoundaryCompensateEventActivityBehavior(BoundaryEvent, CompensateEventDefinition, boolean)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createBoundaryCompensateEventActivityBehavior(BoundaryEvent, CompensateEventDefinition, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.BoundaryCompensateEventActivityBehavior DefaultActivityBehaviorFactory.createBoundaryCompensateEventActivityBehavior(BoundaryEvent, CompensateEventDefinition, boolean)"})
  public void testCreateBoundaryCompensateEventActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createBoundaryCompensateEventActivityBehavior(boundaryEvent, new CompensateEventDefinition(), true)
        .isInterrupting());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createBoundaryTimerEventActivityBehavior(BoundaryEvent, TimerEventDefinition, boolean)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createBoundaryTimerEventActivityBehavior(BoundaryEvent, TimerEventDefinition, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.BoundaryTimerEventActivityBehavior DefaultActivityBehaviorFactory.createBoundaryTimerEventActivityBehavior(BoundaryEvent, TimerEventDefinition, boolean)"})
  public void testCreateBoundaryTimerEventActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createBoundaryTimerEventActivityBehavior(boundaryEvent, new TimerEventDefinition(), true)
        .isInterrupting());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createBoundarySignalEventActivityBehavior(BoundaryEvent, SignalEventDefinition, Signal, boolean)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createBoundarySignalEventActivityBehavior(BoundaryEvent, SignalEventDefinition, Signal, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.bpmn.behavior.BoundarySignalEventActivityBehavior DefaultActivityBehaviorFactory.createBoundarySignalEventActivityBehavior(BoundaryEvent, SignalEventDefinition, Signal, boolean)"})
  public void testCreateBoundarySignalEventActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createBoundarySignalEventActivityBehavior(boundaryEvent, signalEventDefinition, new Signal("42", "Name"), true)
        .isInterrupting());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createBoundaryMessageEventActivityBehavior(BoundaryEvent, MessageEventDefinition, boolean)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createBoundaryMessageEventActivityBehavior(BoundaryEvent, MessageEventDefinition, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "BoundaryMessageEventActivityBehavior DefaultActivityBehaviorFactory.createBoundaryMessageEventActivityBehavior(BoundaryEvent, MessageEventDefinition, boolean)"})
  public void testCreateBoundaryMessageEventActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();

    // Act
    BoundaryMessageEventActivityBehavior actualCreateBoundaryMessageEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createBoundaryMessageEventActivityBehavior(boundaryEvent, messageEventDefinition, true);

    // Assert
    MessageExecutionContext messageExecutionContext = actualCreateBoundaryMessageEventActivityBehaviorResult
        .getMessageExecutionContext();
    assertTrue(messageExecutionContext instanceof DefaultMessageExecutionContext);
    assertTrue(((DefaultMessageExecutionContext) messageExecutionContext)
        .getMessagePayloadMappingProvider() instanceof BpmnMessagePayloadMappingProvider);
    assertNull(((DefaultMessageExecutionContext) messageExecutionContext).getExpressionManager());
    assertTrue(actualCreateBoundaryMessageEventActivityBehaviorResult.isInterrupting());
    assertSame(messageEventDefinition,
        actualCreateBoundaryMessageEventActivityBehaviorResult.getMessageEventDefinition());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createBoundaryMessageEventActivityBehavior(BoundaryEvent, MessageEventDefinition, boolean)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createBoundaryMessageEventActivityBehavior(BoundaryEvent, MessageEventDefinition, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "BoundaryMessageEventActivityBehavior DefaultActivityBehaviorFactory.createBoundaryMessageEventActivityBehavior(BoundaryEvent, MessageEventDefinition, boolean)"})
  public void testCreateBoundaryMessageEventActivityBehavior_thenThrowActivitiException() {
    // Arrange
    MessageExecutionContextFactory messageExecutionContextFactory = mock(MessageExecutionContextFactory.class);
    when(messageExecutionContextFactory.create(Mockito.<MessageEventDefinition>any(),
        Mockito.<MessagePayloadMappingProvider>any(), Mockito.<ExpressionManager>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setMessageExecutionContextFactory(messageExecutionContextFactory);
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultActivityBehaviorFactory
        .createBoundaryMessageEventActivityBehavior(boundaryEvent, new MessageEventDefinition(), true));
    verify(messageExecutionContextFactory).create(isA(MessageEventDefinition.class),
        isA(MessagePayloadMappingProvider.class), isNull());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createThrowMessageEventActivityBehavior(ThrowEvent, MessageEventDefinition, Message)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createThrowMessageEventActivityBehavior(ThrowEvent, MessageEventDefinition, Message)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "IntermediateThrowMessageEventActivityBehavior DefaultActivityBehaviorFactory.createThrowMessageEventActivityBehavior(ThrowEvent, MessageEventDefinition, Message)"})
  public void testCreateThrowMessageEventActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    ThrowEvent throwEvent = new ThrowEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    Builder builderResult = Message.builder();
    Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();

    // Act
    IntermediateThrowMessageEventActivityBehavior actualCreateThrowMessageEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createThrowMessageEventActivityBehavior(throwEvent, messageEventDefinition, message);

    // Assert
    assertTrue(actualCreateThrowMessageEventActivityBehaviorResult
        .getMessageExecutionContext() instanceof DefaultMessageExecutionContext);
    assertTrue(
        actualCreateThrowMessageEventActivityBehaviorResult.getDelegate() instanceof DefaultThrowMessageJavaDelegate);
    assertSame(messageEventDefinition, actualCreateThrowMessageEventActivityBehaviorResult.getMessageEventDefinition());
    assertSame(throwEvent, actualCreateThrowMessageEventActivityBehaviorResult.getThrowEvent());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createThrowMessageEventActivityBehavior(ThrowEvent, MessageEventDefinition, Message)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createThrowMessageEventActivityBehavior(ThrowEvent, MessageEventDefinition, Message)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "IntermediateThrowMessageEventActivityBehavior DefaultActivityBehaviorFactory.createThrowMessageEventActivityBehavior(ThrowEvent, MessageEventDefinition, Message)"})
  public void testCreateThrowMessageEventActivityBehavior2() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setThrowMessageDelegateFactory(new TestThrowMessageDelegateFactory());
    ThrowEvent throwEvent = new ThrowEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    Builder builderResult = Message.builder();
    Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();

    // Act
    IntermediateThrowMessageEventActivityBehavior actualCreateThrowMessageEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createThrowMessageEventActivityBehavior(throwEvent, messageEventDefinition, message);

    // Assert
    assertTrue(actualCreateThrowMessageEventActivityBehaviorResult
        .getMessageExecutionContext() instanceof DefaultMessageExecutionContext);
    assertTrue(actualCreateThrowMessageEventActivityBehaviorResult.getDelegate() instanceof TestThrowMessageDelegate);
    assertSame(messageEventDefinition, actualCreateThrowMessageEventActivityBehaviorResult.getMessageEventDefinition());
    assertSame(throwEvent, actualCreateThrowMessageEventActivityBehaviorResult.getThrowEvent());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createThrowMessageEventActivityBehavior(ThrowEvent, MessageEventDefinition, Message)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createThrowMessageEventActivityBehavior(ThrowEvent, MessageEventDefinition, Message)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "IntermediateThrowMessageEventActivityBehavior DefaultActivityBehaviorFactory.createThrowMessageEventActivityBehavior(ThrowEvent, MessageEventDefinition, Message)"})
  public void testCreateThrowMessageEventActivityBehavior_thenThrowActivitiException() {
    // Arrange
    MessagePayloadMappingProviderFactory messagePayloadMappingProviderFactory = mock(
        MessagePayloadMappingProviderFactory.class);
    when(messagePayloadMappingProviderFactory.create(Mockito.<Event>any(), Mockito.<MessageEventDefinition>any(),
        Mockito.<ExpressionManager>any())).thenThrow(new ActivitiException("An error occurred"));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setMessagePayloadMappingProviderFactory(messagePayloadMappingProviderFactory);
    ThrowEvent throwEvent = new ThrowEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    Builder builderResult = Message.builder();
    Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultActivityBehaviorFactory
        .createThrowMessageEventActivityBehavior(throwEvent, messageEventDefinition, message));
    verify(messagePayloadMappingProviderFactory).create(isA(Event.class), isA(MessageEventDefinition.class), isNull());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, Message)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, Message)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ThrowMessageEndEventActivityBehavior DefaultActivityBehaviorFactory.createThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, Message)"})
  public void testCreateThrowMessageEndEventActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    EndEvent endEvent = new EndEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    Builder builderResult = Message.builder();
    Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();

    // Act
    ThrowMessageEndEventActivityBehavior actualCreateThrowMessageEndEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createThrowMessageEndEventActivityBehavior(endEvent, messageEventDefinition, message);

    // Assert
    assertTrue(actualCreateThrowMessageEndEventActivityBehaviorResult
        .getMessageExecutionContext() instanceof DefaultMessageExecutionContext);
    assertTrue(actualCreateThrowMessageEndEventActivityBehaviorResult
        .getDelegate() instanceof DefaultThrowMessageJavaDelegate);
    assertSame(endEvent, actualCreateThrowMessageEndEventActivityBehaviorResult.getEndEvent());
    assertSame(messageEventDefinition,
        actualCreateThrowMessageEndEventActivityBehaviorResult.getMessageEventDefinition());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, Message)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, Message)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ThrowMessageEndEventActivityBehavior DefaultActivityBehaviorFactory.createThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, Message)"})
  public void testCreateThrowMessageEndEventActivityBehavior2() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setThrowMessageDelegateFactory(new TestThrowMessageDelegateFactory());
    EndEvent endEvent = new EndEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    Builder builderResult = Message.builder();
    Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();

    // Act
    ThrowMessageEndEventActivityBehavior actualCreateThrowMessageEndEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createThrowMessageEndEventActivityBehavior(endEvent, messageEventDefinition, message);

    // Assert
    assertTrue(actualCreateThrowMessageEndEventActivityBehaviorResult
        .getMessageExecutionContext() instanceof DefaultMessageExecutionContext);
    assertTrue(
        actualCreateThrowMessageEndEventActivityBehaviorResult.getDelegate() instanceof TestThrowMessageDelegate);
    assertSame(endEvent, actualCreateThrowMessageEndEventActivityBehaviorResult.getEndEvent());
    assertSame(messageEventDefinition,
        actualCreateThrowMessageEndEventActivityBehaviorResult.getMessageEventDefinition());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, Message)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, Message)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ThrowMessageEndEventActivityBehavior DefaultActivityBehaviorFactory.createThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, Message)"})
  public void testCreateThrowMessageEndEventActivityBehavior_thenThrowActivitiException() {
    // Arrange
    MessagePayloadMappingProviderFactory messagePayloadMappingProviderFactory = mock(
        MessagePayloadMappingProviderFactory.class);
    when(messagePayloadMappingProviderFactory.create(Mockito.<Event>any(), Mockito.<MessageEventDefinition>any(),
        Mockito.<ExpressionManager>any())).thenThrow(new ActivitiException("An error occurred"));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setMessagePayloadMappingProviderFactory(messagePayloadMappingProviderFactory);
    EndEvent endEvent = new EndEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    Builder builderResult = Message.builder();
    Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultActivityBehaviorFactory
        .createThrowMessageEndEventActivityBehavior(endEvent, messageEventDefinition, message));
    verify(messagePayloadMappingProviderFactory).create(isA(Event.class), isA(MessageEventDefinition.class), isNull());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createThrowMessageDelegate(MessageEventDefinition)}.
   * <ul>
   *   <li>Then return {@link DefaultThrowMessageJavaDelegate}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createThrowMessageDelegate(MessageEventDefinition)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ThrowMessageDelegate DefaultActivityBehaviorFactory.createThrowMessageDelegate(MessageEventDefinition)"})
  public void testCreateThrowMessageDelegate_thenReturnDefaultThrowMessageJavaDelegate() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createThrowMessageDelegate(new MessageEventDefinition()) instanceof DefaultThrowMessageJavaDelegate);
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createThrowMessageDelegate(MessageEventDefinition)}.
   * <ul>
   *   <li>Then return {@link MessageThrowCatchEventTest.TestThrowMessageDelegate}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createThrowMessageDelegate(MessageEventDefinition)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ThrowMessageDelegate DefaultActivityBehaviorFactory.createThrowMessageDelegate(MessageEventDefinition)"})
  public void testCreateThrowMessageDelegate_thenReturnTestThrowMessageDelegate() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setThrowMessageDelegateFactory(new TestThrowMessageDelegateFactory());

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createThrowMessageDelegate(new MessageEventDefinition()) instanceof TestThrowMessageDelegate);
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createMessageExecutionContext(Event, MessageEventDefinition)}.
   * <ul>
   *   <li>Then return {@link DefaultMessageExecutionContext}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createMessageExecutionContext(Event, MessageEventDefinition)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "MessageExecutionContext DefaultActivityBehaviorFactory.createMessageExecutionContext(Event, MessageEventDefinition)"})
  public void testCreateMessageExecutionContext_thenReturnDefaultMessageExecutionContext() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();

    // Act
    MessageExecutionContext actualCreateMessageExecutionContextResult = defaultActivityBehaviorFactory
        .createMessageExecutionContext(bpmnEvent, new MessageEventDefinition());

    // Assert
    assertTrue(actualCreateMessageExecutionContextResult instanceof DefaultMessageExecutionContext);
    assertTrue(((DefaultMessageExecutionContext) actualCreateMessageExecutionContextResult)
        .getMessagePayloadMappingProvider() instanceof BpmnMessagePayloadMappingProvider);
    assertNull(((DefaultMessageExecutionContext) actualCreateMessageExecutionContextResult).getExpressionManager());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createMessageExecutionContext(Event, MessageEventDefinition)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createMessageExecutionContext(Event, MessageEventDefinition)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "MessageExecutionContext DefaultActivityBehaviorFactory.createMessageExecutionContext(Event, MessageEventDefinition)"})
  public void testCreateMessageExecutionContext_thenThrowActivitiException() {
    // Arrange
    MessageExecutionContextFactory messageExecutionContextFactory = mock(MessageExecutionContextFactory.class);
    when(messageExecutionContextFactory.create(Mockito.<MessageEventDefinition>any(),
        Mockito.<MessagePayloadMappingProvider>any(), Mockito.<ExpressionManager>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setMessageExecutionContextFactory(messageExecutionContextFactory);
    BoundaryEvent bpmnEvent = new BoundaryEvent();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createMessageExecutionContext(bpmnEvent, new MessageEventDefinition()));
    verify(messageExecutionContextFactory).create(isA(MessageEventDefinition.class),
        isA(MessagePayloadMappingProvider.class), isNull());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ThrowMessageDelegate DefaultActivityBehaviorFactory.createThrowMessageDelegateExpression(String)"})
  public void testCreateThrowMessageDelegateExpression() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createThrowMessageDelegateExpression("Delegate Expression") instanceof ThrowMessageDelegateExpression);
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ThrowMessageDelegate DefaultActivityBehaviorFactory.createThrowMessageDelegateExpression(String)"})
  public void testCreateThrowMessageDelegateExpression2() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createThrowMessageDelegateExpression("Delegate Expression") instanceof ThrowMessageDelegateExpression);
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ThrowMessageDelegate DefaultActivityBehaviorFactory.createThrowMessageDelegateExpression(String)"})
  public void testCreateThrowMessageDelegateExpression3() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider3 = mock(CustomFunctionProvider.class);
    doThrow(new ActivitiException("An error occurred")).when(customFunctionProvider3)
        .addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider3);
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    // Act
    ThrowMessageDelegate actualCreateThrowMessageDelegateExpressionResult = defaultActivityBehaviorFactory
        .createThrowMessageDelegateExpression("Delegate Expression");

    // Assert
    verify(customFunctionProvider3).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualCreateThrowMessageDelegateExpressionResult instanceof ThrowMessageDelegateExpression);
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}.
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ThrowMessageDelegate DefaultActivityBehaviorFactory.createThrowMessageDelegateExpression(String)"})
  public void testCreateThrowMessageDelegateExpression_thenCallsAddCustomFunctions() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    // Act
    ThrowMessageDelegate actualCreateThrowMessageDelegateExpressionResult = defaultActivityBehaviorFactory
        .createThrowMessageDelegateExpression("Delegate Expression");

    // Assert
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualCreateThrowMessageDelegateExpressionResult instanceof ThrowMessageDelegateExpression);
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}.
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ThrowMessageDelegate DefaultActivityBehaviorFactory.createThrowMessageDelegateExpression(String)"})
  public void testCreateThrowMessageDelegateExpression_thenCallsAddCustomFunctions2() {
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

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    // Act
    ThrowMessageDelegate actualCreateThrowMessageDelegateExpressionResult = defaultActivityBehaviorFactory
        .createThrowMessageDelegateExpression("Delegate Expression");

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualCreateThrowMessageDelegateExpressionResult instanceof ThrowMessageDelegateExpression);
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}.
   * <ul>
   *   <li>Then calls {@link ExpressionManager#createExpression(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ThrowMessageDelegate DefaultActivityBehaviorFactory.createThrowMessageDelegateExpression(String)"})
  public void testCreateThrowMessageDelegateExpression_thenCallsCreateExpression() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    // Act
    ThrowMessageDelegate actualCreateThrowMessageDelegateExpressionResult = defaultActivityBehaviorFactory
        .createThrowMessageDelegateExpression("Delegate Expression");

    // Assert
    verify(expressionManager).createExpression(eq("Delegate Expression"));
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertTrue(actualCreateThrowMessageDelegateExpressionResult instanceof ThrowMessageDelegateExpression);
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createDefaultThrowMessageDelegate()}.
   * <ul>
   *   <li>Then return {@link DefaultThrowMessageJavaDelegate}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createDefaultThrowMessageDelegate()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ThrowMessageDelegate DefaultActivityBehaviorFactory.createDefaultThrowMessageDelegate()"})
  public void testCreateDefaultThrowMessageDelegate_thenReturnDefaultThrowMessageJavaDelegate() {
    // Arrange, Act and Assert
    assertTrue((new DefaultActivityBehaviorFactory())
        .createDefaultThrowMessageDelegate() instanceof DefaultThrowMessageJavaDelegate);
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createDefaultThrowMessageDelegate()}.
   * <ul>
   *   <li>Then return {@link MessageThrowCatchEventTest.TestThrowMessageDelegate}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createDefaultThrowMessageDelegate()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ThrowMessageDelegate DefaultActivityBehaviorFactory.createDefaultThrowMessageDelegate()"})
  public void testCreateDefaultThrowMessageDelegate_thenReturnTestThrowMessageDelegate() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setThrowMessageDelegateFactory(new TestThrowMessageDelegateFactory());

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory.createDefaultThrowMessageDelegate() instanceof TestThrowMessageDelegate);
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createMessagePayloadMappingProvider(Event, MessageEventDefinition)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#createMessagePayloadMappingProvider(Event, MessageEventDefinition)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "MessagePayloadMappingProvider DefaultActivityBehaviorFactory.createMessagePayloadMappingProvider(Event, MessageEventDefinition)"})
  public void testCreateMessagePayloadMappingProvider() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory.createMessagePayloadMappingProvider(bpmnEvent,
        new MessageEventDefinition()) instanceof BpmnMessagePayloadMappingProvider);
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#checkClassDelegate(Map)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#checkClassDelegate(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.Optional DefaultActivityBehaviorFactory.checkClassDelegate(Map)"})
  public void testCheckClassDelegate() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkClassDelegate(new HashMap<>()).isPresent());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#checkDelegateExpression(Map)}.
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#checkDelegateExpression(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.Optional DefaultActivityBehaviorFactory.checkDelegateExpression(Map)"})
  public void testCheckDelegateExpression() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkDelegateExpression(new HashMap<>()).isPresent());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ExtensionAttribute#ExtensionAttribute(String)} with name is {@code activiti}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.Optional DefaultActivityBehaviorFactory.getAttributeValue(Map, String)"})
  public void testGetAttributeValue_givenArrayListAddExtensionAttributeWithNameIsActiviti() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("activiti"));

    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("activiti", extensionAttributeList);

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.getAttributeValue(attributes, "Name").isPresent());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ExtensionAttribute#ExtensionAttribute(String)} with name is {@code activiti}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.Optional DefaultActivityBehaviorFactory.getAttributeValue(Map, String)"})
  public void testGetAttributeValue_givenArrayListAddExtensionAttributeWithNameIsActiviti2() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("activiti"));
    extensionAttributeList.add(new ExtensionAttribute("activiti"));

    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("activiti", extensionAttributeList);

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.getAttributeValue(attributes, "Name").isPresent());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.Optional DefaultActivityBehaviorFactory.getAttributeValue(Map, String)"})
  public void testGetAttributeValue_givenArrayList_thenReturnNotPresent() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("activiti", new ArrayList<>());

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.getAttributeValue(attributes, "Name").isPresent());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.Optional DefaultActivityBehaviorFactory.getAttributeValue(Map, String)"})
  public void testGetAttributeValue_whenHashMap_thenReturnNotPresent() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.getAttributeValue(new HashMap<>(), "Name").isPresent());
  }
}
