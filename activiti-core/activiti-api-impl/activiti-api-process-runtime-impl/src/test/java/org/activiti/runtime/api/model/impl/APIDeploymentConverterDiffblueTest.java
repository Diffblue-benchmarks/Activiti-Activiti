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
import org.activiti.api.runtime.model.impl.DeploymentImpl;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {APIDeploymentConverter.class})
@ExtendWith(SpringExtension.class)
class APIDeploymentConverterDiffblueTest {
  @Autowired
  private APIDeploymentConverter aPIDeploymentConverter;

  /**
   * Test {@link APIDeploymentConverter#from(Deployment)} with {@code Deployment}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>Then return ProjectReleaseVersion is {@code 1.0.2}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link APIDeploymentConverter#from(org.activiti.engine.repository.Deployment)}
   */
  @Test
  @DisplayName("Test from(Deployment) with 'Deployment'; given one; then return ProjectReleaseVersion is '1.0.2'")
  void testFromWithDeployment_givenOne_thenReturnProjectReleaseVersionIs102() {
    // Arrange
    DeploymentEntityImpl internalDeployment = mock(DeploymentEntityImpl.class);
    when(internalDeployment.getVersion()).thenReturn(1);
    when(internalDeployment.getId()).thenReturn("42");
    when(internalDeployment.getName()).thenReturn("Name");
    when(internalDeployment.getProjectReleaseVersion()).thenReturn("1.0.2");

    // Act
    org.activiti.api.process.model.Deployment actualFromResult = aPIDeploymentConverter.from(internalDeployment);

    // Assert
    verify(internalDeployment).getId();
    verify(internalDeployment).getName();
    verify(internalDeployment).getProjectReleaseVersion();
    verify(internalDeployment).getVersion();
    assertTrue(actualFromResult instanceof DeploymentImpl);
    assertEquals("1.0.2", actualFromResult.getProjectReleaseVersion());
    assertEquals("42", actualFromResult.getId());
    assertEquals("Name", actualFromResult.getName());
    assertEquals(1, actualFromResult.getVersion().intValue());
  }

  /**
   * Test {@link APIDeploymentConverter#from(Deployment)} with {@code Deployment}.
   * <ul>
   *   <li>When {@link DeploymentEntityImpl} (default constructor).</li>
   *   <li>Then return Version is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link APIDeploymentConverter#from(org.activiti.engine.repository.Deployment)}
   */
  @Test
  @DisplayName("Test from(Deployment) with 'Deployment'; when DeploymentEntityImpl (default constructor); then return Version is 'null'")
  void testFromWithDeployment_whenDeploymentEntityImpl_thenReturnVersionIsNull() {
    // Arrange and Act
    org.activiti.api.process.model.Deployment actualFromResult = aPIDeploymentConverter
        .from(new DeploymentEntityImpl());

    // Assert
    assertTrue(actualFromResult instanceof DeploymentImpl);
    assertNull(actualFromResult.getVersion());
    assertNull(actualFromResult.getId());
    assertNull(actualFromResult.getName());
    assertNull(actualFromResult.getProjectReleaseVersion());
  }
}
