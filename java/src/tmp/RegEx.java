import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {
	public static void main(String[] args) {
		Pattern p = Pattern.compile("(a*)*b");
		Matcher matcher = p.matcher("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		matcher.find();
	}
}
