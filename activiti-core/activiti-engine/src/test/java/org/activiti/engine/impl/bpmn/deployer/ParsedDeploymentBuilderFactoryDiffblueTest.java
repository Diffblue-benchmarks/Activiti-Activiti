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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntity;
import org.junit.Test;

public class ParsedDeploymentBuilderFactoryDiffblueTest {
  /**
   * Method under test:
   * {@link ParsedDeploymentBuilderFactory#getBuilderForDeployment(DeploymentEntity)}
   */
  @Test
  public void testGetBuilderForDeployment() {
    // Arrange
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory = new ParsedDeploymentBuilderFactory();

    // Act
    ParsedDeploymentBuilder actualBuilderForDeployment = parsedDeploymentBuilderFactory
        .getBuilderForDeployment(new DeploymentEntityImpl());

    // Assert
    DeploymentEntity deploymentEntity = actualBuilderForDeployment.deployment;
    Object persistentState = deploymentEntity.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(deploymentEntity instanceof DeploymentEntityImpl);
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertEquals("", ((Map<String, String>) persistentState).get("tenantId"));
    assertEquals("", deploymentEntity.getTenantId());
    assertNull(deploymentEntity.getVersion());
    assertNull(((Map<String, String>) persistentState).get("category"));
    assertNull(((Map<String, String>) persistentState).get("key"));
    assertNull(deploymentEntity.getEngineVersion());
    assertNull(deploymentEntity.getProjectReleaseVersion());
    assertNull(deploymentEntity.getId());
    assertNull(deploymentEntity.getCategory());
    assertNull(deploymentEntity.getKey());
    assertNull(deploymentEntity.getName());
    assertNull(deploymentEntity.getDeploymentTime());
    assertNull(actualBuilderForDeployment.deploymentSettings);
    assertNull(deploymentEntity.getResources());
    assertNull(actualBuilderForDeployment.bpmnParser);
    assertFalse(deploymentEntity.isNew());
    assertFalse(deploymentEntity.isDeleted());
    assertFalse(deploymentEntity.isInserted());
    assertFalse(deploymentEntity.isUpdated());
  }

  /**
   * Method under test:
   * {@link ParsedDeploymentBuilderFactory#getBuilderForDeployment(DeploymentEntity)}
   */
  @Test
  public void testGetBuilderForDeployment2() {
    // Arrange
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory = new ParsedDeploymentBuilderFactory();
    ResourceEntity resource = mock(ResourceEntity.class);
    when(resource.getName()).thenReturn("Name");

    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    deployment.addResource(resource);

    // Act
    ParsedDeploymentBuilder actualBuilderForDeployment = parsedDeploymentBuilderFactory
        .getBuilderForDeployment(deployment);

    // Assert
    verify(resource).getName();
    DeploymentEntity deploymentEntity = actualBuilderForDeployment.deployment;
    Object persistentState = deploymentEntity.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(deploymentEntity instanceof DeploymentEntityImpl);
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertEquals("", ((Map<String, String>) persistentState).get("tenantId"));
    assertEquals("", deploymentEntity.getTenantId());
    assertNull(deploymentEntity.getVersion());
    assertNull(((Map<String, String>) persistentState).get("category"));
    assertNull(((Map<String, String>) persistentState).get("key"));
    assertNull(deploymentEntity.getEngineVersion());
    assertNull(deploymentEntity.getProjectReleaseVersion());
    assertNull(deploymentEntity.getId());
    assertNull(deploymentEntity.getCategory());
    assertNull(deploymentEntity.getKey());
    assertNull(deploymentEntity.getName());
    assertNull(deploymentEntity.getDeploymentTime());
    assertNull(actualBuilderForDeployment.deploymentSettings);
    assertNull(actualBuilderForDeployment.bpmnParser);
    Map<String, ResourceEntity> resources = deploymentEntity.getResources();
    assertEquals(1, resources.size());
    assertFalse(deploymentEntity.isNew());
    assertFalse(deploymentEntity.isDeleted());
    assertFalse(deploymentEntity.isInserted());
    assertFalse(deploymentEntity.isUpdated());
    assertTrue(resources.containsKey("Name"));
  }

