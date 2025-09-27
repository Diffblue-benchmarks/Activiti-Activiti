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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.model.impl.ProcessDefinitionImpl;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.runtime.api.query.impl.PageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DemoApplicationDiffblueTest {
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

    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(content, 1000));

    SecurityUtil securityUtil = mock(SecurityUtil.class);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    DemoApplication demoApplication = new DemoApplication(processRuntime, securityUtil);

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
    ProcessRuntime processRuntime = mock(ProcessRuntime.class);
    when(processRuntime.processDefinitions(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>(), 1000));

    SecurityUtil securityUtil = mock(SecurityUtil.class);
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    DemoApplication demoApplication = new DemoApplication(processRuntime, securityUtil);

    // Act
    demoApplication.run("Args");

    // Assert
    verify(processRuntime).processDefinitions(isA(Pageable.class));
    verify(securityUtil).logInAs("system");
  }
}
