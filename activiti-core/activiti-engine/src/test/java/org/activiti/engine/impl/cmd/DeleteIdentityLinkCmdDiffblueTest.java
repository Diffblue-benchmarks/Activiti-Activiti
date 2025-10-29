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
   * Method under test:
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkCmd() {
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
   * Method under test:
   * {@link DeleteIdentityLinkCmd#validateParams(String, String, String, String)}
   */
  @Test
  public void testValidateParams() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkCmd("42", "42", "42", "Type")).validateParams("42", "42", "assignee", "42"));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkCmd("42", "42", "42", "Type")).validateParams("42", "42", "owner", "42"));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkCmd("42", "42", "42", "Type")).validateParams("42", "42", null, "42"));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkCmd("42", "42", "42", "Type")).validateParams("42", "42", "Type", null));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkCmd("42", "42", "42", "Type")).validateParams(null, null, "Type", "42"));
  }

  /**
   * Method under test:
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkCmd2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new DeleteIdentityLinkCmd(null, "42", "42", "Type"));

  }

  /**
   * Method under test:
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkCmd3() {
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
   * Method under test:
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkCmd4() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new DeleteIdentityLinkCmd("42", "42", "42", "assignee"));

  }

  /**
   * Method under test:
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkCmd5() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new DeleteIdentityLinkCmd("42", "42", "42", "owner"));

  }

  /**
   * Method under test:
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkCmd6() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new DeleteIdentityLinkCmd("42", "42", "42", null));

  }

  /**
   * Method under test:
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkCmd7() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new DeleteIdentityLinkCmd("42", null, null, "Type"));

  }

  /**
   * Method under test:
   * {@link DeleteIdentityLinkCmd#DeleteIdentityLinkCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkCmd8() {
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
}
