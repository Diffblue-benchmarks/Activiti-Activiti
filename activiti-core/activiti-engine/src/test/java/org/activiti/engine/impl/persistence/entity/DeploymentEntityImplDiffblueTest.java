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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DeploymentEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityImpl.<init>()",
    "String DeploymentEntityImpl.getCategory()",
    "Date DeploymentEntityImpl.getDeploymentTime()",
    "String DeploymentEntityImpl.getEngineVersion()",
    "String DeploymentEntityImpl.getKey()",
    "String DeploymentEntityImpl.getName()",
    "String DeploymentEntityImpl.getProjectReleaseVersion()",
    "String DeploymentEntityImpl.getTenantId()",
    "Integer DeploymentEntityImpl.getVersion()",
    "boolean DeploymentEntityImpl.isNew()",
    "void DeploymentEntityImpl.setCategory(String)",
    "void DeploymentEntityImpl.setDeploymentTime(Date)",
    "void DeploymentEntityImpl.setEngineVersion(String)",
    "void DeploymentEntityImpl.setKey(String)",
    "void DeploymentEntityImpl.setName(String)",
    "void DeploymentEntityImpl.setNew(boolean)",
    "void DeploymentEntityImpl.setProjectReleaseVersion(String)",
    "void DeploymentEntityImpl.setResources(Map)",
    "void DeploymentEntityImpl.setTenantId(String)",
    "void DeploymentEntityImpl.setVersion(Integer)",
    "String DeploymentEntityImpl.toString()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    DeploymentEntityImpl actualDeploymentEntityImpl = new DeploymentEntityImpl();
    actualDeploymentEntityImpl.setCategory("Category");
    Date deploymentTime =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
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

    // Assert
    assertEquals("1.0.2", actualEngineVersion);
    assertEquals("1.0.2", actualProjectReleaseVersion);
    assertEquals("42", actualTenantId);
    assertEquals("Category", actualCategory);
    assertEquals("DeploymentEntity[id=null, name=Name]", actualToStringResult);
    assertEquals("Key", actualKey);
    assertEquals("Name", actualName);
    assertNull(actualDeploymentEntityImpl.getId());
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
   *
   * <ul>
   *   <li>Given {@link DeploymentEntityImpl} (default constructor).
   *   <li>Then {@link DeploymentEntityImpl} (default constructor) {@link
   *       DeploymentEntityImpl#resources}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityImpl#addResource(ResourceEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeploymentEntityImpl.addResource(ResourceEntity)"})
  public void testAddResource_givenDeploymentEntityImpl_thenDeploymentEntityImplResources() {
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
   * Test {@link DeploymentEntityImpl#addResource(ResourceEntity)}.
   *
   * <ul>
   *   <li>Then {@link DeploymentEntityImpl} (default constructor) Resources is {@link
   *       HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityImpl#addResource(ResourceEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeploymentEntityImpl.addResource(ResourceEntity)"})
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
   * Test {@link DeploymentEntityImpl#getResources()}.
   *
   * <ul>
   *   <li>Given {@link DeploymentEntityImpl} (default constructor) Resources is {@link
   *       HashMap#HashMap()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityImpl#getResources()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map DeploymentEntityImpl.getResources()"})
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
   *
   * <ul>
   *   <li>Given {@link DeploymentEntityImpl} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityImpl#getResources()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map DeploymentEntityImpl.getResources()"})
  public void testGetResources_givenDeploymentEntityImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new DeploymentEntityImpl().getResources());
  }

  /**
   * Test {@link DeploymentEntityImpl#getPersistentState()}.
   *
   * <p>Method under test: {@link DeploymentEntityImpl#getPersistentState()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object DeploymentEntityImpl.getPersistentState()"})
  public void testGetPersistentState() {
    // Arrange and Act
    Object actualPersistentState = new DeploymentEntityImpl().getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(3, ((Map<String, String>) actualPersistentState).size());
    assertEquals("", ((Map<String, String>) actualPersistentState).get("tenantId"));
    assertNull(((Map<String, String>) actualPersistentState).get("category"));
    assertNull(((Map<String, String>) actualPersistentState).get("key"));
  }

  /**
   * Test {@link DeploymentEntityImpl#getDeployedArtifacts(Class)}.
   *
   * <ul>
   *   <li>When {@code Class}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityImpl#getDeployedArtifacts(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List DeploymentEntityImpl.getDeployedArtifacts(Class)"})
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
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityImpl#getDeployedArtifacts(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List DeploymentEntityImpl.getDeployedArtifacts(Class)"})
  public void testGetDeployedArtifacts_whenJavaLangObject_thenReturnSizeIsOne() {
    // Arrange
    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    deploymentEntityImpl.addDeployedArtifact(JSONObject.NULL);
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertEquals(1, deploymentEntityImpl.getDeployedArtifacts(clazz).size());
  }
}
