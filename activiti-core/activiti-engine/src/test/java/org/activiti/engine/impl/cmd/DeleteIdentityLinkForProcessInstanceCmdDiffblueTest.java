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

public class DeleteIdentityLinkForProcessInstanceCmdDiffblueTest {
  /**
   * Method under test:
   * {@link DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkForProcessInstanceCmd() {
    // Arrange and Act
    DeleteIdentityLinkForProcessInstanceCmd actualDeleteIdentityLinkForProcessInstanceCmd = new DeleteIdentityLinkForProcessInstanceCmd(
        "42", "42", "42", "Type");

    // Assert
    assertEquals("42", actualDeleteIdentityLinkForProcessInstanceCmd.groupId);
    assertEquals("42", actualDeleteIdentityLinkForProcessInstanceCmd.processInstanceId);
    assertEquals("42", actualDeleteIdentityLinkForProcessInstanceCmd.userId);
    assertEquals("Type", actualDeleteIdentityLinkForProcessInstanceCmd.type);
  }

  /**
   * Method under test:
   * {@link DeleteIdentityLinkForProcessInstanceCmd#validateParams(String, String, String, String)}
   */
  @Test
  public void testValidateParams() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type")).validateParams("42", "42", null,
            "Type"));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type")).validateParams("42", "42", "42",
            null));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkForProcessInstanceCmd("42", "42", "42", "Type")).validateParams(null, null, "42",
            "Type"));
  }

  /**
   * Method under test:
   * {@link DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkForProcessInstanceCmd2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new DeleteIdentityLinkForProcessInstanceCmd(null, "42", "42", "Type"));

  }

  /**
   * Method under test:
   * {@link DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkForProcessInstanceCmd3() {
    // Arrange and Act
    DeleteIdentityLinkForProcessInstanceCmd actualDeleteIdentityLinkForProcessInstanceCmd = new DeleteIdentityLinkForProcessInstanceCmd(
        "42", null, "42", "Type");

    // Assert
    assertEquals("42", actualDeleteIdentityLinkForProcessInstanceCmd.groupId);
    assertEquals("42", actualDeleteIdentityLinkForProcessInstanceCmd.processInstanceId);
    assertEquals("Type", actualDeleteIdentityLinkForProcessInstanceCmd.type);
    assertNull(actualDeleteIdentityLinkForProcessInstanceCmd.userId);
  }

  /**
   * Method under test:
   * {@link DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkForProcessInstanceCmd4() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new DeleteIdentityLinkForProcessInstanceCmd("42", "42", "42", null));

  }

  /**
   * Method under test:
   * {@link DeleteIdentityLinkForProcessInstanceCmd#DeleteIdentityLinkForProcessInstanceCmd(String, String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkForProcessInstanceCmd5() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new DeleteIdentityLinkForProcessInstanceCmd("42", null, null, "Type"));

  }
}
