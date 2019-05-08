# Simple Search Engine

## Catalogue of ReadMe 

1. Introduction and Criteria
2. Solution elaboration
3. Package used and Algorithm
4. Compiling and runing 
5. Project File directory and class introduction
    1. App.java 
    2. DataHandler.java
    3. WebCrawler.java
    4. Trie.java
6. Boundary Conditions
7. Some Analyzing on Complexity
    1. Time Complexity
    2. Space Complexity

---------------------------------------------------------------------------------
### Introduction and Criteria

This is the project for Stevens Institute of Technology CS600. The basic idea is to search words in the websites you provided. The detail of the topic is in charapter 23 and Section 23.5.4 in the book "Algorithm Design and Applications".
There are several problems you should concern.
1.Use all the words in the pages of the site as index terms, excluding stop words such as articles, prepositions, and pronouns.
2. Implement what is described in Section 23.5.4. using the approach specified. You are not allowed to use Algorithms and Data Structures that are not covered in the textbook, but you can develop new algorithms based on the data structures that you have seen and proved in the textbook.
3.A ranking function is required.
4.You need to provide screenshots of your output.
5.Using jsoup to read your websites.
---------------------------------------------------------------------------------
### Solution elaboration

Above is the content of the requirement.

Here is the way I solved this. 
First, to solve problem one, I use a package in java called "opennlp", which is a natural language process method so that I can get all the words in the page of websites.

Second, I used the data structure in section 23.5.2 compressed trie, which is basiclly similar to a standard trie but it ensures that each internal node in the trie has at least two children. It enforces this rule by compressing chains of single-child nodes into individual edges.

Thrid, as for ranking system, I'm considering using the times of word appear to be the criteria of ranking. The word occur more times has higher ranking.

Fourth, see the outcomes of my result in my test result.
Fifth, Yeah, I use jsoup to read all the webpage and get the document and then handle the data.
--------------------------------------------------------------------------------
### Package used and Algorithm

So, as the solution elaboration said, I used two packages which is opennlp-tools-1.8.4.jar
jsoup-1.11.3.jar

I used jsoup to get the data from websites and handle the data with opennlp package.
Furthermore, I used a trie data structure to store all the data I get from the website and I can search how many a certain string contain in a website. So that, I can get the number of this string occurs and rank the website according to this.
--------------------------------------------------------------------------------
### Compiling and runing 

To compile and run the project I submit using IDE such as eclipse, at first, you need to add the library of two jar which I mentioned in the previous section. After that open App.java and run the project as java application.

### Project File directory and class introduction

The project I do is a maven project, due to the objective that I want to use the natural language process "opennlp" package and to write some basic testing case for the method I implemented. 

So, the file directory is as follows.

>src/main/java
>>>App.java
>>>DataHandler.java
>>>Trie.java
>>>WebCrawler.java
>src/test/java
>>>AppTest.java
>JRE System Library
>Maven Dependencies
>>>junit-3.8.1.jar
>>>opennlp-tools-1.8.4.jar
>>>jsoup-1.11.3.jar
>src
>target
>en-token.bin
>pom.xml

So, firstly, I'll post the pom.xml content. Which is the maven project properties and dependencies.

~~~
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>cs600finalproject</groupId>
  <artifactId>10442136yangyangliu</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>10442136yangyangliu</name>
  <url>http://maven.apache.org</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.apache.opennlp</groupId>
        <artifactId>opennlp-tools</artifactId>
        <version>1.8.4</version>
    </dependency>
    <dependency>
        <groupId>org.jsoup</groupId>
        <artifactId>jsoup</artifactId>
        <version>1.11.3</version>
    </dependency>
  </dependencies>
</project>
~~~

And the en-token.bin is the bin file I used for the TokenizerME class of the package opennlp.tools.tokenize. The api for this package is here:

https://opennlp.apache.org/docs/1.8.0/apidocs/opennlp-tools/opennlp/tools/tokenize/package-summary.html

And the demo for the usage of this package is here:

https://www.tutorialspoint.com/opennlp/opennlp_tokenization.htm

Except the project dependencies, There are four main java class in this project. I'll explain what these class do in detail.

#### App.java 

This class is the static main method used to prompt input from user. Due to the obejctive of our project is to search a word occurce in several website. So, here I prompt some web link url from user, if user just press Enter and search engine will search on default website which I select -- a tutorail website for programmers:

http://tutorials.jenkov.com/

