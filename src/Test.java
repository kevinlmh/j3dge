import com.minghuiliu.base.engine.Matrix4f;

/**
 * Created by kevin on 2/1/17.
 */
public class Test {
    public static void main(String[] args) {
        Matrix4f a = new Matrix4f();
        a.initIdentity();
        System.out.println(a);

        Matrix4f b = new Matrix4f();
        b.initRotation(0, 0, 0);
        System.out.println(b);

        Matrix4f c = a.mul(b);
        System.out.println(c);
    }
}
