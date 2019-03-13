package com.norman.controller;

import lombok.extern.slf4j.Slf4j;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2019/3/12 9:35 AM.
 */
@RestController
@Slf4j
@RequestMapping("/articles")
public class ArticleController {


    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @Resource(name = "stringRedisTemplate")
    HashOperations<String, String, String> hashOperations;

    @Resource(name = "stringRedisTemplate")
    SetOperations<String, String> setOperations;



    private final static String ARTICLE_KEY_PATTERN = "article:{0}";

    private final static String ARTICLE_SET_KEY = "articles";

    @PostMapping("/article/{id}/like")
    public Object likeArticle(@PathVariable("id") String id){
        StopWatch sw = new Slf4JStopWatch();

        setOperations.add(ARTICLE_SET_KEY,id);

        hashOperations.increment(MessageFormat.format(ARTICLE_KEY_PATTERN, id), "like", 1);
        sw.stop("like article {}", id);
        return "success";
    }

    @PostMapping("/article/{id}/repost")
    public Object repostArticle(@PathVariable("id") String id){
        StopWatch sw = new Slf4JStopWatch();
        setOperations.add(ARTICLE_SET_KEY,id);

        hashOperations.increment(MessageFormat.format(ARTICLE_KEY_PATTERN, id), "repost", 1);
        sw.stop("repost article", id);
        return "success";
    }

    @PostMapping("/article/{id}/comment")
    public Object commentArticle(@PathVariable("id") String id){
        StopWatch sw = new Slf4JStopWatch();
        setOperations.add(ARTICLE_SET_KEY,id);

        hashOperations.increment(MessageFormat.format(ARTICLE_KEY_PATTERN, id), "comment", 1);
        sw.stop("comment article", id);
        return "success";
    }

    @GetMapping("/article/{id}")
    public Object getArticleInfo(@PathVariable("id") String id) {
        StopWatch sw = new Slf4JStopWatch();
        setOperations.add(ARTICLE_SET_KEY,id);

        final Map<String, String> entries = hashOperations.entries(MessageFormat.format(ARTICLE_KEY_PATTERN, id));

        sw.stop("get article", id);
        return entries;
    }

    @GetMapping("")
    public Object listAllArticles() {
        StopWatch sw = new Slf4JStopWatch();

        final Set<String> articleIds = setOperations.members(ARTICLE_SET_KEY);

        Map<String , Map<String, String>> resultMap = new HashMap<>(articleIds.size());

//        for(String id : articleIds) {
//            Map<String, String> entries = hashOperations.entries(MessageFormat.format(ARTICLE_KEY_PATTERN, id));
//            resultMap.putIfAbsent(id, entries);
//        }

        articleIds.stream().forEach(id -> {
            Map<String, String> entries = hashOperations.entries(MessageFormat.format(ARTICLE_KEY_PATTERN, id));
            resultMap.putIfAbsent(id, entries);
        });

        sw.stop("get all articles");

        return resultMap;

    }

    @DeleteMapping("/article/{id}")
    public Object deleteArticle(@PathVariable("id") String id) {
        StopWatch sw = new Slf4JStopWatch();

        final String articleKey = MessageFormat.format(ARTICLE_KEY_PATTERN, id);
        hashOperations.keys(articleKey).stream().forEach(System.out::println);

        hashOperations.values(articleKey).stream().forEach(System.out::println);

        if(hashOperations.getOperations().hasKey(articleKey)){
            hashOperations.getOperations().delete(articleKey);
        }
        sw.stop("delete article", id);

        return "success";
    }

}
