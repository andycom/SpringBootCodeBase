package com.fancv.controller;

import com.esotericsoftware.kryo.Kryo;
import com.fancv.Entity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.redisson.api.*;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.KryoCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 这算是kryo的一个特点，可以把对象信息直接写到序列化数据里，反序列化的时候可以精确地找到原始类信息，
 * 不会出错，这意味着在写readxxx方法时，无需传入Class或Type类信息。
 * 但是如果改了类名会导致反序列化报错，如果如果换了类名需要不同的key
 */
@Api(tags = "测试API")
@RestController
public class TestController {

    @Autowired
    private RedissonClient redissonClient;

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    /**
     * 测试Lua脚本
     *
     * @return
     */
    @GetMapping("testLua")
    @ApiOperation(value = "测试Lua脚本", notes = "测试Lua脚本")
    @ResponseBody
    public String testLua() throws IOException {
        RScript script = redissonClient.getScript(StringCodec.INSTANCE);

        // 组装lua 入参
        List<Object> keys = new ArrayList<>();
        keys.add("001*");
        Object[] args = new Object[1];
        args[0] = "3";
        List<Object> entity_1 = script.eval(RScript.Mode.READ_ONLY, "return redis.call('keys', KEYS[1])", RScript.ReturnType.MULTI, keys);

        String entity = script.eval(RScript.Mode.READ_ONLY, "return redis.call('HGET', 'testHset', ARGV[1])", RScript.ReturnType.MULTI, keys, args);
        System.out.println("返回结果" + entity);
        return entity;
    }

    /**
     * 测试redis写入
     *
     * @return
     */
    @GetMapping("testRedis")
    @ApiOperation(value = "测试redis写入", notes = "测试redis写入")
    @ResponseBody
    public String testRedis() {
        String name = "test";
        String value = "1234566";

        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);

        KryoCodec codec = new KryoCodec();
        codec.yield(kryo);

