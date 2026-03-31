package com.bedatadriven.jackson.datatype.jts.serialization;

import com.bedatadriven.jackson.datatype.jts.parsers.GeometryParser;
import org.locationtech.jts.geom.Geometry;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class GeometryDeserializer<T extends Geometry> extends ValueDeserializer<T> {

    private GeometryParser<T> geometryParser;

    public GeometryDeserializer(GeometryParser<T> geometryParser) {
        this.geometryParser = geometryParser;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        JsonNode root = jsonParser.readValueAsTree();
        return geometryParser.geometryFromJson(root);
    }
}
