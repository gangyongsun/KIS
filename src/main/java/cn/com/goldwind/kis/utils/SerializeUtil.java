package cn.com.goldwind.kis.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.sf.json.JSONObject;

/**
 * Java原生版的 Serialize
 * 
 */
public class SerializeUtil {
	static final Class<?> CLAZZ = SerializeUtil.class;

	public static byte[] serialize(Object object) {
		if (object == null) {
			throw new NullPointerException("Can't serialize null");
		}
		byte[] bytes = null;
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			bytes = baos.toByteArray();
		} catch (Exception e) {
			LoggerUtils.fmtError(CLAZZ, e, "serialize error %s", JSONObject.fromObject(object));
		} finally {
			close(oos);
			close(baos);
		}
		return bytes;
	}

	public static Object deserialize(byte[] bytes) {
		return deserialize(bytes, Object.class);
	}

	public static <T> T deserialize(byte[] bytes, Class<T>... requiredType) {
		Object object = null;
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			if (bytes != null) {
				bais = new ByteArrayInputStream(bytes);
				ois = new ObjectInputStream(bais);
				object = ois.readObject();
			}
		} catch (Exception e) {
			LoggerUtils.fmtError(CLAZZ, e, "serialize error %s", bytes);
		} finally {
			close(ois);
			close(bais);
		}
		return (T) object;
	}

	private static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
				closeable = null;
			} catch (IOException e) {
				LoggerUtils.fmtError(CLAZZ, "close stream error");
			}
		}
	}

}
