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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class ActivitiUserGroupManagerImplDiffblueTest {
  @InjectMocks
  private ActivitiUserGroupManagerImpl activitiUserGroupManagerImpl;

  @Mock
  private UserDetailsService userDetailsService;

  /**
   * Test {@link ActivitiUserGroupManagerImpl#ActivitiUserGroupManagerImpl(UserDetailsService)}.
   * <p>
   * Method under test: {@link ActivitiUserGroupManagerImpl#ActivitiUserGroupManagerImpl(UserDetailsService)}
   */
  @Test
  @DisplayName("Test new ActivitiUserGroupManagerImpl(UserDetailsService)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ActivitiUserGroupManagerImpl.<init>(UserDetailsService)"})
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ActivitiUserGroupManagerImpl.getUserGroups(String)"})
  void testGetUserGroups_thenReturnEmpty() throws UsernameNotFoundException {
    // Arrange
    when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
        .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));

    // Act
    List<String> actualUserGroups = activitiUserGroupManagerImpl.getUserGroups("janedoe");

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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ActivitiUserGroupManagerImpl.getUserGroups(String)"})
  void testGetUserGroups_thenReturnEmpty2() throws UsernameNotFoundException {
    // Arrange
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("Role"));
    when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
        .thenReturn(new User("janedoe", "iloveyou", authorities));

    // Act
    List<String> actualUserGroups = activitiUserGroupManagerImpl.getUserGroups("janedoe");

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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ActivitiUserGroupManagerImpl.getUserGroups(String)"})
  void testGetUserGroups_thenReturnSizeIsOne() throws UsernameNotFoundException {
    // Arrange
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("GROUP_"));
    authorities.add(new SimpleGrantedAuthority("Role"));
    when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
        .thenReturn(new User("janedoe", "iloveyou", authorities));

    // Act
    List<String> actualUserGroups = activitiUserGroupManagerImpl.getUserGroups("janedoe");

    // Assert
    verify(userDetailsService).loadUserByUsername(eq("janedoe"));
    assertEquals(1, actualUserGroups.size());
    assertEquals("", actualUserGroups.get(0));
  }

  /**
   * Test {@link ActivitiUserGroupManagerImpl#getUserRoles(String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link SimpleGrantedAuthority#SimpleGrantedAuthority(String)} with {@code Role}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiUserGroupManagerImpl#getUserRoles(String)}
   */
  @Test
  @DisplayName("Test getUserRoles(String); given ArrayList() add SimpleGrantedAuthority(String) with 'Role'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ActivitiUserGroupManagerImpl.getUserRoles(String)"})
  void testGetUserRoles_givenArrayListAddSimpleGrantedAuthorityWithRole_thenReturnEmpty()
      throws UsernameNotFoundException {
    // Arrange
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("Role"));
    when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
        .thenReturn(new User("janedoe", "iloveyou", authorities));

    // Act
    List<String> actualUserRoles = activitiUserGroupManagerImpl.getUserRoles("janedoe");

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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ActivitiUserGroupManagerImpl.getUserRoles(String)"})
  void testGetUserRoles_thenReturnEmpty() throws UsernameNotFoundException {
    // Arrange
    when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
        .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));

    // Act
    List<String> actualUserRoles = activitiUserGroupManagerImpl.getUserRoles("janedoe");

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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ActivitiUserGroupManagerImpl.getUserRoles(String)"})
  void testGetUserRoles_thenReturnSizeIsOne() throws UsernameNotFoundException {
    // Arrange
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_"));
    authorities.add(new SimpleGrantedAuthority("Role"));
    when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
        .thenReturn(new User("janedoe", "iloveyou", authorities));

    // Act
    List<String> actualUserRoles = activitiUserGroupManagerImpl.getUserRoles("janedoe");

    // Assert
    verify(userDetailsService).loadUserByUsername(eq("janedoe"));
    assertEquals(1, actualUserRoles.size());
    assertEquals("", actualUserRoles.get(0));
  }

  /**
   * Test {@link ActivitiUserGroupManagerImpl#getGroups()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiUserGroupManagerImpl#getGroups()}
   */
  @Test
  @DisplayName("Test getGroups(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ActivitiUserGroupManagerImpl.getGroups()"})
  void testGetGroups_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager())).getGroups().isEmpty());
  }

  /**
   * Test {@link ActivitiUserGroupManagerImpl#getUsers()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiUserGroupManagerImpl#getUsers()}
   */
  @Test
  @DisplayName("Test getUsers(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ActivitiUserGroupManagerImpl.getUsers()"})
  void testGetUsers_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager())).getUsers().isEmpty());
  }
}
