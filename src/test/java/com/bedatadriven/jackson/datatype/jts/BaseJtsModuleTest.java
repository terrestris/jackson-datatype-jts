package com.bedatadriven.jackson.datatype.jts;

import org.junit.Before;
import org.junit.Test;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public abstract class BaseJtsModuleTest<T extends Geometry> {
    protected GeometryFactory gf = new GeometryFactory();
    protected ObjectMapper mapper;
    private ObjectWriter writer;
    private T geometry;
    private String geometryAsGeoJson;

    protected BaseJtsModuleTest() {
    }

    @Before
    public void setup() {
        mapper = JsonMapper.builder().addModule(new JtsModule()).build();
        writer = mapper.writer();
        geometry = createGeometry();
        geometryAsGeoJson = createGeometryAsGeoJson();
    }

    protected abstract Class<T> getType();

    protected abstract String createGeometryAsGeoJson();

    protected abstract T createGeometry();


    @Test
    public void shouldDeserializeConcreteType() throws Exception {
        T concreteGeometry = mapper.readValue(geometryAsGeoJson, getType());
        assertThat(
                toJson(concreteGeometry),
                equalTo(geometryAsGeoJson));
    }

    @Test
    public void shouldDeserializeAsInterface() throws Exception {
        assertRoundTrip(geometry);
        assertThat(
                toJson(geometry),
                equalTo(geometryAsGeoJson));
    }

    protected String toJson(Object value) throws IOException {
        return writer.writeValueAsString(value);
    }

    protected void assertRoundTrip(T geom) throws IOException {
        String json = writer.writeValueAsString(geom);
        System.out.println(json);
        Geometry regeom = mapper.readerFor(Geometry.class).readValue(json);
        assertThat(geom.equalsExact(regeom), is(true));
    }
}
