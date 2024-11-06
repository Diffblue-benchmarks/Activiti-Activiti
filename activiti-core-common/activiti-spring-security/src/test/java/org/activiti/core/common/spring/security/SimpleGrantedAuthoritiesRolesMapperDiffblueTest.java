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
package org.activiti.core.common.spring.security;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SimpleGrantedAuthoritiesRolesMapper.class})
@ExtendWith(SpringExtension.class)
class SimpleGrantedAuthoritiesRolesMapperDiffblueTest {
  @Autowired
  private SimpleGrantedAuthoritiesRolesMapper simpleGrantedAuthoritiesRolesMapper;

  /**
   * Test {@link SimpleGrantedAuthoritiesRolesMapper#getRoles(Collection)}.
   * <ul>
   *   <li>Given {@link SimpleGrantedAuthority#SimpleGrantedAuthority(String)} with
   * {@code Role}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleGrantedAuthoritiesRolesMapper#getRoles(Collection)}
   */
  @Test
  @DisplayName("Test getRoles(Collection); given SimpleGrantedAuthority(String) with 'Role'")
  void testGetRoles_givenSimpleGrantedAuthorityWithRole() {
    // Arrange
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("Role"));

    // Act and Assert
    assertTrue(simpleGrantedAuthoritiesRolesMapper.getRoles(authorities).isEmpty());
  }

  /**
   * Test {@link SimpleGrantedAuthoritiesRolesMapper#getRoles(Collection)}.
   * <ul>
   *   <li>Given {@link SimpleGrantedAuthority#SimpleGrantedAuthority(String)} with
   * {@code Role}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleGrantedAuthoritiesRolesMapper#getRoles(Collection)}
   */
  @Test
  @DisplayName("Test getRoles(Collection); given SimpleGrantedAuthority(String) with 'Role'")
  void testGetRoles_givenSimpleGrantedAuthorityWithRole2() {
    // Arrange
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("Role"));
    authorities.add(new SimpleGrantedAuthority("Role"));

    // Act and Assert
    assertTrue(simpleGrantedAuthoritiesRolesMapper.getRoles(authorities).isEmpty());
  }

  /**
   * Test {@link SimpleGrantedAuthoritiesRolesMapper#getRoles(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleGrantedAuthoritiesRolesMapper#getRoles(Collection)}
   */
  @Test
  @DisplayName("Test getRoles(Collection); when ArrayList(); then return Empty")
  void testGetRoles_whenArrayList_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(simpleGrantedAuthoritiesRolesMapper.getRoles(new ArrayList<>()).isEmpty());
  }
}