        RBucket<String> bucket = redissonClient.getBucket(name);
        bucket.set(value);
        RBucket<HashMap> bucketMap = redissonClient.getBucket("test_map",codec);
        System.out.println(bucket.get());
        RMap rMap = redissonClient.getMap("testHSet",codec);
        rMap.fastPut(name, value);
        RBucket<Entity> entityBucket = redissonClient.getBucket("entity",codec);
        entityBucket.set(Entity.of("123", 123));
        return "success";
    }

    /**
     * 测试redis写入
     *
     * 注意redission 支持多种序列化方式，根据实际情况选择
     * 是否需要 redis 中可读性 数据量大的情况需要考虑redis 中内存使用情况
     *
     *
     *
     * @return
     */
    @GetMapping("testRedis2")
    @ApiOperation(value = "测试redis2写入", notes = "测试redis2写入")
    @ResponseBody
    public String testRedis2() {
        String name = "test2";
        String value = "1234566";


        RBucket<String> bucket = redissonClient.getBucket(name);
        bucket.set(value);
        RBucket<HashMap> bucketMap = redissonClient.getBucket("test_map_2",StringCodec.INSTANCE);
        System.out.println(bucket.get());
        HashMap<String, Entity> dataMap = new HashMap<>();
        dataMap.put(name, Entity.of("123redis ", 123));
        bucketMap.set(dataMap);
        RMap rMap = redissonClient.getMap("testHSet_2");
        rMap.fastPut(name, value);
        RBucket<Entity> entityBucket = redissonClient.getBucket("entity_2", JsonJacksonCodec.INSTANCE);
        entityBucket.set(Entity.of("123_2", 123));
        return "success";
    }



    /**
     * 测试redis写入
     *
     * @return
     */
    @GetMapping("testRedisMap")
    @ApiOperation(value = "测试redisMap写入", notes = "测试redis写入")
    @ResponseBody
    public String testRedisMap() {
        String name = "map";
        String value = "1234566";
        KryoCodec codec = new KryoCodec();
       // kryo 5.0 需要设置 set
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);

        codec.yield(kryo);
        RBucket<HashMap> bucketMap = redissonClient.getBucket("test_map",codec);

        HashMap<String, Entity> dataMap = new HashMap<>();
        dataMap.put(name, Entity.of("123", 123));
        bucketMap.set(dataMap);

        RBucket<HashMap> bucketMap2 = redissonClient.getBucket("test_map",codec);

        bucketMap2.size();

        return "success";
    }


    /**
     * 测试闭锁
     *
     * @return
     */
    @GetMapping("testCountDownLatch")
    @ApiOperation(value = "测试闭锁", notes = "测试闭锁")
    @ResponseBody
    public String testCountDownLatch() {

        /**
         * 闭锁
         */
        cachedThreadPool.submit(() -> {
            try {
                RCountDownLatch latch = redissonClient.getCountDownLatch("anyCountDownLatch");
                latch.trySetCount(1);
                System.out.println("设立Count, 进入等待");
                latch.await();
                System.out.println("完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        /**
         * 闭锁
         */
        cachedThreadPool.submit(() -> {
            RCountDownLatch latch = redissonClient.getCountDownLatch("anyCountDownLatch");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成一个");
            latch.countDown();
        });
        return "success";
    }

    /**
     * 测试获取信号量
     *
     * @return
     */
    @GetMapping("testGetSemaphore")
    @ApiOperation(value = "测试获取信号量", notes = "测试获取信号量")
    @ResponseBody
    public String testSemaphore() throws InterruptedException {

        RPermitExpirableSemaphore semaphore = redissonClient.getPermitExpirableSemaphore("mySemaphore");
        semaphore.trySetPermits(1);

        /**
         * 获取信号量，一定时候后自动返回
         */
        cachedThreadPool.submit(() -> {
            try {
                semaphore.acquire(2, TimeUnit.SECONDS);
                System.out.println(Thread.currentThread().getId() + "时限获得信号量");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        /**
         * 获取信号量
         */
        cachedThreadPool.submit(() -> {
            try {
                String permitId = semaphore.acquire();
                try {
                    System.out.println(Thread.currentThread().getId() + "获得信号量");
                    Thread.sleep(10000);
                } finally {
                    semaphore.release(permitId);
                    System.out.println(Thread.currentThread().getId() + "释放信号量");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        /**
         * 尝试获取信号量，获取不到则返回null
         */
        cachedThreadPool.submit(() -> {
            try {
                String permitId = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (permitId == null) {
                    System.out.println(Thread.currentThread().getId() + "try没有获得信号量");
                } else {
                    try {
                        System.out.println(Thread.currentThread().getId() + "try获得信号量");
                        Thread.sleep(10000);
                    } finally {
                        semaphore.release(permitId);
                        System.out.println(Thread.currentThread().getId() + "释放信号量");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        return "success";
    }

    /**
     * 测试获取锁
     *
     * @return
     */
    @GetMapping("testGetLock")
    @ApiOperation(value = "测试获取锁", notes = "测试获取锁")
    @ResponseBody
    public String testGetLock() {

        /**
         * 获取锁
         */
        cachedThreadPool.submit(() -> {
            RLock lock = redissonClient.getFairLock("test");
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getId() + " get Lock");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getId() + " get unLock ");
            }
        });

        /**
         * 重入锁
         */
        cachedThreadPool.submit(() -> {
            RLock lock = redissonClient.getFairLock("test");
            lock.lock();
            System.out.println(Thread.currentThread().getId() + " reGet first Lock");
            try {
                RLock reLock = redissonClient.getFairLock("test");
                reLock.lock();
                try {
                    System.out.println(Thread.currentThread().getId() + " reGet second Lock");
                } finally {
                    lock.unlock();
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getId() + " reGet unLock");
            }
        });

        /**
         * 尝试获取锁
         */
        cachedThreadPool.submit(() -> {
            try {
                RLock lock = redissonClient.getFairLock("test");
                if (lock.tryLock(1, TimeUnit.SECONDS)) {
                    try {
                        System.out.println(Thread.currentThread().getId() + " trt get Lock");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } finally {
                        lock.unlock();
                        System.out.println(Thread.currentThread().getId() + " trt get unLock ");
                    }
                } else {
                    System.out.println(Thread.currentThread().getId() + " not get Lock");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return "success";
    }

}
