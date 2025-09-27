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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.IdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisIdentityLinkDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IdentityLinkEntityManagerImplDiffblueTest {
  @Mock private IdentityLinkDataManager identityLinkDataManager;

  @InjectMocks private IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl;

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       IdentityLinkEntityManagerImpl#IdentityLinkEntityManagerImpl(ProcessEngineConfigurationImpl,
   *       IdentityLinkDataManager)}
   *   <li>{@link IdentityLinkEntityManagerImpl#setIdentityLinkDataManager(IdentityLinkDataManager)}
   *   <li>{@link IdentityLinkEntityManagerImpl#getDataManager()}
   *   <li>{@link IdentityLinkEntityManagerImpl#getIdentityLinkDataManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void IdentityLinkEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, IdentityLinkDataManager)",
    "DataManager IdentityLinkEntityManagerImpl.getDataManager()",
    "IdentityLinkDataManager IdentityLinkEntityManagerImpl.getIdentityLinkDataManager()",
    "void IdentityLinkEntityManagerImpl.setIdentityLinkDataManager(IdentityLinkDataManager)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    IdentityLinkEntityManagerImpl actualIdentityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(
            processEngineConfiguration,
            new MybatisIdentityLinkDataManager(new JtaProcessEngineConfiguration()));
    MybatisIdentityLinkDataManager identityLinkDataManager =
        new MybatisIdentityLinkDataManager(new JtaProcessEngineConfiguration());
    actualIdentityLinkEntityManagerImpl.setIdentityLinkDataManager(identityLinkDataManager);
    DataManager<IdentityLinkEntity> actualDataManager =
        actualIdentityLinkEntityManagerImpl.getDataManager();

    // Assert
    assertSame(identityLinkDataManager, actualDataManager);
    assertSame(
        identityLinkDataManager, actualIdentityLinkEntityManagerImpl.getIdentityLinkDataManager());
  }

  /**
   * Test {@link IdentityLinkEntityManagerImpl#findIdentityLinksByTaskId(String)}.
   *
   * <p>Method under test: {@link IdentityLinkEntityManagerImpl#findIdentityLinksByTaskId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List IdentityLinkEntityManagerImpl.findIdentityLinksByTaskId(String)"})
  public void testFindIdentityLinksByTaskId() {
    // Arrange
    when(identityLinkDataManager.findIdentityLinksByTaskId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinksByTaskIdResult =
        identityLinkEntityManagerImpl.findIdentityLinksByTaskId("42");

    // Assert
    verify(identityLinkDataManager).findIdentityLinksByTaskId("42");
    assertTrue(actualFindIdentityLinksByTaskIdResult.isEmpty());
  }

  /**
   * Test {@link IdentityLinkEntityManagerImpl#findIdentityLinksByProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link
   * IdentityLinkEntityManagerImpl#findIdentityLinksByProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List IdentityLinkEntityManagerImpl.findIdentityLinksByProcessInstanceId(String)"
  })
  public void testFindIdentityLinksByProcessInstanceId() {
    // Arrange
    when(identityLinkDataManager.findIdentityLinksByProcessInstanceId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinksByProcessInstanceIdResult =
        identityLinkEntityManagerImpl.findIdentityLinksByProcessInstanceId("42");

    // Assert
    verify(identityLinkDataManager).findIdentityLinksByProcessInstanceId("42");
    assertTrue(actualFindIdentityLinksByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link IdentityLinkEntityManagerImpl#findIdentityLinksByProcessDefinitionId(String)}.
   *
   * <p>Method under test: {@link
   * IdentityLinkEntityManagerImpl#findIdentityLinksByProcessDefinitionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List IdentityLinkEntityManagerImpl.findIdentityLinksByProcessDefinitionId(String)"
  })
  public void testFindIdentityLinksByProcessDefinitionId() {
    // Arrange
    when(identityLinkDataManager.findIdentityLinksByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinksByProcessDefinitionIdResult =
        identityLinkEntityManagerImpl.findIdentityLinksByProcessDefinitionId("42");

    // Assert
    verify(identityLinkDataManager).findIdentityLinksByProcessDefinitionId("42");
    assertTrue(actualFindIdentityLinksByProcessDefinitionIdResult.isEmpty());
  }

  /**
   * Test {@link IdentityLinkEntityManagerImpl#findIdentityLinkByTaskUserGroupAndType(String,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * IdentityLinkEntityManagerImpl#findIdentityLinkByTaskUserGroupAndType(String, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List IdentityLinkEntityManagerImpl.findIdentityLinkByTaskUserGroupAndType(String, String, String, String)"
  })
  public void testFindIdentityLinkByTaskUserGroupAndType() {
    // Arrange
    when(identityLinkDataManager.findIdentityLinkByTaskUserGroupAndType(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinkByTaskUserGroupAndTypeResult =
        identityLinkEntityManagerImpl.findIdentityLinkByTaskUserGroupAndType(
            "42", "42", "42", "Type");

    // Assert
    verify(identityLinkDataManager)
        .findIdentityLinkByTaskUserGroupAndType("42", "42", "42", "Type");
    assertTrue(actualFindIdentityLinkByTaskUserGroupAndTypeResult.isEmpty());
  }

  /**
   * Test {@link
   * IdentityLinkEntityManagerImpl#findIdentityLinkByProcessInstanceUserGroupAndType(String, String,
   * String, String)}.
   *
   * <p>Method under test: {@link
   * IdentityLinkEntityManagerImpl#findIdentityLinkByProcessInstanceUserGroupAndType(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List IdentityLinkEntityManagerImpl.findIdentityLinkByProcessInstanceUserGroupAndType(String, String, String, String)"
  })
  public void testFindIdentityLinkByProcessInstanceUserGroupAndType() {
    // Arrange
    when(identityLinkDataManager.findIdentityLinkByProcessInstanceUserGroupAndType(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinkByProcessInstanceUserGroupAndTypeResult =
        identityLinkEntityManagerImpl.findIdentityLinkByProcessInstanceUserGroupAndType(
            "42", "42", "42", "Type");

    // Assert
    verify(identityLinkDataManager)
        .findIdentityLinkByProcessInstanceUserGroupAndType("42", "42", "42", "Type");
    assertTrue(actualFindIdentityLinkByProcessInstanceUserGroupAndTypeResult.isEmpty());
  }

  /**
   * Test {@link
   * IdentityLinkEntityManagerImpl#findIdentityLinkByProcessDefinitionUserAndGroup(String, String,
   * String)}.
   *
   * <p>Method under test: {@link
   * IdentityLinkEntityManagerImpl#findIdentityLinkByProcessDefinitionUserAndGroup(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List IdentityLinkEntityManagerImpl.findIdentityLinkByProcessDefinitionUserAndGroup(String, String, String)"
  })
  public void testFindIdentityLinkByProcessDefinitionUserAndGroup() {
    // Arrange
    when(identityLinkDataManager.findIdentityLinkByProcessDefinitionUserAndGroup(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<IdentityLinkEntity> actualFindIdentityLinkByProcessDefinitionUserAndGroupResult =
        identityLinkEntityManagerImpl.findIdentityLinkByProcessDefinitionUserAndGroup(
            "42", "42", "42");

    // Assert
    verify(identityLinkDataManager)
        .findIdentityLinkByProcessDefinitionUserAndGroup("42", "42", "42");
    assertTrue(actualFindIdentityLinkByProcessDefinitionUserAndGroupResult.isEmpty());
  }

  /**
   * Test {@link IdentityLinkEntityManagerImpl#deleteIdentityLinksByProcDef(String)}.
   *
   * <p>Method under test: {@link
   * IdentityLinkEntityManagerImpl#deleteIdentityLinksByProcDef(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void IdentityLinkEntityManagerImpl.deleteIdentityLinksByProcDef(String)"})
  public void testDeleteIdentityLinksByProcDef() {
    // Arrange
    doNothing().when(identityLinkDataManager).deleteIdentityLinksByProcDef(Mockito.<String>any());

    // Act
    identityLinkEntityManagerImpl.deleteIdentityLinksByProcDef("42");

    // Assert
    verify(identityLinkDataManager).deleteIdentityLinksByProcDef("42");
  }
}
