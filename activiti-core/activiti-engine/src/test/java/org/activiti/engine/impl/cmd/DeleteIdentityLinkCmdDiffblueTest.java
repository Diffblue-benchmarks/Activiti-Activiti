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
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;

public class DeleteIdentityLinkCmdDiffblueTest {
  /**
   * Test
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}.
   * <ul>
   *   <li>When {@code assignee}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkCmd_whenAssignee() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new DeleteIdentityLinkCmd("42", "42", "42", "assignee"));

  }

  /**
   * Test
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}.
   * <ul>
   *   <li>When {@code assignee}.</li>
   *   <li>Then return {@link DeleteIdentityLinkCmd#type} is {@code assignee}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkCmd_whenAssignee_thenReturnTypeIsAssignee() {
    // Arrange and Act
    DeleteIdentityLinkCmd actualDeleteIdentityLinkCmd = new DeleteIdentityLinkCmd("42", "42", null, "assignee");

    // Assert
    assertEquals("42", actualDeleteIdentityLinkCmd.userId);
    assertEquals("42", actualDeleteIdentityLinkCmd.taskId);
    assertEquals("Cannot execute operation: task is suspended",
        actualDeleteIdentityLinkCmd.getSuspendedTaskException());
    assertEquals("assignee", actualDeleteIdentityLinkCmd.type);
    assertNull(actualDeleteIdentityLinkCmd.groupId);
  }

  /**
   * Test
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkCmd_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new DeleteIdentityLinkCmd("42", "42", "42", null));

  }

  /**
   * Test
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}.
   * <ul>
   *   <li>When {@code owner}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkCmd_whenOwner_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new DeleteIdentityLinkCmd("42", "42", "42", "owner"));

  }

  /**
   * Test
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}.
   * <ul>
   *   <li>When {@code Type}.</li>
   *   <li>Then return {@link DeleteIdentityLinkCmd#groupId} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkCmd_whenType_thenReturnGroupIdIs42() {
    // Arrange and Act
    DeleteIdentityLinkCmd actualDeleteIdentityLinkCmd = new DeleteIdentityLinkCmd("42", "42", "42", "Type");

    // Assert
    assertEquals("42", actualDeleteIdentityLinkCmd.groupId);
    assertEquals("42", actualDeleteIdentityLinkCmd.userId);
    assertEquals("42", actualDeleteIdentityLinkCmd.taskId);
    assertEquals("Cannot execute operation: task is suspended",
        actualDeleteIdentityLinkCmd.getSuspendedTaskException());
    assertEquals("Type", actualDeleteIdentityLinkCmd.type);
  }

  /**
   * Test
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}.
   * <ul>
   *   <li>When {@code Type}.</li>
   *   <li>Then return {@link DeleteIdentityLinkCmd#userId} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkCmd_whenType_thenReturnUserIdIsNull() {
    // Arrange and Act
    DeleteIdentityLinkCmd actualDeleteIdentityLinkCmd = new DeleteIdentityLinkCmd("42", null, "42", "Type");

    // Assert
    assertEquals("42", actualDeleteIdentityLinkCmd.groupId);
    assertEquals("42", actualDeleteIdentityLinkCmd.taskId);
    assertEquals("Cannot execute operation: task is suspended",
        actualDeleteIdentityLinkCmd.getSuspendedTaskException());
    assertEquals("Type", actualDeleteIdentityLinkCmd.type);
    assertNull(actualDeleteIdentityLinkCmd.userId);
  }

  /**
   * Test
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}.
   * <ul>
   *   <li>When {@code Type}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkCmd_whenType_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new DeleteIdentityLinkCmd(null, "42", "42", "Type"));

    assertThrows(ActivitiIllegalArgumentException.class, () -> new DeleteIdentityLinkCmd("42", null, null, "Type"));
  }

  /**
   * Test
   * {@link DeleteIdentityLinkCmd#validateParams(String, String, String, String)}.
   * <ul>
   *   <li>When {@code assignee}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeleteIdentityLinkCmd#validateParams(String, String, String, String)}
   */
  @Test
  public void testValidateParams_whenAssignee_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkCmd("42", "42", "42", "Type")).validateParams("42", "42", "assignee", "42"));
  }

  /**
   * Test
   * {@link DeleteIdentityLinkCmd#validateParams(String, String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeleteIdentityLinkCmd#validateParams(String, String, String, String)}
   */
  @Test
  public void testValidateParams_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkCmd("42", "42", "42", "Type")).validateParams("42", "42", null, "42"));
  }

  /**
   * Test
   * {@link DeleteIdentityLinkCmd#validateParams(String, String, String, String)}.
   * <ul>
   *   <li>When {@code owner}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeleteIdentityLinkCmd#validateParams(String, String, String, String)}
   */
  @Test
  public void testValidateParams_whenOwner_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkCmd("42", "42", "42", "Type")).validateParams("42", "42", "owner", "42"));
  }

  /**
   * Test
   * {@link DeleteIdentityLinkCmd#validateParams(String, String, String, String)}.
   * <ul>
   *   <li>When {@code Type}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeleteIdentityLinkCmd#validateParams(String, String, String, String)}
   */
  @Test
  public void testValidateParams_whenType_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkCmd("42", "42", "42", "Type")).validateParams("42", "42", "Type", null));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkCmd("42", "42", "42", "Type")).validateParams(null, null, "Type", "42"));
  }
}