After that, search engine will prompt user to input a number for the maximum search time. Because here I wrote a method to get all the herf tag from the url and get all the sub-link for what professor has asked us to search through several pages and in these pages should have some hyperlink to each other. The Criteria is following:
~~~~
You may download several pages (5 to 10) from Internet, or develop them yourself, and use them as input. Make sure there are some hyperlink between the documents and you can add more pages to your existing ones that is gathered by Web Crawler.
~~~~

Thus, for the website you input the web crawler could get the following results:

~~~~
Use default website and 5 links maximum results;

----------------Trie Search Engine ------------------
--------------By yangyang liu in cs600 course--------
> This is a search engine to search any words from a website. 
> Input the website url you what to search or press 'Enter' to search the default website:

> The website you want to search is  http://tutorials.jenkov.com/
> Input a number as the maxmium search time:
5
> The maximum search time is 5
>> [Web Crawler Running]
> Craw: http://tutorials.jenkov.com/
> Craw: http://tutorials.jenkov.com
> Craw: http://jenkov.com/about/index.html
> Craw: http://jenkov.com/rss.xml
> Craw: http://tutorials.jenkov.com/javafx/accordion.html
> There are 5 URLs 
~~~~

~~~~
Use default website and 10 links maximum results;

----------------Trie Search Engine ------------------
--------------By yangyang liu in cs600 course--------
> This is a search engine to search any words from a website. 
> Input the website url you what to search or press 'Enter' to search the default website:

> The website you want to search is  http://tutorials.jenkov.com/
> Input a number as the maxmium search time:
10
> The maximum search time is 10
>> [Web Crawler Running]
> Craw: http://tutorials.jenkov.com/
> Craw: http://tutorials.jenkov.com
> Craw: http://jenkov.com/about/index.html
> Craw: http://jenkov.com/rss.xml
> Craw: http://tutorials.jenkov.com/javafx/accordion.html
> Craw: http://tutorials.jenkov.com/java-functional-programming/streams.html
> Craw: https://www.youtube.com/watch?v=bcrl-GL0vV4
> Craw: http://tutorials.jenkov.com/java-internationalization/simpledateformat.html
> Craw: http://tutorials.jenkov.com/java/index.html
> Craw: http://tutorials.jenkov.com/java-collections/index.html
> There are 10 URLs 
~~~~

~~~~
Use google.com and 5 links maximum results;

----------------Trie Search Engine ------------------
--------------By yangyang liu in cs600 course--------
> This is a search engine to search any words from a website. 
> Input the website url you what to search or press 'Enter' to search the default website:
https://www.google.com/
> The website you want to search is  https://www.google.com/
> Input a number as the maxmium search time:
5
> The maximum search time is 5
>> [Web Crawler Running]
> Craw: https://www.google.com/
> Craw: https://www.google.com/intl/en/about/?fg=1&utm_source=google-US&utm_medium=referral&utm_campaign=hp-header
> Craw: https://store.google.com/?utm_source=hp_header&utm_medium=google_oo&utm_campaign=GS100042
> Craw: https://mail.google.com/mail/?tab=wm
> Craw: https://www.google.com/imghp?hl=en&tab=wi
> There are 5 URLs 
~~~~

~~~~
Use google.com and 10 links maximum results;

----------------Trie Search Engine ------------------
--------------By yangyang liu in cs600 course--------
> This is a search engine to search any words from a website. 
> Input the website url you what to search or press 'Enter' to search the default website:
https://www.google.com/
> The website you want to search is  https://www.google.com/
> Input a number as the maxmium search time:
10
> The maximum search time is 10
>> [Web Crawler Running]
> Craw: https://www.google.com/
> Craw: https://www.google.com/intl/en/about/?fg=1&utm_source=google-US&utm_medium=referral&utm_campaign=hp-header
> Craw: https://store.google.com/?utm_source=hp_header&utm_medium=google_oo&utm_campaign=GS100042
> Craw: https://mail.google.com/mail/?tab=wm
> Craw: https://www.google.com/imghp?hl=en&tab=wi
> Craw: https://www.google.com/intl/en/about/products?tab=wh
> Craw: https://accounts.google.com/ServiceLogin?hl=en&passive=true&continue=https://www.google.com/
> Craw: https://www.google.com/#
> Craw: https://www.google.com/intl/en_us/policies/privacy/?fg=1
> Craw: https://www.google.com/intl/en_us/policies/terms/?fg=1
> There are 10 URLs 
~~~~

After prompt input for the website you want to search on, then this application will prompt you to input several word to search and return a ranking list for all the websites these words emerge and the number of times these words appear.

~~~~
Use default website and 5 links maximum results;

