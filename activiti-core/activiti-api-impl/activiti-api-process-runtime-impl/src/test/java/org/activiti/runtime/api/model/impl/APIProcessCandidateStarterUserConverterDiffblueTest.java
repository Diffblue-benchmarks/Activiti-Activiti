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

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.process.model.ProcessCandidateStarterUser;
import org.activiti.api.runtime.model.impl.ProcessCandidateStarterUserImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.task.IdentityLink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {APIProcessCandidateStarterUserConverter.class})
@ExtendWith(SpringExtension.class)
class APIProcessCandidateStarterUserConverterDiffblueTest {
  @Autowired
  private APIProcessCandidateStarterUserConverter aPIProcessCandidateStarterUserConverter;

  /**
   * Test {@link APIProcessCandidateStarterUserConverter#from(IdentityLink)} with {@code IdentityLink}.
   * <ul>
   *   <li>Then return {@link ProcessCandidateStarterUserImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link APIProcessCandidateStarterUserConverter#from(IdentityLink)}
   */
  @Test
  @DisplayName("Test from(IdentityLink) with 'IdentityLink'; then return ProcessCandidateStarterUserImpl")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProcessCandidateStarterUser APIProcessCandidateStarterUserConverter.from(IdentityLink)"})
  void testFromWithIdentityLink_thenReturnProcessCandidateStarterUserImpl() {
    // Arrange and Act
    ProcessCandidateStarterUser actualFromResult = aPIProcessCandidateStarterUserConverter
        .from(new IdentityLinkEntityImpl());

    // Assert
    assertTrue(actualFromResult instanceof ProcessCandidateStarterUserImpl);
    assertNull(actualFromResult.getProcessDefinitionId());
    assertNull(actualFromResult.getUserId());
  }
}
