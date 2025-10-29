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
import java.io.UnsupportedEncodingException;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.task.IdentityLink;
import org.junit.jupiter.api.Test;

class ProcessCandidateStarterEventConverterHelperDiffblueTest {
  /**
   * Method under test:
   * {@link ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterUserLink(IdentityLink)}
   */
  @Test
  void testIsProcessCandidateStarterUserLink() {
    // Arrange
    ProcessCandidateStarterEventConverterHelper processCandidateStarterEventConverterHelper = new ProcessCandidateStarterEventConverterHelper();

    // Act and Assert
    assertFalse(
        processCandidateStarterEventConverterHelper.isProcessCandidateStarterUserLink(new IdentityLinkEntityImpl()));
  }

  /**
   * Method under test:
   * {@link ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterUserLink(IdentityLink)}
   */
  @Test
  void testIsProcessCandidateStarterUserLink2() throws UnsupportedEncodingException {
    // Arrange
    ProcessCandidateStarterEventConverterHelper processCandidateStarterEventConverterHelper = new ProcessCandidateStarterEventConverterHelper();

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
    assertFalse(processCandidateStarterEventConverterHelper.isProcessCandidateStarterUserLink(identityLink));
  }

  /**
   * Method under test:
   * {@link ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterUserLink(IdentityLink)}
   */
  @Test
  void testIsProcessCandidateStarterUserLink3() throws UnsupportedEncodingException {
    // Arrange
    ProcessCandidateStarterEventConverterHelper processCandidateStarterEventConverterHelper = new ProcessCandidateStarterEventConverterHelper();

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
    assertFalse(processCandidateStarterEventConverterHelper.isProcessCandidateStarterUserLink(identityLink));
  }

  /**
   * Method under test:
   * {@link ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterGroupLink(IdentityLink)}
   */
  @Test
  void testIsProcessCandidateStarterGroupLink() {
    // Arrange
    ProcessCandidateStarterEventConverterHelper processCandidateStarterEventConverterHelper = new ProcessCandidateStarterEventConverterHelper();

    // Act and Assert
    assertFalse(
        processCandidateStarterEventConverterHelper.isProcessCandidateStarterGroupLink(new IdentityLinkEntityImpl()));
  }

  /**
   * Method under test:
   * {@link ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterGroupLink(IdentityLink)}
   */
  @Test
  void testIsProcessCandidateStarterGroupLink2() throws UnsupportedEncodingException {
    // Arrange
    ProcessCandidateStarterEventConverterHelper processCandidateStarterEventConverterHelper = new ProcessCandidateStarterEventConverterHelper();

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
    assertFalse(processCandidateStarterEventConverterHelper.isProcessCandidateStarterGroupLink(identityLink));
  }

  /**
   * Method under test:
   * {@link ProcessCandidateStarterEventConverterHelper#isProcessCandidateStarterGroupLink(IdentityLink)}
   */
  @Test
  void testIsProcessCandidateStarterGroupLink3() throws UnsupportedEncodingException {
    // Arrange
    ProcessCandidateStarterEventConverterHelper processCandidateStarterEventConverterHelper = new ProcessCandidateStarterEventConverterHelper();

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
    assertFalse(processCandidateStarterEventConverterHelper.isProcessCandidateStarterGroupLink(identityLink));
  }
}
