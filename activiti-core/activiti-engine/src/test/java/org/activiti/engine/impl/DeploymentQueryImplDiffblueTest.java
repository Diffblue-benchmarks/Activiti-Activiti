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
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeploymentQueryImplDiffblueTest {
  @InjectMocks
  private DeploymentQueryImpl deploymentQueryImpl;

  /**
   * Method under test: {@link DeploymentQueryImpl#deploymentId(String)}
   */
  @Test
  public void testDeploymentId() {
    // Arrange and Act
    DeploymentQueryImpl actualDeploymentIdResult = deploymentQueryImpl.deploymentId("42");

    // Assert
    assertEquals("42", deploymentQueryImpl.getDeploymentId());
    assertSame(deploymentQueryImpl, actualDeploymentIdResult);
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#deploymentId(String)}
   */
  @Test
  public void testDeploymentId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deploymentQueryImpl.deploymentId(null));
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#deploymentName(String)}
   */
  @Test
  public void testDeploymentName() {
    // Arrange and Act
    DeploymentQueryImpl actualDeploymentNameResult = deploymentQueryImpl.deploymentName("Deployment Name");

    // Assert
    assertEquals("Deployment Name", deploymentQueryImpl.getName());
    assertSame(deploymentQueryImpl, actualDeploymentNameResult);
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#deploymentName(String)}
   */
  @Test
  public void testDeploymentName2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deploymentQueryImpl.deploymentName(null));
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#deploymentNameLike(String)}
   */
  @Test
  public void testDeploymentNameLike() {
    // Arrange and Act
    DeploymentQueryImpl actualDeploymentNameLikeResult = deploymentQueryImpl.deploymentNameLike("Name Like");

    // Assert
    assertEquals("Name Like", deploymentQueryImpl.getNameLike());
    assertSame(deploymentQueryImpl, actualDeploymentNameLikeResult);
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#deploymentNameLike(String)}
   */
  @Test
  public void testDeploymentNameLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deploymentQueryImpl.deploymentNameLike(null));
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#deploymentCategory(String)}
   */
  @Test
  public void testDeploymentCategory() {
    // Arrange and Act
    DeploymentQueryImpl actualDeploymentCategoryResult = deploymentQueryImpl.deploymentCategory("Deployment Category");

    // Assert
    assertEquals("Deployment Category", deploymentQueryImpl.getCategory());
    assertSame(deploymentQueryImpl, actualDeploymentCategoryResult);
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#deploymentCategory(String)}
   */
  @Test
  public void testDeploymentCategory2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deploymentQueryImpl.deploymentCategory(null));
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#deploymentCategoryLike(String)}
   */
  @Test
  public void testDeploymentCategoryLike() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deploymentQueryImpl.deploymentCategoryLike(null));
  }

  /**
   * Method under test:
   * {@link DeploymentQueryImpl#deploymentCategoryNotEquals(String)}
   */
  @Test
  public void testDeploymentCategoryNotEquals() {
    // Arrange and Act
    DeploymentQueryImpl actualDeploymentCategoryNotEqualsResult = deploymentQueryImpl
        .deploymentCategoryNotEquals("Deployment Category Not Equals");

    // Assert
    assertEquals("Deployment Category Not Equals", deploymentQueryImpl.getCategoryNotEquals());
    assertSame(deploymentQueryImpl, actualDeploymentCategoryNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link DeploymentQueryImpl#deploymentCategoryNotEquals(String)}
   */
  @Test
  public void testDeploymentCategoryNotEquals2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deploymentQueryImpl.deploymentCategoryNotEquals(null));
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#deploymentKey(String)}
   */
  @Test
  public void testDeploymentKey() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deploymentQueryImpl.deploymentKey(null));
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#deploymentKeyLike(String)}
   */
  @Test
  public void testDeploymentKeyLike() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deploymentQueryImpl.deploymentKeyLike(null));
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#deploymentTenantId(String)}
   */
  @Test
  public void testDeploymentTenantId() {
    // Arrange and Act
    DeploymentQueryImpl actualDeploymentTenantIdResult = deploymentQueryImpl.deploymentTenantId("42");

    // Assert
    assertEquals("42", deploymentQueryImpl.getTenantId());
    assertSame(deploymentQueryImpl, actualDeploymentTenantIdResult);
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#deploymentTenantId(String)}
   */
  @Test
  public void testDeploymentTenantId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deploymentQueryImpl.deploymentTenantId(null));
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#deploymentTenantIdLike(String)}
   */
  @Test
  public void testDeploymentTenantIdLike() {
    // Arrange and Act
    DeploymentQueryImpl actualDeploymentTenantIdLikeResult = deploymentQueryImpl
        .deploymentTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", deploymentQueryImpl.getTenantIdLike());
    assertSame(deploymentQueryImpl, actualDeploymentTenantIdLikeResult);
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#deploymentTenantIdLike(String)}
   */
  @Test
  public void testDeploymentTenantIdLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deploymentQueryImpl.deploymentTenantIdLike(null));
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#deploymentWithoutTenantId()}
   */
  @Test
  public void testDeploymentWithoutTenantId() {
    // Arrange
    DeploymentQueryImpl deploymentQueryImpl = new DeploymentQueryImpl();

    // Act
    DeploymentQueryImpl actualDeploymentWithoutTenantIdResult = deploymentQueryImpl.deploymentWithoutTenantId();

    // Assert
    assertTrue(deploymentQueryImpl.isWithoutTenantId());
    assertSame(deploymentQueryImpl, actualDeploymentWithoutTenantIdResult);
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#processDefinitionKey(String)}
   */
  @Test
  public void testProcessDefinitionKey() {
    // Arrange and Act
    DeploymentQueryImpl actualProcessDefinitionKeyResult = deploymentQueryImpl.processDefinitionKey("Key");

    // Assert
    assertEquals("Key", deploymentQueryImpl.getProcessDefinitionKey());
    assertSame(deploymentQueryImpl, actualProcessDefinitionKeyResult);
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#processDefinitionKey(String)}
   */
  @Test
  public void testProcessDefinitionKey2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deploymentQueryImpl.processDefinitionKey(null));
  }

  /**
   * Method under test:
   * {@link DeploymentQueryImpl#processDefinitionKeyLike(String)}
   */
  @Test
  public void testProcessDefinitionKeyLike() {
    // Arrange and Act
    DeploymentQueryImpl actualProcessDefinitionKeyLikeResult = deploymentQueryImpl.processDefinitionKeyLike("Key Like");

    // Assert
    assertEquals("Key Like", deploymentQueryImpl.getProcessDefinitionKeyLike());
    assertSame(deploymentQueryImpl, actualProcessDefinitionKeyLikeResult);
  }

  /**
   * Method under test:
   * {@link DeploymentQueryImpl#processDefinitionKeyLike(String)}
   */
  @Test
  public void testProcessDefinitionKeyLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> deploymentQueryImpl.processDefinitionKeyLike(null));
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#latest()}
   */
  @Test
  public void testLatest() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new DeploymentQueryImpl()).latest());
  }

  /**
   * Method under test: {@link DeploymentQueryImpl#DeploymentQueryImpl()}
   */
  @Test
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
    assertSame(actualDeploymentQueryImpl, actualDeploymentQueryImpl.getParameter());
  }
}
