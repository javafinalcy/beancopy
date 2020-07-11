package com.workit.beancopy;

import com.workit.beancopy.bean.UserBO;
import com.workit.beancopy.bean.UserMapping;
import com.workit.beancopy.bean.UserVO;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

/**
 * @author:
 * @Date: 2020/7/11
 * @Description:
 */

/**
 * 用来配置 Mode 选项，可用于类或者方法上，
 * 这个注解的 value 是一个数组，可以把几种 Mode 集合在一起执行，
 * 如：@BenchmarkMode({Mode.SampleTime, Mode.AverageTime})，
 * 还可以设置为 Mode.All，即全部执行一遍
 */
@BenchmarkMode(Mode.AverageTime)
/**
 * 预热所需要配置的一些基本测试参数，可用于类或者方法上。一般前几次进行程序测试的时候都会比较慢，所以要让程序进行几轮预热，保证测试的准确性。参数如下所示：
 *
 * iterations：预热的次数
 * time：每次预热的时间
 * timeUnit：时间的单位，默认秒
 * batchSize：批处理大小，每次操作调用几次方法
 *
 * 为什么需要预热？
 * 因为 JVM 的 JIT 机制的存在，如果某个函数被调用多次之后，JVM 会尝试将其编译为机器码，从而提高执行速度，所以为了让 benchmark 的结果更加接近真实情况就需要进行预热。
 */
@Warmup(iterations = 3, time = 1)
/**
 * 实际调用方法所需要配置的一些基本测试参数，可用于类或者方法上，参数和 @Warmup 相同。
 */
@Measurement(iterations = 5, time = 5)
/**
 * 每个进程中的测试线程，可用于类或者方法上。
 */
@Threads(6)
/**
 * 进行 fork 的次数，可用于类或者方法上。如果 fork 数是 2 的话，则 JMH 会 fork 出两个进程来进行测试
 */
@Fork(1)
/**
 * 指定某项参数的多种情况，特别适合用来测试一个函数在不同的参数输入的情况下的性能，只能作用在字段上，使用该注解必须定义 @State 注解。
 */
@State(value = Scope.Benchmark)
/**
 * 为统计结果的时间单位，可用于类或者方法注解
 */
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class BeanCopyTest {
    @Param(value = {"1","10","100"})
    private int count;

    public UserBO bo;

    public  BeanCopier copier;

    @Setup(Level.Trial) // 初始化方法，在全部Benchmark运行之前进行
    public void init() {
        copier = BeanCopier.create(UserBO.class, UserVO.class, false);
        bo = new UserBO();
        bo.setUserName("java金融");
        bo.setAge(1);
        bo.setIdCard("88888888");
        bo.setEmail("java金融@qq.com");
    }


    public static void main(String[] args) throws RunnerException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
       Options opt = new OptionsBuilder().include(BeanCopyTest.class.getSimpleName()).result("result.json").resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();

    }

    /**
     * 使用mapStruct来操作
     */
    @Benchmark
    public void mapStruct() {
        for (int i = 1; i <= count; i++) {
            UserVO vo = UserMapping.INSTANCE.converter(bo);
        }
    }

    /**
     * 手动set和Get
     */
    @Benchmark
    public void setAndGet() {
        for (int i = 1; i <= count; i++) {
            UserVO userVO = new UserVO();
            userVO.setUserName(bo.getUserName());
            userVO.setEmail(bo.getEmail());
            userVO.setSex(bo.getSex());
            userVO.setIdCard(bo.getIdCard());
            userVO.setAge(bo.getAge());
        }
    }

    /**
     * 使用cglib的copy方法
     */
    @Benchmark
    public void cglibBeanCopier() {
        for (int i = 1; i <= count; i++) {
            UserVO vo = new UserVO();
            copier.copy(bo, vo, null);
        }
    }

    /**
     * 使用spring提供的copyProperties方法
     */
    @Benchmark
    public void springBeanUtils() {
        for (int i = 1; i <= count; i++) {
            UserVO vo = new UserVO();
            BeanUtils.copyProperties(bo, vo);
        }
    }

    /**
     * 使用apache的copyProperties方法
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Benchmark
    public void apacheBeanUtils() throws InvocationTargetException, IllegalAccessException {
        for (int i = 1; i <= count; i++) {
            UserVO vo = new UserVO();
            org.apache.commons.beanutils.BeanUtils.copyProperties(vo, bo);
        }
    }


}
