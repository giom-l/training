import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import tech.allegro.schema.json2avro.converter.JsonAvroConverter;

import java.io.UnsupportedEncodingException;


public class testConverter {

    public testConverter(){}

    public static void main (String[] args) throws UnsupportedEncodingException {
        JsonAvroConverter converter = new JsonAvroConverter();
        testConverter test = new testConverter();
        String schema =
                "{" +
                        "   \"type\" : \"record\"," +
                        "   \"name\" : \"Acme\"," +
                        "   \"fields\" : [{ \"name\" : \"username\", \"type\" : \"string\" }, { \"name\" : \"age\", \"type\":\"int\"}, { \"name\" : \"date\", \"type\" : [\"null\", {" +
                        "            \"type\" : \"long\"," +
                        "            \"logicalType\" : \"timestamp-millis\"}]}]" +
                        "}";
        GenericRecord gr = test.generateGenericRecord("guillaume", 33, System.currentTimeMillis(), schema);
        byte[] bytes = converter.convertToJson(gr);
        String jsonString = new String(bytes, "UTF-8");
        System.out.println(jsonString);


    }

    private GenericRecord generateGenericRecord(String name, int age, Long date, String schema) {
        Schema.Parser parser = new Schema.Parser();
        Schema schemaParsed = parser.parse(schema);
        GenericRecord gr = new GenericData.Record(schemaParsed);
        gr.put("username", name);
        gr.put("age", age);
        gr.put("date", date);
        return gr;
    }

}
