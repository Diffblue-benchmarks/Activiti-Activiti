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
package org.activiti.bpmn.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DataStoreReferenceDiffblueTest {
  /**
   * Test {@link DataStoreReference#clone()}.
   *
   * <ul>
   *   <li>Given {@link DataStoreReference} (default constructor).
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DataStoreReference#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DataStoreReference DataStoreReference.clone()"})
  public void testClone_givenDataStoreReference_thenReturnIdIsNull() {
    // Arrange and Act
    DataStoreReference actualCloneResult = new DataStoreReference().clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDataState());
    assertNull(actualCloneResult.getDataStoreRef());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link DataStoreReference}
   *   <li>{@link DataStoreReference#setDataState(String)}
   *   <li>{@link DataStoreReference#setDataStoreRef(String)}
   *   <li>{@link DataStoreReference#setItemSubjectRef(String)}
   *   <li>{@link DataStoreReference#getDataState()}
   *   <li>{@link DataStoreReference#getDataStoreRef()}
   *   <li>{@link DataStoreReference#getItemSubjectRef()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DataStoreReference.<init>()",
    "String DataStoreReference.getDataState()",
    "String DataStoreReference.getDataStoreRef()",
    "String DataStoreReference.getItemSubjectRef()",
    "void DataStoreReference.setDataState(String)",
    "void DataStoreReference.setDataStoreRef(String)",
    "void DataStoreReference.setItemSubjectRef(String)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    DataStoreReference actualDataStoreReference = new DataStoreReference();
    actualDataStoreReference.setDataState("Data State");
    actualDataStoreReference.setDataStoreRef("Data Store Ref");
    actualDataStoreReference.setItemSubjectRef("Hello from the Dreaming Spires");
    String actualDataState = actualDataStoreReference.getDataState();
    String actualDataStoreRef = actualDataStoreReference.getDataStoreRef();

    // Assert
    assertEquals("Data State", actualDataState);
    assertEquals("Data Store Ref", actualDataStoreRef);
    assertEquals("Hello from the Dreaming Spires", actualDataStoreReference.getItemSubjectRef());
    assertNull(actualDataStoreReference.getId());
    assertNull(actualDataStoreReference.getDocumentation());
    assertNull(actualDataStoreReference.getName());
    assertNull(actualDataStoreReference.getParentContainer());
    assertEquals(0, actualDataStoreReference.getXmlColumnNumber());
    assertEquals(0, actualDataStoreReference.getXmlRowNumber());
    assertTrue(actualDataStoreReference.getExecutionListeners().isEmpty());
    assertTrue(actualDataStoreReference.getAttributes().isEmpty());
    assertTrue(actualDataStoreReference.getExtensionElements().isEmpty());
  }
}
