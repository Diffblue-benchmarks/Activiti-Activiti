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
package org.activiti.core.common.spring.security.policies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SecurityPolicyDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SecurityPolicy}
   *   <li>{@link SecurityPolicy#setAccess(SecurityPolicyAccess)}
   *   <li>{@link SecurityPolicy#setGroups(List)}
   *   <li>{@link SecurityPolicy#setKeys(List)}
   *   <li>{@link SecurityPolicy#setName(String)}
   *   <li>{@link SecurityPolicy#setServiceName(String)}
   *   <li>{@link SecurityPolicy#setUsers(List)}
   *   <li>{@link SecurityPolicy#getAccess()}
   *   <li>{@link SecurityPolicy#getGroups()}
   *   <li>{@link SecurityPolicy#getKeys()}
   *   <li>{@link SecurityPolicy#getName()}
   *   <li>{@link SecurityPolicy#getServiceName()}
   *   <li>{@link SecurityPolicy#getUsers()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    SecurityPolicy actualSecurityPolicy = new SecurityPolicy();
    actualSecurityPolicy.setAccess(SecurityPolicyAccess.NONE);
    ArrayList<String> groups = new ArrayList<>();
    actualSecurityPolicy.setGroups(groups);
    ArrayList<String> keys = new ArrayList<>();
    actualSecurityPolicy.setKeys(keys);
    actualSecurityPolicy.setName("Name");
    actualSecurityPolicy.setServiceName("Service Name");
    ArrayList<String> users = new ArrayList<>();
    actualSecurityPolicy.setUsers(users);
    SecurityPolicyAccess actualAccess = actualSecurityPolicy.getAccess();
    List<String> actualGroups = actualSecurityPolicy.getGroups();
    List<String> actualKeys = actualSecurityPolicy.getKeys();
    String actualName = actualSecurityPolicy.getName();
    String actualServiceName = actualSecurityPolicy.getServiceName();
    List<String> actualUsers = actualSecurityPolicy.getUsers();

    // Assert that nothing has changed
    assertEquals("Name", actualName);
    assertEquals("Service Name", actualServiceName);
    assertEquals(SecurityPolicyAccess.NONE, actualAccess);
    assertTrue(actualGroups.isEmpty());
    assertTrue(actualKeys.isEmpty());
    assertTrue(actualUsers.isEmpty());
    assertSame(groups, actualGroups);
    assertSame(keys, actualKeys);
    assertSame(users, actualUsers);
  }
}
