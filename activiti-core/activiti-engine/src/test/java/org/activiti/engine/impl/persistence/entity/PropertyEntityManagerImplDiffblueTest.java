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
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.PropertyDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisPropertyDataManager;
import org.junit.Test;

public class PropertyEntityManagerImplDiffblueTest {
  /**
   * Method under test: {@link PropertyEntityManagerImpl#findAll()}
   */
  @Test
  public void testFindAll() {
    // Arrange
    PropertyDataManager propertyDataManager = mock(PropertyDataManager.class);
    ArrayList<PropertyEntity> propertyEntityList = new ArrayList<>();
    when(propertyDataManager.findAll()).thenReturn(propertyEntityList);

    // Act
    List<PropertyEntity> actualFindAllResult = (new PropertyEntityManagerImpl(new JtaProcessEngineConfiguration(),
        propertyDataManager)).findAll();

    // Assert
    verify(propertyDataManager).findAll();
    assertTrue(actualFindAllResult.isEmpty());
    assertSame(propertyEntityList, actualFindAllResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link PropertyEntityManagerImpl#PropertyEntityManagerImpl(ProcessEngineConfigurationImpl, PropertyDataManager)}
   *   <li>{@link PropertyEntityManagerImpl#getDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisPropertyDataManager propertyDataManager = new MybatisPropertyDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(propertyDataManager,
        (new PropertyEntityManagerImpl(processEngineConfiguration, propertyDataManager)).getDataManager());
  }
}
