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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
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
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.BoundaryMessageEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.CallActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.IntermediateCatchMessageEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.IntermediateThrowMessageEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.MailActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.ScriptTaskActivityBehavior;
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
import org.activiti.engine.impl.delegate.ThrowMessageDelegate;
import org.activiti.engine.impl.delegate.ThrowMessageDelegateExpression;
import org.activiti.engine.impl.delegate.ThrowMessageJavaDelegate;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultActivityBehaviorFactoryDiffblueTest {
  @InjectMocks
  private DefaultActivityBehaviorFactory defaultActivityBehaviorFactory;

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createTaskActivityBehavior(Task)}
   */
  @Test
  public void testCreateTaskActivityBehavior() {
    // Arrange, Act and Assert
    assertNull(
        defaultActivityBehaviorFactory.createTaskActivityBehavior(new Task()).getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createTaskActivityBehavior(mock(BusinessRuleTask.class))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createManualTaskActivityBehavior(ManualTask)}
   */
  @Test
  public void testCreateManualTaskActivityBehavior() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createManualTaskActivityBehavior(new ManualTask())
        .getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createManualTaskActivityBehavior(mock(ManualTask.class))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createReceiveTaskActivityBehavior(ReceiveTask)}
   */
  @Test
  public void testCreateReceiveTaskActivityBehavior() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createReceiveTaskActivityBehavior(new ReceiveTask())
        .getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createReceiveTaskActivityBehavior(mock(ReceiveTask.class))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createUserTaskActivityBehavior(UserTask)}
   */
  @Test
  public void testCreateUserTaskActivityBehavior() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createUserTaskActivityBehavior(new UserTask())
        .getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createUserTaskActivityBehavior(null).getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createUserTaskActivityBehavior(mock(UserTask.class))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}
   */
  @Test
  public void testGetSkipExpressionFromServiceTask() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.getSkipExpressionFromServiceTask(new ServiceTask()));
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}
   */
  @Test
  public void testGetSkipExpressionFromServiceTask2() {
    // Arrange
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getSkipExpression()).thenReturn("");

    // Act
    Expression actualSkipExpressionFromServiceTask = defaultActivityBehaviorFactory
        .getSkipExpressionFromServiceTask(serviceTask);

    // Assert
    verify(serviceTask).getSkipExpression();
    assertNull(actualSkipExpressionFromServiceTask);
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  public void testCreateClassDelegateServiceTask() {
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  public void testCreateClassDelegateServiceTask2() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(new ExpressionManager());
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getImplementation()).thenReturn("Implementation");
    when(serviceTask.getSkipExpression()).thenReturn("Skip Expression");
    when(serviceTask.getMapExceptions()).thenReturn(new ArrayList<>());
    when(serviceTask.getFieldExtensions()).thenReturn(new ArrayList<>());

    // Act
    ClassDelegate actualCreateClassDelegateServiceTaskResult = defaultActivityBehaviorFactory
        .createClassDelegateServiceTask(serviceTask);

    // Assert
    verify(serviceTask).getMapExceptions();
    verify(serviceTask).getId();
    verify(serviceTask).getImplementation();
    verify(serviceTask, atLeast(1)).getSkipExpression();
    verify(serviceTask).getFieldExtensions();
    assertEquals("Implementation", actualCreateClassDelegateServiceTaskResult.getClassName());
    assertNull(actualCreateClassDelegateServiceTaskResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  public void testCreateClassDelegateServiceTask3() {
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  public void testCreateClassDelegateServiceTask4() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

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
    assertEquals("Implementation", actualCreateClassDelegateServiceTaskResult.getClassName());
    assertNull(actualCreateClassDelegateServiceTaskResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  public void testCreateClassDelegateServiceTask5() {
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  public void testCreateClassDelegateServiceTask6() {
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
    when(serviceTask.getSkipExpression()).thenReturn("");
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
    verify(serviceTask).getSkipExpression();
    verify(serviceTask).getFieldExtensions();
    verify(expressionManager).createExpression(eq("Expression"));
    assertEquals("Implementation", actualCreateClassDelegateServiceTaskResult.getClassName());
    assertNull(actualCreateClassDelegateServiceTaskResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  public void testCreateClassDelegateServiceTask7() {
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateServiceTaskDelegateExpressionActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(new ExpressionManager());
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getSkipExpression()).thenReturn("Skip Expression");
    when(serviceTask.getFieldExtensions()).thenReturn(new ArrayList<>());
    when(serviceTask.getImplementation()).thenReturn("Implementation");

    // Act
    ServiceTaskDelegateExpressionActivityBehavior actualCreateServiceTaskDelegateExpressionActivityBehaviorResult = defaultActivityBehaviorFactory
        .createServiceTaskDelegateExpressionActivityBehavior(serviceTask);

    // Assert
    verify(serviceTask).getId();
    verify(serviceTask).getImplementation();
    verify(serviceTask, atLeast(1)).getSkipExpression();
    verify(serviceTask).getFieldExtensions();
    assertNull(actualCreateServiceTaskDelegateExpressionActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateServiceTaskDelegateExpressionActivityBehavior2() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getSkipExpression()).thenReturn("Skip Expression");
    when(serviceTask.getFieldExtensions()).thenReturn(new ArrayList<>());
    when(serviceTask.getImplementation()).thenReturn("Implementation");

    // Act
    ServiceTaskDelegateExpressionActivityBehavior actualCreateServiceTaskDelegateExpressionActivityBehaviorResult = defaultActivityBehaviorFactory
        .createServiceTaskDelegateExpressionActivityBehavior(serviceTask);

    // Assert
    verify(serviceTask).getId();
    verify(serviceTask).getImplementation();
    verify(serviceTask, atLeast(1)).getSkipExpression();
    verify(serviceTask).getFieldExtensions();
    assertNull(actualCreateServiceTaskDelegateExpressionActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateServiceTaskDelegateExpressionActivityBehavior3() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getSkipExpression()).thenReturn("Skip Expression");
    when(serviceTask.getFieldExtensions()).thenReturn(new ArrayList<>());
    when(serviceTask.getImplementation()).thenReturn("Implementation");

    // Act
    ServiceTaskDelegateExpressionActivityBehavior actualCreateServiceTaskDelegateExpressionActivityBehaviorResult = defaultActivityBehaviorFactory
        .createServiceTaskDelegateExpressionActivityBehavior(serviceTask);

    // Assert
    verify(serviceTask).getId();
    verify(serviceTask).getImplementation();
    verify(serviceTask, atLeast(1)).getSkipExpression();
    verify(serviceTask).getFieldExtensions();
    verify(expressionManager, atLeast(1)).createExpression(Mockito.<String>any());
    assertNull(actualCreateServiceTaskDelegateExpressionActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateServiceTaskDelegateExpressionActivityBehavior4() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getSkipExpression()).thenReturn("");
    when(serviceTask.getFieldExtensions()).thenReturn(new ArrayList<>());
    when(serviceTask.getImplementation()).thenReturn("Implementation");

    // Act
    ServiceTaskDelegateExpressionActivityBehavior actualCreateServiceTaskDelegateExpressionActivityBehaviorResult = defaultActivityBehaviorFactory
        .createServiceTaskDelegateExpressionActivityBehavior(serviceTask);

    // Assert
    verify(serviceTask).getId();
    verify(serviceTask).getImplementation();
    verify(serviceTask).getSkipExpression();
    verify(serviceTask).getFieldExtensions();
    verify(expressionManager).createExpression(eq("Implementation"));
    assertNull(actualCreateServiceTaskDelegateExpressionActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateServiceTaskDelegateExpressionActivityBehavior5() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(new FieldExtension());
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getSkipExpression()).thenReturn("Skip Expression");
    when(serviceTask.getFieldExtensions()).thenReturn(fieldExtensionList);
    when(serviceTask.getImplementation()).thenReturn("Implementation");

    // Act
    ServiceTaskDelegateExpressionActivityBehavior actualCreateServiceTaskDelegateExpressionActivityBehaviorResult = defaultActivityBehaviorFactory
        .createServiceTaskDelegateExpressionActivityBehavior(serviceTask);

    // Assert
    verify(serviceTask).getId();
    verify(serviceTask).getImplementation();
    verify(serviceTask, atLeast(1)).getSkipExpression();
    verify(serviceTask).getFieldExtensions();
    verify(expressionManager, atLeast(1)).createExpression(Mockito.<String>any());
    assertNull(actualCreateServiceTaskDelegateExpressionActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateServiceTaskDelegateExpressionActivityBehavior6() {
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
    when(serviceTask.getSkipExpression()).thenReturn("Skip Expression");
    when(serviceTask.getFieldExtensions()).thenReturn(fieldExtensionList);
    when(serviceTask.getImplementation()).thenReturn("Implementation");

    // Act
    ServiceTaskDelegateExpressionActivityBehavior actualCreateServiceTaskDelegateExpressionActivityBehaviorResult = defaultActivityBehaviorFactory
        .createServiceTaskDelegateExpressionActivityBehavior(serviceTask);

    // Assert
    verify(serviceTask).getId();
    verify(fieldExtension, atLeast(1)).getExpression();
    verify(fieldExtension).getFieldName();
    verify(serviceTask).getImplementation();
    verify(serviceTask, atLeast(1)).getSkipExpression();
    verify(serviceTask).getFieldExtensions();
    verify(expressionManager, atLeast(1)).createExpression(Mockito.<String>any());
    assertNull(actualCreateServiceTaskDelegateExpressionActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  public void testCreateDefaultServiceTaskBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(new ExpressionManager());

    // Act
    ActivityBehavior actualCreateDefaultServiceTaskBehaviorResult = defaultActivityBehaviorFactory
        .createDefaultServiceTaskBehavior(new ServiceTask());

    // Assert
    assertTrue(actualCreateDefaultServiceTaskBehaviorResult instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) actualCreateDefaultServiceTaskBehaviorResult)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  public void testCreateDefaultServiceTaskBehavior2() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    // Act
    ActivityBehavior actualCreateDefaultServiceTaskBehaviorResult = defaultActivityBehaviorFactory
        .createDefaultServiceTaskBehavior(new ServiceTask());

    // Assert
    assertTrue(actualCreateDefaultServiceTaskBehaviorResult instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) actualCreateDefaultServiceTaskBehaviorResult)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  public void testCreateDefaultServiceTaskBehavior3() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    // Act
    ActivityBehavior actualCreateDefaultServiceTaskBehaviorResult = defaultActivityBehaviorFactory
        .createDefaultServiceTaskBehavior(new ServiceTask());

    // Assert
    verify(expressionManager).createExpression(eq("${defaultServiceTaskBehavior}"));
    assertTrue(actualCreateDefaultServiceTaskBehaviorResult instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) actualCreateDefaultServiceTaskBehaviorResult)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  public void testCreateDefaultServiceTaskBehavior4() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getSkipExpression()).thenReturn("Skip Expression");
    when(serviceTask.getFieldExtensions()).thenReturn(new ArrayList<>());

    // Act
    ActivityBehavior actualCreateDefaultServiceTaskBehaviorResult = defaultActivityBehaviorFactory
        .createDefaultServiceTaskBehavior(serviceTask);

    // Assert
    verify(serviceTask).getId();
    verify(serviceTask, atLeast(1)).getSkipExpression();
    verify(serviceTask).getFieldExtensions();
    verify(expressionManager, atLeast(1)).createExpression(Mockito.<String>any());
    assertTrue(actualCreateDefaultServiceTaskBehaviorResult instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) actualCreateDefaultServiceTaskBehaviorResult)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  public void testCreateDefaultServiceTaskBehavior5() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getSkipExpression()).thenReturn("");
    when(serviceTask.getFieldExtensions()).thenReturn(new ArrayList<>());

    // Act
    ActivityBehavior actualCreateDefaultServiceTaskBehaviorResult = defaultActivityBehaviorFactory
        .createDefaultServiceTaskBehavior(serviceTask);

    // Assert
    verify(serviceTask).getId();
    verify(serviceTask).getSkipExpression();
    verify(serviceTask).getFieldExtensions();
    verify(expressionManager).createExpression(eq("${defaultServiceTaskBehavior}"));
    assertTrue(actualCreateDefaultServiceTaskBehaviorResult instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) actualCreateDefaultServiceTaskBehaviorResult)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  public void testCreateDefaultServiceTaskBehavior6() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(new FieldExtension());
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getSkipExpression()).thenReturn("Skip Expression");
    when(serviceTask.getFieldExtensions()).thenReturn(fieldExtensionList);

    // Act
    ActivityBehavior actualCreateDefaultServiceTaskBehaviorResult = defaultActivityBehaviorFactory
        .createDefaultServiceTaskBehavior(serviceTask);

    // Assert
    verify(serviceTask).getId();
    verify(serviceTask, atLeast(1)).getSkipExpression();
    verify(serviceTask).getFieldExtensions();
    verify(expressionManager, atLeast(1)).createExpression(Mockito.<String>any());
    assertTrue(actualCreateDefaultServiceTaskBehaviorResult instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) actualCreateDefaultServiceTaskBehaviorResult)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  public void testCreateDefaultServiceTaskBehavior7() {
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
    when(serviceTask.getSkipExpression()).thenReturn("Skip Expression");
    when(serviceTask.getFieldExtensions()).thenReturn(fieldExtensionList);

    // Act
    ActivityBehavior actualCreateDefaultServiceTaskBehaviorResult = defaultActivityBehaviorFactory
        .createDefaultServiceTaskBehavior(serviceTask);

    // Assert
    verify(serviceTask).getId();
    verify(fieldExtension, atLeast(1)).getExpression();
    verify(fieldExtension).getFieldName();
    verify(serviceTask, atLeast(1)).getSkipExpression();
    verify(serviceTask).getFieldExtensions();
    verify(expressionManager, atLeast(1)).createExpression(Mockito.<String>any());
    assertTrue(actualCreateDefaultServiceTaskBehaviorResult instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) actualCreateDefaultServiceTaskBehaviorResult)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createServiceTaskExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateServiceTaskExpressionActivityBehavior() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(new ExpressionManager());
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getResultVariableName()).thenReturn("Result Variable Name");
    when(serviceTask.getSkipExpression()).thenReturn("Skip Expression");
    when(serviceTask.getImplementation()).thenReturn("Implementation");

    // Act
    ServiceTaskExpressionActivityBehavior actualCreateServiceTaskExpressionActivityBehaviorResult = defaultActivityBehaviorFactory
        .createServiceTaskExpressionActivityBehavior(serviceTask);

    // Assert
    verify(serviceTask).getId();
    verify(serviceTask).getImplementation();
    verify(serviceTask).getResultVariableName();
    verify(serviceTask, atLeast(1)).getSkipExpression();
    assertNull(actualCreateServiceTaskExpressionActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createServiceTaskExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateServiceTaskExpressionActivityBehavior2() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getResultVariableName()).thenReturn("Result Variable Name");
    when(serviceTask.getSkipExpression()).thenReturn("Skip Expression");
    when(serviceTask.getImplementation()).thenReturn("Implementation");

    // Act
    ServiceTaskExpressionActivityBehavior actualCreateServiceTaskExpressionActivityBehaviorResult = defaultActivityBehaviorFactory
        .createServiceTaskExpressionActivityBehavior(serviceTask);

    // Assert
    verify(serviceTask).getId();
    verify(serviceTask).getImplementation();
    verify(serviceTask).getResultVariableName();
    verify(serviceTask, atLeast(1)).getSkipExpression();
    assertNull(actualCreateServiceTaskExpressionActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createServiceTaskExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateServiceTaskExpressionActivityBehavior3() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getResultVariableName()).thenReturn("Result Variable Name");
    when(serviceTask.getSkipExpression()).thenReturn("Skip Expression");
    when(serviceTask.getImplementation()).thenReturn("Implementation");

    // Act
    ServiceTaskExpressionActivityBehavior actualCreateServiceTaskExpressionActivityBehaviorResult = defaultActivityBehaviorFactory
        .createServiceTaskExpressionActivityBehavior(serviceTask);

    // Assert
    verify(serviceTask).getId();
    verify(serviceTask).getImplementation();
    verify(serviceTask).getResultVariableName();
    verify(serviceTask, atLeast(1)).getSkipExpression();
    verify(expressionManager, atLeast(1)).createExpression(Mockito.<String>any());
    assertNull(actualCreateServiceTaskExpressionActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createServiceTaskExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateServiceTaskExpressionActivityBehavior4() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getResultVariableName()).thenReturn("Result Variable Name");
    when(serviceTask.getSkipExpression()).thenReturn("");
    when(serviceTask.getImplementation()).thenReturn("Implementation");

    // Act
    ServiceTaskExpressionActivityBehavior actualCreateServiceTaskExpressionActivityBehaviorResult = defaultActivityBehaviorFactory
        .createServiceTaskExpressionActivityBehavior(serviceTask);

    // Assert
    verify(serviceTask).getId();
    verify(serviceTask).getImplementation();
    verify(serviceTask).getResultVariableName();
    verify(serviceTask).getSkipExpression();
    verify(expressionManager).createExpression(eq("Implementation"));
    assertNull(actualCreateServiceTaskExpressionActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createWebServiceActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateWebServiceActivityBehavior() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createWebServiceActivityBehavior(new SendTask())
        .getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createWebServiceActivityBehavior(mock(SendTask.class))
        .getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createWebServiceActivityBehavior(new ServiceTask())
        .getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createWebServiceActivityBehavior(mock(ServiceTask.class))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(String, List)}
   */
  @Test
  public void testCreateMailActivityBehavior() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createMailActivityBehavior("42", new ArrayList<>())
        .getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory
        .createMailActivityBehavior("Trying to load class with current thread context classloader: {}",
            new ArrayList<>())
        .getMultiInstanceActivityBehavior());
    assertNull(
        defaultActivityBehaviorFactory.createMailActivityBehavior(new SendTask()).getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createMailActivityBehavior(new ServiceTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateMailActivityBehavior2() {
    // Arrange
    SendTask sendTask = mock(SendTask.class);
    when(sendTask.getId()).thenReturn("42");
    when(sendTask.getFieldExtensions()).thenReturn(new ArrayList<>());

    // Act
    MailActivityBehavior actualCreateMailActivityBehaviorResult = defaultActivityBehaviorFactory
        .createMailActivityBehavior(sendTask);

    // Assert
    verify(sendTask).getId();
    verify(sendTask).getFieldExtensions();
    assertNull(actualCreateMailActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateMailActivityBehavior3() {
    // Arrange
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getFieldExtensions()).thenReturn(new ArrayList<>());

    // Act
    MailActivityBehavior actualCreateMailActivityBehaviorResult = defaultActivityBehaviorFactory
        .createMailActivityBehavior(serviceTask);

    // Assert
    verify(serviceTask).getId();
    verify(serviceTask).getFieldExtensions();
    assertNull(actualCreateMailActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateMuleActivityBehavior() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createMuleActivityBehavior(new SendTask()));
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createMuleActivityBehavior(new ServiceTask()));
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateMuleActivityBehavior2() {
    // Arrange
    SendTask task = new SendTask();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createMuleActivityBehavior(task, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateMuleActivityBehavior3() {
    // Arrange
    SendTask task = mock(SendTask.class);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createMuleActivityBehavior(task, fieldExtensions));
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateMuleActivityBehavior4() {
    // Arrange
    SendTask task = mock(SendTask.class);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());
    fieldExtensions.add(new FieldExtension());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createMuleActivityBehavior(task, fieldExtensions));
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateCamelActivityBehavior() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(new SendTask()));
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(new ServiceTask()));
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateCamelActivityBehavior2() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(new FieldExtension());
    SendTask sendTask = mock(SendTask.class);
    when(sendTask.getFieldExtensions()).thenReturn(fieldExtensionList);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(sendTask));
    verify(sendTask).getFieldExtensions();
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateCamelActivityBehavior3() {
    // Arrange
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("camelBehaviorClass");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    SendTask sendTask = mock(SendTask.class);
    when(sendTask.getFieldExtensions()).thenReturn(fieldExtensionList);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(sendTask));
    verify(sendTask).getFieldExtensions();
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateCamelActivityBehavior4() {
    // Arrange
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("42");
    fieldExtension.setFieldName("camelBehaviorClass");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    SendTask sendTask = mock(SendTask.class);
    when(sendTask.getFieldExtensions()).thenReturn(fieldExtensionList);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(sendTask));
    verify(sendTask).getFieldExtensions();
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateCamelActivityBehavior5() {
    // Arrange
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("");
    fieldExtension.setFieldName("camelBehaviorClass");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    SendTask sendTask = mock(SendTask.class);
    when(sendTask.getFieldExtensions()).thenReturn(fieldExtensionList);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(sendTask));
    verify(sendTask).getFieldExtensions();
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateCamelActivityBehavior6() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(new FieldExtension());
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getFieldExtensions()).thenReturn(fieldExtensionList);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(serviceTask));
    verify(serviceTask).getFieldExtensions();
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateCamelActivityBehavior7() {
    // Arrange
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("camelBehaviorClass");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getFieldExtensions()).thenReturn(fieldExtensionList);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(serviceTask));
    verify(serviceTask).getFieldExtensions();
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateCamelActivityBehavior8() {
    // Arrange
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("42");
    fieldExtension.setFieldName("camelBehaviorClass");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getFieldExtensions()).thenReturn(fieldExtensionList);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(serviceTask));
    verify(serviceTask).getFieldExtensions();
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateCamelActivityBehavior9() {
    // Arrange
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("");
    fieldExtension.setFieldName("camelBehaviorClass");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getFieldExtensions()).thenReturn(fieldExtensionList);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(serviceTask));
    verify(serviceTask).getFieldExtensions();
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateCamelActivityBehavior10() {
    // Arrange
    SendTask task = new SendTask();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(task, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateCamelActivityBehavior11() {
    // Arrange
    SendTask task = mock(SendTask.class);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(task, fieldExtensions));
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateCamelActivityBehavior12() {
    // Arrange
    SendTask task = mock(SendTask.class);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());
    fieldExtensions.add(new FieldExtension());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(task, fieldExtensions));
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateCamelActivityBehavior13() {
    // Arrange
    SendTask task = mock(SendTask.class);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("camelBehaviorClass");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(task, fieldExtensions));
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateCamelActivityBehavior14() {
    // Arrange
    SendTask task = mock(SendTask.class);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("42");
    fieldExtension.setFieldName("camelBehaviorClass");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(task, fieldExtensions));
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateCamelActivityBehavior15() {
    // Arrange
    SendTask task = mock(SendTask.class);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("");
    fieldExtension.setFieldName("camelBehaviorClass");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(task, fieldExtensions));
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createShellActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateShellActivityBehavior() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createShellActivityBehavior(new ServiceTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createShellActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateShellActivityBehavior2() {
    // Arrange
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getFieldExtensions()).thenReturn(new ArrayList<>());

    // Act
    ShellActivityBehavior actualCreateShellActivityBehaviorResult = defaultActivityBehaviorFactory
        .createShellActivityBehavior(serviceTask);

    // Assert
    verify(serviceTask).getFieldExtensions();
    assertNull(actualCreateShellActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createShellActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateShellActivityBehavior3() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn("");
    when(fieldExtension.getFieldName()).thenReturn("wait");
    when(fieldExtension.getStringValue()).thenReturn("42");

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(fieldExtension);
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getFieldExtensions()).thenReturn(fieldExtensionList);

    // Act
    ShellActivityBehavior actualCreateShellActivityBehaviorResult = defaultActivityBehaviorFactory
        .createShellActivityBehavior(serviceTask);

    // Assert
    verify(fieldExtension).getExpression();
    verify(fieldExtension).getFieldName();
    verify(fieldExtension).getStringValue();
    verify(serviceTask).getFieldExtensions();
    assertNull(actualCreateShellActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBusinessRuleTaskActivityBehavior(BusinessRuleTask)}
   */
  @Test
  public void testCreateBusinessRuleTaskActivityBehavior() {
    // Arrange
    BusinessRuleTask businessRuleTask = mock(BusinessRuleTask.class);
    when(businessRuleTask.getId()).thenReturn("42");
    when(businessRuleTask.getClassName()).thenReturn("Class Name");

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createBusinessRuleTaskActivityBehavior(businessRuleTask));
    verify(businessRuleTask).getId();
    verify(businessRuleTask, atLeast(1)).getClassName();
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createScriptTaskActivityBehavior(ScriptTask)}
   */
  @Test
  public void testCreateScriptTaskActivityBehavior() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createScriptTaskActivityBehavior(new ScriptTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createScriptTaskActivityBehavior(ScriptTask)}
   */
  @Test
  public void testCreateScriptTaskActivityBehavior2() {
    // Arrange
    ScriptTask scriptTask = mock(ScriptTask.class);
    when(scriptTask.isAutoStoreVariables()).thenReturn(true);
    when(scriptTask.getId()).thenReturn("42");
    when(scriptTask.getResultVariable()).thenReturn("Result Variable");
    when(scriptTask.getScript()).thenReturn("Script");
    when(scriptTask.getScriptFormat()).thenReturn("Script Format");

    // Act
    ScriptTaskActivityBehavior actualCreateScriptTaskActivityBehaviorResult = defaultActivityBehaviorFactory
        .createScriptTaskActivityBehavior(scriptTask);

    // Assert
    verify(scriptTask).getId();
    verify(scriptTask).getResultVariable();
    verify(scriptTask).getScript();
    verify(scriptTask).getScriptFormat();
    verify(scriptTask).isAutoStoreVariables();
    assertNull(actualCreateScriptTaskActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createSequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testCreateSequentialMultiInstanceBehavior() {
    // Arrange
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createSequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testCreateSequentialMultiInstanceBehavior2() {
    // Arrange
    AdhocSubProcess activity = mock(AdhocSubProcess.class);
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createSequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testCreateSequentialMultiInstanceBehavior3() {
    // Arrange
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testCreateParallelMultiInstanceBehavior() {
    // Arrange
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testCreateParallelMultiInstanceBehavior2() {
    // Arrange
    AdhocSubProcess activity = mock(AdhocSubProcess.class);
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testCreateParallelMultiInstanceBehavior3() {
    // Arrange
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createSubprocessActivityBehavior(SubProcess)}
   */
  @Test
  public void testCreateSubprocessActivityBehavior() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createSubprocessActivityBehavior(new SubProcess())
        .getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createSubprocessActivityBehavior(mock(AdhocSubProcess.class))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createEventSubProcessErrorStartEventActivityBehavior(StartEvent)}
   */
  @Test
  public void testCreateEventSubProcessErrorStartEventActivityBehavior() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createEventSubProcessErrorStartEventActivityBehavior(new StartEvent())
        .getMultiInstanceActivityBehavior());
    assertNull(
        defaultActivityBehaviorFactory.createEventSubProcessErrorStartEventActivityBehavior(mock(StartEvent.class))
            .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)}
   */
  @Test
  public void testCreateEventSubProcessMessageStartEventActivityBehavior() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createEventSubProcessMessageStartEventActivityBehavior(startEvent, new MessageEventDefinition())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)}
   */
  @Test
  public void testCreateEventSubProcessMessageStartEventActivityBehavior2() {
    // Arrange
    StartEvent startEvent = mock(StartEvent.class);

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createEventSubProcessMessageStartEventActivityBehavior(startEvent, new MessageEventDefinition())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)}
   */
  @Test
  public void testCreateEventSubProcessMessageStartEventActivityBehavior3() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createEventSubProcessMessageStartEventActivityBehavior(startEvent, messageEventDefinition)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)}
   */
  @Test
  public void testCreateEventSubProcessMessageStartEventActivityBehavior4() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());
    fieldExtensions.add(new FieldExtension());

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createEventSubProcessMessageStartEventActivityBehavior(startEvent, messageEventDefinition)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createAdhocSubprocessActivityBehavior(SubProcess)}
   */
  @Test
  public void testCreateAdhocSubprocessActivityBehavior() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createAdhocSubprocessActivityBehavior(new SubProcess())
        .getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createAdhocSubprocessActivityBehavior(mock(AdhocSubProcess.class))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   */
  @Test
  public void testCreateCallActivityBehavior() {
    // Arrange and Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior("Called Element", new ArrayList<>());

    // Assert
    assertEquals("Called Element", actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   */
  @Test
  public void testCreateCallActivityBehavior2() {
    // Arrange
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   */
  @Test
  public void testCreateCallActivityBehavior3() {
    // Arrange
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   */
  @Test
  public void testCreateCallActivityBehavior4() {
    // Arrange
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(mock(MapExceptionEntry.class));

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior("Called Element", mapExceptions);

    // Assert
    assertEquals("Called Element", actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}
   */
  @Test
  public void testCreateCallActivityBehavior5() {
    // Arrange and Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior(new CallActivity());

    // Assert
    assertNull(actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}
   */
  @Test
  public void testCreateCallActivityBehavior6() {
    // Arrange
    CallActivity callActivity = mock(CallActivity.class);
    when(callActivity.getCalledElement()).thenReturn("Called Element");
    when(callActivity.getMapExceptions()).thenReturn(new ArrayList<>());

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior(callActivity);

    // Assert
    verify(callActivity).getMapExceptions();
    verify(callActivity, atLeast(1)).getCalledElement();
    assertEquals("Called Element", actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}
   */
  @Test
  public void testCreateCallActivityBehavior7() {
    // Arrange
    CallActivity callActivity = mock(CallActivity.class);
    when(callActivity.getCalledElement()).thenReturn("");
    when(callActivity.getMapExceptions()).thenReturn(new ArrayList<>());

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior(callActivity);

    // Assert
    verify(callActivity).getMapExceptions();
    verify(callActivity, atLeast(1)).getCalledElement();
    assertEquals("", actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   */
  @Test
  public void testCreateCallActivityBehavior8() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior(expression, new ArrayList<>());

    // Assert
    assertNull(actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   */
  @Test
  public void testCreateCallActivityBehavior9() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression expression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type),
        "Expression Text");

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior(expression, new ArrayList<>());

    // Assert
    assertNull(actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   */
  @Test
  public void testCreateCallActivityBehavior10() {
    // Arrange
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   */
  @Test
  public void testCreateCallActivityBehavior11() {
    // Arrange
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createTransactionActivityBehavior(Transaction)}
   */
  @Test
  public void testCreateTransactionActivityBehavior() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createTransactionActivityBehavior(new Transaction())
        .getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createTransactionActivityBehavior(mock(Transaction.class))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchEventActivityBehavior(IntermediateCatchEvent)}
   */
  @Test
  public void testCreateIntermediateCatchEventActivityBehavior() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createIntermediateCatchEventActivityBehavior(new IntermediateCatchEvent())
        .getMultiInstanceActivityBehavior());
    assertNull(
        defaultActivityBehaviorFactory.createIntermediateCatchEventActivityBehavior(mock(IntermediateCatchEvent.class))
            .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}
   */
  @Test
  public void testCreateIntermediateCatchMessageEventActivityBehavior() {
    // Arrange
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}
   */
  @Test
  public void testCreateIntermediateCatchMessageEventActivityBehavior2() {
    // Arrange
    IntermediateCatchEvent intermediateCatchEvent = mock(IntermediateCatchEvent.class);
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}
   */
  @Test
  public void testCreateIntermediateCatchMessageEventActivityBehavior3() {
    // Arrange
    IntermediateCatchEvent intermediateCatchEvent = new IntermediateCatchEvent();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}
   */
  @Test
  public void testCreateIntermediateCatchMessageEventActivityBehavior4() {
    // Arrange
    IntermediateCatchEvent intermediateCatchEvent = new IntermediateCatchEvent();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());
    fieldExtensions.add(new FieldExtension());

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}
   */
  @Test
  public void testCreateIntermediateCatchMessageEventActivityBehavior5() {
    // Arrange
    IntermediateCatchEvent intermediateCatchEvent = new IntermediateCatchEvent();
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn("");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");
    when(fieldExtension.getStringValue()).thenReturn("42");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

    // Act
    IntermediateCatchMessageEventActivityBehavior actualCreateIntermediateCatchMessageEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createIntermediateCatchMessageEventActivityBehavior(intermediateCatchEvent, messageEventDefinition);

    // Assert
    verify(fieldExtension).getExpression();
    verify(fieldExtension).getFieldName();
    verify(fieldExtension).getStringValue();
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchTimerEventActivityBehavior(IntermediateCatchEvent, TimerEventDefinition)}
   */
  @Test
  public void testCreateIntermediateCatchTimerEventActivityBehavior() {
    // Arrange
    IntermediateCatchEvent intermediateCatchEvent = new IntermediateCatchEvent();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateCatchTimerEventActivityBehavior(intermediateCatchEvent, new TimerEventDefinition())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchTimerEventActivityBehavior(IntermediateCatchEvent, TimerEventDefinition)}
   */
  @Test
  public void testCreateIntermediateCatchTimerEventActivityBehavior2() {
    // Arrange
    IntermediateCatchEvent intermediateCatchEvent = mock(IntermediateCatchEvent.class);

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateCatchTimerEventActivityBehavior(intermediateCatchEvent, new TimerEventDefinition())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchSignalEventActivityBehavior(IntermediateCatchEvent, SignalEventDefinition, Signal)}
   */
  @Test
  public void testCreateIntermediateCatchSignalEventActivityBehavior() {
    // Arrange
    IntermediateCatchEvent intermediateCatchEvent = new IntermediateCatchEvent();
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateCatchSignalEventActivityBehavior(intermediateCatchEvent, signalEventDefinition,
            new Signal("42", "Name"))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchSignalEventActivityBehavior(IntermediateCatchEvent, SignalEventDefinition, Signal)}
   */
  @Test
  public void testCreateIntermediateCatchSignalEventActivityBehavior2() {
    // Arrange
    IntermediateCatchEvent intermediateCatchEvent = mock(IntermediateCatchEvent.class);
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateCatchSignalEventActivityBehavior(intermediateCatchEvent, signalEventDefinition,
            new Signal("42", "Name"))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}
   */
  @Test
  public void testCreateIntermediateThrowSignalEventActivityBehavior() {
    // Arrange
    ThrowEvent throwEvent = new ThrowEvent();
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateThrowSignalEventActivityBehavior(throwEvent, signalEventDefinition, new Signal("42", "Name"))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}
   */
  @Test
  public void testCreateIntermediateThrowSignalEventActivityBehavior2() {
    // Arrange
    ThrowEvent throwEvent = mock(ThrowEvent.class);
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateThrowSignalEventActivityBehavior(throwEvent, signalEventDefinition, new Signal("42", "Name"))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}
   */
  @Test
  public void testCreateIntermediateThrowSignalEventActivityBehavior3() {
    // Arrange
    ThrowEvent throwEvent = new ThrowEvent();
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    Signal signal = new Signal("42", "Name");
    signal.setScope(Signal.SCOPE_PROCESS_INSTANCE);

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateThrowSignalEventActivityBehavior(throwEvent, signalEventDefinition, signal)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}
   */
  @Test
  public void testCreateIntermediateThrowSignalEventActivityBehavior4() {
    // Arrange
    ThrowEvent throwEvent = new ThrowEvent();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateThrowSignalEventActivityBehavior(throwEvent, new SignalEventDefinition(), null)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}
   */
  @Test
  public void testCreateIntermediateThrowSignalEventActivityBehavior5() {
    // Arrange
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createErrorEndEventActivityBehavior(EndEvent, ErrorEventDefinition)}
   */
  @Test
  public void testCreateErrorEndEventActivityBehavior() {
    // Arrange
    EndEvent endEvent = new EndEvent();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createErrorEndEventActivityBehavior(endEvent, new ErrorEventDefinition())
        .getErrorRef());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createErrorEndEventActivityBehavior(EndEvent, ErrorEventDefinition)}
   */
  @Test
  public void testCreateErrorEndEventActivityBehavior2() {
    // Arrange
    EndEvent endEvent = mock(EndEvent.class);

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createErrorEndEventActivityBehavior(endEvent, new ErrorEventDefinition())
        .getErrorRef());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  public void testCreateTerminateEndEventActivityBehavior() {
    // Arrange and Act
    TerminateEndEventActivityBehavior actualCreateTerminateEndEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createTerminateEndEventActivityBehavior(new EndEvent());

    // Assert
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateAll());
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateMultiInstance());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  public void testCreateTerminateEndEventActivityBehavior2() {
    // Arrange
    EndEvent endEvent = mock(EndEvent.class);
    when(endEvent.getEventDefinitions()).thenReturn(new ArrayList<>());

    // Act
    TerminateEndEventActivityBehavior actualCreateTerminateEndEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createTerminateEndEventActivityBehavior(endEvent);

    // Assert
    verify(endEvent, atLeast(1)).getEventDefinitions();
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateAll());
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateMultiInstance());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  public void testCreateTerminateEndEventActivityBehavior3() {
    // Arrange
    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new CancelEventDefinition());
    EndEvent endEvent = mock(EndEvent.class);
    when(endEvent.getEventDefinitions()).thenReturn(eventDefinitionList);

    // Act
    TerminateEndEventActivityBehavior actualCreateTerminateEndEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createTerminateEndEventActivityBehavior(endEvent);

    // Assert
    verify(endEvent, atLeast(1)).getEventDefinitions();
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateAll());
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateMultiInstance());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  public void testCreateTerminateEndEventActivityBehavior4() {
    // Arrange
    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new TerminateEventDefinition());
    EndEvent endEvent = mock(EndEvent.class);
    when(endEvent.getEventDefinitions()).thenReturn(eventDefinitionList);

    // Act
    TerminateEndEventActivityBehavior actualCreateTerminateEndEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createTerminateEndEventActivityBehavior(endEvent);

    // Assert
    verify(endEvent, atLeast(1)).getEventDefinitions();
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateAll());
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateMultiInstance());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  public void testCreateTerminateEndEventActivityBehavior5() {
    // Arrange
    TerminateEventDefinition terminateEventDefinition = mock(TerminateEventDefinition.class);
    when(terminateEventDefinition.isTerminateAll()).thenThrow(new ActivitiException("An error occurred"));

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(terminateEventDefinition);
    EndEvent endEvent = mock(EndEvent.class);
    when(endEvent.getEventDefinitions()).thenReturn(eventDefinitionList);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createTerminateEndEventActivityBehavior(endEvent));
    verify(endEvent, atLeast(1)).getEventDefinitions();
    verify(terminateEventDefinition).isTerminateAll();
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundaryEventActivityBehavior(BoundaryEvent, boolean)}
   */
  @Test
  public void testCreateBoundaryEventActivityBehavior() {
    // Arrange, Act and Assert
    assertTrue(
        defaultActivityBehaviorFactory.createBoundaryEventActivityBehavior(new BoundaryEvent(), true).isInterrupting());
    assertTrue(defaultActivityBehaviorFactory.createBoundaryEventActivityBehavior(mock(BoundaryEvent.class), true)
        .isInterrupting());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundaryCancelEventActivityBehavior(CancelEventDefinition)}
   */
  @Test
  public void testCreateBoundaryCancelEventActivityBehavior() {
    // Arrange, Act and Assert
    assertFalse(defaultActivityBehaviorFactory.createBoundaryCancelEventActivityBehavior(new CancelEventDefinition())
        .isInterrupting());
    assertFalse(
        defaultActivityBehaviorFactory.createBoundaryCancelEventActivityBehavior(mock(CancelEventDefinition.class))
            .isInterrupting());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundaryCompensateEventActivityBehavior(BoundaryEvent, CompensateEventDefinition, boolean)}
   */
  @Test
  public void testCreateBoundaryCompensateEventActivityBehavior() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createBoundaryCompensateEventActivityBehavior(boundaryEvent, new CompensateEventDefinition(), true)
        .isInterrupting());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundaryCompensateEventActivityBehavior(BoundaryEvent, CompensateEventDefinition, boolean)}
   */
  @Test
  public void testCreateBoundaryCompensateEventActivityBehavior2() {
    // Arrange
    BoundaryEvent boundaryEvent = mock(BoundaryEvent.class);

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createBoundaryCompensateEventActivityBehavior(boundaryEvent, new CompensateEventDefinition(), true)
        .isInterrupting());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundaryTimerEventActivityBehavior(BoundaryEvent, TimerEventDefinition, boolean)}
   */
  @Test
  public void testCreateBoundaryTimerEventActivityBehavior() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createBoundaryTimerEventActivityBehavior(boundaryEvent, new TimerEventDefinition(), true)
        .isInterrupting());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundaryTimerEventActivityBehavior(BoundaryEvent, TimerEventDefinition, boolean)}
   */
  @Test
  public void testCreateBoundaryTimerEventActivityBehavior2() {
    // Arrange
    BoundaryEvent boundaryEvent = mock(BoundaryEvent.class);

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createBoundaryTimerEventActivityBehavior(boundaryEvent, new TimerEventDefinition(), true)
        .isInterrupting());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundarySignalEventActivityBehavior(BoundaryEvent, SignalEventDefinition, Signal, boolean)}
   */
  @Test
  public void testCreateBoundarySignalEventActivityBehavior() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createBoundarySignalEventActivityBehavior(boundaryEvent, signalEventDefinition, new Signal("42", "Name"), true)
        .isInterrupting());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundarySignalEventActivityBehavior(BoundaryEvent, SignalEventDefinition, Signal, boolean)}
   */
  @Test
  public void testCreateBoundarySignalEventActivityBehavior2() {
    // Arrange
    BoundaryEvent boundaryEvent = mock(BoundaryEvent.class);
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createBoundarySignalEventActivityBehavior(boundaryEvent, signalEventDefinition, new Signal("42", "Name"), true)
        .isInterrupting());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundaryMessageEventActivityBehavior(BoundaryEvent, MessageEventDefinition, boolean)}
   */
  @Test
  public void testCreateBoundaryMessageEventActivityBehavior() {
    // Arrange
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundaryMessageEventActivityBehavior(BoundaryEvent, MessageEventDefinition, boolean)}
   */
  @Test
  public void testCreateBoundaryMessageEventActivityBehavior2() {
    // Arrange
    BoundaryEvent boundaryEvent = mock(BoundaryEvent.class);
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageEventActivityBehavior(ThrowEvent, MessageEventDefinition, Message)}
   */
  @Test
  public void testCreateThrowMessageEventActivityBehavior() {
    // Arrange
    ThrowEvent throwEvent = new ThrowEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();

    // Act
    IntermediateThrowMessageEventActivityBehavior actualCreateThrowMessageEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createThrowMessageEventActivityBehavior(throwEvent, messageEventDefinition,
            new Message("42", "Name", "Item Ref"));

    // Assert
    MessageExecutionContext messageExecutionContext = actualCreateThrowMessageEventActivityBehaviorResult
        .getMessageExecutionContext();
    assertTrue(messageExecutionContext instanceof DefaultMessageExecutionContext);
    assertTrue(((DefaultMessageExecutionContext) messageExecutionContext)
        .getMessagePayloadMappingProvider() instanceof BpmnMessagePayloadMappingProvider);
    assertTrue(
        actualCreateThrowMessageEventActivityBehaviorResult.getDelegate() instanceof DefaultThrowMessageJavaDelegate);
    assertNull(((DefaultMessageExecutionContext) messageExecutionContext).getExpressionManager());
    assertSame(messageEventDefinition, actualCreateThrowMessageEventActivityBehaviorResult.getMessageEventDefinition());
    assertSame(throwEvent, actualCreateThrowMessageEventActivityBehaviorResult.getThrowEvent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageEventActivityBehavior(ThrowEvent, MessageEventDefinition, Message)}
   */
  @Test
  public void testCreateThrowMessageEventActivityBehavior2() {
    // Arrange
    ThrowEvent throwEvent = mock(ThrowEvent.class);
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();

    // Act
    IntermediateThrowMessageEventActivityBehavior actualCreateThrowMessageEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createThrowMessageEventActivityBehavior(throwEvent, messageEventDefinition,
            new Message("42", "Name", "Item Ref"));

    // Assert
    MessageExecutionContext messageExecutionContext = actualCreateThrowMessageEventActivityBehaviorResult
        .getMessageExecutionContext();
    assertTrue(messageExecutionContext instanceof DefaultMessageExecutionContext);
    assertTrue(((DefaultMessageExecutionContext) messageExecutionContext)
        .getMessagePayloadMappingProvider() instanceof BpmnMessagePayloadMappingProvider);
    assertTrue(
        actualCreateThrowMessageEventActivityBehaviorResult.getDelegate() instanceof DefaultThrowMessageJavaDelegate);
    assertNull(((DefaultMessageExecutionContext) messageExecutionContext).getExpressionManager());
    assertSame(messageEventDefinition, actualCreateThrowMessageEventActivityBehaviorResult.getMessageEventDefinition());
    assertSame(throwEvent, actualCreateThrowMessageEventActivityBehaviorResult.getThrowEvent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, Message)}
   */
  @Test
  public void testCreateThrowMessageEndEventActivityBehavior() {
    // Arrange
    EndEvent endEvent = new EndEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();

    // Act
    ThrowMessageEndEventActivityBehavior actualCreateThrowMessageEndEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createThrowMessageEndEventActivityBehavior(endEvent, messageEventDefinition,
            new Message("42", "Name", "Item Ref"));

    // Assert
    MessageExecutionContext messageExecutionContext = actualCreateThrowMessageEndEventActivityBehaviorResult
        .getMessageExecutionContext();
    assertTrue(messageExecutionContext instanceof DefaultMessageExecutionContext);
    assertTrue(((DefaultMessageExecutionContext) messageExecutionContext)
        .getMessagePayloadMappingProvider() instanceof BpmnMessagePayloadMappingProvider);
    assertTrue(actualCreateThrowMessageEndEventActivityBehaviorResult
        .getDelegate() instanceof DefaultThrowMessageJavaDelegate);
    assertNull(((DefaultMessageExecutionContext) messageExecutionContext).getExpressionManager());
    assertSame(endEvent, actualCreateThrowMessageEndEventActivityBehaviorResult.getEndEvent());
    assertSame(messageEventDefinition,
        actualCreateThrowMessageEndEventActivityBehaviorResult.getMessageEventDefinition());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, Message)}
   */
  @Test
  public void testCreateThrowMessageEndEventActivityBehavior2() {
    // Arrange
    EndEvent endEvent = mock(EndEvent.class);
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();

    // Act
    ThrowMessageEndEventActivityBehavior actualCreateThrowMessageEndEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createThrowMessageEndEventActivityBehavior(endEvent, messageEventDefinition,
            new Message("42", "Name", "Item Ref"));

    // Assert
    MessageExecutionContext messageExecutionContext = actualCreateThrowMessageEndEventActivityBehaviorResult
        .getMessageExecutionContext();
    assertTrue(messageExecutionContext instanceof DefaultMessageExecutionContext);
    assertTrue(((DefaultMessageExecutionContext) messageExecutionContext)
        .getMessagePayloadMappingProvider() instanceof BpmnMessagePayloadMappingProvider);
    assertTrue(actualCreateThrowMessageEndEventActivityBehaviorResult
        .getDelegate() instanceof DefaultThrowMessageJavaDelegate);
    assertNull(((DefaultMessageExecutionContext) messageExecutionContext).getExpressionManager());
    assertSame(messageEventDefinition,
        actualCreateThrowMessageEndEventActivityBehaviorResult.getMessageEventDefinition());
    assertSame(endEvent, actualCreateThrowMessageEndEventActivityBehaviorResult.getEndEvent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageDelegate(MessageEventDefinition)}
   */
  @Test
  public void testCreateThrowMessageDelegate() {
    // Arrange, Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createThrowMessageDelegate(new MessageEventDefinition()) instanceof DefaultThrowMessageJavaDelegate);
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageDelegate(MessageEventDefinition)}
   */
  @Test
  public void testCreateThrowMessageDelegate2() {
    // Arrange
    MessageEventDefinition messageEventDefinition = mock(MessageEventDefinition.class);
    when(messageEventDefinition.getAttributes()).thenReturn(new HashMap<>());

    // Act
    ThrowMessageDelegate actualCreateThrowMessageDelegateResult = defaultActivityBehaviorFactory
        .createThrowMessageDelegate(messageEventDefinition);

    // Assert
    verify(messageEventDefinition).getAttributes();
    assertTrue(actualCreateThrowMessageDelegateResult instanceof DefaultThrowMessageJavaDelegate);
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMessageExecutionContext(Event, MessageEventDefinition)}
   */
  @Test
  public void testCreateMessageExecutionContext() {
    // Arrange
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMessageExecutionContext(Event, MessageEventDefinition)}
   */
  @Test
  public void testCreateMessageExecutionContext2() {
    // Arrange
    BoundaryEvent bpmnEvent = mock(BoundaryEvent.class);

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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageJavaDelegate(String)}
   */
  @Test
  public void testCreateThrowMessageJavaDelegate() {
    // Arrange, Act and Assert
    assertTrue(defaultActivityBehaviorFactory.createThrowMessageJavaDelegate(
        "org.activiti.engine.impl.delegate.ThrowMessageDelegate") instanceof ThrowMessageJavaDelegate);
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}
   */
  @Test
  public void testCreateThrowMessageDelegateExpression() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(new ExpressionManager());

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createThrowMessageDelegateExpression("Delegate Expression") instanceof ThrowMessageDelegateExpression);
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}
   */
  @Test
  public void testCreateThrowMessageDelegateExpression3() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    // Act
    ThrowMessageDelegate actualCreateThrowMessageDelegateExpressionResult = defaultActivityBehaviorFactory
        .createThrowMessageDelegateExpression("Delegate Expression");

    // Assert
    verify(expressionManager).createExpression(eq("Delegate Expression"));
    assertTrue(actualCreateThrowMessageDelegateExpressionResult instanceof ThrowMessageDelegateExpression);
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createDefaultThrowMessageDelegate()}
   */
  @Test
  public void testCreateDefaultThrowMessageDelegate() {
    // Arrange, Act and Assert
    assertTrue(
        defaultActivityBehaviorFactory.createDefaultThrowMessageDelegate() instanceof DefaultThrowMessageJavaDelegate);
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMessagePayloadMappingProvider(Event, MessageEventDefinition)}
   */
  @Test
  public void testCreateMessagePayloadMappingProvider() {
    // Arrange
    BoundaryEvent bpmnEvent = new BoundaryEvent();

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory.createMessagePayloadMappingProvider(bpmnEvent,
        new MessageEventDefinition()) instanceof BpmnMessagePayloadMappingProvider);
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMessagePayloadMappingProvider(Event, MessageEventDefinition)}
   */
  @Test
  public void testCreateMessagePayloadMappingProvider2() {
    // Arrange
    BoundaryEvent bpmnEvent = mock(BoundaryEvent.class);

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory.createMessagePayloadMappingProvider(bpmnEvent,
        new MessageEventDefinition()) instanceof BpmnMessagePayloadMappingProvider);
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkClassDelegate(Map)}
   */
  @Test
  public void testCheckClassDelegate() {
    // Arrange, Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkClassDelegate(new HashMap<>()).isPresent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkClassDelegate(Map)}
   */
  @Test
  public void testCheckClassDelegate2() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("class", mock(BiFunction.class));

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkClassDelegate(attributes).isPresent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkClassDelegate(Map)}
   */
  @Test
  public void testCheckClassDelegate3() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("activiti", new ArrayList<>());
    attributes.computeIfPresent("class", mock(BiFunction.class));

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkClassDelegate(attributes).isPresent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkClassDelegate(Map)}
   */
  @Test
  public void testCheckClassDelegate4() {
    // Arrange
    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("class"));

    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("activiti", extensionAttributeList);
    attributes.computeIfPresent("class", mock(BiFunction.class));

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkClassDelegate(attributes).isPresent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkClassDelegate(Map)}
   */
  @Test
  public void testCheckClassDelegate5() {
    // Arrange
    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));
    extensionAttributeList.add(new ExtensionAttribute("class"));

    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("activiti", extensionAttributeList);
    attributes.computeIfPresent("class", mock(BiFunction.class));

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkClassDelegate(attributes).isPresent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkDelegateExpression(Map)}
   */
  @Test
  public void testCheckDelegateExpression() {
    // Arrange, Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkDelegateExpression(new HashMap<>()).isPresent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkDelegateExpression(Map)}
   */
  @Test
  public void testCheckDelegateExpression2() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("delegateExpression", mock(BiFunction.class));

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkDelegateExpression(attributes).isPresent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkDelegateExpression(Map)}
   */
  @Test
  public void testCheckDelegateExpression3() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("activiti", new ArrayList<>());
    attributes.computeIfPresent("delegateExpression", mock(BiFunction.class));

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkDelegateExpression(attributes).isPresent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkDelegateExpression(Map)}
   */
  @Test
  public void testCheckDelegateExpression4() {
    // Arrange
    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("delegateExpression"));

    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("activiti", extensionAttributeList);
    attributes.computeIfPresent("delegateExpression", mock(BiFunction.class));

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkDelegateExpression(attributes).isPresent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkDelegateExpression(Map)}
   */
  @Test
  public void testCheckDelegateExpression5() {
    // Arrange
    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));
    extensionAttributeList.add(new ExtensionAttribute("delegateExpression"));

    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("activiti", extensionAttributeList);
    attributes.computeIfPresent("delegateExpression", mock(BiFunction.class));

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkDelegateExpression(attributes).isPresent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}
   */
  @Test
  public void testGetAttributeValue() {
    // Arrange, Act and Assert
    assertFalse(defaultActivityBehaviorFactory.getAttributeValue(new HashMap<>(), "Name").isPresent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}
   */
  @Test
  public void testGetAttributeValue2() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("activiti", new ArrayList<>());

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.getAttributeValue(attributes, "Name").isPresent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}
   */
  @Test
  public void testGetAttributeValue3() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("activiti", mock(BiFunction.class));
    attributes.put("activiti", new ArrayList<>());

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.getAttributeValue(attributes, "Name").isPresent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}
   */
  @Test
  public void testGetAttributeValue4() {
    // Arrange
    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("activiti"));

    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("activiti", extensionAttributeList);

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.getAttributeValue(attributes, "Name").isPresent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}
   */
  @Test
  public void testGetAttributeValue5() {
    // Arrange
    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("activiti"));
    extensionAttributeList.add(new ExtensionAttribute("activiti"));

    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("activiti", extensionAttributeList);

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.getAttributeValue(attributes, "Name").isPresent());
  }

  /**
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory()}
   */
  @Test
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory(ClassDelegateFactory)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory(ClassDelegateFactory)}
   */
  @Test
  public void testNewDefaultActivityBehaviorFactory3() {
    // Arrange and Act
    DefaultActivityBehaviorFactory actualDefaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory(
        mock(DefaultClassDelegateFactory.class));

    // Assert
    assertTrue(actualDefaultActivityBehaviorFactory
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
    assertTrue(actualDefaultActivityBehaviorFactory
        .getMessagePayloadMappingProviderFactory() instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(actualDefaultActivityBehaviorFactory.getExpressionManager());
  }
}
