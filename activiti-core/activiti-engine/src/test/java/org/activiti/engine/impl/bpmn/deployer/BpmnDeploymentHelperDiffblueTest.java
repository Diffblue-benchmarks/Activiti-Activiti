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
package org.activiti.engine.impl.bpmn.deployer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.junit.Test;

public class BpmnDeploymentHelperDiffblueTest {
  /**
   * Test
   * {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}.
   * <p>
   * Method under test:
   * {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}
   */
  @Test
  public void testCopyDeploymentValuesToProcessDefinitions() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    deployment.setEngineVersion(null);
    deployment.setTenantId(null);

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(new ProcessDefinitionEntityImpl());

    // Act
    bpmnDeploymentHelper.copyDeploymentValuesToProcessDefinitions(deployment, processDefinitions);

    // Assert
    assertEquals(1, processDefinitions.size());
    ProcessDefinitionEntity getResult = processDefinitions.get(0);
    assertTrue(getResult instanceof ProcessDefinitionEntityImpl);
    assertEquals("", getResult.getTenantId());
    assertNull(getResult.getEngineVersion());
  }

  /**
   * Test
   * {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}.
   * <p>
   * Method under test:
   * {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}
   */
  @Test
  public void testCopyDeploymentValuesToProcessDefinitions2() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    deployment.setEngineVersion(null);
    deployment.setTenantId("Deployment");

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(new ProcessDefinitionEntityImpl());

    // Act
    bpmnDeploymentHelper.copyDeploymentValuesToProcessDefinitions(deployment, processDefinitions);

    // Assert
    assertEquals(1, processDefinitions.size());
    ProcessDefinitionEntity getResult = processDefinitions.get(0);
    assertTrue(getResult instanceof ProcessDefinitionEntityImpl);
    assertEquals("Deployment", getResult.getTenantId());
    assertNull(getResult.getEngineVersion());
  }

  /**
   * Test
   * {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}.
   * <p>
   * Method under test:
   * {@link BpmnDeploymentHelper#copyDeploymentValuesToProcessDefinitions(DeploymentEntity, List)}
   */
  @Test
  public void testCopyDeploymentValuesToProcessDefinitions3() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    deployment.setEngineVersion("Deployment");
    deployment.setTenantId(null);

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(new ProcessDefinitionEntityImpl());

    // Act
    bpmnDeploymentHelper.copyDeploymentValuesToProcessDefinitions(deployment, processDefinitions);

    // Assert
    assertEquals(1, processDefinitions.size());
    ProcessDefinitionEntity getResult = processDefinitions.get(0);
    assertTrue(getResult instanceof ProcessDefinitionEntityImpl);
    assertEquals("", getResult.getTenantId());
    assertEquals("Deployment", getResult.getEngineVersion());
  }

  /**
   * Test
   * {@link BpmnDeploymentHelper#getPersistedInstanceOfProcessDefinition(ProcessDefinitionEntity)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnDeploymentHelper#getPersistedInstanceOfProcessDefinition(ProcessDefinitionEntity)}
   */
  @Test
  public void testGetPersistedInstanceOfProcessDefinition_thenThrowIllegalStateException() {
    // Arrange
    BpmnDeploymentHelper bpmnDeploymentHelper = new BpmnDeploymentHelper();

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> bpmnDeploymentHelper.getPersistedInstanceOfProcessDefinition(new ProcessDefinitionEntityImpl()));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BpmnDeploymentHelper}
   *   <li>
   * {@link BpmnDeploymentHelper#setEventSubscriptionManager(EventSubscriptionManager)}
   *   <li>{@link BpmnDeploymentHelper#setTimerManager(TimerManager)}
   *   <li>{@link BpmnDeploymentHelper#getEventSubscriptionManager()}
   *   <li>{@link BpmnDeploymentHelper#getTimerManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    BpmnDeploymentHelper actualBpmnDeploymentHelper = new BpmnDeploymentHelper();
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    actualBpmnDeploymentHelper.setEventSubscriptionManager(eventSubscriptionManager);
    TimerManager timerManager = new TimerManager();
    actualBpmnDeploymentHelper.setTimerManager(timerManager);
    EventSubscriptionManager actualEventSubscriptionManager = actualBpmnDeploymentHelper.getEventSubscriptionManager();

    // Assert that nothing has changed
    assertSame(eventSubscriptionManager, actualEventSubscriptionManager);
    assertSame(timerManager, actualBpmnDeploymentHelper.getTimerManager());
  }
}
