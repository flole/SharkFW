package net.sharkfw.knowledgeBase.rdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;

import net.sharkfw.knowledgeBase.SemanticTag;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.geom.SharkGeometry;
import net.sharkfw.knowledgeBase.geom.inmemory.InMemoSharkGeometry;

import org.junit.Before;
import org.junit.Test;

import com.hp.hpl.jena.query.ReadWrite;

public class TestRDFKB {

	private final String KBDIRECTORY = "C:\\Users\\Barret\\workspace\\RDFSharkKB\\Databases\\Dataset1";

	@Before
	public void clearDatasetBeforeTests() throws SharkKBException {
		File index = new File(KBDIRECTORY);
		String[] entries = index.list();
		for (String s : entries) {
			File currentFile = new File(index.getPath(), s);
			currentFile.delete();
		}
	}

	@Test
	public void testCreateRDFSemanticTag() throws SharkKBException {

		RDFSharkKB kb = new RDFSharkKB(KBDIRECTORY);
		RDFSTSet stSet = kb.getTopicSTSet();
		SemanticTag tag = stSet.createSemanticTag(
				"https://jena.apache.org/documentation/tdb", "Jena - TDB");
		assertNotNull(tag);
		assertTrue(Arrays.asList(tag.getSI()).contains("https://jena.apache.org/documentation/tdb"));
	}

	@Test
	public void testGetRDFSemanticTag() throws SharkKBException {

		RDFSharkKB kb = new RDFSharkKB(KBDIRECTORY);
		RDFSTSet stSet = kb.getTopicSTSet();
		SemanticTag tag = stSet
				.getSemanticTag("https://jena.apache.org/documentation/tdb");
		assertNotNull(tag);
		assertTrue(Arrays.asList(tag.getSI()).contains("https://jena.apache.org/documentation/tdb"));

	}

	@Test
	public void testCreateRDFSemanticTagWithMultipleSIs()
			throws SharkKBException {

		RDFSharkKB kb = new RDFSharkKB(KBDIRECTORY);
		RDFSTSet stSet = kb.getTopicSTSet();
		String[] si = new String[] { "http://www.htw-berlin.de/1",
				"http://www.htw-berlin.de/2", "http://www.htw-berlin.de/3" };
		SemanticTag tag = stSet.createSemanticTag("HTW-AI", si);
		assertEquals(3, tag.getSI().length);
		assertEquals("http://www.htw-berlin.de/2", tag.getSI()[1]);

	}

	@Test
	public void testCreateRDFPeerSemanticTag() throws SharkKBException {

		RDFSharkKB kb = new RDFSharkKB(KBDIRECTORY);
		RDFPeerSTSet peerSet = kb.getPeerSTSet();
		String[] si = new String[] { "https://de.wikipedia.org/wiki/Alpha",
				"https://de.wikipedia.org/wiki/Beta",
				"https://de.wikipedia.org/wiki/Gamma" };
		String[] addresses = new String[] { "s0540042@htw-berlin.de",
				"47487271", "Aristotelessteig 6" };
		String topic = "Shark";
		RDFPeerSemanticTag tag = peerSet.createPeerSemanticTag(topic, si,
				addresses);
		assertEquals(3, tag.getSI().length);
		assertEquals(3, tag.getAddresses().length);
		assertEquals("https://de.wikipedia.org/wiki/Gamma", tag.getSI()[2]);			
//		kb.getDataset().begin(ReadWrite.READ);
//		kb.getDataset().getNamedModel(RDFConstants.PEER_MODEL_NAME)
//				.write(System.out);
//		kb.getDataset().end();
	}
	
	@Test
	public void testGetRDFPeerSemanticTag() throws SharkKBException {
		
		RDFSharkKB kb = new RDFSharkKB(KBDIRECTORY);
		RDFPeerSTSet peerSet = kb.getPeerSTSet();
		RDFPeerSemanticTag tag = peerSet.getSemanticTag("https://de.wikipedia.org/wiki/Alpha");
		assertEquals("Shark", tag.getTopic());
		assertEquals(3, tag.getSI().length);
		assertTrue(Arrays.asList(tag.getSI()).contains("https://de.wikipedia.org/wiki/Alpha"));
		assertTrue(Arrays.asList(tag.getSI()).contains("https://de.wikipedia.org/wiki/Beta"));
		assertTrue(Arrays.asList(tag.getSI()).contains("https://de.wikipedia.org/wiki/Gamma"));
		assertEquals(3, tag.getAddresses().length);
		assertTrue(Arrays.asList(tag.getAddresses()).contains("s0540042@htw-berlin.de"));
		assertTrue(Arrays.asList(tag.getAddresses()).contains("47487271"));
		assertTrue(Arrays.asList(tag.getAddresses()).contains("Aristotelessteig 6"));		
	}
	
	@Test
	public void testCreateRDFSpatialSemanticTag() throws SharkKBException {
		String[] si = new String[] { "https://de.wikipedia.org/wiki/Alpha",
		"https://de.wikipedia.org/wiki/Beta",
		"https://de.wikipedia.org/wiki/Gamma" };
		String topic = "SharkGeometry";
		SharkGeometry sg = InMemoSharkGeometry.createGeomByWKT("POINT (30 10)");
		RDFSharkKB kb = new RDFSharkKB(KBDIRECTORY);
		RDFSpatialSTSet spatialSet = kb.getSpatialSTSet();
		RDFSpatialSemanticTag tag = spatialSet.createSpatialSemanticTag(topic, si, sg);
		assertEquals(3, tag.getSI().length);
		assertEquals("POINT (30 10)", tag.getGeometry().getWKT());
		assertEquals("https://de.wikipedia.org/wiki/Gamma", tag.getSI()[2]);
		
	}
	
	@Test
	public void testGetRDFSpatialSemanticTag() throws SharkKBException {
		RDFSharkKB kb = new RDFSharkKB(KBDIRECTORY);
		RDFSpatialSTSet spatialSet = kb.getSpatialSTSet();
		RDFSpatialSemanticTag tag = spatialSet.getSpatialSemanticTag("https://de.wikipedia.org/wiki/Alpha");
		assertEquals("SharkGeometry", tag.getTopic());
		assertEquals(3, tag.getSI().length);
		assertTrue(Arrays.asList(tag.getSI()).contains("https://de.wikipedia.org/wiki/Alpha"));
		assertTrue(Arrays.asList(tag.getSI()).contains("https://de.wikipedia.org/wiki/Beta"));
		assertTrue(Arrays.asList(tag.getSI()).contains("https://de.wikipedia.org/wiki/Gamma"));
		assertEquals("POINT (30 10)", tag.getGeometry().getWKT());
	}
	
	@Test
	public void testCreateTimeSemanticTag() throws SharkKBException {
		RDFSharkKB kb = new RDFSharkKB(KBDIRECTORY);
		RDFTimeSTSet timeSet = kb.getTimeSTSet();
		long from = System.currentTimeMillis();
		long duration = 86400000;
		RDFTimeSemanticTag tag = timeSet.createTimeSemanticTag(from, duration);
		assertNotNull(tag);
		kb.getDataset().begin(ReadWrite.READ);
		kb.getDataset().getNamedModel(RDFConstants.TIME_MODEL_NAME)
				.write(System.out);
		kb.getDataset().end();
	}



}