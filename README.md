# GankApp<br>
一个福利app<br>
## 写图片加载库的顺序：<br>
### 一、写几个工具类<br>
>	 1、bitmap工具类 -> 肯定是要测量bitmap的大小 然后压缩一下的<br>
2、IO流操作类 -> 写读取和保存bitmap的两个方法 有必要还可以加一个清理磁盘的方法<br>
3、网络操作类 ->用于下载bitmap的<br>
4、MD5类 String转MD5的加密类<br>

### 二、起始<br>
#### ImageLoader：<br>
>	  请求发起的类，顾名思义是请求最开始的地方。构造器中包含着RequestManager ，用单例模式获得对象。

关键步骤：与RequestManager关联<br>
```
	public static RequestManager with(Context context){
  	      getInstance().setContext(context);
  	      return getInstance().requestManager;
 	   }
```
#### RequestManager：<br>
>		处理请求的类，构造器包含着请求处理者。<br>
关键步骤：用load方法与RequestBuilder关联 load方法传不同的类型的loader<br>
```
		public RequestBuilder load(String url){
		        return requestBuilder.init(url,new NetLoader());
 		   }
```
#### RequestBuilder：<br>
>		自定义设置类，用来配置一些加载策略。
  
  init方法里面配置了source、loader、cache策略，初始化了RequestOption。提供了set方法来设置cache策略、apply方法设置option、into设置显示的ImageView、display方法配置loader<br>

### 三、Loader（加载类）<br>
>	  抽象类mLoader：所有Loader父类。

1、checkEngine的方法来确认是否有必要配置的cache和config ps：cache和config需要提供 setter getter。只有这里设置了config 后面的子类才能进一步判断加载方式<br>
2、提供一个checkSource的抽象方法来确认传入的资源来源。<br>
3、提供一个load的抽象方法，load(Object o, View imageView, Object tag)。<br>
4、继承loader的类需要实现checkSource、load方法并且加一个resize方法来获取bitmap大小（用config判断），load方法里面写入三级缓存加载图片。记住，要写一个方法来在ui线程中更新图片<br>

### 四、Config（配置类）<br>
1、接口： <br>
 Executor getThreadPoolExecutor();//获得线程池<br>
long getDiskCacheSize();//获得磁盘最大的容量<br>
boolean hasPreloadPic();//是否有占位图<br>
int getWidth();<br>
int getHeight();<br>
boolean isAutoSizeByView();//通过view自适应<br>
boolean isAutoSizeByHeight();<br>
boolean isAutoSizeByWidth();<br>
int getErrorPic();//获得错误加载之后的图片<br>
int getPreloadPic();//获得占位图<br>
2、子类：构造器中要初始一些各种配置。用链式builder来设置这些配置。<br>

### 五、Cache(不同的缓存策略)<br>
1、Cache(接口) 提供输入输出两个接口<br>
2、DiskCache(磁盘缓存) 用文件类写入bitmap<br>
3、NetCache(网络缓存) 只能提供get方法<br>
4、MemoryCache(内存缓存)  继承LruCache(因为LruCache可以用put方法记录加载的次数，并且根据使用次数的多少来确定销毁的图片) 初始化的时候最好分为运行时的1/8的内存(官方建议)  实现LruCache以及带有SoftwarePreference(软引用)两个HashMap保存图片。<br>
软引用的好处：当系统空间紧张的时候，软引用可以随时销毁，因此软引用是不会影响系统运行的。<br>
覆写的方法：<br>
sizeof用来判断图片的大小，若大了就销毁LruCache里面的图片，以免爆内存。小了就直接存入。<br>
entryRemoved：这里用软引用接受来自LruCache销毁的图片<br>
4、DefaultNetCache(默认缓存策略) 一起用了三种缓存策略<br>


### 使用方法<br>
```
     RequestOptions options =
          new RequestOptions()
            .setPreloadPic(R.mipmap.ic_launcher_round)
            .setErrorPic(R.mipmap.ic_launcher);


   ImageLoader
      .with(context)
      .load(url)
      .into(imageView)
      .apply(options)
      .display();
 ```
