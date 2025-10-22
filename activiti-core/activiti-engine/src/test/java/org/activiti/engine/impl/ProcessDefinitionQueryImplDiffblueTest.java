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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ProcessDefinitionQueryImplDiffblueTest {
  /**
   * Test {@link ProcessDefinitionQueryImpl#ProcessDefinitionQueryImpl()}.
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#ProcessDefinitionQueryImpl()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessDefinitionQueryImpl.<init>()"})
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

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionId(String)}.
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionId(String)"})
  public void testProcessDefinitionId() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQueryImpl actualProcessDefinitionIdResult = processDefinitionQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", processDefinitionQueryImpl.getId());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionCategory(String)}.
   * <ul>
   *   <li>Then {@link ProcessDefinitionQueryImpl#ProcessDefinitionQueryImpl()} Category is {@code Category}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionCategory(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionCategory(String)"})
  public void testProcessDefinitionCategory_thenProcessDefinitionQueryImplCategoryIsCategory() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQueryImpl actualProcessDefinitionCategoryResult = processDefinitionQueryImpl
        .processDefinitionCategory("Category");

    // Assert
    assertEquals("Category", processDefinitionQueryImpl.getCategory());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionCategoryResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionCategory(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionCategory(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionCategory(String)"})
  public void testProcessDefinitionCategory_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionCategory(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionCategoryLike(String)}.
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionCategoryLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionCategoryLike(String)"})
  public void testProcessDefinitionCategoryLike() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQueryImpl actualProcessDefinitionCategoryLikeResult = processDefinitionQueryImpl
        .processDefinitionCategoryLike("Category Like");

    // Assert
    assertEquals("Category Like", processDefinitionQueryImpl.getCategoryLike());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionCategoryLikeResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionCategoryLike(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionCategoryLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionCategoryLike(String)"})
  public void testProcessDefinitionCategoryLike_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionCategoryLike(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionCategoryNotEquals(String)}.
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionCategoryNotEquals(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionCategoryNotEquals(String)"})
  public void testProcessDefinitionCategoryNotEquals() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQueryImpl actualProcessDefinitionCategoryNotEqualsResult = processDefinitionQueryImpl
        .processDefinitionCategoryNotEquals("Category Not Equals");

    // Assert
    assertEquals("Category Not Equals", processDefinitionQueryImpl.getCategoryNotEquals());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionCategoryNotEqualsResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionCategoryNotEquals(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionCategoryNotEquals(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionCategoryNotEquals(String)"})
  public void testProcessDefinitionCategoryNotEquals_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionCategoryNotEquals(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionName(String)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   *   <li>Then {@link ProcessDefinitionQueryImpl#ProcessDefinitionQueryImpl()} Name is {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionName(String)"})
  public void testProcessDefinitionName_whenName_thenProcessDefinitionQueryImplNameIsName() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQueryImpl actualProcessDefinitionNameResult = processDefinitionQueryImpl
        .processDefinitionName("Name");

    // Assert
    assertEquals("Name", processDefinitionQueryImpl.getName());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionNameResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionName(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionName(String)"})
  public void testProcessDefinitionName_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionName(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionNameLike(String)}.
   * <ul>
   *   <li>Then {@link ProcessDefinitionQueryImpl#ProcessDefinitionQueryImpl()} NameLike is {@code Name Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionNameLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionNameLike(String)"})
  public void testProcessDefinitionNameLike_thenProcessDefinitionQueryImplNameLikeIsNameLike() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQueryImpl actualProcessDefinitionNameLikeResult = processDefinitionQueryImpl
        .processDefinitionNameLike("Name Like");

    // Assert
    assertEquals("Name Like", processDefinitionQueryImpl.getNameLike());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionNameLikeResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionNameLike(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionNameLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionNameLike(String)"})
  public void testProcessDefinitionNameLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionNameLike(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#deploymentId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link ProcessDefinitionQueryImpl#ProcessDefinitionQueryImpl()} DeploymentId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#deploymentId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.deploymentId(String)"})
  public void testDeploymentId_when42_thenProcessDefinitionQueryImplDeploymentIdIs42() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQueryImpl actualDeploymentIdResult = processDefinitionQueryImpl.deploymentId("42");

    // Assert
    assertEquals("42", processDefinitionQueryImpl.getDeploymentId());
    assertSame(processDefinitionQueryImpl, actualDeploymentIdResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#deploymentId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#deploymentId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.deploymentId(String)"})
  public void testDeploymentId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ProcessDefinitionQueryImpl()).deploymentId(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#deploymentIds(Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#deploymentIds(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.deploymentIds(Set)"})
  public void testDeploymentIds_given42_whenHashSetAdd42() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    HashSet<String> deploymentIds = new HashSet<>();
    deploymentIds.add("42");
    deploymentIds.add("foo");

    // Act
    ProcessDefinitionQueryImpl actualDeploymentIdsResult = processDefinitionQueryImpl.deploymentIds(deploymentIds);

    // Assert
    assertSame(deploymentIds, processDefinitionQueryImpl.getDeploymentIds());
    assertSame(processDefinitionQueryImpl, actualDeploymentIdsResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#deploymentIds(Set)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#deploymentIds(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.deploymentIds(Set)"})
  public void testDeploymentIds_givenFoo_whenHashSetAddFoo() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    HashSet<String> deploymentIds = new HashSet<>();
    deploymentIds.add("foo");

    // Act
    ProcessDefinitionQueryImpl actualDeploymentIdsResult = processDefinitionQueryImpl.deploymentIds(deploymentIds);

    // Assert
    assertSame(deploymentIds, processDefinitionQueryImpl.getDeploymentIds());
    assertSame(processDefinitionQueryImpl, actualDeploymentIdsResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#deploymentIds(Set)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#deploymentIds(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.deploymentIds(Set)"})
  public void testDeploymentIds_whenHashSet() {
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
   * Test {@link ProcessDefinitionQueryImpl#deploymentIds(Set)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#deploymentIds(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.deploymentIds(Set)"})
  public void testDeploymentIds_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ProcessDefinitionQueryImpl()).deploymentIds(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionKey(String)}.
   * <ul>
   *   <li>When {@code Key}.</li>
   *   <li>Then {@link ProcessDefinitionQueryImpl#ProcessDefinitionQueryImpl()} Key is {@code Key}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionKey(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionKey(String)"})
  public void testProcessDefinitionKey_whenKey_thenProcessDefinitionQueryImplKeyIsKey() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQueryImpl actualProcessDefinitionKeyResult = processDefinitionQueryImpl
        .processDefinitionKey("Key");

    // Assert
    assertEquals("Key", processDefinitionQueryImpl.getKey());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionKeyResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionKey(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionKey(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionKey(String)"})
  public void testProcessDefinitionKey_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionKey(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionIdOrKey(String)}.
   * <ul>
   *   <li>Then {@link ProcessDefinitionQueryImpl#ProcessDefinitionQueryImpl()} IdOrKey is {@code Id Or Key}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionIdOrKey(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionIdOrKey(String)"})
  public void testProcessDefinitionIdOrKey_thenProcessDefinitionQueryImplIdOrKeyIsIdOrKey() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQuery actualProcessDefinitionIdOrKeyResult = processDefinitionQueryImpl
        .processDefinitionIdOrKey("Id Or Key");

    // Assert
    assertEquals("Id Or Key", processDefinitionQueryImpl.getIdOrKey());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionIdOrKeyResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionIdOrKey(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionIdOrKey(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionIdOrKey(String)"})
  public void testProcessDefinitionIdOrKey_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionIdOrKey(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionKeys(Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionKeys(Set)"})
  public void testProcessDefinitionKeys_given42_whenHashSetAdd42() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    HashSet<String> keys = new HashSet<>();
    keys.add("42");
    keys.add("foo");

    // Act
    ProcessDefinitionQueryImpl actualProcessDefinitionKeysResult = processDefinitionQueryImpl
        .processDefinitionKeys(keys);

    // Assert
    assertSame(keys, processDefinitionQueryImpl.getKeys());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionKeysResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionKeys(Set)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionKeys(Set)"})
  public void testProcessDefinitionKeys_givenFoo_whenHashSetAddFoo() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    HashSet<String> keys = new HashSet<>();
    keys.add("foo");

    // Act
    ProcessDefinitionQueryImpl actualProcessDefinitionKeysResult = processDefinitionQueryImpl
        .processDefinitionKeys(keys);

    // Assert
    assertSame(keys, processDefinitionQueryImpl.getKeys());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionKeysResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionKeys(Set)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionKeys(Set)"})
  public void testProcessDefinitionKeys_whenHashSet() {
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
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionKeys(Set)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionKeys(Set)"})
  public void testProcessDefinitionKeys_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionKeys(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionKeyLike(String)}.
   * <ul>
   *   <li>Then {@link ProcessDefinitionQueryImpl#ProcessDefinitionQueryImpl()} KeyLike is {@code Key Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionKeyLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionKeyLike(String)"})
  public void testProcessDefinitionKeyLike_thenProcessDefinitionQueryImplKeyLikeIsKeyLike() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQueryImpl actualProcessDefinitionKeyLikeResult = processDefinitionQueryImpl
        .processDefinitionKeyLike("Key Like");

    // Assert
    assertEquals("Key Like", processDefinitionQueryImpl.getKeyLike());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionKeyLikeResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionKeyLike(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionKeyLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionKeyLike(String)"})
  public void testProcessDefinitionKeyLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionKeyLike(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionResourceName(String)}.
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionResourceName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionResourceName(String)"})
  public void testProcessDefinitionResourceName() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQueryImpl actualProcessDefinitionResourceNameResult = processDefinitionQueryImpl
        .processDefinitionResourceName("Resource Name");

    // Assert
    assertEquals("Resource Name", processDefinitionQueryImpl.getResourceName());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionResourceNameResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionResourceName(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionResourceName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionResourceName(String)"})
  public void testProcessDefinitionResourceName_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionResourceName(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionResourceNameLike(String)}.
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionResourceNameLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionResourceNameLike(String)"})
  public void testProcessDefinitionResourceNameLike() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQueryImpl actualProcessDefinitionResourceNameLikeResult = processDefinitionQueryImpl
        .processDefinitionResourceNameLike("Resource Name Like");

    // Assert
    assertEquals("Resource Name Like", processDefinitionQueryImpl.getResourceNameLike());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionResourceNameLikeResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionResourceNameLike(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionResourceNameLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionResourceNameLike(String)"})
  public void testProcessDefinitionResourceNameLike_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionResourceNameLike(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionVersion(Integer)}.
   * <ul>
   *   <li>Then {@link ProcessDefinitionQueryImpl#ProcessDefinitionQueryImpl()} Version intValue is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionVersion(Integer)"})
  public void testProcessDefinitionVersion_thenProcessDefinitionQueryImplVersionIntValueIsOne() {
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
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionVersion(Integer)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionVersion(Integer)"})
  public void testProcessDefinitionVersion_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersion(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionVersion(Integer)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.processDefinitionVersion(Integer)"})
  public void testProcessDefinitionVersion_whenZero_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersion(0));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThan(Integer)}.
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThan(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionVersionGreaterThan(Integer)"})
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
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThan(Integer)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThan(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionVersionGreaterThan(Integer)"})
  public void testProcessDefinitionVersionGreaterThan_whenNull() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersionGreaterThan(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThan(Integer)}.
   * <ul>
   *   <li>When zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThan(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionVersionGreaterThan(Integer)"})
  public void testProcessDefinitionVersionGreaterThan_whenZero() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersionGreaterThan(0));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThanOrEquals(Integer)}.
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThanOrEquals(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionVersionGreaterThanOrEquals(Integer)"})
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
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThanOrEquals(Integer)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThanOrEquals(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionVersionGreaterThanOrEquals(Integer)"})
  public void testProcessDefinitionVersionGreaterThanOrEquals_whenNull() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersionGreaterThanOrEquals(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThanOrEquals(Integer)}.
   * <ul>
   *   <li>When zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionVersionGreaterThanOrEquals(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionVersionGreaterThanOrEquals(Integer)"})
  public void testProcessDefinitionVersionGreaterThanOrEquals_whenZero() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersionGreaterThanOrEquals(0));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThan(Integer)}.
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThan(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionVersionLowerThan(Integer)"})
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
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThan(Integer)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThan(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionVersionLowerThan(Integer)"})
  public void testProcessDefinitionVersionLowerThan_whenNull() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersionLowerThan(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThan(Integer)}.
   * <ul>
   *   <li>When zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThan(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionVersionLowerThan(Integer)"})
  public void testProcessDefinitionVersionLowerThan_whenZero() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersionLowerThan(0));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThanOrEquals(Integer)}.
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThanOrEquals(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionVersionLowerThanOrEquals(Integer)"})
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
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThanOrEquals(Integer)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThanOrEquals(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionVersionLowerThanOrEquals(Integer)"})
  public void testProcessDefinitionVersionLowerThanOrEquals_whenNull() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersionLowerThanOrEquals(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThanOrEquals(Integer)}.
   * <ul>
   *   <li>When zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionVersionLowerThanOrEquals(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionVersionLowerThanOrEquals(Integer)"})
  public void testProcessDefinitionVersionLowerThanOrEquals_whenZero() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionVersionLowerThanOrEquals(0));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#checkVersion(Integer)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#checkVersion(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessDefinitionQueryImpl.checkVersion(Integer)"})
  public void testCheckVersion_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ProcessDefinitionQueryImpl()).checkVersion(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#checkVersion(Integer)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#checkVersion(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessDefinitionQueryImpl.checkVersion(Integer)"})
  public void testCheckVersion_whenZero_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ProcessDefinitionQueryImpl()).checkVersion(0));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#latestVersion()}.
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#latestVersion()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.latestVersion()"})
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
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionTenantId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link ProcessDefinitionQueryImpl#ProcessDefinitionQueryImpl()} TenantId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionTenantId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionTenantId(String)"})
  public void testProcessDefinitionTenantId_when42_thenProcessDefinitionQueryImplTenantIdIs42() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQuery actualProcessDefinitionTenantIdResult = processDefinitionQueryImpl
        .processDefinitionTenantId("42");

    // Assert
    assertEquals("42", processDefinitionQueryImpl.getTenantId());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionTenantIdResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionTenantId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionTenantId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionTenantId(String)"})
  public void testProcessDefinitionTenantId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionTenantId(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionTenantIdLike(String)}.
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionTenantIdLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionTenantIdLike(String)"})
  public void testProcessDefinitionTenantIdLike() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQuery actualProcessDefinitionTenantIdLikeResult = processDefinitionQueryImpl
        .processDefinitionTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", processDefinitionQueryImpl.getTenantIdLike());
    assertSame(processDefinitionQueryImpl, actualProcessDefinitionTenantIdLikeResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#processDefinitionTenantIdLike(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#processDefinitionTenantIdLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.processDefinitionTenantIdLike(String)"})
  public void testProcessDefinitionTenantIdLike_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).processDefinitionTenantIdLike(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#messageEventSubscription(String)}.
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#messageEventSubscription(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.messageEventSubscription(String)"})
  public void testMessageEventSubscription() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQuery actualMessageEventSubscriptionResult = processDefinitionQueryImpl
        .messageEventSubscription("Message Name");

    // Assert
    assertEquals("Message Name", processDefinitionQueryImpl.getEventSubscriptionName());
    assertEquals("message", processDefinitionQueryImpl.getEventSubscriptionType());
    assertSame(processDefinitionQueryImpl, actualMessageEventSubscriptionResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#messageEventSubscription(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#messageEventSubscription(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.messageEventSubscription(String)"})
  public void testMessageEventSubscription_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).messageEventSubscription(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#messageEventSubscriptionName(String)}.
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#messageEventSubscriptionName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.messageEventSubscriptionName(String)"})
  public void testMessageEventSubscriptionName() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQuery actualMessageEventSubscriptionNameResult = processDefinitionQueryImpl
        .messageEventSubscriptionName("Message Name");

    // Assert
    assertEquals("Message Name", processDefinitionQueryImpl.getEventSubscriptionName());
    assertEquals("message", processDefinitionQueryImpl.getEventSubscriptionType());
    assertSame(processDefinitionQueryImpl, actualMessageEventSubscriptionNameResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#messageEventSubscriptionName(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#messageEventSubscriptionName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.messageEventSubscriptionName(String)"})
  public void testMessageEventSubscriptionName_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).messageEventSubscriptionName(null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#eventSubscription(String, String)}.
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#eventSubscription(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.eventSubscription(String, String)"})
  public void testEventSubscription() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQuery actualEventSubscriptionResult = processDefinitionQueryImpl.eventSubscription("Event Type",
        "Event Name");

    // Assert
    assertEquals("Event Name", processDefinitionQueryImpl.getEventSubscriptionName());
    assertEquals("Event Type", processDefinitionQueryImpl.getEventSubscriptionType());
    assertSame(processDefinitionQueryImpl, actualEventSubscriptionResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#eventSubscription(String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#eventSubscription(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.eventSubscription(String, String)"})
  public void testEventSubscription_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new ProcessDefinitionQueryImpl()).eventSubscription(null, "Event Name"));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#eventSubscription(String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#eventSubscription(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQuery ProcessDefinitionQueryImpl.eventSubscription(String, String)"})
  public void testEventSubscription_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).eventSubscription(null, null));
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#getAuthorizationGroups()}.
   * <ul>
   *   <li>Given {@link ProcessDefinitionQueryImpl#ProcessDefinitionQueryImpl()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#getAuthorizationGroups()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.List ProcessDefinitionQueryImpl.getAuthorizationGroups()"})
  public void testGetAuthorizationGroups_givenProcessDefinitionQueryImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new ProcessDefinitionQueryImpl()).getAuthorizationGroups());
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#getAuthorizationGroups()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#getAuthorizationGroups()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.List ProcessDefinitionQueryImpl.getAuthorizationGroups()"})
  public void testGetAuthorizationGroups_thenReturnEmpty() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();
    processDefinitionQueryImpl.startableByGroups(new ArrayList<>());
    processDefinitionQueryImpl.startableByUser("foo");

    // Act and Assert
    assertTrue(processDefinitionQueryImpl.getAuthorizationGroups().isEmpty());
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#startableByUser(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link ProcessDefinitionQueryImpl#ProcessDefinitionQueryImpl()} AuthorizationUserId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#startableByUser(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.startableByUser(String)"})
  public void testStartableByUser_when42_thenProcessDefinitionQueryImplAuthorizationUserIdIs42() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Act
    ProcessDefinitionQueryImpl actualStartableByUserResult = processDefinitionQueryImpl.startableByUser("42");

    // Assert
    assertEquals("42", processDefinitionQueryImpl.getAuthorizationUserId());
    assertSame(processDefinitionQueryImpl, actualStartableByUserResult);
  }

  /**
   * Test {@link ProcessDefinitionQueryImpl#startableByUser(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDefinitionQueryImpl#startableByUser(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionQueryImpl ProcessDefinitionQueryImpl.startableByUser(String)"})
  public void testStartableByUser_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessDefinitionQueryImpl()).startableByUser(null));
  }
}
