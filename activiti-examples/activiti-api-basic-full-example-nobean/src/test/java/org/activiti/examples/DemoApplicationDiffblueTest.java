package org.activiti.examples;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.model.impl.ProcessDefinitionImpl;
import org.activiti.api.runtime.model.impl.VariableInstanceImpl;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.Task.TaskStatus;
import org.activiti.api.task.model.impl.TaskImpl;
import org.activiti.api.task.model.payloads.ClaimTaskPayload;
import org.activiti.api.task.model.payloads.GetTaskVariablesPayload;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.runtime.api.query.impl.PageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DemoApplicationDiffblueTest {
  /**
   * Test {@link DemoApplication#run(String[])}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ProcessDefinitionImpl} (default constructor).</li>
   *   <li>Then calls {@link ProcessRuntime#processDefinitions(Pageable)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#run(String[])}
   */
  @Test
  @DisplayName("Test run(String[]); given ArrayList() add ProcessDefinitionImpl (default constructor); then calls processDefinitions(Pageable)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DemoApplication.run(String[])"})
  void testRun_givenArrayListAddProcessDefinitionImpl_thenCallsProcessDefinitions() {
    // Arrange
    ArrayList<ProcessDefinition> content = new ArrayList<>();
    content.add(new ProcessDefinitionImpl());
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    when(processRuntime.processDefinitions(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(content, 1000));
    SecurityUtil securityUtil = mock(SecurityUtil.class);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    (new DemoApplication(processRuntime, null, securityUtil, JsonMapper.builder().findAndAddModules().build()))
        .run("Args");

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(securityUtil).logInAs(eq("system"));
  }

  /**
   * Test {@link DemoApplication#run(String[])}.
   * <ul>
   *   <li>Then calls {@link ProcessRuntime#processDefinitions(Pageable)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#run(String[])}
   */
  @Test
  @DisplayName("Test run(String[]); then calls processDefinitions(Pageable)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DemoApplication.run(String[])"})
  void testRun_thenCallsProcessDefinitions() {
    // Arrange
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));
    SecurityUtil securityUtil = mock(SecurityUtil.class);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    (new DemoApplication(processRuntime, null, securityUtil, JsonMapper.builder().findAndAddModules().build()))
        .run("Args");

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(securityUtil).logInAs(eq("system"));
  }

  /**
   * Test {@link DemoApplication#checkAndWorkOnTasksWhenAvailable()}.
   * <p>
   * Method under test: {@link DemoApplication#checkAndWorkOnTasksWhenAvailable()}
   */
  @Test
  @DisplayName("Test checkAndWorkOnTasksWhenAvailable()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DemoApplication.checkAndWorkOnTasksWhenAvailable()"})
  void testCheckAndWorkOnTasksWhenAvailable() {
    // Arrange
    ArrayList<Task> content = new ArrayList<>();
    content.add(new TaskImpl("42", "bob", TaskStatus.CREATED));
    TaskRuntime taskRuntime = mock(TaskRuntime.class);
    when(taskRuntime.tasks(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(content, 0));
    SecurityUtil securityUtil = mock(SecurityUtil.class);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    (new DemoApplication(null, taskRuntime, securityUtil, JsonMapper.builder().findAndAddModules().build()))
        .checkAndWorkOnTasksWhenAvailable();

    // Assert
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(securityUtil).logInAs(eq("bob"));
  }

  /**
   * Test {@link DemoApplication#checkAndWorkOnTasksWhenAvailable()}.
   * <ul>
   *   <li>Then calls {@link TaskRuntime#claim(ClaimTaskPayload)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#checkAndWorkOnTasksWhenAvailable()}
   */
  @Test
  @DisplayName("Test checkAndWorkOnTasksWhenAvailable(); then calls claim(ClaimTaskPayload)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DemoApplication.checkAndWorkOnTasksWhenAvailable()"})
  void testCheckAndWorkOnTasksWhenAvailable_thenCallsClaim() {
    // Arrange
    ArrayList<Task> content = new ArrayList<>();
    content.add(new TaskImpl("42", "bob", TaskStatus.CREATED));
    PageImpl<Task> pageImpl = new PageImpl<>(content, 1000);

    ArrayList<VariableInstance> variableInstanceList = new ArrayList<>();
    variableInstanceList.add(new VariableInstanceImpl<>("bob", "bob", "Value", "42", "42"));
    TaskRuntime taskRuntime = mock(TaskRuntime.class);
    when(taskRuntime.variables(Mockito.<GetTaskVariablesPayload>any())).thenReturn(variableInstanceList);
    when(taskRuntime.claim(Mockito.<ClaimTaskPayload>any())).thenReturn(new TaskImpl("42", "Name", TaskStatus.CREATED));
    when(taskRuntime.tasks(Mockito.<Pageable>any())).thenReturn(pageImpl);
    SecurityUtil securityUtil = mock(SecurityUtil.class);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    (new DemoApplication(null, taskRuntime, securityUtil, JsonMapper.builder().findAndAddModules().build()))
        .checkAndWorkOnTasksWhenAvailable();

    // Assert
    verify(taskRuntime).claim(isA(ClaimTaskPayload.class));
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(taskRuntime).variables(isA(GetTaskVariablesPayload.class));
    verify(securityUtil).logInAs(eq("bob"));
  }

  /**
   * Test {@link DemoApplication#checkAndWorkOnTasksWhenAvailable()}.
   * <ul>
   *   <li>Then calls {@link TaskRuntime#tasks(Pageable)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#checkAndWorkOnTasksWhenAvailable()}
   */
  @Test
  @DisplayName("Test checkAndWorkOnTasksWhenAvailable(); then calls tasks(Pageable)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DemoApplication.checkAndWorkOnTasksWhenAvailable()"})
  void testCheckAndWorkOnTasksWhenAvailable_thenCallsTasks() {
    // Arrange
    TaskRuntime taskRuntime = mock(TaskRuntime.class);
    when(taskRuntime.tasks(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>(), 1000));
    SecurityUtil securityUtil = mock(SecurityUtil.class);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    (new DemoApplication(null, taskRuntime, securityUtil, JsonMapper.builder().findAndAddModules().build()))
        .checkAndWorkOnTasksWhenAvailable();

    // Assert
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(securityUtil).logInAs(eq("bob"));
  }
}
