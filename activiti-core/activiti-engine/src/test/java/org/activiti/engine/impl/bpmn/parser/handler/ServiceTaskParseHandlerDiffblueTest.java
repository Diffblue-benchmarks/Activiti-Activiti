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
package org.activiti.engine.impl.bpmn.parser.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.engine.impl.bpmn.behavior.MailActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ServiceTaskDelegateExpressionActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ServiceTaskExpressionActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ShellActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.WebServiceActivityBehavior;
import org.activiti.engine.impl.bpmn.helper.ClassDelegate;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParseHandlers;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultListenerFactory;
import org.activiti.engine.impl.delegate.ActivityBehavior;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class ServiceTaskParseHandlerDiffblueTest {
  /**
   * Test {@link ServiceTaskParseHandler#executeParse(BpmnParse, ServiceTask)}
   * with {@code BpmnParse}, {@code ServiceTask}.
   * <p>
   * Method under test:
   * {@link ServiceTaskParseHandler#executeParse(BpmnParse, ServiceTask)}
   */
  @Test
  public void testExecuteParseWithBpmnParseServiceTask() {
    // Arrange
    ServiceTaskParseHandler serviceTaskParseHandler = new ServiceTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType(ServiceTask.MAIL_TASK);
    serviceTask.setOperationRef(null);

    // Act
    serviceTaskParseHandler.executeParse(bpmnParse, serviceTask);

    // Assert
    Object behavior = serviceTask.getBehavior();
    assertTrue(behavior instanceof MailActivityBehavior);
    assertNull(((MailActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link ServiceTaskParseHandler#executeParse(BpmnParse, ServiceTask)}
   * with {@code BpmnParse}, {@code ServiceTask}.
   * <p>
   * Method under test:
   * {@link ServiceTaskParseHandler#executeParse(BpmnParse, ServiceTask)}
   */
  @Test
  public void testExecuteParseWithBpmnParseServiceTask2() {
    // Arrange
    ServiceTaskParseHandler serviceTaskParseHandler = new ServiceTaskParseHandler();

    DefaultActivityBehaviorFactory activityBehaviorFactory = new DefaultActivityBehaviorFactory();
    activityBehaviorFactory.setExpressionManager(new ExpressionManager());

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(activityBehaviorFactory);
    BpmnParse bpmnParse = new BpmnParse(parser);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("");
    serviceTask.setOperationRef(null);

    // Act
    serviceTaskParseHandler.executeParse(bpmnParse, serviceTask);

    // Assert
    Object behavior = serviceTask.getBehavior();
    assertTrue(behavior instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link ServiceTaskParseHandler#executeParse(BpmnParse, ServiceTask)}
   * with {@code BpmnParse}, {@code ServiceTask}.
   * <p>
   * Method under test:
   * {@link ServiceTaskParseHandler#executeParse(BpmnParse, ServiceTask)}
   */
  @Test
  public void testExecuteParseWithBpmnParseServiceTask3() {
    // Arrange
    ServiceTaskParseHandler serviceTaskParseHandler = new ServiceTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("shell");
    serviceTask.setOperationRef(null);

    // Act
    serviceTaskParseHandler.executeParse(bpmnParse, serviceTask);

    // Assert
    Object behavior = serviceTask.getBehavior();
    assertTrue(behavior instanceof ShellActivityBehavior);
    assertNull(((ShellActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link ServiceTaskParseHandler#executeParse(BpmnParse, ServiceTask)}
   * with {@code BpmnParse}, {@code ServiceTask}.
   * <ul>
   *   <li>Then {@link ServiceTask} (default constructor) Behavior is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskParseHandler#executeParse(BpmnParse, ServiceTask)}
   */
  @Test
  public void testExecuteParseWithBpmnParseServiceTask_thenServiceTaskBehaviorIsNull() {
    // Arrange
    ServiceTaskParseHandler serviceTaskParseHandler = new ServiceTaskParseHandler();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("Service Task");
    serviceTask.setOperationRef(null);

    // Act
    serviceTaskParseHandler.executeParse(bpmnParse, serviceTask);

    // Assert that nothing has changed
    assertNull(serviceTask.getBehavior());
  }

  /**
   * Test
   * {@link ServiceTaskParseHandler#createActivityBehaviorForServiceTaskType(BpmnParse, ServiceTask)}.
   * <p>
   * Method under test:
   * {@link ServiceTaskParseHandler#createActivityBehaviorForServiceTaskType(BpmnParse, ServiceTask)}
   */
  @Test
  public void testCreateActivityBehaviorForServiceTaskType() {
    // Arrange
    ServiceTaskParseHandler serviceTaskParseHandler = new ServiceTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType(ServiceTask.MAIL_TASK);

    // Act
    serviceTaskParseHandler.createActivityBehaviorForServiceTaskType(bpmnParse, serviceTask);

    // Assert
    Object behavior = serviceTask.getBehavior();
    assertTrue(behavior instanceof MailActivityBehavior);
    assertNull(((MailActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link ServiceTaskParseHandler#createActivityBehaviorForServiceTaskType(BpmnParse, ServiceTask)}.
   * <p>
   * Method under test:
   * {@link ServiceTaskParseHandler#createActivityBehaviorForServiceTaskType(BpmnParse, ServiceTask)}
   */
  @Test
  public void testCreateActivityBehaviorForServiceTaskType2() {
    // Arrange
    ServiceTaskParseHandler serviceTaskParseHandler = new ServiceTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("shell");

    // Act
    serviceTaskParseHandler.createActivityBehaviorForServiceTaskType(bpmnParse, serviceTask);

    // Assert
    Object behavior = serviceTask.getBehavior();
    assertTrue(behavior instanceof ShellActivityBehavior);
    assertNull(((ShellActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link ServiceTaskParseHandler#createActivityBehaviorForServiceTaskType(BpmnParse, ServiceTask)}.
   * <ul>
   *   <li>Then {@link ServiceTask} (default constructor) Behavior is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskParseHandler#createActivityBehaviorForServiceTaskType(BpmnParse, ServiceTask)}
   */
  @Test
  public void testCreateActivityBehaviorForServiceTaskType_thenServiceTaskBehaviorIsNull() {
    // Arrange
    ServiceTaskParseHandler serviceTaskParseHandler = new ServiceTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("Trying to load class with current thread context classloader: {}");

    // Act
    serviceTaskParseHandler.createActivityBehaviorForServiceTaskType(bpmnParse, serviceTask);

    // Assert that nothing has changed
    assertNull(serviceTask.getBehavior());
  }

  /**
   * Test
   * {@link ServiceTaskParseHandler#createMailActivityBehavior(BpmnParse, ServiceTask)}.
   * <ul>
   *   <li>Then {@link ServiceTask} (default constructor) Behavior
   * {@link MailActivityBehavior}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskParseHandler#createMailActivityBehavior(BpmnParse, ServiceTask)}
   */
  @Test
  public void testCreateMailActivityBehavior_thenServiceTaskBehaviorMailActivityBehavior() {
    // Arrange
    ServiceTaskParseHandler serviceTaskParseHandler = new ServiceTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    ServiceTask serviceTask = new ServiceTask();

    // Act
    serviceTaskParseHandler.createMailActivityBehavior(bpmnParse, serviceTask);

    // Assert
    Object behavior = serviceTask.getBehavior();
    assertTrue(behavior instanceof MailActivityBehavior);
    assertNull(((MailActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link ServiceTaskParseHandler#createMuleActivityBehavior(BpmnParse, ServiceTask)}.
   * <ul>
   *   <li>Then calls {@link BpmnParser#getActivityBehaviorFactory()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskParseHandler#createMuleActivityBehavior(BpmnParse, ServiceTask)}
   */
  @Test
  public void testCreateMuleActivityBehavior_thenCallsGetActivityBehaviorFactory() {
    // Arrange
    ServiceTaskParseHandler serviceTaskParseHandler = new ServiceTaskParseHandler();
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = mock(DefaultActivityBehaviorFactory.class);
    when(defaultActivityBehaviorFactory.createMuleActivityBehavior(Mockito.<ServiceTask>any()))
        .thenReturn(mock(ActivityBehavior.class));
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(defaultActivityBehaviorFactory);
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    // Act
    serviceTaskParseHandler.createMuleActivityBehavior(bpmnParse, new ServiceTask());

    // Assert
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
    verify(defaultActivityBehaviorFactory).createMuleActivityBehavior(isA(ServiceTask.class));
  }

  /**
   * Test
   * {@link ServiceTaskParseHandler#createCamelActivityBehavior(BpmnParse, ServiceTask)}.
   * <ul>
   *   <li>Then calls {@link BpmnParser#getActivityBehaviorFactory()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskParseHandler#createCamelActivityBehavior(BpmnParse, ServiceTask)}
   */
  @Test
  public void testCreateCamelActivityBehavior_thenCallsGetActivityBehaviorFactory() {
    // Arrange
    ServiceTaskParseHandler serviceTaskParseHandler = new ServiceTaskParseHandler();
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = mock(DefaultActivityBehaviorFactory.class);
    when(defaultActivityBehaviorFactory.createCamelActivityBehavior(Mockito.<ServiceTask>any()))
        .thenReturn(mock(ActivityBehavior.class));
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(defaultActivityBehaviorFactory);
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    // Act
    serviceTaskParseHandler.createCamelActivityBehavior(bpmnParse, new ServiceTask());

    // Assert
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
    verify(defaultActivityBehaviorFactory).createCamelActivityBehavior(isA(ServiceTask.class));
  }

  /**
   * Test
   * {@link ServiceTaskParseHandler#createShellActivityBehavior(BpmnParse, ServiceTask)}.
   * <ul>
   *   <li>Then {@link ServiceTask} (default constructor) Behavior
   * {@link ShellActivityBehavior}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskParseHandler#createShellActivityBehavior(BpmnParse, ServiceTask)}
   */
  @Test
  public void testCreateShellActivityBehavior_thenServiceTaskBehaviorShellActivityBehavior() {
    // Arrange
    ServiceTaskParseHandler serviceTaskParseHandler = new ServiceTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    ServiceTask serviceTask = new ServiceTask();

    // Act
    serviceTaskParseHandler.createShellActivityBehavior(bpmnParse, serviceTask);

    // Assert
    Object behavior = serviceTask.getBehavior();
    assertTrue(behavior instanceof ShellActivityBehavior);
    assertNull(((ShellActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link ServiceTaskParseHandler#createActivityBehaviorForCustomServiceTaskType(BpmnParse, ServiceTask)}.
   * <p>
   * Method under test:
   * {@link ServiceTaskParseHandler#createActivityBehaviorForCustomServiceTaskType(BpmnParse, ServiceTask)}
   */
  @Test
  public void testCreateActivityBehaviorForCustomServiceTaskType() {
    // Arrange
    ServiceTaskParseHandler serviceTaskParseHandler = new ServiceTaskParseHandler();
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    // Act
    serviceTaskParseHandler.createActivityBehaviorForCustomServiceTaskType(bpmnParse, new ServiceTask());

    // Assert that nothing has changed
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Test
   * {@link ServiceTaskParseHandler#createClassDelegateServiceTask(BpmnParse, ServiceTask)}.
   * <ul>
   *   <li>Then {@link ServiceTask} (default constructor) Behavior
   * {@link ClassDelegate}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskParseHandler#createClassDelegateServiceTask(BpmnParse, ServiceTask)}
   */
  @Test
  public void testCreateClassDelegateServiceTask_thenServiceTaskBehaviorClassDelegate() {
    // Arrange
    ServiceTaskParseHandler serviceTaskParseHandler = new ServiceTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    ServiceTask serviceTask = new ServiceTask();

    // Act
    serviceTaskParseHandler.createClassDelegateServiceTask(bpmnParse, serviceTask);

    // Assert
    Object behavior = serviceTask.getBehavior();
    assertTrue(behavior instanceof ClassDelegate);
    assertNull(((ClassDelegate) behavior).getClassName());
    assertNull(((ClassDelegate) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link ServiceTaskParseHandler#createServiceTaskDelegateExpressionActivityBehavior(BpmnParse, ServiceTask)}.
   * <p>
   * Method under test:
   * {@link ServiceTaskParseHandler#createServiceTaskDelegateExpressionActivityBehavior(BpmnParse, ServiceTask)}
   */
  @Test
  public void testCreateServiceTaskDelegateExpressionActivityBehavior() {
    // Arrange
    ServiceTaskParseHandler serviceTaskParseHandler = new ServiceTaskParseHandler();
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = mock(DefaultActivityBehaviorFactory.class);
    FixedValue expression = new FixedValue(JSONObject.NULL);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);
    ServiceTaskDelegateExpressionActivityBehavior serviceTaskDelegateExpressionActivityBehavior = new ServiceTaskDelegateExpressionActivityBehavior(
        "42", expression, skipExpression, new ArrayList<>());

    when(defaultActivityBehaviorFactory.createServiceTaskDelegateExpressionActivityBehavior(Mockito.<ServiceTask>any()))
        .thenReturn(serviceTaskDelegateExpressionActivityBehavior);
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(defaultActivityBehaviorFactory);
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    ServiceTask serviceTask = new ServiceTask();

    // Act
    serviceTaskParseHandler.createServiceTaskDelegateExpressionActivityBehavior(bpmnParse, serviceTask);

    // Assert
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
    verify(defaultActivityBehaviorFactory).createServiceTaskDelegateExpressionActivityBehavior(isA(ServiceTask.class));
    Object behavior = serviceTask.getBehavior();
    assertTrue(behavior instanceof ServiceTaskDelegateExpressionActivityBehavior);
    assertNull(((ServiceTaskDelegateExpressionActivityBehavior) behavior).getMultiInstanceActivityBehavior());
    assertSame(serviceTaskDelegateExpressionActivityBehavior, behavior);
  }

  /**
   * Test
   * {@link ServiceTaskParseHandler#createServiceTaskExpressionActivityBehavior(BpmnParse, ServiceTask)}.
   * <p>
   * Method under test:
   * {@link ServiceTaskParseHandler#createServiceTaskExpressionActivityBehavior(BpmnParse, ServiceTask)}
   */
  @Test
  public void testCreateServiceTaskExpressionActivityBehavior() {
    // Arrange
    ServiceTaskParseHandler serviceTaskParseHandler = new ServiceTaskParseHandler();
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = mock(DefaultActivityBehaviorFactory.class);
    FixedValue expression = new FixedValue(JSONObject.NULL);
    ServiceTaskExpressionActivityBehavior serviceTaskExpressionActivityBehavior = new ServiceTaskExpressionActivityBehavior(
        "42", expression, new FixedValue(JSONObject.NULL), "Result Variable");

    when(defaultActivityBehaviorFactory.createServiceTaskExpressionActivityBehavior(Mockito.<ServiceTask>any()))
        .thenReturn(serviceTaskExpressionActivityBehavior);
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(defaultActivityBehaviorFactory);
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    ServiceTask serviceTask = new ServiceTask();

    // Act
    serviceTaskParseHandler.createServiceTaskExpressionActivityBehavior(bpmnParse, serviceTask);

    // Assert
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
    verify(defaultActivityBehaviorFactory).createServiceTaskExpressionActivityBehavior(isA(ServiceTask.class));
    Object behavior = serviceTask.getBehavior();
    assertTrue(behavior instanceof ServiceTaskExpressionActivityBehavior);
    assertNull(((ServiceTaskExpressionActivityBehavior) behavior).getMultiInstanceActivityBehavior());
    assertSame(serviceTaskExpressionActivityBehavior, behavior);
  }

  /**
   * Test
   * {@link ServiceTaskParseHandler#createWebServiceActivityBehavior(BpmnParse, ServiceTask)}.
   * <p>
   * Method under test:
   * {@link ServiceTaskParseHandler#createWebServiceActivityBehavior(BpmnParse, ServiceTask)}
   */
  @Test
  public void testCreateWebServiceActivityBehavior() {
    // Arrange
    ServiceTaskParseHandler serviceTaskParseHandler = new ServiceTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    ServiceTask serviceTask = new ServiceTask();

    // Act
    serviceTaskParseHandler.createWebServiceActivityBehavior(bpmnParse, serviceTask);

    // Assert
    Object behavior = serviceTask.getBehavior();
    assertTrue(behavior instanceof WebServiceActivityBehavior);
    assertNull(((WebServiceActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link ServiceTaskParseHandler#createDefaultServiceTaskActivityBehavior(BpmnParse, ServiceTask)}.
   * <ul>
   *   <li>Then calls {@link BpmnParser#getActivityBehaviorFactory()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskParseHandler#createDefaultServiceTaskActivityBehavior(BpmnParse, ServiceTask)}
   */
  @Test
  public void testCreateDefaultServiceTaskActivityBehavior_thenCallsGetActivityBehaviorFactory() {
    // Arrange
    ServiceTaskParseHandler serviceTaskParseHandler = new ServiceTaskParseHandler();
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = mock(DefaultActivityBehaviorFactory.class);
    when(defaultActivityBehaviorFactory.createDefaultServiceTaskBehavior(Mockito.<ServiceTask>any()))
        .thenReturn(mock(ActivityBehavior.class));
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(defaultActivityBehaviorFactory);
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    // Act
    serviceTaskParseHandler.createDefaultServiceTaskActivityBehavior(bpmnParse, new ServiceTask());

    // Assert
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
    verify(defaultActivityBehaviorFactory).createDefaultServiceTaskBehavior(isA(ServiceTask.class));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ServiceTaskParseHandler}
   *   <li>{@link ServiceTaskParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new ServiceTaskParseHandler()).getHandledType();

    // Assert
    Class<ServiceTask> expectedHandledType = ServiceTask.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
