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
   * Test {@link ModelQueryImpl#ModelQueryImpl()}.
   * <p>
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

  /**
   * Test {@link ModelQueryImpl#modelId(String)}.
   * <p>
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
   * Test {@link ModelQueryImpl#modelCategory(String)}.
   * <ul>
   *   <li>When {@code Category}.</li>
   *   <li>Then {@link ModelQueryImpl} Category is {@code Category}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelCategory(String)}
   */
  @Test
  public void testModelCategory_whenCategory_thenModelQueryImplCategoryIsCategory() {
    // Arrange and Act
    ModelQueryImpl actualModelCategoryResult = modelQueryImpl.modelCategory("Category");

    // Assert
    assertEquals("Category", modelQueryImpl.getCategory());
    assertSame(modelQueryImpl, actualModelCategoryResult);
  }

  /**
   * Test {@link ModelQueryImpl#modelCategory(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelCategory(String)}
   */
  @Test
  public void testModelCategory_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.modelCategory(null));
  }

  /**
   * Test {@link ModelQueryImpl#modelCategoryLike(String)}.
   * <ul>
   *   <li>Then {@link ModelQueryImpl} CategoryLike is {@code Category Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelCategoryLike(String)}
   */
  @Test
  public void testModelCategoryLike_thenModelQueryImplCategoryLikeIsCategoryLike() {
    // Arrange and Act
    ModelQueryImpl actualModelCategoryLikeResult = modelQueryImpl.modelCategoryLike("Category Like");

    // Assert
    assertEquals("Category Like", modelQueryImpl.getCategoryLike());
    assertSame(modelQueryImpl, actualModelCategoryLikeResult);
  }

  /**
   * Test {@link ModelQueryImpl#modelCategoryLike(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelCategoryLike(String)}
   */
  @Test
  public void testModelCategoryLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.modelCategoryLike(null));
  }

  /**
   * Test {@link ModelQueryImpl#modelCategoryNotEquals(String)}.
   * <p>
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
   * Test {@link ModelQueryImpl#modelCategoryNotEquals(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelCategoryNotEquals(String)}
   */
  @Test
  public void testModelCategoryNotEquals_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.modelCategoryNotEquals(null));
  }

  /**
   * Test {@link ModelQueryImpl#modelName(String)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   *   <li>Then {@link ModelQueryImpl} Name is {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelName(String)}
   */
  @Test
  public void testModelName_whenName_thenModelQueryImplNameIsName() {
    // Arrange and Act
    ModelQueryImpl actualModelNameResult = modelQueryImpl.modelName("Name");

    // Assert
    assertEquals("Name", modelQueryImpl.getName());
    assertSame(modelQueryImpl, actualModelNameResult);
  }

  /**
   * Test {@link ModelQueryImpl#modelName(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelName(String)}
   */
  @Test
  public void testModelName_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.modelName(null));
  }

  /**
   * Test {@link ModelQueryImpl#modelNameLike(String)}.
   * <ul>
   *   <li>When {@code Name Like}.</li>
   *   <li>Then {@link ModelQueryImpl} NameLike is {@code Name Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelNameLike(String)}
   */
  @Test
  public void testModelNameLike_whenNameLike_thenModelQueryImplNameLikeIsNameLike() {
    // Arrange and Act
    ModelQueryImpl actualModelNameLikeResult = modelQueryImpl.modelNameLike("Name Like");

    // Assert
    assertEquals("Name Like", modelQueryImpl.getNameLike());
    assertSame(modelQueryImpl, actualModelNameLikeResult);
  }

  /**
   * Test {@link ModelQueryImpl#modelNameLike(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelNameLike(String)}
   */
  @Test
  public void testModelNameLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.modelNameLike(null));
  }

  /**
   * Test {@link ModelQueryImpl#modelKey(String)}.
   * <ul>
   *   <li>When {@code Key}.</li>
   *   <li>Then {@link ModelQueryImpl} Key is {@code Key}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelKey(String)}
   */
  @Test
  public void testModelKey_whenKey_thenModelQueryImplKeyIsKey() {
    // Arrange and Act
    ModelQuery actualModelKeyResult = modelQueryImpl.modelKey("Key");

    // Assert
    assertEquals("Key", modelQueryImpl.getKey());
    assertSame(modelQueryImpl, actualModelKeyResult);
  }

  /**
   * Test {@link ModelQueryImpl#modelKey(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelKey(String)}
   */
  @Test
  public void testModelKey_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.modelKey(null));
  }

  /**
   * Test {@link ModelQueryImpl#modelVersion(Integer)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelVersion(Integer)}
   */
  @Test
  public void testModelVersion_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ModelQueryImpl()).modelVersion(null));
  }

  /**
   * Test {@link ModelQueryImpl#modelVersion(Integer)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then {@link ModelQueryImpl#ModelQueryImpl()} Version intValue is
   * one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelVersion(Integer)}
   */
  @Test
  public void testModelVersion_whenOne_thenModelQueryImplVersionIntValueIsOne() {
    // Arrange
    ModelQueryImpl modelQueryImpl = new ModelQueryImpl();

    // Act
    ModelQueryImpl actualModelVersionResult = modelQueryImpl.modelVersion(1);

    // Assert
    assertEquals(1, modelQueryImpl.getVersion().intValue());
    assertSame(modelQueryImpl, actualModelVersionResult);
  }

  /**
   * Test {@link ModelQueryImpl#modelVersion(Integer)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelVersion(Integer)}
   */
  @Test
  public void testModelVersion_whenZero_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ModelQueryImpl()).modelVersion(0));
  }

  /**
   * Test {@link ModelQueryImpl#deploymentId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link ModelQueryImpl} DeploymentId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#deploymentId(String)}
   */
  @Test
  public void testDeploymentId_when42_thenModelQueryImplDeploymentIdIs42() {
    // Arrange and Act
    ModelQuery actualDeploymentIdResult = modelQueryImpl.deploymentId("42");

    // Assert
    assertEquals("42", modelQueryImpl.getDeploymentId());
    assertSame(modelQueryImpl, actualDeploymentIdResult);
  }

  /**
   * Test {@link ModelQueryImpl#deploymentId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#deploymentId(String)}
   */
  @Test
  public void testDeploymentId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.deploymentId(null));
  }

  /**
   * Test {@link ModelQueryImpl#notDeployed()}.
   * <p>
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
   * Test {@link ModelQueryImpl#deployed()}.
   * <p>
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
   * Test {@link ModelQueryImpl#modelTenantId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link ModelQueryImpl} TenantId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelTenantId(String)}
   */
  @Test
  public void testModelTenantId_when42_thenModelQueryImplTenantIdIs42() {
    // Arrange and Act
    ModelQuery actualModelTenantIdResult = modelQueryImpl.modelTenantId("42");

    // Assert
    assertEquals("42", modelQueryImpl.getTenantId());
    assertSame(modelQueryImpl, actualModelTenantIdResult);
  }

  /**
   * Test {@link ModelQueryImpl#modelTenantId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelTenantId(String)}
   */
  @Test
  public void testModelTenantId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.modelTenantId(null));
  }

  /**
   * Test {@link ModelQueryImpl#modelTenantIdLike(String)}.
   * <ul>
   *   <li>Then {@link ModelQueryImpl} TenantIdLike is {@code Tenant Id Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelTenantIdLike(String)}
   */
  @Test
  public void testModelTenantIdLike_thenModelQueryImplTenantIdLikeIsTenantIdLike() {
    // Arrange and Act
    ModelQuery actualModelTenantIdLikeResult = modelQueryImpl.modelTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", modelQueryImpl.getTenantIdLike());
    assertSame(modelQueryImpl, actualModelTenantIdLikeResult);
  }

  /**
   * Test {@link ModelQueryImpl#modelTenantIdLike(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelQueryImpl#modelTenantIdLike(String)}
   */
  @Test
  public void testModelTenantIdLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> modelQueryImpl.modelTenantIdLike(null));
  }
}
