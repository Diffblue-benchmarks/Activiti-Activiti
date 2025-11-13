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
package org.activiti.engine.impl.bpmn.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import groovy.lang.GroovyClassLoader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.bpmn.behavior.AdhocSubProcessActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultListenerFactory;
import org.activiti.engine.impl.bpmn.parser.factory.ListenerFactory;
import org.activiti.engine.impl.bpmn.parser.handler.AdhocSubProcessParseHandler;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.activiti.engine.impl.util.io.ResourceStreamSource;
import org.activiti.engine.impl.util.io.StreamSource;
import org.activiti.engine.impl.util.io.StringStreamSource;
import org.activiti.engine.impl.util.io.UrlStreamSource;
import org.activiti.engine.test.util.TestProcessUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BpmnParseDiffblueTest {
  /**
   * Test {@link BpmnParse#BpmnParse(BpmnParser)}.
   *
   * <ul>
   *   <li>When {@link BpmnParser} (default constructor).
   *   <li>Then return TargetNamespace is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#BpmnParse(BpmnParser)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.<init>(BpmnParser)"})
  public void testNewBpmnParse_whenBpmnParser_thenReturnTargetNamespaceIsNull() {
    // Arrange and Act
    BpmnParse actualBpmnParse = new BpmnParse(new BpmnParser());

    // Assert
    assertNull(actualBpmnParse.getTargetNamespace());
    assertNull(actualBpmnParse.name);
    assertNull(actualBpmnParse.sourceSystemId);
    assertNull(actualBpmnParse.getSequenceFlows());
    assertNull(actualBpmnParse.getBpmnModel());
    assertNull(actualBpmnParse.getCurrentFlowElement());
    assertNull(actualBpmnParse.getCurrentProcess());
    assertNull(actualBpmnParse.getCurrentSubProcess());
    assertNull(actualBpmnParse.getBpmnParserHandlers());
    assertNull(actualBpmnParse.getActivityBehaviorFactory());
    assertNull(actualBpmnParse.getListenerFactory());
    assertNull(actualBpmnParse.getDeployment());
    assertNull(actualBpmnParse.getCurrentProcessDefinition());
    assertNull(actualBpmnParse.streamSource);
    assertTrue(actualBpmnParse.currentSubprocessStack.isEmpty());
    assertTrue(actualBpmnParse.getProcessDefinitions().isEmpty());
    assertTrue(actualBpmnParse.prefixs.isEmpty());
    assertTrue(actualBpmnParse.isValidateProcess());
    assertTrue(actualBpmnParse.isValidateSchema());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link BpmnParse#deployment(DeploymentEntity)}
   *   <li>{@link BpmnParse#name(String)}
   *   <li>{@link BpmnParse#setActivityBehaviorFactory(ActivityBehaviorFactory)}
   *   <li>{@link BpmnParse#setBpmnModel(BpmnModel)}
   *   <li>{@link BpmnParse#setBpmnParserHandlers(BpmnParseHandlers)}
   *   <li>{@link BpmnParse#setCurrentFlowElement(FlowElement)}
   *   <li>{@link BpmnParse#setCurrentProcess(Process)}
   *   <li>{@link BpmnParse#setCurrentProcessDefinition(ProcessDefinitionEntity)}
   *   <li>{@link BpmnParse#setDeployment(DeploymentEntity)}
   *   <li>{@link BpmnParse#setListenerFactory(ListenerFactory)}
   *   <li>{@link BpmnParse#setSourceSystemId(String)}
   *   <li>{@link BpmnParse#setValidateProcess(boolean)}
   *   <li>{@link BpmnParse#setValidateSchema(boolean)}
   *   <li>{@link BpmnParse#getActivityBehaviorFactory()}
   *   <li>{@link BpmnParse#getBpmnModel()}
   *   <li>{@link BpmnParse#getBpmnParserHandlers()}
   *   <li>{@link BpmnParse#getCurrentFlowElement()}
   *   <li>{@link BpmnParse#getCurrentProcess()}
   *   <li>{@link BpmnParse#getCurrentProcessDefinition()}
   *   <li>{@link BpmnParse#getDeployment()}
   *   <li>{@link BpmnParse#getListenerFactory()}
   *   <li>{@link BpmnParse#getProcessDefinitions()}
   *   <li>{@link BpmnParse#getSequenceFlows()}
   *   <li>{@link BpmnParse#getTargetNamespace()}
   *   <li>{@link BpmnParse#isValidateProcess()}
   *   <li>{@link BpmnParse#isValidateSchema()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BpmnParse BpmnParse.deployment(DeploymentEntity)",
    "ActivityBehaviorFactory BpmnParse.getActivityBehaviorFactory()",
    "BpmnModel BpmnParse.getBpmnModel()",
    "BpmnParseHandlers BpmnParse.getBpmnParserHandlers()",
    "FlowElement BpmnParse.getCurrentFlowElement()",
    "Process BpmnParse.getCurrentProcess()",
    "ProcessDefinitionEntity BpmnParse.getCurrentProcessDefinition()",
    "DeploymentEntity BpmnParse.getDeployment()",
    "ListenerFactory BpmnParse.getListenerFactory()",
    "List BpmnParse.getProcessDefinitions()",
    "Map BpmnParse.getSequenceFlows()",
    "String BpmnParse.getTargetNamespace()",
    "boolean BpmnParse.isValidateProcess()",
    "boolean BpmnParse.isValidateSchema()",
    "BpmnParse BpmnParse.name(String)",
    "void BpmnParse.setActivityBehaviorFactory(ActivityBehaviorFactory)",
    "void BpmnParse.setBpmnModel(BpmnModel)",
    "void BpmnParse.setBpmnParserHandlers(BpmnParseHandlers)",
    "void BpmnParse.setCurrentFlowElement(FlowElement)",
    "void BpmnParse.setCurrentProcess(Process)",
    "void BpmnParse.setCurrentProcessDefinition(ProcessDefinitionEntity)",
    "void BpmnParse.setDeployment(DeploymentEntity)",
    "void BpmnParse.setListenerFactory(ListenerFactory)",
    "BpmnParse BpmnParse.setSourceSystemId(String)",
    "void BpmnParse.setValidateProcess(boolean)",
    "void BpmnParse.setValidateSchema(boolean)"
  })
  public void testGettersAndSetters() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act
    BpmnParse actualDeploymentResult = bpmnParse.deployment(new DeploymentEntityImpl());
    BpmnParse actualNameResult = bpmnParse.name("Name");
    DefaultActivityBehaviorFactory activityBehaviorFactory = new DefaultActivityBehaviorFactory();
    bpmnParse.setActivityBehaviorFactory(activityBehaviorFactory);
    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    bpmnParse.setBpmnModel(bpmnModel);
    BpmnParseHandlers bpmnParserHandlers = new BpmnParseHandlers();
    bpmnParse.setBpmnParserHandlers(bpmnParserHandlers);
    AdhocSubProcess currentFlowElement = new AdhocSubProcess();
    bpmnParse.setCurrentFlowElement(currentFlowElement);
    Process currentProcess = TestProcessUtil.createOneTaskProcessWithId("42");
    bpmnParse.setCurrentProcess(currentProcess);
    ProcessDefinitionEntityImpl currentProcessDefinition = new ProcessDefinitionEntityImpl();
    bpmnParse.setCurrentProcessDefinition(currentProcessDefinition);
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    bpmnParse.setDeployment(deployment);
    DefaultListenerFactory listenerFactory = new DefaultListenerFactory();
    bpmnParse.setListenerFactory(listenerFactory);
    BpmnParse actualSetSourceSystemIdResult = bpmnParse.setSourceSystemId("42");
    bpmnParse.setValidateProcess(true);
    bpmnParse.setValidateSchema(true);
    ActivityBehaviorFactory actualActivityBehaviorFactory = bpmnParse.getActivityBehaviorFactory();
    BpmnModel actualBpmnModel = bpmnParse.getBpmnModel();
    BpmnParseHandlers actualBpmnParserHandlers = bpmnParse.getBpmnParserHandlers();
    FlowElement actualCurrentFlowElement = bpmnParse.getCurrentFlowElement();
    Process actualCurrentProcess = bpmnParse.getCurrentProcess();
    ProcessDefinitionEntity actualCurrentProcessDefinition =
        bpmnParse.getCurrentProcessDefinition();
    DeploymentEntity actualDeployment = bpmnParse.getDeployment();
    ListenerFactory actualListenerFactory = bpmnParse.getListenerFactory();
    List<ProcessDefinitionEntity> actualProcessDefinitions = bpmnParse.getProcessDefinitions();
    Map<String, SequenceFlow> actualSequenceFlows = bpmnParse.getSequenceFlows();
    String actualTargetNamespace = bpmnParse.getTargetNamespace();
    boolean actualIsValidateProcessResult = bpmnParse.isValidateProcess();
    boolean actualIsValidateSchemaResult = bpmnParse.isValidateSchema();

    // Assert
    assertNull(actualTargetNamespace);
    assertNull(actualSequenceFlows);
    assertTrue(actualProcessDefinitions.isEmpty());
    assertTrue(actualIsValidateProcessResult);
    assertTrue(actualIsValidateSchemaResult);
    assertSame(currentFlowElement, actualCurrentFlowElement);
    assertSame(bpmnParse, actualDeploymentResult);
    assertSame(bpmnParse, actualNameResult);
    assertSame(bpmnParse, actualSetSourceSystemIdResult);
    assertSame(bpmnParserHandlers, actualBpmnParserHandlers);
    assertSame(activityBehaviorFactory, actualActivityBehaviorFactory);
    assertSame(listenerFactory, actualListenerFactory);
    assertSame(deployment, actualDeployment);
    assertSame(currentProcessDefinition, actualCurrentProcessDefinition);
    assertSame(bpmnModel, actualBpmnModel);
    assertSame(currentProcess, actualCurrentProcess);
  }

  /**
   * Test {@link BpmnParse#execute()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#execute()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParse BpmnParse.execute()"})
  public void testExecute_thenThrowActivitiException() throws IOException {
    // Arrange
    DataInputStream dataInputStream = mock(DataInputStream.class);
    when(dataInputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt()))
        .thenThrow(new ActivitiException("An error occurred"));
    doThrow(new ActivitiException("An error occurred")).when(dataInputStream).close();

    StreamSource streamSource = mock(StreamSource.class);
    when(streamSource.getInputStream()).thenReturn(dataInputStream);

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setStreamSource(streamSource);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> bpmnParse.execute());
    verify(dataInputStream).read(isA(byte[].class), eq(0), eq(8192));
    verify(dataInputStream).close();
    verify(streamSource).getInputStream();
  }

  /**
   * Test {@link BpmnParse#sourceInputStream(InputStream)}.
   *
   * <p>Method under test: {@link BpmnParse#sourceInputStream(InputStream)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceInputStream(InputStream)"})
  public void testSourceInputStream() throws UnsupportedEncodingException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act
    BpmnParse actualSourceInputStreamResult =
        bpmnParse.sourceInputStream(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Assert
    assertTrue(bpmnParse.streamSource instanceof InputStreamSource);
    assertEquals("inputStream", bpmnParse.name);
    assertSame(bpmnParse, actualSourceInputStreamResult);
  }

  /**
   * Test {@link BpmnParse#sourceInputStream(InputStream)}.
   *
   * <ul>
   *   <li>Given {@link BpmnParse#BpmnParse(BpmnParser)} with parser is {@link BpmnParser} (default
   *       constructor) name {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#sourceInputStream(InputStream)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceInputStream(InputStream)"})
  public void testSourceInputStream_givenBpmnParseWithParserIsBpmnParserNameFoo()
      throws UnsupportedEncodingException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.name("foo");
    bpmnParse.setStreamSource(mock(StreamSource.class));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> bpmnParse.sourceInputStream(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
  }

  /**
   * Test {@link BpmnParse#sourceInputStream(InputStream)}.
   *
   * <ul>
   *   <li>Given {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code AXAXAXAX}
   *       Bytes is {@code UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#sourceInputStream(InputStream)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceInputStream(InputStream)"})
  public void testSourceInputStream_givenByteArrayInputStreamWithAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setStreamSource(
        new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> bpmnParse.sourceInputStream(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
  }

  /**
   * Test {@link BpmnParse#sourceResource(String)} with {@code resource}.
   *
   * <p>Method under test: {@link BpmnParse#sourceResource(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceResource(String)"})
  public void testSourceResourceWithResource() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act
    BpmnParse actualSourceResourceResult = bpmnParse.sourceResource("Resource");

    // Assert
    assertTrue(bpmnParse.streamSource instanceof ResourceStreamSource);
    assertEquals("Resource", bpmnParse.name);
    assertSame(bpmnParse, actualSourceResourceResult);
  }

  /**
   * Test {@link BpmnParse#sourceResource(String, ClassLoader)} with {@code resource}, {@code
   * classLoader}.
   *
   * <p>Method under test: {@link BpmnParse#sourceResource(String, ClassLoader)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceResource(String, ClassLoader)"})
  public void testSourceResourceWithResourceClassLoader() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act
    BpmnParse actualSourceResourceResult =
        bpmnParse.sourceResource("Resource", new GroovyClassLoader());

    // Assert
    assertTrue(bpmnParse.streamSource instanceof ResourceStreamSource);
    assertEquals("Resource", bpmnParse.name);
    assertSame(bpmnParse, actualSourceResourceResult);
  }

  /**
   * Test {@link BpmnParse#sourceResource(String, ClassLoader)} with {@code resource}, {@code
   * classLoader}.
   *
   * <p>Method under test: {@link BpmnParse#sourceResource(String, ClassLoader)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceResource(String, ClassLoader)"})
  public void testSourceResourceWithResourceClassLoader2() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.name("foo");
    bpmnParse.setStreamSource(mock(StreamSource.class));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> bpmnParse.sourceResource("Resource", new GroovyClassLoader()));
  }

  /**
   * Test {@link BpmnParse#sourceResource(String, ClassLoader)} with {@code resource}, {@code
   * classLoader}.
   *
   * <p>Method under test: {@link BpmnParse#sourceResource(String, ClassLoader)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceResource(String, ClassLoader)"})
  public void testSourceResourceWithResourceClassLoader3() throws UnsupportedEncodingException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setStreamSource(
        new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> bpmnParse.sourceResource("Resource", new GroovyClassLoader()));
  }

  /**
   * Test {@link BpmnParse#sourceResource(String)} with {@code resource}.
   *
   * <ul>
   *   <li>Given {@link BpmnParse#BpmnParse(BpmnParser)} with parser is {@link BpmnParser} (default
   *       constructor) name {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#sourceResource(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceResource(String)"})
  public void testSourceResourceWithResource_givenBpmnParseWithParserIsBpmnParserNameFoo() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.name("foo");
    bpmnParse.setStreamSource(mock(StreamSource.class));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> bpmnParse.sourceResource("Resource"));
  }

  /**
   * Test {@link BpmnParse#sourceResource(String)} with {@code resource}.
   *
   * <ul>
   *   <li>Given {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code AXAXAXAX}
   *       Bytes is {@code UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#sourceResource(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceResource(String)"})
  public void testSourceResourceWithResource_givenByteArrayInputStreamWithAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setStreamSource(
        new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> bpmnParse.sourceResource("Resource"));
  }

  /**
   * Test {@link BpmnParse#sourceString(String)}.
   *
   * <p>Method under test: {@link BpmnParse#sourceString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceString(String)"})
  public void testSourceString() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act
    BpmnParse actualSourceStringResult = bpmnParse.sourceString("String");

    // Assert
    assertTrue(bpmnParse.streamSource instanceof StringStreamSource);
    assertEquals("string", bpmnParse.name);
    assertSame(bpmnParse, actualSourceStringResult);
  }

  /**
   * Test {@link BpmnParse#sourceString(String)}.
   *
   * <ul>
   *   <li>Given {@link BpmnParse#BpmnParse(BpmnParser)} with parser is {@link BpmnParser} (default
   *       constructor) name {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#sourceString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceString(String)"})
  public void testSourceString_givenBpmnParseWithParserIsBpmnParserNameFoo() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.name("foo");
    bpmnParse.setStreamSource(mock(StreamSource.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> bpmnParse.sourceString("String"));
  }

  /**
   * Test {@link BpmnParse#sourceString(String)}.
   *
   * <ul>
   *   <li>Given {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code AXAXAXAX}
   *       Bytes is {@code UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#sourceString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceString(String)"})
  public void testSourceString_givenByteArrayInputStreamWithAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setStreamSource(
        new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> bpmnParse.sourceString("String"));
  }

  /**
   * Test {@link BpmnParse#sourceUrl(URL)} with {@code URL}.
   *
   * <p>Method under test: {@link BpmnParse#sourceUrl(URL)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceUrl(URL)"})
  public void testSourceUrlWithUrl() throws MalformedURLException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act
    BpmnParse actualSourceUrlResult =
        bpmnParse.sourceUrl(
            Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    // Assert
    assertTrue(bpmnParse.streamSource instanceof UrlStreamSource);
    String expectedString =
        String.join(
            "", "file:", Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toString());
    assertEquals(expectedString, bpmnParse.name);
    assertSame(bpmnParse, actualSourceUrlResult);
  }

  /**
   * Test {@link BpmnParse#sourceUrl(URL)} with {@code URL}.
   *
   * <ul>
   *   <li>Given {@link BpmnParse#BpmnParse(BpmnParser)} with parser is {@link BpmnParser} (default
   *       constructor) name {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#sourceUrl(URL)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceUrl(URL)"})
  public void testSourceUrlWithUrl_givenBpmnParseWithParserIsBpmnParserNameFoo()
      throws MalformedURLException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.name("foo");
    bpmnParse.setStreamSource(mock(StreamSource.class));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            bpmnParse.sourceUrl(
                Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL()));
  }

  /**
   * Test {@link BpmnParse#sourceUrl(URL)} with {@code URL}.
   *
   * <ul>
   *   <li>Given {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code AXAXAXAX}
   *       Bytes is {@code UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#sourceUrl(URL)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceUrl(URL)"})
  public void testSourceUrlWithUrl_givenByteArrayInputStreamWithAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException, MalformedURLException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setStreamSource(
        new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            bpmnParse.sourceUrl(
                Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL()));
  }

  /**
   * Test {@link BpmnParse#setStreamSource(StreamSource)}.
   *
   * <p>Method under test: {@link BpmnParse#setStreamSource(StreamSource)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.setStreamSource(StreamSource)"})
  public void testSetStreamSource() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setStreamSource(mock(StreamSource.class));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> bpmnParse.setStreamSource(mock(StreamSource.class)));
  }

  /**
   * Test {@link BpmnParse#setStreamSource(StreamSource)}.
   *
   * <ul>
   *   <li>Given {@link BpmnParse#BpmnParse(BpmnParser)} with parser is {@link BpmnParser} (default
   *       constructor).
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#setStreamSource(StreamSource)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.setStreamSource(StreamSource)"})
  public void testSetStreamSource_givenBpmnParseWithParserIsBpmnParser_thenDoesNotThrow() {
    // Arrange, Act and Assert
    new BpmnParse(new BpmnParser()).setStreamSource(mock(StreamSource.class));
  }

  /**
   * Test {@link BpmnParse#setStreamSource(StreamSource)}.
   *
   * <ul>
   *   <li>Given {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code AXAXAXAX}
   *       Bytes is {@code UTF-8}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#setStreamSource(StreamSource)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.setStreamSource(StreamSource)"})
  public void testSetStreamSource_givenByteArrayInputStreamWithAxaxaxaxBytesIsUtf8_whenNull()
      throws UnsupportedEncodingException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setStreamSource(
        new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> bpmnParse.setStreamSource(null));
  }

  /**
   * Test {@link BpmnParse#setStreamSource(StreamSource)}.
   *
   * <ul>
   *   <li>When {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code AXAXAXAX}
   *       Bytes is {@code UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#setStreamSource(StreamSource)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.setStreamSource(StreamSource)"})
  public void testSetStreamSource_whenByteArrayInputStreamWithAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setStreamSource(
        new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            bpmnParse.setStreamSource(
                new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))));
  }

  /**
   * Test {@link BpmnParse#applyParseHandlers()}.
   *
   * <ul>
   *   <li>Then {@link BpmnParse#BpmnParse(BpmnParser)} with parser is {@link BpmnParser} (default
   *       constructor) SequenceFlows Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#applyParseHandlers()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.applyParseHandlers()"})
  public void testApplyParseHandlers_thenBpmnParseWithParserIsBpmnParserSequenceFlowsEmpty() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(new BpmnModel());

    // Act
    bpmnParse.applyParseHandlers();

    // Assert
    assertTrue(bpmnParse.getSequenceFlows().isEmpty());
  }

  /**
   * Test {@link BpmnParse#processFlowElements(Collection)}.
   *
   * <p>Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.processFlowElements(Collection)"})
  public void testProcessFlowElements() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnParserHandlers(new BpmnParseHandlers());

    LinkedHashSet<FlowElement> flowElements = new LinkedHashSet<>();
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    flowElements.add(adhocSubProcess);

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert
    assertSame(adhocSubProcess, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link BpmnParse#processFlowElements(Collection)}.
   *
   * <p>Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.processFlowElements(Collection)"})
  public void testProcessFlowElements2() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnParserHandlers(new BpmnParseHandlers());

    LinkedHashSet<FlowElement> flowElements = new LinkedHashSet<>();
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    flowElements.add(boundaryEvent);

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert
    assertSame(boundaryEvent, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link BpmnParse#processFlowElements(Collection)}.
   *
   * <p>Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.processFlowElements(Collection)"})
  public void testProcessFlowElements3() {
    // Arrange
    BpmnParseHandlers bpmnParserHandlers = new BpmnParseHandlers();
    bpmnParserHandlers.addHandler(new AdhocSubProcessParseHandler());

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    bpmnParse.setBpmnParserHandlers(bpmnParserHandlers);

    LinkedHashSet<FlowElement> flowElements = new LinkedHashSet<>();
    flowElements.add(new AdhocSubProcess());

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert
    FlowElement currentFlowElement = bpmnParse.getCurrentFlowElement();
    Collection<Artifact> artifacts = ((AdhocSubProcess) currentFlowElement).getArtifacts();
    assertTrue(artifacts instanceof List);
    assertTrue(currentFlowElement instanceof AdhocSubProcess);
    Object behavior = ((AdhocSubProcess) currentFlowElement).getBehavior();
    assertTrue(behavior instanceof AdhocSubProcessActivityBehavior);
    assertNull(((AdhocSubProcessActivityBehavior) behavior).getMultiInstanceActivityBehavior());
    assertTrue(artifacts.isEmpty());
  }

  /**
   * Test {@link BpmnParse#processFlowElements(Collection)}.
   *
   * <p>Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.processFlowElements(Collection)"})
  public void testProcessFlowElements4() {
    // Arrange
    BpmnParseHandlers bpmnParserHandlers = new BpmnParseHandlers();
    bpmnParserHandlers.addHandler(new AdhocSubProcessParseHandler());

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(TestProcessUtil.createOneTaskBpmnModel());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    bpmnParse.setBpmnParserHandlers(bpmnParserHandlers);

    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    Association artifact = new Association();
    adhocSubProcess.addArtifact(artifact);

    LinkedHashSet<FlowElement> flowElements = new LinkedHashSet<>();
    flowElements.add(adhocSubProcess);

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert
    FlowElement currentFlowElement = bpmnParse.getCurrentFlowElement();
    Collection<Artifact> artifacts = ((AdhocSubProcess) currentFlowElement).getArtifacts();
    assertEquals(1, artifacts.size());
    assertTrue(artifacts instanceof List);
    assertTrue(currentFlowElement instanceof AdhocSubProcess);
    assertSame(artifact, ((List<Artifact>) artifacts).get(0));
  }

  /**
   * Test {@link BpmnParse#processFlowElements(Collection)}.
   *
   * <p>Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.processFlowElements(Collection)"})
  public void testProcessFlowElements5() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnParserHandlers(new BpmnParseHandlers());

    LinkedHashSet<FlowElement> flowElements = new LinkedHashSet<>();
    SequenceFlow sequenceFlow = new SequenceFlow();
    flowElements.add(sequenceFlow);

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert
    assertSame(sequenceFlow, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link BpmnParse#processFlowElements(Collection)}.
   *
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.processFlowElements(Collection)"})
  public void testProcessFlowElements_givenBooleanDataObject() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnParserHandlers(new BpmnParseHandlers());

    LinkedHashSet<FlowElement> flowElements = new LinkedHashSet<>();
    flowElements.add(new BooleanDataObject());

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert that nothing has changed
    assertNull(bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link BpmnParse#processFlowElements(Collection)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.processFlowElements(Collection)"})
  public void testProcessFlowElements_whenArrayList() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act
    bpmnParse.processFlowElements(new ArrayList<>());

    // Assert that nothing has changed
    assertNull(bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link BpmnParse#createBPMNEdge(String, List)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} {@link AdhocSubProcess#getId()} return {@code 42}.
   *   <li>When {@code not empty}.
   *   <li>Then calls {@link AdhocSubProcess#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#createBPMNEdge(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.createBPMNEdge(String, List)"})
  public void testCreateBPMNEdge_givenAdhocSubProcessGetIdReturn42_whenNotEmpty_thenCallsGetId() {
    // Arrange
    AdhocSubProcess element = mock(AdhocSubProcess.class);
    when(element.getId()).thenReturn("42");
    when(element.getFlowElements()).thenReturn(new ArrayList<>());
    when(element.getFlowElementMap()).thenReturn(new HashMap<>());
    when(element.getArtifact(Mockito.<String>any())).thenReturn(new Association());
    when(element.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    doNothing().when(element).setId(Mockito.<String>any());
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());
    element.setId(null);

    Process process = TestProcessUtil.createOneTaskProcessWithId("42");
    process.addFlowElement(element);
    process.setFlowElementMap(new HashMap<>());
    process.addArtifact(new Association());

    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    bpmnModel.addProcess(process);

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(bpmnModel);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    ArrayList<GraphicInfo> graphicList = new ArrayList<>();
    graphicList.add(graphicInfo);

    // Act
    bpmnParse.createBPMNEdge("not empty", graphicList);

    // Assert
    verify(element, atLeast(1)).getId();
    verify(element).setId(null);
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    verify(element).getArtifact("not empty");
    verify(element).getFlowElement("not empty");
    verify(element).getFlowElementMap();
    verify(element, atLeast(1)).getFlowElements();
  }

  /**
   * Test {@link BpmnParse#createBPMNEdge(String, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AdhocSubProcess} (default constructor).
   *   <li>When {@code not empty}.
   *   <li>Then calls {@link AdhocSubProcess#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#createBPMNEdge(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.createBPMNEdge(String, List)"})
  public void testCreateBPMNEdge_givenArrayListAddAdhocSubProcess_whenNotEmpty_thenCallsGetId() {
    // Arrange
    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(new AdhocSubProcess());

    AdhocSubProcess element = mock(AdhocSubProcess.class);
    when(element.getId()).thenReturn("42");
    when(element.getFlowElements()).thenReturn(flowElementList);
    when(element.getFlowElementMap()).thenReturn(new HashMap<>());
    when(element.getArtifact(Mockito.<String>any())).thenReturn(new Association());
    when(element.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    doNothing().when(element).setId(Mockito.<String>any());
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());
    element.setId(null);

    Process process = TestProcessUtil.createOneTaskProcessWithId("42");
    process.addFlowElement(element);
    process.setFlowElementMap(new HashMap<>());
    process.addArtifact(new Association());

    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    bpmnModel.addProcess(process);

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(bpmnModel);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    ArrayList<GraphicInfo> graphicList = new ArrayList<>();
    graphicList.add(graphicInfo);

    // Act
    bpmnParse.createBPMNEdge("not empty", graphicList);

    // Assert
    verify(element, atLeast(1)).getId();
    verify(element).setId(null);
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    verify(element).getArtifact("not empty");
    verify(element).getFlowElement("not empty");
    verify(element).getFlowElementMap();
    verify(element, atLeast(1)).getFlowElements();
  }

  /**
   * Test {@link BpmnParse#createBPMNEdge(String, List)}.
   *
   * <ul>
   *   <li>Given {@link Process} {@link Process#getArtifact(String)} return {@code null}.
   *   <li>Then calls {@link Process#getArtifact(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#createBPMNEdge(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.createBPMNEdge(String, List)"})
  public void testCreateBPMNEdge_givenProcessGetArtifactReturnNull_thenCallsGetArtifact() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("foo");

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<SubProcess>>any()))
        .thenReturn(new ArrayList<>());
    when(process.getArtifact(Mockito.<String>any())).thenReturn(null);
    when(process.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    doNothing().when(process).setFlowElementMap(Mockito.<Map<String, FlowElement>>any());
    process.addFlowElement(element);
    process.setFlowElementMap(new HashMap<>());
    process.addArtifact(new Association());

    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    bpmnModel.addProcess(process);

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(bpmnModel);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    ArrayList<GraphicInfo> graphicList = new ArrayList<>();
    graphicList.add(graphicInfo);

    // Act
    bpmnParse.createBPMNEdge("", graphicList);

    // Assert
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifact("");
    verify(process).getFlowElement("");
    verify(process).setFlowElementMap(isA(Map.class));
  }

  /**
   * Test {@link BpmnParse#createBPMNEdge(String, List)}.
   *
   * <ul>
   *   <li>Given {@link Process} {@link Process#getFlowElement(String)} return {@code null}.
   *   <li>Then calls {@link Process#getArtifact(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnParse#createBPMNEdge(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.createBPMNEdge(String, List)"})
  public void testCreateBPMNEdge_givenProcessGetFlowElementReturnNull_thenCallsGetArtifact() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setId("foo");

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<SubProcess>>any()))
        .thenReturn(new ArrayList<>());
    when(process.getArtifact(Mockito.<String>any())).thenReturn(new Association());
    when(process.getFlowElement(Mockito.<String>any())).thenReturn(null);
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    doNothing().when(process).setFlowElementMap(Mockito.<Map<String, FlowElement>>any());
    process.addFlowElement(element);
    process.setFlowElementMap(new HashMap<>());
    process.addArtifact(new Association());

    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    bpmnModel.addProcess(process);

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(bpmnModel);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    ArrayList<GraphicInfo> graphicList = new ArrayList<>();
    graphicList.add(graphicInfo);

    // Act
    bpmnParse.createBPMNEdge("", graphicList);

    // Assert
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifact("");
    verify(process).getFlowElement("");
    verify(process).setFlowElementMap(isA(Map.class));
  }

  /**
   * Test {@link BpmnParse#getProcessDefinition(String)}.
   *
   * <p>Method under test: {@link BpmnParse#getProcessDefinition(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessDefinitionEntity BpmnParse.getProcessDefinition(String)"})
  public void testGetProcessDefinition() {
    // Arrange, Act and Assert
    assertNull(new BpmnParse(new BpmnParser()).getProcessDefinition("Process Definition Key"));
  }

  /**
   * Test {@link BpmnParse#setCurrentSubProcess(SubProcess)}.
   *
   * <p>Method under test: {@link BpmnParse#setCurrentSubProcess(SubProcess)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnParse.setCurrentSubProcess(SubProcess)"})
  public void testSetCurrentSubProcess() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    SubProcess subProcess = new SubProcess();

    // Act
    bpmnParse.setCurrentSubProcess(subProcess);

    // Assert
    Collection<FlowElement> flowElements = subProcess.getFlowElements();
    assertTrue(flowElements instanceof List);
    LinkedList<SubProcess> subProcessList = bpmnParse.currentSubprocessStack;
    assertEquals(1, subProcessList.size());
    assertTrue(flowElements.isEmpty());
    assertSame(subProcess, subProcessList.get(0));
    assertSame(subProcess, bpmnParse.getCurrentSubProcess());
  }

  /**
   * Test {@link BpmnParse#getCurrentSubProcess()}.
   *
   * <p>Method under test: {@link BpmnParse#getCurrentSubProcess()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SubProcess BpmnParse.getCurrentSubProcess()"})
  public void testGetCurrentSubProcess() {
    // Arrange, Act and Assert
    assertNull(new BpmnParse(new BpmnParser()).getCurrentSubProcess());
  }
}
