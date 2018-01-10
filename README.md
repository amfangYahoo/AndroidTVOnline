# AndroidTVOnline
Android TV项目Demo，包含视频播放，水平，垂直RecyclerView的使用，接口请求，viewpager的使用等功能


#### 开发方式

1.通过wifiadb连接

确保盒子连接的wifi和电脑ip在同一个网段，然后打开adb调试，打开wifiadb软件，通过页面显示的adb connect xxx.xxx.xxx.xxx:5555等，在命令行cmd下执行；

2.此处以小米盒子3增强版为例，有线连接需使用双USB线连接盒子和电脑，方可使用有线连接开发；


#### 把App设置为桌面(多个桌面当按下Home键时，会弹出选择框)

注意：我是在root情况下测试的，非root请自行测试


```
<intent-filter>
    <action android:name="android.intent.action.MAIN" />
    <category android:name="android.intent.category.HOME"/>
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.MONKEY" />
</intent-filter>
```



