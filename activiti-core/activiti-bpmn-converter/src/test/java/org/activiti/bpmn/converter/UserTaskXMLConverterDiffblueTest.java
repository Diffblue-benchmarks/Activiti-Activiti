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
package org.activiti.bpmn.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.UserTaskXMLConverter.CustomIdentityLinkParser;
import org.activiti.bpmn.converter.UserTaskXMLConverter.HumanPerformerParser;
import org.activiti.bpmn.converter.UserTaskXMLConverter.PotentialOwnerParser;
import org.activiti.bpmn.converter.child.BaseChildElementParser;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CustomProperty;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.UserTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserTaskXMLConverterDiffblueTest {
  /**
   * Test CustomIdentityLinkParser getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link CustomIdentityLinkParser#CustomIdentityLinkParser(UserTaskXMLConverter)}
   *   <li>{@link CustomIdentityLinkParser#getElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test CustomIdentityLinkParser getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CustomIdentityLinkParser.<init>(UserTaskXMLConverter)",
    "String CustomIdentityLinkParser.getElementName()"
  })
  void testCustomIdentityLinkParserGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(
        "customResource",
        new UserTaskXMLConverter().new CustomIdentityLinkParser().getElementName());
  }

  /**
   * Test HumanPerformerParser getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link HumanPerformerParser#HumanPerformerParser(UserTaskXMLConverter)}
   *   <li>{@link HumanPerformerParser#getElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test HumanPerformerParser getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void HumanPerformerParser.<init>(UserTaskXMLConverter)",
    "String HumanPerformerParser.getElementName()"
  })
  void testHumanPerformerParserGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(
        "humanPerformer", new UserTaskXMLConverter().new HumanPerformerParser().getElementName());
  }

  /**
   * Test new {@link UserTaskXMLConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link UserTaskXMLConverter}
   */
  @Test
  @DisplayName("Test new UserTaskXMLConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskXMLConverter.<init>()"})
  void testNewUserTaskXMLConverter() {
    // Arrange and Act
    UserTaskXMLConverter actualUserTaskXMLConverter = new UserTaskXMLConverter();

    // Assert
    Map<String, BaseChildElementParser> stringBaseChildElementParserMap =
        actualUserTaskXMLConverter.childParserMap;
    assertEquals(3, stringBaseChildElementParserMap.size());
    BaseChildElementParser getResult = stringBaseChildElementParserMap.get("customResource");
    assertTrue(getResult instanceof CustomIdentityLinkParser);
    BaseChildElementParser getResult2 = stringBaseChildElementParserMap.get("humanPerformer");
    assertTrue(getResult2 instanceof HumanPerformerParser);
    BaseChildElementParser getResult3 = stringBaseChildElementParserMap.get("potentialOwner");
    assertTrue(getResult3 instanceof PotentialOwnerParser);
    assertEquals("customResource", getResult.getElementName());
    assertEquals("humanPerformer", getResult2.getElementName());
    assertEquals("potentialOwner", getResult3.getElementName());
    assertEquals("userTask", actualUserTaskXMLConverter.getXMLElementName());
    Class<UserTask> expectedBpmnElementType = UserTask.class;
    assertEquals(expectedBpmnElementType, actualUserTaskXMLConverter.getBpmnElementType());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link UserTaskXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel,
   *       XMLStreamWriter)}
   *   <li>{@link UserTaskXMLConverter#getBpmnElementType()}
   *   <li>{@link UserTaskXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Class UserTaskXMLConverter.getBpmnElementType()",
    "String UserTaskXMLConverter.getXMLElementName()",
    "void UserTaskXMLConverter.writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testGettersAndSetters() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message element =
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build();
    BpmnModel model = new BpmnModel();

    // Act
    userTaskXMLConverter.writeAdditionalChildElements(
        element, model, new IndentingXMLStreamWriter(null));
    Class<? extends BaseElement> actualBpmnElementType = userTaskXMLConverter.getBpmnElementType();

    // Assert
    assertEquals("userTask", userTaskXMLConverter.getXMLElementName());
    Class<UserTask> expectedBpmnElementType = UserTask.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }

  /**
   * Test PotentialOwnerParser getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link PotentialOwnerParser#PotentialOwnerParser(UserTaskXMLConverter)}
   *   <li>{@link PotentialOwnerParser#getElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test PotentialOwnerParser getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void PotentialOwnerParser.<init>(UserTaskXMLConverter)",
    "String PotentialOwnerParser.getElementName()"
  })
  void testPotentialOwnerParserGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(
        "potentialOwner", new UserTaskXMLConverter().new PotentialOwnerParser().getElementName());
  }

  /**
   * Test {@link UserTaskXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskXMLConverter#writeAdditionalAttributes(BaseElement,
   * BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); then calls writeAttribute(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskXMLConverter.writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testWriteAdditionalAttributes_thenCallsWriteAttribute() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();

    UserTask element = new UserTask();
    element.setPriority("Element");
    element.setExtensionId("not empty");
    element.setSkipExpression(null);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());

    // Act
    userTaskXMLConverter.writeAdditionalAttributes(
        element, model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
  }

  /**
   * Test {@link UserTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link CustomProperty} (default constructor) SimpleValue is empty string.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskXMLConverter#writeExtensionChildElements(BaseElement,
   * boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); given CustomProperty (default constructor) SimpleValue is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean UserTaskXMLConverter.writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteExtensionChildElements_givenCustomPropertySimpleValueIsEmptyString()
      throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();

    CustomProperty customProperty = new CustomProperty();
    customProperty.setSimpleValue("");

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    UserTask element = new UserTask();
    element.setCustomProperties(customProperties);
    element.setCustomUserIdentityLinks(new HashMap<>());
    element.setCustomGroupIdentityLinks(new HashMap<>());

    // Act and Assert
    assertTrue(
        userTaskXMLConverter.writeExtensionChildElements(
            element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link UserTaskXMLConverter#writeExtensionChildElements(BaseElement, boolean,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link CustomProperty} (default constructor) SimpleValue is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskXMLConverter#writeExtensionChildElements(BaseElement,
   * boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); given CustomProperty (default constructor) SimpleValue is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean UserTaskXMLConverter.writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteExtensionChildElements_givenCustomPropertySimpleValueIsNull() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();

    CustomProperty customProperty = new CustomProperty();
    customProperty.setSimpleValue(null);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    UserTask element = new UserTask();
    element.setCustomProperties(customProperties);
    element.setCustomUserIdentityLinks(new HashMap<>());
    element.setCustomGroupIdentityLinks(new HashMap<>());

    // Act and Assert
    assertTrue(
        userTaskXMLConverter.writeExtensionChildElements(
            element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link UserTaskXMLConverter#writeCustomIdentities(BaseElement, boolean, XMLStreamWriter)}
   * with {@code element}, {@code didWriteExtensionStartElement}, {@code xtw}.
   *
   * <p>Method under test: {@link UserTaskXMLConverter#writeCustomIdentities(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCustomIdentities(BaseElement, boolean, XMLStreamWriter) with 'element', 'didWriteExtensionStartElement', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean UserTaskXMLConverter.writeCustomIdentities(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteCustomIdentitiesWithElementDidWriteExtensionStartElementXtw() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();

    UserTask element = new UserTask();
    element.setCustomUserIdentityLinks(new HashMap<>());
    element.setCustomGroupIdentityLinks(new HashMap<>());

    // Act and Assert
    assertFalse(
        userTaskXMLConverter.writeCustomIdentities(
            element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link UserTaskXMLConverter#writeCustomIdentities(BaseElement, boolean, XMLStreamWriter)}
   * with {@code element}, {@code didWriteExtensionStartElement}, {@code xtw}.
   *
   * <p>Method under test: {@link UserTaskXMLConverter#writeCustomIdentities(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCustomIdentities(BaseElement, boolean, XMLStreamWriter) with 'element', 'didWriteExtensionStartElement', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean UserTaskXMLConverter.writeCustomIdentities(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteCustomIdentitiesWithElementDidWriteExtensionStartElementXtw2() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();

    UserTask element = new UserTask();
    element.setCustomUserIdentityLinks(new HashMap<>());
    element.setCustomGroupIdentityLinks(new HashMap<>());

    // Act and Assert
    assertTrue(
        userTaskXMLConverter.writeCustomIdentities(
            element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set,
   * XMLStreamWriter)} with {@code userTask}, {@code identityType}, {@code users}, {@code groups},
   * {@code xtw}.
   *
   * <p>Method under test: {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set,
   * Set, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter) with 'userTask', 'identityType', 'users', 'groups', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskXMLConverter.writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)"
  })
  void testWriteCustomIdentitiesWithUserTaskIdentityTypeUsersGroupsXtw() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();
    UserTask userTask = new UserTask();
    HashSet<String> users = new HashSet<>();
    HashSet<String> groups = new HashSet<>();

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    userTaskXMLConverter.writeCustomIdentities(userTask, "Identity Type", users, groups, xtw);

    // Assert
    verify(xtw).writeAttribute("name", "Identity Type");
    verify(xtw).writeCharacters("");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement("activiti", "customResource", "http://activiti.org/bpmn");
  }

  /**
   * Test {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set,
   * XMLStreamWriter)} with {@code userTask}, {@code identityType}, {@code users}, {@code groups},
   * {@code xtw}.
   *
   * <p>Method under test: {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set,
   * Set, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter) with 'userTask', 'identityType', 'users', 'groups', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskXMLConverter.writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)"
  })
  void testWriteCustomIdentitiesWithUserTaskIdentityTypeUsersGroupsXtw2() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();
    UserTask userTask = new UserTask();

    HashSet<String> users = new HashSet<>();
    users.add("customResource");
    users.add("activiti");
    HashSet<String> groups = new HashSet<>();

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    userTaskXMLConverter.writeCustomIdentities(userTask, "Identity Type", users, groups, xtw);

    // Assert
    verify(xtw).writeAttribute("name", "Identity Type");
    verify(xtw).writeCharacters("user(activiti),user(customResource)");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement("activiti", "customResource", "http://activiti.org/bpmn");
  }

  /**
   * Test {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set,
   * XMLStreamWriter)} with {@code userTask}, {@code identityType}, {@code users}, {@code groups},
   * {@code xtw}.
   *
   * <p>Method under test: {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set,
   * Set, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter) with 'userTask', 'identityType', 'users', 'groups', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskXMLConverter.writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)"
  })
  void testWriteCustomIdentitiesWithUserTaskIdentityTypeUsersGroupsXtw3() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();
    UserTask userTask = new UserTask();
    HashSet<String> users = new HashSet<>();

    HashSet<String> groups = new HashSet<>();
    groups.add("customResource");
    groups.add("activiti");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    userTaskXMLConverter.writeCustomIdentities(userTask, "Identity Type", users, groups, xtw);

    // Assert
    verify(xtw).writeAttribute("name", "Identity Type");
    verify(xtw).writeCharacters("group(activiti),group(customResource)");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement("activiti", "customResource", "http://activiti.org/bpmn");
  }

  /**
   * Test {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set,
   * XMLStreamWriter)} with {@code userTask}, {@code identityType}, {@code users}, {@code groups},
   * {@code xtw}.
   *
   * <ul>
   *   <li>Given {@code activiti}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set,
   * Set, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter) with 'userTask', 'identityType', 'users', 'groups', 'xtw'; given 'activiti'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskXMLConverter.writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)"
  })
  void testWriteCustomIdentitiesWithUserTaskIdentityTypeUsersGroupsXtw_givenActiviti()
      throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();
    UserTask userTask = new UserTask();

    HashSet<String> users = new HashSet<>();
    users.add("activiti");
    HashSet<String> groups = new HashSet<>();

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    userTaskXMLConverter.writeCustomIdentities(userTask, "Identity Type", users, groups, xtw);

    // Assert
    verify(xtw).writeAttribute("name", "Identity Type");
    verify(xtw).writeCharacters("user(activiti)");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement("activiti", "customResource", "http://activiti.org/bpmn");
  }

  /**
   * Test {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set,
   * XMLStreamWriter)} with {@code userTask}, {@code identityType}, {@code users}, {@code groups},
   * {@code xtw}.
   *
   * <ul>
   *   <li>Given {@code activiti}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set,
   * Set, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter) with 'userTask', 'identityType', 'users', 'groups', 'xtw'; given 'activiti'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskXMLConverter.writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)"
  })
  void testWriteCustomIdentitiesWithUserTaskIdentityTypeUsersGroupsXtw_givenActiviti2()
      throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();
    UserTask userTask = new UserTask();
    HashSet<String> users = new HashSet<>();

    HashSet<String> groups = new HashSet<>();
    groups.add("activiti");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    userTaskXMLConverter.writeCustomIdentities(userTask, "Identity Type", users, groups, xtw);

    // Assert
    verify(xtw).writeAttribute("name", "Identity Type");
    verify(xtw).writeCharacters("group(activiti)");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement("activiti", "customResource", "http://activiti.org/bpmn");
  }

  /**
   * Test {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set,
   * XMLStreamWriter)} with {@code userTask}, {@code identityType}, {@code users}, {@code groups},
   * {@code xtw}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set,
   * Set, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter) with 'userTask', 'identityType', 'users', 'groups', 'xtw'; when empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskXMLConverter.writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)"
  })
  void testWriteCustomIdentitiesWithUserTaskIdentityTypeUsersGroupsXtw_whenEmptyString()
      throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();
    UserTask userTask = new UserTask();
    HashSet<String> users = new HashSet<>();
    HashSet<String> groups = new HashSet<>();

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    userTaskXMLConverter.writeCustomIdentities(userTask, "", users, groups, xtw);

    // Assert
    verify(xtw).writeCharacters("");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement("activiti", "customResource", "http://activiti.org/bpmn");
  }

  /**
   * Test {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set,
   * XMLStreamWriter)} with {@code userTask}, {@code identityType}, {@code users}, {@code groups},
   * {@code xtw}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set,
   * Set, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter) with 'userTask', 'identityType', 'users', 'groups', 'xtw'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskXMLConverter.writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)"
  })
  void testWriteCustomIdentitiesWithUserTaskIdentityTypeUsersGroupsXtw_whenNull() throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();
    UserTask userTask = new UserTask();
    HashSet<String> users = new HashSet<>();
    HashSet<String> groups = new HashSet<>();

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    userTaskXMLConverter.writeCustomIdentities(userTask, "null", users, groups, xtw);

    // Assert
    verify(xtw).writeCharacters("");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement("activiti", "customResource", "http://activiti.org/bpmn");
  }

  /**
   * Test {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set, Set,
   * XMLStreamWriter)} with {@code userTask}, {@code identityType}, {@code users}, {@code groups},
   * {@code xtw}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskXMLConverter#writeCustomIdentities(UserTask, String, Set,
   * Set, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter) with 'userTask', 'identityType', 'users', 'groups', 'xtw'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTaskXMLConverter.writeCustomIdentities(UserTask, String, Set, Set, XMLStreamWriter)"
  })
  void testWriteCustomIdentitiesWithUserTaskIdentityTypeUsersGroupsXtw_whenNull2()
      throws Exception {
    // Arrange
    UserTaskXMLConverter userTaskXMLConverter = new UserTaskXMLConverter();
    UserTask userTask = new UserTask();
    HashSet<String> users = new HashSet<>();
    HashSet<String> groups = new HashSet<>();

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    userTaskXMLConverter.writeCustomIdentities(userTask, null, users, groups, xtw);

    // Assert
    verify(xtw).writeCharacters("");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement("activiti", "customResource", "http://activiti.org/bpmn");
  }
}
