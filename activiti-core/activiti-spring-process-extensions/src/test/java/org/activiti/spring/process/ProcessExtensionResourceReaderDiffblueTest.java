package org.activiti.spring.process;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessExtensionResourceReaderDiffblueTest {
  /**
   * Test {@link ProcessExtensionResourceReader#getResourceNameSelector()}.
   * <ul>
   *   <li>Then return not test {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessExtensionResourceReader#getResourceNameSelector()}
   */
  @Test
  @DisplayName("Test getResourceNameSelector(); then return not test 'foo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.function.Predicate ProcessExtensionResourceReader.getResourceNameSelector()"})
  void testGetResourceNameSelector_thenReturnNotTestFoo() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertFalse(
        (new ProcessExtensionResourceReader(objectMapper, new HashMap<>())).getResourceNameSelector().test("foo"));
  }

  /**
   * Test {@link ProcessExtensionResourceReader#getResourceNameSelector()}.
   * <ul>
   *   <li>Then return test {@code -extensions.json}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessExtensionResourceReader#getResourceNameSelector()}
   */
  @Test
  @DisplayName("Test getResourceNameSelector(); then return test '-extensions.json'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.function.Predicate ProcessExtensionResourceReader.getResourceNameSelector()"})
  void testGetResourceNameSelector_thenReturnTestExtensionsJson() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertTrue((new ProcessExtensionResourceReader(objectMapper, new HashMap<>())).getResourceNameSelector()
        .test("-extensions.json"));
  }

  /**
   * Test {@link ProcessExtensionResourceReader#read(InputStream)}.
   * <ul>
   *   <li>Given {@link IOException#IOException(String)} with {@code foo}.</li>
   *   <li>Then throw {@link IOException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessExtensionResourceReader#read(InputStream)}
   */
  @Test
  @DisplayName("Test read(InputStream); given IOException(String) with 'foo'; then throw IOException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.activiti.spring.process.model.ProcessExtensionModel ProcessExtensionResourceReader.read(InputStream)"})
  void testRead_givenIOExceptionWithFoo_thenThrowIOException() throws IOException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionResourceReader processExtensionResourceReader = new ProcessExtensionResourceReader(objectMapper,
        new HashMap<>());
    DataInputStream inputStream = mock(DataInputStream.class);
    when(inputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenThrow(new IOException("foo"));

    // Act and Assert
    assertThrows(IOException.class, () -> processExtensionResourceReader.read(inputStream));
    verify(inputStream).read(isA(byte[].class), eq(0), eq(8000));
  }
}
