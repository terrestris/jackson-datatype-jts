package com.bedatadriven.jackson.datatype.jts;

import com.bedatadriven.jackson.datatype.jts.parsers.JacksonGeometryException;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;

public class JtsModuleTest {
	private GeometryFactory gf = new GeometryFactory();
	private ObjectMapper mapper;

	@Before
	public void setupMapper() {
		mapper = JsonMapper.builder().addModule(new JtsModule()).build();
	}

	@Test(expected = JacksonGeometryException.class)
	public void invalidGeometryType() throws IOException {
		String json = "{\"type\":\"Singularity\",\"coordinates\":[]}";
		mapper.readValue(json, Geometry.class);
	}

	@Test(expected = JacksonGeometryException.class)
	public void unsupportedGeometry() throws IOException {
		Geometry unsupportedGeometry = EasyMock.createNiceMock("NonEuclideanGeometry", Geometry.class);
		EasyMock.replay(unsupportedGeometry);

		mapper.writeValue(System.out, unsupportedGeometry);
	}

}
