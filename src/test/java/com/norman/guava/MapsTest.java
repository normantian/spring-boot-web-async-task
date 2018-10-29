package com.norman.guava;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.common.collect.TreeMultimap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/29 4:36 PM.
 */
public class MapsTest {

    public static void main(String[] args) {

        ArrayList<String> strings = Lists.newArrayList("string", "he", "she");


        ImmutableMap<Integer, String> stringsByIndex = Maps.uniqueIndex(strings, new Function<String, Integer>() {
            public Integer apply(String string) {
                return string.length();
            }
        });

        System.out.println(stringsByIndex);

        //如果索引值不是独一无二的，请参见下面的Multimaps.index方法。

        ArrayList<String> strings1 = Lists.newArrayList("string", "he", "she", "who", "am", "pm");

        final Function<String, Integer> ftFunction = (string) -> string.length();

        final ImmutableListMultimap<Integer, String> index = Multimaps.index(strings1, //ftFunction
                (string) -> string.length()
        );
        System.out.println(index);

        //Maps.difference(Map, Map)用来比较两个Map以获取所有不同点, 该方法返回MapDifference对象

        Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
        Map<String, Integer> right = ImmutableMap.of("b", 2, "c", 4, "d", 5);
        MapDifference<String, Integer> diff = Maps.difference(left, right);
        System.out.println(diff);

        System.out.println("entries in common : " + diff.entriesInCommon()); // {"b" => 2}, 两个Map中都有的映射项，包括键与值
        System.out.println("entries in differing : " + diff.entriesDiffering()); // {"c" => (3, 4)}, 键相同但是值不同的映射项。

        System.out.println("entries in left map : " + diff.entriesOnlyOnLeft()); // {"a" => 1}, 键只存在于左边Map的映射项
        System.out.println("entries in right map : " + diff.entriesOnlyOnRight()); // {"d" => 5}, 键只存在于右边Map的映射项

        //鉴于Multimap可以把多个键映射到同一个值，也可以把一个键映射到多个值，反转Multimap也会很有用.
        TreeMultimap<String, Integer> inverse = Multimaps.invertFrom(index, TreeMultimap.create());

        System.out.println(inverse);

        Map<String, Integer> map = ImmutableMap.of("a", 1, "b", 1, "c", 2);
        SetMultimap<String, Integer> multimap = Multimaps.forMap(map);
        System.out.println(multimap);
        // multimap maps ["a" => {1}, "b" => {1}, "c" => {2}]
        Multimap<Integer, String> inverse2 = Multimaps.invertFrom(multimap, HashMultimap.<Integer, String> create());
        System.out.println(inverse2);
        // inverse maps [1 => {"a", "b"}, 2 => {"c"}]


        //Tables.newCustomTable(Map, Supplier)允许你指定Table用什么样的map实现行和列。
        // use LinkedHashMaps instead of HashMaps
        final LinkedHashMap<String, Map<Character, Integer>> stringMapLinkedHashMap
                = Maps.<String, Map<Character, Integer>>newLinkedHashMap();

//        stringMapLinkedHashMap.put("key1", ImmutableMap.of('a', 1, 'b', 1, 'c', 2));
//        stringMapLinkedHashMap.put("key2", ImmutableMap.of('a', 14, 'b', 15, 'd', 25));

        Table<String, Character, Integer> table = Tables.newCustomTable(
                stringMapLinkedHashMap,
                new Supplier<Map<Character, Integer>>() {
                    public Map<Character, Integer> get() {
                        return Maps.newLinkedHashMap();
//                        return Maps.newLinkedHashMap();

                    }
                }
        );

        System.out.println(table);
        //transpose(Table<R, C, V>)方法允许你把Table<C, R, V>转置成Table<R, C, V>。例如，如果你在用Table构建加权有向图，这个方法就可以把有向图反转。

    }
}
