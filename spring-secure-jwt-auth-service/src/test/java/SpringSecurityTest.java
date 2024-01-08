import com.bidpages.JwtApplicationStarter;
import com.bidpages.mapper.SysUserMapper;
import com.bidpages.mdm.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@SpringBootTest(classes = JwtApplicationStarter.class)
public class SpringSecurityTest {

    @Autowired
    SysUserMapper sysUserMapper;


    /**
     * 插入一个系统用户
     */
    @Test
    public void testAddSysUser() {
        SysUser newSysUser = SysUser.builder().
                userName("jinmingchao").
                password("forgive1").
                nickName("jmc").
                userType("0").
                email("441821724@qq.com").
                phoneNum("13261301850").
                sex("0").build();

        Assertions.assertEquals(1, sysUserMapper.insert(newSysUser));
    }

    /**
     * 测试生成用户密码密文
     */
    @Test
    public void testSpringSecurityDefaultEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode("forgive1");
        System.out.println("encryptedPassword: " + encryptedPassword);
        Assertions.assertTrue(passwordEncoder.matches("forgive1", encryptedPassword));
    }

}
