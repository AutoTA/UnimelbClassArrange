import java.util.ArrayList;

public class testArraylist {
	public static void main(String[] args) {
		ArrayList<Lesson> lessons = new ArrayList<Lesson>();
		for (int i = 0; i < 9; i++) {
			lessons.add(new Lesson());
		}
		System.out.println(lessons.size());
		for (int i = 0; i < 5; i++) {
			System.out.println(lessons.get(i).start);
		}
	}
	
	public static class Lesson{
		double start = 12.15;
		double end = 13.15;
		String locatio = "peter hall";
	}
}
