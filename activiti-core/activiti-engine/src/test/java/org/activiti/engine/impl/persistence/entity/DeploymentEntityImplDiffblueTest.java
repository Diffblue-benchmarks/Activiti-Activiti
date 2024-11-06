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
   * Test getters and setters.
   * <p>
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

  /**
   * Test {@link DeploymentEntityImpl#addResource(ResourceEntity)}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   *   <li>Then {@link DeploymentEntityImpl} (default constructor) Resources
   * {@code Name} is {@link ResourceEntity}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentEntityImpl#addResource(ResourceEntity)}
   */
  @Test
  public void testAddResource_givenName_thenDeploymentEntityImplResourcesNameIsResourceEntity() {
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
   * Test {@link DeploymentEntityImpl#addResource(ResourceEntity)}.
   * <ul>
   *   <li>Then {@link DeploymentEntityImpl} (default constructor) Resources is
   * {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentEntityImpl#addResource(ResourceEntity)}
   */
  @Test
  public void testAddResource_thenDeploymentEntityImplResourcesIsHashMap() {
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
   * Test {@link DeploymentEntityImpl#addResource(ResourceEntity)}.
   * <ul>
   *   <li>Then {@link DeploymentEntityImpl} (default constructor) Resources
   * {@code null} is {@link ResourceEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentEntityImpl#addResource(ResourceEntity)}
   */
  @Test
  public void testAddResource_thenDeploymentEntityImplResourcesNullIsResourceEntityImpl() {
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
   * Test {@link DeploymentEntityImpl#getResources()}.
   * <ul>
   *   <li>Given {@link DeploymentEntityImpl} (default constructor) Resources is
   * {@link HashMap#HashMap()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentEntityImpl#getResources()}
   */
  @Test
  public void testGetResources_givenDeploymentEntityImplResourcesIsHashMap_thenReturnEmpty() {
    // Arrange
    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    deploymentEntityImpl.setResources(new HashMap<>());
    deploymentEntityImpl.setId(null);

    // Act and Assert
    assertTrue(deploymentEntityImpl.getResources().isEmpty());
  }

  /**
   * Test {@link DeploymentEntityImpl#getResources()}.
   * <ul>
   *   <li>Given {@link DeploymentEntityImpl} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentEntityImpl#getResources()}
   */
  @Test
  public void testGetResources_givenDeploymentEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new DeploymentEntityImpl()).getResources());
  }

  /**
   * Test {@link DeploymentEntityImpl#getResources()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentEntityImpl#getResources()}
   */
  @Test
  public void testGetResources_givenHashMapComputeIfPresentFooAndBiFunction_thenReturnEmpty() {
    // Arrange
    HashMap<String, ResourceEntity> resources = new HashMap<>();
    resources.computeIfPresent("foo", mock(BiFunction.class));

    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    deploymentEntityImpl.setResources(resources);
    deploymentEntityImpl.setId(null);

    // Act and Assert
    assertTrue(deploymentEntityImpl.getResources().isEmpty());
  }

  /**
   * Test {@link DeploymentEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Given {@link DeploymentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_givenDeploymentEntityImpl() {
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
   * Test {@link DeploymentEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Given {@link ResourceEntity} {@link ResourceEntity#getName()} return
   * {@code Name}.</li>
   *   <li>Then calls {@link ResourceEntity#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_givenResourceEntityGetNameReturnName_thenCallsGetName() {
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
   * Test {@link DeploymentEntityImpl#getDeployedArtifacts(Class)}.
   * <ul>
   *   <li>Given {@link ResourceEntity} {@link ResourceEntity#getName()} return
   * {@code Name}.</li>
   *   <li>Then calls {@link ResourceEntity#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentEntityImpl#getDeployedArtifacts(Class)}
   */
  @Test
  public void testGetDeployedArtifacts_givenResourceEntityGetNameReturnName_thenCallsGetName() {
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
   * Test {@link DeploymentEntityImpl#getDeployedArtifacts(Class)}.
   * <ul>
   *   <li>When {@code java.lang.Class}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentEntityImpl#getDeployedArtifacts(Class)}
   */
  @Test
  public void testGetDeployedArtifacts_whenJavaLangClass_thenReturnNull() {
    // Arrange
    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    deploymentEntityImpl.addDeployedArtifact(JSONObject.NULL);
    Class<Class> clazz = Class.class;

    // Act and Assert
    assertNull(deploymentEntityImpl.getDeployedArtifacts(clazz));
  }

  /**
   * Test {@link DeploymentEntityImpl#getDeployedArtifacts(Class)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeploymentEntityImpl#getDeployedArtifacts(Class)}
   */
  @Test
  public void testGetDeployedArtifacts_whenJavaLangObject_thenReturnSizeIsOne() {
    // Arrange
    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    deploymentEntityImpl.addDeployedArtifact(JSONObject.NULL);
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertEquals(1, deploymentEntityImpl.getDeployedArtifacts(clazz).size());
  }
}
