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
package org.activiti.api.runtime.event.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.process.model.Deployment;
import org.activiti.api.process.model.events.ApplicationEvent;
import org.activiti.api.process.model.events.ApplicationEvent.ApplicationEvents;
import org.activiti.api.runtime.model.impl.DeploymentImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ApplicationDeployedEventImplDiffblueTest {
  /**
   * Test {@link ApplicationDeployedEventImpl#ApplicationDeployedEventImpl(Deployment)}.
   * <p>
   * Method under test: {@link ApplicationDeployedEventImpl#ApplicationDeployedEventImpl(Deployment)}
   */
  @Test
  @DisplayName("Test new ApplicationDeployedEventImpl(Deployment)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ApplicationDeployedEventImpl.<init>(Deployment)"})
  void testNewApplicationDeployedEventImpl() {
    // Arrange
    DeploymentImpl entity = new DeploymentImpl();

    // Act
    ApplicationDeployedEventImpl actualApplicationDeployedEventImpl = new ApplicationDeployedEventImpl(entity);

    // Assert
    assertNull(actualApplicationDeployedEventImpl.getProcessDefinitionVersion());
    assertNull(actualApplicationDeployedEventImpl.getBusinessKey());
    assertNull(actualApplicationDeployedEventImpl.getParentProcessInstanceId());
    assertNull(actualApplicationDeployedEventImpl.getProcessDefinitionId());
    assertNull(actualApplicationDeployedEventImpl.getProcessDefinitionKey());
    assertNull(actualApplicationDeployedEventImpl.getProcessInstanceId());
    assertEquals(ApplicationEvents.APPLICATION_DEPLOYED, actualApplicationDeployedEventImpl.getEventType());
    assertSame(entity, actualApplicationDeployedEventImpl.getEntity());
  }

  /**
   * Test {@link ApplicationDeployedEventImpl#ApplicationDeployedEventImpl(Deployment, ApplicationEvents)}.
   * <p>
   * Method under test: {@link ApplicationDeployedEventImpl#ApplicationDeployedEventImpl(Deployment, ApplicationEvents)}
   */
  @Test
  @DisplayName("Test new ApplicationDeployedEventImpl(Deployment, ApplicationEvents)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ApplicationDeployedEventImpl.<init>(Deployment, ApplicationEvents)"})
  void testNewApplicationDeployedEventImpl2() {
    // Arrange
    DeploymentImpl entity = new DeploymentImpl();

    // Act
    ApplicationDeployedEventImpl actualApplicationDeployedEventImpl = new ApplicationDeployedEventImpl(entity,
        ApplicationEvents.APPLICATION_DEPLOYED);

    // Assert
    assertNull(actualApplicationDeployedEventImpl.getProcessDefinitionVersion());
    assertNull(actualApplicationDeployedEventImpl.getBusinessKey());
    assertNull(actualApplicationDeployedEventImpl.getParentProcessInstanceId());
    assertNull(actualApplicationDeployedEventImpl.getProcessDefinitionId());
    assertNull(actualApplicationDeployedEventImpl.getProcessDefinitionKey());
    assertNull(actualApplicationDeployedEventImpl.getProcessInstanceId());
    assertEquals(ApplicationEvents.APPLICATION_DEPLOYED, actualApplicationDeployedEventImpl.getEventType());
    assertSame(entity, actualApplicationDeployedEventImpl.getEntity());
  }

  /**
   * Test {@link ApplicationDeployedEventImpl#getEventType()}.
   * <p>
   * Method under test: {@link ApplicationDeployedEventImpl#getEventType()}
   */
  @Test
  @DisplayName("Test getEventType()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ApplicationEvents ApplicationDeployedEventImpl.getEventType()"})
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(ApplicationEvents.APPLICATION_DEPLOYED,
        (new ApplicationDeployedEventImpl(new DeploymentImpl())).getEventType());
  }
}
