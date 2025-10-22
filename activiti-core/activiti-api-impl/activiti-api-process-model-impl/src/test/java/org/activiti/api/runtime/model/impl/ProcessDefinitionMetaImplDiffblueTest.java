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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProcessDefinitionMetaImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessDefinitionMetaImpl#ProcessDefinitionMetaImpl()}
   *   <li>{@link ProcessDefinitionMetaImpl#setConnectorsIds(List)}
   *   <li>{@link ProcessDefinitionMetaImpl#setGroupIds(List)}
   *   <li>{@link ProcessDefinitionMetaImpl#setProcessDefinitionKey(String)}
   *   <li>{@link ProcessDefinitionMetaImpl#setUsersIds(List)}
   *   <li>{@link ProcessDefinitionMetaImpl#getConnectorsIds()}
   *   <li>{@link ProcessDefinitionMetaImpl#getGroupIds()}
   *   <li>{@link ProcessDefinitionMetaImpl#getProcessDefinitionKey()}
   *   <li>{@link ProcessDefinitionMetaImpl#getUsersIds()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProcessDefinitionMetaImpl.<init>()", "void ProcessDefinitionMetaImpl.<init>(String)",
      "List ProcessDefinitionMetaImpl.getConnectorsIds()", "List ProcessDefinitionMetaImpl.getGroupIds()",
      "String ProcessDefinitionMetaImpl.getProcessDefinitionKey()", "List ProcessDefinitionMetaImpl.getUsersIds()",
      "void ProcessDefinitionMetaImpl.setConnectorsIds(List)", "void ProcessDefinitionMetaImpl.setGroupIds(List)",
      "void ProcessDefinitionMetaImpl.setProcessDefinitionKey(String)",
      "void ProcessDefinitionMetaImpl.setUsersIds(List)"})
  void testGettersAndSetters() {
    // Arrange and Act
    ProcessDefinitionMetaImpl actualProcessDefinitionMetaImpl = new ProcessDefinitionMetaImpl();
    ArrayList<String> connectorsIds = new ArrayList<>();
    actualProcessDefinitionMetaImpl.setConnectorsIds(connectorsIds);
    ArrayList<String> groupIds = new ArrayList<>();
    actualProcessDefinitionMetaImpl.setGroupIds(groupIds);
    actualProcessDefinitionMetaImpl.setProcessDefinitionKey("Process Definition Key");
    ArrayList<String> usersIds = new ArrayList<>();
    actualProcessDefinitionMetaImpl.setUsersIds(usersIds);
    List<String> actualConnectorsIds = actualProcessDefinitionMetaImpl.getConnectorsIds();
    List<String> actualGroupIds = actualProcessDefinitionMetaImpl.getGroupIds();
    String actualProcessDefinitionKey = actualProcessDefinitionMetaImpl.getProcessDefinitionKey();
    List<String> actualUsersIds = actualProcessDefinitionMetaImpl.getUsersIds();

    // Assert
    assertEquals("Process Definition Key", actualProcessDefinitionKey);
    assertTrue(actualConnectorsIds.isEmpty());
    assertTrue(actualGroupIds.isEmpty());
    assertTrue(actualUsersIds.isEmpty());
    assertSame(connectorsIds, actualConnectorsIds);
    assertSame(groupIds, actualGroupIds);
    assertSame(usersIds, actualUsersIds);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Process Definition Key}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessDefinitionMetaImpl#ProcessDefinitionMetaImpl(String)}
   *   <li>{@link ProcessDefinitionMetaImpl#setConnectorsIds(List)}
   *   <li>{@link ProcessDefinitionMetaImpl#setGroupIds(List)}
   *   <li>{@link ProcessDefinitionMetaImpl#setProcessDefinitionKey(String)}
   *   <li>{@link ProcessDefinitionMetaImpl#setUsersIds(List)}
   *   <li>{@link ProcessDefinitionMetaImpl#getConnectorsIds()}
   *   <li>{@link ProcessDefinitionMetaImpl#getGroupIds()}
   *   <li>{@link ProcessDefinitionMetaImpl#getProcessDefinitionKey()}
   *   <li>{@link ProcessDefinitionMetaImpl#getUsersIds()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when 'Process Definition Key'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProcessDefinitionMetaImpl.<init>()", "void ProcessDefinitionMetaImpl.<init>(String)",
      "List ProcessDefinitionMetaImpl.getConnectorsIds()", "List ProcessDefinitionMetaImpl.getGroupIds()",
      "String ProcessDefinitionMetaImpl.getProcessDefinitionKey()", "List ProcessDefinitionMetaImpl.getUsersIds()",
      "void ProcessDefinitionMetaImpl.setConnectorsIds(List)", "void ProcessDefinitionMetaImpl.setGroupIds(List)",
      "void ProcessDefinitionMetaImpl.setProcessDefinitionKey(String)",
      "void ProcessDefinitionMetaImpl.setUsersIds(List)"})
  void testGettersAndSetters_whenProcessDefinitionKey() {
    // Arrange and Act
    ProcessDefinitionMetaImpl actualProcessDefinitionMetaImpl = new ProcessDefinitionMetaImpl("Process Definition Key");
    ArrayList<String> connectorsIds = new ArrayList<>();
    actualProcessDefinitionMetaImpl.setConnectorsIds(connectorsIds);
    ArrayList<String> groupIds = new ArrayList<>();
    actualProcessDefinitionMetaImpl.setGroupIds(groupIds);
    actualProcessDefinitionMetaImpl.setProcessDefinitionKey("Process Definition Key");
    ArrayList<String> usersIds = new ArrayList<>();
    actualProcessDefinitionMetaImpl.setUsersIds(usersIds);
    List<String> actualConnectorsIds = actualProcessDefinitionMetaImpl.getConnectorsIds();
    List<String> actualGroupIds = actualProcessDefinitionMetaImpl.getGroupIds();
    String actualProcessDefinitionKey = actualProcessDefinitionMetaImpl.getProcessDefinitionKey();
    List<String> actualUsersIds = actualProcessDefinitionMetaImpl.getUsersIds();

    // Assert
    assertEquals("Process Definition Key", actualProcessDefinitionKey);
    assertTrue(actualConnectorsIds.isEmpty());
    assertTrue(actualGroupIds.isEmpty());
    assertTrue(actualUsersIds.isEmpty());
    assertSame(connectorsIds, actualConnectorsIds);
    assertSame(groupIds, actualGroupIds);
    assertSame(usersIds, actualUsersIds);
  }
}
