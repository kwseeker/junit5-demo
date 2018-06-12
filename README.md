# JUnit 5

官方文档： 
https://junit.org/junit5/docs/current/user-guide/  
官方demo: 
https://github.com/junit-team/junit5-samples  

个人现在对JUnit定位上只是一个工具，所以这篇文档只讲 JUnit 5 能帮我们做什么测试，测试的方法有哪些，是一个导引图（用于在需要时迅速查找到对应的知识点），关于具体的编程方法与完整的使用在工作以及个人项目需要时再具体研究。

编程demo会在 https://github.com/kwseeker/junit5-demo 逐步添加。

### 1. 简介

##### 1.1 支持Java版本
JUnit5只支持Java8及以上版本。

##### 1.2 与JUnit4的区别



##### 1.3 安装
JUnit 依赖：

![image](https://junit.org/junit5/docs/current/user-guide/images/component-diagram.svg)

##### 1.4 JUnit5 架构和体系

+ **JUnit5的三个子项目**

    **JUnit Jupiter** 包含了我们用于撰写测试的 API（关注点1），以及一个能理解测试代码的引擎（关注点2.1）。  
    **JUnit Platform** 提供了一套统一的 API 以运行测试，及基于 API 之上的一套工具（关注点2.2和2.3）。  
    **JUnit Vintage** 提供了一套引擎，用以++在 JUnit 5 中运行 JUnit 3 和 JUnit 4 的测试++（关注点2.1）。

    **关注点解耦**：  
    1.  一个支持测试代码撰写的 API
    2. 一个识别测试、运行测试的机制  
        2.1 一个识别、运行特定类型（比如，JUnit 5测试的机制）  
        2.2 另一套协调上述机制的机制  
        2.3 上两者之间的 API

    JUnit 5 的架构体系完全是遵循这个关注点分离思想的产物：  
    **junit-jupiter-api**(1)
    开发者用于撰写测试的 API，包含了我们在JUnit 5 的基础知识一节中所提及的所有注解、断言等。  
    **junit-platgorm-engine**(2.3)
    包含了一套所有测试引擎都必须实现的 API。这样，不同的测试引擎之间可以通过统一的接口被调用。引擎可以跑正常的 JUnit 测试，但也可以实现不同的引擎用以执行其他框架写成的测试，如 TestNG、Spock、Cucumber 等。  
    **junit-jupiter-engine**(2.1)
    junit-platform-engine API 的一个实现，专门用于执行 JUnit 5 撰写的测试。  
    **junit-vintage-engine**(2.1)
    junit-platform-engine API 的一个实现，专门用于执行 JUnit 3 或 JUnit 4 撰写的测试。过去，JUnit 4 的构件 junit-4.12 充当了两个角色：它既是开发人员用于实现测试的 API，又包含了用以执行测试的核心组件。这个引擎，可以认为是低版本的 JUnit 3/4 与 JUnit 5 之间的一个适配器。  
    **junit-platform-launcher**(2.2)
    这部分使用了一个服务加载器 ServiceLoader 来发现测试引擎，并协调不同实现之间的执行。它提供了一个 API 给 IDE 和构建工具，使得它们能够与测试执行过程交互，比如，运行单个的测试、搜集测试结果并展示等。

    ![image](http://7xqu8w.com1.z0.glb.clouddn.com/junit-5-architecture.png)

### 2. JUnit 功能 

##### 2.1 注解

通过注解配置测试和拓展测试框架。  
如：   
指定测试方法；  
指定参数化测试方法；  
重复测试；  
指定动态测试的测试工厂方法；  
配置测试实例的生命周期；  
指定测试用例的模板方法；  
为测试类或测试方法声明一个定制的显示名称；  
指定在[所有]测试方法（@Test）前后执行的方法；  
指定嵌套测试类；  
为测试打标签（用于测试过滤）；  
跳过测试；
注册自定义拓展。

##### 2.2 测试类和方法

##### 2.3 显示名字
@DisplayName("╯°□°）╯")   
这个在IDE中跑的时候并不会显示；mvn test时会显示。

##### 2.4 断言与设想

++断言不会抛出异常；设想可能抛出异常并中断执行++。

[JUnit5 Assertions API](https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/Assertions.html)  

引用第三方断言库：  
[AssertJ](http://joel-costigliola.github.io/assertj/)  
[Hamcrest](http://hamcrest.org/JavaHamcrest/)  
[Truth](http://google.github.io/truth/)

关于断言结果怎么被系统检测到以及怎么被处理是断言库内部实现的。

assertAll 类型，断言执行所有给定的可执行方法不会抛出异常。  
assertEquals 类型，断言给定的表达式结果和实际值相等。  
assertNotThrow 类型，断言给定的可执行方法不会抛出任何类型异常。
assertFalse 类型，断言条件为false。  
assertMatch 类型，断言给定的与实际的匹配。  
assertNotNull 类型，断言给定的对象不为空。  
assertNotSame 类型，断言给定的与实际的相同。  
assertTimeout 类型，断言方法执行在给定的时间前完成。  
fail 类型，返回的测试结果始终为失败的。  

[JUnit5 Assumptions API](https://junit.org/junit5/docs/current/api/org/junit/jupiter/api/Assumptions.html)  

assumeTrue 和 assumeFalse 判断条件是否为true或false, 与预期一致则继续执行后面代码，否则抛出错误。  
assumeThat则更像是会抛出异常的if条件判断语句，当条件满足执行后面的可执行语句，否则抛出异常。

##### 2.5 关闭测试
@Disabled

TODO: IDE中测试还是会跑被@Disabled注释的类和方法。为什么？

##### 2.6 条件测试执行

```
@EnableOnOS(MAC)
@EnableOnOS({LINUX, MAC})
@DisabledOnOs(WINDOWS)

@EnabledOnJre(JAVA_8)
@EnabledOnJre({ JAVA_9, JAVA_10 })
@DisabledOnJre(JAVA_9)

@EnabledIfEnvironmentVariable(named = "ENV", matches = "staging-server")
@DisabledIfEnvironmentVariable(named = "ENV", matches = ".*development.*")

@EnabledIf("2 * 3 == 6")
@DisabledIf("Math.random() < 0.314159")
@EnabledIf("'CI' == systemEnvironment.get('ENV')")
```

##### 2.7 加标签与过滤  

@Tag("xxx")  
标签格式有要求

##### 2.8 测试实例的生命周期

测试实例的默认生命周期是 PER_METHOD ；即只作用于方法。 

@TestInstance(Lifecycle.PER_CLASS)  
还有一种方法
-Djunit.jupiter.testinstance.lifecycle.default=per_class

##### 2.9 嵌套测试

@Nested

##### 2.10 构造器和方法的依赖注入

带参数的测试方法

+ RepetitionInfoParameterResolver
+ TestReporterParameterResolver
+ RandomParametersExtension

##### 2.11 测试接口和默认方法

##### 2.12 重复测试

@RepeatedTest(10)

有一个 RepetitionInfo 对象维护重复测试的信息，
有一个 TestInfo 对象维护测试的信息。

##### 2.13 参数化测试和使用参数

可以使用不同的参数重复运行多次测试。  
需要添加依赖： junit-jupiter-params。

```
@ParameterizedTest
@ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" })
void palindromes(String candidate) {
    assertTrue(isPalindrome(candidate));
}
```

参数数据源：  
@ValueSource  
@EnumSource   
@MethodSource 通过方法获取参数数据  
@CsvSource  可以将参数列表表示为逗号分隔的值（例如字符串)。
@CsvFileSource 指定使用的.csv文件
@ArgumentsSource 通过实现ArgumentsProvider接口的类提供参数  

还涉及参数转换与参数聚合的问题。  
还可以自定义参数聚合器，以及定制DisplayName。

```
@ParameterizedTest
@EnumSource(TimeUnit.class)
void testWithEnumSource(TimeUnit timeUnit) {
    assertNotNull(timeUnit);
}

@ParameterizedTest
@CsvSource({ "foo, 1", "bar, 2", "'baz, qux', 3" })
void testWithCsvSource(String first, int second) {
    assertNotNull(first);
    assertNotEquals(0, second);
}
```

CSV问价格式：
```
Country, reference
Sweden, 1
Poland, 2
"United States of America", 3
```

##### 2.14 测试模板

测试用例模板???

##### 2.15 动态测试

动态测试在运行时生成测试用例。

### 3 执行测试

##### 3.1 执行JUnit的测试有3种方法：  
1）在IDE中执行（IDEA和Eclipse都添加了对JUnit运行的支持）  
2）终端中用编译工具执行（如：Gradle和Maven）  
3）终端中使用 ConsoleLauncher 执行  
```
java -jar junit-platform-console-standalone-1.2.0.jar <Options>
```

##### 3.2 配置参数

##### 3.3 Tag表达式

### 4 拓展模型

比较高级的东西，使得任何开发人员或工具供应商都能扩展 JUnit 的核心功能。

Runner 通常是 BlockJUnit4ClassRunner 的子类，用于提供 JUnit 中没有直接提供的某种行为。目前有许多第三方 Runner，比如用于运行基于 Spring 的单元测试的 SpringJUnit4ClassRunner，以及用于处理单元测试中 Mockito 对象的 MockitoJUnitRunner。

+ JUnit 4 的扩展模型
    - Runners（运行器）
    - Rules（规则）
    - 现状
+ JUnit 5 的扩展模型
    - 扩展点
    - 无状态
    - 应用扩展
    - 自定义注解

### 5 将测试从JUnit4迁移到JUnit5

使用JUnit Vintage test engine迁移JUnit3/4测试到JUnit5.

具体参考官方demo  
https://github.com/junit-team/junit5-samples/  
junit5-migration-maven  
junit5-migration-gradle

### 6 JUnit5 平台启动器API （定制测试）

JUnit5的一个主要目标就是使JUnit和编译工具和IDEs之间的接口更加强大和稳定，目的是++将内部发现和执行测试与外部必要的过滤和配置分离开++。

++JUnit5引入了启动器的概念，它可以用来发现、过滤和执行测试++。此外，第三方测试库，比如Spock、Cucumber和FitNesse，可以通过提供定制的TestEngine来插入JUnit平台的启动器内部。

**这部分使用了一个服务加载器 ServiceLoader 来发现测试引擎，并协调不同实现之间的执行。它提供了一个 API 给 IDE 和构建工具，使得它们能够与测试执行过程交互，比如，运行单个的测试、搜集测试结果并展示等。**

### 7 JUnit 发行版本列表
https://junit.org/junit5/docs/current/release-notes/index.html#release-notes