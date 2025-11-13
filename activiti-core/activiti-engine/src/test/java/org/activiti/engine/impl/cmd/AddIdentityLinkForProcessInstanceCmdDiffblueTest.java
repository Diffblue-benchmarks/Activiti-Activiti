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

import static org.junit.Assert.assertArrayEquals;
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
import java.io.UnsupportedEncodingException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityManager;
import org.activiti.engine.impl.persistence.entity.data.ExecutionDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class AddIdentityLinkForProcessInstanceCmdDiffblueTest {
  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String)"
  })
  public void testNewAddIdentityLinkForProcessInstanceCmd() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new AddIdentityLinkForProcessInstanceCmd("42", "42", "42", null));
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String)"
  })
  public void testNewAddIdentityLinkForProcessInstanceCmd2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new AddIdentityLinkForProcessInstanceCmd("42", null, null, "Type"));
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String,
   * String, String, String)}.
   *
   * <p>Method under test: {@link
   * AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String)"
  })
  public void testNewAddIdentityLinkForProcessInstanceCmd3() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new AddIdentityLinkForProcessInstanceCmd(null, "42", "42", "Type"));
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String,
   * String, String, String, byte[])}.
   *
   * <p>Method under test: {@link
   * AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String,
   * String, String, byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String, byte[])"
  })
  public void testNewAddIdentityLinkForProcessInstanceCmd4() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            new AddIdentityLinkForProcessInstanceCmd(
                "42", "42", "42", null, new byte[] {'A', 'X', 'A', 'X', 'A', 'X', 'A', 'X'}));
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String,
   * String, String, String, byte[])}.
   *
   * <p>Method under test: {@link
   * AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String,
   * String, String, byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String, byte[])"
  })
  public void testNewAddIdentityLinkForProcessInstanceCmd5() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            new AddIdentityLinkForProcessInstanceCmd(
                "42", null, null, "Type", new byte[] {'A', 'X', 'A', 'X', 'A', 'X', 'A', 'X'}));
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String,
   * String, String, String, byte[])}.
   *
   * <p>Method under test: {@link
   * AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String,
   * String, String, byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String, byte[])"
  })
  public void testNewAddIdentityLinkForProcessInstanceCmd6() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            new AddIdentityLinkForProcessInstanceCmd(
                null, "42", "42", "Type", new byte[] {'A', 'X', 'A', 'X', 'A', 'X', 'A', 'X'}));
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String,
   * String, String, String, byte[])}.
   *
   * <ul>
   *   <li>Then return {@link AddIdentityLinkForProcessInstanceCmd#userId} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String,
   * String, String, byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String, byte[])"
  })
  public void testNewAddIdentityLinkForProcessInstanceCmd_thenReturnUserIdIs42()
      throws UnsupportedEncodingException {
    // Arrange and Act
    AddIdentityLinkForProcessInstanceCmd actualAddIdentityLinkForProcessInstanceCmd =
        new AddIdentityLinkForProcessInstanceCmd(
            "42", "42", "42", "Type", "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.groupId);
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.processInstanceId);
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.userId);
    assertEquals("Type", actualAddIdentityLinkForProcessInstanceCmd.type);
    assertArrayEquals(
        "AXAXAXAX".getBytes("UTF-8"), actualAddIdentityLinkForProcessInstanceCmd.details);
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String,
   * String, String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@link AddIdentityLinkForProcessInstanceCmd#groupId} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String)"
  })
  public void testNewAddIdentityLinkForProcessInstanceCmd_whenNull_thenReturnGroupIdIsNull() {
    // Arrange and Act
    AddIdentityLinkForProcessInstanceCmd actualAddIdentityLinkForProcessInstanceCmd =
        new AddIdentityLinkForProcessInstanceCmd("42", "42", null, "Type");
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);
    IdentityLinkEntityManager identityLinkEntityManager = mock(IdentityLinkEntityManager.class);
    when(identityLinkEntityManager.addIdentityLink(
            Mockito.<ExecutionEntity>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<byte[]>any()))
        .thenReturn(new IdentityLinkEntityImpl());
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getHistoryManager())
        .thenReturn(
            new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    when(commandContext.getIdentityLinkEntityManager()).thenReturn(identityLinkEntityManager);
    when(commandContext.getExecutionEntityManager()).thenReturn(executionEntityManagerImpl);
    Void actualExecuteResult = actualAddIdentityLinkForProcessInstanceCmd.execute(commandContext);

    // Assert
    verify(commandContext).getExecutionEntityManager();
    verify(commandContext).getHistoryManager();
    verify(commandContext).getIdentityLinkEntityManager();
    verify(identityLinkEntityManager)
        .addIdentityLink(
            isA(ExecutionEntity.class), eq("42"), (String) isNull(), eq("Type"), (byte[]) isNull());
    verify(executionDataManager).findById("42");
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.processInstanceId);
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.userId);
    assertEquals("Type", actualAddIdentityLinkForProcessInstanceCmd.type);
    assertNull(actualAddIdentityLinkForProcessInstanceCmd.details);
    assertNull(actualAddIdentityLinkForProcessInstanceCmd.groupId);
    assertNull(actualExecuteResult);
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String,
   * String, String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@link AddIdentityLinkForProcessInstanceCmd#userId} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String)"
  })
  public void testNewAddIdentityLinkForProcessInstanceCmd_whenNull_thenReturnUserIdIsNull() {
    // Arrange and Act
    AddIdentityLinkForProcessInstanceCmd actualAddIdentityLinkForProcessInstanceCmd =
        new AddIdentityLinkForProcessInstanceCmd("42", null, "42", "Type");

    // Assert
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.groupId);
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.processInstanceId);
    assertEquals("Type", actualAddIdentityLinkForProcessInstanceCmd.type);
    assertNull(actualAddIdentityLinkForProcessInstanceCmd.details);
    assertNull(actualAddIdentityLinkForProcessInstanceCmd.userId);
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String,
   * String, String, String, byte[])}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@link AddIdentityLinkForProcessInstanceCmd#userId} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String,
   * String, String, byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String, byte[])"
  })
  public void testNewAddIdentityLinkForProcessInstanceCmd_whenNull_thenReturnUserIdIsNull2()
      throws UnsupportedEncodingException {
    // Arrange and Act
    AddIdentityLinkForProcessInstanceCmd actualAddIdentityLinkForProcessInstanceCmd =
        new AddIdentityLinkForProcessInstanceCmd(
            "42", null, "42", "Type", new byte[] {'A', 'X', 'A', 'X', 'A', 'X', 'A', 'X'});

    // Assert
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.groupId);
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.processInstanceId);
    assertEquals("Type", actualAddIdentityLinkForProcessInstanceCmd.type);
    assertNull(actualAddIdentityLinkForProcessInstanceCmd.userId);
    assertArrayEquals(
        "AXAXAXAX".getBytes("UTF-8"), actualAddIdentityLinkForProcessInstanceCmd.details);
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String,
   * String, String, String)}.
   *
   * <ul>
   *   <li>When {@code Type}.
   *   <li>Then return {@link AddIdentityLinkForProcessInstanceCmd#groupId} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AddIdentityLinkForProcessInstanceCmd#AddIdentityLinkForProcessInstanceCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessInstanceCmd.<init>(String, String, String, String)"
  })
  public void testNewAddIdentityLinkForProcessInstanceCmd_whenType_thenReturnGroupIdIs42() {
    // Arrange and Act
    AddIdentityLinkForProcessInstanceCmd actualAddIdentityLinkForProcessInstanceCmd =
        new AddIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type");

    // Assert
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.groupId);
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.processInstanceId);
    assertEquals("42", actualAddIdentityLinkForProcessInstanceCmd.userId);
    assertEquals("Type", actualAddIdentityLinkForProcessInstanceCmd.type);
    assertNull(actualAddIdentityLinkForProcessInstanceCmd.details);
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#validateParams(String, String, String,
   * String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link AddIdentityLinkForProcessInstanceCmd#validateParams(String,
   * String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessInstanceCmd.validateParams(String, String, String, String)"
  })
  public void testValidateParams_whenNull_thenDoesNotThrow() {
    // Arrange
    AddIdentityLinkForProcessInstanceCmd addIdentityLinkForProcessInstanceCmd =
        new AddIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type");

    // Act and Assert
    addIdentityLinkForProcessInstanceCmd.validateParams("42", null, "42", "Type");
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#validateParams(String, String, String,
   * String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link AddIdentityLinkForProcessInstanceCmd#validateParams(String,
   * String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessInstanceCmd.validateParams(String, String, String, String)"
  })
  public void testValidateParams_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    AddIdentityLinkForProcessInstanceCmd addIdentityLinkForProcessInstanceCmd =
        new AddIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> addIdentityLinkForProcessInstanceCmd.validateParams("42", "42", "42", null));
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#validateParams(String, String, String,
   * String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link AddIdentityLinkForProcessInstanceCmd#validateParams(String,
   * String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessInstanceCmd.validateParams(String, String, String, String)"
  })
  public void testValidateParams_whenNull_thenThrowActivitiIllegalArgumentException2() {
    // Arrange
    AddIdentityLinkForProcessInstanceCmd addIdentityLinkForProcessInstanceCmd =
        new AddIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> addIdentityLinkForProcessInstanceCmd.validateParams("42", null, null, "Type"));
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#validateParams(String, String, String,
   * String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link AddIdentityLinkForProcessInstanceCmd#validateParams(String,
   * String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessInstanceCmd.validateParams(String, String, String, String)"
  })
  public void testValidateParams_whenNull_thenThrowActivitiIllegalArgumentException3() {
    // Arrange
    AddIdentityLinkForProcessInstanceCmd addIdentityLinkForProcessInstanceCmd =
        new AddIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> addIdentityLinkForProcessInstanceCmd.validateParams(null, "42", "42", "Type"));
  }

  /**
   * Test {@link AddIdentityLinkForProcessInstanceCmd#validateParams(String, String, String,
   * String)}.
   *
   * <ul>
   *   <li>When {@code Type}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link AddIdentityLinkForProcessInstanceCmd#validateParams(String,
   * String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AddIdentityLinkForProcessInstanceCmd.validateParams(String, String, String, String)"
  })
  public void testValidateParams_whenType_thenDoesNotThrow() {
    // Arrange
    AddIdentityLinkForProcessInstanceCmd addIdentityLinkForProcessInstanceCmd =
        new AddIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type");

    // Act and Assert
    addIdentityLinkForProcessInstanceCmd.validateParams("42", "42", "42", "Type");
  }
}
