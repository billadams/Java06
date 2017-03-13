package bestbooks.ui;

public class StringUtil {

    public static String padWithSpaces(String s, int length) 
    {
        if (s.length() < length) {
            StringBuilder sb = new StringBuilder(s);
            while (sb.length() < length) {
                sb.append(" ");
            }
            return sb.toString();
        } else {
            return s.substring(0, length);
        }
    }
    
//    public static String padWithCharacters(String charToPadWith, int length)
//    {
//        if (s.length() < length) {
//    		String s;
//            StringBuilder sb = new StringBuilder(s);
//            while (sb.length() < length) {
//                sb.append(charToPadWith);
//            }
//            return sb.toString();
//        } else {
//            return s.substring(0, length);
//        }
//    }
}
