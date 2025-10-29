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

public class DeleteIdentityLinkForProcessDefinitionCmdDiffblueTest {
  /**
   * Method under test:
   * {@link DeleteIdentityLinkForProcessDefinitionCmd#DeleteIdentityLinkForProcessDefinitionCmd(String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkForProcessDefinitionCmd() {
    // Arrange and Act
    DeleteIdentityLinkForProcessDefinitionCmd actualDeleteIdentityLinkForProcessDefinitionCmd = new DeleteIdentityLinkForProcessDefinitionCmd(
        "42", "42", "42");

    // Assert
    assertEquals("42", actualDeleteIdentityLinkForProcessDefinitionCmd.groupId);
    assertEquals("42", actualDeleteIdentityLinkForProcessDefinitionCmd.processDefinitionId);
    assertEquals("42", actualDeleteIdentityLinkForProcessDefinitionCmd.userId);
  }

  /**
   * Method under test:
   * {@link DeleteIdentityLinkForProcessDefinitionCmd#validateParams(String, String, String)}
   */
  @Test
  public void testValidateParams() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkForProcessDefinitionCmd("42", "42", "42")).validateParams("42", "42", null));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new DeleteIdentityLinkForProcessDefinitionCmd("42", "42", "42")).validateParams(null, null, "42"));
  }

  /**
   * Method under test:
   * {@link DeleteIdentityLinkForProcessDefinitionCmd#DeleteIdentityLinkForProcessDefinitionCmd(String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkForProcessDefinitionCmd2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new DeleteIdentityLinkForProcessDefinitionCmd(null, "42", "42"));

  }

  /**
   * Method under test:
   * {@link DeleteIdentityLinkForProcessDefinitionCmd#DeleteIdentityLinkForProcessDefinitionCmd(String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkForProcessDefinitionCmd3() {
    // Arrange and Act
    DeleteIdentityLinkForProcessDefinitionCmd actualDeleteIdentityLinkForProcessDefinitionCmd = new DeleteIdentityLinkForProcessDefinitionCmd(
        "42", null, "42");

    // Assert
    assertEquals("42", actualDeleteIdentityLinkForProcessDefinitionCmd.groupId);
    assertEquals("42", actualDeleteIdentityLinkForProcessDefinitionCmd.processDefinitionId);
    assertNull(actualDeleteIdentityLinkForProcessDefinitionCmd.userId);
  }

  /**
   * Method under test:
   * {@link DeleteIdentityLinkForProcessDefinitionCmd#DeleteIdentityLinkForProcessDefinitionCmd(String, String, String)}
   */
  @Test
  public void testNewDeleteIdentityLinkForProcessDefinitionCmd4() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> new DeleteIdentityLinkForProcessDefinitionCmd("42", null, null));

  }
}