>> [Begin searching]
>> [Web Crawler Complete]
> Input some word to search: 
java
> Searched against: java > Total occurence: 344
> Rank 1: http://jenkov.com/rss.xml
> Total counts for these words appear on this site is: 257
> Rank 2: http://tutorials.jenkov.com/
> Total counts for these words appear on this site is: 43
> Rank 3: http://tutorials.jenkov.com
> Total counts for these words appear on this site is: 43
> Rank 4: http://tutorials.jenkov.com/javafx/accordion.html
> Total counts for these words appear on this site is: 1
> [End of Current Seach]

> Input some word to search: 
html
> Searched against: html > Total occurence: 294
> Rank 1: http://jenkov.com/rss.xml
> Total counts for these words appear on this site is: 288
> Rank 2: http://tutorials.jenkov.com/
> Total counts for these words appear on this site is: 3
> Rank 3: http://tutorials.jenkov.com
> Total counts for these words appear on this site is: 3
> [End of Current Seach]

> Input some word to search: 
java html
> Searched against: java html > Total occurence: 638
> Rank 1: http://jenkov.com/rss.xml
> Total counts for these words appear on this site is: 545
> Rank 2: http://tutorials.jenkov.com/
> Total counts for these words appear on this site is: 46
> Rank 3: http://tutorials.jenkov.com
> Total counts for these words appear on this site is: 46
> Rank 4: http://tutorials.jenkov.com/javafx/accordion.html
> Total counts for these words appear on this site is: 1
> [End of Current Seach]
~~~~

~~~~
Use default website and 10 links maximum results;

>> [Begin searching]
>> [Web Crawler Complete]
> Input some word to search: 
java
> Searched against: java > Total occurence: 776
> Rank 1: http://jenkov.com/rss.xml
> Total counts for these words appear on this site is: 257
> Rank 2: http://tutorials.jenkov.com/java/index.html
> Total counts for these words appear on this site is: 158
> Rank 3: http://tutorials.jenkov.com/java-collections/index.html
> Total counts for these words appear on this site is: 116
> Rank 4: http://tutorials.jenkov.com/java-functional-programming/streams.html
> Total counts for these words appear on this site is: 100
> Rank 5: http://tutorials.jenkov.com/
> Total counts for these words appear on this site is: 43
> Rank 6: http://tutorials.jenkov.com
> Total counts for these words appear on this site is: 43
> Rank 7: http://tutorials.jenkov.com/java-internationalization/simpledateformat.html
> Total counts for these words appear on this site is: 38
> Rank 8: https://www.youtube.com/watch?v=bcrl-GL0vV4
> Total counts for these words appear on this site is: 20
> Rank 9: http://tutorials.jenkov.com/javafx/accordion.html
> Total counts for these words appear on this site is: 1
> [End of Current Seach]

> Input some word to search: 
html
> Searched against: html > Total occurence: 294
> Rank 1: http://jenkov.com/rss.xml
> Total counts for these words appear on this site is: 288
> Rank 2: http://tutorials.jenkov.com/
> Total counts for these words appear on this site is: 3
> Rank 3: http://tutorials.jenkov.com
> Total counts for these words appear on this site is: 3
> [End of Current Seach]

> Input some word to search: 
java html
> Searched against: java html > Total occurence: 1070
> Rank 1: http://jenkov.com/rss.xml
> Total counts for these words appear on this site is: 545
> Rank 2: http://tutorials.jenkov.com/java/index.html
> Total counts for these words appear on this site is: 158
> Rank 3: http://tutorials.jenkov.com/java-collections/index.html
> Total counts for these words appear on this site is: 116
> Rank 4: http://tutorials.jenkov.com/java-functional-programming/streams.html
> Total counts for these words appear on this site is: 100
> Rank 5: http://tutorials.jenkov.com/
> Total counts for these words appear on this site is: 46
> Rank 6: http://tutorials.jenkov.com
> Total counts for these words appear on this site is: 46
> Rank 7: http://tutorials.jenkov.com/java-internationalization/simpledateformat.html
> Total counts for these words appear on this site is: 38
> Rank 8: https://www.youtube.com/watch?v=bcrl-GL0vV4
> Total counts for these words appear on this site is: 20
> Rank 9: http://tutorials.jenkov.com/javafx/accordion.html
> Total counts for these words appear on this site is: 1
> [End of Current Seach]
~~~~

PS:You can use google.com to search any word you'd like to search. I'm not going to show the results here. And this the normal test cases for this project. 

#### DataHandler.java

This is the class to deal with the document I read from website. Here is the core code I used to get all the words in a html string.

