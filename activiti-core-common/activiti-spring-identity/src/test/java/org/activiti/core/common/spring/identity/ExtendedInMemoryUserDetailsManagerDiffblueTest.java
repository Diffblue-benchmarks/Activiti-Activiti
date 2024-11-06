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
package org.activiti.core.common.spring.identity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {ExtendedInMemoryUserDetailsManager.class})
@ExtendWith(SpringExtension.class)
class ExtendedInMemoryUserDetailsManagerDiffblueTest {
  @Autowired
  private ExtendedInMemoryUserDetailsManager extendedInMemoryUserDetailsManager;

  /**
   * Test {@link ExtendedInMemoryUserDetailsManager#createUser(UserDetails)}.
   * <ul>
   *   <li>Then {@link ExtendedInMemoryUserDetailsManager} Users size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExtendedInMemoryUserDetailsManager#createUser(UserDetails)}
   */
  @Test
  @DisplayName("Test createUser(UserDetails); then ExtendedInMemoryUserDetailsManager Users size is one")
  void testCreateUser_thenExtendedInMemoryUserDetailsManagerUsersSizeIsOne() {
    // Arrange and Act
    extendedInMemoryUserDetailsManager.createUser(new User("janedoe", "iloveyou", new ArrayList<>()));

    // Assert
    List<String> users = extendedInMemoryUserDetailsManager.getUsers();
    assertEquals(1, users.size());
    assertEquals("janedoe", users.get(0));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExtendedInMemoryUserDetailsManager#getGroups()}
   *   <li>{@link ExtendedInMemoryUserDetailsManager#getUsers()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ExtendedInMemoryUserDetailsManager extendedInMemoryUserDetailsManager = new ExtendedInMemoryUserDetailsManager();

    // Act
    List<String> actualGroups = extendedInMemoryUserDetailsManager.getGroups();
    List<String> actualUsers = extendedInMemoryUserDetailsManager.getUsers();

    // Assert
    assertTrue(actualGroups.isEmpty());
    assertTrue(actualUsers.isEmpty());
  }

  /**
   * Test new {@link ExtendedInMemoryUserDetailsManager} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link ExtendedInMemoryUserDetailsManager}
   */
  @Test
  @DisplayName("Test new ExtendedInMemoryUserDetailsManager (default constructor)")
  void testNewExtendedInMemoryUserDetailsManager() {
    // Arrange and Act
    ExtendedInMemoryUserDetailsManager actualExtendedInMemoryUserDetailsManager = new ExtendedInMemoryUserDetailsManager();

    // Assert
    assertTrue(actualExtendedInMemoryUserDetailsManager.getGroups().isEmpty());
    assertTrue(actualExtendedInMemoryUserDetailsManager.getUsers().isEmpty());
  }
}
