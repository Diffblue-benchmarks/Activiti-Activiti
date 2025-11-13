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

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.model.impl.ProcessDefinitionImpl;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.api.runtime.model.impl.VariableInstanceImpl;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.impl.TaskImpl;
import org.activiti.api.task.model.payloads.ClaimTaskPayload;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.api.task.model.payloads.GetTaskVariablesPayload;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.runtime.api.query.impl.PageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DemoApplicationDiffblueTest {
  @InjectMocks private DemoApplication demoApplication;

  @Mock private ProcessRuntime processRuntime;

  @Mock private SecurityUtil securityUtil;

  @Mock private TaskRuntime taskRuntime;

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

    // Act
    demoApplication.run("Args");

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(securityUtil).logInAs("system");
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

    // Act
    demoApplication.run("Args");

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(securityUtil).logInAs("system");
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

    // Act
    demoApplication.processText();

    // Assert
    verify(processRuntime).start(isA(StartProcessPayload.class));
    verify(securityUtil).logInAs("system");
  }

  /**
   * Test {@link DemoApplication#checkAndWorkOnTasksWhenAvailable()}.
   *
   * <p>Method under test: {@link DemoApplication#checkAndWorkOnTasksWhenAvailable()}
   */
  @Test
  @DisplayName("Test checkAndWorkOnTasksWhenAvailable()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DemoApplication.checkAndWorkOnTasksWhenAvailable()"})
  void testCheckAndWorkOnTasksWhenAvailable() {
    // Arrange
    ArrayList<Task> content = new ArrayList<>();
    content.add(new TaskImpl());
    when(taskRuntime.tasks(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(content, 0));
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    demoApplication.checkAndWorkOnTasksWhenAvailable();

    // Assert
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(securityUtil).logInAs("bob");
  }

  /**
   * Test {@link DemoApplication#checkAndWorkOnTasksWhenAvailable()}.
   *
   * <p>Method under test: {@link DemoApplication#checkAndWorkOnTasksWhenAvailable()}
   */
  @Test
  @DisplayName("Test checkAndWorkOnTasksWhenAvailable()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DemoApplication.checkAndWorkOnTasksWhenAvailable()"})
  void testCheckAndWorkOnTasksWhenAvailable2() {
    // Arrange
    ArrayList<Task> content = new ArrayList<>();
    content.add(new TaskImpl());
    PageImpl<Task> pageImpl = new PageImpl<>(content, 1000);

    ArrayList<VariableInstance> variableInstanceList = new ArrayList<>();
    VariableInstanceImpl<Object> variableInstanceImpl =
        new VariableInstanceImpl<>("bob", "bob", "Value", "42", "42");
    variableInstanceList.add(variableInstanceImpl);
    when(taskRuntime.variables(Mockito.<GetTaskVariablesPayload>any()))
        .thenReturn(variableInstanceList);
    when(taskRuntime.claim(Mockito.<ClaimTaskPayload>any())).thenReturn(new TaskImpl());
    when(taskRuntime.tasks(Mockito.<Pageable>any())).thenReturn(pageImpl);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    demoApplication.checkAndWorkOnTasksWhenAvailable();

    // Assert
    verify(taskRuntime).claim(isA(ClaimTaskPayload.class));
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(taskRuntime).variables(isA(GetTaskVariablesPayload.class));
    verify(securityUtil).logInAs("bob");
  }

  /**
   * Test {@link DemoApplication#checkAndWorkOnTasksWhenAvailable()}.
   *
   * <p>Method under test: {@link DemoApplication#checkAndWorkOnTasksWhenAvailable()}
   */
  @Test
  @DisplayName("Test checkAndWorkOnTasksWhenAvailable()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DemoApplication.checkAndWorkOnTasksWhenAvailable()"})
  void testCheckAndWorkOnTasksWhenAvailable3() {
    // Arrange
    ArrayList<Task> content = new ArrayList<>();
    content.add(new TaskImpl());
    PageImpl<Task> pageImpl = new PageImpl<>(content, 1000);

    ArrayList<VariableInstance> variableInstanceList = new ArrayList<>();
    VariableInstanceImpl<Object> variableInstanceImpl =
        new VariableInstanceImpl<>(
            "content",
            "bob",
            new Content("Not all who wander are lost", true, new ArrayList<>()),
            "42",
            "42");
    variableInstanceList.add(variableInstanceImpl);
    when(taskRuntime.complete(Mockito.<CompleteTaskPayload>any())).thenReturn(new TaskImpl());
    when(taskRuntime.variables(Mockito.<GetTaskVariablesPayload>any()))
        .thenReturn(variableInstanceList);
    when(taskRuntime.claim(Mockito.<ClaimTaskPayload>any())).thenReturn(new TaskImpl());
    when(taskRuntime.tasks(Mockito.<Pageable>any())).thenReturn(pageImpl);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    demoApplication.checkAndWorkOnTasksWhenAvailable();

    // Assert
    verify(taskRuntime).claim(isA(ClaimTaskPayload.class));
    verify(taskRuntime).complete(isA(CompleteTaskPayload.class));
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(taskRuntime).variables(isA(GetTaskVariablesPayload.class));
    verify(securityUtil).logInAs("bob");
  }

  /**
   * Test {@link DemoApplication#checkAndWorkOnTasksWhenAvailable()}.
   *
   * <p>Method under test: {@link DemoApplication#checkAndWorkOnTasksWhenAvailable()}
   */
  @Test
  @DisplayName("Test checkAndWorkOnTasksWhenAvailable()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DemoApplication.checkAndWorkOnTasksWhenAvailable()"})
  void testCheckAndWorkOnTasksWhenAvailable4() {
    // Arrange
    ArrayList<Task> content = new ArrayList<>();
    content.add(new TaskImpl());
    PageImpl<Task> pageImpl = new PageImpl<>(content, 1000);

    ArrayList<VariableInstance> variableInstanceList = new ArrayList<>();
    VariableInstanceImpl<Object> variableInstanceImpl =
        new VariableInstanceImpl<>(
            "content", "bob", new Content("activiti", true, new ArrayList<>()), "42", "42");
    variableInstanceList.add(variableInstanceImpl);
    when(taskRuntime.complete(Mockito.<CompleteTaskPayload>any())).thenReturn(new TaskImpl());
    when(taskRuntime.variables(Mockito.<GetTaskVariablesPayload>any()))
        .thenReturn(variableInstanceList);
    when(taskRuntime.claim(Mockito.<ClaimTaskPayload>any())).thenReturn(new TaskImpl());
    when(taskRuntime.tasks(Mockito.<Pageable>any())).thenReturn(pageImpl);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    demoApplication.checkAndWorkOnTasksWhenAvailable();

    // Assert
    verify(taskRuntime).claim(isA(ClaimTaskPayload.class));
    verify(taskRuntime).complete(isA(CompleteTaskPayload.class));
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(taskRuntime).variables(isA(GetTaskVariablesPayload.class));
    verify(securityUtil).logInAs("bob");
  }

  /**
   * Test {@link DemoApplication#checkAndWorkOnTasksWhenAvailable()}.
   *
   * <ul>
   *   <li>Then calls {@link TaskRuntime#tasks(Pageable)}.
   * </ul>
   *
   * <p>Method under test: {@link DemoApplication#checkAndWorkOnTasksWhenAvailable()}
   */
  @Test
  @DisplayName("Test checkAndWorkOnTasksWhenAvailable(); then calls tasks(Pageable)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DemoApplication.checkAndWorkOnTasksWhenAvailable()"})
  void testCheckAndWorkOnTasksWhenAvailable_thenCallsTasks() {
    // Arrange
    when(taskRuntime.tasks(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    demoApplication.checkAndWorkOnTasksWhenAvailable();

    // Assert
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(securityUtil).logInAs("bob");
  }
}
