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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.runtime.model.impl.VariableInstanceImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityImpl;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {APIVariableInstanceConverter.class})
@ExtendWith(SpringExtension.class)
class ListConverterDiffblueTest {
  @Autowired
  private ListConverter<org.activiti.engine.impl.persistence.entity.VariableInstance, VariableInstance> listConverter;

  /**
   * Test {@link ListConverter#from(Collection)} with {@code sources}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ListConverter#from(Collection)}
   */
  @Test
  @DisplayName("Test from(Collection) with 'sources'; then return size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ListConverter.from(Collection)"})
  void testFromWithSources_thenReturnSizeIsOne() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntityImpl = new VariableInstanceEntityImpl();
    variableInstanceEntityImpl.setType(new BigDecimalType());

    ArrayList<org.activiti.engine.impl.persistence.entity.VariableInstance> sources = new ArrayList<>();
    sources.add(variableInstanceEntityImpl);

    // Act
    List<VariableInstance> actualFromResult = listConverter.from(sources);

    // Assert
    assertEquals(1, actualFromResult.size());
    VariableInstance getResult = actualFromResult.get(0);
    assertTrue(getResult instanceof VariableInstanceImpl);
    assertEquals("bigdecimal", getResult.getType());
    assertNull(getResult.getValue());
    assertNull(getResult.getName());
    assertNull(getResult.getProcessInstanceId());
    assertNull(getResult.getTaskId());
    assertFalse(getResult.isTaskVariable());
  }

  /**
   * Test {@link ListConverter#from(Collection)} with {@code sources}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ListConverter#from(Collection)}
   */
  @Test
  @DisplayName("Test from(Collection) with 'sources'; when ArrayList(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ListConverter.from(Collection)"})
  void testFromWithSources_whenArrayList_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(listConverter.from(new ArrayList<>()).isEmpty());
  }
}
