# WhatsappFormatter


Intro
------

WhatsappFormatter repo helps to perform formatting just like whatsapp does it on its EditText and TextView. The repo provides custom view such as 
- WhatsappTextView - a TextView that can be used for chat head and the formatting takes place by default. 
- WhatsappEditText - a EditText that can be used for compose box that shows the live formatting as and when we type.

Also provides one liner compatablity for your existing EditText / TextViews. 

Demo
-----
<img src='https://raw.githubusercontent.com/cooltechworks/WhatsappFormatter/master/screenshot/whatsappformattingdemo.gif' width=300 height=480 />


Usage
--------

#### Simple One liner Compatablity

##### ViewCompat for EditText 

```java
EditText editText; // your version of EditText object.
WhatsappViewCompat.applyFormatting(editText);
```

If you had text change listeners, use it this way to prevent multiple TextWatcher callbacks.

```java
EditText editText; // your version of EditText object.
TextWatcher watcher1, watcher2, watcher3, watcher_n; // your watcher objects.
WhatsappViewCompat.applyFormatting(editText, watcher1, watcher2, watcher3, watcher_n);
```

##### ViewCompat for TextView 

```java
TextView textView; // your version of TextView object.
WhatsappViewCompat.applyFormatting(textView);
```

CustomViews
-----------

#### WhatsAppTextView

```xml
<com.cooltechworks.views.WhatsAppTextView
                android:id="@+id/whatsapp_edit_view"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:textColor="@android:color/black"
                android:textSize="16sp" />


```

#### WhatsAppEditText

```xml
<com.cooltechworks.views.WhatsAppEditText
                android:id="@+id/whatsapp_edit_view"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:hint="Your Message"
                android:minHeight="48dp"
                android:textColor="@android:color/black"
                android:textSize="16sp" />

```


Adding to your project
------------------------

- Add the following configuration in your build.gradle file.

```gradle
repositories {
    jcenter()
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.cooltechworks:WhatsappFormatting:v1.0'
}
```

Developed By
------------

* Harish Sridharan - <harish.sridhar@gmail.com>


License
--------
```
Copyright 2016 Harish Sridharan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


[scratch_image]:https://raw.githubusercontent.com/cooltechworks/ScratchView/2ec97c9a539d5976b68bf62ec07df8c727d72be2/screenshots/scratch_image_view_demo.gif
[scratch_text]:https://raw.githubusercontent.com/cooltechworks/ScratchView/master/screenshots/scratch_text_view_\
