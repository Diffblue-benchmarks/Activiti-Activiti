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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.api.runtime.model.impl.ProcessDefinitionImpl;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {APIProcessDefinitionConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class APIProcessDefinitionConverterDiffblueTest {
  @Autowired
  private APIProcessDefinitionConverter aPIProcessDefinitionConverter;

  @MockBean
  private RepositoryService repositoryService;

  /**
   * Test {@link APIProcessDefinitionConverter#from(ProcessDefinition)} with
   * {@code ProcessDefinition}.
   * <ul>
   *   <li>Then return {@link ProcessDefinitionImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link APIProcessDefinitionConverter#from(org.activiti.engine.repository.ProcessDefinition)}
   */
  @Test
  @DisplayName("Test from(ProcessDefinition) with 'ProcessDefinition'; then return ProcessDefinitionImpl")
  void testFromWithProcessDefinition_thenReturnProcessDefinitionImpl() {
    // Arrange
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getStartFormKey(Mockito.<String>any())).thenReturn("Start Form Key");
    when(repositoryService.getBpmnModel(Mockito.<String>any())).thenReturn(bpmnModel);

    // Act
    org.activiti.api.process.model.ProcessDefinition actualFromResult = aPIProcessDefinitionConverter
        .from(new ProcessDefinitionEntityImpl());

    // Assert
    verify(bpmnModel).getStartFormKey(isNull());
    verify(repositoryService).getBpmnModel(isNull());
    assertTrue(actualFromResult instanceof ProcessDefinitionImpl);
    assertEquals("Start Form Key", actualFromResult.getFormKey());
    assertNull(actualFromResult.getAppVersion());
    assertNull(actualFromResult.getCategory());
    assertNull(actualFromResult.getDescription());
    assertNull(actualFromResult.getId());
    assertNull(actualFromResult.getKey());
    assertNull(actualFromResult.getName());
    assertEquals(0, actualFromResult.getVersion());
  }
}
