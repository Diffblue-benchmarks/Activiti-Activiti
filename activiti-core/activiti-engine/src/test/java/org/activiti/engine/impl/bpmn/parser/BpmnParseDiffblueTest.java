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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import groovy.lang.GroovyClassLoader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.SubProcess;
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
import org.activiti.engine.impl.util.io.StreamSource;
import org.activiti.engine.impl.util.io.UrlStreamSource;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@RunWith(MockitoJUnitRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BpmnParseDiffblueTest {
  @InjectMocks
  private BpmnParse bpmnParse;

  @Mock
  private BpmnParser bpmnParser;

  /**
   * Test {@link BpmnParse#BpmnParse(BpmnParser)}.
   * <ul>
   *   <li>When {@link BpmnParser} (default constructor).</li>
   *   <li>Then return TargetNamespace is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#BpmnParse(BpmnParser)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <p>
   * Methods under test:
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnParse BpmnParse.deployment(DeploymentEntity)",
      "ActivityBehaviorFactory BpmnParse.getActivityBehaviorFactory()", "BpmnModel BpmnParse.getBpmnModel()",
      "BpmnParseHandlers BpmnParse.getBpmnParserHandlers()", "FlowElement BpmnParse.getCurrentFlowElement()",
      "Process BpmnParse.getCurrentProcess()", "ProcessDefinitionEntity BpmnParse.getCurrentProcessDefinition()",
      "DeploymentEntity BpmnParse.getDeployment()", "ListenerFactory BpmnParse.getListenerFactory()",
      "List BpmnParse.getProcessDefinitions()", "Map BpmnParse.getSequenceFlows()",
      "String BpmnParse.getTargetNamespace()", "boolean BpmnParse.isValidateProcess()",
      "boolean BpmnParse.isValidateSchema()", "BpmnParse BpmnParse.name(String)",
      "void BpmnParse.setActivityBehaviorFactory(ActivityBehaviorFactory)", "void BpmnParse.setBpmnModel(BpmnModel)",
      "void BpmnParse.setBpmnParserHandlers(BpmnParseHandlers)", "void BpmnParse.setCurrentFlowElement(FlowElement)",
      "void BpmnParse.setCurrentProcess(Process)",
      "void BpmnParse.setCurrentProcessDefinition(ProcessDefinitionEntity)",
      "void BpmnParse.setDeployment(DeploymentEntity)", "void BpmnParse.setListenerFactory(ListenerFactory)",
      "BpmnParse BpmnParse.setSourceSystemId(String)", "void BpmnParse.setValidateProcess(boolean)",
      "void BpmnParse.setValidateSchema(boolean)"})
  public void testGettersAndSetters() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act
    BpmnParse actualDeploymentResult = bpmnParse.deployment(new DeploymentEntityImpl());
    BpmnParse actualNameResult = bpmnParse.name("Name");
    DefaultActivityBehaviorFactory activityBehaviorFactory = new DefaultActivityBehaviorFactory();
    bpmnParse.setActivityBehaviorFactory(activityBehaviorFactory);
    BpmnModel bpmnModel = new BpmnModel();
    bpmnParse.setBpmnModel(bpmnModel);
    BpmnParseHandlers bpmnParserHandlers = new BpmnParseHandlers();
    bpmnParse.setBpmnParserHandlers(bpmnParserHandlers);
    AdhocSubProcess currentFlowElement = new AdhocSubProcess();
    bpmnParse.setCurrentFlowElement(currentFlowElement);
    Process currentProcess = new Process();
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
    ProcessDefinitionEntity actualCurrentProcessDefinition = bpmnParse.getCurrentProcessDefinition();
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
    assertSame(bpmnModel, actualBpmnModel);
    assertSame(currentProcess, actualCurrentProcess);
    assertSame(bpmnParse, actualDeploymentResult);
    assertSame(bpmnParse, actualNameResult);
    assertSame(bpmnParse, actualSetSourceSystemIdResult);
    assertSame(bpmnParserHandlers, actualBpmnParserHandlers);
    assertSame(activityBehaviorFactory, actualActivityBehaviorFactory);
    assertSame(listenerFactory, actualListenerFactory);
    assertSame(deployment, actualDeployment);
    assertSame(currentProcessDefinition, actualCurrentProcessDefinition);
  }

  /**
   * Test {@link BpmnParse#sourceInputStream(InputStream)}.
   * <ul>
   *   <li>Given {@link BpmnParse#BpmnParse(BpmnParser)} with parser is {@link BpmnParser} (default constructor) name {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#sourceInputStream(InputStream)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceInputStream(InputStream)"})
  public void testSourceInputStream_givenBpmnParseWithParserIsBpmnParserNameNull() throws UnsupportedEncodingException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.name(null);
    bpmnParse.setStreamSource(mock(StreamSource.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> bpmnParse.sourceInputStream(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
  }

  /**
   * Test {@link BpmnParse#sourceInputStream(InputStream)}.
   * <ul>
   *   <li>Given {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code AXAXAXAX} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#sourceInputStream(InputStream)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceInputStream(InputStream)"})
  public void testSourceInputStream_givenByteArrayInputStreamWithAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.name("foo");
    bpmnParse.setStreamSource(new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> bpmnParse.sourceInputStream(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
  }

  /**
   * Test {@link BpmnParse#sourceResource(String, ClassLoader)} with {@code resource}, {@code classLoader}.
   * <p>
   * Method under test: {@link BpmnParse#sourceResource(String, ClassLoader)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceResource(String, ClassLoader)"})
  public void testSourceResourceWithResourceClassLoader() throws UnsupportedEncodingException {
    // Arrange
    bpmnParse.name("foo");
    bpmnParse.setStreamSource(new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> bpmnParse.sourceResource("Resource", new GroovyClassLoader()));
  }

  /**
   * Test {@link BpmnParse#sourceResource(String, ClassLoader)} with {@code resource}, {@code classLoader}.
   * <ul>
   *   <li>Given {@link BpmnParse} name {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#sourceResource(String, ClassLoader)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceResource(String, ClassLoader)"})
  public void testSourceResourceWithResourceClassLoader_givenBpmnParseNameNull() {
    // Arrange
    bpmnParse.name(null);
    bpmnParse.setStreamSource(mock(StreamSource.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> bpmnParse.sourceResource("Resource", new GroovyClassLoader()));
  }

  /**
   * Test {@link BpmnParse#sourceResource(String)} with {@code resource}.
   * <ul>
   *   <li>Given {@link BpmnParse} name {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#sourceResource(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceResource(String)"})
  public void testSourceResourceWithResource_givenBpmnParseNameNull() {
    // Arrange
    bpmnParse.name(null);
    bpmnParse.setStreamSource(mock(StreamSource.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> bpmnParse.sourceResource("Resource"));
  }

  /**
   * Test {@link BpmnParse#sourceResource(String)} with {@code resource}.
   * <ul>
   *   <li>Given {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code AXAXAXAX} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#sourceResource(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceResource(String)"})
  public void testSourceResourceWithResource_givenByteArrayInputStreamWithAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    bpmnParse.name("foo");
    bpmnParse.setStreamSource(new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> bpmnParse.sourceResource("Resource"));
  }

  /**
   * Test {@link BpmnParse#sourceString(String)}.
   * <ul>
   *   <li>Given {@link BpmnParse} name {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#sourceString(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceString(String)"})
  public void testSourceString_givenBpmnParseNameNull() {
    // Arrange
    bpmnParse.name(null);
    bpmnParse.setStreamSource(mock(StreamSource.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> bpmnParse.sourceString("String"));
  }

  /**
   * Test {@link BpmnParse#sourceString(String)}.
   * <ul>
   *   <li>Given {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code AXAXAXAX} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#sourceString(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceString(String)"})
  public void testSourceString_givenByteArrayInputStreamWithAxaxaxaxBytesIsUtf8() throws UnsupportedEncodingException {
    // Arrange
    bpmnParse.name("foo");
    bpmnParse.setStreamSource(new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> bpmnParse.sourceString("String"));
  }

  /**
   * Test {@link BpmnParse#sourceUrl(String)} with {@code String}.
   * <ul>
   *   <li>Given {@link BpmnParse} name {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#sourceUrl(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceUrl(String)"})
  public void testSourceUrlWithString_givenBpmnParseNameNull() {
    // Arrange
    bpmnParse.name(null);
    bpmnParse.setStreamSource(mock(StreamSource.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> bpmnParse.sourceUrl("https://example.org/example"));
  }

  /**
   * Test {@link BpmnParse#sourceUrl(String)} with {@code String}.
   * <ul>
   *   <li>Given {@link BpmnParse}.</li>
   *   <li>When {@code Url}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#sourceUrl(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceUrl(String)"})
  public void testSourceUrlWithString_givenBpmnParse_whenUrl() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> bpmnParse.sourceUrl("Url"));
  }

  /**
   * Test {@link BpmnParse#sourceUrl(String)} with {@code String}.
   * <ul>
   *   <li>Given {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code AXAXAXAX} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#sourceUrl(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceUrl(String)"})
  public void testSourceUrlWithString_givenByteArrayInputStreamWithAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    bpmnParse.name("foo");
    bpmnParse.setStreamSource(new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> bpmnParse.sourceUrl("https://example.org/example"));
  }

  /**
   * Test {@link BpmnParse#sourceUrl(URL)} with {@code URL}.
   * <p>
   * Method under test: {@link BpmnParse#sourceUrl(URL)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceUrl(URL)"})
  public void testSourceUrlWithUrl() throws MalformedURLException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act
    BpmnParse actualSourceUrlResult = bpmnParse
        .sourceUrl(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    // Assert
    assertTrue(bpmnParse.streamSource instanceof UrlStreamSource);
    assertEquals(String.join("", "file:", Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toString()),
        bpmnParse.name);
    assertSame(bpmnParse, actualSourceUrlResult);
  }

  /**
   * Test {@link BpmnParse#sourceUrl(URL)} with {@code URL}.
   * <ul>
   *   <li>Given {@link BpmnParse#BpmnParse(BpmnParser)} with parser is {@link BpmnParser} (default constructor) name {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#sourceUrl(URL)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceUrl(URL)"})
  public void testSourceUrlWithUrl_givenBpmnParseWithParserIsBpmnParserNameNull() throws MalformedURLException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.name(null);
    bpmnParse.setStreamSource(mock(StreamSource.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> bpmnParse.sourceUrl(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL()));
  }

  /**
   * Test {@link BpmnParse#sourceUrl(URL)} with {@code URL}.
   * <ul>
   *   <li>Given {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code AXAXAXAX} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#sourceUrl(URL)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BpmnParse BpmnParse.sourceUrl(URL)"})
  public void testSourceUrlWithUrl_givenByteArrayInputStreamWithAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException, MalformedURLException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.name("foo");
    bpmnParse.setStreamSource(new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> bpmnParse.sourceUrl(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL()));
  }

  /**
   * Test {@link BpmnParse#applyParseHandlers()}.
   * <p>
   * Method under test: {@link BpmnParse#applyParseHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnParse.applyParseHandlers()"})
  public void testApplyParseHandlers() {
    // Arrange
    BpmnParser parser = new BpmnParser();
    parser.setBpmnParserHandlers(new BpmnParseHandlers());

    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    bpmnModel.addProcess(process);

    BpmnParse bpmnParse = new BpmnParse(parser);
    bpmnParse.setBpmnModel(bpmnModel);

    // Act
    bpmnParse.applyParseHandlers();

    // Assert
    assertSame(process, bpmnParse.getCurrentProcess());
  }

  /**
   * Test {@link BpmnParse#applyParseHandlers()}.
   * <ul>
   *   <li>Then {@link BpmnParse#BpmnParse(BpmnParser)} with parser is {@link BpmnParser} (default constructor) CurrentProcess is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#applyParseHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnParse.applyParseHandlers()"})
  public void testApplyParseHandlers_thenBpmnParseWithParserIsBpmnParserCurrentProcessIsNull() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(new BpmnModel());

    // Act
    bpmnParse.applyParseHandlers();

    // Assert
    assertNull(bpmnParse.getCurrentProcess());
    assertTrue(bpmnParse.getSequenceFlows().isEmpty());
  }

  /**
   * Test {@link BpmnParse#processFlowElements(Collection)}.
   * <p>
   * Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnParse.processFlowElements(Collection)"})
  public void testProcessFlowElements() {
    // Arrange
    BpmnParseHandlers bpmnParserHandlers = new BpmnParseHandlers();
    bpmnParserHandlers.addHandler(new AdhocSubProcessParseHandler());

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    parser.setBpmnParserHandlers(bpmnParserHandlers);
    BpmnParse bpmnParse = new BpmnParse(parser);

    ArrayList<FlowElement> flowElements = new ArrayList<>();
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    flowElements.add(adhocSubProcess);

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert
    assertEquals(1, flowElements.size());
    FlowElement getResult = flowElements.get(0);
    assertTrue(getResult instanceof AdhocSubProcess);
    Object behavior = ((AdhocSubProcess) getResult).getBehavior();
    assertTrue(behavior instanceof AdhocSubProcessActivityBehavior);
    assertNull(((AdhocSubProcessActivityBehavior) behavior).getMultiInstanceActivityBehavior());
    assertSame(adhocSubProcess, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link BpmnParse#processFlowElements(Collection)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} first Behavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnParse.processFlowElements(Collection)"})
  public void testProcessFlowElements_givenAdhocSubProcess_thenArrayListFirstBehaviorIsNull() {
    // Arrange
    BpmnParser parser = new BpmnParser();
    parser.setBpmnParserHandlers(new BpmnParseHandlers());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ArrayList<FlowElement> flowElements = new ArrayList<>();
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    flowElements.add(adhocSubProcess);

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert
    assertEquals(1, flowElements.size());
    FlowElement getResult = flowElements.get(0);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertNull(((AdhocSubProcess) getResult).getBehavior());
    assertSame(adhocSubProcess, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link BpmnParse#processFlowElements(Collection)}.
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor).</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link BooleanDataObject} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnParse.processFlowElements(Collection)"})
  public void testProcessFlowElements_givenBooleanDataObject_whenArrayListAddBooleanDataObject() {
    // Arrange
    BpmnParser parser = new BpmnParser();
    parser.setBpmnParserHandlers(new BpmnParseHandlers());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ArrayList<FlowElement> flowElements = new ArrayList<>();
    flowElements.add(new BooleanDataObject());

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert that nothing has changed
    assertNull(bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link BpmnParse#processFlowElements(Collection)}.
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} first {@link BoundaryEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnParse.processFlowElements(Collection)"})
  public void testProcessFlowElements_givenBoundaryEvent_thenArrayListFirstBoundaryEvent() {
    // Arrange
    BpmnParser parser = new BpmnParser();
    parser.setBpmnParserHandlers(new BpmnParseHandlers());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ArrayList<FlowElement> flowElements = new ArrayList<>();
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    flowElements.add(boundaryEvent);

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert
    assertEquals(1, flowElements.size());
    FlowElement getResult = flowElements.get(0);
    assertTrue(getResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) getResult).getBehavior());
    assertSame(boundaryEvent, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link BpmnParse#processFlowElements(Collection)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor) addProcess {@link Process} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnParse.processFlowElements(Collection)"})
  public void testProcessFlowElements_givenBpmnModelAddProcessProcess() {
    // Arrange
    BpmnParseHandlers bpmnParserHandlers = new BpmnParseHandlers();
    bpmnParserHandlers.addHandler(new AdhocSubProcessParseHandler());

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    parser.setBpmnParserHandlers(bpmnParserHandlers);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    BpmnParse bpmnParse = new BpmnParse(parser);
    bpmnParse.setBpmnModel(bpmnModel);

    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.addArtifact(new Association());

    ArrayList<FlowElement> flowElements = new ArrayList<>();
    flowElements.add(adhocSubProcess);

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert
    assertEquals(1, flowElements.size());
    FlowElement getResult = flowElements.get(0);
    assertTrue(getResult instanceof AdhocSubProcess);
    Object behavior = ((AdhocSubProcess) getResult).getBehavior();
    assertTrue(behavior instanceof AdhocSubProcessActivityBehavior);
    assertNull(((AdhocSubProcessActivityBehavior) behavior).getMultiInstanceActivityBehavior());
    assertSame(adhocSubProcess, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link BpmnParse#processFlowElements(Collection)}.
   * <ul>
   *   <li>Given {@link BpmnParse#BpmnParse(BpmnParser)} with parser is {@link BpmnParser} (default constructor) BpmnModel is {@link BpmnModel} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnParse.processFlowElements(Collection)"})
  public void testProcessFlowElements_givenBpmnParseWithParserIsBpmnParserBpmnModelIsBpmnModel() {
    // Arrange
    BpmnParseHandlers bpmnParserHandlers = new BpmnParseHandlers();
    bpmnParserHandlers.addHandler(new AdhocSubProcessParseHandler());

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    parser.setBpmnParserHandlers(bpmnParserHandlers);

    BpmnParse bpmnParse = new BpmnParse(parser);
    bpmnParse.setBpmnModel(new BpmnModel());

    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.addArtifact(new Association());

    ArrayList<FlowElement> flowElements = new ArrayList<>();
    flowElements.add(adhocSubProcess);

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert
    assertEquals(1, flowElements.size());
    FlowElement getResult = flowElements.get(0);
    assertTrue(getResult instanceof AdhocSubProcess);
    Object behavior = ((AdhocSubProcess) getResult).getBehavior();
    assertTrue(behavior instanceof AdhocSubProcessActivityBehavior);
    assertNull(((AdhocSubProcessActivityBehavior) behavior).getMultiInstanceActivityBehavior());
    assertSame(adhocSubProcess, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link BpmnParse#processFlowElements(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>Then calls {@link BpmnModel#getArtifact(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnParse#createBPMNEdge(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BpmnParse.createBPMNEdge(String, List)"})
  public void testCreateBPMNEdge_thenCallsGetArtifact() {
    // Arrange
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getArtifact(Mockito.<String>any())).thenReturn(new Association());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(bpmnModel);

    // Act
    bpmnParse.createBPMNEdge("Key", new ArrayList<>());

    // Assert
    verify(bpmnModel).getArtifact(eq("Key"));
    verify(bpmnModel).getFlowElement(eq("Key"));
  }

  /**
   * Test {@link BpmnParse#getProcessDefinition(String)}.
   * <p>
   * Method under test: {@link BpmnParse#getProcessDefinition(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionEntity BpmnParse.getProcessDefinition(String)"})
  public void testGetProcessDefinition() {
    // Arrange, Act and Assert
    assertNull(bpmnParse.getProcessDefinition("Process Definition Key"));
  }

  /**
   * Test {@link BpmnParse#setCurrentSubProcess(SubProcess)}.
   * <p>
   * Method under test: {@link BpmnParse#setCurrentSubProcess(SubProcess)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <p>
   * Method under test: {@link BpmnParse#getCurrentSubProcess()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"SubProcess BpmnParse.getCurrentSubProcess()"})
  public void testGetCurrentSubProcess() {
    // Arrange, Act and Assert
    assertNull((new BpmnParse(new BpmnParser())).getCurrentSubProcess());
  }
}
