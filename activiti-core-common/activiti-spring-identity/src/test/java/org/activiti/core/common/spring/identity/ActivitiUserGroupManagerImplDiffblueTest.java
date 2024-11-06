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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class ActivitiUserGroupManagerImplDiffblueTest {
  /**
   * Test
   * {@link ActivitiUserGroupManagerImpl#ActivitiUserGroupManagerImpl(UserDetailsService)}.
   * <p>
   * Method under test:
   * {@link ActivitiUserGroupManagerImpl#ActivitiUserGroupManagerImpl(UserDetailsService)}
   */
  @Test
  @DisplayName("Test new ActivitiUserGroupManagerImpl(UserDetailsService)")
  void testNewActivitiUserGroupManagerImpl() {
    // Arrange and Act
    ActivitiUserGroupManagerImpl actualActivitiUserGroupManagerImpl = new ActivitiUserGroupManagerImpl(
        new ExtendedInMemoryUserDetailsManager());

    // Assert
    assertTrue(actualActivitiUserGroupManagerImpl.getGroups().isEmpty());
    assertTrue(actualActivitiUserGroupManagerImpl.getUsers().isEmpty());
  }

  /**
   * Test {@link ActivitiUserGroupManagerImpl#getUserGroups(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiUserGroupManagerImpl#getUserGroups(String)}
   */
  @Test
  @DisplayName("Test getUserGroups(String); then return Empty")
  void testGetUserGroups_thenReturnEmpty() throws UsernameNotFoundException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ExtendedInMemoryUserDetailsManager userDetailsService = mock(ExtendedInMemoryUserDetailsManager.class);
    when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
        .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));

    // Act
    List<String> actualUserGroups = (new ActivitiUserGroupManagerImpl(userDetailsService)).getUserGroups("janedoe");

    // Assert
    verify(userDetailsService).loadUserByUsername(eq("janedoe"));
    assertTrue(actualUserGroups.isEmpty());
  }

  /**
   * Test {@link ActivitiUserGroupManagerImpl#getUserGroups(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiUserGroupManagerImpl#getUserGroups(String)}
   */
  @Test
  @DisplayName("Test getUserGroups(String); then return Empty")
  void testGetUserGroups_thenReturnEmpty2() throws UsernameNotFoundException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("Role"));
    ExtendedInMemoryUserDetailsManager userDetailsService = mock(ExtendedInMemoryUserDetailsManager.class);
    when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
        .thenReturn(new User("janedoe", "iloveyou", authorities));

    // Act
    List<String> actualUserGroups = (new ActivitiUserGroupManagerImpl(userDetailsService)).getUserGroups("janedoe");

    // Assert
    verify(userDetailsService).loadUserByUsername(eq("janedoe"));
    assertTrue(actualUserGroups.isEmpty());
  }

  /**
   * Test {@link ActivitiUserGroupManagerImpl#getUserGroups(String)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiUserGroupManagerImpl#getUserGroups(String)}
   */
  @Test
  @DisplayName("Test getUserGroups(String); then return size is one")
  void testGetUserGroups_thenReturnSizeIsOne() throws UsernameNotFoundException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("GROUP_"));
    authorities.add(new SimpleGrantedAuthority("Role"));
    ExtendedInMemoryUserDetailsManager userDetailsService = mock(ExtendedInMemoryUserDetailsManager.class);
    when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
        .thenReturn(new User("janedoe", "iloveyou", authorities));

    // Act
    List<String> actualUserGroups = (new ActivitiUserGroupManagerImpl(userDetailsService)).getUserGroups("janedoe");

    // Assert
    verify(userDetailsService).loadUserByUsername(eq("janedoe"));
    assertEquals(1, actualUserGroups.size());
    assertEquals("", actualUserGroups.get(0));
  }

  /**
   * Test {@link ActivitiUserGroupManagerImpl#getUserRoles(String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link SimpleGrantedAuthority#SimpleGrantedAuthority(String)} with
   * {@code Role}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiUserGroupManagerImpl#getUserRoles(String)}
   */
  @Test
  @DisplayName("Test getUserRoles(String); given ArrayList() add SimpleGrantedAuthority(String) with 'Role'; then return Empty")
  void testGetUserRoles_givenArrayListAddSimpleGrantedAuthorityWithRole_thenReturnEmpty()
      throws UsernameNotFoundException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("Role"));
    ExtendedInMemoryUserDetailsManager userDetailsService = mock(ExtendedInMemoryUserDetailsManager.class);
    when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
        .thenReturn(new User("janedoe", "iloveyou", authorities));

    // Act
    List<String> actualUserRoles = (new ActivitiUserGroupManagerImpl(userDetailsService)).getUserRoles("janedoe");

    // Assert
    verify(userDetailsService).loadUserByUsername(eq("janedoe"));
    assertTrue(actualUserRoles.isEmpty());
  }

  /**
   * Test {@link ActivitiUserGroupManagerImpl#getUserRoles(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiUserGroupManagerImpl#getUserRoles(String)}
   */
  @Test
  @DisplayName("Test getUserRoles(String); then return Empty")
  void testGetUserRoles_thenReturnEmpty() throws UsernameNotFoundException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ExtendedInMemoryUserDetailsManager userDetailsService = mock(ExtendedInMemoryUserDetailsManager.class);
    when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
        .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));

    // Act
    List<String> actualUserRoles = (new ActivitiUserGroupManagerImpl(userDetailsService)).getUserRoles("janedoe");

    // Assert
    verify(userDetailsService).loadUserByUsername(eq("janedoe"));
    assertTrue(actualUserRoles.isEmpty());
  }

  /**
   * Test {@link ActivitiUserGroupManagerImpl#getUserRoles(String)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiUserGroupManagerImpl#getUserRoles(String)}
   */
  @Test
  @DisplayName("Test getUserRoles(String); then return size is one")
  void testGetUserRoles_thenReturnSizeIsOne() throws UsernameNotFoundException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_"));
    authorities.add(new SimpleGrantedAuthority("Role"));
    ExtendedInMemoryUserDetailsManager userDetailsService = mock(ExtendedInMemoryUserDetailsManager.class);
    when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
        .thenReturn(new User("janedoe", "iloveyou", authorities));

    // Act
    List<String> actualUserRoles = (new ActivitiUserGroupManagerImpl(userDetailsService)).getUserRoles("janedoe");

    // Assert
    verify(userDetailsService).loadUserByUsername(eq("janedoe"));
    assertEquals(1, actualUserRoles.size());
    assertEquals("", actualUserRoles.get(0));
  }

  /**
   * Test {@link ActivitiUserGroupManagerImpl#getGroups()}.
   * <p>
   * Method under test: {@link ActivitiUserGroupManagerImpl#getGroups()}
   */
  @Test
  @DisplayName("Test getGroups()")
  void testGetGroups() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertTrue((new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager())).getGroups().isEmpty());
  }

  /**
   * Test {@link ActivitiUserGroupManagerImpl#getGroups()}.
   * <ul>
   *   <li>Then calls {@link ExtendedInMemoryUserDetailsManager#getGroups()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiUserGroupManagerImpl#getGroups()}
   */
  @Test
  @DisplayName("Test getGroups(); then calls getGroups()")
  void testGetGroups_thenCallsGetGroups() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ExtendedInMemoryUserDetailsManager userDetailsService = mock(ExtendedInMemoryUserDetailsManager.class);
    when(userDetailsService.getGroups()).thenReturn(new ArrayList<>());

    // Act
    List<String> actualGroups = (new ActivitiUserGroupManagerImpl(userDetailsService)).getGroups();

    // Assert
    verify(userDetailsService).getGroups();
    assertTrue(actualGroups.isEmpty());
  }

  /**
   * Test {@link ActivitiUserGroupManagerImpl#getUsers()}.
   * <p>
   * Method under test: {@link ActivitiUserGroupManagerImpl#getUsers()}
   */
  @Test
  @DisplayName("Test getUsers()")
  void testGetUsers() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertTrue((new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager())).getUsers().isEmpty());
  }

  /**
   * Test {@link ActivitiUserGroupManagerImpl#getUsers()}.
   * <ul>
   *   <li>Then calls {@link ExtendedInMemoryUserDetailsManager#getUsers()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiUserGroupManagerImpl#getUsers()}
   */
  @Test
  @DisplayName("Test getUsers(); then calls getUsers()")
  void testGetUsers_thenCallsGetUsers() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ExtendedInMemoryUserDetailsManager userDetailsService = mock(ExtendedInMemoryUserDetailsManager.class);
    when(userDetailsService.getUsers()).thenReturn(new ArrayList<>());

    // Act
    List<String> actualUsers = (new ActivitiUserGroupManagerImpl(userDetailsService)).getUsers();

    // Assert
    verify(userDetailsService).getUsers();
    assertTrue(actualUsers.isEmpty());
  }
}
