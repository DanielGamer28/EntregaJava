package pentago;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import pentago.control.SignaturasControladorTest;
import pentago.modelo.SignaturasRegionTest;
import pentago.modelo.SignaturasTableroTest;

/**
 * Suite con todos los tests de signaturas solicitados para Pentago.
 *
 * Se seleccionan explícitamente las tres clases para no mezclar estos
 * tests estructurales con otros tests funcionales del proyecto.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 */
@Suite
@SelectClasses({
	SignaturasControladorTest.class,
	SignaturasRegionTest.class,
	SignaturasTableroTest.class
})
@SuiteDisplayName("Tests de signaturas de Pentago")
public class SuiteAllTests {

	/** Constructor. */
	private SuiteAllTests() { }
}
