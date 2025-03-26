# Appium_Test_Auto

# 테스트 자동화의 방법 중 하나인 Appium을 적용기

플러터도 지원을 하는 것 같은데 일단은 네이티브 안드, iOS에 먼저 적용해본 뒤 플러터도 해볼 예정


## Gradle 설정
```
implementation("io.appium:java-client:9.4.0")
implementation("org.seleniumhq.selenium:selenium-java:4.28.0")
```

java-client와 selenium간에 버전을 맞춰줘야한다 
[공식문서 버전 매핑 참고]("https://github.com/appium/java-client?tab=readme-ov-file#compatibility-matrix")

## Driver
안드로이드의 경우 UI를 찾고 행동을 하기 위한 드라이버는 EspressoDriver와 UIAutomator2가 있는데 이중 UIAutomater2를 선택했다
UIAutomator2와 Espresso 모두 Compose를 지원하는데 서로 방식이 조금 다르다

UIAutomator2는 semantics의 testTag나 화면에 보여지는 텍스트를 기반으로 엘리먼트를 찾아야하는데 
Espresso는 Compose 엘리먼트에 직접적으로 접근할 수 있게 해준다

### Espresso Driver를 포기하게된 이유

메모차 적어두면 espresso는 compose뷰에 접근하기 전 
`driver.setSetting("driver", "compose")`
드라이버 세팅을 해줘야한다

구글링이나 공식문서를 봐도 나오지않는 오류에 막혀 몇일의 시간을 버리게 된 것이 가장 큰 이유다
`java.lang.NoSuchMethodError: No virtual method composeKt`
이 오류는 client단에서 EspressoBuildConfig를 추가해서 해결을 했다
```
{
  "toolsVersions": {
    "compileSdk": 31
  },
  "additionalAndroidTestDependencies": [
    "androidx.compose.runtime:runtime:1.1.1",
    "androidx.compose.ui:ui-tooling:1.1.1",
    "androidx.compose.foundation:foundation:1.1.1",
    "androidx.compose.material:material:1.1.1"
  ]
}
```
그 이후에는 `java.lang.NoSuchMethodError: No virtual method addOnConfigurationChangedListener` 같은 에러가 나왔고 
이것도 위와 비슷하게 EspressoBuildConfig에 
``` 
{
  "additionalAppDependencies": [
    "androidx.fragment:fragment:1.8.0",
    "androidx.core:core-ktx:1.10.1",
    "androidx.lifecycle:lifecycle-runtime:2.6.1"
  ]
}
```
추가를 해서 해결되었다
다만 여기서 멈추지 않고 
```
java.lang.NoSuchMethodError: No static method provider(Ldagger/internal/Provider;)Ldagger/internal/Provider; in class Ldagger/internal/DoubleCheck; or its super classes (declaration of 'dagger.internal.DoubleCheck' appears in /data/app/io.appium.espressoserver.test-0fk9qiRaKfO7d2-fD59MxQ==/base.apk!classes5.dex)
```
같은 힐트에 대한 에러도 나왔는데 여기서 결국 막히게 되었다 안드로이드의 hilt버전을 수정해보기도하고 EspressoBuildConfig를 수정해보기도 했으나 소용이 없었고
나와 비슷한 에러를 겪는 사람도 없는 것 같았다

만약 이 오류를 고쳐도 반복해서 NoSuchMethodError가 나올 것 같아 espresso의 사용은 전체적인 구조나 작동방식을 espresso, appium 모두 자세하게 알아야 작동시킬 수 있을 것 같다

### UiAutomater2 Driver
Espresso Driver와는 다르게 아주 손쉽게 접근이 가능했다 
별다른 buildConfig 설정 없이 keystore설정, package설정 같은 기본값들로만으로도 잘 연결되고 엘리먼트를 찾을 수 있었다
엘리먼트를 찾을 때 xml의 경우는 `By.id()`를 통해서, compose의 경우 `By.xpath()`를 통해서 찾았다
