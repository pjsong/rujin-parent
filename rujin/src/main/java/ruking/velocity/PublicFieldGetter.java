package ruking.velocity;

import java.lang.reflect.Field;

public class PublicFieldGetter
{
	private Object obj;
	
	public PublicFieldGetter(Object obj)
	{
		this.obj = obj;
	}
	
	// return the value of the public field
	public Object get(String fieldName) throws Exception
	{
		Field field = obj.getClass().getField(fieldName);
		if (field != null)
		{
			return field.get(obj);
		}
		return null;
	}
}
