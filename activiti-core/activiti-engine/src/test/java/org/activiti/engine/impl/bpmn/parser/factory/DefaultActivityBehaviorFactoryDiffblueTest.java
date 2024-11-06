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
import org.activiti.bpmn.model.BaseElement;
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
   * Test {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory()}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory(ClassDelegateFactory)}.
   * <ul>
   *   <li>When {@link DefaultClassDelegateFactory} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory(ClassDelegateFactory)}
   */
  @Test
  public void testNewDefaultActivityBehaviorFactory_whenDefaultClassDelegateFactory() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory(ClassDelegateFactory)}.
   * <ul>
   *   <li>When {@link DefaultClassDelegateFactory}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory(ClassDelegateFactory)}
   */
  @Test
  public void testNewDefaultActivityBehaviorFactory_whenDefaultClassDelegateFactory2() {
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

  /**
   * Test {@link DefaultActivityBehaviorFactory#createTaskActivityBehavior(Task)}.
   * <ul>
   *   <li>When {@link BusinessRuleTask}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createTaskActivityBehavior(Task)}
   */
  @Test
  public void testCreateTaskActivityBehavior_whenBusinessRuleTask() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createTaskActivityBehavior(mock(BusinessRuleTask.class))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#createTaskActivityBehavior(Task)}.
   * <ul>
   *   <li>When {@link Task} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createTaskActivityBehavior(Task)}
   */
  @Test
  public void testCreateTaskActivityBehavior_whenTask() {
    // Arrange, Act and Assert
    assertNull(
        defaultActivityBehaviorFactory.createTaskActivityBehavior(new Task()).getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createManualTaskActivityBehavior(ManualTask)}.
   * <ul>
   *   <li>When {@link ManualTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createManualTaskActivityBehavior(ManualTask)}
   */
  @Test
  public void testCreateManualTaskActivityBehavior_whenManualTask() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createManualTaskActivityBehavior(new ManualTask())
        .getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createManualTaskActivityBehavior(mock(ManualTask.class))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createReceiveTaskActivityBehavior(ReceiveTask)}.
   * <ul>
   *   <li>When {@link ReceiveTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createReceiveTaskActivityBehavior(ReceiveTask)}
   */
  @Test
  public void testCreateReceiveTaskActivityBehavior_whenReceiveTask() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createReceiveTaskActivityBehavior(new ReceiveTask())
        .getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createReceiveTaskActivityBehavior(mock(ReceiveTask.class))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createUserTaskActivityBehavior(UserTask)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createUserTaskActivityBehavior(UserTask)}
   */
  @Test
  public void testCreateUserTaskActivityBehavior_whenNull() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createUserTaskActivityBehavior(null).getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createUserTaskActivityBehavior(UserTask)}.
   * <ul>
   *   <li>When {@link UserTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createUserTaskActivityBehavior(UserTask)}
   */
  @Test
  public void testCreateUserTaskActivityBehavior_whenUserTask() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createUserTaskActivityBehavior(new UserTask())
        .getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createUserTaskActivityBehavior(mock(UserTask.class))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>Then calls {@link ServiceTask#getSkipExpression()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}
   */
  @Test
  public void testGetSkipExpressionFromServiceTask_givenEmptyString_thenCallsGetSkipExpression() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#getSkipExpressionFromServiceTask(ServiceTask)}
   */
  @Test
  public void testGetSkipExpressionFromServiceTask_whenServiceTask_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.getSkipExpressionFromServiceTask(new ServiceTask()));
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  public void testCreateClassDelegateServiceTask() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  public void testCreateClassDelegateServiceTask2() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  public void testCreateClassDelegateServiceTask3() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  public void testCreateClassDelegateServiceTask4() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  public void testCreateClassDelegateServiceTask5() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  public void testCreateClassDelegateServiceTask_givenEmptyString() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor).</li>
   *   <li>Then return ClassName is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  public void testCreateClassDelegateServiceTask_whenServiceTask_thenReturnClassNameIsNull() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateServiceTaskDelegateExpressionActivityBehavior_givenEmptyString() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}.
   * <ul>
   *   <li>Then calls {@link FieldExtension#getExpression()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createServiceTaskDelegateExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateServiceTaskDelegateExpressionActivityBehavior_thenCallsGetExpression() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  public void testCreateDefaultServiceTaskBehavior_givenArrayListAddFieldExtension() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link BaseElement#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  public void testCreateDefaultServiceTaskBehavior_givenArrayList_thenCallsGetId() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  public void testCreateDefaultServiceTaskBehavior_givenEmptyString() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}.
   * <ul>
   *   <li>Then calls {@link FieldExtension#getExpression()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  public void testCreateDefaultServiceTaskBehavior_thenCallsGetExpression() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor).</li>
   *   <li>Then calls {@link ExpressionManager#createExpression(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createDefaultServiceTaskBehavior(ServiceTask)}
   */
  @Test
  public void testCreateDefaultServiceTaskBehavior_whenServiceTask_thenCallsCreateExpression() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createServiceTaskExpressionActivityBehavior(ServiceTask)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createServiceTaskExpressionActivityBehavior(ServiceTask)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createServiceTaskExpressionActivityBehavior(ServiceTask)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createServiceTaskExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateServiceTaskExpressionActivityBehavior_givenEmptyString() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createServiceTaskExpressionActivityBehavior(ServiceTask)}.
   * <ul>
   *   <li>Then calls {@link ExpressionManager#createExpression(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createServiceTaskExpressionActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateServiceTaskExpressionActivityBehavior_thenCallsCreateExpression() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createWebServiceActivityBehavior(SendTask)}
   * with {@code sendTask}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createWebServiceActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateWebServiceActivityBehaviorWithSendTask_whenNull() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createWebServiceActivityBehavior((SendTask) null)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createWebServiceActivityBehavior(SendTask)}
   * with {@code sendTask}.
   * <ul>
   *   <li>When {@link SendTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createWebServiceActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateWebServiceActivityBehaviorWithSendTask_whenSendTask() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createWebServiceActivityBehavior(new SendTask())
        .getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createWebServiceActivityBehavior(mock(SendTask.class))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createWebServiceActivityBehavior(ServiceTask)}
   * with {@code serviceTask}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createWebServiceActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateWebServiceActivityBehaviorWithServiceTask_whenServiceTask() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createWebServiceActivityBehavior(new ServiceTask())
        .getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createWebServiceActivityBehavior(mock(ServiceTask.class))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(SendTask)}
   * with {@code sendTask}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link BaseElement#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateMailActivityBehaviorWithSendTask_givenArrayList_thenCallsGetId() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(SendTask)}
   * with {@code sendTask}.
   * <ul>
   *   <li>When {@link SendTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateMailActivityBehaviorWithSendTask_whenSendTask() {
    // Arrange, Act and Assert
    assertNull(
        defaultActivityBehaviorFactory.createMailActivityBehavior(new SendTask()).getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(ServiceTask)}
   * with {@code serviceTask}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link BaseElement#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateMailActivityBehaviorWithServiceTask_givenArrayList_thenCallsGetId() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(ServiceTask)}
   * with {@code serviceTask}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateMailActivityBehaviorWithServiceTask_whenServiceTask() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createMailActivityBehavior(new ServiceTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(String, List)}
   * with {@code taskId}, {@code fields}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMailActivityBehavior(String, List)}
   */
  @Test
  public void testCreateMailActivityBehaviorWithTaskIdFields() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createMailActivityBehavior("42", new ArrayList<>())
        .getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory
        .createMailActivityBehavior("Trying to load class with current thread context classloader: {}",
            new ArrayList<>())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(SendTask)}
   * with {@code sendTask}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateMuleActivityBehaviorWithSendTask_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createMuleActivityBehavior(new SendTask()));
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(ServiceTask)}
   * with {@code serviceTask}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateMuleActivityBehaviorWithServiceTask_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createMuleActivityBehavior(new ServiceTask()));
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(TaskWithFieldExtensions, List)}
   * with {@code task}, {@code fieldExtensions}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateMuleActivityBehaviorWithTaskFieldExtensions_givenFieldExtension() {
    // Arrange
    SendTask task = mock(SendTask.class);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createMuleActivityBehavior(task, fieldExtensions));
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(TaskWithFieldExtensions, List)}
   * with {@code task}, {@code fieldExtensions}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateMuleActivityBehaviorWithTaskFieldExtensions_givenFieldExtension2() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(TaskWithFieldExtensions, List)}
   * with {@code task}, {@code fieldExtensions}.
   * <ul>
   *   <li>When {@link SendTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMuleActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateMuleActivityBehaviorWithTaskFieldExtensions_whenSendTask() {
    // Arrange
    SendTask task = new SendTask();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createMuleActivityBehavior(task, new ArrayList<>()));
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   * with {@code sendTask}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateCamelActivityBehaviorWithSendTask() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   * with {@code sendTask}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateCamelActivityBehaviorWithSendTask2() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   * with {@code sendTask}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) StringValue is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateCamelActivityBehaviorWithSendTask_givenFieldExtensionStringValueIs42() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   * with {@code sendTask}.
   * <ul>
   *   <li>Then calls {@link TaskWithFieldExtensions#getFieldExtensions()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateCamelActivityBehaviorWithSendTask_thenCallsGetFieldExtensions() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   * with {@code sendTask}.
   * <ul>
   *   <li>When {@link SendTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateCamelActivityBehaviorWithSendTask_whenSendTask() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(new SendTask()));
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)}
   * with {@code serviceTask}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateCamelActivityBehaviorWithServiceTask() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)}
   * with {@code serviceTask}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateCamelActivityBehaviorWithServiceTask2() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)}
   * with {@code serviceTask}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateCamelActivityBehaviorWithServiceTask3() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)}
   * with {@code serviceTask}.
   * <ul>
   *   <li>Then calls {@link TaskWithFieldExtensions#getFieldExtensions()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateCamelActivityBehaviorWithServiceTask_thenCallsGetFieldExtensions() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)}
   * with {@code serviceTask}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateCamelActivityBehaviorWithServiceTask_whenServiceTask() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(new ServiceTask()));
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   * with {@code task}, {@code fieldExtensions}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateCamelActivityBehaviorWithTaskFieldExtensions() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   * with {@code task}, {@code fieldExtensions}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateCamelActivityBehaviorWithTaskFieldExtensions2() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   * with {@code task}, {@code fieldExtensions}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateCamelActivityBehaviorWithTaskFieldExtensions3() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   * with {@code task}, {@code fieldExtensions}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateCamelActivityBehaviorWithTaskFieldExtensions_givenFieldExtension() {
    // Arrange
    SendTask task = mock(SendTask.class);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(task, fieldExtensions));
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   * with {@code task}, {@code fieldExtensions}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateCamelActivityBehaviorWithTaskFieldExtensions_givenFieldExtension2() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   * with {@code task}, {@code fieldExtensions}.
   * <ul>
   *   <li>When {@link SendTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCamelActivityBehavior(TaskWithFieldExtensions, List)}
   */
  @Test
  public void testCreateCamelActivityBehaviorWithTaskFieldExtensions_whenSendTask() {
    // Arrange
    SendTask task = new SendTask();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> defaultActivityBehaviorFactory.createCamelActivityBehavior(task, new ArrayList<>()));
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createShellActivityBehavior(ServiceTask)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link TaskWithFieldExtensions#getFieldExtensions()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createShellActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateShellActivityBehavior_givenArrayList_thenCallsGetFieldExtensions() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createShellActivityBehavior(ServiceTask)}.
   * <ul>
   *   <li>Then calls {@link FieldExtension#getExpression()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createShellActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateShellActivityBehavior_thenCallsGetExpression() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createShellActivityBehavior(ServiceTask)}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createShellActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateShellActivityBehavior_whenServiceTask() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createShellActivityBehavior(new ServiceTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createBusinessRuleTaskActivityBehavior(BusinessRuleTask)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBusinessRuleTaskActivityBehavior(BusinessRuleTask)}
   */
  @Test
  public void testCreateBusinessRuleTaskActivityBehavior_thenThrowActivitiException() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createScriptTaskActivityBehavior(ScriptTask)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>Then calls {@link BaseElement#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createScriptTaskActivityBehavior(ScriptTask)}
   */
  @Test
  public void testCreateScriptTaskActivityBehavior_givenTrue_thenCallsGetId() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createScriptTaskActivityBehavior(ScriptTask)}.
   * <ul>
   *   <li>When {@link ScriptTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createScriptTaskActivityBehavior(ScriptTask)}
   */
  @Test
  public void testCreateScriptTaskActivityBehavior_whenScriptTask() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createScriptTaskActivityBehavior(new ScriptTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createSequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createSequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createSequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testCreateSequentialMultiInstanceBehavior2() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createSequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createSequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testCreateSequentialMultiInstanceBehavior_whenAdhocSubProcess() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testCreateParallelMultiInstanceBehavior2() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testCreateParallelMultiInstanceBehavior_whenAdhocSubProcess() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createSubprocessActivityBehavior(SubProcess)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createSubprocessActivityBehavior(SubProcess)}
   */
  @Test
  public void testCreateSubprocessActivityBehavior_whenAdhocSubProcess() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createSubprocessActivityBehavior(mock(AdhocSubProcess.class))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createSubprocessActivityBehavior(SubProcess)}.
   * <ul>
   *   <li>When {@link SubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createSubprocessActivityBehavior(SubProcess)}
   */
  @Test
  public void testCreateSubprocessActivityBehavior_whenSubProcess() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createSubprocessActivityBehavior(new SubProcess())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createEventSubProcessErrorStartEventActivityBehavior(StartEvent)}.
   * <ul>
   *   <li>When {@link StartEvent} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createEventSubProcessErrorStartEventActivityBehavior(StartEvent)}
   */
  @Test
  public void testCreateEventSubProcessErrorStartEventActivityBehavior_whenStartEvent() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createEventSubProcessErrorStartEventActivityBehavior(new StartEvent())
        .getMultiInstanceActivityBehavior());
    assertNull(
        defaultActivityBehaviorFactory.createEventSubProcessErrorStartEventActivityBehavior(mock(StartEvent.class))
            .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)}
   */
  @Test
  public void testCreateEventSubProcessMessageStartEventActivityBehavior2() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)}
   */
  @Test
  public void testCreateEventSubProcessMessageStartEventActivityBehavior3() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)}.
   * <ul>
   *   <li>When {@link StartEvent}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)}
   */
  @Test
  public void testCreateEventSubProcessMessageStartEventActivityBehavior_whenStartEvent() {
    // Arrange
    StartEvent startEvent = mock(StartEvent.class);

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createEventSubProcessMessageStartEventActivityBehavior(startEvent, new MessageEventDefinition())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createAdhocSubprocessActivityBehavior(SubProcess)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createAdhocSubprocessActivityBehavior(SubProcess)}
   */
  @Test
  public void testCreateAdhocSubprocessActivityBehavior_whenAdhocSubProcess() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createAdhocSubprocessActivityBehavior(mock(AdhocSubProcess.class))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createAdhocSubprocessActivityBehavior(SubProcess)}.
   * <ul>
   *   <li>When {@link SubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createAdhocSubprocessActivityBehavior(SubProcess)}
   */
  @Test
  public void testCreateAdhocSubprocessActivityBehavior_whenSubProcess() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createAdhocSubprocessActivityBehavior(new SubProcess())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}
   * with {@code callActivity}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}
   */
  @Test
  public void testCreateCallActivityBehaviorWithCallActivity() {
    // Arrange and Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior(new CallActivity());

    // Assert
    assertNull(actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}
   * with {@code callActivity}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}
   */
  @Test
  public void testCreateCallActivityBehaviorWithCallActivity2() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}
   * with {@code callActivity}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}
   */
  @Test
  public void testCreateCallActivityBehaviorWithCallActivity3() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   * with {@code calledElement}, {@code mapExceptions}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   */
  @Test
  public void testCreateCallActivityBehaviorWithCalledElementMapExceptions() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   * with {@code calledElement}, {@code mapExceptions}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   */
  @Test
  public void testCreateCallActivityBehaviorWithCalledElementMapExceptions2() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   * with {@code calledElement}, {@code mapExceptions}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   */
  @Test
  public void testCreateCallActivityBehaviorWithCalledElementMapExceptions3() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   * with {@code calledElement}, {@code mapExceptions}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   */
  @Test
  public void testCreateCallActivityBehaviorWithCalledElementMapExceptions_whenArrayList() {
    // Arrange and Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = defaultActivityBehaviorFactory
        .createCallActivityBehavior("Called Element", new ArrayList<>());

    // Assert
    assertEquals("Called Element", actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   * with {@code expression}, {@code mapExceptions}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   */
  @Test
  public void testCreateCallActivityBehaviorWithExpressionMapExceptions() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   * with {@code expression}, {@code mapExceptions}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   */
  @Test
  public void testCreateCallActivityBehaviorWithExpressionMapExceptions2() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   * with {@code expression}, {@code mapExceptions}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   */
  @Test
  public void testCreateCallActivityBehaviorWithExpressionMapExceptions_whenArrayList() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   * with {@code expression}, {@code mapExceptions}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   */
  @Test
  public void testCreateCallActivityBehaviorWithExpressionMapExceptions_whenJavaLangObject() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createTransactionActivityBehavior(Transaction)}.
   * <ul>
   *   <li>When {@link Transaction} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createTransactionActivityBehavior(Transaction)}
   */
  @Test
  public void testCreateTransactionActivityBehavior_whenTransaction() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createTransactionActivityBehavior(new Transaction())
        .getMultiInstanceActivityBehavior());
    assertNull(defaultActivityBehaviorFactory.createTransactionActivityBehavior(mock(Transaction.class))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchEventActivityBehavior(IntermediateCatchEvent)}.
   * <ul>
   *   <li>When {@link IntermediateCatchEvent} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchEventActivityBehavior(IntermediateCatchEvent)}
   */
  @Test
  public void testCreateIntermediateCatchEventActivityBehavior_whenIntermediateCatchEvent() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.createIntermediateCatchEventActivityBehavior(new IntermediateCatchEvent())
        .getMultiInstanceActivityBehavior());
    assertNull(
        defaultActivityBehaviorFactory.createIntermediateCatchEventActivityBehavior(mock(IntermediateCatchEvent.class))
            .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}
   */
  @Test
  public void testCreateIntermediateCatchMessageEventActivityBehavior3() {
    // Arrange
    IntermediateCatchEvent intermediateCatchEvent = new IntermediateCatchEvent();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtensions.add(fieldExtension);

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

    // Act and Assert
    List<FieldExtension> fieldExtensions2 = defaultActivityBehaviorFactory
        .createIntermediateCatchMessageEventActivityBehavior(intermediateCatchEvent, messageEventDefinition)
        .getMessageEventDefinition()
        .getFieldExtensions();
    assertEquals(1, fieldExtensions2.size());
    assertSame(fieldExtension, fieldExtensions2.get(0));
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}
   */
  @Test
  public void testCreateIntermediateCatchMessageEventActivityBehavior4() {
    // Arrange
    IntermediateCatchEvent intermediateCatchEvent = new IntermediateCatchEvent();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());
    FieldExtension fieldExtension = new FieldExtension();
    fieldExtensions.add(fieldExtension);

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

    // Act and Assert
    List<FieldExtension> fieldExtensions2 = defaultActivityBehaviorFactory
        .createIntermediateCatchMessageEventActivityBehavior(intermediateCatchEvent, messageEventDefinition)
        .getMessageEventDefinition()
        .getFieldExtensions();
    assertEquals(2, fieldExtensions2.size());
    assertSame(fieldExtension, fieldExtensions2.get(1));
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}.
   * <ul>
   *   <li>Then calls {@link FieldExtension#getExpression()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}
   */
  @Test
  public void testCreateIntermediateCatchMessageEventActivityBehavior_thenCallsGetExpression() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchTimerEventActivityBehavior(IntermediateCatchEvent, TimerEventDefinition)}.
   * <ul>
   *   <li>When {@link IntermediateCatchEvent} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchTimerEventActivityBehavior(IntermediateCatchEvent, TimerEventDefinition)}
   */
  @Test
  public void testCreateIntermediateCatchTimerEventActivityBehavior_whenIntermediateCatchEvent() {
    // Arrange
    IntermediateCatchEvent intermediateCatchEvent = new IntermediateCatchEvent();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateCatchTimerEventActivityBehavior(intermediateCatchEvent, new TimerEventDefinition())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchTimerEventActivityBehavior(IntermediateCatchEvent, TimerEventDefinition)}.
   * <ul>
   *   <li>When {@link IntermediateCatchEvent}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchTimerEventActivityBehavior(IntermediateCatchEvent, TimerEventDefinition)}
   */
  @Test
  public void testCreateIntermediateCatchTimerEventActivityBehavior_whenIntermediateCatchEvent2() {
    // Arrange
    IntermediateCatchEvent intermediateCatchEvent = mock(IntermediateCatchEvent.class);

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateCatchTimerEventActivityBehavior(intermediateCatchEvent, new TimerEventDefinition())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchSignalEventActivityBehavior(IntermediateCatchEvent, SignalEventDefinition, Signal)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createIntermediateCatchSignalEventActivityBehavior(IntermediateCatchEvent, SignalEventDefinition, Signal)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}
   */
  @Test
  public void testCreateIntermediateThrowSignalEventActivityBehavior2() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}
   */
  @Test
  public void testCreateIntermediateThrowSignalEventActivityBehavior3() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}
   */
  @Test
  public void testCreateIntermediateThrowSignalEventActivityBehavior_whenNull() {
    // Arrange
    ThrowEvent throwEvent = new ThrowEvent();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateThrowSignalEventActivityBehavior(throwEvent, new SignalEventDefinition(), null)
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}.
   * <ul>
   *   <li>When {@link ThrowEvent}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}
   */
  @Test
  public void testCreateIntermediateThrowSignalEventActivityBehavior_whenThrowEvent() {
    // Arrange
    ThrowEvent throwEvent = mock(ThrowEvent.class);
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory
        .createIntermediateThrowSignalEventActivityBehavior(throwEvent, signalEventDefinition, new Signal("42", "Name"))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createErrorEndEventActivityBehavior(EndEvent, ErrorEventDefinition)}.
   * <ul>
   *   <li>When {@link EndEvent} (default constructor).</li>
   *   <li>Then return ErrorRef is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createErrorEndEventActivityBehavior(EndEvent, ErrorEventDefinition)}
   */
  @Test
  public void testCreateErrorEndEventActivityBehavior_whenEndEvent_thenReturnErrorRefIsNull() {
    // Arrange
    EndEvent endEvent = new EndEvent();

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createErrorEndEventActivityBehavior(endEvent, new ErrorEventDefinition())
        .getErrorRef());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createErrorEndEventActivityBehavior(EndEvent, ErrorEventDefinition)}.
   * <ul>
   *   <li>When {@link EndEvent}.</li>
   *   <li>Then return ErrorRef is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createErrorEndEventActivityBehavior(EndEvent, ErrorEventDefinition)}
   */
  @Test
  public void testCreateErrorEndEventActivityBehavior_whenEndEvent_thenReturnErrorRefIsNull2() {
    // Arrange
    EndEvent endEvent = mock(EndEvent.class);

    // Act and Assert
    assertNull(defaultActivityBehaviorFactory.createErrorEndEventActivityBehavior(endEvent, new ErrorEventDefinition())
        .getErrorRef());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  public void testCreateTerminateEndEventActivityBehavior() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  public void testCreateTerminateEndEventActivityBehavior2() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  public void testCreateTerminateEndEventActivityBehavior_givenArrayList() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  public void testCreateTerminateEndEventActivityBehavior_thenThrowActivitiException() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}.
   * <ul>
   *   <li>When {@link EndEvent} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  public void testCreateTerminateEndEventActivityBehavior_whenEndEvent() {
    // Arrange and Act
    TerminateEndEventActivityBehavior actualCreateTerminateEndEventActivityBehaviorResult = defaultActivityBehaviorFactory
        .createTerminateEndEventActivityBehavior(new EndEvent());

    // Assert
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateAll());
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateMultiInstance());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createBoundaryEventActivityBehavior(BoundaryEvent, boolean)}.
   * <ul>
   *   <li>When {@link BoundaryEvent} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundaryEventActivityBehavior(BoundaryEvent, boolean)}
   */
  @Test
  public void testCreateBoundaryEventActivityBehavior_whenBoundaryEvent() {
    // Arrange, Act and Assert
    assertTrue(
        defaultActivityBehaviorFactory.createBoundaryEventActivityBehavior(new BoundaryEvent(), true).isInterrupting());
    assertTrue(defaultActivityBehaviorFactory.createBoundaryEventActivityBehavior(mock(BoundaryEvent.class), true)
        .isInterrupting());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createBoundaryCancelEventActivityBehavior(CancelEventDefinition)}.
   * <ul>
   *   <li>When {@link CancelEventDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundaryCancelEventActivityBehavior(CancelEventDefinition)}
   */
  @Test
  public void testCreateBoundaryCancelEventActivityBehavior_whenCancelEventDefinition() {
    // Arrange, Act and Assert
    assertFalse(defaultActivityBehaviorFactory.createBoundaryCancelEventActivityBehavior(new CancelEventDefinition())
        .isInterrupting());
    assertFalse(
        defaultActivityBehaviorFactory.createBoundaryCancelEventActivityBehavior(mock(CancelEventDefinition.class))
            .isInterrupting());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createBoundaryCompensateEventActivityBehavior(BoundaryEvent, CompensateEventDefinition, boolean)}.
   * <ul>
   *   <li>When {@link BoundaryEvent} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundaryCompensateEventActivityBehavior(BoundaryEvent, CompensateEventDefinition, boolean)}
   */
  @Test
  public void testCreateBoundaryCompensateEventActivityBehavior_whenBoundaryEvent() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createBoundaryCompensateEventActivityBehavior(boundaryEvent, new CompensateEventDefinition(), true)
        .isInterrupting());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createBoundaryCompensateEventActivityBehavior(BoundaryEvent, CompensateEventDefinition, boolean)}.
   * <ul>
   *   <li>When {@link BoundaryEvent}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundaryCompensateEventActivityBehavior(BoundaryEvent, CompensateEventDefinition, boolean)}
   */
  @Test
  public void testCreateBoundaryCompensateEventActivityBehavior_whenBoundaryEvent2() {
    // Arrange
    BoundaryEvent boundaryEvent = mock(BoundaryEvent.class);

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createBoundaryCompensateEventActivityBehavior(boundaryEvent, new CompensateEventDefinition(), true)
        .isInterrupting());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createBoundaryTimerEventActivityBehavior(BoundaryEvent, TimerEventDefinition, boolean)}.
   * <ul>
   *   <li>When {@link BoundaryEvent} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundaryTimerEventActivityBehavior(BoundaryEvent, TimerEventDefinition, boolean)}
   */
  @Test
  public void testCreateBoundaryTimerEventActivityBehavior_whenBoundaryEvent() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createBoundaryTimerEventActivityBehavior(boundaryEvent, new TimerEventDefinition(), true)
        .isInterrupting());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createBoundaryTimerEventActivityBehavior(BoundaryEvent, TimerEventDefinition, boolean)}.
   * <ul>
   *   <li>When {@link BoundaryEvent}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundaryTimerEventActivityBehavior(BoundaryEvent, TimerEventDefinition, boolean)}
   */
  @Test
  public void testCreateBoundaryTimerEventActivityBehavior_whenBoundaryEvent2() {
    // Arrange
    BoundaryEvent boundaryEvent = mock(BoundaryEvent.class);

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createBoundaryTimerEventActivityBehavior(boundaryEvent, new TimerEventDefinition(), true)
        .isInterrupting());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createBoundarySignalEventActivityBehavior(BoundaryEvent, SignalEventDefinition, Signal, boolean)}.
   * <ul>
   *   <li>When {@link BoundaryEvent} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundarySignalEventActivityBehavior(BoundaryEvent, SignalEventDefinition, Signal, boolean)}
   */
  @Test
  public void testCreateBoundarySignalEventActivityBehavior_whenBoundaryEvent() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createBoundarySignalEventActivityBehavior(boundaryEvent, signalEventDefinition, new Signal("42", "Name"), true)
        .isInterrupting());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createBoundarySignalEventActivityBehavior(BoundaryEvent, SignalEventDefinition, Signal, boolean)}.
   * <ul>
   *   <li>When {@link BoundaryEvent}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundarySignalEventActivityBehavior(BoundaryEvent, SignalEventDefinition, Signal, boolean)}
   */
  @Test
  public void testCreateBoundarySignalEventActivityBehavior_whenBoundaryEvent2() {
    // Arrange
    BoundaryEvent boundaryEvent = mock(BoundaryEvent.class);
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createBoundarySignalEventActivityBehavior(boundaryEvent, signalEventDefinition, new Signal("42", "Name"), true)
        .isInterrupting());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createBoundaryMessageEventActivityBehavior(BoundaryEvent, MessageEventDefinition, boolean)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createBoundaryMessageEventActivityBehavior(BoundaryEvent, MessageEventDefinition, boolean)}.
   * <ul>
   *   <li>When {@link BoundaryEvent}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createBoundaryMessageEventActivityBehavior(BoundaryEvent, MessageEventDefinition, boolean)}
   */
  @Test
  public void testCreateBoundaryMessageEventActivityBehavior_whenBoundaryEvent() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createThrowMessageEventActivityBehavior(ThrowEvent, MessageEventDefinition, Message)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageEventActivityBehavior(ThrowEvent, MessageEventDefinition, Message)}
   */
  @Test
  public void testCreateThrowMessageEventActivityBehavior() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createThrowMessageEventActivityBehavior(ThrowEvent, MessageEventDefinition, Message)}.
   * <ul>
   *   <li>Then return ThrowEvent is {@link ThrowEvent} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageEventActivityBehavior(ThrowEvent, MessageEventDefinition, Message)}
   */
  @Test
  public void testCreateThrowMessageEventActivityBehavior_thenReturnThrowEventIsThrowEvent() {
    // Arrange
    ThrowEvent throwEvent = new ThrowEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();

    // Act and Assert
    assertSame(throwEvent,
        defaultActivityBehaviorFactory
            .createThrowMessageEventActivityBehavior(throwEvent, messageEventDefinition,
                new Message("42", "Name", "Item Ref"))
            .getThrowEvent());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, Message)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, Message)}
   */
  @Test
  public void testCreateThrowMessageEndEventActivityBehavior() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, Message)}.
   * <ul>
   *   <li>Then return EndEvent is {@link EndEvent} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, Message)}
   */
  @Test
  public void testCreateThrowMessageEndEventActivityBehavior_thenReturnEndEventIsEndEvent() {
    // Arrange
    EndEvent endEvent = new EndEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();

    // Act and Assert
    assertSame(endEvent,
        defaultActivityBehaviorFactory
            .createThrowMessageEndEventActivityBehavior(endEvent, messageEventDefinition,
                new Message("42", "Name", "Item Ref"))
            .getEndEvent());
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createThrowMessageDelegate(MessageEventDefinition)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then calls {@link BaseElement#getAttributes()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageDelegate(MessageEventDefinition)}
   */
  @Test
  public void testCreateThrowMessageDelegate_givenHashMap_thenCallsGetAttributes() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createThrowMessageDelegate(MessageEventDefinition)}.
   * <ul>
   *   <li>When {@link MessageEventDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageDelegate(MessageEventDefinition)}
   */
  @Test
  public void testCreateThrowMessageDelegate_whenMessageEventDefinition() {
    // Arrange, Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .createThrowMessageDelegate(new MessageEventDefinition()) instanceof DefaultThrowMessageJavaDelegate);
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createMessageExecutionContext(Event, MessageEventDefinition)}.
   * <ul>
   *   <li>Then return {@link DefaultMessageExecutionContext}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMessageExecutionContext(Event, MessageEventDefinition)}
   */
  @Test
  public void testCreateMessageExecutionContext_thenReturnDefaultMessageExecutionContext() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createMessageExecutionContext(Event, MessageEventDefinition)}.
   * <ul>
   *   <li>When {@link BoundaryEvent}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMessageExecutionContext(Event, MessageEventDefinition)}
   */
  @Test
  public void testCreateMessageExecutionContext_whenBoundaryEvent() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createThrowMessageJavaDelegate(String)}.
   * <ul>
   *   <li>Then return {@link ThrowMessageJavaDelegate}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageJavaDelegate(String)}
   */
  @Test
  public void testCreateThrowMessageJavaDelegate_thenReturnThrowMessageJavaDelegate() {
    // Arrange, Act and Assert
    assertTrue(defaultActivityBehaviorFactory.createThrowMessageJavaDelegate(
        "org.activiti.engine.impl.delegate.ThrowMessageDelegate") instanceof ThrowMessageJavaDelegate);
  }

  /**
   * Test
   * {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}.
   * <ul>
   *   <li>Then calls {@link ExpressionManager#createExpression(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createThrowMessageDelegateExpression(String)}
   */
  @Test
  public void testCreateThrowMessageDelegateExpression_thenCallsCreateExpression() {
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createDefaultThrowMessageDelegate()}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createMessagePayloadMappingProvider(Event, MessageEventDefinition)}.
   * <p>
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
   * Test
   * {@link DefaultActivityBehaviorFactory#createMessagePayloadMappingProvider(Event, MessageEventDefinition)}.
   * <ul>
   *   <li>When {@link BoundaryEvent}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#createMessagePayloadMappingProvider(Event, MessageEventDefinition)}
   */
  @Test
  public void testCreateMessagePayloadMappingProvider_whenBoundaryEvent() {
    // Arrange
    BoundaryEvent bpmnEvent = mock(BoundaryEvent.class);

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory.createMessagePayloadMappingProvider(bpmnEvent,
        new MessageEventDefinition()) instanceof BpmnMessagePayloadMappingProvider);
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#checkClassDelegate(Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link ExtensionAttribute#ExtensionAttribute(String)} with {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkClassDelegate(Map)}
   */
  @Test
  public void testCheckClassDelegate_givenArrayListAddExtensionAttributeWithName() {
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
   * Test {@link DefaultActivityBehaviorFactory#checkClassDelegate(Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link ExtensionAttribute#ExtensionAttribute(String)} with name is
   * {@code class}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkClassDelegate(Map)}
   */
  @Test
  public void testCheckClassDelegate_givenArrayListAddExtensionAttributeWithNameIsClass() {
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
   * Test {@link DefaultActivityBehaviorFactory#checkClassDelegate(Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code activiti} is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkClassDelegate(Map)}
   */
  @Test
  public void testCheckClassDelegate_givenArrayList_whenHashMapActivitiIsArrayList() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("activiti", new ArrayList<>());
    attributes.computeIfPresent("class", mock(BiFunction.class));

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkClassDelegate(attributes).isPresent());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#checkClassDelegate(Map)}.
   * <ul>
   *   <li>Given {@code class}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkClassDelegate(Map)}
   */
  @Test
  public void testCheckClassDelegate_givenClass_thenReturnNotPresent() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("class", mock(BiFunction.class));

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkClassDelegate(attributes).isPresent());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#checkClassDelegate(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkClassDelegate(Map)}
   */
  @Test
  public void testCheckClassDelegate_whenHashMap_thenReturnNotPresent() {
    // Arrange, Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkClassDelegate(new HashMap<>()).isPresent());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#checkDelegateExpression(Map)}.
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkDelegateExpression(Map)}
   */
  @Test
  public void testCheckDelegateExpression() {
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
   * Test {@link DefaultActivityBehaviorFactory#checkDelegateExpression(Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link ExtensionAttribute#ExtensionAttribute(String)} with {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkDelegateExpression(Map)}
   */
  @Test
  public void testCheckDelegateExpression_givenArrayListAddExtensionAttributeWithName() {
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
   * Test {@link DefaultActivityBehaviorFactory#checkDelegateExpression(Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code activiti} is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkDelegateExpression(Map)}
   */
  @Test
  public void testCheckDelegateExpression_givenArrayList_whenHashMapActivitiIsArrayList() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("activiti", new ArrayList<>());
    attributes.computeIfPresent("delegateExpression", mock(BiFunction.class));

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkDelegateExpression(attributes).isPresent());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#checkDelegateExpression(Map)}.
   * <ul>
   *   <li>Given {@code delegateExpression}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkDelegateExpression(Map)}
   */
  @Test
  public void testCheckDelegateExpression_givenDelegateExpression_thenReturnNotPresent() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("delegateExpression", mock(BiFunction.class));

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkDelegateExpression(attributes).isPresent());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#checkDelegateExpression(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#checkDelegateExpression(Map)}
   */
  @Test
  public void testCheckDelegateExpression_whenHashMap_thenReturnNotPresent() {
    // Arrange, Act and Assert
    assertFalse(defaultActivityBehaviorFactory.checkDelegateExpression(new HashMap<>()).isPresent());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link ExtensionAttribute#ExtensionAttribute(String)} with name is
   * {@code activiti}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}
   */
  @Test
  public void testGetAttributeValue_givenArrayListAddExtensionAttributeWithNameIsActiviti() {
    // Arrange
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
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link ExtensionAttribute#ExtensionAttribute(String)} with name is
   * {@code activiti}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}
   */
  @Test
  public void testGetAttributeValue_givenArrayListAddExtensionAttributeWithNameIsActiviti2() {
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
   * Test {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}
   */
  @Test
  public void testGetAttributeValue_givenArrayList_thenReturnNotPresent() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("activiti", new ArrayList<>());

    // Act and Assert
    assertFalse(defaultActivityBehaviorFactory.getAttributeValue(attributes, "Name").isPresent());
  }

  /**
   * Test {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}.
   * <ul>
   *   <li>Given {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}
   */
  @Test
  public void testGetAttributeValue_givenBiFunction() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("activiti", mock(BiFunction.class));
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
   * Method under test:
   * {@link DefaultActivityBehaviorFactory#getAttributeValue(Map, String)}
   */
  @Test
  public void testGetAttributeValue_whenHashMap_thenReturnNotPresent() {
    // Arrange, Act and Assert
    assertFalse(defaultActivityBehaviorFactory.getAttributeValue(new HashMap<>(), "Name").isPresent());
  }
}
