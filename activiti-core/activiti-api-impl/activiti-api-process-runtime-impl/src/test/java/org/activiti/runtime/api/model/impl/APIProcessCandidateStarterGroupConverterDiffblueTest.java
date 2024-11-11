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
package org.activiti.runtime.api.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.api.process.model.ProcessCandidateStarterGroup;
import org.activiti.api.runtime.model.impl.ProcessCandidateStarterGroupImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.task.IdentityLink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {APIProcessCandidateStarterGroupConverter.class})
@ExtendWith(SpringExtension.class)
class APIProcessCandidateStarterGroupConverterDiffblueTest {
  @Autowired
  private APIProcessCandidateStarterGroupConverter aPIProcessCandidateStarterGroupConverter;

  /**
   * Test {@link APIProcessCandidateStarterGroupConverter#from(IdentityLink)} with
   * {@code IdentityLink}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then return ProcessDefinitionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link APIProcessCandidateStarterGroupConverter#from(IdentityLink)}
   */
  @Test
  @DisplayName("Test from(IdentityLink) with 'IdentityLink'; given '42'; then return ProcessDefinitionId is '42'")
  void testFromWithIdentityLink_given42_thenReturnProcessDefinitionIdIs42() {
    // Arrange
    IdentityLinkEntityImpl identityLink = mock(IdentityLinkEntityImpl.class);
    when(identityLink.getGroupId()).thenReturn("42");
    when(identityLink.getProcessDefinitionId()).thenReturn("42");

    // Act
    ProcessCandidateStarterGroup actualFromResult = aPIProcessCandidateStarterGroupConverter.from(identityLink);

    // Assert
    verify(identityLink).getGroupId();
    verify(identityLink).getProcessDefinitionId();
    assertTrue(actualFromResult instanceof ProcessCandidateStarterGroupImpl);
    assertEquals("42", actualFromResult.getProcessDefinitionId());
    assertEquals("42", actualFromResult.getGroupId());
  }

  /**
   * Test {@link APIProcessCandidateStarterGroupConverter#from(IdentityLink)} with
   * {@code IdentityLink}.
   * <ul>
   *   <li>Then return ProcessDefinitionId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link APIProcessCandidateStarterGroupConverter#from(IdentityLink)}
   */
  @Test
  @DisplayName("Test from(IdentityLink) with 'IdentityLink'; then return ProcessDefinitionId is 'null'")
  void testFromWithIdentityLink_thenReturnProcessDefinitionIdIsNull() {
    // Arrange and Act
    ProcessCandidateStarterGroup actualFromResult = aPIProcessCandidateStarterGroupConverter
        .from(new IdentityLinkEntityImpl());

    // Assert
    assertTrue(actualFromResult instanceof ProcessCandidateStarterGroupImpl);
    assertNull(actualFromResult.getProcessDefinitionId());
    assertNull(actualFromResult.getGroupId());
  }
}