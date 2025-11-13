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
package org.activiti.spring.conformance.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.List;
import org.activiti.core.common.spring.identity.ExtendedInMemoryUserDetailsManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetailsService;

class RuntimeTestConfigurationDiffblueTest {
  /**
   * Test {@link RuntimeTestConfiguration#myUserDetailsService()}.
   *
   * <ul>
   *   <li>Then return {@link ExtendedInMemoryUserDetailsManager}.
   * </ul>
   *
   * <p>Method under test: {@link RuntimeTestConfiguration#myUserDetailsService()}
   */
  @Test
  @DisplayName("Test myUserDetailsService(); then return ExtendedInMemoryUserDetailsManager")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"UserDetailsService RuntimeTestConfiguration.myUserDetailsService()"})
  void testMyUserDetailsService_thenReturnExtendedInMemoryUserDetailsManager() {
    // Arrange and Act
    UserDetailsService actualMyUserDetailsServiceResult =
        new RuntimeTestConfiguration().myUserDetailsService();

    // Assert
    assertTrue(actualMyUserDetailsServiceResult instanceof ExtendedInMemoryUserDetailsManager);
    List<String> users =
        ((ExtendedInMemoryUserDetailsManager) actualMyUserDetailsServiceResult).getUsers();
    assertEquals(5, users.size());
    assertEquals("admin", users.get(4));
    assertEquals("user1", users.get(0));
    assertEquals("user2", users.get(1));
    assertEquals("user3", users.get(2));
    assertEquals("user4", users.get(3));
    assertTrue(
        ((ExtendedInMemoryUserDetailsManager) actualMyUserDetailsServiceResult)
            .getGroups()
            .isEmpty());
  }
}