~~~~
        InputStream is = new FileInputStream("en-token.bin");
        TokenizerModel model = new TokenizerModel(is);
        Tokenizer tokenizer = new TokenizerME(model);
        //use Opennlp package to handle the stirng and get the tokens
        String tokens[] = tokenizer.tokenize(htmlString);
~~~~

And to test this is a right method for natural language process. I write some testcase in AppTest.java using Junit test. If you find this java file, right click on this file and run as Junit test, you can see the test result for all the token I got.Still we need to filter all the punctuation and replace any whitespace character
using regular expression. Which I meant to use WhitespaceTokenizer class as the test case I wrote first, but find out this is a meaningless method because I have to append the string again. For a big document, this is just drag work.

#### WebCrawler.java

Here I implement two method. One to get all the data in a html and handle all the data using DataHandler. Another is to get all the sub-link of a website. What we use is Jsoup method. Which you can see here:

https://jsoup.org/

I also run a Junit test in AppTest.java. You can see the test result using Jsoup running Junit test.

#### Trie.java

This is the data structure we gonna use to store all the data we get from the websites. The purpose I used compressed trie instead of tire is to insert all the data as edges which is a 26 length list to store character from "a" to "z". Add a wordcount property so that each node can keep counts of the times of a word appear.

### Boundary Conditions

Here we are gonna test for all the boundary cases are:  
1.Invalid URL, which means you input something is not a url or url is not a valid website.
2.Invalid crawl limit, although you can input a really big number. But that will take really long time. So, the suggest is input no more than 50. And we need to check if input 0 or negative number
3.Invalid searching, such as stop words , empty string or symbol.

~~~~
Case 1

> This is a search engine to search any words from a website. 
> Input the website url you what to search or press 'Enter' to search the default website:
foobar
> The website you want to search is  foobar
> Input a number as the maxmium search time:
5
> The maximum search time is 5
>> [Web Crawler Running]
> Craw: foobar
> Error: Malformed URL: foobar
> There are 1 URLs 

> This is a search engine to search any words from a website. 
> Input the website url you what to search or press 'Enter' to search the default website:
123456
> The website you want to search is  123456
> Input a number as the maxmium search time:
3
> The maximum search time is 3
>> [Web Crawler Running]
> Craw: 123456
> Error: Malformed URL: 123456
> There are 1 URLs 
~~~~

~~~~
Case 2
Use https://www.google.com/ as test case:

1.try to search maximum 50 websites

----------------Trie Search Engine ------------------
--------------By yangyang liu in cs600 course--------
> This is a search engine to search any words from a website. 
> Input the website url you what to search or press 'Enter' to search the default website:
https://www.google.com/
> The website you want to search is  https://www.google.com/
> Input a number as the maxmium search time:
50
> The maximum search time is 50
>> [Web Crawler Running]
> Craw: https://www.google.com/
> Craw: https://www.google.com/intl/en/about/?fg=1&utm_source=google-US&utm_medium=referral&utm_campaign=hp-header
> Craw: https://store.google.com/?utm_source=hp_header&utm_medium=google_oo&utm_campaign=GS100042
> Craw: https://mail.google.com/mail/?tab=wm
> Craw: https://www.google.com/imghp?hl=en&tab=wi
> Craw: https://www.google.com/intl/en/about/products?tab=wh
> Craw: https://accounts.google.com/ServiceLogin?hl=en&passive=true&continue=https://www.google.com/
> Craw: https://www.google.com/#
> Craw: https://www.google.com/url?q=https://safety.google/privacy/privacy-controls%3Futm_source%3Dgoogle%3Futm_medium%3Dhpp-desktop-unauth%3Futm_campaign%3Dioprivacy&source=hpp&id=19012122&ct=3&usg=AFQjCNFQLgDJ90AP7OGWgxTjNaC5GZNt7w&sa=X&ved=0ahUKEwjQ1L683YviAhVukuAKHYIzA4IQ8IcBCAk
> Craw: https://www.google.com/intl/en_us/policies/privacy/?fg=1
> Craw: https://www.google.com/intl/en_us/policies/terms/?fg=1
> Craw: https://www.google.com/preferences?hl=en
> Craw: https://www.google.com/preferences?hl=en&fg=1
> Craw: https://www.google.com/advanced_search?hl=en&fg=1
> Craw: https://www.google.com/history/privacyadvisor/search?utm_source=googlemenu&fg=1
> Craw: https://www.google.com/history/optout?hl=en&fg=1
> Craw: https://support.google.com/websearch/?p=ws_results_help&hl=en&fg=1
> Craw: https://www.google.com/intl/en_us/ads/?subid=ww-ww-et-g-awa-a-g_hpafoot1_1!o2&utm_source=google.com&utm_medium=referral&utm_campaign=google_hpafooter&fg=1
> Craw: https://www.google.com/services/?subid=ww-ww-et-g-awa-a-g_hpbfoot1_1!o2&utm_source=google.com&utm_medium=referral&utm_campaign=google_hpbfooter&fg=1
> Craw: https://about.google/
> Craw: https://about.google/products/
> Craw: https://about.google/commitments/
> Craw: https://about.google/stories/
> Craw: https://events.google.com/io/
> Craw: https://about.google/products
> Craw: https://safety.google
> Craw: https://about.google/commitments
> Craw: https://about.google/stories
> Craw: https://careers.google.com/
> Craw: https://about.google/locations
> Craw: https://about.google/our-story
> Craw: https://abc.xyz/investor/founders-letters/2004/ipo-letter.html#_ga=2.165626872.610004439.1532311821-929489725.1521479135
> Craw: https://www.google.com/doodles
> Craw: https://about.google/#
> Craw: https://instagram.com/google/
> Craw: https://www.youtube.com/user/Google
> Craw: https://twitter.com/google
> Craw: https://www.facebook.com/Google
> Craw: https://www.linkedin.com/company/google
> Error: HTTP error fetching URL
> Craw: https://www.google.com/contact/
> Craw: https://abc.xyz/investor/
> Craw: https://about.google/locations/
> Craw: https://www.blog.google/
> Craw: https://www.thinkwithgoogle.com/
> Craw: https://www.blog.google/press/
> Craw: https://www.google.com/press/blog-social-directory.html
> Craw: https://www.google.com/permissions/
> Craw: https://services.google.com/fb/forms/speakerrequest/
> Craw: https://www.google.com/about/appsecurity/
> Craw: https://www.google.com/about/software-principles.html
> There are 50 URLs 

