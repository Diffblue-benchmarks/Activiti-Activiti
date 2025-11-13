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
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.query.QueryProperty;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class DeploymentQueryImplDiffblueTest {
  /**
   * Test {@link DeploymentQueryImpl#DeploymentQueryImpl()}.
   *
   * <p>Method under test: {@link DeploymentQueryImpl#DeploymentQueryImpl()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeploymentQueryImpl.<init>()"})
  public void testNewDeploymentQueryImpl() {
    // Arrange and Act
    DeploymentQueryImpl actualDeploymentQueryImpl = new DeploymentQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualDeploymentQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualDeploymentQueryImpl.getOrderByColumns());
    assertNull(actualDeploymentQueryImpl.getDatabaseType());
    assertNull(actualDeploymentQueryImpl.getCategory());
    assertNull(actualDeploymentQueryImpl.getCategoryNotEquals());
    assertNull(actualDeploymentQueryImpl.getDeploymentId());
    assertNull(actualDeploymentQueryImpl.getName());
    assertNull(actualDeploymentQueryImpl.getNameLike());
    assertNull(actualDeploymentQueryImpl.getProcessDefinitionKey());
    assertNull(actualDeploymentQueryImpl.getProcessDefinitionKeyLike());
    assertNull(actualDeploymentQueryImpl.getTenantId());
    assertNull(actualDeploymentQueryImpl.getTenantIdLike());
    assertNull(actualDeploymentQueryImpl.orderBy);
    assertNull(actualDeploymentQueryImpl.categoryLike);
    assertNull(actualDeploymentQueryImpl.key);
    assertNull(actualDeploymentQueryImpl.keyLike);
    assertNull(actualDeploymentQueryImpl.nullHandlingOnOrder);
    assertNull(actualDeploymentQueryImpl.resultType);
    assertNull(actualDeploymentQueryImpl.commandContext);
    assertNull(actualDeploymentQueryImpl.commandExecutor);
    assertNull(actualDeploymentQueryImpl.orderProperty);
    assertEquals(0, actualDeploymentQueryImpl.getFirstResult());
    assertEquals(1, actualDeploymentQueryImpl.getFirstRow());
    assertFalse(actualDeploymentQueryImpl.isLatestVersion());
    assertFalse(actualDeploymentQueryImpl.isWithoutTenantId());
    assertFalse(actualDeploymentQueryImpl.latest);
    assertEquals(Integer.MAX_VALUE, actualDeploymentQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualDeploymentQueryImpl.getMaxResults());
    Object actualParameter = actualDeploymentQueryImpl.getParameter();
    assertSame(actualDeploymentQueryImpl, actualParameter);
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link DeploymentQueryImpl#DeploymentQueryImpl()} DeploymentId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentId(String)"})
  public void testDeploymentId_when42_thenDeploymentQueryImplDeploymentIdIs42() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl();

    // Act
    DeploymentQueryImpl actualDeploymentIdResult = deploymentQueryImpl.deploymentId("42");

    // Assert
    assertEquals("42", deploymentQueryImpl.getDeploymentId());
    assertSame(deploymentQueryImpl, actualDeploymentIdResult);
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentId(String)"})
  public void testDeploymentId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> new DeploymentQueryImpl().deploymentId(null));
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentName(String)}.
   *
   * <ul>
   *   <li>Then {@link DeploymentQueryImpl#DeploymentQueryImpl()} Name is {@code Deployment Name}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentName(String)"})
  public void testDeploymentName_thenDeploymentQueryImplNameIsDeploymentName() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl();

    // Act
    DeploymentQueryImpl actualDeploymentNameResult =
        deploymentQueryImpl.deploymentName("Deployment Name");

    // Assert
    assertEquals("Deployment Name", deploymentQueryImpl.getName());
    assertSame(deploymentQueryImpl, actualDeploymentNameResult);
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentName(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentName(String)"})
  public void testDeploymentName_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeploymentQueryImpl().deploymentName(null));
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentNameLike(String)}.
   *
   * <ul>
   *   <li>When {@code Name Like}.
   *   <li>Then {@link DeploymentQueryImpl#DeploymentQueryImpl()} NameLike is {@code Name Like}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentNameLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentNameLike(String)"})
  public void testDeploymentNameLike_whenNameLike_thenDeploymentQueryImplNameLikeIsNameLike() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl();

    // Act
    DeploymentQueryImpl actualDeploymentNameLikeResult =
        deploymentQueryImpl.deploymentNameLike("Name Like");

    // Assert
    assertEquals("Name Like", deploymentQueryImpl.getNameLike());
    assertSame(deploymentQueryImpl, actualDeploymentNameLikeResult);
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentNameLike(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentNameLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentNameLike(String)"})
  public void testDeploymentNameLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeploymentQueryImpl().deploymentNameLike(null));
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentCategory(String)}.
   *
   * <ul>
   *   <li>Then {@link DeploymentQueryImpl#DeploymentQueryImpl()} Category is {@code Deployment
   *       Category}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentCategory(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentCategory(String)"})
  public void testDeploymentCategory_thenDeploymentQueryImplCategoryIsDeploymentCategory() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl();

    // Act
    DeploymentQueryImpl actualDeploymentCategoryResult =
        deploymentQueryImpl.deploymentCategory("Deployment Category");

    // Assert
    assertEquals("Deployment Category", deploymentQueryImpl.getCategory());
    assertSame(deploymentQueryImpl, actualDeploymentCategoryResult);
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentCategory(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentCategory(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentCategory(String)"})
  public void testDeploymentCategory_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeploymentQueryImpl().deploymentCategory(null));
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentCategoryLike(String)}.
   *
   * <ul>
   *   <li>Then {@link DeploymentQueryImpl#DeploymentQueryImpl()} {@link
   *       DeploymentQueryImpl#categoryLike} is {@code Category Like}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentCategoryLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentCategoryLike(String)"})
  public void testDeploymentCategoryLike_thenDeploymentQueryImplCategoryLikeIsCategoryLike() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl();

    // Act
    DeploymentQueryImpl actualDeploymentCategoryLikeResult =
        deploymentQueryImpl.deploymentCategoryLike("Category Like");

    // Assert
    assertEquals("Category Like", deploymentQueryImpl.categoryLike);
    assertSame(deploymentQueryImpl, actualDeploymentCategoryLikeResult);
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentCategoryLike(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentCategoryLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentCategoryLike(String)"})
  public void testDeploymentCategoryLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeploymentQueryImpl().deploymentCategoryLike(null));
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentCategoryNotEquals(String)}.
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentCategoryNotEquals(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentCategoryNotEquals(String)"})
  public void testDeploymentCategoryNotEquals() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl();

    // Act
    DeploymentQueryImpl actualDeploymentCategoryNotEqualsResult =
        deploymentQueryImpl.deploymentCategoryNotEquals("Deployment Category Not Equals");

    // Assert
    assertEquals("Deployment Category Not Equals", deploymentQueryImpl.getCategoryNotEquals());
    assertSame(deploymentQueryImpl, actualDeploymentCategoryNotEqualsResult);
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentCategoryNotEquals(String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentCategoryNotEquals(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentCategoryNotEquals(String)"})
  public void testDeploymentCategoryNotEquals_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeploymentQueryImpl().deploymentCategoryNotEquals(null));
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentKey(String)}.
   *
   * <ul>
   *   <li>When {@code Deployment Key}.
   *   <li>Then {@link DeploymentQueryImpl#DeploymentQueryImpl()} {@link DeploymentQueryImpl#key} is
   *       {@code Deployment Key}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentKey(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentKey(String)"})
  public void testDeploymentKey_whenDeploymentKey_thenDeploymentQueryImplKeyIsDeploymentKey() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl();

    // Act
    DeploymentQueryImpl actualDeploymentKeyResult =
        deploymentQueryImpl.deploymentKey("Deployment Key");

    // Assert
    assertEquals("Deployment Key", deploymentQueryImpl.key);
    assertSame(deploymentQueryImpl, actualDeploymentKeyResult);
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentKey(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentKey(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentKey(String)"})
  public void testDeploymentKey_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeploymentQueryImpl().deploymentKey(null));
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentKeyLike(String)}.
   *
   * <ul>
   *   <li>Then {@link DeploymentQueryImpl#DeploymentQueryImpl()} {@link
   *       DeploymentQueryImpl#keyLike} is {@code Deployment Key Like}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentKeyLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentKeyLike(String)"})
  public void testDeploymentKeyLike_thenDeploymentQueryImplKeyLikeIsDeploymentKeyLike() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl();

    // Act
    DeploymentQueryImpl actualDeploymentKeyLikeResult =
        deploymentQueryImpl.deploymentKeyLike("Deployment Key Like");

    // Assert
    assertEquals("Deployment Key Like", deploymentQueryImpl.keyLike);
    assertSame(deploymentQueryImpl, actualDeploymentKeyLikeResult);
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentKeyLike(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentKeyLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentKeyLike(String)"})
  public void testDeploymentKeyLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeploymentQueryImpl().deploymentKeyLike(null));
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentTenantId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link DeploymentQueryImpl#DeploymentQueryImpl()} TenantId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentTenantId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentTenantId(String)"})
  public void testDeploymentTenantId_when42_thenDeploymentQueryImplTenantIdIs42() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl();

    // Act
    DeploymentQueryImpl actualDeploymentTenantIdResult =
        deploymentQueryImpl.deploymentTenantId("42");

    // Assert
    assertEquals("42", deploymentQueryImpl.getTenantId());
    assertSame(deploymentQueryImpl, actualDeploymentTenantIdResult);
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentTenantId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentTenantId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentTenantId(String)"})
  public void testDeploymentTenantId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeploymentQueryImpl().deploymentTenantId(null));
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentTenantIdLike(String)}.
   *
   * <ul>
   *   <li>Then {@link DeploymentQueryImpl#DeploymentQueryImpl()} TenantIdLike is {@code Tenant Id
   *       Like}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentTenantIdLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentTenantIdLike(String)"})
  public void testDeploymentTenantIdLike_thenDeploymentQueryImplTenantIdLikeIsTenantIdLike() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl();

    // Act
    DeploymentQueryImpl actualDeploymentTenantIdLikeResult =
        deploymentQueryImpl.deploymentTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", deploymentQueryImpl.getTenantIdLike());
    assertSame(deploymentQueryImpl, actualDeploymentTenantIdLikeResult);
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentTenantIdLike(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentTenantIdLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentTenantIdLike(String)"})
  public void testDeploymentTenantIdLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeploymentQueryImpl().deploymentTenantIdLike(null));
  }

  /**
   * Test {@link DeploymentQueryImpl#deploymentWithoutTenantId()}.
   *
   * <p>Method under test: {@link DeploymentQueryImpl#deploymentWithoutTenantId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.deploymentWithoutTenantId()"})
  public void testDeploymentWithoutTenantId() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl();

    // Act
    DeploymentQueryImpl actualDeploymentWithoutTenantIdResult =
        deploymentQueryImpl.deploymentWithoutTenantId();

    // Assert
    assertTrue(deploymentQueryImpl.isWithoutTenantId());
    assertSame(deploymentQueryImpl, actualDeploymentWithoutTenantIdResult);
  }

  /**
   * Test {@link DeploymentQueryImpl#processDefinitionKey(String)}.
   *
   * <ul>
   *   <li>Then {@link DeploymentQueryImpl#DeploymentQueryImpl()} ProcessDefinitionKey is {@code
   *       Key}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#processDefinitionKey(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.processDefinitionKey(String)"})
  public void testProcessDefinitionKey_thenDeploymentQueryImplProcessDefinitionKeyIsKey() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl();

    // Act
    DeploymentQueryImpl actualProcessDefinitionKeyResult =
        deploymentQueryImpl.processDefinitionKey("Key");

    // Assert
    assertEquals("Key", deploymentQueryImpl.getProcessDefinitionKey());
    assertSame(deploymentQueryImpl, actualProcessDefinitionKeyResult);
  }

  /**
   * Test {@link DeploymentQueryImpl#processDefinitionKey(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#processDefinitionKey(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.processDefinitionKey(String)"})
  public void testProcessDefinitionKey_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeploymentQueryImpl().processDefinitionKey(null));
  }

  /**
   * Test {@link DeploymentQueryImpl#processDefinitionKeyLike(String)}.
   *
   * <p>Method under test: {@link DeploymentQueryImpl#processDefinitionKeyLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.processDefinitionKeyLike(String)"})
  public void testProcessDefinitionKeyLike() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl();

    // Act
    DeploymentQueryImpl actualProcessDefinitionKeyLikeResult =
        deploymentQueryImpl.processDefinitionKeyLike("Key Like");

    // Assert
    assertEquals("Key Like", deploymentQueryImpl.getProcessDefinitionKeyLike());
    assertSame(deploymentQueryImpl, actualProcessDefinitionKeyLikeResult);
  }

  /**
   * Test {@link DeploymentQueryImpl#processDefinitionKeyLike(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#processDefinitionKeyLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.processDefinitionKeyLike(String)"})
  public void testProcessDefinitionKeyLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeploymentQueryImpl().processDefinitionKeyLike(null));
  }

  /**
   * Test {@link DeploymentQueryImpl#latest()}.
   *
   * <ul>
   *   <li>Given {@link DeploymentQueryImpl#DeploymentQueryImpl()}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#latest()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.latest()"})
  public void testLatest_givenDeploymentQueryImpl_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new DeploymentQueryImpl().latest());
  }

  /**
   * Test {@link DeploymentQueryImpl#latest()}.
   *
   * <ul>
   *   <li>Then {@link DeploymentQueryImpl#DeploymentQueryImpl()} {@link
   *       DeploymentQueryImpl#latest}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#latest()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DeploymentQueryImpl DeploymentQueryImpl.latest()"})
  public void testLatest_thenDeploymentQueryImplLatest() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl();
    deploymentQueryImpl.deploymentKey("latest can only be used together with a deployment key");

    // Act
    DeploymentQueryImpl actualLatestResult = deploymentQueryImpl.latest();

    // Assert
    assertTrue(deploymentQueryImpl.latest);
    assertSame(deploymentQueryImpl, actualLatestResult);
  }

  /**
   * Test {@link DeploymentQueryImpl#executeCount(CommandContext)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#executeCount(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long DeploymentQueryImpl.executeCount(CommandContext)"})
  public void testExecuteCount_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl();
    deploymentQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> deploymentQueryImpl.executeCount(null));
  }

  /**
   * Test {@link DeploymentQueryImpl#executeList(CommandContext, Page)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentQueryImpl#executeList(CommandContext, Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List DeploymentQueryImpl.executeList(CommandContext, Page)"})
  public void testExecuteList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl();
    deploymentQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> deploymentQueryImpl.executeList(null, new Page(1, 3)));
  }
}
