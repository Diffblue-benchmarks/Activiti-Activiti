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
package org.activiti.spring.boot;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.CandidateStartersDeploymentConfigurer.CandidateStartersDeploymentHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CandidateStartersDeploymentConfigurer.class})
@ExtendWith(SpringExtension.class)
class CandidateStartersDeploymentConfigurerDiffblueTest {
  @Autowired
  private CandidateStartersDeploymentConfigurer candidateStartersDeploymentConfigurer;

  /**
   * Test CandidateStartersDeploymentHelper {@link CandidateStartersDeploymentHelper#addAuthorizationsForNewProcessDefinition(Process, ProcessDefinitionEntity)}.
   * <p>
   * Method under test: {@link CandidateStartersDeploymentHelper#addAuthorizationsForNewProcessDefinition(Process, ProcessDefinitionEntity)}
   */
  @Test
  @DisplayName("Test CandidateStartersDeploymentHelper addAuthorizationsForNewProcessDefinition(Process, ProcessDefinitionEntity)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void CandidateStartersDeploymentHelper.addAuthorizationsForNewProcessDefinition(Process, ProcessDefinitionEntity)"})
  void testCandidateStartersDeploymentHelperAddAuthorizationsForNewProcessDefinition() {
    // Arrange
    CandidateStartersDeploymentHelper candidateStartersDeploymentHelper = (new CandidateStartersDeploymentConfigurer()).new CandidateStartersDeploymentHelper();
    Process process = mock(Process.class);
    when(process.isCandidateStarterUsersDefined()).thenReturn(true);
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());

    // Act
    candidateStartersDeploymentHelper.addAuthorizationsForNewProcessDefinition(process,
        new ProcessDefinitionEntityImpl());

    // Assert
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process).isCandidateStarterUsersDefined();
  }

  /**
   * Test CandidateStartersDeploymentHelper {@link CandidateStartersDeploymentHelper#addAuthorizationsForNewProcessDefinition(Process, ProcessDefinitionEntity)}.
   * <p>
   * Method under test: {@link CandidateStartersDeploymentHelper#addAuthorizationsForNewProcessDefinition(Process, ProcessDefinitionEntity)}
   */
  @Test
  @DisplayName("Test CandidateStartersDeploymentHelper addAuthorizationsForNewProcessDefinition(Process, ProcessDefinitionEntity)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void CandidateStartersDeploymentHelper.addAuthorizationsForNewProcessDefinition(Process, ProcessDefinitionEntity)"})
  void testCandidateStartersDeploymentHelperAddAuthorizationsForNewProcessDefinition2() {
    // Arrange
    CandidateStartersDeploymentHelper candidateStartersDeploymentHelper = (new CandidateStartersDeploymentConfigurer()).new CandidateStartersDeploymentHelper();
    Process process = mock(Process.class);
    when(process.isCandidateStarterGroupsDefined()).thenReturn(true);
    when(process.isCandidateStarterUsersDefined()).thenReturn(false);
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());

    // Act
    candidateStartersDeploymentHelper.addAuthorizationsForNewProcessDefinition(process,
        new ProcessDefinitionEntityImpl());

    // Assert
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process).isCandidateStarterGroupsDefined();
    verify(process).isCandidateStarterUsersDefined();
  }

  /**
   * Test CandidateStartersDeploymentHelper {@link CandidateStartersDeploymentHelper#CandidateStartersDeploymentHelper(CandidateStartersDeploymentConfigurer)}.
   * <p>
   * Method under test: {@link CandidateStartersDeploymentHelper#CandidateStartersDeploymentHelper(CandidateStartersDeploymentConfigurer)}
   */
  @Test
  @DisplayName("Test CandidateStartersDeploymentHelper new CandidateStartersDeploymentHelper(CandidateStartersDeploymentConfigurer)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CandidateStartersDeploymentHelper.<init>(CandidateStartersDeploymentConfigurer)"})
  void testCandidateStartersDeploymentHelperNewCandidateStartersDeploymentHelper() {
    // Arrange and Act
    CandidateStartersDeploymentHelper actualCandidateStartersDeploymentHelper = (new CandidateStartersDeploymentConfigurer()).new CandidateStartersDeploymentHelper();

    // Assert
    assertNull(actualCandidateStartersDeploymentHelper.getEventSubscriptionManager());
    assertNull(actualCandidateStartersDeploymentHelper.getTimerManager());
  }

  /**
   * Test {@link CandidateStartersDeploymentConfigurer#configure(SpringProcessEngineConfiguration)}.
   * <p>
   * Method under test: {@link CandidateStartersDeploymentConfigurer#configure(SpringProcessEngineConfiguration)}
   */
  @Test
  @DisplayName("Test configure(SpringProcessEngineConfiguration)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CandidateStartersDeploymentConfigurer.configure(SpringProcessEngineConfiguration)"})
  void testConfigure() {
    // Arrange
    SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();

    // Act
    candidateStartersDeploymentConfigurer.configure(processEngineConfiguration);

    // Assert
    assertTrue(processEngineConfiguration.getBpmnDeploymentHelper() instanceof CandidateStartersDeploymentHelper);
  }
}
