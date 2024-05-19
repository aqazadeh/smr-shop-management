package smr.shop.libs.common.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GrpcHelper {

    public static <T> List<T> iteratorToList(Iterator<T> iterator) {
        List<T> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }
}
