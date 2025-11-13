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
package org.activiti.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.api.process.model.IntegrationContext;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.events.ProcessRuntimeEvent;
import org.activiti.api.process.model.events.ProcessRuntimeEvent.ProcessEvents;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.api.process.runtime.events.ProcessCompletedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.model.impl.IntegrationContextImpl;
import org.activiti.api.runtime.model.impl.ProcessDefinitionImpl;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.runtime.api.event.impl.ProcessCompletedImpl;
import org.activiti.runtime.api.query.impl.PageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

@ExtendWith(MockitoExtension.class)
class DemoApplicationDiffblueTest {
  @InjectMocks private DemoApplication demoApplication;

  @Mock private Logger logger;

  @Mock private ProcessRuntime processRuntime;

  @Mock private SecurityUtil securityUtil;

  /**
   * Test {@link DemoApplication#run(String[])}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ProcessDefinitionImpl} (default
   *       constructor).
   *   <li>Then calls {@link ProcessRuntime#processDefinitions(Pageable)}.
   * </ul>
   *
   * <p>Method under test: {@link DemoApplication#run(String[])}
   */
  @Test
  @DisplayName(
      "Test run(String[]); given ArrayList() add ProcessDefinitionImpl (default constructor); then calls processDefinitions(Pageable)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DemoApplication.run(String[])"})
  void testRun_givenArrayListAddProcessDefinitionImpl_thenCallsProcessDefinitions() {
    // Arrange
    ArrayList<ProcessDefinition> content = new ArrayList<>();
    content.add(new ProcessDefinitionImpl());
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(content, 1000));
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());
    doNothing().when(logger).info(Mockito.<String>any());

    // Act
    demoApplication.run("Args");

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(securityUtil).logInAs("system");
    verify(logger, atLeast(1)).info(Mockito.<String>any());
  }

  /**
   * Test {@link DemoApplication#run(String[])}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessRuntime#processDefinitions(Pageable)}.
   * </ul>
   *
   * <p>Method under test: {@link DemoApplication#run(String[])}
   */
  @Test
  @DisplayName("Test run(String[]); then calls processDefinitions(Pageable)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DemoApplication.run(String[])"})
  void testRun_thenCallsProcessDefinitions() {
    // Arrange
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());
    doNothing().when(logger).info(Mockito.<String>any());

    // Act
    demoApplication.run("Args");

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(securityUtil).logInAs("system");
    verify(logger).info("> Available Process definitions: 1000");
  }

  /**
   * Test {@link DemoApplication#processText()}.
   *
   * <p>Method under test: {@link DemoApplication#processText()}
   */
  @Test
  @DisplayName("Test processText()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DemoApplication.processText()"})
  void testProcessText() {
    // Arrange
    when(processRuntime.start(Mockito.<StartProcessPayload>any()))
        .thenReturn(new ProcessInstanceImpl());
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());
    doNothing().when(logger).info(Mockito.<String>any());

    // Act
    demoApplication.processText();

    // Assert
    verify(processRuntime).start(isA(StartProcessPayload.class));
    verify(securityUtil).logInAs("system");
    verify(logger, atLeast(1)).info(Mockito.<String>any());
  }

  /**
   * Test {@link DemoApplication#processTextConnector()}.
   *
   * <p>Method under test: {@link DemoApplication#processTextConnector()}
   */
  @Test
  @DisplayName("Test processTextConnector()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Connector DemoApplication.processTextConnector()"})
  void testProcessTextConnector() {
    // Arrange and Act
    Connector actualProcessTextConnectorResult = new DemoApplication().processTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.addInBoundVariable("content", "Value");
    IntegrationContext actualApplyResult =
        actualProcessTextConnectorResult.apply(integrationContextImpl);

    // Assert
    assertNull(integrationContextImpl.getProcessDefinitionVersion());
    assertNull(integrationContextImpl.getAppVersion());
    assertNull(integrationContextImpl.getBusinessKey());
    assertNull(integrationContextImpl.getClientId());
    assertNull(integrationContextImpl.getClientName());
    assertNull(integrationContextImpl.getClientType());
    assertNull(integrationContextImpl.getConnectorType());
    assertNull(integrationContextImpl.getExecutionId());
    assertNull(integrationContextImpl.getParentProcessInstanceId());
    assertNull(integrationContextImpl.getProcessDefinitionId());
    assertNull(integrationContextImpl.getProcessDefinitionKey());
    assertNull(integrationContextImpl.getProcessInstanceId());
    assertNull(integrationContextImpl.getRootProcessInstanceId());
    assertSame(integrationContextImpl, actualApplyResult);
  }

  /**
   * Test {@link DemoApplication#processTextConnector()}.
   *
   * <p>Method under test: {@link DemoApplication#processTextConnector()}
   */
  @Test
  @DisplayName("Test processTextConnector()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Connector DemoApplication.processTextConnector()"})
  void testProcessTextConnector2() {
    // Arrange and Act
    Connector actualProcessTextConnectorResult = new DemoApplication().processTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.addInBoundVariable("content", "activiti");
    IntegrationContext actualApplyResult =
        actualProcessTextConnectorResult.apply(integrationContextImpl);

    // Assert
    assertNull(integrationContextImpl.getProcessDefinitionVersion());
    assertNull(integrationContextImpl.getAppVersion());
    assertNull(integrationContextImpl.getBusinessKey());
    assertNull(integrationContextImpl.getClientId());
    assertNull(integrationContextImpl.getClientName());
    assertNull(integrationContextImpl.getClientType());
    assertNull(integrationContextImpl.getConnectorType());
    assertNull(integrationContextImpl.getExecutionId());
    assertNull(integrationContextImpl.getParentProcessInstanceId());
    assertNull(integrationContextImpl.getProcessDefinitionId());
    assertNull(integrationContextImpl.getProcessDefinitionKey());
    assertNull(integrationContextImpl.getProcessInstanceId());
    assertNull(integrationContextImpl.getRootProcessInstanceId());
    assertSame(integrationContextImpl, actualApplyResult);
  }

  /**
   * Test {@link DemoApplication#tagTextConnector()}.
   *
   * <p>Method under test: {@link DemoApplication#tagTextConnector()}
   */
  @Test
  @DisplayName("Test tagTextConnector()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Connector DemoApplication.tagTextConnector()"})
  void testTagTextConnector() {
    // Arrange and Act
    Connector actualTagTextConnectorResult = new DemoApplication().tagTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    IntegrationContext actualApplyResult =
        actualTagTextConnectorResult.apply(integrationContextImpl);

    // Assert
    assertSame(integrationContextImpl, actualApplyResult);
  }

  /**
   * Test {@link DemoApplication#discardTextConnector()}.
   *
   * <p>Method under test: {@link DemoApplication#discardTextConnector()}
   */
  @Test
  @DisplayName("Test discardTextConnector()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Connector DemoApplication.discardTextConnector()"})
  void testDiscardTextConnector() {
    // Arrange and Act
    Connector actualDiscardTextConnectorResult = new DemoApplication().discardTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    IntegrationContext actualApplyResult =
        actualDiscardTextConnectorResult.apply(integrationContextImpl);

    // Assert
    assertSame(integrationContextImpl, actualApplyResult);
  }

  /**
   * Test {@link DemoApplication#processCompletedListener()}.
   *
   * <p>Method under test: {@link DemoApplication#processCompletedListener()}
   */
  @Test
  @DisplayName("Test processCompletedListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessRuntimeEventListener DemoApplication.processCompletedListener()"})
  void testProcessCompletedListener() {
    // Arrange
    doNothing().when(logger).info(Mockito.<String>any());

    // Act
    ProcessRuntimeEventListener<ProcessCompletedEvent> actualProcessCompletedListenerResult =
        demoApplication.processCompletedListener();
    ProcessInstanceImpl entity = new ProcessInstanceImpl();
    ProcessCompletedImpl processCompletedImpl = new ProcessCompletedImpl(entity);
    actualProcessCompletedListenerResult.onEvent(processCompletedImpl);

    // Assert that nothing has changed
    verify(logger)
        .info(">>> Process Completed: 'null' We can send a notification to the initiator: null");
    ProcessInstance entity2 = processCompletedImpl.getEntity();
    assertTrue(entity2 instanceof ProcessInstanceImpl);
    assertEquals(ProcessEvents.PROCESS_COMPLETED, processCompletedImpl.getEventType());
    assertSame(entity, entity2);
  }
}