  /**
   * Method under test:
   * {@link ParsedDeploymentBuilderFactory#getBuilderForDeploymentAndSettings(DeploymentEntity, Map)}
   */
  @Test
  public void testGetBuilderForDeploymentAndSettings() {
    // Arrange
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory = new ParsedDeploymentBuilderFactory();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();

    // Act
    ParsedDeploymentBuilder actualBuilderForDeploymentAndSettings = parsedDeploymentBuilderFactory
        .getBuilderForDeploymentAndSettings(deployment, new HashMap<>());

    // Assert
    DeploymentEntity deploymentEntity = actualBuilderForDeploymentAndSettings.deployment;
    Object persistentState = deploymentEntity.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(deploymentEntity instanceof DeploymentEntityImpl);
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertEquals("", ((Map<String, String>) persistentState).get("tenantId"));
    assertEquals("", deploymentEntity.getTenantId());
    assertNull(deploymentEntity.getVersion());
    assertNull(((Map<String, String>) persistentState).get("category"));
    assertNull(((Map<String, String>) persistentState).get("key"));
    assertNull(deploymentEntity.getEngineVersion());
    assertNull(deploymentEntity.getProjectReleaseVersion());
    assertNull(deploymentEntity.getId());
    assertNull(deploymentEntity.getCategory());
    assertNull(deploymentEntity.getKey());
    assertNull(deploymentEntity.getName());
    assertNull(deploymentEntity.getDeploymentTime());
    assertNull(deploymentEntity.getResources());
    assertNull(actualBuilderForDeploymentAndSettings.bpmnParser);
    assertFalse(deploymentEntity.isNew());
    assertFalse(deploymentEntity.isDeleted());
    assertFalse(deploymentEntity.isInserted());
    assertFalse(deploymentEntity.isUpdated());
    assertTrue(actualBuilderForDeploymentAndSettings.deploymentSettings.isEmpty());
  }

  /**
   * Method under test:
   * {@link ParsedDeploymentBuilderFactory#getBuilderForDeploymentAndSettings(DeploymentEntity, Map)}
   */
  @Test
  public void testGetBuilderForDeploymentAndSettings2() {
    // Arrange
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory = new ParsedDeploymentBuilderFactory();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();

    HashMap<String, Object> deploymentSettings = new HashMap<>();
    deploymentSettings.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    ParsedDeploymentBuilder actualBuilderForDeploymentAndSettings = parsedDeploymentBuilderFactory
        .getBuilderForDeploymentAndSettings(deployment, deploymentSettings);

    // Assert
    DeploymentEntity deploymentEntity = actualBuilderForDeploymentAndSettings.deployment;
    Object persistentState = deploymentEntity.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(deploymentEntity instanceof DeploymentEntityImpl);
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertEquals("", ((Map<String, String>) persistentState).get("tenantId"));
    assertEquals("", deploymentEntity.getTenantId());
    assertNull(deploymentEntity.getVersion());
    assertNull(((Map<String, String>) persistentState).get("category"));
    assertNull(((Map<String, String>) persistentState).get("key"));
    assertNull(deploymentEntity.getEngineVersion());
    assertNull(deploymentEntity.getProjectReleaseVersion());
    assertNull(deploymentEntity.getId());
    assertNull(deploymentEntity.getCategory());
    assertNull(deploymentEntity.getKey());
    assertNull(deploymentEntity.getName());
    assertNull(deploymentEntity.getDeploymentTime());
    assertNull(deploymentEntity.getResources());
    assertNull(actualBuilderForDeploymentAndSettings.bpmnParser);
    assertFalse(deploymentEntity.isNew());
    assertFalse(deploymentEntity.isDeleted());
    assertFalse(deploymentEntity.isInserted());
    assertFalse(deploymentEntity.isUpdated());
    assertTrue(actualBuilderForDeploymentAndSettings.deploymentSettings.isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link ParsedDeploymentBuilderFactory}
   *   <li>{@link ParsedDeploymentBuilderFactory#setBpmnParser(BpmnParser)}
   *   <li>{@link ParsedDeploymentBuilderFactory#getBpmnParser()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ParsedDeploymentBuilderFactory actualParsedDeploymentBuilderFactory = new ParsedDeploymentBuilderFactory();
    BpmnParser bpmnParser = new BpmnParser();
    actualParsedDeploymentBuilderFactory.setBpmnParser(bpmnParser);

    // Assert that nothing has changed
    assertSame(bpmnParser, actualParsedDeploymentBuilderFactory.getBpmnParser());
  }
}
