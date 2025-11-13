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
package org.activiti.engine.impl.persistence.entity.data.impl.cachematcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class IdentityLinksByProcInstMatcherDiffblueTest {
  /**
   * Test {@link IdentityLinksByProcInstMatcher#isRetained(IdentityLinkEntity, Object)} with {@code
   * IdentityLinkEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@code 42}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link IdentityLinksByProcInstMatcher#isRetained(IdentityLinkEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean IdentityLinksByProcInstMatcher.isRetained(IdentityLinkEntity, Object)"
  })
  public void testIsRetainedWithIdentityLinkEntityObject_given42_when42_thenReturnTrue() {
    // Arrange
    IdentityLinksByProcInstMatcher identityLinksByProcInstMatcher =
        new IdentityLinksByProcInstMatcher();

    IdentityLinkEntity entity = mock(IdentityLinkEntity.class);
    when(entity.getProcessInstanceId()).thenReturn("42");

    // Act
    boolean actualIsRetainedResult = identityLinksByProcInstMatcher.isRetained(entity, "42");

    // Assert
    verify(entity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualIsRetainedResult);
  }

  /**
   * Test {@link IdentityLinksByProcInstMatcher#isRetained(IdentityLinkEntity, Object)} with {@code
   * IdentityLinkEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@code Parameter}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link IdentityLinksByProcInstMatcher#isRetained(IdentityLinkEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean IdentityLinksByProcInstMatcher.isRetained(IdentityLinkEntity, Object)"
  })
  public void testIsRetainedWithIdentityLinkEntityObject_given42_whenParameter_thenReturnFalse() {
    // Arrange
    IdentityLinksByProcInstMatcher identityLinksByProcInstMatcher =
        new IdentityLinksByProcInstMatcher();

    IdentityLinkEntity entity = mock(IdentityLinkEntity.class);
    when(entity.getProcessInstanceId()).thenReturn("42");

    // Act
    boolean actualIsRetainedResult = identityLinksByProcInstMatcher.isRetained(entity, "Parameter");

    // Assert
    verify(entity, atLeast(1)).getProcessInstanceId();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link IdentityLinksByProcInstMatcher#isRetained(IdentityLinkEntity, Object)} with {@code
   * IdentityLinkEntity}, {@code Object}.
   *
   * <ul>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link IdentityLinksByProcInstMatcher#isRetained(IdentityLinkEntity,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean IdentityLinksByProcInstMatcher.isRetained(IdentityLinkEntity, Object)"
  })
  public void testIsRetainedWithIdentityLinkEntityObject_whenIdentityLinkEntityImpl() {
    // Arrange
    IdentityLinksByProcInstMatcher identityLinksByProcInstMatcher =
        new IdentityLinksByProcInstMatcher();

    // Act and Assert
    assertFalse(
        identityLinksByProcInstMatcher.isRetained(new IdentityLinkEntityImpl(), JSONObject.NULL));
  }
}
