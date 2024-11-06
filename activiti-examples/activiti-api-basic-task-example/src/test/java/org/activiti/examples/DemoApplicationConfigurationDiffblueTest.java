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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DemoApplicationConfiguration.class})
@ExtendWith(SpringExtension.class)
class DemoApplicationConfigurationDiffblueTest {
  @Autowired
  private DemoApplicationConfiguration demoApplicationConfiguration;

  /**
   * Test {@link DemoApplicationConfiguration#myUserDetailsService()}.
   * <p>
   * Method under test:
   * {@link DemoApplicationConfiguration#myUserDetailsService()}
   */
  @Test
  @DisplayName("Test myUserDetailsService()")
  void testMyUserDetailsService() {
    // Arrange, Act and Assert
    assertTrue(demoApplicationConfiguration.myUserDetailsService() instanceof InMemoryUserDetailsManager);
  }

  /**
   * Test {@link DemoApplicationConfiguration#passwordEncoder()}.
   * <p>
   * Method under test: {@link DemoApplicationConfiguration#passwordEncoder()}
   */
  @Test
  @DisplayName("Test passwordEncoder()")
  void testPasswordEncoder() {
    // Arrange, Act and Assert
    assertTrue(demoApplicationConfiguration.passwordEncoder() instanceof BCryptPasswordEncoder);
  }
}
