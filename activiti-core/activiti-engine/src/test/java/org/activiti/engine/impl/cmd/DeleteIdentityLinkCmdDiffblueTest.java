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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DeleteIdentityLinkCmdDiffblueTest {
  /**
   * Test {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}.
   *
   * <ul>
   *   <li>When {@code assignee}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeleteIdentityLinkCmd.<init>(String, String, String, String)"})
  public void testNewDeleteIdentityLinkCmd_whenAssignee() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeleteIdentityLinkCmd("42", null, "42", "assignee"));
  }

  /**
   * Test {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}.
   *
   * <ul>
   *   <li>When {@code assignee}.
   *   <li>Then return {@link DeleteIdentityLinkCmd#type} is {@code assignee}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeleteIdentityLinkCmd.<init>(String, String, String, String)"})
  public void testNewDeleteIdentityLinkCmd_whenAssignee_thenReturnTypeIsAssignee() {
    // Arrange and Act
    DeleteIdentityLinkCmd actualDeleteIdentityLinkCmd =
        new DeleteIdentityLinkCmd("42", null, null, "assignee");

    // Assert
    assertEquals("42", actualDeleteIdentityLinkCmd.taskId);
    assertEquals(
        "Cannot execute operation: task is suspended",
        actualDeleteIdentityLinkCmd.getSuspendedTaskException());
    assertEquals("assignee", actualDeleteIdentityLinkCmd.type);
    assertNull(actualDeleteIdentityLinkCmd.groupId);
    assertNull(actualDeleteIdentityLinkCmd.userId);
  }

  /**
   * Test {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeleteIdentityLinkCmd.<init>(String, String, String, String)"})
  public void testNewDeleteIdentityLinkCmd_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeleteIdentityLinkCmd(null, null, null, null));
  }

  /**
   * Test {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeleteIdentityLinkCmd.<init>(String, String, String, String)"})
  public void testNewDeleteIdentityLinkCmd_whenNull_thenThrowActivitiIllegalArgumentException2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeleteIdentityLinkCmd("42", null, null, null));
  }

  /**
   * Test {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}.
   *
   * <ul>
   *   <li>When {@code owner}.
   *   <li>Then return {@link DeleteIdentityLinkCmd#type} is {@code owner}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeleteIdentityLinkCmd.<init>(String, String, String, String)"})
  public void testNewDeleteIdentityLinkCmd_whenOwner_thenReturnTypeIsOwner() {
    // Arrange and Act
    DeleteIdentityLinkCmd actualDeleteIdentityLinkCmd =
        new DeleteIdentityLinkCmd("42", null, null, "owner");

    // Assert
    assertEquals("42", actualDeleteIdentityLinkCmd.taskId);
    assertEquals(
        "Cannot execute operation: task is suspended",
        actualDeleteIdentityLinkCmd.getSuspendedTaskException());
    assertEquals("owner", actualDeleteIdentityLinkCmd.type);
    assertNull(actualDeleteIdentityLinkCmd.groupId);
    assertNull(actualDeleteIdentityLinkCmd.userId);
  }

  /**
   * Test {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}.
   *
   * <ul>
   *   <li>When {@code Type}.
   *   <li>Then return {@link DeleteIdentityLinkCmd#groupId} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeleteIdentityLinkCmd.<init>(String, String, String, String)"})
  public void testNewDeleteIdentityLinkCmd_whenType_thenReturnGroupIdIs42() {
    // Arrange and Act
    DeleteIdentityLinkCmd actualDeleteIdentityLinkCmd =
        new DeleteIdentityLinkCmd("42", null, "42", "Type");

    // Assert
    assertEquals("42", actualDeleteIdentityLinkCmd.groupId);
    assertEquals("42", actualDeleteIdentityLinkCmd.taskId);
    assertEquals(
        "Cannot execute operation: task is suspended",
        actualDeleteIdentityLinkCmd.getSuspendedTaskException());
    assertEquals("Type", actualDeleteIdentityLinkCmd.type);
    assertNull(actualDeleteIdentityLinkCmd.userId);
  }

  /**
   * Test {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}.
   *
   * <ul>
   *   <li>When {@code Type}.
   *   <li>Then return {@link DeleteIdentityLinkCmd#userId} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeleteIdentityLinkCmd.<init>(String, String, String, String)"})
  public void testNewDeleteIdentityLinkCmd_whenType_thenReturnUserIdIs42() {
    // Arrange and Act
    DeleteIdentityLinkCmd actualDeleteIdentityLinkCmd =
        new DeleteIdentityLinkCmd("42", "42", "42", "Type");

    // Assert
    assertEquals("42", actualDeleteIdentityLinkCmd.groupId);
    assertEquals("42", actualDeleteIdentityLinkCmd.userId);
    assertEquals("42", actualDeleteIdentityLinkCmd.taskId);
    assertEquals(
        "Cannot execute operation: task is suspended",
        actualDeleteIdentityLinkCmd.getSuspendedTaskException());
    assertEquals("Type", actualDeleteIdentityLinkCmd.type);
  }

  /**
   * Test {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}.
   *
   * <ul>
   *   <li>When {@code Type}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeleteIdentityLinkCmd.<init>(String, String, String, String)"})
  public void testNewDeleteIdentityLinkCmd_whenType_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DeleteIdentityLinkCmd("42", null, null, "Type"));
  }

  /**
   * Test {@link DeleteIdentityLinkCmd#validateParams(String, String, String, String)}.
   *
   * <ul>
   *   <li>When {@code assignee}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkCmd#validateParams(String, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeleteIdentityLinkCmd.validateParams(String, String, String, String)"})
  public void testValidateParams_whenAssignee_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DeleteIdentityLinkCmd deleteIdentityLinkCmd =
        new DeleteIdentityLinkCmd("42", "42", "42", "Type");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> deleteIdentityLinkCmd.validateParams(null, "42", "assignee", "42"));
  }

  /**
   * Test {@link DeleteIdentityLinkCmd#validateParams(String, String, String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkCmd#validateParams(String, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeleteIdentityLinkCmd.validateParams(String, String, String, String)"})
  public void testValidateParams_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DeleteIdentityLinkCmd deleteIdentityLinkCmd =
        new DeleteIdentityLinkCmd("42", "42", "42", "Type");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> deleteIdentityLinkCmd.validateParams(null, null, null, null));
  }

  /**
   * Test {@link DeleteIdentityLinkCmd#validateParams(String, String, String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkCmd#validateParams(String, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeleteIdentityLinkCmd.validateParams(String, String, String, String)"})
  public void testValidateParams_whenNull_thenThrowActivitiIllegalArgumentException2() {
    // Arrange
    DeleteIdentityLinkCmd deleteIdentityLinkCmd =
        new DeleteIdentityLinkCmd("42", "42", "42", "Type");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> deleteIdentityLinkCmd.validateParams(null, null, null, "42"));
  }

  /**
   * Test {@link DeleteIdentityLinkCmd#validateParams(String, String, String, String)}.
   *
   * <ul>
   *   <li>When {@code Type}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DeleteIdentityLinkCmd#validateParams(String, String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeleteIdentityLinkCmd.validateParams(String, String, String, String)"})
  public void testValidateParams_whenType_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DeleteIdentityLinkCmd deleteIdentityLinkCmd =
        new DeleteIdentityLinkCmd("42", "42", "42", "Type");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> deleteIdentityLinkCmd.validateParams(null, null, "Type", "42"));
  }
}
