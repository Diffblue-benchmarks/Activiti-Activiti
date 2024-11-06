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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.ByteArrayDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisByteArrayDataManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ByteArrayEntityManagerImplDiffblueTest {
  @Mock
  private ByteArrayDataManager byteArrayDataManager;

  @InjectMocks
  private ByteArrayEntityManagerImpl byteArrayEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ByteArrayEntityManagerImpl#ByteArrayEntityManagerImpl(ProcessEngineConfigurationImpl, ByteArrayDataManager)}
   *   <li>
   * {@link ByteArrayEntityManagerImpl#setByteArrayDataManager(ByteArrayDataManager)}
   *   <li>{@link ByteArrayEntityManagerImpl#getByteArrayDataManager()}
   *   <li>{@link ByteArrayEntityManagerImpl#getDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ByteArrayEntityManagerImpl actualByteArrayEntityManagerImpl = new ByteArrayEntityManagerImpl(
        processEngineConfiguration, new MybatisByteArrayDataManager(new JtaProcessEngineConfiguration()));
    MybatisByteArrayDataManager byteArrayDataManager = new MybatisByteArrayDataManager(
        new JtaProcessEngineConfiguration());
    actualByteArrayEntityManagerImpl.setByteArrayDataManager(byteArrayDataManager);
    ByteArrayDataManager actualByteArrayDataManager = actualByteArrayEntityManagerImpl.getByteArrayDataManager();

    // Assert that nothing has changed
    assertSame(byteArrayDataManager, actualByteArrayDataManager);
    assertSame(byteArrayDataManager, actualByteArrayEntityManagerImpl.getDataManager());
  }

  /**
   * Test {@link ByteArrayEntityManagerImpl#findAll()}.
   * <ul>
   *   <li>Given {@link ByteArrayDataManager} {@link ByteArrayDataManager#findAll()}
   * return {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ByteArrayEntityManagerImpl#findAll()}
   */
  @Test
  public void testFindAll_givenByteArrayDataManagerFindAllReturnArrayList_thenReturnEmpty() {
    // Arrange
    ByteArrayDataManager byteArrayDataManager = mock(ByteArrayDataManager.class);
    when(byteArrayDataManager.findAll()).thenReturn(new ArrayList<>());

    // Act
    List<ByteArrayEntity> actualFindAllResult = (new ByteArrayEntityManagerImpl(new JtaProcessEngineConfiguration(),
        byteArrayDataManager)).findAll();

    // Assert
    verify(byteArrayDataManager).findAll();
    assertTrue(actualFindAllResult.isEmpty());
  }

  /**
   * Test {@link ByteArrayEntityManagerImpl#deleteByteArrayById(String)}.
   * <p>
   * Method under test:
   * {@link ByteArrayEntityManagerImpl#deleteByteArrayById(String)}
   */
  @Test
  public void testDeleteByteArrayById() {
    // Arrange
    doNothing().when(byteArrayDataManager).deleteByteArrayNoRevisionCheck(Mockito.<String>any());

    // Act
    byteArrayEntityManagerImpl.deleteByteArrayById("42");

    // Assert
    verify(byteArrayDataManager).deleteByteArrayNoRevisionCheck(eq("42"));
  }
}
