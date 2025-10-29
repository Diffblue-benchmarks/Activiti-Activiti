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
import org.activiti.engine.repository.ModelQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ModelQueryImplDiffblueTest {
  @InjectMocks
  private ModelQueryImpl modelQueryImpl;

  /**
   * Method under test: {@link ModelQueryImpl#modelId(String)}
   */
  @Test
  public void testModelId() {
    // Arrange and Act
    ModelQueryImpl actualModelIdResult = modelQueryImpl.modelId("42");

    // Assert
    assertEquals("42", modelQueryImpl.getId());
    assertSame(modelQueryImpl, actualModelIdResult);
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelCategory(String)}
   */
  @Test
  public void testModelCategory() {
    // Arrange and Act
    ModelQueryImpl actualModelCategoryResult = modelQueryImpl.modelCategory("Category");

    // Assert
    assertEquals("Category", modelQueryImpl.getCategory());
    assertSame(modelQueryImpl, actualModelCategoryResult);
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelCategory(String)}
   */
  @Test
  public void testModelCategory2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.modelCategory(null));
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelCategoryLike(String)}
   */
  @Test
  public void testModelCategoryLike() {
    // Arrange and Act
    ModelQueryImpl actualModelCategoryLikeResult = modelQueryImpl.modelCategoryLike("Category Like");

    // Assert
    assertEquals("Category Like", modelQueryImpl.getCategoryLike());
    assertSame(modelQueryImpl, actualModelCategoryLikeResult);
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelCategoryLike(String)}
   */
  @Test
  public void testModelCategoryLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.modelCategoryLike(null));
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelCategoryNotEquals(String)}
   */
  @Test
  public void testModelCategoryNotEquals() {
    // Arrange and Act
    ModelQueryImpl actualModelCategoryNotEqualsResult = modelQueryImpl.modelCategoryNotEquals("Category Not Equals");

    // Assert
    assertEquals("Category Not Equals", modelQueryImpl.getCategoryNotEquals());
    assertSame(modelQueryImpl, actualModelCategoryNotEqualsResult);
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelCategoryNotEquals(String)}
   */
  @Test
  public void testModelCategoryNotEquals2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.modelCategoryNotEquals(null));
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelName(String)}
   */
  @Test
  public void testModelName() {
    // Arrange and Act
    ModelQueryImpl actualModelNameResult = modelQueryImpl.modelName("Name");

    // Assert
    assertEquals("Name", modelQueryImpl.getName());
    assertSame(modelQueryImpl, actualModelNameResult);
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelName(String)}
   */
  @Test
  public void testModelName2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.modelName(null));
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelNameLike(String)}
   */
  @Test
  public void testModelNameLike() {
    // Arrange and Act
    ModelQueryImpl actualModelNameLikeResult = modelQueryImpl.modelNameLike("Name Like");

    // Assert
    assertEquals("Name Like", modelQueryImpl.getNameLike());
    assertSame(modelQueryImpl, actualModelNameLikeResult);
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelNameLike(String)}
   */
  @Test
  public void testModelNameLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.modelNameLike(null));
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelKey(String)}
   */
  @Test
  public void testModelKey() {
    // Arrange and Act
    ModelQuery actualModelKeyResult = modelQueryImpl.modelKey("Key");

    // Assert
    assertEquals("Key", modelQueryImpl.getKey());
    assertSame(modelQueryImpl, actualModelKeyResult);
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelKey(String)}
   */
  @Test
  public void testModelKey2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.modelKey(null));
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelVersion(Integer)}
   */
  @Test
  public void testModelVersion() {
    // Arrange
    ModelQueryImpl modelQueryImpl = new ModelQueryImpl();

    // Act
    ModelQueryImpl actualModelVersionResult = modelQueryImpl.modelVersion(1);

    // Assert
    assertEquals(1, modelQueryImpl.getVersion().intValue());
    assertSame(modelQueryImpl, actualModelVersionResult);
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelVersion(Integer)}
   */
  @Test
  public void testModelVersion2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ModelQueryImpl()).modelVersion(null));
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelVersion(Integer)}
   */
  @Test
  public void testModelVersion3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ModelQueryImpl()).modelVersion(0));
  }

  /**
   * Method under test: {@link ModelQueryImpl#deploymentId(String)}
   */
  @Test
  public void testDeploymentId() {
    // Arrange and Act
    ModelQuery actualDeploymentIdResult = modelQueryImpl.deploymentId("42");

    // Assert
    assertEquals("42", modelQueryImpl.getDeploymentId());
    assertSame(modelQueryImpl, actualDeploymentIdResult);
  }

  /**
   * Method under test: {@link ModelQueryImpl#deploymentId(String)}
   */
  @Test
  public void testDeploymentId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.deploymentId(null));
  }

  /**
   * Method under test: {@link ModelQueryImpl#notDeployed()}
   */
  @Test
  public void testNotDeployed() {
    // Arrange
    ModelQueryImpl modelQueryImpl = new ModelQueryImpl();

    // Act
    ModelQuery actualNotDeployedResult = modelQueryImpl.notDeployed();

    // Assert
    assertTrue(modelQueryImpl.isNotDeployed());
    assertSame(modelQueryImpl, actualNotDeployedResult);
  }

  /**
   * Method under test: {@link ModelQueryImpl#deployed()}
   */
  @Test
  public void testDeployed() {
    // Arrange
    ModelQueryImpl modelQueryImpl = new ModelQueryImpl();

    // Act
    ModelQuery actualDeployedResult = modelQueryImpl.deployed();

    // Assert
    assertTrue(modelQueryImpl.isDeployed());
    assertSame(modelQueryImpl, actualDeployedResult);
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelTenantId(String)}
   */
  @Test
  public void testModelTenantId() {
    // Arrange and Act
    ModelQuery actualModelTenantIdResult = modelQueryImpl.modelTenantId("42");

    // Assert
    assertEquals("42", modelQueryImpl.getTenantId());
    assertSame(modelQueryImpl, actualModelTenantIdResult);
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelTenantId(String)}
   */
  @Test
  public void testModelTenantId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.modelTenantId(null));
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelTenantIdLike(String)}
   */
  @Test
  public void testModelTenantIdLike() {
    // Arrange and Act
    ModelQuery actualModelTenantIdLikeResult = modelQueryImpl.modelTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", modelQueryImpl.getTenantIdLike());
    assertSame(modelQueryImpl, actualModelTenantIdLikeResult);
  }

  /**
   * Method under test: {@link ModelQueryImpl#modelTenantIdLike(String)}
   */
  @Test
  public void testModelTenantIdLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.modelTenantIdLike(null));
  }

  /**
   * Method under test: {@link ModelQueryImpl#ModelQueryImpl()}
   */
  @Test
  public void testNewModelQueryImpl() {
    // Arrange and Act
    ModelQueryImpl actualModelQueryImpl = new ModelQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualModelQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualModelQueryImpl.getOrderByColumns());
    assertNull(actualModelQueryImpl.getVersion());
    assertNull(actualModelQueryImpl.getDatabaseType());
    assertNull(actualModelQueryImpl.getCategory());
    assertNull(actualModelQueryImpl.getCategoryLike());
    assertNull(actualModelQueryImpl.getCategoryNotEquals());
    assertNull(actualModelQueryImpl.getDeploymentId());
    assertNull(actualModelQueryImpl.getId());
    assertNull(actualModelQueryImpl.getKey());
    assertNull(actualModelQueryImpl.getName());
    assertNull(actualModelQueryImpl.getNameLike());
    assertNull(actualModelQueryImpl.getTenantId());
    assertNull(actualModelQueryImpl.getTenantIdLike());
    assertNull(actualModelQueryImpl.orderBy);
    assertNull(actualModelQueryImpl.nullHandlingOnOrder);
    assertNull(actualModelQueryImpl.resultType);
    assertNull(actualModelQueryImpl.commandContext);
    assertNull(actualModelQueryImpl.commandExecutor);
    assertNull(actualModelQueryImpl.orderProperty);
    assertEquals(0, actualModelQueryImpl.getFirstResult());
    assertEquals(1, actualModelQueryImpl.getFirstRow());
    assertFalse(actualModelQueryImpl.isDeployed());
    assertFalse(actualModelQueryImpl.isLatest());
    assertFalse(actualModelQueryImpl.isNotDeployed());
    assertFalse(actualModelQueryImpl.isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, actualModelQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualModelQueryImpl.getMaxResults());
    assertSame(actualModelQueryImpl, actualModelQueryImpl.getParameter());
  }
}
