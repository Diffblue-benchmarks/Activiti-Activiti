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
package org.activiti.engine.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.Map;
import java.util.Set;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.CompensateEventDefinition;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.IntermediateCatchEvent;
import org.activiti.bpmn.model.ManualTask;
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
import org.activiti.bpmn.model.ThrowEvent;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.bpmn.model.Transaction;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.BoundaryMessageEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.CallActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.IntermediateCatchMessageEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.IntermediateThrowMessageEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.TerminateEndEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ThrowMessageEndEventActivityBehavior;
import org.activiti.engine.impl.bpmn.helper.ClassDelegate;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContext;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContextFactory;
import org.activiti.engine.impl.bpmn.parser.factory.MessageExecutionContext;
import org.activiti.engine.impl.delegate.BpmnMessagePayloadMappingProvider;
import org.activiti.engine.impl.delegate.BpmnMessagePayloadMappingProviderFactory;
import org.activiti.engine.impl.delegate.DefaultThrowMessageJavaDelegate;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProviderFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestActivityBehaviorFactoryDiffblueTest {
  @InjectMocks
  private TestActivityBehaviorFactory testActivityBehaviorFactory;

  /**
   * Test {@link TestActivityBehaviorFactory#TestActivityBehaviorFactory()}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#TestActivityBehaviorFactory()}
   */
  @Test
  public void testNewTestActivityBehaviorFactory() {
    // Arrange and Act
    TestActivityBehaviorFactory actualTestActivityBehaviorFactory = new TestActivityBehaviorFactory();

    // Assert
    assertTrue(actualTestActivityBehaviorFactory
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
    assertTrue(actualTestActivityBehaviorFactory
        .getMessagePayloadMappingProviderFactory() instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(actualTestActivityBehaviorFactory.getWrappedActivityBehaviorFactory());
    assertNull(actualTestActivityBehaviorFactory.getExpressionManager());
    assertFalse(actualTestActivityBehaviorFactory.allServiceTasksNoOp);
    assertTrue(actualTestActivityBehaviorFactory.mockedClassDelegatesMapping.isEmpty());
    assertTrue(actualTestActivityBehaviorFactory.noOpServiceTaskClassNames.isEmpty());
    assertTrue(actualTestActivityBehaviorFactory.noOpServiceTaskIds.isEmpty());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#TestActivityBehaviorFactory(ActivityBehaviorFactory)}.
   * <ul>
   *   <li>Given {@link MessagePayloadMappingProviderFactory}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#TestActivityBehaviorFactory(ActivityBehaviorFactory)}
   */
  @Test
  public void testNewTestActivityBehaviorFactory_givenMessagePayloadMappingProviderFactory() {
    // Arrange
    DefaultActivityBehaviorFactory wrappedActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    wrappedActivityBehaviorFactory
        .setMessagePayloadMappingProviderFactory(mock(MessagePayloadMappingProviderFactory.class));

    // Act
    TestActivityBehaviorFactory actualTestActivityBehaviorFactory = new TestActivityBehaviorFactory(
        wrappedActivityBehaviorFactory);

    // Assert
    assertTrue(actualTestActivityBehaviorFactory
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
    assertTrue(actualTestActivityBehaviorFactory
        .getMessagePayloadMappingProviderFactory() instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(actualTestActivityBehaviorFactory.getExpressionManager());
    assertFalse(actualTestActivityBehaviorFactory.allServiceTasksNoOp);
    assertTrue(actualTestActivityBehaviorFactory.mockedClassDelegatesMapping.isEmpty());
    assertTrue(actualTestActivityBehaviorFactory.noOpServiceTaskClassNames.isEmpty());
    assertTrue(actualTestActivityBehaviorFactory.noOpServiceTaskIds.isEmpty());
    assertSame(wrappedActivityBehaviorFactory, actualTestActivityBehaviorFactory.getWrappedActivityBehaviorFactory());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#TestActivityBehaviorFactory(ActivityBehaviorFactory)}.
   * <ul>
   *   <li>When
   * {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#TestActivityBehaviorFactory(ActivityBehaviorFactory)}
   */
  @Test
  public void testNewTestActivityBehaviorFactory_whenDefaultActivityBehaviorFactory() {
    // Arrange
    DefaultActivityBehaviorFactory wrappedActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act
    TestActivityBehaviorFactory actualTestActivityBehaviorFactory = new TestActivityBehaviorFactory(
        wrappedActivityBehaviorFactory);

    // Assert
    assertTrue(actualTestActivityBehaviorFactory
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
    assertTrue(actualTestActivityBehaviorFactory
        .getMessagePayloadMappingProviderFactory() instanceof BpmnMessagePayloadMappingProviderFactory);
    assertNull(actualTestActivityBehaviorFactory.getExpressionManager());
    assertFalse(actualTestActivityBehaviorFactory.allServiceTasksNoOp);
    assertTrue(actualTestActivityBehaviorFactory.mockedClassDelegatesMapping.isEmpty());
    assertTrue(actualTestActivityBehaviorFactory.noOpServiceTaskClassNames.isEmpty());
    assertTrue(actualTestActivityBehaviorFactory.noOpServiceTaskIds.isEmpty());
    assertSame(wrappedActivityBehaviorFactory, actualTestActivityBehaviorFactory.getWrappedActivityBehaviorFactory());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link TestActivityBehaviorFactory#setWrappedActivityBehaviorFactory(ActivityBehaviorFactory)}
   *   <li>{@link TestActivityBehaviorFactory#setAllServiceTasksNoOp()}
   *   <li>{@link TestActivityBehaviorFactory#getWrappedActivityBehaviorFactory()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory();
    DefaultActivityBehaviorFactory wrappedActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act
    testActivityBehaviorFactory.setWrappedActivityBehaviorFactory(wrappedActivityBehaviorFactory);
    testActivityBehaviorFactory.setAllServiceTasksNoOp();

    // Assert that nothing has changed
    assertSame(wrappedActivityBehaviorFactory, testActivityBehaviorFactory.getWrappedActivityBehaviorFactory());
  }

  /**
   * Test {@link TestActivityBehaviorFactory#createTaskActivityBehavior(Task)}.
   * <ul>
   *   <li>Then return MultiInstanceActivityBehavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createTaskActivityBehavior(Task)}
   */
  @Test
  public void testCreateTaskActivityBehavior_thenReturnMultiInstanceActivityBehaviorIsNull() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act and Assert
    assertNull(testActivityBehaviorFactory.createTaskActivityBehavior(new Task()).getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createManualTaskActivityBehavior(ManualTask)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createManualTaskActivityBehavior(ManualTask)}
   */
  @Test
  public void testCreateManualTaskActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act and Assert
    assertNull(testActivityBehaviorFactory.createManualTaskActivityBehavior(new ManualTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createReceiveTaskActivityBehavior(ReceiveTask)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createReceiveTaskActivityBehavior(ReceiveTask)}
   */
  @Test
  public void testCreateReceiveTaskActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act and Assert
    assertNull(testActivityBehaviorFactory.createReceiveTaskActivityBehavior(new ReceiveTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createUserTaskActivityBehavior(UserTask)}.
   * <ul>
   *   <li>Then return MultiInstanceActivityBehavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createUserTaskActivityBehavior(UserTask)}
   */
  @Test
  public void testCreateUserTaskActivityBehavior_thenReturnMultiInstanceActivityBehaviorIsNull() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act and Assert
    assertNull(
        testActivityBehaviorFactory.createUserTaskActivityBehavior(new UserTask()).getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}.
   * <ul>
   *   <li>Then return ClassName is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createClassDelegateServiceTask(ServiceTask)}
   */
  @Test
  public void testCreateClassDelegateServiceTask_thenReturnClassNameIsNull() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act
    ClassDelegate actualCreateClassDelegateServiceTaskResult = testActivityBehaviorFactory
        .createClassDelegateServiceTask(new ServiceTask());

    // Assert
    assertNull(actualCreateClassDelegateServiceTaskResult.getClassName());
    assertNull(actualCreateClassDelegateServiceTaskResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createWebServiceActivityBehavior(SendTask)}
   * with {@code sendTask}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createWebServiceActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateWebServiceActivityBehaviorWithSendTask() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act and Assert
    assertNull(testActivityBehaviorFactory.createWebServiceActivityBehavior(new SendTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createWebServiceActivityBehavior(ServiceTask)}
   * with {@code serviceTask}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createWebServiceActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateWebServiceActivityBehaviorWithServiceTask() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act and Assert
    assertNull(testActivityBehaviorFactory.createWebServiceActivityBehavior(new ServiceTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link TestActivityBehaviorFactory#createMailActivityBehavior(SendTask)}
   * with {@code sendTask}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createMailActivityBehavior(SendTask)}
   */
  @Test
  public void testCreateMailActivityBehaviorWithSendTask() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act and Assert
    assertNull(
        testActivityBehaviorFactory.createMailActivityBehavior(new SendTask()).getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createMailActivityBehavior(ServiceTask)}
   * with {@code serviceTask}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createMailActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateMailActivityBehaviorWithServiceTask() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act and Assert
    assertNull(
        testActivityBehaviorFactory.createMailActivityBehavior(new ServiceTask()).getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createShellActivityBehavior(ServiceTask)}.
   * <ul>
   *   <li>Then return MultiInstanceActivityBehavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createShellActivityBehavior(ServiceTask)}
   */
  @Test
  public void testCreateShellActivityBehavior_thenReturnMultiInstanceActivityBehaviorIsNull() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act and Assert
    assertNull(
        testActivityBehaviorFactory.createShellActivityBehavior(new ServiceTask()).getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createScriptTaskActivityBehavior(ScriptTask)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createScriptTaskActivityBehavior(ScriptTask)}
   */
  @Test
  public void testCreateScriptTaskActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act and Assert
    assertNull(testActivityBehaviorFactory.createScriptTaskActivityBehavior(new ScriptTask())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createSequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createSequentialMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testCreateSequentialMultiInstanceBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());
    AdhocSubProcess activity = new AdhocSubProcess();
    AbstractBpmnActivityBehavior innerActivityBehavior = new AbstractBpmnActivityBehavior();

    // Act
    SequentialMultiInstanceBehavior actualCreateSequentialMultiInstanceBehaviorResult = testActivityBehaviorFactory
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
   * {@link TestActivityBehaviorFactory#createParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testCreateParallelMultiInstanceBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());
    AdhocSubProcess activity = new AdhocSubProcess();
    AbstractBpmnActivityBehavior innerActivityBehavior = new AbstractBpmnActivityBehavior();

    // Act
    ParallelMultiInstanceBehavior actualCreateParallelMultiInstanceBehaviorResult = testActivityBehaviorFactory
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
   * {@link TestActivityBehaviorFactory#createSubprocessActivityBehavior(SubProcess)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createSubprocessActivityBehavior(SubProcess)}
   */
  @Test
  public void testCreateSubprocessActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act and Assert
    assertNull(testActivityBehaviorFactory.createSubprocessActivityBehavior(new SubProcess())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createEventSubProcessErrorStartEventActivityBehavior(StartEvent)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createEventSubProcessErrorStartEventActivityBehavior(StartEvent)}
   */
  @Test
  public void testCreateEventSubProcessErrorStartEventActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act and Assert
    assertNull(testActivityBehaviorFactory.createEventSubProcessErrorStartEventActivityBehavior(new StartEvent())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createEventSubProcessMessageStartEventActivityBehavior(StartEvent, MessageEventDefinition)}
   */
  @Test
  public void testCreateEventSubProcessMessageStartEventActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());
    StartEvent startEvent = new StartEvent();

    // Act and Assert
    assertNull(testActivityBehaviorFactory
        .createEventSubProcessMessageStartEventActivityBehavior(startEvent, new MessageEventDefinition())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createAdhocSubprocessActivityBehavior(SubProcess)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createAdhocSubprocessActivityBehavior(SubProcess)}
   */
  @Test
  public void testCreateAdhocSubprocessActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act and Assert
    assertNull(testActivityBehaviorFactory.createAdhocSubprocessActivityBehavior(new SubProcess())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}.
   * <ul>
   *   <li>Then return ProcessDefinitionKey is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createCallActivityBehavior(CallActivity)}
   */
  @Test
  public void testCreateCallActivityBehavior_thenReturnProcessDefinitionKeyIsNull() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = testActivityBehaviorFactory
        .createCallActivityBehavior(new CallActivity());

    // Assert
    assertNull(actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createTransactionActivityBehavior(Transaction)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createTransactionActivityBehavior(Transaction)}
   */
  @Test
  public void testCreateTransactionActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act and Assert
    assertNull(testActivityBehaviorFactory.createTransactionActivityBehavior(new Transaction())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createIntermediateCatchEventActivityBehavior(IntermediateCatchEvent)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createIntermediateCatchEventActivityBehavior(IntermediateCatchEvent)}
   */
  @Test
  public void testCreateIntermediateCatchEventActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act and Assert
    assertNull(testActivityBehaviorFactory.createIntermediateCatchEventActivityBehavior(new IntermediateCatchEvent())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createIntermediateCatchMessageEventActivityBehavior(IntermediateCatchEvent, MessageEventDefinition)}
   */
  @Test
  public void testCreateIntermediateCatchMessageEventActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());
    IntermediateCatchEvent intermediateCatchEvent = new IntermediateCatchEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();

    // Act
    IntermediateCatchMessageEventActivityBehavior actualCreateIntermediateCatchMessageEventActivityBehaviorResult = testActivityBehaviorFactory
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
   * {@link TestActivityBehaviorFactory#createIntermediateCatchTimerEventActivityBehavior(IntermediateCatchEvent, TimerEventDefinition)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createIntermediateCatchTimerEventActivityBehavior(IntermediateCatchEvent, TimerEventDefinition)}
   */
  @Test
  public void testCreateIntermediateCatchTimerEventActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());
    IntermediateCatchEvent intermediateCatchEvent = new IntermediateCatchEvent();

    // Act and Assert
    assertNull(testActivityBehaviorFactory
        .createIntermediateCatchTimerEventActivityBehavior(intermediateCatchEvent, new TimerEventDefinition())
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createIntermediateCatchSignalEventActivityBehavior(IntermediateCatchEvent, SignalEventDefinition, Signal)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createIntermediateCatchSignalEventActivityBehavior(IntermediateCatchEvent, SignalEventDefinition, Signal)}
   */
  @Test
  public void testCreateIntermediateCatchSignalEventActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());
    IntermediateCatchEvent intermediateCatchEvent = new IntermediateCatchEvent();
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act and Assert
    assertNull(testActivityBehaviorFactory
        .createIntermediateCatchSignalEventActivityBehavior(intermediateCatchEvent, signalEventDefinition,
            new Signal("42", "Name"))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createIntermediateThrowSignalEventActivityBehavior(ThrowEvent, SignalEventDefinition, Signal)}
   */
  @Test
  public void testCreateIntermediateThrowSignalEventActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());
    ThrowEvent throwEvent = new ThrowEvent();
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act and Assert
    assertNull(testActivityBehaviorFactory
        .createIntermediateThrowSignalEventActivityBehavior(throwEvent, signalEventDefinition, new Signal("42", "Name"))
        .getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createErrorEndEventActivityBehavior(EndEvent, ErrorEventDefinition)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createErrorEndEventActivityBehavior(EndEvent, ErrorEventDefinition)}
   */
  @Test
  public void testCreateErrorEndEventActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new TestActivityBehaviorFactory(new DefaultActivityBehaviorFactory()));
    EndEvent endEvent = new EndEvent();

    // Act and Assert
    assertNull(testActivityBehaviorFactory.createErrorEndEventActivityBehavior(endEvent, new ErrorEventDefinition())
        .getErrorRef());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createErrorEndEventActivityBehavior(EndEvent, ErrorEventDefinition)}.
   * <ul>
   *   <li>Then return ErrorRef is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createErrorEndEventActivityBehavior(EndEvent, ErrorEventDefinition)}
   */
  @Test
  public void testCreateErrorEndEventActivityBehavior_thenReturnErrorRefIsNull() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());
    EndEvent endEvent = new EndEvent();

    // Act and Assert
    assertNull(testActivityBehaviorFactory.createErrorEndEventActivityBehavior(endEvent, new ErrorEventDefinition())
        .getErrorRef());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  public void testCreateTerminateEndEventActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new TestActivityBehaviorFactory(new DefaultActivityBehaviorFactory()));

    // Act
    TerminateEndEventActivityBehavior actualCreateTerminateEndEventActivityBehaviorResult = testActivityBehaviorFactory
        .createTerminateEndEventActivityBehavior(new EndEvent());

    // Assert
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateAll());
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateMultiInstance());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}.
   * <ul>
   *   <li>Then return not TerminateAll.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createTerminateEndEventActivityBehavior(EndEvent)}
   */
  @Test
  public void testCreateTerminateEndEventActivityBehavior_thenReturnNotTerminateAll() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act
    TerminateEndEventActivityBehavior actualCreateTerminateEndEventActivityBehaviorResult = testActivityBehaviorFactory
        .createTerminateEndEventActivityBehavior(new EndEvent());

    // Assert
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateAll());
    assertFalse(actualCreateTerminateEndEventActivityBehaviorResult.isTerminateMultiInstance());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createBoundaryEventActivityBehavior(BoundaryEvent, boolean)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createBoundaryEventActivityBehavior(BoundaryEvent, boolean)}
   */
  @Test
  public void testCreateBoundaryEventActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new TestActivityBehaviorFactory(new DefaultActivityBehaviorFactory()));

    // Act and Assert
    assertTrue(
        testActivityBehaviorFactory.createBoundaryEventActivityBehavior(new BoundaryEvent(), true).isInterrupting());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createBoundaryEventActivityBehavior(BoundaryEvent, boolean)}.
   * <ul>
   *   <li>Then return Interrupting.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createBoundaryEventActivityBehavior(BoundaryEvent, boolean)}
   */
  @Test
  public void testCreateBoundaryEventActivityBehavior_thenReturnInterrupting() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act and Assert
    assertTrue(
        testActivityBehaviorFactory.createBoundaryEventActivityBehavior(new BoundaryEvent(), true).isInterrupting());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createBoundaryCancelEventActivityBehavior(CancelEventDefinition)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createBoundaryCancelEventActivityBehavior(CancelEventDefinition)}
   */
  @Test
  public void testCreateBoundaryCancelEventActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new TestActivityBehaviorFactory(new DefaultActivityBehaviorFactory()));

    // Act and Assert
    assertFalse(testActivityBehaviorFactory.createBoundaryCancelEventActivityBehavior(new CancelEventDefinition())
        .isInterrupting());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createBoundaryCancelEventActivityBehavior(CancelEventDefinition)}.
   * <ul>
   *   <li>Then return not Interrupting.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createBoundaryCancelEventActivityBehavior(CancelEventDefinition)}
   */
  @Test
  public void testCreateBoundaryCancelEventActivityBehavior_thenReturnNotInterrupting() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());

    // Act and Assert
    assertFalse(testActivityBehaviorFactory.createBoundaryCancelEventActivityBehavior(new CancelEventDefinition())
        .isInterrupting());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createBoundaryTimerEventActivityBehavior(BoundaryEvent, TimerEventDefinition, boolean)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createBoundaryTimerEventActivityBehavior(BoundaryEvent, TimerEventDefinition, boolean)}
   */
  @Test
  public void testCreateBoundaryTimerEventActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new TestActivityBehaviorFactory(new DefaultActivityBehaviorFactory()));
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    // Act and Assert
    assertTrue(testActivityBehaviorFactory
        .createBoundaryTimerEventActivityBehavior(boundaryEvent, new TimerEventDefinition(), true)
        .isInterrupting());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createBoundaryTimerEventActivityBehavior(BoundaryEvent, TimerEventDefinition, boolean)}.
   * <ul>
   *   <li>Then return Interrupting.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createBoundaryTimerEventActivityBehavior(BoundaryEvent, TimerEventDefinition, boolean)}
   */
  @Test
  public void testCreateBoundaryTimerEventActivityBehavior_thenReturnInterrupting() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    // Act and Assert
    assertTrue(testActivityBehaviorFactory
        .createBoundaryTimerEventActivityBehavior(boundaryEvent, new TimerEventDefinition(), true)
        .isInterrupting());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createBoundarySignalEventActivityBehavior(BoundaryEvent, SignalEventDefinition, Signal, boolean)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createBoundarySignalEventActivityBehavior(BoundaryEvent, SignalEventDefinition, Signal, boolean)}
   */
  @Test
  public void testCreateBoundarySignalEventActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new TestActivityBehaviorFactory(new DefaultActivityBehaviorFactory()));
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act and Assert
    assertTrue(testActivityBehaviorFactory
        .createBoundarySignalEventActivityBehavior(boundaryEvent, signalEventDefinition, new Signal("42", "Name"), true)
        .isInterrupting());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createBoundarySignalEventActivityBehavior(BoundaryEvent, SignalEventDefinition, Signal, boolean)}.
   * <ul>
   *   <li>Then return Interrupting.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createBoundarySignalEventActivityBehavior(BoundaryEvent, SignalEventDefinition, Signal, boolean)}
   */
  @Test
  public void testCreateBoundarySignalEventActivityBehavior_thenReturnInterrupting() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act and Assert
    assertTrue(testActivityBehaviorFactory
        .createBoundarySignalEventActivityBehavior(boundaryEvent, signalEventDefinition, new Signal("42", "Name"), true)
        .isInterrupting());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createBoundaryMessageEventActivityBehavior(BoundaryEvent, MessageEventDefinition, boolean)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createBoundaryMessageEventActivityBehavior(BoundaryEvent, MessageEventDefinition, boolean)}
   */
  @Test
  public void testCreateBoundaryMessageEventActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();

    // Act
    BoundaryMessageEventActivityBehavior actualCreateBoundaryMessageEventActivityBehaviorResult = testActivityBehaviorFactory
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
   * {@link TestActivityBehaviorFactory#createBoundaryCompensateEventActivityBehavior(BoundaryEvent, CompensateEventDefinition, boolean)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createBoundaryCompensateEventActivityBehavior(BoundaryEvent, CompensateEventDefinition, boolean)}
   */
  @Test
  public void testCreateBoundaryCompensateEventActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new TestActivityBehaviorFactory(new DefaultActivityBehaviorFactory()));
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    // Act and Assert
    assertTrue(testActivityBehaviorFactory
        .createBoundaryCompensateEventActivityBehavior(boundaryEvent, new CompensateEventDefinition(), true)
        .isInterrupting());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createBoundaryCompensateEventActivityBehavior(BoundaryEvent, CompensateEventDefinition, boolean)}.
   * <ul>
   *   <li>Then return Interrupting.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createBoundaryCompensateEventActivityBehavior(BoundaryEvent, CompensateEventDefinition, boolean)}
   */
  @Test
  public void testCreateBoundaryCompensateEventActivityBehavior_thenReturnInterrupting() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    // Act and Assert
    assertTrue(testActivityBehaviorFactory
        .createBoundaryCompensateEventActivityBehavior(boundaryEvent, new CompensateEventDefinition(), true)
        .isInterrupting());
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#createThrowMessageEventActivityBehavior(ThrowEvent, MessageEventDefinition, Message)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createThrowMessageEventActivityBehavior(ThrowEvent, MessageEventDefinition, Message)}
   */
  @Test
  public void testCreateThrowMessageEventActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());
    ThrowEvent throwEvent = new ThrowEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();

    // Act
    IntermediateThrowMessageEventActivityBehavior actualCreateThrowMessageEventActivityBehaviorResult = testActivityBehaviorFactory
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
   * {@link TestActivityBehaviorFactory#createThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, Message)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#createThrowMessageEndEventActivityBehavior(EndEvent, MessageEventDefinition, Message)}
   */
  @Test
  public void testCreateThrowMessageEndEventActivityBehavior() {
    // Arrange
    TestActivityBehaviorFactory testActivityBehaviorFactory = new TestActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory());
    EndEvent endEvent = new EndEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();

    // Act
    ThrowMessageEndEventActivityBehavior actualCreateThrowMessageEndEventActivityBehaviorResult = testActivityBehaviorFactory
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
   * Test {@link TestActivityBehaviorFactory#addClassDelegateMock(String, Class)}
   * with {@code originalClassFqn}, {@code mockClass}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#addClassDelegateMock(String, Class)}
   */
  @Test
  public void testAddClassDelegateMockWithOriginalClassFqnMockClass() {
    // Arrange
    Class<Object> mockClass = Object.class;

    // Act
    testActivityBehaviorFactory.addClassDelegateMock("Original Class Fqn", mockClass);

    // Assert
    Map<String, String> stringStringMap = testActivityBehaviorFactory.mockedClassDelegatesMapping;
    assertEquals(1, stringStringMap.size());
    assertEquals("java.lang.Object", stringStringMap.get("Original Class Fqn"));
  }

  /**
   * Test {@link TestActivityBehaviorFactory#addClassDelegateMock(String, String)}
   * with {@code originalClassFqn}, {@code mockedClassFqn}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#addClassDelegateMock(String, String)}
   */
  @Test
  public void testAddClassDelegateMockWithOriginalClassFqnMockedClassFqn() {
    // Arrange and Act
    testActivityBehaviorFactory.addClassDelegateMock("Original Class Fqn", "Mocked Class Fqn");

    // Assert
    Map<String, String> stringStringMap = testActivityBehaviorFactory.mockedClassDelegatesMapping;
    assertEquals(1, stringStringMap.size());
    assertEquals("Mocked Class Fqn", stringStringMap.get("Original Class Fqn"));
  }

  /**
   * Test {@link TestActivityBehaviorFactory#addNoOpServiceTaskById(String)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#addNoOpServiceTaskById(String)}
   */
  @Test
  public void testAddNoOpServiceTaskById() {
    // Arrange and Act
    testActivityBehaviorFactory.addNoOpServiceTaskById("42");

    // Assert
    Set<String> stringSet = testActivityBehaviorFactory.noOpServiceTaskIds;
    assertEquals(1, stringSet.size());
    assertTrue(stringSet.contains("42"));
  }

  /**
   * Test
   * {@link TestActivityBehaviorFactory#addNoOpServiceTaskByClassName(String)}.
   * <p>
   * Method under test:
   * {@link TestActivityBehaviorFactory#addNoOpServiceTaskByClassName(String)}
   */
  @Test
  public void testAddNoOpServiceTaskByClassName() {
    // Arrange and Act
    testActivityBehaviorFactory.addNoOpServiceTaskByClassName("Class Name");

    // Assert
    Set<String> stringSet = testActivityBehaviorFactory.noOpServiceTaskClassNames;
    assertEquals(1, stringSet.size());
    assertTrue(stringSet.contains("Class Name"));
  }
}
