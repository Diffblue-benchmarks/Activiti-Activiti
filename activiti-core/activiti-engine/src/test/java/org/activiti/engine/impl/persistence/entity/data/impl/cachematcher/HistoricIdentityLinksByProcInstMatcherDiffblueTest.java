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
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HistoricIdentityLinksByProcInstMatcherDiffblueTest {
  /**
   * Test {@link HistoricIdentityLinksByProcInstMatcher#isRetained(HistoricIdentityLinkEntity,
   * Object)} with {@code HistoricIdentityLinkEntity}, {@code Object}.
   *
   * <p>Method under test: {@link
   * HistoricIdentityLinksByProcInstMatcher#isRetained(HistoricIdentityLinkEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean HistoricIdentityLinksByProcInstMatcher.isRetained(HistoricIdentityLinkEntity, Object)"
  })
  public void testIsRetainedWithHistoricIdentityLinkEntityObject() {
    // Arrange
    HistoricIdentityLinksByProcInstMatcher historicIdentityLinksByProcInstMatcher =
        new HistoricIdentityLinksByProcInstMatcher();

    // Act and Assert
    assertFalse(
        historicIdentityLinksByProcInstMatcher.isRetained(
            new HistoricIdentityLinkEntityImpl(), JSONObject.NULL));
  }

  /**
   * Test {@link HistoricIdentityLinksByProcInstMatcher#isRetained(HistoricIdentityLinkEntity,
   * Object)} with {@code HistoricIdentityLinkEntity}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@code 42}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricIdentityLinksByProcInstMatcher#isRetained(HistoricIdentityLinkEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean HistoricIdentityLinksByProcInstMatcher.isRetained(HistoricIdentityLinkEntity, Object)"
  })
  public void testIsRetainedWithHistoricIdentityLinkEntityObject_given42_when42_thenReturnTrue() {
    // Arrange
    HistoricIdentityLinksByProcInstMatcher historicIdentityLinksByProcInstMatcher =
        new HistoricIdentityLinksByProcInstMatcher();

    HistoricIdentityLinkEntity historicIdentityLinkEntity = mock(HistoricIdentityLinkEntity.class);
    when(historicIdentityLinkEntity.getProcessInstanceId()).thenReturn("42");

    // Act
    boolean actualIsRetainedResult =
        historicIdentityLinksByProcInstMatcher.isRetained(historicIdentityLinkEntity, "42");

    // Assert
    verify(historicIdentityLinkEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualIsRetainedResult);
  }

  /**
   * Test {@link HistoricIdentityLinksByProcInstMatcher#isRetained(HistoricIdentityLinkEntity,
   * Object)} with {@code HistoricIdentityLinkEntity}, {@code Object}.
   *
   * <ul>
   *   <li>When {@code Parameter}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricIdentityLinksByProcInstMatcher#isRetained(HistoricIdentityLinkEntity, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean HistoricIdentityLinksByProcInstMatcher.isRetained(HistoricIdentityLinkEntity, Object)"
  })
  public void testIsRetainedWithHistoricIdentityLinkEntityObject_whenParameter_thenReturnFalse() {
    // Arrange
    HistoricIdentityLinksByProcInstMatcher historicIdentityLinksByProcInstMatcher =
        new HistoricIdentityLinksByProcInstMatcher();

    HistoricIdentityLinkEntity historicIdentityLinkEntity = mock(HistoricIdentityLinkEntity.class);
    when(historicIdentityLinkEntity.getProcessInstanceId()).thenReturn("42");

    // Act
    boolean actualIsRetainedResult =
        historicIdentityLinksByProcInstMatcher.isRetained(historicIdentityLinkEntity, "Parameter");

    // Assert
    verify(historicIdentityLinkEntity, atLeast(1)).getProcessInstanceId();
    assertFalse(actualIsRetainedResult);
  }
}
