import com.zscp.master.util.StringUtil;
import org.junit.Assert;
import org.junit.Test;

public class StringUtilTester {

    @Test
    public void testEndWith(){
        Assert.assertEquals(StringUtil.endWith("abce","ce",true),true);
    }

    @Test
    public void testEndWithWrong(){
        Assert.assertEquals(StringUtil.endWith("abce","ce",true),false);
    }
}
