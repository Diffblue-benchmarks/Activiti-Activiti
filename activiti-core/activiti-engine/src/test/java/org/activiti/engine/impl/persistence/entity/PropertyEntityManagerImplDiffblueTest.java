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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.PropertyDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisPropertyDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PropertyEntityManagerImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       PropertyEntityManagerImpl#PropertyEntityManagerImpl(ProcessEngineConfigurationImpl,
   *       PropertyDataManager)}
   *   <li>{@link PropertyEntityManagerImpl#getDataManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void PropertyEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, PropertyDataManager)",
    "org.activiti.engine.impl.persistence.entity.data.DataManager PropertyEntityManagerImpl.getDataManager()"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisPropertyDataManager propertyDataManager =
        new MybatisPropertyDataManager(new JtaProcessEngineConfiguration());

    // Act
    PropertyEntityManagerImpl actualPropertyEntityManagerImpl =
        new PropertyEntityManagerImpl(processEngineConfiguration, propertyDataManager);

    // Assert
    assertSame(propertyDataManager, actualPropertyEntityManagerImpl.getDataManager());
  }

  /**
   * Test {@link PropertyEntityManagerImpl#findAll()}.
   *
   * <ul>
   *   <li>Given {@link PropertyDataManager} {@link PropertyDataManager#findAll()} return {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link PropertyEntityManagerImpl#findAll()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List PropertyEntityManagerImpl.findAll()"})
  public void testFindAll_givenPropertyDataManagerFindAllReturnArrayList_thenReturnEmpty() {
    // Arrange
    PropertyDataManager propertyDataManager = mock(PropertyDataManager.class);
    when(propertyDataManager.findAll()).thenReturn(new ArrayList<>());
    PropertyEntityManagerImpl propertyEntityManagerImpl =
        new PropertyEntityManagerImpl(new JtaProcessEngineConfiguration(), propertyDataManager);

    // Act
    List<PropertyEntity> actualFindAllResult = propertyEntityManagerImpl.findAll();

    // Assert
    verify(propertyDataManager).findAll();
    assertTrue(actualFindAllResult.isEmpty());
  }
}
