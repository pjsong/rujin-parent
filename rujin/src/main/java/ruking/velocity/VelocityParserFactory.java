package ruking.velocity;

public class VelocityParserFactory {
	private static VelocityParser vp = null;
	public static VelocityParser getVP(){
		if(vp == null){
			vp = new VelocityParser();
		}
		return vp;
	}
}
