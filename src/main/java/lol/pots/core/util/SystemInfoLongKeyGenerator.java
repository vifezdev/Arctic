package lol.pots.core.util;

import java.util.Arrays;
import java.util.List;

public class SystemInfoLongKeyGenerator {
    public static long generateUniqueLongKey() {
        List<String> properties = Arrays.asList(
                "java.version", "java.vendor", "java.vendor.url", "java.home",
                "java.vm.specification.version", "java.vm.specification.vendor",
                "java.vm.specification.name", "java.vm.version", "java.vm.vendor",
                "java.vm.name", "java.specification.version", "java.specification.vendor",
                "java.specification.name", "java.class.version", "java.class.path",
                "java.library.path", "java.io.tmpdir", "java.compiler", "java.ext.dirs",
                "os.name", "os.arch", "os.version", "file.separator", "path.separator",
                "line.separator", "user.name", "user.home", "user.dir"
        );

        StringBuilder sb = new StringBuilder();
        for (String key : properties) {
            sb.append(System.getProperty(key, ""));
        }

        return sb.toString().hashCode() & 0x00000000ffffffffL;
    }
}
