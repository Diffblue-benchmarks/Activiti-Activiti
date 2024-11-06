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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.ResourceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisResourceDataManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResourceEntityManagerImplDiffblueTest {
  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  @Mock
  private ResourceDataManager resourceDataManager;

  @InjectMocks
  private ResourceEntityManagerImpl resourceEntityManagerImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ResourceEntityManagerImpl#ResourceEntityManagerImpl(ProcessEngineConfigurationImpl, ResourceDataManager)}
   *   <li>
   * {@link ResourceEntityManagerImpl#setResourceDataManager(ResourceDataManager)}
   *   <li>{@link ResourceEntityManagerImpl#getDataManager()}
   *   <li>{@link ResourceEntityManagerImpl#getResourceDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ResourceEntityManagerImpl actualResourceEntityManagerImpl = new ResourceEntityManagerImpl(
        processEngineConfiguration, new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));
    MybatisResourceDataManager resourceDataManager = new MybatisResourceDataManager(
        new JtaProcessEngineConfiguration());
    actualResourceEntityManagerImpl.setResourceDataManager(resourceDataManager);
    DataManager<ResourceEntity> actualDataManager = actualResourceEntityManagerImpl.getDataManager();

    // Assert that nothing has changed
    assertSame(resourceDataManager, actualDataManager);
    assertSame(resourceDataManager, actualResourceEntityManagerImpl.getResourceDataManager());
  }

  /**
   * Test {@link ResourceEntityManagerImpl#deleteResourcesByDeploymentId(String)}.
   * <p>
   * Method under test:
   * {@link ResourceEntityManagerImpl#deleteResourcesByDeploymentId(String)}
   */
  @Test
  public void testDeleteResourcesByDeploymentId() {
    // Arrange
    doNothing().when(resourceDataManager).deleteResourcesByDeploymentId(Mockito.<String>any());

    // Act
    resourceEntityManagerImpl.deleteResourcesByDeploymentId("42");

    // Assert
    verify(resourceDataManager).deleteResourcesByDeploymentId(eq("42"));
  }

  /**
   * Test
   * {@link ResourceEntityManagerImpl#findResourceByDeploymentIdAndResourceName(String, String)}.
   * <p>
   * Method under test:
   * {@link ResourceEntityManagerImpl#findResourceByDeploymentIdAndResourceName(String, String)}
   */
  @Test
  public void testFindResourceByDeploymentIdAndResourceName() {
    // Arrange
    ResourceEntityImpl resourceEntityImpl = new ResourceEntityImpl();
    when(resourceDataManager.findResourceByDeploymentIdAndResourceName(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(resourceEntityImpl);

    // Act
    ResourceEntity actualFindResourceByDeploymentIdAndResourceNameResult = resourceEntityManagerImpl
        .findResourceByDeploymentIdAndResourceName("42", "Resource Name");

    // Assert
    verify(resourceDataManager).findResourceByDeploymentIdAndResourceName(eq("42"), eq("Resource Name"));
    assertSame(resourceEntityImpl, actualFindResourceByDeploymentIdAndResourceNameResult);
  }

  /**
   * Test {@link ResourceEntityManagerImpl#findResourcesByDeploymentId(String)}.
   * <p>
   * Method under test:
   * {@link ResourceEntityManagerImpl#findResourcesByDeploymentId(String)}
   */
  @Test
  public void testFindResourcesByDeploymentId() {
    // Arrange
    when(resourceDataManager.findResourcesByDeploymentId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<ResourceEntity> actualFindResourcesByDeploymentIdResult = resourceEntityManagerImpl
        .findResourcesByDeploymentId("42");

    // Assert
    verify(resourceDataManager).findResourcesByDeploymentId(eq("42"));
    assertTrue(actualFindResourcesByDeploymentIdResult.isEmpty());
  }
}
