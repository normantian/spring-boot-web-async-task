package com.norman.guava;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

import java.util.Set;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/29 6:09 PM.
 */
public class RangeSetTest {

    public static void main(String[] args) {
        //RangeSet描述了一组不相连的、非空的区间。
        //当把一个区间添加到可变的RangeSet时，所有相连的区间会被合并，空区间会被忽略

        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10)); // {[1, 10]}
        rangeSet.add(Range.closedOpen(11, 15)); // 不相连的区间: {[1, 10], [11, 15)}
        rangeSet.add(Range.closedOpen(15, 20)); // 相连的区间; {[1, 10], [11, 20)}
        rangeSet.add(Range.openClosed(0, 0)); // 空区间; {[1, 10], [11, 20)}
        rangeSet.remove(Range.open(5, 10)); // 分割[1, 10]; {[1, 5], [10, 10], [11, 20)}

        System.out.println(rangeSet);

        rangeSet.asRanges().forEach(System.out::println);

        final Set<Range<Integer>> ranges = rangeSet.asRanges();
        for (Range<Integer> range : ranges){
            System.out.println(range.lowerEndpoint() +"-"+ range.upperEndpoint() );
            System.out.println(range.hasUpperBound() +"-" + range.hasLowerBound());
//            System.out.println(range.hasUpperBound());
        }

    }
}
