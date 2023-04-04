package addressbook.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Slf4j
public class JsonUtil {

    public static String toJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String content, Class<T> tClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(content, tClass);
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    public static byte[] toJsonAndZip(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            zipOutputStream.putNextEntry(new ZipEntry("def"));
            zipOutputStream.write(objectMapper.writeValueAsBytes(object));
        } catch (IOException e) {
            log.error("", e);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static <T> T fromZipAndJson(byte[] bytes, Class<T> tClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        T ret = null;

        try (ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(bytes))) {

            if (zipInputStream.getNextEntry() != null) {
                ret = objectMapper.readValue(zipInputStream, tClass);
            }
        } catch (IOException e) {
            log.error("", e);
        }

        return ret;
    }

}
