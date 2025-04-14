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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DataSpecDiffblueTest {
  /**
   * Test {@link DataSpec#clone()}.
   * <ul>
   *   <li>Given {@link DataSpec} (default constructor) Collection is {@code true}.</li>
   *   <li>Then return Collection.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataSpec#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DataSpec DataSpec.clone()"})
  public void testClone_givenDataSpecCollectionIsTrue_thenReturnCollection() {
    // Arrange
    DataSpec dataSpec = new DataSpec();
    dataSpec.setCollection(true);

    // Act
    DataSpec actualCloneResult = dataSpec.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isCollection());
  }

  /**
   * Test {@link DataSpec#clone()}.
   * <ul>
   *   <li>Given {@link DataSpec} (default constructor).</li>
   *   <li>Then return not Collection.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataSpec#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DataSpec DataSpec.clone()"})
  public void testClone_givenDataSpec_thenReturnNotCollection() {
    // Arrange and Act
    DataSpec actualCloneResult = (new DataSpec()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isCollection());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DataSpec}
   *   <li>{@link DataSpec#setCollection(boolean)}
   *   <li>{@link DataSpec#setItemSubjectRef(String)}
   *   <li>{@link DataSpec#setName(String)}
   *   <li>{@link DataSpec#getItemSubjectRef()}
   *   <li>{@link DataSpec#getName()}
   *   <li>{@link DataSpec#isCollection()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DataSpec.<init>()", "String DataSpec.getItemSubjectRef()", "String DataSpec.getName()",
      "boolean DataSpec.isCollection()", "void DataSpec.setCollection(boolean)",
      "void DataSpec.setItemSubjectRef(String)", "void DataSpec.setName(String)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    DataSpec actualDataSpec = new DataSpec();
    actualDataSpec.setCollection(true);
    actualDataSpec.setItemSubjectRef("Hello from the Dreaming Spires");
    actualDataSpec.setName("Name");
    String actualItemSubjectRef = actualDataSpec.getItemSubjectRef();
    String actualName = actualDataSpec.getName();
    boolean actualIsCollectionResult = actualDataSpec.isCollection();

    // Assert
    assertEquals("Hello from the Dreaming Spires", actualItemSubjectRef);
    assertEquals("Name", actualName);
    assertNull(actualDataSpec.getId());
    assertEquals(0, actualDataSpec.getXmlColumnNumber());
    assertEquals(0, actualDataSpec.getXmlRowNumber());
    assertTrue(actualDataSpec.getAttributes().isEmpty());
    assertTrue(actualDataSpec.getExtensionElements().isEmpty());
    assertTrue(actualIsCollectionResult);
  }
}
