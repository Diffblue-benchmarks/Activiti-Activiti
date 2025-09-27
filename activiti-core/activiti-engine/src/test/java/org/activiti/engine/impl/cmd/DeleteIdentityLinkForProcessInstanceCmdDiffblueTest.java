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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.ExecutionDataManager;
import org.activiti.engine.impl.persistence.entity.data.IdentityLinkDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class DeleteIdentityLinkForProcessInstanceCmdDiffblueTest {
  /**
   * Test {@link
   * DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}.
   *
   * <p>Method under test: {@link
   * DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String)"
  })
  public void testNewDeleteIdentityLinkForProcessInstanceCmd() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeleteIdentityLinkForProcessInstanceCmd(null, null, null, null));
  }

  /**
   * Test {@link
   * DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}.
   *
   * <p>Method under test: {@link
   * DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String)"
  })
  public void testNewDeleteIdentityLinkForProcessInstanceCmd2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeleteIdentityLinkForProcessInstanceCmd("42", null, null, null));
  }

  /**
   * Test {@link
   * DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}.
   *
   * <p>Method under test: {@link
   * DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String)"
  })
  public void testNewDeleteIdentityLinkForProcessInstanceCmd3() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeleteIdentityLinkForProcessInstanceCmd("42", null, null, "Type"));
  }

  /**
   * Test {@link
   * DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@link DeleteIdentityLinkForProcessInstanceCmd#groupId} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String)"
  })
  public void testNewDeleteIdentityLinkForProcessInstanceCmd_whenNull_thenReturnGroupIdIsNull() {
    // Arrange and Act
    DeleteIdentityLinkForProcessInstanceCmd actualDeleteIdentityLinkForProcessInstanceCmd =
        new DeleteIdentityLinkForProcessInstanceCmd("42", "42", null, "Type");
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinkByProcessInstanceUserGroupAndType(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(
            new JtaProcessEngineConfiguration(), identityLinkDataManager);
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    when(commandContext.getIdentityLinkEntityManager()).thenReturn(identityLinkEntityManagerImpl);
    when(commandContext.getExecutionEntityManager()).thenReturn(executionEntityManagerImpl);
    Void actualExecuteResult =
        actualDeleteIdentityLinkForProcessInstanceCmd.execute(commandContext);

    // Assert
    verify(commandContext).getExecutionEntityManager();
    verify(commandContext).getHistoryManager();
    verify(commandContext).getIdentityLinkEntityManager();
    verify(executionDataManager).findById("42");
    verify(identityLinkDataManager)
        .findIdentityLinkByProcessInstanceUserGroupAndType(null, "42", null, "Type");
    assertEquals("42", actualDeleteIdentityLinkForProcessInstanceCmd.processInstanceId);
    assertEquals("42", actualDeleteIdentityLinkForProcessInstanceCmd.userId);
    assertEquals("Type", actualDeleteIdentityLinkForProcessInstanceCmd.type);
    assertNull(actualDeleteIdentityLinkForProcessInstanceCmd.groupId);
    assertNull(actualExecuteResult);
  }

  /**
   * Test {@link
   * DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@link DeleteIdentityLinkForProcessInstanceCmd#userId} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String)"
  })
  public void testNewDeleteIdentityLinkForProcessInstanceCmd_whenNull_thenReturnUserIdIsNull() {
    // Arrange and Act
    DeleteIdentityLinkForProcessInstanceCmd actualDeleteIdentityLinkForProcessInstanceCmd =
        new DeleteIdentityLinkForProcessInstanceCmd("42", null, "42", "Type");

    // Assert
    assertEquals("42", actualDeleteIdentityLinkForProcessInstanceCmd.groupId);
    assertEquals("42", actualDeleteIdentityLinkForProcessInstanceCmd.processInstanceId);
    assertEquals("Type", actualDeleteIdentityLinkForProcessInstanceCmd.type);
    assertNull(actualDeleteIdentityLinkForProcessInstanceCmd.userId);
  }

  /**
   * Test {@link
   * DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}.
   *
   * <ul>
   *   <li>When {@code Type}.
   *   <li>Then return {@link DeleteIdentityLinkForProcessInstanceCmd#groupId} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String)"
  })
  public void testNewDeleteIdentityLinkForProcessInstanceCmd_whenType_thenReturnGroupIdIs42() {
    // Arrange and Act
    DeleteIdentityLinkForProcessInstanceCmd actualDeleteIdentityLinkForProcessInstanceCmd =
        new DeleteIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type");

    // Assert
    assertEquals("42", actualDeleteIdentityLinkForProcessInstanceCmd.groupId);
    assertEquals("42", actualDeleteIdentityLinkForProcessInstanceCmd.processInstanceId);
    assertEquals("42", actualDeleteIdentityLinkForProcessInstanceCmd.userId);
    assertEquals("Type", actualDeleteIdentityLinkForProcessInstanceCmd.type);
  }

  /**
   * Test {@link DeleteIdentityLinkForProcessInstanceCmd#validateParams(String, String, String,
   * String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkForProcessInstanceCmd#validateParams(String,
   * String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessInstanceCmd.validateParams(String, String, String, String)"
  })
  public void testValidateParams_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DeleteIdentityLinkForProcessInstanceCmd deleteIdentityLinkForProcessInstanceCmd =
        new DeleteIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> deleteIdentityLinkForProcessInstanceCmd.validateParams(null, null, null, null));
  }

  /**
   * Test {@link DeleteIdentityLinkForProcessInstanceCmd#validateParams(String, String, String,
   * String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkForProcessInstanceCmd#validateParams(String,
   * String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessInstanceCmd.validateParams(String, String, String, String)"
  })
  public void testValidateParams_whenNull_thenThrowActivitiIllegalArgumentException2() {
    // Arrange
    DeleteIdentityLinkForProcessInstanceCmd deleteIdentityLinkForProcessInstanceCmd =
        new DeleteIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> deleteIdentityLinkForProcessInstanceCmd.validateParams(null, null, "42", null));
  }

  /**
   * Test {@link DeleteIdentityLinkForProcessInstanceCmd#validateParams(String, String, String,
   * String)}.
   *
   * <ul>
   *   <li>When {@code Type}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkForProcessInstanceCmd#validateParams(String,
   * String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessInstanceCmd.validateParams(String, String, String, String)"
  })
  public void testValidateParams_whenType_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DeleteIdentityLinkForProcessInstanceCmd deleteIdentityLinkForProcessInstanceCmd =
        new DeleteIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> deleteIdentityLinkForProcessInstanceCmd.validateParams(null, null, "42", "Type"));
  }
}
