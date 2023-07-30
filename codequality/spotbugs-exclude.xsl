<?xml version="1.0"?>
<!--
  spotbugs (https://spotbugs.github.io/) is the current source base for findbugs
  (http://findbugs.sourceforge.net/ or https://github.com/findbugsproject/findbugs,
  neither of which are active).

  findbugs is a static code analyzer that works by examining the COMPILED class files for your source code. This is in contrast
  to pmd, for instance, that examines your program strictly at the source level.
-->

<!--
  This file contains some false positive bugs detected by findbugs. Developers are responsible for assessing each
  and every warning individually and to exclude them here, together with coherent explanations of why they believe each
  warning is a false positive.
-->
<FindBugsFilter>

    <!-- See http://findbugs.sourceforge.net/bugDescriptions.html#EI_EXPOSE_REP -->
    <!-- This is too restrictive unless you are creating a library jar. It's good defensive practice when creating
     an external API, but it is often cumbersome to fix and provides no value in a RESTful web service.

     You should reconsider this if you are implementing a Java library.
     -->
    <Match>
        <Bug pattern="EI_EXPOSE_REP"/>
    </Match>

    <!-- See http://findbugs.sourceforge.net/bugDescriptions.html#EI_EXPOSE_REP2 -->
    <!-- This is too restrictive unless you are creating a library jar. It's good defensive practice when creating
     an external API, but it is often cumbersome to fix and provides no value in a RESTful web service.

     You should reconsider this if you are implementing a Java library.
     -->
    <Match>
        <Bug pattern="EI_EXPOSE_REP2"/>
    </Match>

    <!-- See http://findbugs.sourceforge.net/bugDescriptions.html#SE_NO_SERIALVERSIONID -->
    <!-- Does anyone still use Java serialization? -->
    <Match>
        <Bug pattern="SE_NO_SERIALVERSIONID"/>
    </Match>

    <!-- See http://findbugs.sourceforge.net/bugDescriptions.html#DM_NUMBER_CTOR -->
    <!-- Reason: In recent Java compilers, "new Integer(...)" has been optimized to almost always be equivalent or faster in
         performance to Integer.valueOf -->
    <Match>
        <Bug pattern="DM_NUMBER_CTOR"/>
    </Match>
    <Match>
        <Bug pattern="DM_EXIT"/>
    </Match>
    <Match>
        <Bug pattern="DLS_DEAD_LOCAL_STORE"/>
    </Match>
    <Match>
        <Bug pattern="DM_DEFAULT_ENCODING"/>
    </Match>
    <Match>
        <Bug pattern="RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE"/>
    </Match>
    <Match>
        <Bug pattern="UC_USELESS_OBJECT"/>
    </Match>
    <Match>
        <Bug pattern="NP_NULL_ON_SOME_PATH"/>
    </Match>
    <Match>
        <Bug pattern="NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"/>
    </Match>
    <Match>
        <Bug pattern="OS_OPEN_STREAM"/>
    </Match>

</FindBugsFilter>
