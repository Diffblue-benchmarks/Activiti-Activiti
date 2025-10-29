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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class DeploymentEntityImplDiffblueTest {
  /**
   * Method under test: {@link DeploymentEntityImpl#addResource(ResourceEntity)}
   */
  @Test
  public void testAddResource() {
    // Arrange
    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    ResourceEntityImpl resource = new ResourceEntityImpl();

    // Act
    deploymentEntityImpl.addResource(resource);

    // Assert
    Map<String, ResourceEntity> resources = deploymentEntityImpl.getResources();
    assertEquals(1, resources.size());
    Map<String, ResourceEntity> stringResourceEntityMap = deploymentEntityImpl.resources;
    assertEquals(1, stringResourceEntityMap.size());
    assertSame(resource, resources.get(null));
    assertSame(resource, stringResourceEntityMap.get(null));
    assertSame(deploymentEntityImpl.resources, resources);
  }

  /**
   * Method under test: {@link DeploymentEntityImpl#addResource(ResourceEntity)}
   */
  @Test
  public void testAddResource2() {
    // Arrange
    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    HashMap<String, ResourceEntity> resources = new HashMap<>();
    deploymentEntityImpl.setResources(resources);
    ResourceEntityImpl resource = new ResourceEntityImpl();

    // Act
    deploymentEntityImpl.addResource(resource);

    // Assert
    Map<String, ResourceEntity> resources2 = deploymentEntityImpl.getResources();
    assertEquals(1, resources2.size());
    Map<String, ResourceEntity> stringResourceEntityMap = deploymentEntityImpl.resources;
    assertEquals(1, stringResourceEntityMap.size());
    assertSame(resources, resources2);
    assertSame(resource, resources2.get(null));
    assertSame(resource, stringResourceEntityMap.get(null));
  }

  /**
   * Method under test: {@link DeploymentEntityImpl#addResource(ResourceEntity)}
   */
  @Test
  public void testAddResource3() {
    // Arrange
    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    ResourceEntity resource = mock(ResourceEntity.class);
    when(resource.getName()).thenReturn("Name");

    // Act
    deploymentEntityImpl.addResource(resource);

    // Assert
    verify(resource).getName();
    Map<String, ResourceEntity> resources = deploymentEntityImpl.getResources();
    assertEquals(1, resources.size());
    Map<String, ResourceEntity> stringResourceEntityMap = deploymentEntityImpl.resources;
    assertEquals(1, stringResourceEntityMap.size());
    assertSame(deploymentEntityImpl.resources, resources);
    assertSame(resource, resources.get("Name"));
    assertSame(resource, stringResourceEntityMap.get("Name"));
  }

  /**
   * Method under test: {@link DeploymentEntityImpl#getResources()}
   */
  @Test
  public void testGetResources() {
    // Arrange, Act and Assert
    assertNull((new DeploymentEntityImpl()).getResources());
  }

  /**
   * Method under test: {@link DeploymentEntityImpl#getResources()}
   */
  @Test
  public void testGetResources2() {
    // Arrange
    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    HashMap<String, ResourceEntity> resources = new HashMap<>();
    deploymentEntityImpl.setResources(resources);
    deploymentEntityImpl.setId(null);

    // Act
    Map<String, ResourceEntity> actualResources = deploymentEntityImpl.getResources();

    // Assert
    assertTrue(actualResources.isEmpty());
    assertSame(resources, actualResources);
  }

  /**
   * Method under test: {@link DeploymentEntityImpl#getResources()}
   */
  @Test
  public void testGetResources3() {
    // Arrange
    HashMap<String, ResourceEntity> resources = new HashMap<>();
    resources.computeIfPresent("foo", mock(BiFunction.class));

    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    deploymentEntityImpl.setResources(resources);
    deploymentEntityImpl.setId(null);

    // Act
    Map<String, ResourceEntity> actualResources = deploymentEntityImpl.getResources();

    // Assert
    assertTrue(actualResources.isEmpty());
    assertSame(resources, actualResources);
  }

  /**
   * Method under test: {@link DeploymentEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState() {
    // Arrange and Act
    Object actualPersistentState = (new DeploymentEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) actualPersistentState).size());
    assertEquals("", ((Map<String, String>) actualPersistentState).get("tenantId"));
    assertNull(((Map<String, String>) actualPersistentState).get("category"));
    assertNull(((Map<String, String>) actualPersistentState).get("key"));
  }

  /**
   * Method under test: {@link DeploymentEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState2() {
    // Arrange
    ResourceEntity resource = mock(ResourceEntity.class);
    when(resource.getName()).thenReturn("Name");

    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    deploymentEntityImpl.addResource(resource);

    // Act
    Object actualPersistentState = deploymentEntityImpl.getPersistentState();

    // Assert
    verify(resource).getName();
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) actualPersistentState).size());
    assertEquals("", ((Map<String, String>) actualPersistentState).get("tenantId"));
    assertNull(((Map<String, String>) actualPersistentState).get("category"));
    assertNull(((Map<String, String>) actualPersistentState).get("key"));
  }

  /**
   * Method under test: {@link DeploymentEntityImpl#getDeployedArtifacts(Class)}
   */
  @Test
  public void testGetDeployedArtifacts() {
    // Arrange
    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    deploymentEntityImpl.addDeployedArtifact(JSONObject.NULL);
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertEquals(1, deploymentEntityImpl.getDeployedArtifacts(clazz).size());
  }

  /**
   * Method under test: {@link DeploymentEntityImpl#getDeployedArtifacts(Class)}
   */
  @Test
  public void testGetDeployedArtifacts2() {
    // Arrange
    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    deploymentEntityImpl.addDeployedArtifact(JSONObject.NULL);
    Class<Class> clazz = Class.class;

    // Act and Assert
    assertNull(deploymentEntityImpl.getDeployedArtifacts(clazz));
  }

  /**
   * Method under test: {@link DeploymentEntityImpl#getDeployedArtifacts(Class)}
   */
  @Test
  public void testGetDeployedArtifacts3() {
    // Arrange
    ResourceEntity resource = mock(ResourceEntity.class);
    when(resource.getName()).thenReturn("Name");

    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    deploymentEntityImpl.addResource(resource);
    deploymentEntityImpl.addDeployedArtifact(JSONObject.NULL);
    Class<Object> clazz = Object.class;

    // Act
    List<Object> actualDeployedArtifacts = deploymentEntityImpl.getDeployedArtifacts(clazz);

    // Assert
    verify(resource).getName();
    assertEquals(1, actualDeployedArtifacts.size());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DeploymentEntityImpl}
   *   <li>{@link DeploymentEntityImpl#setCategory(String)}
   *   <li>{@link DeploymentEntityImpl#setDeploymentTime(Date)}
   *   <li>{@link DeploymentEntityImpl#setEngineVersion(String)}
   *   <li>{@link DeploymentEntityImpl#setKey(String)}
   *   <li>{@link DeploymentEntityImpl#setName(String)}
   *   <li>{@link DeploymentEntityImpl#setNew(boolean)}
   *   <li>{@link DeploymentEntityImpl#setProjectReleaseVersion(String)}
   *   <li>{@link DeploymentEntityImpl#setResources(Map)}
   *   <li>{@link DeploymentEntityImpl#setTenantId(String)}
   *   <li>{@link DeploymentEntityImpl#setVersion(Integer)}
   *   <li>{@link DeploymentEntityImpl#toString()}
   *   <li>{@link DeploymentEntityImpl#getCategory()}
   *   <li>{@link DeploymentEntityImpl#getDeploymentTime()}
   *   <li>{@link DeploymentEntityImpl#getEngineVersion()}
   *   <li>{@link DeploymentEntityImpl#getKey()}
   *   <li>{@link DeploymentEntityImpl#getName()}
   *   <li>{@link DeploymentEntityImpl#getProjectReleaseVersion()}
   *   <li>{@link DeploymentEntityImpl#getTenantId()}
   *   <li>{@link DeploymentEntityImpl#getVersion()}
   *   <li>{@link DeploymentEntityImpl#isNew()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DeploymentEntityImpl actualDeploymentEntityImpl = new DeploymentEntityImpl();
    actualDeploymentEntityImpl.setCategory("Category");
    Date deploymentTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualDeploymentEntityImpl.setDeploymentTime(deploymentTime);
    actualDeploymentEntityImpl.setEngineVersion("1.0.2");
    actualDeploymentEntityImpl.setKey("Key");
    actualDeploymentEntityImpl.setName("Name");
    actualDeploymentEntityImpl.setNew(true);
    actualDeploymentEntityImpl.setProjectReleaseVersion("1.0.2");
    actualDeploymentEntityImpl.setResources(new HashMap<>());
    actualDeploymentEntityImpl.setTenantId("42");
    actualDeploymentEntityImpl.setVersion(1);
    String actualToStringResult = actualDeploymentEntityImpl.toString();
    String actualCategory = actualDeploymentEntityImpl.getCategory();
    Date actualDeploymentTime = actualDeploymentEntityImpl.getDeploymentTime();
    String actualEngineVersion = actualDeploymentEntityImpl.getEngineVersion();
    String actualKey = actualDeploymentEntityImpl.getKey();
    String actualName = actualDeploymentEntityImpl.getName();
    String actualProjectReleaseVersion = actualDeploymentEntityImpl.getProjectReleaseVersion();
    String actualTenantId = actualDeploymentEntityImpl.getTenantId();
    Integer actualVersion = actualDeploymentEntityImpl.getVersion();
    boolean actualIsNewResult = actualDeploymentEntityImpl.isNew();

    // Assert that nothing has changed
    assertEquals("1.0.2", actualEngineVersion);
    assertEquals("1.0.2", actualProjectReleaseVersion);
    assertEquals("42", actualTenantId);
    assertEquals("Category", actualCategory);
    assertEquals("DeploymentEntity[id=null, name=Name]", actualToStringResult);
    assertEquals("Key", actualKey);
    assertEquals("Name", actualName);
    assertEquals(1, actualVersion.intValue());
    assertFalse(actualDeploymentEntityImpl.isDeleted());
    assertFalse(actualDeploymentEntityImpl.isInserted());
    assertFalse(actualDeploymentEntityImpl.isUpdated());
    assertTrue(actualDeploymentEntityImpl.resources.isEmpty());
    assertTrue(actualIsNewResult);
    assertSame(deploymentTime, actualDeploymentTime);
  }
}
