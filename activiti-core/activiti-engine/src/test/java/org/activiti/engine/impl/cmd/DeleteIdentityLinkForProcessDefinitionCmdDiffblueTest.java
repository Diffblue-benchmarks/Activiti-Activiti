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
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.IdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.ProcessDefinitionDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class DeleteIdentityLinkForProcessDefinitionCmdDiffblueTest {
  /**
   * Test {@link
   * DeleteIdentityLinkForProcessDefinitionCmd#DeleteIdentityLinkForProcessDefinitionCmd(String,
   * String, String)}.
   *
   * <p>Method under test: {@link
   * DeleteIdentityLinkForProcessDefinitionCmd#DeleteIdentityLinkForProcessDefinitionCmd(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessDefinitionCmd.<init>(String, String, String)"
  })
  public void testNewDeleteIdentityLinkForProcessDefinitionCmd() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeleteIdentityLinkForProcessDefinitionCmd("42", null, null));
  }

  /**
   * Test {@link
   * DeleteIdentityLinkForProcessDefinitionCmd#DeleteIdentityLinkForProcessDefinitionCmd(String,
   * String, String)}.
   *
   * <p>Method under test: {@link
   * DeleteIdentityLinkForProcessDefinitionCmd#DeleteIdentityLinkForProcessDefinitionCmd(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessDefinitionCmd.<init>(String, String, String)"
  })
  public void testNewDeleteIdentityLinkForProcessDefinitionCmd2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeleteIdentityLinkForProcessDefinitionCmd(null, "42", "42"));
  }

  /**
   * Test {@link
   * DeleteIdentityLinkForProcessDefinitionCmd#DeleteIdentityLinkForProcessDefinitionCmd(String,
   * String, String)}.
   *
   * <ul>
   *   <li>Then return {@link DeleteIdentityLinkForProcessDefinitionCmd#groupId} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeleteIdentityLinkForProcessDefinitionCmd#DeleteIdentityLinkForProcessDefinitionCmd(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessDefinitionCmd.<init>(String, String, String)"
  })
  public void testNewDeleteIdentityLinkForProcessDefinitionCmd_thenReturnGroupIdIsNull() {
    // Arrange and Act
    DeleteIdentityLinkForProcessDefinitionCmd actualDeleteIdentityLinkForProcessDefinitionCmd =
        new DeleteIdentityLinkForProcessDefinitionCmd("42", "42", null);
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findById(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionEntityImpl());
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    when(identityLinkDataManager.findIdentityLinkByProcessDefinitionUserAndGroup(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(
            new JtaProcessEngineConfiguration(), identityLinkDataManager);
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getIdentityLinkEntityManager()).thenReturn(identityLinkEntityManagerImpl);
    when(commandContext.getProcessDefinitionEntityManager())
        .thenReturn(processDefinitionEntityManagerImpl);
    Void actualExecuteResult =
        actualDeleteIdentityLinkForProcessDefinitionCmd.execute(commandContext);

    // Assert
    verify(commandContext).getIdentityLinkEntityManager();
    verify(commandContext).getProcessDefinitionEntityManager();
    verify(processDefinitionDataManager).findById("42");
    verify(identityLinkDataManager)
        .findIdentityLinkByProcessDefinitionUserAndGroup(null, "42", null);
    assertEquals("42", actualDeleteIdentityLinkForProcessDefinitionCmd.processDefinitionId);
    assertEquals("42", actualDeleteIdentityLinkForProcessDefinitionCmd.userId);
    assertNull(actualDeleteIdentityLinkForProcessDefinitionCmd.groupId);
    assertNull(actualExecuteResult);
  }

  /**
   * Test {@link
   * DeleteIdentityLinkForProcessDefinitionCmd#DeleteIdentityLinkForProcessDefinitionCmd(String,
   * String, String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@link DeleteIdentityLinkForProcessDefinitionCmd#groupId} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeleteIdentityLinkForProcessDefinitionCmd#DeleteIdentityLinkForProcessDefinitionCmd(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessDefinitionCmd.<init>(String, String, String)"
  })
  public void testNewDeleteIdentityLinkForProcessDefinitionCmd_when42_thenReturnGroupIdIs42() {
    // Arrange and Act
    DeleteIdentityLinkForProcessDefinitionCmd actualDeleteIdentityLinkForProcessDefinitionCmd =
        new DeleteIdentityLinkForProcessDefinitionCmd("42", "42", "42");

    // Assert
    assertEquals("42", actualDeleteIdentityLinkForProcessDefinitionCmd.groupId);
    assertEquals("42", actualDeleteIdentityLinkForProcessDefinitionCmd.processDefinitionId);
    assertEquals("42", actualDeleteIdentityLinkForProcessDefinitionCmd.userId);
  }

  /**
   * Test {@link
   * DeleteIdentityLinkForProcessDefinitionCmd#DeleteIdentityLinkForProcessDefinitionCmd(String,
   * String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@link DeleteIdentityLinkForProcessDefinitionCmd#userId} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeleteIdentityLinkForProcessDefinitionCmd#DeleteIdentityLinkForProcessDefinitionCmd(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessDefinitionCmd.<init>(String, String, String)"
  })
  public void testNewDeleteIdentityLinkForProcessDefinitionCmd_whenNull_thenReturnUserIdIsNull() {
    // Arrange and Act
    DeleteIdentityLinkForProcessDefinitionCmd actualDeleteIdentityLinkForProcessDefinitionCmd =
        new DeleteIdentityLinkForProcessDefinitionCmd("42", null, "42");

    // Assert
    assertEquals("42", actualDeleteIdentityLinkForProcessDefinitionCmd.groupId);
    assertEquals("42", actualDeleteIdentityLinkForProcessDefinitionCmd.processDefinitionId);
    assertNull(actualDeleteIdentityLinkForProcessDefinitionCmd.userId);
  }

  /**
   * Test {@link DeleteIdentityLinkForProcessDefinitionCmd#validateParams(String, String, String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkForProcessDefinitionCmd#validateParams(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessDefinitionCmd.validateParams(String, String, String)"
  })
  public void testValidateParams_when42_thenDoesNotThrow() {
    // Arrange
    DeleteIdentityLinkForProcessDefinitionCmd deleteIdentityLinkForProcessDefinitionCmd =
        new DeleteIdentityLinkForProcessDefinitionCmd("42", "42", "42");

    // Act and Assert
    deleteIdentityLinkForProcessDefinitionCmd.validateParams("42", "42", "42");
  }

  /**
   * Test {@link DeleteIdentityLinkForProcessDefinitionCmd#validateParams(String, String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkForProcessDefinitionCmd#validateParams(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessDefinitionCmd.validateParams(String, String, String)"
  })
  public void testValidateParams_whenNull_thenDoesNotThrow() {
    // Arrange
    DeleteIdentityLinkForProcessDefinitionCmd deleteIdentityLinkForProcessDefinitionCmd =
        new DeleteIdentityLinkForProcessDefinitionCmd("42", "42", "42");

    // Act and Assert
    deleteIdentityLinkForProcessDefinitionCmd.validateParams(null, "42", "42");
  }

  /**
   * Test {@link DeleteIdentityLinkForProcessDefinitionCmd#validateParams(String, String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkForProcessDefinitionCmd#validateParams(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessDefinitionCmd.validateParams(String, String, String)"
  })
  public void testValidateParams_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DeleteIdentityLinkForProcessDefinitionCmd deleteIdentityLinkForProcessDefinitionCmd =
        new DeleteIdentityLinkForProcessDefinitionCmd("42", "42", "42");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> deleteIdentityLinkForProcessDefinitionCmd.validateParams("42", "42", null));
  }

  /**
   * Test {@link DeleteIdentityLinkForProcessDefinitionCmd#validateParams(String, String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkForProcessDefinitionCmd#validateParams(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeleteIdentityLinkForProcessDefinitionCmd.validateParams(String, String, String)"
  })
  public void testValidateParams_whenNull_thenThrowActivitiIllegalArgumentException2() {
    // Arrange
    DeleteIdentityLinkForProcessDefinitionCmd deleteIdentityLinkForProcessDefinitionCmd =
        new DeleteIdentityLinkForProcessDefinitionCmd("42", "42", "42");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> deleteIdentityLinkForProcessDefinitionCmd.validateParams(null, null, "42"));
  }
}
