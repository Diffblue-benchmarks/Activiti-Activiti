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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ExtendedInMemoryUserDetailsManager.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
class ExtendedInMemoryUserDetailsManagerDiffblueTest {
  @Autowired private ExtendedInMemoryUserDetailsManager extendedInMemoryUserDetailsManager;

  /**
   * Test {@link ExtendedInMemoryUserDetailsManager#createUser(UserDetails)}.
   *
   * <ul>
   *   <li>Then {@link ExtendedInMemoryUserDetailsManager} (default constructor) Groups Empty.
   * </ul>
   *
   * <p>Method under test: {@link ExtendedInMemoryUserDetailsManager#createUser(UserDetails)}
   */
  @Test
  @DisplayName(
      "Test createUser(UserDetails); then ExtendedInMemoryUserDetailsManager (default constructor) Groups Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExtendedInMemoryUserDetailsManager.createUser(UserDetails)"})
  void testCreateUser_thenExtendedInMemoryUserDetailsManagerGroupsEmpty() {
    // Arrange
    ExtendedInMemoryUserDetailsManager extendedInMemoryUserDetailsManager =
        new ExtendedInMemoryUserDetailsManager();

    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("Role"));
    User user = new User("janedoe", "iloveyou", authorities);

    // Act
    extendedInMemoryUserDetailsManager.createUser(user);

    // Assert
    List<String> users = extendedInMemoryUserDetailsManager.getUsers();
    assertEquals(1, users.size());
    assertEquals("janedoe", users.get(0));
    assertTrue(extendedInMemoryUserDetailsManager.getGroups().isEmpty());
  }

  /**
   * Test {@link ExtendedInMemoryUserDetailsManager#createUser(UserDetails)}.
   *
   * <ul>
   *   <li>Then {@link ExtendedInMemoryUserDetailsManager} (default constructor) Groups size is one.
   * </ul>
   *
   * <p>Method under test: {@link ExtendedInMemoryUserDetailsManager#createUser(UserDetails)}
   */
  @Test
  @DisplayName(
      "Test createUser(UserDetails); then ExtendedInMemoryUserDetailsManager (default constructor) Groups size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExtendedInMemoryUserDetailsManager.createUser(UserDetails)"})
  void testCreateUser_thenExtendedInMemoryUserDetailsManagerGroupsSizeIsOne() {
    // Arrange
    ExtendedInMemoryUserDetailsManager extendedInMemoryUserDetailsManager =
        new ExtendedInMemoryUserDetailsManager();

    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("GROUP"));
    authorities.add(new SimpleGrantedAuthority("Role"));
    User user = new User("janedoe", "iloveyou", authorities);

    // Act
    extendedInMemoryUserDetailsManager.createUser(user);

    // Assert
    List<String> groups = extendedInMemoryUserDetailsManager.getGroups();
    assertEquals(1, groups.size());
    assertEquals("GROUP", groups.get(0));
    List<String> users = extendedInMemoryUserDetailsManager.getUsers();
    assertEquals(1, users.size());
    assertEquals("janedoe", users.get(0));
  }

  /**
   * Test {@link ExtendedInMemoryUserDetailsManager#createUser(UserDetails)}.
   *
   * <ul>
   *   <li>Then {@link ExtendedInMemoryUserDetailsManager} Users size is one.
   * </ul>
   *
   * <p>Method under test: {@link ExtendedInMemoryUserDetailsManager#createUser(UserDetails)}
   */
  @Test
  @DisplayName(
      "Test createUser(UserDetails); then ExtendedInMemoryUserDetailsManager Users size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExtendedInMemoryUserDetailsManager.createUser(UserDetails)"})
  void testCreateUser_thenExtendedInMemoryUserDetailsManagerUsersSizeIsOne() {
    // Arrange
    User user = new User("janedoe", "iloveyou", new ArrayList<>());

    // Act
    extendedInMemoryUserDetailsManager.createUser(user);

    // Assert
    List<String> users = extendedInMemoryUserDetailsManager.getUsers();
    assertEquals(1, users.size());
    assertEquals("janedoe", users.get(0));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ExtendedInMemoryUserDetailsManager#getGroups()}
   *   <li>{@link ExtendedInMemoryUserDetailsManager#getUsers()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ExtendedInMemoryUserDetailsManager.getGroups()",
    "List ExtendedInMemoryUserDetailsManager.getUsers()"
  })
  void testGettersAndSetters() {
    // Arrange
    ExtendedInMemoryUserDetailsManager extendedInMemoryUserDetailsManager =
        new ExtendedInMemoryUserDetailsManager();

    // Act
    List<String> actualGroups = extendedInMemoryUserDetailsManager.getGroups();
    List<String> actualUsers = extendedInMemoryUserDetailsManager.getUsers();

    // Assert
    assertTrue(actualGroups.isEmpty());
    assertTrue(actualUsers.isEmpty());
  }

  /**
   * Test new {@link ExtendedInMemoryUserDetailsManager} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link
   * ExtendedInMemoryUserDetailsManager}
   */
  @Test
  @DisplayName("Test new ExtendedInMemoryUserDetailsManager (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExtendedInMemoryUserDetailsManager.<init>()"})
  void testNewExtendedInMemoryUserDetailsManager() {
    // Arrange and Act
    ExtendedInMemoryUserDetailsManager actualExtendedInMemoryUserDetailsManager =
        new ExtendedInMemoryUserDetailsManager();

    // Assert
    assertTrue(actualExtendedInMemoryUserDetailsManager.getGroups().isEmpty());
    assertTrue(actualExtendedInMemoryUserDetailsManager.getUsers().isEmpty());
  }
}
