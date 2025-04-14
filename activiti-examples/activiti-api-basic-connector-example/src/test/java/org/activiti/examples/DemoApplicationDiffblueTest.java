package org.activiti.examples;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.payloads.GetVariablesPayload;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.api.runtime.model.impl.IntegrationContextImpl;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.api.runtime.model.impl.VariableInstanceImpl;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.Task.TaskStatus;
import org.activiti.api.task.model.impl.TaskImpl;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.api.task.model.payloads.GetTaskVariablesPayload;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.runtime.api.query.impl.PageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DemoApplicationDiffblueTest {
  /**
   * Test {@link DemoApplication#commandLineRunner()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link VariableInstanceImpl#VariableInstanceImpl()}.</li>
   *   <li>Then calls {@link TaskRuntime#complete(CompleteTaskPayload)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#commandLineRunner()}
   */
  @Test
  @DisplayName("Test commandLineRunner(); given ArrayList() add VariableInstanceImpl(); then calls complete(CompleteTaskPayload)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"org.springframework.boot.CommandLineRunner DemoApplication.commandLineRunner()"})
  void testCommandLineRunner_givenArrayListAddVariableInstanceImpl_thenCallsComplete() throws Exception {
    // Arrange
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    when(processRuntime.variables(Mockito.<GetVariablesPayload>any())).thenReturn(new ArrayList<>());
    when(processRuntime.start(Mockito.<StartProcessPayload>any())).thenReturn(new ProcessInstanceImpl());
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));

    ArrayList<Task> content = new ArrayList<>();
    content.add(new TaskImpl("42", "reviewer", TaskStatus.CREATED));
    PageImpl<Task> pageImpl = new PageImpl<>(content, 1000);

    ArrayList<VariableInstance> variableInstanceList = new ArrayList<>();
    variableInstanceList.add(new VariableInstanceImpl<>());
    TaskRuntime taskRuntime = mock(TaskRuntime.class);
    when(taskRuntime.variables(Mockito.<GetTaskVariablesPayload>any())).thenReturn(variableInstanceList);
    when(taskRuntime.complete(Mockito.<CompleteTaskPayload>any()))
        .thenReturn(new TaskImpl("42", "Name", TaskStatus.CREATED));
    when(taskRuntime.tasks(Mockito.<Pageable>any())).thenReturn(pageImpl);
    SecurityUtil securityUtil = mock(SecurityUtil.class);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    (new DemoApplication(processRuntime, taskRuntime, securityUtil)).commandLineRunner().run("Args");

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(processRuntime).start(isA(StartProcessPayload.class));
    verify(processRuntime).variables(isA(GetVariablesPayload.class));
    verify(taskRuntime).complete(isA(CompleteTaskPayload.class));
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(taskRuntime).variables(isA(GetTaskVariablesPayload.class));
    verify(securityUtil).logInAs(eq("reviewer"));
  }

  /**
   * Test {@link DemoApplication#commandLineRunner()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link VariableInstanceImpl#VariableInstanceImpl()}.</li>
   *   <li>Then calls {@link TaskRuntime#complete(CompleteTaskPayload)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#commandLineRunner()}
   */
  @Test
  @DisplayName("Test commandLineRunner(); given ArrayList() add VariableInstanceImpl(); then calls complete(CompleteTaskPayload)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"org.springframework.boot.CommandLineRunner DemoApplication.commandLineRunner()"})
  void testCommandLineRunner_givenArrayListAddVariableInstanceImpl_thenCallsComplete2() throws Exception {
    // Arrange
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    when(processRuntime.variables(Mockito.<GetVariablesPayload>any())).thenReturn(new ArrayList<>());
    when(processRuntime.start(Mockito.<StartProcessPayload>any())).thenReturn(new ProcessInstanceImpl());
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));

    ArrayList<Task> content = new ArrayList<>();
    content.add(new TaskImpl("42", "reviewer", TaskStatus.CREATED));
    PageImpl<Task> pageImpl = new PageImpl<>(content, 1000);

    ArrayList<VariableInstance> variableInstanceList = new ArrayList<>();
    variableInstanceList.add(new VariableInstanceImpl<>());
    variableInstanceList.add(new VariableInstanceImpl<>());
    TaskRuntime taskRuntime = mock(TaskRuntime.class);
    when(taskRuntime.variables(Mockito.<GetTaskVariablesPayload>any())).thenReturn(variableInstanceList);
    when(taskRuntime.complete(Mockito.<CompleteTaskPayload>any()))
        .thenReturn(new TaskImpl("42", "Name", TaskStatus.CREATED));
    when(taskRuntime.tasks(Mockito.<Pageable>any())).thenReturn(pageImpl);
    SecurityUtil securityUtil = mock(SecurityUtil.class);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    (new DemoApplication(processRuntime, taskRuntime, securityUtil)).commandLineRunner().run("Args");

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(processRuntime).start(isA(StartProcessPayload.class));
    verify(processRuntime).variables(isA(GetVariablesPayload.class));
    verify(taskRuntime).complete(isA(CompleteTaskPayload.class));
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(taskRuntime).variables(isA(GetTaskVariablesPayload.class));
    verify(securityUtil).logInAs(eq("reviewer"));
  }

  /**
   * Test {@link DemoApplication#commandLineRunner()}.
   * <ul>
   *   <li>Then calls {@link TaskRuntime#complete(CompleteTaskPayload)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#commandLineRunner()}
   */
  @Test
  @DisplayName("Test commandLineRunner(); then calls complete(CompleteTaskPayload)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"org.springframework.boot.CommandLineRunner DemoApplication.commandLineRunner()"})
  void testCommandLineRunner_thenCallsComplete() throws Exception {
    // Arrange
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    when(processRuntime.variables(Mockito.<GetVariablesPayload>any())).thenReturn(new ArrayList<>());
    when(processRuntime.start(Mockito.<StartProcessPayload>any())).thenReturn(new ProcessInstanceImpl());
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));

    ArrayList<Task> content = new ArrayList<>();
    content.add(new TaskImpl("42", "reviewer", TaskStatus.CREATED));
    PageImpl<Task> pageImpl = new PageImpl<>(content, 1000);

    TaskRuntime taskRuntime = mock(TaskRuntime.class);
    when(taskRuntime.variables(Mockito.<GetTaskVariablesPayload>any())).thenReturn(new ArrayList<>());
    when(taskRuntime.complete(Mockito.<CompleteTaskPayload>any()))
        .thenReturn(new TaskImpl("42", "Name", TaskStatus.CREATED));
    when(taskRuntime.tasks(Mockito.<Pageable>any())).thenReturn(pageImpl);
    SecurityUtil securityUtil = mock(SecurityUtil.class);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    (new DemoApplication(processRuntime, taskRuntime, securityUtil)).commandLineRunner().run("Args");

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(processRuntime).start(isA(StartProcessPayload.class));
    verify(processRuntime).variables(isA(GetVariablesPayload.class));
    verify(taskRuntime).complete(isA(CompleteTaskPayload.class));
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(taskRuntime).variables(isA(GetTaskVariablesPayload.class));
    verify(securityUtil).logInAs(eq("reviewer"));
  }

  /**
   * Test {@link DemoApplication#commandLineRunner()}.
   * <ul>
   *   <li>Then calls {@link TaskRuntime#complete(CompleteTaskPayload)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#commandLineRunner()}
   */
  @Test
  @DisplayName("Test commandLineRunner(); then calls complete(CompleteTaskPayload)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"org.springframework.boot.CommandLineRunner DemoApplication.commandLineRunner()"})
  void testCommandLineRunner_thenCallsComplete2() throws Exception {
    // Arrange
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    when(processRuntime.variables(Mockito.<GetVariablesPayload>any())).thenReturn(new ArrayList<>());
    when(processRuntime.start(Mockito.<StartProcessPayload>any())).thenReturn(new ProcessInstanceImpl());
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));

    ArrayList<Task> content = new ArrayList<>();
    content.add(new TaskImpl("42", "reviewer", TaskStatus.CREATED));
    content.add(new TaskImpl("42", "reviewer", TaskStatus.CREATED));
    PageImpl<Task> pageImpl = new PageImpl<>(content, 1000);

    TaskRuntime taskRuntime = mock(TaskRuntime.class);
    when(taskRuntime.variables(Mockito.<GetTaskVariablesPayload>any())).thenReturn(new ArrayList<>());
    when(taskRuntime.complete(Mockito.<CompleteTaskPayload>any()))
        .thenReturn(new TaskImpl("42", "Name", TaskStatus.CREATED));
    when(taskRuntime.tasks(Mockito.<Pageable>any())).thenReturn(pageImpl);
    SecurityUtil securityUtil = mock(SecurityUtil.class);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    (new DemoApplication(processRuntime, taskRuntime, securityUtil)).commandLineRunner().run("Args");

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(processRuntime).start(isA(StartProcessPayload.class));
    verify(processRuntime).variables(isA(GetVariablesPayload.class));
    verify(taskRuntime, atLeast(1)).complete(Mockito.<CompleteTaskPayload>any());
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(taskRuntime, atLeast(1)).variables(Mockito.<GetTaskVariablesPayload>any());
    verify(securityUtil).logInAs(eq("reviewer"));
  }

  /**
   * Test {@link DemoApplication#commandLineRunner()}.
   * <ul>
   *   <li>Then calls {@link ProcessRuntime#processDefinitions(Pageable)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#commandLineRunner()}
   */
  @Test
  @DisplayName("Test commandLineRunner(); then calls processDefinitions(Pageable)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"org.springframework.boot.CommandLineRunner DemoApplication.commandLineRunner()"})
  void testCommandLineRunner_thenCallsProcessDefinitions() throws Exception {
    // Arrange
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    when(processRuntime.variables(Mockito.<GetVariablesPayload>any())).thenReturn(new ArrayList<>());
    when(processRuntime.start(Mockito.<StartProcessPayload>any())).thenReturn(new ProcessInstanceImpl());
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));
    TaskRuntime taskRuntime = mock(TaskRuntime.class);
    when(taskRuntime.tasks(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>(), 1000));
    SecurityUtil securityUtil = mock(SecurityUtil.class);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    // Act
    (new DemoApplication(processRuntime, taskRuntime, securityUtil)).commandLineRunner().run("Args");

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(processRuntime).start(isA(StartProcessPayload.class));
    verify(processRuntime).variables(isA(GetVariablesPayload.class));
    verify(taskRuntime).tasks(isA(Pageable.class));
    verify(securityUtil).logInAs(eq("reviewer"));
  }

  /**
   * Test {@link DemoApplication#getMovieDesc()}.
   * <ul>
   *   <li>Then return apply {@link IntegrationContextImpl} (default constructor) is {@link IntegrationContextImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplication#getMovieDesc()}
   */
  @Test
  @DisplayName("Test getMovieDesc(); then return apply IntegrationContextImpl (default constructor) is IntegrationContextImpl (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Connector DemoApplication.getMovieDesc()"})
  void testGetMovieDesc_thenReturnApplyIntegrationContextImplIsIntegrationContextImpl() {
    // Arrange and Act
    Connector actualMovieDesc = (new DemoApplication(null, null, new SecurityUtil())).getMovieDesc();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();

    // Assert
    assertSame(integrationContextImpl, actualMovieDesc.apply(integrationContextImpl));
  }

  /**
   * Test {@link DemoApplication#getMovieDescUUIDs()}.
   * <p>
   * Method under test: {@link DemoApplication#getMovieDescUUIDs()}
   */
  @Test
  @DisplayName("Test getMovieDescUUIDs()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Connector DemoApplication.getMovieDescUUIDs()"})
  void testGetMovieDescUUIDs() {
    // Arrange and Act
    Connector actualMovieDescUUIDs = (new DemoApplication(null, null, new SecurityUtil())).getMovieDescUUIDs();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();

    // Assert
    assertSame(integrationContextImpl, actualMovieDescUUIDs.apply(integrationContextImpl));
  }
}
