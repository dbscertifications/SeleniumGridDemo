package grid.groups;

import org.testng.annotations.Test;

/**
 * Created by my computers on 5/29/2016.
 */
public class TestGroups {

    @Test(priority = 1, alwaysRun = true, groups={"grp1"})
    public void Method1() {
        System.out.println("Method1");
    }

    @Test(priority = 2, alwaysRun = true, groups={"grp1","grp2"})
    public void Method2() {
        System.out.println("Method2");
    }

    @Test(priority = 3, alwaysRun = true, groups={"grp1","grp2"})
    public void Method3() {
        System.out.println("Method3");
    }

    @Test(priority = 4, alwaysRun = true, groups={"grp2"})
    public void Method4() {
        System.out.println("Method4");
    }

}
