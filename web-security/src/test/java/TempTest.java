import org.junit.Test;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class TempTest {
    @Test
    public void testMD5(){
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        System.out.println(encoder.encodePassword("123456",null));
    }

}
