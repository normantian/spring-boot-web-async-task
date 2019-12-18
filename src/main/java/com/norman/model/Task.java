package com.norman.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author tianfei
 * @version 1.0.0
 * @description 任务实体类
 * @date 2019/11/11 9:08 AM.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private int taskId;

    private DeferredResult<ResponseMsg<String>> taskResult;

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskResult" + "{responseMsg=" + taskResult.getResult() + "}" +
                '}';
    }
}
