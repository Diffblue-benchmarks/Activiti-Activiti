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
package org.activiti.spring.process.variable;

import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.Map;
import org.activiti.engine.ActivitiException;
import org.activiti.spring.process.model.VariableDefinition;
import org.activiti.spring.process.variable.types.VariableType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VariableParsingService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class VariableParsingServiceDiffblueTest {
  @Autowired
  private Map<String, VariableType> map;

  @Autowired
  private VariableParsingService variableParsingService;

  @MockBean
  private VariableType variableType;

  /**
   * Method under test: {@link VariableParsingService#parse(VariableDefinition)}
   */
  @Test
  void testParse() throws ActivitiException {
    // Arrange, Act and Assert
    assertNull(variableParsingService.parse(new VariableDefinition()));
  }
}
