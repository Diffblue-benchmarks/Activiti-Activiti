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
package org.activiti.core.common.spring.identity.config;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.activiti.api.runtime.shared.identity.UserGroupManager;
import org.activiti.core.common.spring.identity.ActivitiUserGroupManagerImpl;
import org.activiti.core.common.spring.identity.ExtendedInMemoryUserDetailsManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivitiSpringIdentityAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ActivitiSpringIdentityAutoConfigurationDiffblueTest {
  @Autowired
  private ActivitiSpringIdentityAutoConfiguration activitiSpringIdentityAutoConfiguration;

  @MockBean
  private UserDetailsService userDetailsService;

  /**
   * Test
   * {@link ActivitiSpringIdentityAutoConfiguration#userGroupManager(UserDetailsService)}.
   * <ul>
   *   <li>When {@link ExtendedInMemoryUserDetailsManager} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiSpringIdentityAutoConfiguration#userGroupManager(UserDetailsService)}
   */
  @Test
  @DisplayName("Test userGroupManager(UserDetailsService); when ExtendedInMemoryUserDetailsManager (default constructor)")
  void testUserGroupManager_whenExtendedInMemoryUserDetailsManager() {
    // Arrange and Act
    UserGroupManager actualUserGroupManagerResult = activitiSpringIdentityAutoConfiguration
        .userGroupManager(new ExtendedInMemoryUserDetailsManager());

    // Assert
    assertTrue(actualUserGroupManagerResult instanceof ActivitiUserGroupManagerImpl);
    assertTrue(actualUserGroupManagerResult.getGroups().isEmpty());
    assertTrue(actualUserGroupManagerResult.getUsers().isEmpty());
  }

  /**
   * Test
   * {@link ActivitiSpringIdentityAutoConfiguration#userGroupManager(UserDetailsService)}.
   * <ul>
   *   <li>When {@link ExtendedInMemoryUserDetailsManager}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiSpringIdentityAutoConfiguration#userGroupManager(UserDetailsService)}
   */
  @Test
  @DisplayName("Test userGroupManager(UserDetailsService); when ExtendedInMemoryUserDetailsManager")
  void testUserGroupManager_whenExtendedInMemoryUserDetailsManager2() {
    // Arrange and Act
    UserGroupManager actualUserGroupManagerResult = activitiSpringIdentityAutoConfiguration
        .userGroupManager(mock(ExtendedInMemoryUserDetailsManager.class));

    // Assert
    assertTrue(actualUserGroupManagerResult instanceof ActivitiUserGroupManagerImpl);
    assertTrue(actualUserGroupManagerResult.getGroups().isEmpty());
    assertTrue(actualUserGroupManagerResult.getUsers().isEmpty());
  }
}
