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
package org.activiti.examples;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DemoApplicationConfiguration.class})
@ExtendWith(SpringExtension.class)
class DemoApplicationConfigurationDiffblueTest {
  @Autowired private DemoApplicationConfiguration demoApplicationConfiguration;

  /**
   * Test {@link DemoApplicationConfiguration#userDetailsService()}.
   *
   * <ul>
   *   <li>Given {@link DemoApplicationConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DemoApplicationConfiguration#userDetailsService()}
   */
  @Test
  @DisplayName(
      "Test userDetailsService(); given DemoApplicationConfiguration (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.security.core.userdetails.UserDetailsService DemoApplicationConfiguration.userDetailsService()"
  })
  void testUserDetailsService_givenDemoApplicationConfiguration() {
    // Arrange, Act and Assert
    assertTrue(
        new DemoApplicationConfiguration().userDetailsService()
            instanceof InMemoryUserDetailsManager);
  }

  /**
   * Test {@link DemoApplicationConfiguration#userDetailsService()}.
   *
   * <ul>
   *   <li>Then return {@link InMemoryUserDetailsManager}.
   * </ul>
   *
   * <p>Method under test: {@link DemoApplicationConfiguration#userDetailsService()}
   */
  @Test
  @DisplayName("Test userDetailsService(); then return InMemoryUserDetailsManager")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.security.core.userdetails.UserDetailsService DemoApplicationConfiguration.userDetailsService()"
  })
  void testUserDetailsService_thenReturnInMemoryUserDetailsManager() {
    // Arrange, Act and Assert
    assertTrue(
        demoApplicationConfiguration.userDetailsService() instanceof InMemoryUserDetailsManager);
  }
}
