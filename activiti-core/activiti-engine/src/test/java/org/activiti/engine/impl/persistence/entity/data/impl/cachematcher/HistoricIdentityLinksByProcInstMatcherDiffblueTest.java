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
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class HistoricIdentityLinksByProcInstMatcherDiffblueTest {
  /**
   * Method under test:
   * {@link HistoricIdentityLinksByProcInstMatcher#isRetained(HistoricIdentityLinkEntity, Object)}
   */
  @Test
  public void testIsRetained() {
    // Arrange
    HistoricIdentityLinksByProcInstMatcher historicIdentityLinksByProcInstMatcher = new HistoricIdentityLinksByProcInstMatcher();

    // Act and Assert
    assertFalse(
        historicIdentityLinksByProcInstMatcher.isRetained(new HistoricIdentityLinkEntityImpl(), JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link HistoricIdentityLinksByProcInstMatcher#isRetained(HistoricIdentityLinkEntity, Object)}
   */
  @Test
  public void testIsRetained2() {
    // Arrange
    HistoricIdentityLinksByProcInstMatcher historicIdentityLinksByProcInstMatcher = new HistoricIdentityLinksByProcInstMatcher();
    HistoricIdentityLinkEntity historicIdentityLinkEntity = mock(HistoricIdentityLinkEntity.class);
    when(historicIdentityLinkEntity.getProcessInstanceId()).thenReturn("42");

    // Act
    boolean actualIsRetainedResult = historicIdentityLinksByProcInstMatcher.isRetained(historicIdentityLinkEntity,
        "Parameter");

    // Assert
    verify(historicIdentityLinkEntity, atLeast(1)).getProcessInstanceId();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Method under test:
   * {@link HistoricIdentityLinksByProcInstMatcher#isRetained(HistoricIdentityLinkEntity, Object)}
   */
  @Test
  public void testIsRetained3() {
    // Arrange
    HistoricIdentityLinksByProcInstMatcher historicIdentityLinksByProcInstMatcher = new HistoricIdentityLinksByProcInstMatcher();
    HistoricIdentityLinkEntity historicIdentityLinkEntity = mock(HistoricIdentityLinkEntity.class);
    when(historicIdentityLinkEntity.getProcessInstanceId()).thenReturn("42");

    // Act
    boolean actualIsRetainedResult = historicIdentityLinksByProcInstMatcher.isRetained(historicIdentityLinkEntity,
        "42");

    // Assert
    verify(historicIdentityLinkEntity, atLeast(1)).getProcessInstanceId();
    assertTrue(actualIsRetainedResult);
  }
}
