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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.ProcessDefinitionDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class AddIdentityLinkForProcessDefinitionCmdDiffblueTest {
  /**
   * Test {@link
   * AddIdentityLinkForProcessDefinitionCmd#AddIdentityLinkForProcessDefinitionCmd(String, String,
   * String)}.
   *
   * <p>Method under test: {@link
   * AddIdentityLinkForProcessDefinitionCmd#AddIdentityLinkForProcessDefinitionCmd(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AddIdentityLinkForProcessDefinitionCmd.<init>(String, String, String)"})
  public void testNewAddIdentityLinkForProcessDefinitionCmd() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new AddIdentityLinkForProcessDefinitionCmd("42", null, null));
  }

  /**
   * Test {@link
   * AddIdentityLinkForProcessDefinitionCmd#AddIdentityLinkForProcessDefinitionCmd(String, String,
   * String)}.
   *
   * <p>Method under test: {@link
   * AddIdentityLinkForProcessDefinitionCmd#AddIdentityLinkForProcessDefinitionCmd(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AddIdentityLinkForProcessDefinitionCmd.<init>(String, String, String)"})
  public void testNewAddIdentityLinkForProcessDefinitionCmd2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new AddIdentityLinkForProcessDefinitionCmd(null, "42", "42"));
  }

  /**
   * Test {@link
   * AddIdentityLinkForProcessDefinitionCmd#AddIdentityLinkForProcessDefinitionCmd(String, String,
   * String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@link AddIdentityLinkForProcessDefinitionCmd#groupId} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AddIdentityLinkForProcessDefinitionCmd#AddIdentityLinkForProcessDefinitionCmd(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AddIdentityLinkForProcessDefinitionCmd.<init>(String, String, String)"})
  public void testNewAddIdentityLinkForProcessDefinitionCmd_when42_thenReturnGroupIdIs42() {
    // Arrange and Act
    AddIdentityLinkForProcessDefinitionCmd actualAddIdentityLinkForProcessDefinitionCmd =
        new AddIdentityLinkForProcessDefinitionCmd("42", "42", "42");

    // Assert
    assertEquals("42", actualAddIdentityLinkForProcessDefinitionCmd.groupId);
    assertEquals("42", actualAddIdentityLinkForProcessDefinitionCmd.processDefinitionId);
    assertEquals("42", actualAddIdentityLinkForProcessDefinitionCmd.userId);
  }

  /**
   * Test {@link
   * AddIdentityLinkForProcessDefinitionCmd#AddIdentityLinkForProcessDefinitionCmd(String, String,
   * String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@link AddIdentityLinkForProcessDefinitionCmd#groupId} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AddIdentityLinkForProcessDefinitionCmd#AddIdentityLinkForProcessDefinitionCmd(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AddIdentityLinkForProcessDefinitionCmd.<init>(String, String, String)"})
  public void testNewAddIdentityLinkForProcessDefinitionCmd_whenNull_thenReturnGroupIdIsNull() {
    // Arrange and Act
    AddIdentityLinkForProcessDefinitionCmd actualAddIdentityLinkForProcessDefinitionCmd =
        new AddIdentityLinkForProcessDefinitionCmd("42", "42", null);
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findById(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionEntityImpl());
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);
    IdentityLinkEntityManager identityLinkEntityManager = mock(IdentityLinkEntityManager.class);
    when(identityLinkEntityManager.addIdentityLink(
            Mockito.<ProcessDefinitionEntity>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new IdentityLinkEntityImpl());
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getIdentityLinkEntityManager()).thenReturn(identityLinkEntityManager);
    when(commandContext.getProcessDefinitionEntityManager())
        .thenReturn(processDefinitionEntityManagerImpl);
    Void actualExecuteResult = actualAddIdentityLinkForProcessDefinitionCmd.execute(commandContext);

    // Assert
    verify(commandContext).getIdentityLinkEntityManager();
    verify(commandContext).getProcessDefinitionEntityManager();
    verify(identityLinkEntityManager)
        .addIdentityLink(isA(ProcessDefinitionEntity.class), eq("42"), isNull());
    verify(processDefinitionDataManager).findById("42");
    assertEquals("42", actualAddIdentityLinkForProcessDefinitionCmd.processDefinitionId);
    assertEquals("42", actualAddIdentityLinkForProcessDefinitionCmd.userId);
    assertNull(actualAddIdentityLinkForProcessDefinitionCmd.groupId);
    assertNull(actualExecuteResult);
  }

  /**
   * Test {@link
   * AddIdentityLinkForProcessDefinitionCmd#AddIdentityLinkForProcessDefinitionCmd(String, String,
   * String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@link AddIdentityLinkForProcessDefinitionCmd#userId} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AddIdentityLinkForProcessDefinitionCmd#AddIdentityLinkForProcessDefinitionCmd(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AddIdentityLinkForProcessDefinitionCmd.<init>(String, String, String)"})
  public void testNewAddIdentityLinkForProcessDefinitionCmd_whenNull_thenReturnUserIdIsNull() {
    // Arrange and Act
    AddIdentityLinkForProcessDefinitionCmd actualAddIdentityLinkForProcessDefinitionCmd =
        new AddIdentityLinkForProcessDefinitionCmd("42", null, "42");

    // Assert
    assertEquals("42", actualAddIdentityLinkForProcessDefinitionCmd.groupId);
    assertEquals("42", actualAddIdentityLinkForProcessDefinitionCmd.processDefinitionId);
    assertNull(actualAddIdentityLinkForProcessDefinitionCmd.userId);
  }

  /**
   * Test {@link AddIdentityLinkForProcessDefinitionCmd#validateParams(String, String, String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link AddIdentityLinkForProcessDefinitionCmd#validateParams(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessDefinitionCmd.validateParams(String, String, String)"
  })
  public void testValidateParams_when42_thenDoesNotThrow() {
    // Arrange
    AddIdentityLinkForProcessDefinitionCmd addIdentityLinkForProcessDefinitionCmd =
        new AddIdentityLinkForProcessDefinitionCmd("42", "42", "42");

    // Act and Assert
    addIdentityLinkForProcessDefinitionCmd.validateParams("42", "42", "42");
  }

  /**
   * Test {@link AddIdentityLinkForProcessDefinitionCmd#validateParams(String, String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link AddIdentityLinkForProcessDefinitionCmd#validateParams(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessDefinitionCmd.validateParams(String, String, String)"
  })
  public void testValidateParams_whenNull_thenDoesNotThrow() {
    // Arrange
    AddIdentityLinkForProcessDefinitionCmd addIdentityLinkForProcessDefinitionCmd =
        new AddIdentityLinkForProcessDefinitionCmd("42", "42", "42");

    // Act and Assert
    addIdentityLinkForProcessDefinitionCmd.validateParams(null, "42", "42");
  }

  /**
   * Test {@link AddIdentityLinkForProcessDefinitionCmd#validateParams(String, String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link AddIdentityLinkForProcessDefinitionCmd#validateParams(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessDefinitionCmd.validateParams(String, String, String)"
  })
  public void testValidateParams_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    AddIdentityLinkForProcessDefinitionCmd addIdentityLinkForProcessDefinitionCmd =
        new AddIdentityLinkForProcessDefinitionCmd("42", "42", "42");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> addIdentityLinkForProcessDefinitionCmd.validateParams("42", "42", null));
  }

  /**
   * Test {@link AddIdentityLinkForProcessDefinitionCmd#validateParams(String, String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link AddIdentityLinkForProcessDefinitionCmd#validateParams(String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessDefinitionCmd.validateParams(String, String, String)"
  })
  public void testValidateParams_whenNull_thenThrowActivitiIllegalArgumentException2() {
    // Arrange
    AddIdentityLinkForProcessDefinitionCmd addIdentityLinkForProcessDefinitionCmd =
        new AddIdentityLinkForProcessDefinitionCmd("42", "42", "42");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> addIdentityLinkForProcessDefinitionCmd.validateParams(null, null, "42"));
  }
}
