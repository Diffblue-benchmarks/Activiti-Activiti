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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessDefinitionQueryImplDiffblueTest {
  @InjectMocks
  private ProcessDefinitionQueryImpl processDefinitionQueryImpl;

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId() {
    // Arrange and Act
    ProcessDefinitionQueryImpl actualProcessDefinitionIdResult = processDefinitionQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", processDefinitionQueryImpl.getId());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionCategory(String)}
   */
  @Test
  public void testProcessDefinitionCategory() {
    // Arrange and Act
    ProcessDefinitionQueryImpl actualProcessDefinitionCategoryResult = processDefinitionQueryImpl
        .processDefinitionCategory("Category");

    // Assert
    assertEquals("Category", processDefinitionQueryImpl.getCategory());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionCategoryResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionCategory(String)}
   */
  @Test
  public void testProcessDefinitionCategory2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processDefinitionQueryImpl.processDefinitionCategory(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionCategoryLike(String)}
   */
  @Test
  public void testProcessDefinitionCategoryLike() {
    // Arrange and Act
    ProcessDefinitionQueryImpl actualProcessDefinitionCategoryLikeResult = processDefinitionQueryImpl
        .processDefinitionCategoryLike("Category Like");

    // Assert
    assertEquals("Category Like", processDefinitionQueryImpl.getCategoryLike());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionCategoryLikeResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionCategoryLike(String)}
   */
  @Test
  public void testProcessDefinitionCategoryLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processDefinitionQueryImpl.processDefinitionCategoryLike(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionCategoryNotEquals(String)}
   */
  @Test
  public void testProcessDefinitionCategoryNotEquals() {
    // Arrange and Act
    ProcessDefinitionQueryImpl actualProcessDefinitionCategoryNotEqualsResult = processDefinitionQueryImpl
        .processDefinitionCategoryNotEquals("Category Not Equals");

    // Assert
    assertEquals("Category Not Equals", processDefinitionQueryImpl.getCategoryNotEquals());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionCategoryNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionCategoryNotEquals(String)}
   */
  @Test
  public void testProcessDefinitionCategoryNotEquals2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processDefinitionQueryImpl.processDefinitionCategoryNotEquals(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionName(String)}
   */
  @Test
  public void testProcessDefinitionName() {
    // Arrange and Act
    ProcessDefinitionQueryImpl actualProcessDefinitionNameResult = processDefinitionQueryImpl
        .processDefinitionName("Name");

    // Assert
    assertEquals("Name", processDefinitionQueryImpl.getName());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionNameResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionName(String)}
   */
  @Test
  public void testProcessDefinitionName2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> processDefinitionQueryImpl.processDefinitionName(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionNameLike(String)}
   */
  @Test
  public void testProcessDefinitionNameLike() {
    // Arrange and Act
    ProcessDefinitionQueryImpl actualProcessDefinitionNameLikeResult = processDefinitionQueryImpl
        .processDefinitionNameLike("Name Like");

    // Assert
    assertEquals("Name Like", processDefinitionQueryImpl.getNameLike());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionNameLikeResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionNameLike(String)}
   */
  @Test
  public void testProcessDefinitionNameLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processDefinitionQueryImpl.processDefinitionNameLike(null));
  }

  /**
   * Method under test: {@link ProcessDefinitionQueryImpl#deploymentId(String)}
   */
  @Test
  public void testDeploymentId() {
    // Arrange and Act
    ProcessDefinitionQueryImpl actualDeploymentIdResult = processDefinitionQueryImpl.deploymentId("42");

    // Assert
    assertEquals("42", processDefinitionQueryImpl.getDeploymentId());
    assertSame(processDefinitionQueryImpl, actualDeploymentIdResult);
  }

  /**
   * Method under test: {@link ProcessDefinitionQueryImpl#deploymentId(String)}
   */
  @Test
  public void testDeploymentId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> processDefinitionQueryImpl.deploymentId(null));
  }

  /**
   * Method under test: {@link ProcessDefinitionQueryImpl#deploymentIds(Set)}
   */
  @Test
  public void testDeploymentIds() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();
    HashSet<String> deploymentIds = new HashSet<>();

    // Act
    ProcessDefinitionQueryImpl actualDeploymentIdsResult = processDefinitionQueryImpl.deploymentIds(deploymentIds);

    // Assert
    assertSame(deploymentIds, processDefinitionQueryImpl.getDeploymentIds());
    assertSame(processDefinitionQueryImpl, actualDeploymentIdsResult);
  }

  /**
   * Method under test: {@link ProcessDefinitionQueryImpl#deploymentIds(Set)}
   */
  @Test
  public void testDeploymentIds2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ProcessDefinitionQueryImpl()).deploymentIds(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionKey(String)}
   */
  @Test
  public void testProcessDefinitionKey() {
    // Arrange and Act
    ProcessDefinitionQueryImpl actualProcessDefinitionKeyResult = processDefinitionQueryImpl
        .processDefinitionKey("Key");

    // Assert
    assertEquals("Key", processDefinitionQueryImpl.getKey());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionKeyResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionKey(String)}
   */
  @Test
  public void testProcessDefinitionKey2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> processDefinitionQueryImpl.processDefinitionKey(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionIdOrKey(String)}
   */
  @Test
  public void testProcessDefinitionIdOrKey() {
    // Arrange and Act
    ProcessDefinitionQuery actualProcessDefinitionIdOrKeyResult = processDefinitionQueryImpl
        .processDefinitionIdOrKey("Id Or Key");

    // Assert
    assertEquals("Id Or Key", processDefinitionQueryImpl.getIdOrKey());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionIdOrKeyResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionIdOrKey(String)}
   */
  @Test
  public void testProcessDefinitionIdOrKey2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processDefinitionQueryImpl.processDefinitionIdOrKey(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  public void testProcessDefinitionKeys() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();
    HashSet<String> keys = new HashSet<>();

    // Act
    ProcessDefinitionQueryImpl actualProcessDefinitionKeysResult = processDefinitionQueryImpl
        .processDefinitionKeys(keys);

    // Assert
    assertSame(keys, processDefinitionQueryImpl.getKeys());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionKeysResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  public void testProcessDefinitionKeys2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionKeys(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionKeyLike(String)}
   */
  @Test
  public void testProcessDefinitionKeyLike() {
    // Arrange and Act
    ProcessDefinitionQueryImpl actualProcessDefinitionKeyLikeResult = processDefinitionQueryImpl
        .processDefinitionKeyLike("Key Like");

    // Assert
    assertEquals("Key Like", processDefinitionQueryImpl.getKeyLike());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionKeyLikeResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionKeyLike(String)}
   */
  @Test
  public void testProcessDefinitionKeyLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processDefinitionQueryImpl.processDefinitionKeyLike(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionResourceName(String)}
   */
  @Test
  public void testProcessDefinitionResourceName() {
    // Arrange and Act
    ProcessDefinitionQueryImpl actualProcessDefinitionResourceNameResult = processDefinitionQueryImpl
        .processDefinitionResourceName("Resource Name");

    // Assert
    assertEquals("Resource Name", processDefinitionQueryImpl.getResourceName());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionResourceNameResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionResourceName(String)}
   */
  @Test
  public void testProcessDefinitionResourceName2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processDefinitionQueryImpl.processDefinitionResourceName(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionResourceNameLike(String)}
   */
  @Test
  public void testProcessDefinitionResourceNameLike() {
    // Arrange and Act
    ProcessDefinitionQueryImpl actualProcessDefinitionResourceNameLikeResult = processDefinitionQueryImpl
        .processDefinitionResourceNameLike("Resource Name Like");

    // Assert
    assertEquals("Resource Name Like", processDefinitionQueryImpl.getResourceNameLike());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionResourceNameLikeResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionResourceNameLike(String)}
   */
  @Test
  public void testProcessDefinitionResourceNameLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processDefinitionQueryImpl.processDefinitionResourceNameLike(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  public void testProcessDefinitionVersion() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQueryImpl actualProcessDefinitionVersionResult = processDefinitionQueryImpl
        .processDefinitionVersion(1);

    // Assert
    assertEquals(1, processDefinitionQueryImpl.getVersion().intValue());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionVersionResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  public void testProcessDefinitionVersion2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersion(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  public void testProcessDefinitionVersion3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersion(0));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThan(Integer)}
   */
  @Test
  public void testProcessDefinitionVersionGreaterThan() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQuery actualProcessDefinitionVersionGreaterThanResult = processDefinitionQueryImpl
        .processDefinitionVersionGreaterThan(1);

    // Assert
    assertEquals(1, processDefinitionQueryImpl.getVersionGt().intValue());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionVersionGreaterThanResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThan(Integer)}
   */
  @Test
  public void testProcessDefinitionVersionGreaterThan2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersionGreaterThan(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThan(Integer)}
   */
  @Test
  public void testProcessDefinitionVersionGreaterThan3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersionGreaterThan(0));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThanOrEquals(Integer)}
   */
  @Test
  public void testProcessDefinitionVersionGreaterThanOrEquals() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQuery actualProcessDefinitionVersionGreaterThanOrEqualsResult = processDefinitionQueryImpl
        .processDefinitionVersionGreaterThanOrEquals(1);

    // Assert
    assertEquals(1, processDefinitionQueryImpl.getVersionGte().intValue());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionVersionGreaterThanOrEqualsResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThanOrEquals(Integer)}
   */
  @Test
  public void testProcessDefinitionVersionGreaterThanOrEquals2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersionGreaterThanOrEquals(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThanOrEquals(Integer)}
   */
  @Test
  public void testProcessDefinitionVersionGreaterThanOrEquals3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersionGreaterThanOrEquals(0));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThan(Integer)}
   */
  @Test
  public void testProcessDefinitionVersionLowerThan() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQuery actualProcessDefinitionVersionLowerThanResult = processDefinitionQueryImpl
        .processDefinitionVersionLowerThan(1);

    // Assert
    assertEquals(1, processDefinitionQueryImpl.getVersionLt().intValue());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionVersionLowerThanResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThan(Integer)}
   */
  @Test
  public void testProcessDefinitionVersionLowerThan2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersionLowerThan(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThan(Integer)}
   */
  @Test
  public void testProcessDefinitionVersionLowerThan3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersionLowerThan(0));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThanOrEquals(Integer)}
   */
  @Test
  public void testProcessDefinitionVersionLowerThanOrEquals() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQuery actualProcessDefinitionVersionLowerThanOrEqualsResult = processDefinitionQueryImpl
        .processDefinitionVersionLowerThanOrEquals(1);

    // Assert
    assertEquals(1, processDefinitionQueryImpl.getVersionLte().intValue());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionVersionLowerThanOrEqualsResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThanOrEquals(Integer)}
   */
  @Test
  public void testProcessDefinitionVersionLowerThanOrEquals2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersionLowerThanOrEquals(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThanOrEquals(Integer)}
   */
  @Test
  public void testProcessDefinitionVersionLowerThanOrEquals3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersionLowerThanOrEquals(0));
  }

  /**
   * Method under test: {@link ProcessDefinitionQueryImpl#checkVersion(Integer)}
   */
  @Test
  public void testCheckVersion() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ProcessDefinitionQueryImpl()).checkVersion(null));
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ProcessDefinitionQueryImpl()).checkVersion(0));
  }

  /**
   * Method under test: {@link ProcessDefinitionQueryImpl#latestVersion()}
   */
  @Test
  public void testLatestVersion() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQueryImpl actualLatestVersionResult = processDefinitionQueryImpl.latestVersion();

    // Assert
    assertTrue(processDefinitionQueryImpl.isLatest());
    assertSame(processDefinitionQueryImpl, actualLatestVersionResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionTenantId(String)}
   */
  @Test
  public void testProcessDefinitionTenantId() {
    // Arrange and Act
    ProcessDefinitionQuery actualProcessDefinitionTenantIdResult = processDefinitionQueryImpl
        .processDefinitionTenantId("42");

    // Assert
    assertEquals("42", processDefinitionQueryImpl.getTenantId());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionTenantIdResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionTenantId(String)}
   */
  @Test
  public void testProcessDefinitionTenantId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processDefinitionQueryImpl.processDefinitionTenantId(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionTenantIdLike(String)}
   */
  @Test
  public void testProcessDefinitionTenantIdLike() {
    // Arrange and Act
    ProcessDefinitionQuery actualProcessDefinitionTenantIdLikeResult = processDefinitionQueryImpl
        .processDefinitionTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", processDefinitionQueryImpl.getTenantIdLike());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionTenantIdLikeResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#processDefinitionTenantIdLike(String)}
   */
  @Test
  public void testProcessDefinitionTenantIdLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processDefinitionQueryImpl.processDefinitionTenantIdLike(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#messageEventSubscription(String)}
   */
  @Test
  public void testMessageEventSubscription() {
    // Arrange and Act
    ProcessDefinitionQuery actualMessageEventSubscriptionResult = processDefinitionQueryImpl
        .messageEventSubscription("Message Name");

    // Assert
    assertEquals("Message Name", processDefinitionQueryImpl.getEventSubscriptionName());
    assertEquals("message", processDefinitionQueryImpl.getEventSubscriptionType());
    assertSame(processDefinitionQueryImpl, actualMessageEventSubscriptionResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#messageEventSubscription(String)}
   */
  @Test
  public void testMessageEventSubscription2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processDefinitionQueryImpl.messageEventSubscription(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#messageEventSubscriptionName(String)}
   */
  @Test
  public void testMessageEventSubscriptionName() {
    // Arrange and Act
    ProcessDefinitionQuery actualMessageEventSubscriptionNameResult = processDefinitionQueryImpl
        .messageEventSubscriptionName("Message Name");

    // Assert
    assertEquals("Message Name", processDefinitionQueryImpl.getEventSubscriptionName());
    assertEquals("message", processDefinitionQueryImpl.getEventSubscriptionType());
    assertSame(processDefinitionQueryImpl, actualMessageEventSubscriptionNameResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#messageEventSubscriptionName(String)}
   */
  @Test
  public void testMessageEventSubscriptionName2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processDefinitionQueryImpl.messageEventSubscriptionName(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#eventSubscription(String, String)}
   */
  @Test
  public void testEventSubscription() {
    // Arrange and Act
    ProcessDefinitionQuery actualEventSubscriptionResult = processDefinitionQueryImpl.eventSubscription("Event Type",
        "Event Name");

    // Assert
    assertEquals("Event Name", processDefinitionQueryImpl.getEventSubscriptionName());
    assertEquals("Event Type", processDefinitionQueryImpl.getEventSubscriptionType());
    assertSame(processDefinitionQueryImpl, actualEventSubscriptionResult);
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#eventSubscription(String, String)}
   */
  @Test
  public void testEventSubscription2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> processDefinitionQueryImpl.eventSubscription(null, "Event Name"));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#eventSubscription(String, String)}
   */
  @Test
  public void testEventSubscription3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processDefinitionQueryImpl.eventSubscription("Event Type", null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#getAuthorizationGroups()}
   */
  @Test
  public void testGetAuthorizationGroups() {
    // Arrange, Act and Assert
    assertNull((new ProcessDefinitionQueryImpl()).getAuthorizationGroups());
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#getAuthorizationGroups()}
   */
  @Test
  public void testGetAuthorizationGroups2() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();
    ArrayList<String> groupIds = new ArrayList<>();
    processDefinitionQueryImpl.startableByGroups(groupIds);
    processDefinitionQueryImpl.startableByUser("foo");

    // Act
    List<String> actualAuthorizationGroups = processDefinitionQueryImpl.getAuthorizationGroups();

    // Assert
    assertTrue(actualAuthorizationGroups.isEmpty());
    assertSame(groupIds, actualAuthorizationGroups);
  }

  /**
   * Method under test: {@link ProcessDefinitionQueryImpl#startableByUser(String)}
   */
  @Test
  public void testStartableByUser() {
    // Arrange and Act
    ProcessDefinitionQueryImpl actualStartableByUserResult = processDefinitionQueryImpl.startableByUser("42");

    // Assert
    assertEquals("42", processDefinitionQueryImpl.getAuthorizationUserId());
    assertSame(processDefinitionQueryImpl, actualStartableByUserResult);
  }

  /**
   * Method under test: {@link ProcessDefinitionQueryImpl#startableByUser(String)}
   */
  @Test
  public void testStartableByUser2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> processDefinitionQueryImpl.startableByUser(null));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#ProcessDefinitionQueryImpl()}
   */
  @Test
  public void testNewProcessDefinitionQueryImpl() {
    // Arrange and Act
    ProcessDefinitionQueryImpl actualProcessDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualProcessDefinitionQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualProcessDefinitionQueryImpl.getOrderByColumns());
    assertNull(actualProcessDefinitionQueryImpl.getVersion());
    assertNull(actualProcessDefinitionQueryImpl.getVersionGt());
    assertNull(actualProcessDefinitionQueryImpl.getVersionGte());
    assertNull(actualProcessDefinitionQueryImpl.getVersionLt());
    assertNull(actualProcessDefinitionQueryImpl.getVersionLte());
    assertNull(actualProcessDefinitionQueryImpl.getDatabaseType());
    assertNull(actualProcessDefinitionQueryImpl.getAuthorizationUserId());
    assertNull(actualProcessDefinitionQueryImpl.getCategory());
    assertNull(actualProcessDefinitionQueryImpl.getCategoryLike());
    assertNull(actualProcessDefinitionQueryImpl.getCategoryNotEquals());
    assertNull(actualProcessDefinitionQueryImpl.getDeploymentId());
    assertNull(actualProcessDefinitionQueryImpl.getEventSubscriptionName());
    assertNull(actualProcessDefinitionQueryImpl.getEventSubscriptionType());
    assertNull(actualProcessDefinitionQueryImpl.getId());
    assertNull(actualProcessDefinitionQueryImpl.getIdOrKey());
    assertNull(actualProcessDefinitionQueryImpl.getKey());
    assertNull(actualProcessDefinitionQueryImpl.getKeyLike());
    assertNull(actualProcessDefinitionQueryImpl.getName());
    assertNull(actualProcessDefinitionQueryImpl.getNameLike());
    assertNull(actualProcessDefinitionQueryImpl.getProcDefId());
    assertNull(actualProcessDefinitionQueryImpl.getResourceName());
    assertNull(actualProcessDefinitionQueryImpl.getResourceNameLike());
    assertNull(actualProcessDefinitionQueryImpl.getTenantId());
    assertNull(actualProcessDefinitionQueryImpl.getTenantIdLike());
    assertNull(actualProcessDefinitionQueryImpl.orderBy);
    assertNull(actualProcessDefinitionQueryImpl.getAuthorizationGroups());
    assertNull(actualProcessDefinitionQueryImpl.getDeploymentIds());
    assertNull(actualProcessDefinitionQueryImpl.getIds());
    assertNull(actualProcessDefinitionQueryImpl.getKeys());
    assertNull(actualProcessDefinitionQueryImpl.nullHandlingOnOrder);
    assertNull(actualProcessDefinitionQueryImpl.resultType);
    assertNull(actualProcessDefinitionQueryImpl.commandContext);
    assertNull(actualProcessDefinitionQueryImpl.commandExecutor);
    assertNull(actualProcessDefinitionQueryImpl.getSuspensionState());
    assertNull(actualProcessDefinitionQueryImpl.orderProperty);
    assertEquals(0, actualProcessDefinitionQueryImpl.getFirstResult());
    assertEquals(1, actualProcessDefinitionQueryImpl.getFirstRow());
    assertFalse(actualProcessDefinitionQueryImpl.isLatest());
    assertFalse(actualProcessDefinitionQueryImpl.isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, actualProcessDefinitionQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualProcessDefinitionQueryImpl.getMaxResults());
    assertSame(actualProcessDefinitionQueryImpl, actualProcessDefinitionQueryImpl.getParameter());
  }
}
