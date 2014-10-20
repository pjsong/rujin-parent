package ruking.utils;

public class RegExp {
	public static String emailRegExp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z]{2,}){1}$)";

	
	
	
	
	public static void main(String[] args){
		System.out.println("xrsx  savc".matches(emailRegExp));
		System.out.println(" 122.bx@savc".matches(emailRegExp));
		System.out.println("erz.fv1@ssc.com".matches(emailRegExp));
	}
}
