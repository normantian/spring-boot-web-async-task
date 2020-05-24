package com.norman.controller;

import com.google.common.collect.Lists;
import com.norman.model.NodeDTO;
import com.norman.service.ZkService;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/26 10:02 AM.
 */
//@RestController
//@RequestMapping(value = "/zk")
public class ZooKeeperController {

    @Autowired
    ZkService zkService;

    @GetMapping("/nodes")
    public List<String> listChildren(@RequestParam(name = "pnode") String pnode) {
        List<String> list = Lists.newArrayList();

        try{
            list = zkService.listChildren(pnode);
        } catch (Exception ex){

        }

        return list;
    }

    @GetMapping("/node")
    public String getNode(@RequestParam(name = "nodeName") String nodeName) throws Exception {
        final String node = zkService.getNode(nodeName);
        return node;
    }

    @GetMapping("/checkExist")
    public Stat checkNode(@RequestParam(name = "nodeName") String nodeName) throws Exception {
        return zkService.checkExist(nodeName);
    }

    @GetMapping("/nodeType")
    public CreateMode getNodeType(@RequestParam(name = "nodeName") String nodeName) throws Exception {
        return zkService.getNodeType(nodeName);
    }

    @PostMapping("/node")
    public void createNode(@RequestBody NodeDTO nodeDTO) throws Exception{

        if(CreateMode.fromFlag(nodeDTO.getNodeType()).isEphemeral()){
            zkService.createEphemeralNode(nodeDTO.getNodePath(), nodeDTO.getNodeData());
        } else {
            zkService.createNode(nodeDTO.getNodePath(), nodeDTO.getNodeData());
        }

    }

}
