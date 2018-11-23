package self.practice.string;

public class TernaryExpressionSplite {
    public static void main() {
        String color = "黑色";
        String size = "L";

        String com = "颜色：" + color == null ? null : color + "尺码：" + size == null ? null : size;

        System.out.println(com);
        //System.out.println("Hello World!");
    }
}
