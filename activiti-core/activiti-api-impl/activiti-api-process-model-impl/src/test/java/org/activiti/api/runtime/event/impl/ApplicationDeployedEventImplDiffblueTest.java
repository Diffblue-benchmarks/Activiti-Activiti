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
import org.activiti.api.process.model.Deployment;
import org.activiti.api.process.model.events.ApplicationEvent;
import org.activiti.api.runtime.model.impl.DeploymentImpl;
import org.junit.jupiter.api.Test;

class ApplicationDeployedEventImplDiffblueTest {
  /**
   * Method under test: {@link ApplicationDeployedEventImpl#getEventType()}
   */
  @Test
  void testGetEventType() {
    // Arrange, Act and Assert
    assertEquals(ApplicationEvent.ApplicationEvents.APPLICATION_DEPLOYED,
        (new ApplicationDeployedEventImpl(new DeploymentImpl())).getEventType());
  }

  /**
   * Method under test:
   * {@link ApplicationDeployedEventImpl#ApplicationDeployedEventImpl(Deployment)}
   */
  @Test
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
    assertEquals(ApplicationEvent.ApplicationEvents.APPLICATION_DEPLOYED,
        actualApplicationDeployedEventImpl.getEventType());
    assertSame(entity, actualApplicationDeployedEventImpl.getEntity());
  }

  /**
   * Method under test:
   * {@link ApplicationDeployedEventImpl#ApplicationDeployedEventImpl(Deployment, ApplicationEvent.ApplicationEvents)}
   */
  @Test
  void testNewApplicationDeployedEventImpl2() {
    // Arrange
    DeploymentImpl entity = new DeploymentImpl();

    // Act
    ApplicationDeployedEventImpl actualApplicationDeployedEventImpl = new ApplicationDeployedEventImpl(entity,
        ApplicationEvent.ApplicationEvents.APPLICATION_DEPLOYED);

    // Assert
    assertNull(actualApplicationDeployedEventImpl.getProcessDefinitionVersion());
    assertNull(actualApplicationDeployedEventImpl.getBusinessKey());
    assertNull(actualApplicationDeployedEventImpl.getParentProcessInstanceId());
    assertNull(actualApplicationDeployedEventImpl.getProcessDefinitionId());
    assertNull(actualApplicationDeployedEventImpl.getProcessDefinitionKey());
    assertNull(actualApplicationDeployedEventImpl.getProcessInstanceId());
    assertEquals(ApplicationEvent.ApplicationEvents.APPLICATION_DEPLOYED,
        actualApplicationDeployedEventImpl.getEventType());
    assertSame(entity, actualApplicationDeployedEventImpl.getEntity());
  }
}
