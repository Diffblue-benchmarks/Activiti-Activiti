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
package org.activiti.runtime.api.event.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.UnsupportedEncodingException;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.task.IdentityLink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProcessCandidateStarterEventConverterHelperDiffblueTest {
  /**
   * Test {@link
   * ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterUserLink(IdentityLink)}.
   *
   * <ul>
   *   <li>Given {@code candidate}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterUserLink(IdentityLink)}
   */
  @Test
  @DisplayName("Test isProcessCandidateStarterUserLink(IdentityLink); given 'candidate'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ProcessCandidateStarterEventConverterHelper.isProcessCandidateStarterUserLink(IdentityLink)"
  })
  void testIsProcessCandidateStarterUserLink_givenCandidate() throws UnsupportedEncodingException {
    // Arrange
    ProcessCandidateStarterEventConverterHelper processCandidateStarterEventConverterHelper =
        new ProcessCandidateStarterEventConverterHelper();

    IdentityLinkEntityImpl identityLink = new IdentityLinkEntityImpl();
    identityLink.setDeleted(true);
    identityLink.setDetails("AXAXAXAX".getBytes("UTF-8"));
    identityLink.setGroupId("42");
    identityLink.setId("42");
    identityLink.setInserted(true);
    identityLink.setProcessInstanceId("42");
    identityLink.setTaskId("42");
    identityLink.setType("candidate");
    identityLink.setUpdated(true);
    identityLink.setUserId(null);
    identityLink.setProcessDefId("Identity Link");

    // Act and Assert
    assertFalse(
        processCandidateStarterEventConverterHelper.isProcessCandidateStarterUserLink(
            identityLink));
  }

  /**
   * Test {@link
   * ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterUserLink(IdentityLink)}.
   *
   * <ul>
   *   <li>Given {@code Type}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterUserLink(IdentityLink)}
   */
  @Test
  @DisplayName("Test isProcessCandidateStarterUserLink(IdentityLink); given 'Type'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ProcessCandidateStarterEventConverterHelper.isProcessCandidateStarterUserLink(IdentityLink)"
  })
  void testIsProcessCandidateStarterUserLink_givenType() throws UnsupportedEncodingException {
    // Arrange
    ProcessCandidateStarterEventConverterHelper processCandidateStarterEventConverterHelper =
        new ProcessCandidateStarterEventConverterHelper();

    IdentityLinkEntityImpl identityLink = new IdentityLinkEntityImpl();
    identityLink.setDeleted(true);
    identityLink.setDetails("AXAXAXAX".getBytes("UTF-8"));
    identityLink.setGroupId("42");
    identityLink.setId("42");
    identityLink.setInserted(true);
    identityLink.setProcessInstanceId("42");
    identityLink.setTaskId("42");
    identityLink.setType("Type");
    identityLink.setUpdated(true);
    identityLink.setUserId(null);
    identityLink.setProcessDefId("Identity Link");

    // Act and Assert
    assertFalse(
        processCandidateStarterEventConverterHelper.isProcessCandidateStarterUserLink(
            identityLink));
  }

  /**
   * Test {@link
   * ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterUserLink(IdentityLink)}.
   *
   * <ul>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterUserLink(IdentityLink)}
   */
  @Test
  @DisplayName(
      "Test isProcessCandidateStarterUserLink(IdentityLink); when IdentityLinkEntityImpl (default constructor); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ProcessCandidateStarterEventConverterHelper.isProcessCandidateStarterUserLink(IdentityLink)"
  })
  void testIsProcessCandidateStarterUserLink_whenIdentityLinkEntityImpl_thenReturnFalse() {
    // Arrange
    ProcessCandidateStarterEventConverterHelper processCandidateStarterEventConverterHelper =
        new ProcessCandidateStarterEventConverterHelper();

    // Act and Assert
    assertFalse(
        processCandidateStarterEventConverterHelper.isProcessCandidateStarterUserLink(
            new IdentityLinkEntityImpl()));
  }

  /**
   * Test {@link
   * ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterGroupLink(IdentityLink)}.
   *
   * <ul>
   *   <li>Given {@code candidate}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterGroupLink(IdentityLink)}
   */
  @Test
  @DisplayName("Test isProcessCandidateStarterGroupLink(IdentityLink); given 'candidate'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ProcessCandidateStarterEventConverterHelper.isProcessCandidateStarterGroupLink(IdentityLink)"
  })
  void testIsProcessCandidateStarterGroupLink_givenCandidate() throws UnsupportedEncodingException {
    // Arrange
    ProcessCandidateStarterEventConverterHelper processCandidateStarterEventConverterHelper =
        new ProcessCandidateStarterEventConverterHelper();

    IdentityLinkEntityImpl identityLink = new IdentityLinkEntityImpl();
    identityLink.setDeleted(true);
    identityLink.setDetails("AXAXAXAX".getBytes("UTF-8"));
    identityLink.setId("42");
    identityLink.setInserted(true);
    identityLink.setProcessInstanceId("42");
    identityLink.setTaskId("42");
    identityLink.setType("candidate");
    identityLink.setUpdated(true);
    identityLink.setUserId("42");
    identityLink.setGroupId(null);
    identityLink.setProcessDefId("Identity Link");

    // Act and Assert
    assertFalse(
        processCandidateStarterEventConverterHelper.isProcessCandidateStarterGroupLink(
            identityLink));
  }

  /**
   * Test {@link
   * ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterGroupLink(IdentityLink)}.
   *
   * <ul>
   *   <li>Given {@code Type}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterGroupLink(IdentityLink)}
   */
  @Test
  @DisplayName("Test isProcessCandidateStarterGroupLink(IdentityLink); given 'Type'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ProcessCandidateStarterEventConverterHelper.isProcessCandidateStarterGroupLink(IdentityLink)"
  })
  void testIsProcessCandidateStarterGroupLink_givenType() throws UnsupportedEncodingException {
    // Arrange
    ProcessCandidateStarterEventConverterHelper processCandidateStarterEventConverterHelper =
        new ProcessCandidateStarterEventConverterHelper();

    IdentityLinkEntityImpl identityLink = new IdentityLinkEntityImpl();
    identityLink.setDeleted(true);
    identityLink.setDetails("AXAXAXAX".getBytes("UTF-8"));
    identityLink.setId("42");
    identityLink.setInserted(true);
    identityLink.setProcessInstanceId("42");
    identityLink.setTaskId("42");
    identityLink.setType("Type");
    identityLink.setUpdated(true);
    identityLink.setUserId("42");
    identityLink.setGroupId(null);
    identityLink.setProcessDefId("Identity Link");

    // Act and Assert
    assertFalse(
        processCandidateStarterEventConverterHelper.isProcessCandidateStarterGroupLink(
            identityLink));
  }

  /**
   * Test {@link
   * ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterGroupLink(IdentityLink)}.
   *
   * <ul>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterGroupLink(IdentityLink)}
   */
  @Test
  @DisplayName(
      "Test isProcessCandidateStarterGroupLink(IdentityLink); when IdentityLinkEntityImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ProcessCandidateStarterEventConverterHelper.isProcessCandidateStarterGroupLink(IdentityLink)"
  })
  void testIsProcessCandidateStarterGroupLink_whenIdentityLinkEntityImpl() {
    // Arrange
    ProcessCandidateStarterEventConverterHelper processCandidateStarterEventConverterHelper =
        new ProcessCandidateStarterEventConverterHelper();

    // Act and Assert
    assertFalse(
        processCandidateStarterEventConverterHelper.isProcessCandidateStarterGroupLink(
            new IdentityLinkEntityImpl()));
  }
}
