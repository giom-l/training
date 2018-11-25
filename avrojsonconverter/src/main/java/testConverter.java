import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import tech.allegro.schema.json2avro.converter.JsonAvroConverter;

import java.io.UnsupportedEncodingException;


public class testConverter {
    public static void main (String[] args) throws UnsupportedEncodingException {
        JsonAvroConverter converter = new JsonAvroConverter();
        String schema =
                "{" +
                        "   \"type\" : \"record\"," +
                        "   \"name\" : \"Acme\"," +
                        "   \"fields\" : [{ \"name\" : \"username\", \"type\" : \"string\" }, { \"name\" : \"age\", \"type\":\"int\"}]" +
                        "}";
        GenericRecord test = generateGenericRecord("guillaume", 33, schema);
        byte[] bytes = converter.convertToJson(test);
        System.out.println(new String(bytes, "UTF-8"));


    }

    static GenericRecord generateGenericRecord(String name, int age, String schema) {
        Schema.Parser parser = new Schema.Parser();
        Schema schemaParsed = parser.parse(schema);
        GenericRecord gr = new GenericData.Record(schemaParsed);
        gr.put("username", name);
        gr.put("age", age);
        return gr;
    }

}