2.input negative number as limit

----------------Trie Search Engine ------------------
--------------By yangyang liu in cs600 course--------
> This is a search engine to search any words from a website. 
> Input the website url you what to search or press 'Enter' to search the default website:
https://www.google.com/ 
> The website you want to search is  https://www.google.com/ 
> Input a number as the maxmium search time:
-5
> Come on! You have to search at least once.


3.input 0 as limit

----------------Trie Search Engine ------------------
--------------By yangyang liu in cs600 course--------
> This is a search engine to search any words from a website. 
> Input the website url you what to search or press 'Enter' to search the default website:
https://www.google.com/ 
> The website you want to search is  https://www.google.com/ 
> Input a number as the maxmium search time:
0
> Come on! You have to search at least once.

4.input some string not a number

----------------Trie Search Engine ------------------
--------------By yangyang liu in cs600 course--------
> This is a search engine to search any words from a website. 
> Input the website url you what to search or press 'Enter' to search the default website:
https://www.google.com/ 
> The website you want to search is  https://www.google.com/ 
> Input a number as the maxmium search time:
abc
java.lang.NumberFormatException: For input string: "abc">> [Web Crawler Running]
~~~~
For case2, if the error happened, this application will be closed. You have to restart to run the app.

~~~~
Case 3
Use default page as website:
1.stop words such as "a" "above"

>> [Begin searching]
>> [Web Crawler Complete]
> Input some word to search: 
a
> The word you want to search is not validate.
> Input some word to search: 
above
> The word you want to search is not validate.

2.empty string

> Input some word to search: 

> The word you want to search is not validate.

3.symbol
> Input some word to search: 
%^$%$^
> The word you want to search is not validate.
~~~~
For case3, because a wrong input could happen sometime, so we won't exist when error occurs. Keep prompt from user and get valid input.

-----------------------------------------------------------------------------------
### Time and Space Complexity Analyzation

#### Time Complexity

Here the data structure we use is a compressed trie.

So,for each insertion, the time complexity is O(n), where n is the length of the word inserted. For m words, the insertion would cost O(nm). Also the url and its occurence for each word is stored as HashMap, since the insertion for HashMap only cost O(1), which make data insertions a lot faster considering the large amount of data. The time for re-arranging the data into TreeMap is O(log n) but searching operation is a lot less than the times of data insertion.  

#### Space Complexity

A compressed trie Structure uses much less space than a normal trie Structure. 
The data in HashMap is seperated at each node of the trie, and we can store all of them in seperated disk easily. All space are used to store the trie data structure  The only place called the Trie Class is the DataHandler class, which means the Trie only exist when we need to use DataHandler. That's the only place of storage used. The space complexity is definitly normal.