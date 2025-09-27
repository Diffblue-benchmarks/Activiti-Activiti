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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ParsedDeploymentBuilderFactoryDiffblueTest {
  /**
   * Test {@link ParsedDeploymentBuilderFactory#getBuilderForDeployment(DeploymentEntity)}.
   *
   * <p>Method under test: {@link
   * ParsedDeploymentBuilderFactory#getBuilderForDeployment(DeploymentEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ParsedDeploymentBuilder ParsedDeploymentBuilderFactory.getBuilderForDeployment(DeploymentEntity)"
  })
  public void testGetBuilderForDeployment() {
    // Arrange
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory =
        new ParsedDeploymentBuilderFactory();

    // Act
    ParsedDeploymentBuilder actualBuilderForDeployment =
        parsedDeploymentBuilderFactory.getBuilderForDeployment(new DeploymentEntityImpl());

    // Assert
    DeploymentEntity deploymentEntity = actualBuilderForDeployment.deployment;
    Object persistentState = deploymentEntity.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(deploymentEntity instanceof DeploymentEntityImpl);
    assertEquals("", deploymentEntity.getTenantId());
    assertNull(deploymentEntity.getVersion());
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
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertFalse(deploymentEntity.isNew());
    assertFalse(deploymentEntity.isDeleted());
    assertFalse(deploymentEntity.isInserted());
    assertFalse(deploymentEntity.isUpdated());
    assertTrue(((Map<String, String>) persistentState).containsKey("category"));
    assertTrue(((Map<String, String>) persistentState).containsKey("key"));
    assertTrue(((Map<String, String>) persistentState).containsKey("tenantId"));
  }

  /**
   * Test {@link ParsedDeploymentBuilderFactory#getBuilderForDeploymentAndSettings(DeploymentEntity,
   * Map)}.
   *
   * <p>Method under test: {@link
   * ParsedDeploymentBuilderFactory#getBuilderForDeploymentAndSettings(DeploymentEntity, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ParsedDeploymentBuilder ParsedDeploymentBuilderFactory.getBuilderForDeploymentAndSettings(DeploymentEntity, Map)"
  })
  public void testGetBuilderForDeploymentAndSettings() {
    // Arrange
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory =
        new ParsedDeploymentBuilderFactory();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();

    // Act
    ParsedDeploymentBuilder actualBuilderForDeploymentAndSettings =
        parsedDeploymentBuilderFactory.getBuilderForDeploymentAndSettings(
            deployment, new HashMap<>());

    // Assert
    DeploymentEntity deploymentEntity = actualBuilderForDeploymentAndSettings.deployment;
    Object persistentState = deploymentEntity.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(deploymentEntity instanceof DeploymentEntityImpl);
    assertEquals("", deploymentEntity.getTenantId());
    assertNull(deploymentEntity.getVersion());
    assertNull(deploymentEntity.getEngineVersion());
    assertNull(deploymentEntity.getProjectReleaseVersion());
    assertNull(deploymentEntity.getId());
    assertNull(deploymentEntity.getCategory());
    assertNull(deploymentEntity.getKey());
    assertNull(deploymentEntity.getName());
    assertNull(deploymentEntity.getDeploymentTime());
    assertNull(deploymentEntity.getResources());
    assertNull(actualBuilderForDeploymentAndSettings.bpmnParser);
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertFalse(deploymentEntity.isNew());
    assertFalse(deploymentEntity.isDeleted());
    assertFalse(deploymentEntity.isInserted());
    assertFalse(deploymentEntity.isUpdated());
    assertTrue(((Map<String, String>) persistentState).containsKey("category"));
    assertTrue(((Map<String, String>) persistentState).containsKey("key"));
    assertTrue(((Map<String, String>) persistentState).containsKey("tenantId"));
    assertTrue(actualBuilderForDeploymentAndSettings.deploymentSettings.isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link ParsedDeploymentBuilderFactory}
   *   <li>{@link ParsedDeploymentBuilderFactory#setBpmnParser(BpmnParser)}
   *   <li>{@link ParsedDeploymentBuilderFactory#getBpmnParser()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ParsedDeploymentBuilderFactory.<init>()",
    "BpmnParser ParsedDeploymentBuilderFactory.getBpmnParser()",
    "void ParsedDeploymentBuilderFactory.setBpmnParser(BpmnParser)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    ParsedDeploymentBuilderFactory actualParsedDeploymentBuilderFactory =
        new ParsedDeploymentBuilderFactory();
    BpmnParser bpmnParser = new BpmnParser();
    actualParsedDeploymentBuilderFactory.setBpmnParser(bpmnParser);

    // Assert
    assertSame(bpmnParser, actualParsedDeploymentBuilderFactory.getBpmnParser());
  }
}
