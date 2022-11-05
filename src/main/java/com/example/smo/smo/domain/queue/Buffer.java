package com.example.smo.smo.domain.queue;

import com.example.smo.smo.domain.Task;

import java.util.*;

public class Buffer {

    public final Deque<Task> queue;
    public final Long bufferSize;

    public Buffer(Long bufferSize) {
        this.bufferSize = bufferSize;
        queue = new ArrayDeque<>(bufferSize.intValue());
    }

    public boolean add(Task task) {
        if(queue.size() == bufferSize){
            oldTask = getMostOldTask();
            oldTask.isRejected = true;
            addinsted(oldTask, task);
//            task.isRejected = true;
//            return false;
            return true;
        }
        queue.push(task);
        task.isRejected = false;
        return true;
    }

    //пытается найти по такому же типу прибора, иначе возвращает первую таску очереди, если очередь не пуста
    public Task getNextTask(Long producerId) {
        if(queue.size() == 0)
            return null;

        var deviceTask = getTaskByProducerId(producerId);
        if(deviceTask != null) {
            queue.remove(deviceTask);
            return deviceTask;
        }
        var mostPrioritizedTask = getMostPrioritizedTask();
        queue.remove(mostPrioritizedTask);
        return mostPrioritizedTask;
    }

    private Task getMostPrioritizedTask() {
        var copy = new ArrayList<>(queue.stream().toList());
        Collections.reverse(copy);
        Task resultTask = null;
        for(var task: copy) {
            if(resultTask == null || task.producerId > resultTask.producerId)
                resultTask = task;
        }
        return resultTask;
    }

    private Task getTaskByProducerId(Long producerId) {
        var copy = new ArrayList<>(queue.stream().toList());
        Collections.reverse(copy);
        for(var task: copy) {
            if(task.producerId.equals(producerId))
                return task;
        }
        return null;
    }
}
